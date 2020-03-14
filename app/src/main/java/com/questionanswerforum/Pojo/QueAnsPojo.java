package com.questionanswerforum.Pojo;

public class QueAnsPojo {
    private String Question;
    private String Answer;
    public QueAnsPojo(String Question,String Answer){
        this.Question=Question;
        this.Answer=Answer;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
