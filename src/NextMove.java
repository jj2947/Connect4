package src;

import java.util.ArrayList;
import java.util.List;
import src.directions.Diagonal;
import src.directions.Horizontal;
import src.directions.Vertical;

public class NextMove {

  private BoardArray boardArray;

  public enum Direction {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL
  }

  Diagonal diagonal = new Diagonal();
  Horizontal horizontal = new Horizontal();
  Vertical vertical = new Vertical();

  public NextMove(BoardArray boardArray) {
    this.boardArray = boardArray;
  }

  public List<Position> getNextMoves() {

    List<Position> blockMoves = getNext('X');
    List<Position> nextMoves = getNext('O');

    if (blockMoves.isEmpty()) {
      return nextMoves;
    }

    if (nextMoves.isEmpty()) {
      return blockMoves;
    } else if (nextMoves.get(0).getCount() == 3 || blockMoves.get(0).getCount() < 2) {
      return nextMoves;
    } else {
      return blockMoves;
    }
  }

  private List<Position> getNext(char currentSymbol) {
    List<Position> nextMoves = new ArrayList<>();

    List<Position> longestDiagonal = diagonal.getLongestSequence(boardArray, currentSymbol);
    List<Position> longestHorizontal = horizontal.getLongestSequence(boardArray, currentSymbol);
    List<Position> longestVertical = vertical.getLongestSequence(boardArray, currentSymbol);
    List<Position> horizontalGapMoves = horizontal.getGapSequence(boardArray, currentSymbol);
    List<Position> diagonalGapMoves = diagonal.getGapSequence(boardArray, currentSymbol);

    nextMoves = longestDiagonal;

    if (nextMoves.isEmpty()) {
      nextMoves = longestHorizontal;
    } else {
      nextMoves = compareLongestSequences(nextMoves, longestHorizontal);
    }

    if (nextMoves.isEmpty()) {
      nextMoves = longestVertical;
    } else {
      nextMoves = compareLongestSequences(nextMoves, longestVertical);
    }

    if (nextMoves.isEmpty()) {
      nextMoves = horizontalGapMoves;
    } else {
      nextMoves = compareLongestSequences(nextMoves, horizontalGapMoves);
    }

    if (nextMoves.isEmpty()) {
      nextMoves = diagonalGapMoves;
    } else {
      nextMoves = compareLongestSequences(nextMoves, diagonalGapMoves);
    }

    return nextMoves;
  }

  private List<Position> compareLongestSequences(List<Position> seq1, List<Position> seq2) {
    if (!seq2.isEmpty()) {
      if (seq2.get(0).getCount() > seq1.get(0).getCount()) {
        return seq2;
      } else if (seq2.get(0).getCount() == seq1.get(0).getCount()) {
        seq1.addAll(seq2);
      }
    }
    return seq1;
  }

  public List<Position> addMove(List<Position> nextMoves, int count, int row, int col) {

    if (nextMoves.isEmpty()) {
      Position position = new Position(count, row, col);
      nextMoves.add(position);
    } else if (nextMoves.get(nextMoves.size() - 1).getCount() < count) {
      nextMoves.clear();
      Position position = new Position(count, row, col);
      nextMoves.add(position);
    } else if (count == nextMoves.get(nextMoves.size() - 1).getCount()) {
      Position position = new Position(count, row, col);
      nextMoves.add(position);
    }

    return nextMoves;
  }

  public boolean validMove(
      String type, String direction, int row, int col, int count, char symbol) {

    if (type.equals("gap")
        && (boardArray.getBoard()[row][col] != ' ' && boardArray.getBoard()[row][col] != symbol)) {
      return false;
    } else if (!(type.equals("gap"))
        && (boardArray.getBoard()[row][col] != ' ' || boardArray.getBoard()[row + 1][col] == ' ')) {
      return false;
    }

    if (count == 2) {
      switch (direction) {
        case "left horizontal":
          if (boardArray.getBoard()[row][col - 1] != ' '
              && boardArray.getBoard()[row][col - 1] != symbol) {
            return false;
          }
          break;

        case "right horizontal":
          if (boardArray.getBoard()[row][col + 1] != ' '
              && boardArray.getBoard()[row][col + 1] != symbol) {
            return false;
          }
          break;

        case "left diagonal":
          if (boardArray.getBoard()[row - 1][col + 1] != ' '
              && boardArray.getBoard()[row - 1][col + 1] != symbol) {
            return false;
          }
          break;

        case "right diagonal":
          if (boardArray.getBoard()[row - 1][col - 1] != ' '
              && boardArray.getBoard()[row - 1][col - 1] != symbol) {
            return false;
          }
          break;

        case "vertical":
          if (boardArray.getBoard()[row - 1][col] != ' '
              && boardArray.getBoard()[row - 1][col] != symbol) {
            return false;
          }
          break;
      }

    } else if (count == 1) {
      int maxCol = boardArray.getWidth() - 1;
      int minCol = 0;
      int minRow = 0;

      switch (direction) {
        case "left horizontal":
          for (int i = 1; i <= 2; i++) {
            if (col - 2 < minCol
                || (boardArray.getBoard()[row][col - i] != ' '
                    && boardArray.getBoard()[row][col - i] != symbol)) {
              return false;
            }
          }
          break;

        case "right horizontal":
          for (int i = 1; i <= 2; i++) {
            if (col + 2 > maxCol
                || (boardArray.getBoard()[row][col + i] != ' '
                    && boardArray.getBoard()[row][col + i] != symbol)) {
              return false;
            }
          }
          break;

        case "left diagonal":
          for (int i = 1; i <= 2; i++) {
            if (row - 2 < minRow
                || col + 2 > maxCol
                || (boardArray.getBoard()[row - i][col + i] != ' '
                    && boardArray.getBoard()[row - i][col + i] != symbol)) {
              return false;
            }
          }
          break;

        case "right diagonal":
          for (int i = 1; i <= 2; i++) {
            if (row - 2 < minRow
                || col - 2 < minCol
                || (boardArray.getBoard()[row - i][col - i] != ' '
                    && boardArray.getBoard()[row - i][col - i] != symbol)) {
              return false;
            }
          }
          break;

        case "vertical":
          for (int i = 1; i <= 2; i++) {
            if (row - 2 < minRow
                || boardArray.getBoard()[row - i][col] != ' '
                    && boardArray.getBoard()[row - i][col] != symbol) {
              return false;
            }
          }
          break;
      }

    } else if (count == 0) {
      int maxCol = boardArray.getWidth() - 1;
      int minCol = 0;
      int minRow = 0;

      switch (direction) {
        case "left horizontal":
          for (int i = 1; i <= 3; i++) {
            if (col - 3 < minCol
                || (boardArray.getBoard()[row][col - i] != ' '
                    && boardArray.getBoard()[row][col - i] != symbol)) {
              return false;
            }
          }
          break;

        case "right horizontal":
          for (int i = 1; i <= 3; i++) {
            if (col + 3 > maxCol
                || (boardArray.getBoard()[row][col + i] != ' '
                    && boardArray.getBoard()[row][col + i] != symbol)) {
              return false;
            }
          }
          break;

        case "left diagonal":
          for (int i = 1; i <= 3; i++) {
            if (row - 3 < minRow
                || col + 3 > maxCol
                || (boardArray.getBoard()[row - i][col + i] != ' '
                    && boardArray.getBoard()[row - i][col + i] != symbol)) {
              return false;
            }
          }
          break;

        case "right diagonal":
          for (int i = 1; i <= 3; i++) {
            if (row - 3 < minRow
                || col - 3 < minCol
                || (boardArray.getBoard()[row - i][col - i] != ' '
                    && boardArray.getBoard()[row - i][col - i] != symbol)) {
              return false;
            }
          }
          break;

        case "vertical":
          for (int i = 1; i <= 3; i++) {
            if (row - 3 < minRow
                || (boardArray.getBoard()[row - i][col] != ' '
                    && boardArray.getBoard()[row - i][col] != symbol)) {
              return false;
            }
          }
          break;
      }
    }
    return true;
  }
}
