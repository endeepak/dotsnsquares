package com.example.dotsnsquares.domain;

import com.example.dotsnsquares.exception.UnEthicalMoveException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game implements Serializable, Player.MoveDecidedEventListener {
    private final Board board;
    private final int numberOfPlayers;
    transient private final ArrayList<PlayerChangedEventListener> playerChangedEventListeners = new ArrayList<PlayerChangedEventListener>();
    transient private final ArrayList<ScoreChangedEventListener> scoreChangedEventListeners = new ArrayList<ScoreChangedEventListener>();
    private final List<Player> players;
    private int currentPlayerIndex;
    private final ScoreCard scoreCard;
    private String currentToken = UUID.randomUUID().toString();

    public Game(Board board, List<Player> players) {
        this.board = board;
        this.players = players;
        numberOfPlayers = players.size();
        currentPlayerIndex = 0;
        scoreCard = new ScoreCard(players);
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public void onMoveDecided(Player.MoveDecidedEvent moveDecidedEvent) {
        if(currentToken.equals(moveDecidedEvent.getToken())) {
            Board.BoardStateChange boardStateChange = board.drawLine(moveDecidedEvent.getLine());
            if(boardStateChange.areSquaresCompleted()) {
                scoreCard.updateScoreForPlayer(currentPlayerIndex, boardStateChange.getNumberOfSquaresCompleted());
                publish(new ScoreChangedEvent(currentPlayerIndex, scoreCard.getScoreEntries().get(currentPlayerIndex).getScore()));
            } else {
                int previousPlayerIndex = currentPlayerIndex;
                currentPlayerIndex = (currentPlayerIndex + 1) % numberOfPlayers;
                board.setCurrentSquareFillColor(players.get(currentPlayerIndex).getColor());
                publish(new PlayerChangedEvent(previousPlayerIndex, currentPlayerIndex));
            }
            if(!this.isOver()) {
                makeCurrentPlayerPlay();
            }
        } else {
            throw new UnEthicalMoveException();
        }
    }

    private void publish(ScoreChangedEvent scoreChangedEvent) {
        for(ScoreChangedEventListener listener: scoreChangedEventListeners) {
            listener.onScoreChange(scoreChangedEvent);
        }
    }

    private void publish(PlayerChangedEvent playerChangedEvent) {
        for(PlayerChangedEventListener listener: playerChangedEventListeners) {
            listener.onPlayerChange(playerChangedEvent);
        }
    }

    public void addPlayerChangedEventListener(PlayerChangedEventListener listener) {
        if(playerChangedEventListeners.contains(listener)) return;
        playerChangedEventListeners.add(listener);
    }

    public void addScoreChangedEventListener(ScoreChangedEventListener listener) {
        if(scoreChangedEventListeners.contains(listener)) return;
        scoreChangedEventListeners.add(listener);
    }

    public ArrayList<ScoreEntry> getScoreEntries() {
        return scoreCard.getScoreEntries();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public boolean isOver(){
        return scoreCard.getTotalScore() == board.getNumberOfSquares();
    }

    public void start() {
        makeCurrentPlayerPlay();
    }

    private void makeCurrentPlayerPlay() {
        currentToken = UUID.randomUUID().toString();
        BoardState boardState = new BoardState(board.getBoardSize(), board.isLinesCompleted.clone(), board.getSquareMatrix(), board.getSquares());
        getCurrentPlayer().play(currentToken, this, boardState);
    }

    private Player getCurrentPlayer() {
        return players.get(getCurrentPlayerIndex());
    }

    public static class PlayerChangedEvent {
        private int currentPlayerIndex;
        private int previousPlayerIndex;

        public PlayerChangedEvent(int previousPlayerIndex, int currentPlayerIndex) {
            this.previousPlayerIndex = previousPlayerIndex;
            this.currentPlayerIndex = currentPlayerIndex;
        }

        public int getCurrentPlayerIndex() {
            return currentPlayerIndex;
        }

        public int getPreviousPlayerIndex() {
            return previousPlayerIndex;
        }
    }

    public static interface PlayerChangedEventListener {
        void onPlayerChange(PlayerChangedEvent event);
    }

    public static class ScoreChangedEvent {
        private int currentPlayerIndex;
        private int currentPlayerScore;

        public ScoreChangedEvent(int currentPlayerIndex, int currentPlayerScore) {
            this.currentPlayerIndex = currentPlayerIndex;
            this.currentPlayerScore = currentPlayerScore;
        }

        public int getCurrentPlayerIndex() {
            return currentPlayerIndex;
        }

        public int getCurrentPlayerScore() {
            return currentPlayerScore;
        }
    }

    public static interface ScoreChangedEventListener {
        void onScoreChange(ScoreChangedEvent event);
    }
}