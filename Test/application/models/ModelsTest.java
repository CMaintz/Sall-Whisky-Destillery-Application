// CombinedTest.java
import application.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ModelsTest {
    private ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
    private Destillering destillering;
    private Korn korn;
    private Destillat destillat;
    private Fad fad;

    @BeforeEach
    void setUp() {
        fad = new Fad(100);
        destillat = new Destillat("Destillat1");
        destillat.setFad(fad);
        korn = new Korn("TestSort", "TestVariant", "Mark1");
        destillering = new Destillering("TestMaltBatch", korn, "Medarbejder1", 40, 50, "Røg1", "Smager godt");
    }
    @Test
    void destillatConstructorTest() {
    }

    @Test
    void destillatCreatePåfyldningTest() {
        Påfyldning påfyldning = destillat.createPåfyldning("Jens", 20, destillering);
        assertTrue(destillat.getPåfyldninger().contains(påfyldning));
        Påfyldning påfyldning2 = destillat.createPåfyldning("Børge", 15, destillering);
        assertTrue(destillat.getPåfyldninger().contains(påfyldning2));
        assertEquals(35, destillat.getAntalLiter());
    }

    @Test
    void destillatAddFadHistorikTest() {
        Fad testFad = new Fad(80);
//        TODO
//        destillat.createModningsHistorik(testFad);
        assertTrue(destillat.getFad().equals(testFad));
        assertTrue(destillat.getModningsHistorik().get(0).getFad().equals(fad));
    }

    @Test
    void destillatSetAlkoholProcentTest() {
        Destillering destillering1 = new Destillering("MaltbatchTest2", korn, "Jens", 120, 60, "RøgTest", "Smager godt");
        destillat.createPåfyldning("Bob", 20, destillering1);
        assertEquals(60, destillat.getAlkoholprocent());
        destillat.createPåfyldning("Jens", 30, destillering);
        assertEquals(54, destillat.getAlkoholprocent());
    }

    @Test
    void destillatKlarTest() {
        assertFalse(destillat.destillatKlar());
        Destillat destillatTest = new Destillat("Test");
//        TODO
//        destillat.setPåfyldningsDato(LocalDate.of(2020, 10, 10));
        assertTrue(destillat.destillatKlar());
    }

    @Test
    void testSetters() {
        Destillat destillat = new Destillat("Test Destillat");

//        TODO
//        destillat.setPåfyldningsDato(LocalDate.of(2022, 5, 15));
        assertEquals(LocalDate.of(2022, 5, 15), destillat.getPåfyldningsDato());

        Fad fad = new Fad(50);
        destillat.setFad(fad);
        assertEquals(fad, destillat.getFad());

        destillat.fjernAntalLiter(5.0);
        assertEquals(-5.0, destillat.getAntalLiter());
    }

    @Test
    void testGetters() {
        destillat.createPåfyldning("Jens", 30, destillering);
        assertEquals(50, destillat.getAlkoholprocent());
        assertEquals(30, destillat.getAntalLiter());
        assertTrue(destillat.getFad().equals(fad));
        destillat.getPåfyldninger();
        destillat.getPåfyldningsDato();
        destillat.getModningsHistorik();
        destillat.getNavn();
    }
}