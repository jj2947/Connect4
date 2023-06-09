package src;

import java.util.Scanner;

// Game implementation
public class Main {

  public static void main(String[] args) {

    int numPlayers = 0;
    boolean correct = true;
    Scanner input = new Scanner(System.in);

    System.out.println(
        "  ______   ______   .__   __. .__   __.  _______   ______ .___________.    _  _    ");
    System.out.println(
        " /      | /  __  \\  |  \\ |  | |  \\ |  | |   ____| /      ||           |   | || |   ");
    System.out.println(
        "|  ,----'|  |  |  | |   \\|  | |   \\|  | |  |__   |  ,----'`---|  |----`   | || |_  ");
    System.out.println(
        "|  |     |  |  |  | |  . `  | |  . `  | |   __|  |  |         |  |        |__   _| ");
    System.out.println(
        "|  `----.|  `--'  | |  |\\   | |  |\\   | |  |____ |  `----.    |  |           | |   ");
    System.out.println(
        " \\______| \\______/  |__| \\__| |__| \\__| |_______| \\______|    |__|           |_|  "
            + " \n");

    System.out.print("Enter number of players: ");

    do {
      try {
        numPlayers = Integer.parseInt(input.nextLine());
        correct = true;
      } catch (NumberFormatException e) {
        System.out.print("Enter a valid number of players: ");
        correct = false;
      }
    } while (!correct);

    // If the number of players is invalid, ask the user to enter a valid number of
    // players
    while (numPlayers != 1 && numPlayers != 2) {
      System.out.print("Enter a valid number of players: ");

      // Checks if the input is valid
      do {
        try {
          numPlayers = Integer.parseInt(input.nextLine());
          correct = true;
        } catch (NumberFormatException e) {
          System.out.print("Enter a valid number of players: ");
          correct = false;
        }
      } while (!correct);
    }

    // Creates a new board
    BoardArray boardArray = new BoardArray(new char[7][8], 8, 7);

    boardArray.initialiseBoard();
    boardArray.printBoard();

    int p1Win = 0;
    int p2Win = 0;

    /* Main game loop */
    // While neither player has won, continue the game
    while (p1Win == 0 && p2Win == 0) {

      // Player 1's turn
      Move move1 = new Move(1, boardArray);
      move1.makeMove(move1.getMove());
      boardArray.printBoard();

      // Checks if player 1 has won and assigns the value to p1Win
      p1Win = move1.CheckWin();

      // If player 1 has won, break the while loop
      if (p1Win == 1) {
        break;
      }

      // If there are two players, get player two's move
      if (numPlayers == 2) {
        Move move2 = new Move(2, boardArray);
        move2.makeMove(move2.getMove());
        boardArray.printBoard();

        // Checks if player 2 has won and assigns the value to p2Win
        p2Win = move2.CheckWin();

        // If there is a single player, get AI's move
      } else if (numPlayers == 1) {

        Move move2 = new Move(2, boardArray);
        move2.makeMove(move2.getMove2());
        boardArray.printBoard();

        // Checks if player 2 has won and assigns the value to p2Win
        p2Win = move2.CheckWin();
      }
    }
    input.close();
  }
}
