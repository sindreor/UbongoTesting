package com.example.UbonGo.controller;

import android.graphics.Canvas;
import android.util.Pair;

import com.example.UbonGo.Main;
import com.example.UbonGo.model.GameModel;
import com.example.UbonGo.model.GamePiece;
import com.example.UbonGo.view.GameView;
import com.example.UbonGo.view.View;

import sheep.game.State;

/**
 * Created by Sindre on 17.03.2016.
 */
public class GameController extends State {
    Main main;
    View view;
    GameModel gameModel;
    GamePiece selectedPiece;
    Pair<Float, Float> startPosition;
    Long downPressedTime;

    public GameController(Main m) // TODO: change this so that networking decides what board is played, or something
    {
        main = m;
        view = new GameView(this);
        gameModel = new GameModel(""); // TODO: set boardString
        downPressedTime = System.currentTimeMillis();
    }

    public void update(float dt){

    }

    public void draw(Canvas canvas){
        view.drawComponents(canvas);
        ((GameView) view).drawBoard(canvas, gameModel.getBoard());
        ((GameView) view).drawGamePieces(canvas, gameModel.getBoard());
        ((GameView) view).drawGhost(canvas, gameModel.getGhostedPiece());
    }

    public void touchDown(float x, float y)
    {
        // Set the start position used for moving pieces
        startPosition = Pair.create(x, y);

        // Get the targeted piece
        selectedPiece = gameModel.getPiece(Pair.create(x, y));
        System.out.println("(" + x + ", " + y + ")");
        System.out.println("Got: " + selectedPiece);

        // Check if double tap
        if (System.currentTimeMillis() - downPressedTime < 200) // Tap time 200ms
        {
            gameModel.rotate(Pair.create(x, y));
        }
        downPressedTime = System.currentTimeMillis();

        // Set ghost
        gameModel.setGhostedPiece(selectedPiece);

    }

    public void touchMove(float x, float y)
    {
        // Move ghost piece
        if (gameModel.getGhostedPiece() != null)
            gameModel.getGhostedPiece().setPosition(x, y);
    }

    public void touchUp(float x, float y)
    {
        // If no piece was selected, don't do nothing
        if (selectedPiece == null)
            return;

        // Check what side of the screen it was dropped
        if (x < 0.5f)
        { // Left side
            gameModel.movePieceToOff(startPosition, Pair.create(x, y));
        }
        else
        { // Right side
            gameModel.movePieceToOn(startPosition, Pair.create((int)x, (int)y)); // TODO: Fix this; A probable cause of piece misplacement
        }

        // Unselect piece
        selectedPiece = null;

        // Remove ghost
        gameModel.setGhostedPiece(null);
        System.out.println(gameModel.isCompleted());
    }
}
