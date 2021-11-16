package controllers;

import java.io.IOException;

interface GameController{
    public int getScore();

    public void randomStart(int moves);

    public void win() throws IOException;
}
