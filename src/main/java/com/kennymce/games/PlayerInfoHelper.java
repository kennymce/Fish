package com.kennymce.games;

import java.io.File;
import java.util.List;

public class PlayerInfoHelper {
    private PlayerInfo playerInfo = new PlayerInfo();
    private List<PlayerInfo> playerInfoList;
    private JSONMapper mapper = JSONMapper.getInstance();

    public PlayerInfoHelper(File file) {

        playerInfoList = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, PlayerInfo.class));
    }

    public PlayerInfo getPlayerInfo(int index){
        return playerInfoList.get(index);
    }

    public List<PlayerInfo> getPlayersInfo(){
        return playerInfoList;
    }
}
