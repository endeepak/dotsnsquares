package com.endeepak.dotsnsquares.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable, Board.LineDrawnEventListener {
    private static final int HOST_PLAYER_INDEX = 0;
    private static final int OTHER_PLAYER_INDEX = 1;
    private final Board board;
    private final int numberOfPlayers;
    transient private final ArrayList<PlayerChangedEventListener> playerChangedEventListeners = new ArrayList<PlayerChangedEventListener>();
    transient private final ArrayList<ScoreChangedEventListener> scoreChangedEventListeners = new ArrayList<ScoreChangedEventListener>();
    private final List<Player> players;
    private int currentPlayerIndex;
    private final ScoreCard scoreCard;

    public Game(Board board, List<Player> players, int firstPlayerIndex) {
        this.board = board;
        this.players = players;
        numberOfPlayers = players.size();
        currentPlayerIndex = firstPlayerIndex;
        scoreCard = new ScoreCard(players);
    }

    public Board getBoard() {
        return board;
    }

    public void onMoveDecided(Line line) {
        getCurrentPlayer().stop();
        Board.BoardStateChange boardStateChange = board.drawLine(line);
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

    public boolean isNotStarted(){
        return board.getNumberOfLinesCompleted() == 0;
    }

    public boolean canBeInterrupted(){
        return this.isNotStarted() || this.isOver();
    }

    public void start() {
        makeCurrentPlayerPlay();
    }

    private void makeCurrentPlayerPlay() {
        getCurrentPlayer().play(board.getState());
        board.addLineDrawingListener(this);
    }

    @Override
    public void onLineDrawn(Board.LineDrawnEvent event) {
        board.removeLineDrawingListener(this);
        onMoveDecided(event.getLine());
    }

    private Player getCurrentPlayer() {
        return players.get(getCurrentPlayerIndex());
    }

    public ScoreState getScoreState() {
        int hostPlayerScore = scoreCard.getScore(HOST_PLAYER_INDEX);
        int otherPlayerScore = scoreCard.getScore(OTHER_PLAYER_INDEX);
        if(hostPlayerScore > otherPlayerScore)
            return ScoreState.HostLeading;
        if(hostPlayerScore < otherPlayerScore)
            return ScoreState.GuestLeading;
        return ScoreState.Tie;
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