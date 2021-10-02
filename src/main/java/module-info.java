module SlidingPuzzle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.fasterxml.classmate;
    requires java.persistence;
    opens controllers to javafx.fxml;
    exports controllers;
    opens database to org.hibernate.orm.core;
}