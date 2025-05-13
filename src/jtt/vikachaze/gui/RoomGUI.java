package jtt.vikachaze.gui;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import jtt.vikachaze.Main;
import jtt.vikachaze.dao.MessageDAO;
import jtt.vikachaze.dao.impl.MessageDAOImpl;
import jtt.vikachaze.dto.Message;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.util.MessageFactory;
import jtt.vikachaze.util.Scalr;
import jtt.vikachaze.connection.Database;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImagingOpException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RoomGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane, inputPanel, messagePanel;
	private JTextArea textArea;
	private JButton sendButton, attachmentButton;
	private JScrollPane scrollPane;
	
	private int currentMessageHeight = 0;
	private Room currentRoom;
	private List<Message> messages;
	
	private MessageDAO messageDAO;
	
	private File currentAttachment = null;
	
	private static String ATTACHMENT_BUTTON_ADD_TEXT = "Add attachment";
	private static String ATTACHMENT_BUTTON_REMOVE_TEXT = "Remove attachment";
	
	private void AddMessage(Message message) throws SQLException, IOException {
		JPanel newMessagePanel = MessageFactory.createMessagePanel(message);
		newMessagePanel.setBounds(0, currentMessageHeight, newMessagePanel.getWidth(), newMessagePanel.getHeight());
		
		currentMessageHeight+=newMessagePanel.getHeight();
		
		messagePanel.add(newMessagePanel);
		messagePanel.setSize(new Dimension(messagePanel.getPreferredSize().width, currentMessageHeight));
		messagePanel.setPreferredSize(new Dimension(messagePanel.getPreferredSize().width, currentMessageHeight));

	}
	
	private void ScrollToBottom() {
		JScrollBar vertical = scrollPane.getVerticalScrollBar();	
		scrollPane.validate(); 
		vertical.setValue( vertical.getMaximum() );
	}
	
	private void AddAllMessages() throws SQLException, IOException {
		messagePanel.removeAll();
		currentMessageHeight = 0;
		
		messages = messageDAO.getByRoom(currentRoom);
		
		for (Message message : messages) {
			AddMessage(message);
		}
		ScrollToBottom();
	}
	
	public void sendMessage() {
		if (textArea.getText().equals("")) {
			if (currentAttachment == null) {
				return;
			}
		}
		
		/*if (messagesSentThisSecond>1) {
			JOptionPane.showMessageDialog(null, "Lūdzu NESPAMO");
			return;
		}*/
		
		Message m = new Message(currentRoom, textArea.getText(), Timestamp.valueOf(LocalDateTime.now()), Main.getLoggedUser());
		
		if (currentAttachment != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				String format = currentAttachment.toPath().getFileName().toString().split("\\.")[1];
				ImageIO.write(Scalr.resize(ImageIO.read(currentAttachment), 236), format, baos);
				Blob b1 = Database.getConnection().createBlob();
				b1.setBytes(1,  baos.toByteArray());
				m.setAttachment(b1);
			} catch (IllegalArgumentException | ImagingOpException | IOException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			//messagesSentThisSecond++;
			textArea.setText("");
			
			currentAttachment = null;
			attachmentButton.setText(ATTACHMENT_BUTTON_ADD_TEXT);
			messageDAO.insert(m);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public RoomGUI(Room currentRoom) throws SQLException, IOException {
		messageDAO = new MessageDAOImpl();
		this.currentRoom = currentRoom;
		
		setResizable(false);
		setTitle("VIKACHAZE - " + currentRoom.getTitle());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 668, 652);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		inputPanel = new JPanel();
		//panel_2.setBorder(new LineBorder(new Color(150, 150, 150)));
		inputPanel.setBounds(0, 462, 652, 151);
		contentPane.add(inputPanel);
		inputPanel.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
		textArea.setLineWrap(true);
		textArea.setBounds(10, 12, 485, 84);
		inputPanel.add(textArea);
		
		sendButton = new JButton("Sūtīt");
		sendButton.setBackground(new Color(201, 239, 248));
		sendButton.setBounds(507, 12, 137, 84);
		inputPanel.add(sendButton);
		
		attachmentButton = new JButton("Add attachment");
		attachmentButton.setBackground(new Color(201, 239, 248));
		attachmentButton.setBounds(10, 104, 632, 36);
		inputPanel.add(attachmentButton);
		
		attachmentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==attachmentButton && currentAttachment == null) {
					JFileChooser fileChooser = new JFileChooser();
					int response = fileChooser.showOpenDialog(null);
					
					if(response == JFileChooser.APPROVE_OPTION) {
						currentAttachment = new File(fileChooser.getSelectedFile().getAbsolutePath());
						attachmentButton.setText(ATTACHMENT_BUTTON_REMOVE_TEXT);
						JOptionPane.showMessageDialog(RoomGUI.this, "Bilde tika pievienota.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else {
					currentAttachment = null;
					attachmentButton.setText(ATTACHMENT_BUTTON_ADD_TEXT);
					JOptionPane.showMessageDialog(RoomGUI.this, "Bilde tika noņemta.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 0, 634, 463);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(scrollPane);

        messagePanel = new JPanel(null);
        scrollPane.setViewportView(messagePanel);
        
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendMessage();
			}
		});
		
		AddAllMessages();
		
		// Pievieno čata atjaunošanas, jeb refresh funkcionalitāti. Tiek izpildīts ar 1hz frekvenci.
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
		    @Override
		    public void run() {   
		    	if (!Main.isLoggedIn()) {
		    		RoomGUI.this.dispose();
		    	}
		    	
		    	int lastIndex = 0;
		    	if (!messages.isEmpty()) {
		    		lastIndex = messages.get(messages.size()-1).getId();
		    	}

		        try {
					List<Message> newMessages = messageDAO.getSinceIndex(currentRoom, lastIndex);
					
					for (Message message : newMessages) {
						messages.add(message);
						AddMessage(message);
					}
					
					if (!newMessages.isEmpty()) {
						ScrollToBottom();
					}
					
				} catch (SQLException | IOException e) {
					e.printStackTrace();
				}
		    }
		}, 0, 1, TimeUnit.SECONDS);
	}
}
