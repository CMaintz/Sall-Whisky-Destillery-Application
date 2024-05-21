// CombinedTest.java
import application.controller.Controller;
import application.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import storage.Storage;

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
        fad.createFadHistorik("Sherry", "Spanien", LocalDate.of(2020,10,10), "TestLeverandør");
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
        destillat.addDestillatHistorik(testFad);
        assertTrue(destillat.getFad().equals(testFad));
        assertTrue(destillat.getDestillatHistorik().get(0).getFad().equals(fad));
        Fad testFad2 = new Fad(60);
        destillat.addDestillatHistorik(testFad2);
        assertTrue(destillat.getFad().equals(testFad2));
        assertTrue(destillat.getDestillatHistorik().get(1).getFad().equals(testFad));
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
        destillat.setPåfyldningsDato(LocalDate.of(2022, 10, 10));
        assertFalse(destillat.destillatKlar());
        Destillat destillatTest = new Destillat("Test");
        destillat.setPåfyldningsDato(LocalDate.of(2020, 10, 10));
        assertTrue(destillat.destillatKlar());
    }

    @Test
    void testSetters() {
        Destillat destillat = new Destillat("Test Destillat");

        destillat.setPåfyldningsDato(LocalDate.of(2022, 5, 15));
        assertEquals(LocalDate.of(2022, 5, 15), destillat.getPåfyldningsDato());

        Fad fad = new Fad(50);
        destillat.setFad(fad);
        assertEquals(fad, destillat.getFad());

        destillat.fjernAntalLiter(5.0);
        assertEquals(-5.0, destillat.getAntalLiter());
    }

    @Test
    void testGetters() {
        Påfyldning påfyldning = destillat.createPåfyldning("Jens", 30, destillering);
        assertEquals(50, destillat.getAlkoholprocent());
        assertEquals(30, destillat.getAntalLiter());
        assertTrue(destillat.getFad().equals(fad));
        assertTrue(destillat.getPåfyldninger().contains(påfyldning));
        assertTrue(destillat.getPåfyldningsDato().equals(LocalDate.now()));
        Fad fad = new Fad(40);
        destillat.addDestillatHistorik(fad);
        assertTrue(destillat.getDestillatHistorik().get(0).getFad().equals(this.fad));
        assertTrue(destillat.getNavn().equals("Destillat1"));
    }

    @Test
    void testFadKlasse() {
        Fad fadTest = new Fad(100);
        FadHistorik fadHistorik = fadTest.createFadHistorik("Sherry", "Spanien", LocalDate.of(2020, 10, 10), "TestLeverandør");
        assertTrue(fadTest.getFadHistorik().equals(fadHistorik));

        fadTest.addDestillat(destillat);
        assertTrue(fadTest.getDestillat().equals(destillat));
        Destillat destillatTest = new Destillat("Test");
        fadTest.addDestillat(destillatTest);
        assertTrue(fadTest.getDestillat().equals(destillatTest));
        assertTrue(fadTest.getFadHistorik().getTidligereDestillater().contains(destillat));
    }

    @Test
    void whiskyProduktWhiskyTypeTest() {
        fad.addDestillat(destillat);
        destillat.createPåfyldning("Jens", 30, destillering);
        WhiskyProdukt whiskyProdukt = new WhiskyProdukt("TestWhiskyProdukt", "Test");
        whiskyProdukt.createFadTapning("Jens", 30, fad);
        assertEquals("Cask Strength", whiskyProdukt.whiskyType());

        whiskyProdukt.tilføjVand(20);
        assertEquals("Single Cask", whiskyProdukt.whiskyType());

        whiskyProdukt.createFadTapning("Bob", 20, fad);
        assertEquals("Single Malt", whiskyProdukt.whiskyType());
    }
}