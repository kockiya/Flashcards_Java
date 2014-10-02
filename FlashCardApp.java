/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Flashcards_Java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Xuxion
 */
public class FlashCardApp {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Deck d = new Deck();
        
        System.out.println("Adding Q:Hello A:World P:0...");
        d.add(new Card("Hello", "World"));
        d.debugPrint();
        
        System.out.println("\nAdding Q:Goodbye A:Universe P:1...");
        d.add(new Card("Goodbye", "Universe", 1));
        d.debugPrint();
        
        System.out.println("\nAttempting undo()");
        if(d.undo()){
            System.out.println("Undo success!");
            d.debugPrint();}
        else
            System.out.println("Undo failed!");
        
        System.out.println("\nAttempting undo()");
        if(d.undo()){
            System.out.println("Undo success!");
            d.debugPrint();}
        else
            System.out.println("Undo failed!");
        
        System.out.println("\nAttempting undo()");
        if(d.undo()){
            System.out.println("Undo success!");
            d.debugPrint();}
        else
            System.out.println("Undo failed!");
        
        
        System.out.println("\nAttempting redo()");
        if(d.redo()){
            System.out.println("Redo success!");
            d.debugPrint();}
        else
            System.out.println("Redo failed!");
        
        System.out.println("\nAttempting redo()");
        if(d.redo()){
            System.out.println("Redo success!");
            d.debugPrint();}
        else
            System.out.println("Redo failed!");
        
        System.out.println("\nAttempting redo()");
        if(d.redo()){
            System.out.println("Redo success!");
            d.debugPrint();}
        else
            System.out.println("Redo failed!");
        
        
        
        
        
        
        
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
    
    public void setPriority(int p){
        priority = p;
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
    
    public void debugPrint(){
        System.out.println("Q:"+question+" A:"+answer+" P:"+String.valueOf(priority));
    }
    
    
}

class Deck{
    private HashMap<Integer, Card> cards; //Each card is associated with a number
    private int c_index; // Position of empty space
    private int s_index; // Position of selected card
    private HashMap<Integer, HashMap<Integer, Card>> history;
    private int h_index; // History index
    
    public Deck(){
        c_index = 0;
        h_index = 0;
        s_index = 0;
        cards = new HashMap<>();
        history = new HashMap<>();
        save();
    }
    
    public Deck(Card[] c){
        c_index = 0;
        h_index = 0;
        s_index = 0;
        cards = new HashMap<>();
        history = new HashMap<>();
        for (Card c1 : c) {
            add(c1);
        }
        save();
    }
    
    public int size(){
        return cards.size();
    }
    
    public void add(Card c){
        //Adds a card to the deck. Adding changes history.
        cards.put(c_index, c);
        c_index++;
        save();
        overwrite();
    }
    
    public Boolean remove(int i){
        //Re-numbers cards after removing. Removing changes history.
        if(!isEmpty()){
            cards.remove(i);
            i++;
            while(i < size()){
                cards.put(i-1, cards.get(i));
                cards.remove(i);
                i++;
            }
            save();
            overwrite();
            return true;
        }
        return false;
    }
    
    
    
    private void save(){
        //Saves cards map to history map.
        if(!history.isEmpty())
            h_index++;
        history.put(h_index, new HashMap<>(cards));
    }
    
    private void overwrite(){
        //Removes content of the history map from h_index onward.
        int overwrite_index = history.size();
        while(overwrite_index > h_index){
            history.remove(overwrite_index);
            overwrite_index--;
        }
    }
    
    public Boolean isEmpty(){
        return cards.size() <= 0;
    }
    
    public Boolean canUndo(){
        return h_index > 0;
    }
    
    public Boolean canRedo(){
        return h_index < history.size()-1;
    }
    
    public Boolean undo(){
        if(canUndo()){
            h_index--;
            cards = history.get(h_index);
            return true;
        }
        return false;
    }
    
    public Boolean redo(){
        if(canRedo()){
            h_index++;
            cards = history.get(h_index);
            return true;
        }
        return false;
    }
    
    public void debugPrint(){
        for(Card c: cards.values()){
            c.debugPrint();
        }
    }
}