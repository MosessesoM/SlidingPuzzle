package aialgorithms;

public class BoardState {
    private int gScore;

    private int hScore = 9;

    private int fScore;

    private int[][] state = new int[3][3];

    private int parentIndex;

    private int fitnessFunction =0;

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
        int index=1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == index || (state[i][j]==-1 && index==9)) {
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

    public void setFitnessFunction(Integer parentFitnessScore) {
        int index=1;
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                if (state[i][j]==-1){
                    fitnessFunction +=Math.abs(9-index);
                } else {
                    fitnessFunction +=Math.abs(state[i][j]-index);
                }
                index++;
            }
        }
        fitnessFunction+=parentFitnessScore;
    }
}
