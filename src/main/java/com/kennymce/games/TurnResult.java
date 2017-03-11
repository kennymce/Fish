package com.kennymce.games;

class TurnResult {
    private Player player;
    private Pip pip;
    private Player askingPlayer;
    private final FishLogger fishLogger = new FishLogger();

    public TurnResult(Player player, Pip pip, Player askingPlayer) {
        this.player = player;               //The player being asked
        this.pip = pip;                     //The PIP
        this.askingPlayer = askingPlayer;   //The asking player

        fishLogger.LogMessage("Creating TurnResult: " + System.lineSeparator() +
            "player: " +  player.getName().toString() + System.lineSeparator() +
            "PIP: " + pip.toString() + System.lineSeparator() +
            "askingPlayer: " + askingPlayer.getName().toString());
    }

    public Player getPlayer() {
        return player;
    }

    public Pip getPip() {
        return pip;
    }

    public Player getAskingPlayer() {
        return askingPlayer;
    }
}
