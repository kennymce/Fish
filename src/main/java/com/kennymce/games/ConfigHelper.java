/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kennymce.games;

import java.io.File;

public class ConfigHelper {
    private Config config = new Config();
    private JSONMapper mapper = JSONMapper.getInstance();

    public ConfigHelper(File file) {
            Object obj = null;
            obj = mapper.readValue(file,config.getClass());
            config = Config.class.cast(obj);
    }

    public int getNumberOfCardsPerPlayer(){
        return config.getNumberOfCardsPerPlayer();
    }
    public int getNumberOfPlayers(){
        return config.getNumberOfPlayers();
    }
}