package controllers;

abstract class Controller {
    MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
