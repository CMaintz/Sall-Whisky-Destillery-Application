// CombinedTest.java
import application.controller.Controller;
import application.models.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ModelsTest {

    @Test
    void testCreateFad() {
        Fad fad = Controller.createFad(50, "Cherry", "Spanien", LocalDate.of(2004, 1, 1), "LeveretAfJens");
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
        Destillat destillat = Controller.createDestilat("TestDestillat");
        Destillering destillering = Controller.createDestillering("Batch 1", Controller.createKorn("Corn", "Yellow", "Brand X"), "Test Medarbejder", 100, 40, "Oak", "Test Kommentar");
        Påfyldning påfyldning = Controller.createPåfyldning("Test Medarbejder", 50, destillering, destillat);
//        assertTrue(Storage.getPåfyldninger().contains(påfyldning));
        assertEquals(50, påfyldning.getLiterPåfyldt());
        assertEquals(50, destillering.getCurrentAntalLiter());
        Controller.createPåfyldning("Test Medarbejder", 150, destillering, destillat);

    }

    @Test
    @Disabled
    void testCreatePåfyldningWithError() {
        Destillering destillering = Controller.createDestillering("Batch 1", Controller.createKorn("Corn", "Yellow", "Brand X"), "Test Medarbejder", 100, 40, "Oak", "Test Kommentar");
        assertThrows(IllegalArgumentException.class, () -> {
//            Controller.createPåfyldning("Test Medarbejder", 150, destillering);
//            TODO wtf is dis? ^ Det er kun hvis man selv har indsat et throw man skal teste throws.
        });
    }

    @Test
    void testCreateDestillat() {
//        TODO ryd op i det her lårt
//        Destillering destillering = Controller.createDestillering("Batch 1", Controller.createKorn("Corn", "Yellow", "Brand X"), "Test Medarbejder", 100, 40, "Oak", "Test Kommentar");
        Destillat destillat = Controller.createDestilat("Test Destillat");
        Fad fad = new Fad(200);
        fad.addDestillat(destillat);
        assertTrue(fad.getDestillat().equals(destillat));
//        Påfyldning påfyldning = Controller.createPåfyldning("Test Medarbejder", 50, destillering, destillat);
//        assertTrue(destillat.getPåfyldninger().contains(påfyldning));
//        assertTrue(påfyldning.getDestillering().equals(destillering));
    }

    @Test
    void testCreateReol() {
        Lager lager = new Lager("Lager1");
        lager.createReol(10);
        assertTrue(lager.getReoler().size() == 1);
        assertTrue(lager.getReoler().get(0).getHylder().length == 10);
    }
}