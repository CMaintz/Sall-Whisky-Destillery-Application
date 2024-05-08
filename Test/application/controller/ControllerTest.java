package application.controller;

import application.models.Destillering;
import application.models.Korn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.models.Fad;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Korn korn;
    @BeforeEach
    void setUp() {
        korn = new Korn("Byg", "Evergreen", "Mark1");
    }

    @Test
    void createFad() {
        Fad fad = new Fad(5, 10);
        assertTrue(fad.getFadId().equals("1"));
        assertTrue(fad.getAlder() == 10);
        assertTrue(fad.getPåFyldning() == null);
    }

    @Test
    void createDestillering() {
        Destillering destillering = new Destillering("Maltbach1", korn, "Sniper", 20, 40, "Papir", "Brygget godt");
        assertTrue(destillering.getAntalLiter() == 20);
        assertTrue(destillering.getMaltBatch().equals("Maltbach1"));
    }

    @Test
    void createKorn() {
        assertTrue(korn.getSort().equals("Byg"));
        assertTrue(korn.getMarkNavn().equals("Mark1"));
    }
}