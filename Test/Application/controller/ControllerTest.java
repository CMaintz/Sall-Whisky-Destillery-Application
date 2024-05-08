package Application.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.models.Fad;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void createFad() {
        Fad fad = new Fad(5, 10);
        assertTrue(fad.getFadId().equals("1"));
        assertTrue(fad.getAlder() == 10);
        assertTrue(fad.getPåFyldning() == null);
    }
}