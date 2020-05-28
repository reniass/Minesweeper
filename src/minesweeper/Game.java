
package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Game {

    private Scanner scanner;
    private GameBoard gameBoard;
    private UserBoard userBoard;
    private int rows;
    private int columns;
    private int mines;
    boolean isGameWon;
    boolean isGameLost;

    private boolean isGameInitialized;



    public Game(int rows, int columns) {
        scanner = new Scanner(System.in);
        gameBoard = new GameBoard(rows, columns);
        userBoard = new UserBoard(rows, columns);
        this.rows = rows;
        this.columns = columns;
        isGameInitialized = false;
        isGameWon = true;
        isGameLost = false;
    }


    private boolean isGameWon() {
//        boolean isGameWon = true;
        isGameWon = true;

        for (int i = 0; i < gameBoard.getGameBoard().length; i++) {
            for (int j = 0; j < gameBoard.getGameBoard().length; j++) {
                if (gameBoard.getGameBoard()[i][j] != 'X') {
                    if (!userBoard.getUserFields()[i][j].isRevealed()) {
                        isGameWon = false;
                    }
                }

//                else {
//                    if (userBoard.getUserFields()[i][j].isRevealed()) {
//                        isGameWon = false;
////                    }
////                }
            }
        }
        return isGameWon;
    }

    private boolean isGameLost() {
//        boolean isGameLost = false;

        for (int i = 0; i < userBoard.getUserFields().length; i++) {
            for (int j = 0; j < userBoard.getUserFields().length; j++) {
                if (gameBoard.getGameBoard()[i][j] == 'X') {
                    if (userBoard.getUserFields()[i][j].isRevealed()) {
                        isGameLost = true;
                    }
                }
            }
        }

        return isGameLost;
    }

    public void startGame() {
        System.out.print("How many mines do you want on the field? ");
        this.mines = scanner.nextInt();
        scanner.nextLine();

//        boolean isFirstFieldRevealed = false;
        String input;
        do {
            printUserBoard();
            System.out.print("Set/unset mines marks or claim a cell as free: ");

            input = scanner.nextLine();

            int x = Integer.valueOf(input.substring(0, 1)) - 1;
            int y = Integer.valueOf(input.substring(2, 3)) - 1;

            if (!isGameInitialized) {

                if (input.contains("free")) {
                    // init game board
                    gameBoard.initGameBoard(x, y);
//                    isFirstFieldRevealed = true;
                    isGameInitialized = true;

                    userBoard.reveal(x, y);

                } else if (input.contains("mine")) {
                    if (userBoard.getUserFields()[x][y].getCh() == '.') {
                        userBoard.setMine(x, y);
                    } else if (userBoard.getUserFields()[x][y].getCh() == '*'){
                        userBoard.unsetMine(x, y);
                    }
                }
            } else {
                if (input.contains("free")) {

                    userBoard.reveal(x, y);

                } else if (input.contains("mine")) {
                    if (userBoard.getUserFields()[x][y].getCh() == '.') {
                        userBoard.setMine(x, y);
                    } else if (userBoard.getUserFields()[x][y].getCh() == '*'){
                        userBoard.unsetMine(x, y);
                    }
                }
            }

//            boolean isGameWon = isGameWon();
////            boolean isGameLost = isGameLost();
////            boolean miau = true;

        } while (!isGameInitialized || (!isGameWon() && !isGameLost()));

        if (isGameWon) {
            System.out.println("Congratulations! You found all mines!");
        }

        if (isGameLost) {
            System.out.println("You stepped on a mine and failed!");
        }
    }

    public void printGameBoard() {
        System.out.println(" |123456789|" );
        for (int i = 0; i < rows; i++) {
            System.out.print((i+1) + "|");
            for (int j = 0; j < columns; j++) {
                System.out.print(gameBoard.getGameBoard()[i][j]);
            }
            System.out.println("|");
        }
        System.out.println();
    }

    public void printUserBoard() {
        System.out.println(" |123456789|" );
        for (int i = 0; i < rows; i++) {
            System.out.print((i+1) + "|");
            for (int j = 0; j < columns; j++) {
                System.out.print(userBoard.getUserFields()[i][j].getCh());

            }
            System.out.println("|");
        }
        System.out.println();
    }

    private class GameBoard {

        private char[][] gameBoard;

        GameBoard(int rows, int columns) {
            this.gameBoard = new char[rows][columns];
        }

        // x >= 1 y >= 1
        public void initGameBoard(int x, int y) {

            int numberThatCantBeGenerated = columns * x + (y + 1) ;

            int[] randomNumbers = generateRandomNumbers(mines, numberThatCantBeGenerated);

            System.out.println();

            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard.length; j++) {
                    int convertedNumber = columns * i + (j + 1);
                    // field with mine
                    if (isArrayContainNumber(randomNumbers, convertedNumber)) {
                        gameBoard[i][j] = 'X';
                        // empty field
                    } else {
                        gameBoard[i][j] = '.';
                    }
                }
            }

            printGameBoard();

            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard.length; j++) {

                    if (gameBoard[i][j] == '.') {
                        int mines = 0;
                        if(i > 0 && i < 8 && j > 0 && j < 8) {
                            char[] chars = new char[8];
                            chars[0] = gameBoard[i - 1][j - 1];
                            chars[1] = gameBoard[i - 1][j];
                            chars[2] = gameBoard[i - 1][j + 1];

                            chars[3] = gameBoard[i][j - 1];
                            chars[4] = gameBoard[i][j + 1];

                            chars[5] = gameBoard[i + 1][j - 1];
                            chars[6] = gameBoard[i + 1][j];
                            chars[7] = gameBoard[i + 1][j + 1];
                            for (int k = 0; k < chars.length; k++) {
                                if (chars[k] == 'X') {
                                    mines++;
                                }
                            }
                        } else if (i == 0 && j > 0 && j < 8) {
                            char[] chars = new char[5];
                            chars[0] = gameBoard[i][j - 1];
                            chars[1] = gameBoard[i + 1][j - 1];
                            chars[2] = gameBoard[i + 1][j];
                            chars[3] = gameBoard[i + 1][j + 1];
                            chars[4] = gameBoard[i][j + 1];
                            for (int k = 0; k < chars.length; k++) {
                                if (chars[k] == 'X') {
                                    mines++;
                                }
                            }
                        } else if(j == 0 && i > 0 && i < 8) {
                            char[] chars = new char[5];
                            chars[0] = gameBoard[i - 1][j];
                            chars[1] = gameBoard[i - 1][j + 1];
                            chars[2] = gameBoard[i][j + 1];
                            chars[3] = gameBoard[i + 1][j + 1];
                            chars[4] = gameBoard[i + 1][j];
                            for (int k = 0; k < chars.length; k++) {
                                if (chars[k] == 'X') {
                                    mines++;
                                }
                            }
                        } else if (j == 8 && i > 0 && i < 8) {
                            char[] chars = new char[5];
                            chars[0] = gameBoard[i - 1][j];
                            chars[1] = gameBoard[i - 1][j - 1];
                            chars[2] = gameBoard[i][j - 1];
                            chars[3] = gameBoard[i + 1][j - 1];
                            chars[4] = gameBoard[i + 1][j];
                            for (int k = 0; k < chars.length; k++) {
                                if (chars[k] == 'X') {
                                    mines++;
                                }
                            }
                        } else if (i == 8 && j > 0 && j < 8) {
                            char[] chars = new char[5];
                            chars[0] = gameBoard[i][j - 1];
                            chars[1] = gameBoard[i - 1][j - 1];
                            chars[2] = gameBoard[i - 1][j];
                            chars[3] = gameBoard[i - 1][j + 1];
                            chars[4] = gameBoard[i][j + 1];
                            for (int k = 0; k < chars.length; k++) {
                                if (chars[k] == 'X') {
                                    mines++;
                                }
                            }
                        } else if (i == 0 && j == 0) {
                            char[] chars = new char[3];
                            chars[0] = gameBoard[i + 1][j];
                            chars[1] = gameBoard[i + 1][j + 1];
                            chars[2] = gameBoard[i][j + 1];
                            for (int k = 0; k < chars.length; k++) {
                                if (chars[k] == 'X') {
                                    mines++;
                                }
                            }
                        } else if (i == 0 && j == 8) {
                            char[] chars = new char[3];
                            chars[0] = gameBoard[i + 1][j];
                            chars[1] = gameBoard[i + 1][j - 1];
                            chars[2] = gameBoard[i][j - 1];
                            for (int k = 0; k < chars.length; k++) {
                                if (chars[k] == 'X') {
                                    mines++;
                                }
                            }
                        } else if (i == 8 && j == 0) {
                            char[] chars = new char[3];
                            chars[0] = gameBoard[i - 1][j];
                            chars[1] = gameBoard[i - 1][j + 1];
                            chars[2] = gameBoard[i][j + 1];
                            for (int k = 0; k < chars.length; k++) {
                                if (chars[k] == 'X') {
                                    mines++;
                                }
                            }
                        } else if (i == 8 && j == 8) {
                            char[] chars = new char[3];
                            chars[0] = gameBoard[i - 1][j];
                            chars[1] = gameBoard[i - 1][j - 1];
                            chars[2] = gameBoard[i][j - 1];
                            for (int k = 0; k < chars.length; k++) {
                                if (chars[k] == 'X') {
                                    mines++;
                                }
                            }
                        }

                        if (mines > 0) {
                            switch (mines) {
                                case 1:
                                    gameBoard[i][j] = '1';
                                    break;
                                case 2:
                                    gameBoard[i][j] = '2';
                                    break;
                                case 3:
                                    gameBoard[i][j] = '3';
                                    break;
                                case 4:
                                    gameBoard[i][j] = '4';
                                    break;
                                case 5:
                                    gameBoard[i][j] = '5';
                                    break;
                                case 6:
                                    gameBoard[i][j] = '6';
                                    break;
                                case 7:
                                    gameBoard[i][j] = '7';
                                    break;
                                case 8:
                                    gameBoard[i][j] = '8';
                                    break;
                            }
                        } else {
                            gameBoard[i][j] = '.';
                        }
                    }
                }
            }
            printGameBoard();
        }

        // each generated number is associated with putting mine in the proper field on the board
        public int[] generateRandomNumbers(int numbersAmount, int numberThatCantBeGenerated) {
            int[] generatedNumbers = new int[numbersAmount];
            Random random = new Random();

            // numbers adjacent to numberThatCanBeGenerated
            int[] adjacentNumbers;
            // corners
            if (numberThatCantBeGenerated == 1) {
                adjacentNumbers = new int[3];
                adjacentNumbers[0] = 2;
                adjacentNumbers[1] = columns + 1;
                adjacentNumbers[2] = columns + 2;
            } else if (numberThatCantBeGenerated == columns) {
                adjacentNumbers = new int[3];
                adjacentNumbers[0] = columns - 1;
                adjacentNumbers[1] = 2 * columns - 1;
                adjacentNumbers[2] = 2 * columns;
            } else if (numberThatCantBeGenerated == (rows - 1) * columns + 1) {
                adjacentNumbers = new int[3];
                adjacentNumbers[0] = (rows - 2) * columns + 1;
                adjacentNumbers[1] = (rows - 2) * columns + 2;
                adjacentNumbers[2] = (rows - 1) * columns + 2;
            } else if (numberThatCantBeGenerated == rows * columns) {
                adjacentNumbers = new int[3];
                adjacentNumbers[0] = (rows - 1) * columns;
                adjacentNumbers[1] = (rows - 1) * columns - 1;
                adjacentNumbers[2] = (rows - 1) * columns + 2;
            // sides
            } else if (numberThatCantBeGenerated > 1 && numberThatCantBeGenerated < columns) {
                adjacentNumbers = new int[5];
                adjacentNumbers[0] = numberThatCantBeGenerated - 1;
                adjacentNumbers[1] = numberThatCantBeGenerated + 1;
                adjacentNumbers[2] = numberThatCantBeGenerated - 1 + columns;
                adjacentNumbers[3] = numberThatCantBeGenerated + columns;
                adjacentNumbers[4] = numberThatCantBeGenerated + 1 + columns;
            } else if (numberThatCantBeGenerated % columns == 1) {
                adjacentNumbers = new int[5];
                adjacentNumbers[0] = numberThatCantBeGenerated - columns;
                adjacentNumbers[1] = numberThatCantBeGenerated - columns + 1 ;
                adjacentNumbers[2] = numberThatCantBeGenerated + 1;
                adjacentNumbers[3] = numberThatCantBeGenerated + columns;
                adjacentNumbers[4] = numberThatCantBeGenerated + 1 + columns;
            } else if (numberThatCantBeGenerated % columns == 0) {
                adjacentNumbers = new int[5];
                adjacentNumbers[0] = numberThatCantBeGenerated - columns;
                adjacentNumbers[1] = numberThatCantBeGenerated - columns - 1;
                adjacentNumbers[2] = numberThatCantBeGenerated - 1;
                adjacentNumbers[3] = numberThatCantBeGenerated + columns - 1;
                adjacentNumbers[4] = numberThatCantBeGenerated + columns;
            }  else if (numberThatCantBeGenerated > (rows - 1) * columns + 1
                            && numberThatCantBeGenerated < rows * columns) {
                adjacentNumbers = new int[5];
                adjacentNumbers[0] = numberThatCantBeGenerated - 1;
                adjacentNumbers[1] = numberThatCantBeGenerated - columns - 1;
                adjacentNumbers[2] = numberThatCantBeGenerated - columns;
                adjacentNumbers[3] = numberThatCantBeGenerated - columns + 1;
                adjacentNumbers[4] = numberThatCantBeGenerated + 1;
            // center of board
            }  else {
                adjacentNumbers = new int[8];
                adjacentNumbers[0] = numberThatCantBeGenerated - 1 - columns;
                adjacentNumbers[1] = numberThatCantBeGenerated - columns;
                adjacentNumbers[2] = numberThatCantBeGenerated + 1 - columns;
                adjacentNumbers[3] = numberThatCantBeGenerated - 1;
                adjacentNumbers[4] = numberThatCantBeGenerated + 1;
                adjacentNumbers[5] = numberThatCantBeGenerated - 1 + columns;
                adjacentNumbers[6] = numberThatCantBeGenerated + columns;
                adjacentNumbers[7] = numberThatCantBeGenerated + 1 + columns;
            }


            int index = 0;
            while (index < numbersAmount) {
                int randomNumber = random.nextInt(rows * columns) + 1;
                if (randomNumber != numberThatCantBeGenerated
                      && !isArrayContainNumber(adjacentNumbers, randomNumber)
                        && !isArrayContainNumber(generatedNumbers, randomNumber)) {
                    generatedNumbers[index] = randomNumber;
                    index++;
                }
            }

            return generatedNumbers;
        }

        public boolean isArrayContainNumber(int[] numbers, int number) {
            for (int i = 0; i < numbers.length; i++) {
                if (numbers[i] == number) {
                    return true;
                }
            }
            return false;
        }

        public char[][] getGameBoard() {
            return gameBoard;
        }
    }



    private class UserBoard {

        private final Field[][] userFields;

        UserBoard(int rows, int columns) {
            this.userFields = new Field[rows][columns];
            initUserBoard();
        }

        private void initUserBoard() {
            for (int i = 0; i < userFields.length; i++) {
                for (int j = 0; j < userFields.length; j++) {
                    userFields[i][j] = new Field('.');
                }
            }
        }

        public void setMine(int x, int y) {
            if(!userFields[x][y].isRevealed()) {
                if (userFields[x][y].getCh() != '*') {
                    userFields[x][y].setCh('*');
                }
            } else {
                System.out.println("You can't put the mine on this field, because the field is already revealed. ");
            }
        }

        public void unsetMine(int x, int y) {
            if (!userFields[x][y].isRevealed()) {
                if (userFields[x][y].getCh() == '*') {
                    userFields[x][y].setCh('.');
                }
            } else {
                System.out.println("You can't take the mine from the field, because the field is already revealed. ");
            }

        }

        public void reveal(int x, int y) {
            if (!userFields[x][y].isRevealed()) {
                char ch = gameBoard.getGameBoard()[x][y];
                // field contain number 1, 2, 3, 4, 5, 6, 7 or 8
                if (ch == '1' || ch == '2' || ch == '3' || ch == '4'
                        || ch == '5' || ch == '6' || ch == '7' || ch == '8') {

                    switch (ch) {
                        case '1':
                            userFields[x][y].setCh('1');
                            userFields[x][y].setRevealed(true);
                            break;
                        case '2':
                            userFields[x][y].setCh('2');
                            userFields[x][y].setRevealed(true);
                            break;
                        case '3':
                            userFields[x][y].setCh('3');
                            userFields[x][y].setRevealed(true);
                            break;
                        case '4':
                            userFields[x][y].setCh('4');
                            userFields[x][y].setRevealed(true);
                            break;
                        case '5':
                            userFields[x][y].setCh('5');
                            userFields[x][y].setRevealed(true);
                            break;
                        case '6':
                            userFields[x][y].setCh('6');
                            userFields[x][y].setRevealed(true);
                            break;
                        case '7':
                            userFields[x][y].setCh('7');
                            userFields[x][y].setRevealed(true);
                            break;
                        case '8':
                            userFields[x][y].setCh('8');
                            userFields[x][y].setRevealed(true);
                            break;
                    }
                // mine on the field
                } else if (ch == 'X') {
                    userFields[x][y].setCh('X');
                    userFields[x][y].setRevealed(true);

                // if field is empty
                } else {
                    if (x > 0 && x < 8 && y > 0 && y < 8) {
                        userFields[x][y].setCh('/');
                        userFields[x][y].setRevealed(true);
                        reveal(x - 1, y - 1);
                        reveal(x - 1, y);
                        reveal(x - 1, y + 1);
                        reveal(x, y - 1);
                        reveal(x, y + 1);
                        reveal(x + 1, y - 1);
                        reveal(x + 1, y);
                        reveal(x + 1, y + 1);
                    } else if (x == 0 && y > 0 && y < 8) {
                        userFields[x][y].setCh('/');
                        userFields[x][y].setRevealed(true);
                        reveal(x, y - 1);
                        reveal(x + 1, y - 1);
                        reveal(x + 1, y);
                        reveal(x + 1, y + 1);
                        reveal(x, y + 1);
                    } else if (y == 0 && x > 0 && x < 8) {
                        userFields[x][y].setCh('/');
                        userFields[x][y].setRevealed(true);
                        reveal(x - 1, y);
                        reveal(x - 1, y + 1);
                        reveal(x, y + 1);
                        reveal(x + 1, y + 1);
                        reveal(x + 1, y);
                    } else if (y == 8 && x > 0 && x < 8) {
                        userFields[x][y].setCh('/');
                        userFields[x][y].setRevealed(true);
                        reveal(x - 1, y);
                        reveal(x - 1, y - 1);
                        reveal(x, y - 1);
                        reveal(x + 1, y - 1);
                        reveal(x + 1, y);
                    } else if (x == 8 && y > 0 && y < 8) {
                        userFields[x][y].setCh('/');
                        userFields[x][y].setRevealed(true);
                        reveal(x, y - 1);
                        reveal(x - 1, y - 1);
                        reveal(x - 1, y);
                        reveal(x - 1, y + 1);
                        reveal(x, y + 1);
                    } else if (x == 0 && y == 0) {
                        userFields[x][y].setCh('/');
                        userFields[x][y].setRevealed(true);
                        reveal(x + 1, y);
                        reveal(x + 1, y + 1);
                        reveal(x, y + 1);
                    } else if (x == 0 && y == 8) {
                        userFields[x][y].setCh('/');
                        userFields[x][y].setRevealed(true);
                        reveal(x + 1, y);
                        reveal(x + 1, y - 1);
                        reveal(x, y - 1);
                    } else if (x == 8 && y == 0) {
                        userFields[x][y].setCh('/');
                        userFields[x][y].setRevealed(true);
                        reveal(x - 1, y);
                        reveal(x - 1, y + 1);
                        reveal(x, y + 1);
                    } else if (x == 8 && y == 8) {
                        userFields[x][y].setCh('/');
                        userFields[x][y].setRevealed(true);
                        reveal(x - 1, y);
                        reveal(x - 1, y - 1);
                        reveal(x, y - 1);
                    }
                }
            } else {
                System.out.println("Field: x = " + (x + 1) + ", y = " + (y + 1) + " was revealed");
            }
        }

        public Field[][] getUserFields() {
            return this.userFields;
        }

    }
}
