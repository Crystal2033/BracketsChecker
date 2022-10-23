package models;

import java.util.Objects;

public class BracketInfo {
    private Direction direction;
    private String pairBracket;

    public BracketInfo(Direction direction, String pairBracket) {
        this.direction = direction;
        this.pairBracket = pairBracket;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getPairBracket() {
        return pairBracket;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BracketInfo that = (BracketInfo) o;
        return direction == that.direction && Objects.equals(pairBracket, that.pairBracket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, pairBracket);
    }

    public void setPairBracket(String pairBracket) {
        this.pairBracket = pairBracket;
    }
}
