package gui;

import application.controller.Controller;
import application.models.Destillering;
import application.models.Korn;
import javafx.application.Application;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        initContents();
        Application.launch(LoginPane.class);

    }

    private static void initContents() {
        Korn korn = Controller.createKorn("TestSort", "TestVariant", "TestMark");
        Controller.createDestillering("MaltTest1", korn, "Jens", 40, 40, "Røg1", "Test1");
        Controller.createDestillering("MaltTest2", korn, "Jens", 30, 42, "Røg2", "Test2");
        Controller.createDestillering("MaltTest3", korn, "Jens", 50, 45, "Røg3", "Test3");
        Controller.createDestillering("MaltTest4", korn, "Jens", 60, 48, "Røg4", "Test4");
        Controller.createFad(100, "Sherry", "Spanien", LocalDate.of(2012, 10, 10), "Test1");
        Controller.createFad(70, "Vin", "Frankrig", LocalDate.of(2013, 10, 10), "Test2");
        Controller.createFad(80, "Bombs", "Tyskland", LocalDate.of(2014, 10, 10), "Test3");

    }
}
