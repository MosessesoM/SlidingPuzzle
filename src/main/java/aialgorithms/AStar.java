package aialgorithms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class AStar {

//    TODO: trzeba wybierać najniższą wartość f ze wszytkich mozliwych pójść i dodawaj je do listy

    private int g_score=0;

    private final int[][] end = {{1, 2, 3}, {4, 5, 6}, {7, 8, -1}};

    private ArrayList<StateNode> pastStates = new ArrayList<>();

    private ArrayList<StateNode> availableStates = new ArrayList<>();

    public AStar(int[][] start) {
        StateNode startNode = new StateNode(start);
        startNode.setgScore(0);
        startNode.sethScore();
        startNode.setfScore();
        startNode.setParentIndex(-1);
        pastStates.add(startNode);
        availableStates.add(startNode);
    }

    public ArrayList<StateNode> findiSolution() throws IOException {
        StateNode present;
        while (!availableStates.isEmpty()){
            availableStates.sort(new Comparator<StateNode>() {
                @Override
                public int compare(StateNode o1, StateNode o2) {
                    return Integer.compare(o1.getfScore(),o2.getfScore());
                }
            });
            present=availableStates.get(0);
            if (checkFinalState(present)){
                ArrayList<StateNode> solution = new ArrayList<>();
                while (present.getParentIndex()!=-1){
                    solution.add(present);
                    present=pastStates.get(present.getParentIndex());
                }
                solution.add(present);
                return solution;
            }
            ArrayList<StateNode> successors = expansion(present);
            pastStates.add(present);
            for (StateNode successor:successors){
                if (pastStates.contains(successor)){
                    successor.setgScore(present.getgScore());
                    successor.sethScore();
                    successor.setfScore();
                    successor.setParentIndex(pastStates.indexOf(present));
                    if (pastStates.get(pastStates.indexOf(successor)).getgScore()>successor.getgScore()){
                        pastStates.get(pastStates.indexOf(successor)).setgScore(successor.getgScore());
                        availableStates.add(successor);
                    }
                    availableStates.add(successor);
                } else {
                    successor.setgScore(present.getgScore());
                    successor.sethScore();
                    successor.setfScore();
                    successor.setParentIndex(pastStates.indexOf(present));
                    availableStates.add(successor);
                }
            }
            availableStates.remove(present);
        }
        return null;
    }

    private ArrayList<StateNode> expansion(StateNode present){
        ArrayList<StateNode> successors = new ArrayList<>();
        int[][] state = present.getState();
        int[][] successor = new int[3][3];
        int row=-1;
        int column=-1;
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                if (state[i][j]==-1){
                    row=i;
                    column=j;
                    break;
                }
            }
            if (row!=-1){
                break;
            }
        }
        if (row+1<3){
            for (int i=0;i<3;i++){
                successor[i]=state[i].clone();
            }
            successor[row][column]=state[row+1][column];
            successor[row+1][column]=state[row][column];
            StateNode stateNode = new StateNode(successor);
            successors.add(stateNode);
        }
        if (row-1>-1){
            for (int i=0;i<3;i++){
                successor[i]=state[i].clone();
            }
            successor[row][column]=state[row-1][column];
            successor[row-1][column]=state[row][column];
            StateNode stateNode = new StateNode(successor);
            successors.add(stateNode);
        }
        if (column+1<3){
            for (int i=0;i<3;i++){
                successor[i]=state[i].clone();
            }
            successor[row][column]=state[row][column+1];
            successor[row][column+1]=state[row][column];
            StateNode stateNode = new StateNode(successor);
            successors.add(stateNode);
        }
        if (column-1>-1){
            for (int i=0;i<3;i++){
                successor[i]=state[i].clone();
            }
            successor[row][column]=state[row][column-1];
            successor[row][column-1]=state[row][column];
            StateNode stateNode = new StateNode(successor);
            successors.add(stateNode);
        }
        return successors;
    }

    private boolean checkFinalState(StateNode n){
        int[][] end = {{1, 2, 3}, {4, 5, 6}, {7, 8, -1}};
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                if (end[i][j]!=n.getState()[i][j]){
                    return false;
                }
            }
        }
        return true;
    }


    public class StateNode {
        private int gScore;

        private int hScore =8;

        private int fScore;

        private int[][] state=new int[3][3];

        private int parentIndex;

        public StateNode(int[][] state){
            setState(state);
        }

        public int getgScore() {
            return gScore;
        }

        public void setgScore(int gScore) {
            this.gScore = gScore+1;
//            TODO: Jakoś zapisywać głebokość
        }

        public int gethScore() {
            return hScore;
        }

        public void sethScore() {
            for (int i=0;i<3;i++){
                for (int j=0;j<3;j++){
                    if (state[i][j] == end[i][j] && state[i][j]!=-1) {
                        hScore--;
                    }
                }
            }
        }

        public int getfScore() {
            return fScore;
        }

        public void setfScore() {
            this.fScore = hScore + gScore;
        }

        public int[][] getState() {
            return state;
        }

        public void setState(int[][] state) {
            for (int i=0;i<3;i++){
                    this.state[i]=state[i].clone();
            }
        }

        public int getParentIndex() {
            return parentIndex;
        }

        public void setParentIndex(int parentIndex) {
            this.parentIndex = parentIndex;
        }
    }
}
