package com.questionanswerforum.Pojo;

import com.google.gson.annotations.SerializedName;

public class CommentsPojo {
    @SerializedName("id")
    private String id;

    @SerializedName("answer_id")
    private
    String answer_id;

    @SerializedName("comment")
    private
    String comment;
    public CommentsPojo(String id, String answer_id,String comment) {
        this.setId(id);
        this.setAnswer_id(answer_id);
        this.setComment(comment);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
