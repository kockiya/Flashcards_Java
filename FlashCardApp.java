/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Flashcards_Java;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        d.undo();
        d.debugPrint();
        
        System.out.println("\nAttempting undo()");
        d.undo();
        d.debugPrint();
        
        System.out.println("\nAttempting undo()");
        d.undo();
        d.debugPrint();
        
        System.out.println("\nAttempting redo()");
        d.redo();
        d.debugPrint();
        
        System.out.println("\nAttempting redo()");
        d.redo();
        d.debugPrint();
        
        System.out.println("\nAttempting redo()");
        d.redo();
        d.debugPrint();

        System.out.println("\nAdding Slow | Pink Fox...");
        d.add(new Card("Slow", "Pink Fox"));
        d.debugPrint();
        
        System.out.println("\nAdding Quick | Brown Fox...");
        d.add(new Card("Quick", "Brown Fox"));
        d.debugPrint();
        
        System.out.println("\nRemoving card #0...");
        d.remove(0);
        d.debugPrint();
        
        System.out.println("\nRemoving card #1...");
        d.remove(1);
        d.debugPrint();
        
        System.out.println("\nRemoving card #1...");
        d.remove(1);
        d.debugPrint();
        
        System.out.println("\nRemoving card #0...");
        d.remove(0);
        d.debugPrint();
        
        
        System.out.println("\nAttempting undo()");
        d.undo();
        d.debugPrint();
        
        System.out.println("\nAttempting undo()");
        d.undo();
        d.debugPrint();
        
        System.out.println("\nAttempting undo()");
        d.undo();
        d.debugPrint();
        
        System.out.println("\nRemoving card #0...");
        d.remove(0);
        d.debugPrint();
        
        System.out.println("\nAttempting redo()");
        d.redo();
        d.debugPrint();
        
        System.out.println("\nEditing card #0 to 'Fire beats'|'Ice'");
        d.edit(0, "Fire beats", "Ice");
        d.debugPrint();
        
        System.out.println("\nEditing card #1 to 'Ice beats'|'Fire' with priority 5");
        d.edit(1, "Ice beats", "Fire", 5);
        d.debugPrint();
        
        //Testing regex ...
        String p = "(?:[\\S\\s]*Q#([\\S\\s]*)#Q[\\S\\s]*A#([\\S\\s]*)#A[\\S\\s]*)";
        Pattern pattern = Pattern.compile("(?:[\\S\\s]*Q#([\\S\\s]*)#Q[\\S\\s]*A#([\\S\\s]*)#A[\\S\\s]*)");
        Matcher matcher = pattern.matcher("dfgdQ#Hello#QasfasfA#World#AasfasQ#nono#QbA#YesYes#Adfg");
        while(matcher.find()){
            System.out.println(matcher.group(1)+" "+matcher.group(2));
        }
        
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
    private int mode; //0 = linear (default), 1 = random, 2 = priority
    
    public Deck(){
        c_index = 0;
        h_index = 0;
        s_index = 0;
        mode = 0;
        cards = new HashMap<>();
        history = new HashMap<>();
        save();
    }
    
    public Deck(Card[] c){
        c_index = 0;
        h_index = 0;
        s_index = 0;
        mode = 0;
        cards = new HashMap<>();
        history = new HashMap<>();
        for (Card c1 : c) {
            _add(c1);
        }
        save();
    }
    
    public int size(){
        return cards.size();
    }
    
    private void _add(Card c){
        cards.put(c_index, c);
        c_index++;
        save();
        overwrite();
    }
    public void add(Card c){
        //Adds a card to the deck. Adding changes history.
        _add(c);
    }
    
    public Boolean remove(int i){
        //Re-numbers cards after removing. Removing changes history.
        if(!isEmpty()){
            cards.remove(i);
            i++;
            while(i <= size()){
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
    
    public Boolean edit(int i, String new_question, String new_answer){
        if(!isEmpty()){
            cards.get(i).setAnswer(new_answer);
            cards.get(i).setQuestion(new_question);
            save();
            overwrite();
            return true;
        }
        return false;
    }
    
    public Boolean edit(int i, String new_question, String new_answer, int new_priority){
        if(!isEmpty()){
            cards.get(i).setAnswer(new_answer);
            cards.get(i).setQuestion(new_question);
            cards.get(i).setPriority(new_priority);
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
        //For simplicity, undo should reset s_index to 0 ... for now.
        if(canUndo()){
            h_index--;
            cards = history.get(h_index);
            System.out.println("Undo sucess!");
            s_index = 0;
            return true;
        }
        System.out.println("Undo failed!");
        return false;
        
    }
    
    public Boolean redo(){
        ////For simplicity, redo should reset s_index to 0 ... for now.
        if(canRedo()){
            h_index++;
            cards = history.get(h_index);
            System.out.println("Redo sucess!");
            s_index = 0;
            return true;
        }
        System.out.println("Redo failed!");   
        return false;
    }
    
    public void debugPrint(){
        if(isEmpty()){
            System.out.println("-empty-");
        }
        else{
            for(Map.Entry<Integer, Card> e: cards.entrySet()){
                System.out.print(e.getKey() + "|");
                e.getValue().debugPrint();
            }
        }
    }
    
    public void next(){
        //next() modifies s_index but does not modify history.
        switch(mode){
            case 0: s_index = (s_index < size()-1) ? s_index+1 : 0;
                    break;
            case 1: Random rand = new Random();
                    s_index = rand.nextInt((size()+1));
                    break;
            case 2: break;
            default:break;
        }
        
    }
    
    public Card getSelected(){
        //returns card that correspondes with s_index
        return cards.get(s_index);
    }
    
    public Boolean setSelected(int i){
        if(i >= 0 && i <= size()){
            s_index = i;
            return true;
        }
        return false;
            
        
    }
}