package dataaccess;

public interface DataAccessInterface {

    public void clear();

    public void add(Record thingToAdd);

    public Record get(String identifier);

}
