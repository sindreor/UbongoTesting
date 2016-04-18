package com.example.UbonGo.view;

import android.graphics.Canvas;
import android.util.Pair;
import android.view.MotionEvent;

import com.example.UbonGo.DisplayElements;
import com.example.UbonGo.controller.GameController;
import com.example.UbonGo.model.GameBoard;
import com.example.UbonGo.model.GamePiece;

import java.util.ArrayList;

import sheep.graphics.Image;
import sheep.input.TouchListener;


/**
 * Created by Sindre on 17.03.2016.
 */
public class GameView implements View, TouchListener {
    GameController controller;
    private Image background;
    private Image pieceImage;
    private Image emptyImage;

    public GameView(GameController controller)
    {
        this.controller = controller;
        controller.addTouchListener(this);

        background = DisplayElements.getInstance().getBackground();
        pieceImage = DisplayElements.getInstance().getPieceSquare();
        emptyImage = DisplayElements.getInstance().getEmptySquare();
    }

    public void drawComponents(Canvas canvas){
        background.draw(canvas, 0, 0);
    }

    public void drawBoard(Canvas canvas, GameBoard board)
    {
        float width = (float)DisplayElements.getInstance().getWidth();
        float emptyWidth = emptyImage.getWidth();

        // Draw board
        for ( Pair<Integer, Integer> pair : board.getSlots()) {
            float x = pair.first * emptyWidth + width / 2.0f;
            float y = pair.second * emptyWidth;
            emptyImage.draw(canvas, x, y);
        }
    }

    public void drawGamePieces(Canvas canvas, GameBoard board)
    {
        float pieceWidth =  pieceImage.getWidth();
        float width = (float)DisplayElements.getInstance().getWidth();
        float height = (float)DisplayElements.getInstance().getHeight();


        // Draw pieces
        for ( GamePiece piece : board.getPieces()) {
            float pieceX = piece.getX();
            float pieceY = piece.getY();

            for ( Pair<Integer, Integer> pair : piece.getSlots()) {
                float x = pair.first * pieceWidth + pieceX * width;
                float y = pair.second * pieceWidth + pieceY * height;
                pieceImage.draw(canvas, x, y);
            }
        }
    }

    public void drawGhost(Canvas canvas, GamePiece piece)
    {
        if (piece == null)
            return;

        float width = (float)DisplayElements.getInstance().getWidth();
        float height = (float)DisplayElements.getInstance().getHeight();
        float pieceWidth =  pieceImage.getWidth();
        float pieceX = piece.getX();
        float pieceY = piece.getY();

        for ( Pair<Integer, Integer> pair : piece.getSlots()) {
            float x = pair.first * pieceWidth + pieceX * width;
            float y = pair.second * pieceWidth + pieceY * height;
            pieceImage.draw(canvas, x, y);
        }
    }



    @Override
    public boolean onTouchDown(MotionEvent event) {
        controller.touchDown(
                event.getX() / DisplayElements.getInstance().getWidth(),
                event.getY() / DisplayElements.getInstance().getHeight());
        return false;
    }

    @Override
    public boolean onTouchUp(MotionEvent event) {
        controller.touchUp(
                event.getX() / DisplayElements.getInstance().getWidth(),
                event.getY() / DisplayElements.getInstance().getHeight());
        return false;
    }

    @Override
    public boolean onTouchMove(MotionEvent event) {
        controller.touchMove(
                event.getX() / DisplayElements.getInstance().getWidth(),
                event.getY() / DisplayElements.getInstance().getHeight());
        return false;
    }
}
