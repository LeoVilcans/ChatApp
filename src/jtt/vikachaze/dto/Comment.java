package jtt.vikachaze.dto;

import java.sql.Timestamp;

public class Comment {
private int id;
private Timestamp sent_time;
private User user;
private Post post;
private String text;

public Comment(Timestamp sent_time, User user, Post post, String text) {
	this.sent_time = sent_time;
	this.user = user;
	this.post = post;
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

public Post getPost() {
	return post;
}

public void setPost(Post postID) {
	this.post = postID;
}

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}


}
