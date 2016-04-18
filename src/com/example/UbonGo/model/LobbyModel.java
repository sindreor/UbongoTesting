package com.example.UbonGo.model;

import java.util.ArrayList;

/**
 * Created by Sindre on 17.03.2016.
 */
public class LobbyModel {
    private ArrayList<String> players;
    private int difficulty;
    private String pin;

    public LobbyModel(String pin){
        players=new ArrayList<String>();
        difficulty=0;
        this.pin=pin;
    }

    public void addplayer(String player){
        int count=0;//Uses this to make sure that players can't have the same name
        for(String p:players){
            if(p.length()>=player.length() && p.substring(0, player.length()).equals(player)) {
                count++;

            }
        }
        if(count==0) {
            players.add(player);
        }
        else{
            players.add(player+""+count);
        }
    }

    public void removePlayer(String player){
        players.remove(player);
    }
    public void setDifficulty(int difficulty){
        if(difficulty==0||difficulty==1||difficulty==2){
            this.difficulty=difficulty;
        }
        else{
            throw new IllegalArgumentException("Difficulty can only be 0, 1 og 2");
        }
    }
    public int getDifficulty(){
        return difficulty;
    }

    public ArrayList<String> getPlayers(){
        return players;
    }

    public String getPin(){
        return pin;
    }


}
