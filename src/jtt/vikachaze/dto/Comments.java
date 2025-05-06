package jtt.vikachaze.dto;

import java.sql.Timestamp;

public class Comments {
private int id;
private Timestamp sent_time;
private User user;
private Post postID;
private String text;

public Comments(Timestamp sent_time,User user,Post postID,String text) {
	this.sent_time = sent_time;
	this.postID = postID;
	this.text = text;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public Timestamp getSent_time() {
	return sent_time;
}

public void setSent_time(Timestamp sent_time) {
	this.sent_time = sent_time;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Post getPostID() {
	return postID;
}

public void setPostID(Post postID) {
	this.postID = postID;
}

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}


}
