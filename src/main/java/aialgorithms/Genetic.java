package aialgorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Genetic implements Algorithm {

    private ArrayList<BoardState> currentPopulation = new ArrayList<>();

    private ArrayList<BoardState> history = new ArrayList<>();

    public Genetic(int[][] start) {
        BoardState startNode = new BoardState(start);
        startNode.setFitnessFunction(0);
        startNode.setParentIndex(-1);
        currentPopulation.add(startNode);
    }

    @Override
    public ArrayList<BoardState> findSolution() {
        boolean check = false;
        int generation = 0;
        BoardState present = null;
        ArrayList<BoardState> solution = new ArrayList<>();
        while (!check && generation < 100000) {
            for (BoardState boardState : currentPopulation) {
                if (checkFinalState(boardState)) {
                    check = true;
                    present = boardState;
                }
            }
            generation++;
            setPopulation();
        }

        if (present != null) {
            while (present.getParentIndex() != -1) {
                solution.add(present);
                present = history.get(present.getParentIndex());
            }
            solution.add(present);
        } else {
            solution = null;
        }
        return solution;
    }

    private void setPopulation() {
        if (currentPopulation.size() > 100) {
            currentPopulation.subList(100, currentPopulation.size()).clear();
        }

        ArrayList<BoardState> pom = new ArrayList<>(0);
        for (BoardState boardState : currentPopulation) {
            pom.addAll(expansion(boardState));
        }

        ArrayList<BoardState> newPopulation = new ArrayList<>(0);
        while (pom.size() > 1) {
            newPopulation.addAll(reproduction(pom.remove(0), pom.remove(0)));
        }
        pom.clear();

        while (!newPopulation.isEmpty()) {
            pom.add(mutation(newPopulation.remove(0)));
        }

        currentPopulation.clear();
        currentPopulation.addAll(pom);

        currentPopulation.sort(Comparator.comparingInt(BoardState::getFitnessFunction));
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
        }

        history.add(present);
        int parentIndex = history.indexOf(present);

        if (row + 1 < 3) {
            for (int i = 0; i < 3; i++) {
                successor[i] = state[i].clone();
            }
            successor[row][column] = state[row + 1][column];
            successor[row + 1][column] = state[row][column];
            BoardState boardState = new BoardState(successor);
            boardState.setParentIndex(parentIndex);
            boardState.setFitnessFunction(checkPast(boardState));
            successors.add(boardState);
        }
        if (row - 1 > -1) {
            for (int i = 0; i < 3; i++) {
                successor[i] = state[i].clone();
            }
            successor[row][column] = state[row - 1][column];
            successor[row - 1][column] = state[row][column];
            BoardState boardState = new BoardState(successor);
            boardState.setParentIndex(parentIndex);
            boardState.setFitnessFunction(checkPast(boardState));
            successors.add(boardState);
        }
        if (column + 1 < 3) {
            for (int i = 0; i < 3; i++) {
                successor[i] = state[i].clone();
            }
            successor[row][column] = state[row][column + 1];
            successor[row][column + 1] = state[row][column];
            BoardState boardState = new BoardState(successor);
            boardState.setParentIndex(parentIndex);
            boardState.setFitnessFunction(checkPast(boardState));
            successors.add(boardState);
        }
        if (column - 1 > -1) {
            for (int i = 0; i < 3; i++) {
                successor[i] = state[i].clone();
            }
            successor[row][column] = state[row][column - 1];
            successor[row][column - 1] = state[row][column];
            BoardState boardState = new BoardState(successor);
            boardState.setParentIndex(parentIndex);
            boardState.setFitnessFunction(checkPast(boardState));
            successors.add(boardState);
        }

        return successors;
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
        int historyIndex = -1;
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

        history.add(parent1);
        historyIndex = history.indexOf(parent1);
        children.add(parent1);

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
            BoardState boardState = new BoardState(child1State);
            boardState.setParentIndex(historyIndex);
            boardState.setFitnessFunction(checkPast(boardState));

            history.add(boardState);
            historyIndex = history.indexOf(boardState);
            children.add(boardState);
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
            BoardState boardState = new BoardState(child1State);
            boardState.setParentIndex(historyIndex);
            boardState.setFitnessFunction(checkPast(boardState));

            history.add(boardState);
            historyIndex = history.indexOf(boardState);
            children.add(boardState);
        }

        BoardState child1 = children.remove(children.size() - 1);

        history.add(parent2);
        historyIndex = history.indexOf(parent2);
        children.add(parent2);

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
            BoardState boardState = new BoardState(child2State);
            boardState.setParentIndex(historyIndex);
            boardState.setFitnessFunction(checkPast(boardState));

            history.add(boardState);
            historyIndex = history.indexOf(boardState);
            children.add(boardState);
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
            BoardState boardState = new BoardState(child2State);
            boardState.setParentIndex(historyIndex);
            boardState.setFitnessFunction(checkPast(boardState));

            history.add(boardState);
            historyIndex = history.indexOf(boardState);
            children.add(boardState);
        }

        BoardState child2 = children.remove(children.size() - 1);

        children.clear();
        children.add(child1);
        children.add(child2);

        return children;
    }

    private BoardState mutation(BoardState chromosome) {
        Random random = new Random();
        ArrayList<BoardState> mutants = new ArrayList<>();
        mutants.add(chromosome);
        boolean check = false;


        while (!check) {
            history.add(mutants.get(mutants.size() - 1));
            int[][] state = mutants.get(mutants.size() - 1).getState();
            int row = -1;
            int column = -1;
            int[][] successor = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (state[i][j] == -1) {
                        row = i;
                        column = j;
                    }
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
                        boardState.setParentIndex(history.indexOf(mutants.get(mutants.size() - 1)));
                        boardState.setFitnessFunction(checkPast(boardState));
                        mutants.add(boardState);
                    } else {
                        check = true;
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
                        boardState.setParentIndex(history.indexOf(mutants.get(mutants.size() - 1)));
                        boardState.setFitnessFunction(checkPast(boardState));
                        mutants.add(boardState);
                    } else {
                        check = true;
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
                        boardState.setParentIndex(history.indexOf(mutants.get(mutants.size() - 1)));
                        boardState.setFitnessFunction(checkPast(boardState));
                        mutants.add(boardState);
                    } else {
                        check = true;
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
                        boardState.setParentIndex(history.indexOf(mutants.get(mutants.size() - 1)));
                        boardState.setFitnessFunction(checkPast(boardState));
                        mutants.add(boardState);
                    } else {
                        check = true;
                    }
                    break;
                default:
                    check = true;
                    break;
            }
        }

        return mutants.get(mutants.size() - 1);
    }

    @Override
    public boolean checkFinalState(BoardState n) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (end[i][j] != n.getState()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int checkPast(BoardState current) {
        boolean check = true;
        ArrayList<BoardState> past = new ArrayList<>();
        BoardState boardState = current;
        int value = 0;
        while (boardState.getParentIndex() != -1) {
            past.add(history.get(boardState.getParentIndex()));
            boardState = history.get(boardState.getParentIndex());
        }
        for (BoardState pastState : past) {
            check = true;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (current.getState()[i][j] != pastState.getState()[i][j]) {
                        check = false;
                    }
                }
            }
            if (check) {
                value++;
            }
        }
        return value;
    }

}
