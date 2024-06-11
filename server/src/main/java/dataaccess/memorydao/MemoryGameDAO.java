package dataaccess.memorydao;

import dataaccess.DataAccessInterface;
import dataaccess.exception.ResponseException;
import model.GameData;
import model.ListGamesGameUnit;

import java.util.*;

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
    public void clear(String databaseName) throws ResponseException {
    }

    @Override
    public int add(GameData dataObj) {
        dataMap.put(dataObj.gameID(), dataObj);
        return 0;
    }

    @Override
    public GameData get(Integer identifier) {
        return dataMap.get(identifier);
    }

    public int size(){
        return dataMap.size();
    }

    public Object[] listGames(){
        Collection<ListGamesGameUnit> listOfGames = new ArrayList<>(List.of());
        for (GameData val:dataMap.values()){
            listOfGames.add(new ListGamesGameUnit(val.gameID(), val.whiteUsername(), val.blackUsername(), val.gameName()));
        }
        return listOfGames.toArray();
    }

}
