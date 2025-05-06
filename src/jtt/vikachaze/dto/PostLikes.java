package jtt.vikachaze.dto;

public class PostLikes {
private int id;
private User userID;
private Post PostID;

public PostLikes(User userID,Post PostID) {
	this.userID = userID;
	this.PostID = PostID;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public User getUserID() {
	return userID;
}

public void setUserID(User userID) {
	this.userID = userID;
}

public Post getPostID() {
	return PostID;
}

public void setPostID(Post postID) {
	PostID = postID;
}

}