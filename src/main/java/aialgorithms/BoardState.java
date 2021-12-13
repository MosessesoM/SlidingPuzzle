package aialgorithms;

public class BoardState {
    private int gScore;

    private int hScore = 8;

    private int fScore;

    private int[][] state = new int[3][3];

    private int parentIndex;

    private int fitnesFunction;

    public BoardState(int[][] state) {
        setState(state);
    }

    public int getgScore() {
        return gScore;
    }

    public void setgScore(int gScore) {
        this.gScore = gScore + 1;
    }

    public int gethScore() {
        return hScore;
    }

    public void sethScore() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == Algorithm.end[i][j] && state[i][j] != -1) {
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
        for (int i = 0; i < 3; i++) {
            this.state[i] = state[i].clone();
        }
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public int getFitnesFunction() {
        return fitnesFunction;
    }

    public void setFinesFunction() {
        int index=1;
        fitnesFunction =0;
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                if (state[i][j]==-1){
                    fitnesFunction +=Math.abs(9-index);
                } else {
                    fitnesFunction +=Math.abs(state[i][j]-index);
                }
                index++;
            }
        }
    }
}
