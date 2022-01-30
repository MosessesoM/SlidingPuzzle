package aialgorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;

public class Genetic implements Algorithm {

    private ArrayList<BoardState> currentPopulation = new ArrayList<>();

    private ArrayList<BoardState> history = new ArrayList<>();

    public Genetic(int[][] start) {
        BoardState startNode = new BoardState(start);
        startNode.setFitnessFunction(0);
        startNode.setParentIndex(-1);
        currentPopulation.add(startNode);
    }
    //    TODO: Przy krzyżowaniu można spróbować z wykonywaniem takich ruchów by puste pola były na tym samym mijescu co u drugieo rodzica

    @Override
    public ArrayList<BoardState> findSolution() {
        boolean check=false;
        int generation=0;
        BoardState present = null;
        ArrayList<BoardState> solution = new ArrayList<>();
        while (!check && generation<100000){
            for (BoardState boardState:currentPopulation){
                if (checkFinalState(boardState)){
                    check=true;
                    present=boardState;
                }
            }
            generation++;
            setPopulation();
        }

        if (!present.equals(null)){
            while (present.getParentIndex() != -1) {
                solution.add(present);
                present = history.get(present.getParentIndex());
            }
            solution.add(present);
        } else {
            solution=null;
        }

        return solution;
    }

    private void setPopulation() {
        if (currentPopulation.size()>10){
            currentPopulation.subList(10, currentPopulation.size()).clear();
        }

        ArrayList<BoardState> pom = new ArrayList<>();
        for (BoardState boardState : currentPopulation) {
            pom.addAll(expansion(boardState));
        }
        pom.sort(new Comparator<BoardState>() {
            @Override
            public int compare(BoardState o1, BoardState o2) {
                return Integer.compare(o1.getFitnessFunction(), o2.getFitnessFunction());
            }
        });

        ArrayList<BoardState> newPopulation = new ArrayList<>(0);
        while (pom.size()>1){
            newPopulation.addAll(reproduction(pom.remove(0), pom.remove(0)));
        }
        pom.clear();
        pom.addAll(newPopulation);
        newPopulation.clear();
        for (int i=0;i< pom.size();i++){
            newPopulation.add(mutation(pom.remove(i)));
        }
        currentPopulation.clear();
        currentPopulation.addAll(newPopulation);
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

        if (row + 1 < 3) {
            for (int i = 0; i < 3; i++) {
                successor[i] = state[i].clone();
            }
            successor[row][column] = state[row + 1][column];
            successor[row + 1][column] = state[row][column];
            BoardState boardState = new BoardState(successor);
            boardState.setParentIndex(history.indexOf(present));
            boardState.setFitnessFunction(history.get(boardState.getParentIndex()).getFitnessFunction());
            successors.add(boardState);
        }
        if (row - 1 > -1) {
            for (int i = 0; i < 3; i++) {
                successor[i] = state[i].clone();
            }
            successor[row][column] = state[row - 1][column];
            successor[row - 1][column] = state[row][column];
            BoardState boardState = new BoardState(successor);
            boardState.setParentIndex(history.indexOf(present));
            boardState.setFitnessFunction(history.get(boardState.getParentIndex()).getFitnessFunction());
            successors.add(boardState);
        }
        if (column + 1 < 3) {
            for (int i = 0; i < 3; i++) {
                successor[i] = state[i].clone();
            }
            successor[row][column] = state[row][column + 1];
            successor[row][column + 1] = state[row][column];
            BoardState boardState = new BoardState(successor);
            boardState.setParentIndex(history.indexOf(present));
            boardState.setFitnessFunction(history.get(boardState.getParentIndex()).getFitnessFunction());
            successors.add(boardState);
        }
        if (column - 1 > -1) {
            for (int i = 0; i < 3; i++) {
                successor[i] = state[i].clone();
            }
            successor[row][column] = state[row][column - 1];
            successor[row][column - 1] = state[row][column];
            BoardState boardState = new BoardState(successor);
            boardState.setParentIndex(history.indexOf(present));
            boardState.setFitnessFunction(history.get(boardState.getParentIndex()).getFitnessFunction());
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
        int history_index=-1;
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
        history_index=history.indexOf(parent1);

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
            BoardState boardState = new BoardState(child1State);
            boardState.setParentIndex(history_index);
            boardState.setFitnessFunction(history.get(boardState.getParentIndex()).getFitnessFunction());
            history.add(boardState);
            history_index=history.indexOf(boardState);
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
            boardState.setParentIndex(history_index);
            boardState.setFitnessFunction(history.get(boardState.getParentIndex()).getFitnessFunction());
            history.add(boardState);
            history_index=history.indexOf(boardState);
        }
        if (history_index!=history.indexOf(parent1)){
            BoardState child1 = new BoardState(child1State);
            child1.setParentIndex(history.get(history_index).getParentIndex());
            child1.setFitnessFunction(history.get(child1.getParentIndex()).getFitnessFunction());
            children.add(child1);
        } else {
            children.add(parent1);
        }

        history.add(parent2);
        history_index=history.indexOf(parent2);

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
            BoardState boardState = new BoardState(child1State);
            boardState.setParentIndex(history_index);
            boardState.setFitnessFunction(history.get(boardState.getParentIndex()).getFitnessFunction());
            history.add(boardState);
            history_index=history.indexOf(boardState);
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
            BoardState boardState = new BoardState(child1State);
            boardState.setParentIndex(history_index);
            boardState.setFitnessFunction(history.get(boardState.getParentIndex()).getFitnessFunction());
            history.add(boardState);
            history_index=history.indexOf(boardState);
        }
        if (history_index!=history.indexOf(parent2)){
            BoardState child2 = new BoardState(child2State);
            child2.setParentIndex(history.get(history_index).getParentIndex());
            child2.setFitnessFunction(history.get(child2.getParentIndex()).getFitnessFunction());
            children.add(child2);
        } else {
            children.add(parent2);
        }
        return children;
    }

    private BoardState mutation(BoardState chromosome) {
        Random random = new Random();
        ArrayList<BoardState> mutants = new ArrayList<>();
        mutants.add(chromosome);
        boolean check=false;


        while (!check){
            history.add(mutants.get(mutants.size()-1));
            int[][] state = mutants.get(mutants.size()-1).getState();
            int row = -1;
            int column = -1;
            int[][] successor = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (state[i][j] == -1) {
                        row = i;
                        column = j;
                        break;
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
                        boardState.setFitnessFunction(history.get(boardState.getParentIndex()).getFitnessFunction());
                        mutants.add(boardState);
                    } else {
                        check=true;
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
                        boardState.setFitnessFunction(history.get(boardState.getParentIndex()).getFitnessFunction());
                        mutants.add(boardState);
                    } else {
                        check=true;
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
                        boardState.setFitnessFunction(history.get(boardState.getParentIndex()).getFitnessFunction());
                        mutants.add(boardState);
                    } else {
                        check=true;
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
                        boardState.setFitnessFunction(history.get(boardState.getParentIndex()).getFitnessFunction());
                        mutants.add(boardState);
                    } else {
                        check=true;
                    }
                    break;
                default:
                    check=true;
                    break;
            }
        }
        return mutants.get(mutants.size()-1);
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
