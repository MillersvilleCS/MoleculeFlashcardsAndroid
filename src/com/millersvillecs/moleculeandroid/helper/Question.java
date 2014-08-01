package com.millersvillecs.moleculeandroid.helper;

/**
 * 
 * @author connor
 * 
 * Convenience class for storing everything related to
 * a given question.
 * 
 */
public class Question {
    
    private String id, questionText;
    private Answer[] answers;
    
    public Question (String id, String questionText, Answer[] answers) {
        this.id = id;
        this.questionText = questionText;
        this.answers = answers;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getQuestionText() {
        return this.questionText;
    }
    
    public Answer getAnswer(int index) {
        return this.answers[index];
    }
    
    public Answer[] getAnswers() {
        return this.answers;
    }
}
