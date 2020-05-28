package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class UserBoard {

    private final Field[][] fields;
    private final Field[][] userFields;
    private final int rows;
    private final int columns;

    public UserBoard(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.fields = new Field[rows][columns];
        this.userFields = new Field[rows][columns];
    }

    public static void main(String[] args) {
        UserBoard board = new UserBoard(9, 9);
        board.createBoard();
        board.createUserBoard();
        board.printFields();

        board.startGame();

    }


    public void createBoard() {
        Random random = new Random();
        Field field = null;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int number = random.nextInt(2) + 1;
                if (number == 1) {
                    field = new Field('0');
                    fields[i][j] = field;
                } else {
                    field = new Field('.');
                    fields[i][j] = field;
                }
            }
        }
    }

    public void createUserBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Field field = new Field('.');
                userFields[i][j] = field;
            }
        }
    }

    public void printFields() {
        System.out.println(" |123456789|");
        for (int i = 0; i < rows; i++) {
            System.out.print((i+1) + "|");
            for (int j = 0; j < columns; j++) {
                System.out.print(fields[i][j].getCh());
            }
            System.out.println("|");
        }
        System.out.println();
    }

    public void printUserFields() {
        System.out.println(" |123456789|" );
        for (int i = 0; i < rows; i++) {
            System.out.print((i+1) + "|");
            for (int j = 0; j < columns; j++) {
                System.out.print(userFields[i][j].getCh());
            }
            System.out.println("|");
        }
        System.out.println();
    }

    public void startGame() {
        System.out.println("startGame method called");
        printUserFields();
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        reveal(x, y);
        printUserFields();
    }

    public boolean putMine(int x, int y) {
        if(!userFields[x][y].isRevealed()) {
            userFields[x][y].setCh('*');
            return true;
        }

        System.out.println("You can't put a mine on this field, because it is occupied. ");
        return false;
    }

    public void reveal(int x, int y) {
        if (!userFields[x][y].isRevealed()) {
            if (fields[x][y].getCh() == '0') {
                userFields[x][y].ch = '0';
                userFields[x][y].isRevealed = true;
                // if cell is empty
            } else {
                if(x > 0 && x < 8 && y > 0 && y < 8) {
                    userFields[x][y].ch = '/';
                    userFields[x][y].isRevealed = true;
                    reveal(x - 1, y - 1);
                    reveal(x - 1, y);
                    reveal(x - 1, y + 1);
                    reveal(x, y - 1);
                    reveal(x, y + 1);
                    reveal(x + 1, y - 1);
                    reveal(x + 1, y);
                    reveal(x + 1, y + 1);
                } else if (x == 0 && y > 0 && y < 8) {
                    userFields[x][y].ch = '/';
                    userFields[x][y].isRevealed = true;
                    reveal(x, y - 1);
                    reveal(x + 1, y - 1);
                    reveal(x + 1, y);
                    reveal(x + 1, y + 1);
                    reveal(x, y + 1);
                } else if (y == 0 && x > 0 && x < 8) {
                    userFields[x][y].ch = '/';
                    userFields[x][y].isRevealed = true;
                    reveal(x - 1, y);
                    reveal(x - 1, y + 1);
                    reveal(x, y + 1);
                    reveal(x + 1, y + 1);
                    reveal(x + 1, y);
                } else if (y == 8 && x > 0 && x < 8) {
                    userFields[x][y].ch = '/';
                    userFields[x][y].isRevealed = true;
                    reveal(x - 1, y);
                    reveal(x - 1, y - 1);
                    reveal(x, y - 1);
                    reveal(x + 1, y - 1);
                    reveal(x + 1, y);
                } else if (x == 8 && y > 0 && y < 8) {
                    userFields[x][y].ch = '/';
                    userFields[x][y].isRevealed = true;
                    reveal(x, y - 1);
                    reveal(x - 1, y - 1);
                    reveal(x - 1, y);
                    reveal(x - 1, y + 1);
                    reveal(x, y + 1);
                } else if (x == 0 && y == 0) {
                    userFields[x][y].ch = '/';
                    userFields[x][y].isRevealed = true;
                    reveal(x + 1, y);
                    reveal(x + 1, y + 1);
                    reveal(x, y + 1);
                } else if (x == 0 && y == 8) {
                    userFields[x][y].ch = '/';
                    userFields[x][y].isRevealed = true;
                    reveal(x + 1, y);
                    reveal(x + 1, y - 1);
                    reveal(x, y - 1);
                } else if (x == 8 && y == 0) {
                    userFields[x][y].ch = '/';
                    userFields[x][y].isRevealed = true;
                    reveal(x - 1, y);
                    reveal(x - 1, y + 1);
                    reveal(x, y + 1);
                } else if (x == 8 && y == 8) {
                    userFields[x][y].ch = '/';
                    userFields[x][y].isRevealed = true;
                    reveal(x - 1, y);
                    reveal(x - 1, y - 1);
                    reveal(x, y - 1);
                }

            }
        } else {
            System.out.println("Field: x = " + (x + 1) + ", y = " + (y + 1) + " was revealed");
        }

    }


}
