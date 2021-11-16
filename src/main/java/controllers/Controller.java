package controllers;

import database.User;

abstract class Controller {
    MainController mainController;

    User user = (User) Main.getStage().getUserData();

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
