import application.controller.Controller;
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
        korn = new Korn("TestSort", "TestVariant", "Mark1");
        destillering = new Destillering("TestMaltBatch", korn, "Medarbejder1", 40, 50, "Røg1", "Smager godt");
        fad = new Fad(100);
        fad.createFadHistorik("Sherry", "Spanien", LocalDate.of(2020, 10, 10), "TestLeverandør");
        destillat = new Destillat();
        destillat.createPåfyldning("Jens", 20, destillering);
        fad.addDestillat(destillat);
    }

    @Test
    void fadAddDestillatTest() {
        fad.addDestillat(destillat);
        assertEquals(fad.getDestillat(), destillat);
        Destillat destillatTest = new Destillat();
        destillatTest.createPåfyldning("Jens", 20, destillering);
        fad.addDestillat(destillatTest);
        assertEquals(fad.getDestillat(), destillatTest);
        assertEquals(destillat, fad.getFadHistorik().getTidligereDestillater().get(0));
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
    void destillatAddModningTest() {
        assertTrue(destillat.getModningsHistorik().get(0).getFad().equals(fad));
        Fad testFad = new Fad(80);
        testFad.createFadHistorik("Test", "Spanien", LocalDate.of(2010, 10, 10), "LeverandørTest");
        destillat.omhældDestillat(testFad);
        assertTrue(destillat.getModningsHistorik().get(1).getFad().equals(testFad));
        assertTrue(destillat.getFad().equals(testFad));
        Fad testFad2 = new Fad(60);
        testFad2.createFadHistorik("Test2", "Danmark", LocalDate.of(2008, 10, 10), "LeverandørTest2");
        destillat.omhældDestillat(testFad2);
        assertTrue(destillat.getFad().equals(testFad2));

        assertEquals(3, destillat.getModningsHistorik().size());
        assertTrue(destillat.getModningsHistorik().get(2).getFad().equals(testFad2));

    }

    @Test
    void destillatSetAlkoholProcentTest() {
        Destillering destillering1 = new Destillering("MaltbatchTest2", korn, "Jens", 120, 60,
                "RøgTest", "Smager godt");
        destillat.createPåfyldning("Bob", 20, destillering1);
        assertEquals(60, destillat.getAlkoholprocent());
        destillat.createPåfyldning("Jens", 30, destillering);
        assertEquals(54, destillat.getAlkoholprocent());
    }

    @Test
    void destillatKlarTest() {
        assertFalse(destillat.destillatKlar());
        Destillat destillatTest = new Destillat();
        destillat.getModningsHistorik().get(0).setPåfyldningsDato(LocalDate.of(2020, 10, 10));
        assertTrue(destillat.destillatKlar());
    }

    @Test
    void testSetters() {
        Destillat destillat = new Destillat();

        fad.addDestillat(destillat);
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
        destillat.getPåfyldninger();
        destillat.getPåfyldningsDato();
        destillat.getModningsHistorik();
        assertTrue(destillat.getPåfyldninger().contains(påfyldning));
        assertTrue(destillat.getPåfyldningsDato().equals(LocalDate.now()));
        Fad fad = new Fad(40);
        fad.createFadHistorik("test", "test", LocalDate.now(), "test");
        fad.addDestillat(destillat);
        assertTrue(destillat.getModningsHistorik().get(0).getFad().equals(this.fad));
    }

    @Test
    void testFadKlasse() {
        Fad fadTest = new Fad(100);
        FadHistorik fadHistorik = fadTest.createFadHistorik("Sherry", "Spanien",
                LocalDate.of(2020, 10, 10), "TestLeverandør");
        assertTrue(fadTest.getFadHistorik().equals(fadHistorik));

        fadTest.addDestillat(destillat);
        assertTrue(fadTest.getDestillat().equals(destillat));
        Destillat destillatTest = new Destillat();
        fadTest.addDestillat(destillatTest);
        assertTrue(fadTest.getDestillat().equals(destillatTest));
        assertTrue(fadTest.getFadHistorik().getTidligereDestillater().contains(destillat));
    }

    @Test
    void whiskyProduktWhiskyTypeTest() {
        fad.addDestillat(destillat);
        destillat.createPåfyldning("Jens", 30, destillering);
        WhiskyProdukt whiskyProdukt = new WhiskyProdukt("TestWhiskyProdukt");
        whiskyProdukt.createFadTapning("Jens", 30, fad);
        assertEquals("Cask Strength", whiskyProdukt.whiskyType());

        whiskyProdukt.tilføjVand(20);
        assertEquals("Single Cask", whiskyProdukt.whiskyType());

        whiskyProdukt.createFadTapning("Bob", 20, fad);
        assertEquals("Single Malt", whiskyProdukt.whiskyType());
    }

}