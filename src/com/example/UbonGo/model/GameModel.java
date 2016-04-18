package com.example.UbonGo.model;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by Sindre on 17.03.2016.
 */
public class GameModel {

    private GameBoard board;
    private GamePiece ghostedPiece;

    public GameModel(String boardData)
    {
        // TODO: Use board data to generate board.

        // TESTING CODE!!!!!!!!!
        ArrayList<Pair<Integer, Integer>> slots = new ArrayList<>();
        slots.add(Pair.create(0, 0));
        slots.add(Pair.create(1, 0));
        slots.add(Pair.create(2, 0));
        slots.add(Pair.create(0, 1));
        slots.add(Pair.create(1, 1));
        slots.add(Pair.create(2, 1));

        ArrayList<Pair<Integer, Integer>> slots2 = new ArrayList<>();
        slots2.add(Pair.create(0, 0));
        slots2.add(Pair.create(1, 0));
        slots2.add(Pair.create(2, 0));
        slots2.add(Pair.create(0, 1));
        slots2.add(Pair.create(1, 1));

        board = new GameBoard(slots);
        board.addPiece(new GamePiece(slots2));
    }

    public GameBoard getBoard()
    {
        return board;
    }

    public void movePieceToOn(Pair<Float, Float> position, Pair<Integer, Integer> boardRelativeCoordinate)
    {
        GamePiece p = getPiece(position);
        board.setNewPiecePosition(p, boardRelativeCoordinate);
    }

    public void movePieceToOff(Pair<Float, Float> startPosition, Pair<Float, Float> endPosition)
    {
        GamePiece p = getPiece(startPosition);
        System.out.println("(" + startPosition.first + ", " + startPosition.second + ")");
        System.out.println("Got for OffMove: " + p);

        if (p != null){
            //calculate upper left corner
            float positionX = p.getX() + (endPosition.first - startPosition.first);
            float positionY = p.getY() + (endPosition.second - startPosition.second);

            p.setPosition(positionX, positionY);

        }
    }

    public GamePiece getPiece(Pair<Float, Float> pos)
    {
        return board.getPiece(pos.first, pos.second);
    }

    public void rotate(Pair<Float, Float> pos)
    {
        // TODO: Rotate the piece at that location
    }

    public void flip(Pair<Float, Float> pos)
    {
        // TODO: Flip it along the y axis
    }

    public void undo()
    {
        // TODO: Make this undo your last move.
    }

    public boolean isCompleted()
    {
        return board.isCompleted();
    }


    public GamePiece getGhostedPiece() {
        return ghostedPiece;
    }

    public void setGhostedPiece(GamePiece ghostedPiece) {
        if (ghostedPiece != null){
            this.ghostedPiece = new GamePiece(ghostedPiece);
        }else{
            this.ghostedPiece = null;
        }
    }

    /**
     * If there's a ghost, this changes its position.
     * @param position
     */
    public void setGhostedPiecePosition(Pair<Float, Float> position)
    {
        if (ghostedPiece != null)
            ghostedPiece.setPosition(position.first, position.second);
    }
}

