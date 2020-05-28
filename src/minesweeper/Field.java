package minesweeper;

public class Field {

    boolean isRevealed;
    char ch;

    public Field(char ch) {
        this.ch = ch;
        this.isRevealed = false;
    }

    public char getCh() {
        return ch;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public void setRevealed(boolean logicalValue) {
        this.isRevealed = logicalValue;
    }
}
