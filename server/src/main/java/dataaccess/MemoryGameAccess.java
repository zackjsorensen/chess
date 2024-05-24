//package dataaccess;
//
//import model.GameData;
//import model.UserData;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MemoryGameAccess implements DataAccessInterface<Integer, GameData> {
//    private Map<Integer, GameData> dataMap;
//
//    public MemoryGameAccess() {
//        dataMap = new HashMap<Integer, GameData>();
//    }
//
//    @Override
//    public void clear() {
//        dataMap.clear();
//    }
//
//    @Override
//    public void add(GameData thingToAdd) {
//        dataMap.put(thingToAdd.gameID(), thingToAdd);
//    }
//
//    @Override
//    public GameData get(Integer identifier) {
//        return dataMap.get(identifier);
//                // getIdentifier, could be plugged in here
//    }
//}
