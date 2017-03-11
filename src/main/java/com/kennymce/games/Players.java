package com.kennymce.games;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class Players implements Iterable<Player> {
    ArrayList<Player> players;

    public Players()  {
        players = new ArrayList<Player>();
    }
    public ListIterator<Player> iterator() {
        return players.listIterator();
    }

    public void AddPlayer(Player player){
        players.add(player);
    }
    public void RemovePlayer(Player player){players.remove(player);}

    public Player player(int index){
        return players.get(index);
    }

    public int Size(){
        return players.size();
    }

    public Player RandomPlayer(){
        int index = 0;
        try {
            Random randomGenerator = new Random();
            index = randomGenerator.nextInt(players.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return players.get(index);
        }
    }

    public boolean contains(Player player){
        boolean containsPlayer = false;
        if (players.contains(player)){
            containsPlayer = true;
        }
        return containsPlayer;
    }

    //DEBUG methods below here
    public String ShowAllPlayersAllCards(){
        StringBuilder sb = new StringBuilder();
        for (Player p:players) {
            sb.append("Player ").append(p.getName().toString()).append(": ").append(p.cardsToString()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
