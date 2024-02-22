package com.rohith.ecommercemobilefashionapp;

import java.util.Date;

public class Comment {

    public String userName;
    public String comment;
    public Date commentUploadDate;
    public String imageUrl;
    public float rating;
    public String userId;

    public Comment(String userName, String comment, Date commentUploadDate, String imageUrl, float rating, String userId) {
        this.userName = userName;
        this.comment = comment;
        this.commentUploadDate = commentUploadDate;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentUploadDate() {
        return commentUploadDate;
    }

    public void setCommentUploadDate(Date commentUploadDate) {
        this.commentUploadDate = commentUploadDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
