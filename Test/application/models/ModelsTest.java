// CombinedTest.java
import application.controller.Controller;
import application.models.*;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ModelsTest {

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
        assertEquals(50, påfyldning.getLiterPåfyldt());
        assertEquals(50, destillering.getCurrentAntalLiter());
        Controller.createPåfyldning("Test Medarbejder", 150, destillering);

    }

    @Test
    void testCreatePåfyldningWithError() {
        Destillering destillering = Controller.createDestillering("Batch 1", Controller.createKorn("Corn", "Yellow", "Brand X"), "Test Medarbejder", 100, 40, "Oak", "Test Kommentar");
        assertThrows(IllegalArgumentException.class, () -> {
            Controller.createPåfyldning("Test Medarbejder", 150, destillering);
        });
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
    void testCreateReol() {
        Lager lager = new Lager("Lager1");
        lager.createReol(1, 10);
        assertTrue(lager.getReoler().size() == 1);
        assertTrue(lager.getReoler().get(0).getHylder().length == 10);
    }
}