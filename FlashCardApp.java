/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Flashcards_Java;

/**
 *
 * @author Xuxion
 */
public class FlashCardApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Card p = new Card("Hello", "World");
        System.out.println(p);
    }
    
}


class Card {
    private String question;
    private String answer;
    private Boolean face;
    private int priority;
    
    public Card(String q, String a){
        question = q;
        face = true;
        answer = a;
    }
    
    public String getQuestion(){
        return question;
    }
    
    public void setQuestion(String new_question){
        question = new_question;
    }
    public String getAnswer(){
        return answer;
    }
    
    public void setAnswer(String new_answer){
        answer = new_answer;
    }
    
    public int getPriority(){
        return priority;
    }
    
    public String getFace(){
        return face ? question : answer;
    }
    
    public void flipCard(){
       face = !face;
    }
    
    @Override
    public String toString(){
        return getFace();
    }
    
    
}