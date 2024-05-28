package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements DataAccessInterface<Integer, GameData> {
    private final Map<Integer, GameData> dataMap;

    public MemoryGameDAO() {
        dataMap = new HashMap<Integer, GameData>();
    }


    @Override
    public void clear() {
        dataMap.clear();
    }

    @Override
    public void add(GameData dataObj) {
        dataMap.put(dataObj.gameID(), dataObj);
    }

    @Override
    public GameData get(Integer identifier) {
        return dataMap.get(identifier);
    }

    public int size(){
        return dataMap.size();
    }

    public ArrayList listGames(){
        return (ArrayList<GameData>) dataMap.values();
    }

    public void updateGame(int gameID, GameData game){
        dataMap.put(gameID, game);
    }



}
