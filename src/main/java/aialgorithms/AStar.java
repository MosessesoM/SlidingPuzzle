package aialgorithms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class AStar implements Algorithm {

    private ArrayList<BoardState> pastStates = new ArrayList<>();

    private ArrayList<BoardState> availableStates = new ArrayList<>();

    public AStar(int[][] start) {
        BoardState startNode = new BoardState(start);
        startNode.setgScore(-1);
        startNode.sethScore();
        startNode.setfScore();
        startNode.setParentIndex(-1);
        pastStates.add(startNode);
        availableStates.add(startNode);
    }

    public ArrayList<BoardState> findSolution() throws IOException {
        BoardState present;
        boolean check=false;
        int index=-1;
        while (!availableStates.isEmpty()) {
            availableStates.sort(new Comparator<BoardState>() {
                @Override
                public int compare(BoardState o1, BoardState o2) {
                    return Integer.compare(o1.getfScore(), o2.getfScore());
                }
            });

//            for (BoardState boardState :availableStates){
//                for (int i=0;i<3;i++){
//                    System.out.println(boardState.getState()[i][0]+" "+boardState.getState()[i][1]+" "+boardState.getState()[i][2]);
//                }
//                System.out.println("----------------------");
//            }
//            System.out.println("======================");
//            System.in.read();

            present = availableStates.remove(0);

            if (checkFinalState(present)) {
                ArrayList<BoardState> solution = new ArrayList<>();

                System.out.println(present.getgScore());

                while (present.getParentIndex() != -1) {
                    solution.add(present);
                    present = pastStates.get(present.getParentIndex());
                }
                solution.add(present);
                return solution;
            }
            ArrayList<BoardState> successors = expansion(present);
            pastStates.add(present);
            for (BoardState successor : successors) {
                check=false;
                successor.setgScore(present.getgScore());
                successor.sethScore();
                successor.setfScore();
                successor.setParentIndex(pastStates.indexOf(present));
                for (BoardState boardState: pastStates){
                    if (checkIfStateSame(boardState,successor)){
                        check=true;
                        index=pastStates.indexOf(boardState);
                        break;
                    }
                }
                if (check) {
                    if (pastStates.get(index).getgScore() > successor.getgScore()) {
                        pastStates.get(index).setgScore(present.getgScore());
                        pastStates.get(index).setfScore();
                        availableStates.add(successor);
                    }
                } else {
                    availableStates.add(successor);
                }
            }
        }
        return null;
    }

    public ArrayList<BoardState> expansion(BoardState present) {
        ArrayList<BoardState> successors = new ArrayList<>();
        int[][] state = present.getState();
        int[][] successor = new int[3][3];
        int row = -1;
        int column = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == -1) {
                    row = i;
                    column = j;
                    break;
                }
            }
        }
        if (row + 1 < 3) {
            for (int i = 0; i < 3; i++) {
                successor[i] = state[i].clone();
            }
            successor[row][column] = state[row + 1][column];
            successor[row + 1][column] = state[row][column];
            BoardState boardState = new BoardState(successor);
            successors.add(boardState);
        }
        if (row - 1 > -1) {
            for (int i = 0; i < 3; i++) {
                successor[i] = state[i].clone();
            }
            successor[row][column] = state[row - 1][column];
            successor[row - 1][column] = state[row][column];
            BoardState boardState = new BoardState(successor);
            successors.add(boardState);
        }
        if (column + 1 < 3) {
            for (int i = 0; i < 3; i++) {
                successor[i] = state[i].clone();
            }
            successor[row][column] = state[row][column + 1];
            successor[row][column + 1] = state[row][column];
            BoardState boardState = new BoardState(successor);
            successors.add(boardState);
        }
        if (column - 1 > -1) {
            for (int i = 0; i < 3; i++) {
                successor[i] = state[i].clone();
            }
            successor[row][column] = state[row][column - 1];
            successor[row][column - 1] = state[row][column];
            BoardState boardState = new BoardState(successor);
            successors.add(boardState);
        }
        return successors;
    }

    public boolean checkFinalState(BoardState n) {
        int[][] end = {{1, 2, 3}, {4, 5, 6}, {7, 8, -1}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (end[i][j] != n.getState()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIfStateSame(BoardState a, BoardState b){
        boolean check =true;
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                if (a.getState()[i][j]!=b.getState()[i][j]){
                    check=false;
                }
            }
        }
        return check;
    }
}
