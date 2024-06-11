package model;

public record ListGamesResult(java.util.ArrayList<ListGamesGameUnit> games) {

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ListGamesGameUnit game : games) {
            stringBuilder.append(game);
        }
        return stringBuilder.toString();
    }
}
