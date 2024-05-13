package application.controller;

import application.models.*;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class ControllerTest {

    @Test
    void testCreateFad() {
        FadHistorik fadHistorik = new FadHistorik("Cherry", "Spanien", 2004, 2019, "LeveretAfJens");
        Fad fad = Controller.createFad(50, 3, fadHistorik);
        assertTrue(Storage.getFade().contains(fad));
    }

    @Test
    void testCreateKorn() {
        Korn korn = Controller.createKorn("Corn", "Yellow", "Brand X");
        assertTrue(Storage.getKorn().contains(korn));
    }

    @Test
    void testCreateDestillering() {
        Korn korn = Controller.createKorn("Corn", "Yellow", "Brand X");
        Destillering destillering = Controller.createDestillering("Batch 1", korn, "Test Medarbejder", 100, 40, "Oak", "Test Kommentar");
        assertTrue(Storage.getDestillering().contains(destillering));
    }

    @Test
    void testCreatePåfyldning() {
        Destillering destillering = Controller.createDestillering("Batch 1", Controller.createKorn("Corn", "Yellow", "Brand X"), "Test Medarbejder", 100, 40, "Oak", "Test Kommentar");
        Påfyldning påfyldning = Controller.createPåfyldning("Test Medarbejder", 50, destillering);
        assertTrue(Storage.getPåfyldninger().contains(påfyldning));
    }

    @Test
    void testCreateDestillat() {
        Destillering destillering = Controller.createDestillering("Batch 1", Controller.createKorn("Corn", "Yellow", "Brand X"), "Test Medarbejder", 100, 40, "Oak", "Test Kommentar");
        Påfyldning påfyldning = Controller.createPåfyldning("Test Medarbejder", 50, destillering);
        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        påfyldninger.add(påfyldning);
        Destillat destillat = Controller.createDestilat(påfyldninger, "Test Destillat");
        assertTrue(Storage.getDestillater().contains(destillat));
    }

    @Test
    void testCreateLager() {
        Lager lager = new Lager("Lager1");
        assertTrue(Storage.getLager().contains(lager));
    }
}