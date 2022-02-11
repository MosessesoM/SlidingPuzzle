package controllers;

import java.io.IOException;

interface GameController {
    int getScore();

    void randomStart(int moves);

    void win(String playerName) throws IOException;
}
