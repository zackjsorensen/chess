package model;

import java.util.Objects;

public record ListGamesGameUnit(int gameID, String whiteUsername, String blackUsername, String gameName) {

    @Override
    public String toString() {
        return "ListGamesGameUnit[" +
                "gameID=" + gameID + ", " +
                "whiteUsername=" + whiteUsername + ", " +
                "blackUsername=" + blackUsername + ", " +
                "gameName=" + gameName + ']';
    }

}
