package aialgorithms;

import java.io.IOException;
import java.util.ArrayList;

public interface Algorithm {

    int[][] end = {{1, 2, 3}, {4, 5, 6}, {7, 8, -1}};

    ArrayList<BoardState> findSolution() throws IOException;

    ArrayList<BoardState> expansion(BoardState present);

    boolean checkFinalState(BoardState n);
}
