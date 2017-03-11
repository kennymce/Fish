/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kennymce.games;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.PlayGame();

        //final FishLogger fishLogger = LogManager.getLogger("Fish");

/*
        LogInfo(fishLogger, "Starting Game...........");
        LogInfo(fishLogger, game.players.Size() + " players:");
        LogInfo(fishLogger, game.toString());
*/


        for (int i=0;i<game.getNumberOfPlayers();i++){
/*
            LogInfo(fishLogger, game.players.player(i).getName() + ":");
            LogInfo(fishLogger, game.players.player(i).cardsToString());
*/
        }

    }

/*
    static void LogInfo( FishLogger fishLogger, String str){
        System.out.println(str);
        fishLogger.info(str);
*/
    }

