package aialgorithms;

public class BoardState {
    private int gScore;

    private int hScore = 9;

    private int fScore;

    private int[][] state = new int[3][3];

    private int[][] end = {{1, 2, 3}, {4, 5, 6}, {7, 8, -1}};

    private int parentIndex;

    private int fitnessFunction = 0;

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
        int index = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == index || (state[i][j] == -1 && index == 9)) {
                    hScore--;
                }
                index++;
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

    public int getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(Integer cloneWeight) {
        fitnessFunction+=cloneWeight;
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                for (int k=0;k<3;k++){
                    for (int l=0;l<3;l++){
                        if (state[i][j]==end[k][l]){
                            fitnessFunction+=Math.abs(i-k)+Math.abs(j-l);
                        }
                    }
                }
            }
        }
    }
}
