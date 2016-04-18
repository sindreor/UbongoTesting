package com.example.UbonGo.controller;



import android.content.Context;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.UbonGo.Main;
import com.example.UbonGo.model.LobbyModel;
import com.example.UbonGo.serverManager.ClientCom;
import com.example.UbonGo.view.StartLobbyView;
import com.example.UbonGo.view.StartedLobbyView;
import com.example.UbonGo.view.View;

import java.util.ArrayList;
import java.util.Arrays;

import sheep.game.State;
import sheep.input.KeyboardListener;

/**
 * Created by Sindre on 17.03.2016.
 */
public class LobbyController extends State implements KeyboardListener {
    private Main main;
    private LobbyModel model;
    private View view;

    public LobbyController(Main main){
        this.main=main;
        this.view=new StartLobbyView(this);
    }

    public void update(float dt){

    }

    public void draw(Canvas canvas){
        view.drawComponents(canvas);
    }

    public void btnBackClicked(){
        main.changeMainController(new MenuController(main));
        ((StartLobbyView) view).removeTextFields();
    }

    public void btnStartNewLobbyClicked(String playerName){
        try{
            if(playerName.length()<1){
                throw  new IllegalArgumentException("Missing player name");
            }
            model=new LobbyModel(ClientCom.getInstance().startLobby());
            model.addplayer(playerName);
            ((StartLobbyView) view).removeTextFields();
            view=new StartedLobbyView(this,true); //The parameter is true, since this player is the owner. The gui for the owner will therefore be different, to give access to owner functionality
            ((StartedLobbyView)view).setPlayersList(model.getPlayers());
        }
        catch(IllegalArgumentException e){
            ((StartLobbyView) view).setError(e.getMessage()); //Prints the error message
        }



    }

    public void btnStartExistingLobbyClicked(String playerName,String pin){
        try{
            if(playerName.length()<1){
                throw  new IllegalArgumentException("Missing player name");
            }
            if(pin.length()!=4){
                throw new IllegalArgumentException("Pin must be 4 digits long");
            }

            ArrayList<String> lobbyDetails=ClientCom.getInstance().joinPlayer(playerName, pin);
            int difficulty=ClientCom.getInstance().getDifficulty(pin);

            model=new LobbyModel(pin);
            ((StartLobbyView) view).removeTextFields();
            view=new StartedLobbyView(this,false);//Owner-parameter set to false.
            for(String player:lobbyDetails){
                model.addplayer(player);
            }
            model.setDifficulty(difficulty);
            if(difficulty==0){
                ((StartedLobbyView) view).writeDifficulty("easy");
            }
            else if(difficulty==1){
                ((StartedLobbyView) view).writeDifficulty("medium");
            }
            else if(difficulty==3){
                ((StartedLobbyView) view).writeDifficulty("hard");
            }

            ((StartedLobbyView) view).setPlayersList(model.getPlayers());



        }
        catch(IllegalArgumentException e){
            ((StartLobbyView) view).setError(e.getMessage()); //Prints the error message
        }


    }

    public void btnBackToLobbyJoiningClicked(){
        main.changeMainController(new LobbyController(main));


    }

    public void btnStartGameClicked()
    {
        System.out.println("Starting the game");
        main.changeMainController(new GameController(main));
    }

    public void dropDownChanged(String value){
        if("easy".equals(value)) {
            model.setDifficulty(0);
        }
        else if("medium".equals(value)){
            model.setDifficulty(1);
        }
        else if("hard".equals(value)){
            model.setDifficulty(2);
        }
        ClientCom.getInstance().setDifficulty(model.getPin(),model.getDifficulty());
    }
    public Main getMain(){
        return main;
    }


}
