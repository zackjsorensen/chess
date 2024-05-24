package dataaccess;

import model.GameData;

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

}
