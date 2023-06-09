package src.directions;

import java.util.ArrayList;
import java.util.List;
import src.BoardArray;
import src.NextMove;
import src.Position;

// Class for the horizontal direction
public class Horizontal {

  // Gets longest continous sequence in horizontal direction
  public List<Position> getLongestSequence(BoardArray boardArray, char symbol) {
    List<Position> horizontalMoves = new ArrayList<>();

    NextMove move = new NextMove(boardArray);

    int count, row, col;
    count = 0;

    // Right horizontal sequence
    for (row = boardArray.getHeight() - 1; row >= 0; row--) {
      count = 0;
      for (col = 1; col < boardArray.getWidth() - 1; col++) {
        // If the symbol is the same as the player's symbol, increment count
        if (boardArray.getBoard()[row][col] == symbol) {
          count++;
        }

        // If the symbol is not the same as the player's symbol, reset count and
        // continue the loop
        else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {
          if (move.validMove("", "right horizontal", row, col, count, symbol)) {
            move.addMove(horizontalMoves, count, row, col);
          }
          count = 0;
        }
      }
    }

    // Left horizontal sequence
    for (row = boardArray.getHeight() - 1; row >= 0; row--) {
      count = 0;
      for (col = boardArray.getWidth() - 1; col >= 1; col--) {
        // If the symbol is the same as the player's symbol, increment count
        if (boardArray.getBoard()[row][col] == symbol) {
          count++;
        }

        // If the symbol is not the same as the player's symbol, reset count and
        // continue the loop
        else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {
          if (move.validMove("", "left horizontal", row, col, count, symbol)) {
            move.addMove(horizontalMoves, count, row, col);
          }
          count = 0;
        }
      }
    }
    return horizontalMoves;
  }

  // Checks for a win in the horizontal direction
  public boolean checkWin(BoardArray boardArray, char symbol) {
    int row, col;
    int count;
    boolean win = false;

    for (row = boardArray.getHeight() - 1; row >= 0; row--) {
      count = 0;
      for (col = 1; col < boardArray.getWidth() - 1; col++) {
        // If the symbol is the same as the player's symbol, increment count
        if (boardArray.getBoard()[row][col] == symbol) {
          count++;

          if (count == 4) {
            win = true;
          }
        }

        // If the symbol is not the same as the player's symbol, reset count and
        // continue the loop
        else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {
          count = 0;
        }
      }
    }
    return win;
  }

  // Checks for a gap move in the horizontal direction
  public List<Position> getGapSequence(BoardArray boardArray, char symbol) {
    int row, col, count;

    NextMove move = new NextMove(boardArray);
    List<Position> gapMoves = new ArrayList<>();

    for (row = boardArray.getHeight() - 2; row >= 0; row--) {
      for (col = 1; col < boardArray.getWidth() - 2; col++) {
        if (move.validMove("gap", "right horizontal", row, col, 0, symbol)) {
          count = findGapCount(row, col, boardArray, symbol);
          if (count > 1) {
            for (int i = col; i < col + 4; i++) {
              if (boardArray.getBoard()[row][i] == ' '
                  && boardArray.getBoard()[row + 1][i] != ' '
                  && boardArray.getBoard()[row][i - 1] == symbol) {
                move.addMove(gapMoves, count, row, i);
              }
            }
          }
        }
      }
    }

    for (row = boardArray.getHeight() - 2; row >= 0; row--) {
      for (col = boardArray.getWidth() - 2; col > 0; col--) {
        if (move.validMove("gap", "left horizontal", row, col, 0, symbol)) {
          count = findGapCount(row, col - 4, boardArray, symbol);
          if (count > 1) {
            for (int i = col - 3; i <= col; i++) {
              if (boardArray.getBoard()[row][i] == ' '
                  && boardArray.getBoard()[row + 1][i] != ' '
                  && boardArray.getBoard()[row][i + 1] == symbol) {
                move.addMove(gapMoves, count, row, i);
              }
            }
          }
        }
      }
    }
    return gapMoves;
  }

  // Helper method that gets the number of symbols in the horizontal direction for a gap move
  public int findGapCount(int row, int col, BoardArray boardArray, char symbol) {
    int count = 0;

    for (int i = col; i < col + 4; i++) {
      if (boardArray.getBoard()[row][i] == symbol) {
        count++;
      }
    }
    return count;
  }
}
