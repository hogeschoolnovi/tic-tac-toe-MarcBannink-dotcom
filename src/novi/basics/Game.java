package novi.basics;

import jdk.nashorn.internal.parser.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static novi.basics.Main.PLAYERINPUT;

public class Game {

    private Field[] board;
    private int maxRounds;

    private Player player1;
    private Player player2;

    private int drawCount;

    private Player activePlayer;

    //Constructor
    public Game(Player player1, Player player2) {

        //board = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        board = new Field[9];

        for (int fieldIndex = 0; fieldIndex < 9; fieldIndex++) {
            board[fieldIndex] = new Field(fieldIndex + 1);
        }

        maxRounds = board.length;

        this.player1 = player1;
        this.player2 = player2;
        drawCount = 0;
        activePlayer = player1;

    }

    public void play() {

        printBoard();

        for (int round = 0; round < maxRounds; round++) {

            String activePlayerName = activePlayer.getName();
            System.out.println(activePlayerName + ", please choose a field");

            //setField ();

            int chosenField = PLAYERINPUT.nextInt();
            int chosenIndex = chosenField - 1;
            // als het veld bestaat
            if (chosenIndex < 9 && chosenIndex >= 0) {
                //als het veld leeg is, wanneer er geen token staat
                if (board[chosenIndex].isEmpty()) {
                    // wanneer de speler een token op het bord kan plaatsen
                    // de token van de actieve speler op het gekozen veld plaatsen
                    board[chosenIndex].setToken(activePlayer.getToken());
                    //player.score += 10;
                    // het nieuwe speelbord tonen
                    printBoard();

                    //   changePlayer();
                    determineWinner();
                    changePlayer(activePlayer);
                    //of al bezet is

                }

            }
            // als het veld niet bestaat
            else {
                // het mamimale aantal beurten verhogen
                maxRounds++;
                // foutmelding tonen aan de speler
                System.out.println("the chosen field does not exist, try again");
            }
            // -- terug naar het begin van de volgende beurt

        }

    }

    public void printBoard() {
        for (int fieldIndex = 0; fieldIndex < board.length; fieldIndex++) {
            System.out.print(board[fieldIndex].getToken() + " ");
            // als we het tweede veld geprint hebben of het vijfde veld geprint hebben
            // dan gaan we naar de volgende regel
            if (fieldIndex == 2 || fieldIndex == 5) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public void changePlayer(Player activePlayer) {
        if (activePlayer == player1) {
            // maak de actieve speler, speler 2
            this.activePlayer = player2;
        }
        // anders
        else {
            // maak de actieve speler weer speler 1
            this.activePlayer = player1;
        }

    }

    public void determineWinner(){
        for (int i = 0; i < 9; i = i+3) {
            //System.out.println(i);
            ArrayList<Integer> combinatie = new ArrayList();
            for (int j = i; j < i + 3 ; j++) {
               //horizontaal
                combinatie.add(j);
            }
            isWinner(combinatie);
        }

        for (int i = 0; i < 3; i++) {
            ArrayList<Integer> combinatie = new ArrayList();
            for (int j = i; j <= i + 6 ; j = j + 3) {
                //verticaal
                combinatie.add(j);
            }
            isWinner(combinatie);
        }

        ArrayList<Integer> diagonaal1 = new ArrayList();
        for (int i = 0; i < 9; i = i + 4) {
            diagonaal1.add(i);
        }
        isWinner(diagonaal1);

        ArrayList<Integer> diagonaal2 = new ArrayList();
        for (int i = 2; i < 7; i = i + 2) {
            diagonaal2.add(i);
        }
        isWinner(diagonaal2);
    }

    private boolean isWinner(List<Integer> combination){
        for(Integer i : combination){
            if(board[i].getToken() != this.activePlayer.getToken())
                return false;
        }

        System.out.println("Speler " + this.activePlayer.getName() + " heeft gewonnen!");
        return true;
    }
}