//package dataaccess;
//
//import model.GameData;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public abstract class MemoryParentDAO<T, U> implements DataAccessInterface{
//    public Map<T, U> dataMap;
//
//    public MemoryParentDAO() {
//        dataMap = new HashMap<T, U>();
//    }
//
//    public void clear() {
//        dataMap.clear();
//    }
//
//    public abstract T getIdentifier(U dataObj);
//
//    public void add(Object dataObj) {
//        dataMap.put(this.getIdentifier(), (U) dataObj); // maybe need some kind of check there....
//    }
//
//}