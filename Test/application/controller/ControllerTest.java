package application.controller;

import application.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class ControllerTest {

    @Test
    void testCreateFad() {
        Fad fad = Controller.createFad(50, 3);
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
}