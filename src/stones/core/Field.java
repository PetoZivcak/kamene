package stones.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Field {
    private GameState state = GameState.PLAYING;
    private int nrOfRow;
    private int nrOfCol;
//   private final Square square;

    public int getNrOfRow() {
        return nrOfRow;
    }

    public void setNrOfRow(int nrOfRow) {
        this.nrOfRow = nrOfRow;
    }

    public int getNrOfCol() {
        return nrOfCol;
    }


    public void setNrOfCol(int nrOfCol) {
        this.nrOfCol = nrOfCol;
    }

    public Square getSquare(int r, int c) {
        return squares[r][c];
    }

//    public Square[][] getSquares(int r, int c) {
//        return this.squares[r][c];
//    }

    private final Square[][] squares;

    private static final Pattern PATTERN = Pattern.compile("([0-9]{1})([S]{1})([0-9]{1})", Pattern.CASE_INSENSITIVE);

    public void moveSquare(String command) {
        String myString;
//        int emptySquare;
        int moveUp = 0;
        int moveDown = 0;
        int moveLeft = 0;
        int moveRight = 0;
        boolean moveUpB = false;
        boolean moveDownB = false;
        boolean moveLeftB = false;
        boolean moveRightB = false;

        myString = findEmpty();

        Matcher matcher = PATTERN.matcher(myString);

        if (matcher.matches()) {

            int rPos = Integer.parseInt(matcher.group(1));
            int cPos = Integer.parseInt(matcher.group(3));
//            emptySquare = squares[rPos][cPos].getValue();


            if (command.equals("U")) {
                if (rPos + 1 < nrOfRow && rPos + 1 > 0) {
                    moveUp = squares[rPos + 1][cPos].getValue();
                    squares[rPos][cPos].setValue(moveUp);
                    squares[rPos + 1][cPos].setValue(0);
                }

            }
            if (command.equals("D")) {
                if ((rPos - 1) >= 0 && rPos - 1 < nrOfRow - 1) {
                    moveDown = squares[rPos - 1][cPos].getValue();

                    squares[rPos][cPos].setValue(moveDown);
                    squares[rPos - 1][cPos].setValue(0);
                }

            }
            if (command.equals("L")) {
                if ((cPos + 1) > 0 && cPos + 1 < nrOfRow) {
                    moveLeft = squares[rPos][cPos + 1].getValue();
                    moveLeftB = true;
                    squares[rPos][cPos].setValue(moveLeft);
                    squares[rPos][cPos + 1].setValue(0);
                }

            }
            if (command.equals("R")) {
                if ((cPos - 1) >= 0 && cPos - 1 < nrOfCol) {
                    moveRight = squares[rPos][cPos - 1].getValue();
                    squares[rPos][cPos].setValue(moveRight);
                    squares[rPos][cPos - 1].setValue(0);

                }

            }

            //squares[Integer.parseInt(matcher.group(0))][Integer.parseInt(matcher.group(2))].setValue(squares[r][c].getValue());
            //squares[r][c].setValue(0);

            //squares[Character.getNumericValue()][c].setValue(0);
        }
    }

    public Field(int nrOfRow, int nrOfCol) {
        this.nrOfRow = nrOfRow;
        this.nrOfCol = nrOfCol;
        squares = new Square[nrOfRow][nrOfCol];
        generate();
    }

    public void generate() {
        int k = 0;
        for (int i = 0; i < nrOfRow; i++) {
            for (int j = 0; j < nrOfCol; j++) {
                squares[i][j] = new Square();
                squares[i][j].setValue(k);
                k++;
            }
        }
    }

    public int getValuesOfSquare(int r, int c) {
        int result = 0;
        result = squares[r][c].getValue();
        return result;
    }

    private String findEmpty() {
        String myEmpty = "";
        for (int i = 0; i < nrOfRow; i++) {
            for (int j = 0; j < nrOfCol; j++) {
                if (squares[i][j].getValue() == 0) {
                    myEmpty = i + "S" + j;
                }

            }

        }

        return myEmpty;
    }

    public boolean solvedGame() {
        int k = 0;
        int validator = 0;
        for (int i = 0; i < nrOfRow; i++) {
            for (int j = 0; j < nrOfCol; j++) {

                if (squares[i][j].getValue() == k) {
                    validator++;
                }
                if (validator == nrOfCol * nrOfRow) {
                    state = GameState.SOLVED;
                    return true;
                }
                k++;
            }
        }
        return false;
    }

    long startMillis;


}