/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Flashcards_Java;

import java.util.ArrayList;
import java.util.HashMap;

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
    
    public Card(String q, String a, int p){
        question = q;
        priority = p;
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
    
    public String[] storeString(){
        String[] s = new String[3];
        s[0] = question;
        s[1] = answer;
        s[2] = String.valueOf(priority);
        return s;
    }
    
    @Override
    public String toString(){
        return getFace();
    }
    
    
}

class Deck{
    private HashMap<Integer, Card> cards;
    private int c_index; // Position of empty space
    private int s_index; // Position of selected card
    private HashMap<Integer, String> history;
    private int h_index; // History index
    
    public Deck(){
        c_index = 0;
        h_index = 0;
    }
    
    public Deck(Card[] c){
        c_index = 0;
        h_index = 0;
        for(int i = 0; i < c.length; i++)
            add(c[i]);
    }
    
    public int size(){
        return cards.size();
    }
    
    public void add(Card c){
        cards.put(c_index, c);
        c_index += 1;
    }
    
}