package dataaccess;

import model.GameData;
import server.ListGamesGameUnit;

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

    public Object[] listGames(){
        Collection<ListGamesGameUnit> listOfGames = new ArrayList<>(List.of());
        for (GameData val:dataMap.values()){
            listOfGames.add(new ListGamesGameUnit(val.gameID(), val.whiteUsername(), val.blackUsername(), val.gameName()));
        }
        return listOfGames.toArray();
    }

    public void updateGame(int gameID, GameData game){
        dataMap.put(gameID, game);
    }

}
