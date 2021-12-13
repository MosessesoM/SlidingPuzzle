package aialgorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Genetic implements Algorithm {

    private ArrayList<BoardState> currentPopulation = new ArrayList<>();

    private ArrayList<BoardState> history = new ArrayList<>();

    public Genetic(int[][] start) {
        BoardState startNode = new BoardState(start);
        startNode.setFinesFunction();
        startNode.setParentIndex(-1);
        currentPopulation.add(startNode);
    }
    //    TODO: Przy krzyżowaniu można spróbować z wykonywaniem takich ruchów by puste pola były na tym samym mijescu co u drugieo rodzica

    @Override
    public ArrayList<BoardState> findSolution() {
        boolean check=false;
        BoardState present = new BoardState(null);
        ArrayList<BoardState> solution = new ArrayList<>();
        while (!check){
            for (BoardState boardState:currentPopulation){
                check=checkFinalState(boardState);
                if (check){
                    present=boardState;
                }
            }
            setPopulation();
        }

        while (present.getParentIndex()!=-1){
            solution.add(present);
            present=history.get(present.getParentIndex());
        }
        return solution;
    }

    @Override
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
            if (row != -1) {
                break;
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
        for (BoardState boardState : successors) {
            boardState.setParentIndex(history.indexOf(present));
        }
        return successors;
    }

    private BoardState mutation(BoardState chromosome) {
        Random random = new Random();
        int[][] state = chromosome.getState();
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
            if (row != -1) {
                break;
            }
        }
        switch (random.nextInt(4)) {
            case 0:
                if (row + 1 < 3) {
                    for (int i = 0; i < 3; i++) {
                        successor[i] = state[i].clone();
                    }
                    successor[row][column] = state[row + 1][column];
                    successor[row + 1][column] = state[row][column];
                    BoardState boardState = new BoardState(successor);
                    boardState.setFinesFunction();
                    history.add(chromosome);
                    boardState.setParentIndex(history.indexOf(chromosome));
                    return boardState;
                }
                break;
            case 1:
                if (row - 1 > -1) {
                    for (int i = 0; i < 3; i++) {
                        successor[i] = state[i].clone();
                    }
                    successor[row][column] = state[row - 1][column];
                    successor[row - 1][column] = state[row][column];
                    BoardState boardState = new BoardState(successor);
                    boardState.setFinesFunction();
                    history.add(chromosome);
                    boardState.setParentIndex(history.indexOf(chromosome));
                    return boardState;
                }
                break;
            case 2:
                if (column + 1 < 3) {
                    for (int i = 0; i < 3; i++) {
                        successor[i] = state[i].clone();
                    }
                    successor[row][column] = state[row][column + 1];
                    successor[row][column + 1] = state[row][column];
                    BoardState boardState = new BoardState(successor);
                    boardState.setFinesFunction();
                    history.add(chromosome);
                    boardState.setParentIndex(history.indexOf(chromosome));
                    return boardState;
                }
                break;
            case 3:
                if (column - 1 > -1) {
                    for (int i = 0; i < 3; i++) {
                        successor[i] = state[i].clone();
                    }
                    successor[row][column] = state[row][column - 1];
                    successor[row][column - 1] = state[row][column];
                    BoardState boardState = new BoardState(successor);
                    boardState.setFinesFunction();
                    history.add(chromosome);
                    boardState.setParentIndex(history.indexOf(chromosome));
                    return boardState;
                }
                break;
            default:
                break;
        }
        return chromosome;
    }

    private ArrayList<BoardState> reproduction(BoardState parent1, BoardState parent2) {
        ArrayList<BoardState> children = new ArrayList<>();
        int pom;
        int row = -1;
        int column = -1;
        int row1 = -1;
        int row2 = -1;
        int column1 = -1;
        int column2 = -1;
        int[][] child1State = new int[3][3];
        int[][] child2State = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                child1State[i][j] = parent1.getState()[i][j];
                child2State[i][j] = parent2.getState()[i][j];
                if (parent1.getState()[i][j] == -1) {
                    row1 = i;
                    column1 = j;
                }
                if (parent2.getState()[i][j] == -1) {
                    row2 = i;
                    column2 = j;
                }
            }
        }
//        TODO: ruchy zrobić przez przestawianie kafli (zmniejszanie/zwiększania współrzędnych pustego miejsca w tym dziecku) aż kolumna i wiersz nie będzie się zgadzało ze współrzędnymi pustego miejsca w drugim rodzicu
        row = row1;
        column = column1;
        while (row != row2) {
            if (row < row2) {
                pom = child1State[row + 1][column];
                child1State[row + 1][column] = child1State[row][column];
                child1State[row][column] = pom;
                row++;
            } else {
                pom = child1State[row - 1][column];
                child1State[row - 1][column] = child1State[row][column];
                child1State[row][column] = pom;
                row--;
            }
        }
        while (column != column2) {
            if (column < column2) {
                pom = child1State[row][column + 1];
                child1State[row][column + 1] = child1State[row][column];
                child1State[row][column] = pom;
                column++;
            } else {
                pom = child1State[row][column - 1];
                child1State[row][column - 1] = child1State[row][column];
                child1State[row][column] = pom;
                column--;
            }
        }

        row = row2;
        column = column2;
        while (row != row1) {
            if (row < row1) {
                pom = child2State[row + 1][column];
                child2State[row + 1][column] = child2State[row][column];
                child2State[row][column] = pom;
                row++;
            } else {
                pom = child2State[row - 1][column];
                child2State[row - 1][column] = child2State[row][column];
                child2State[row][column] = pom;
                row--;
            }
        }
        while (column != column1) {
            if (column < column1) {
                pom = child2State[row][column + 1];
                child2State[row][column + 1] = child2State[row][column];
                child2State[row][column] = pom;
                column++;
            } else {
                pom = child2State[row][column - 1];
                child2State[row][column - 1] = child2State[row][column];
                child2State[row][column] = pom;
                column--;
            }
        }
        BoardState child1 = new BoardState(child1State);
        child1.setFinesFunction();
        BoardState child2 = new BoardState(child2State);
        child2.setFinesFunction();
        children.add(child1);
        children.add(child2);
        return children;
    }

    //    To jeszcze nie zrobine, nie am tak łatwo
    private void setPopulation() {
        ArrayList<BoardState> nextPopulation = new ArrayList<>();
        for (BoardState boardState : currentPopulation) {
            history.add(boardState);
            nextPopulation.addAll(expansion(boardState));
        }
        nextPopulation.sort(new Comparator<BoardState>() {
            @Override
            public int compare(BoardState o1, BoardState o2) {
                return Integer.compare(o1.getFitnesFunction(), o2.getFitnesFunction());
            }
        });
        int size = nextPopulation.size();
        for (int i = 0; i < size; i += 2) {
            nextPopulation.addAll(reproduction(nextPopulation.remove(i), nextPopulation.remove(i + 1)));
        }
        for (int i=0;i<size;i++){
            nextPopulation.add(mutation(nextPopulation.remove(i)));
        }
        currentPopulation.clear();
        currentPopulation.addAll(nextPopulation);
    }

    @Override
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
}
