// CombinedTest.java
import application.models.*;
import javafx.beans.binding.When;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        fad.createFadHistorik("Sherry", "Spanien", LocalDate.of(2020,10,10), LocalDate.of(2024, 1, 1), "TestLeverandør");
        destillat = new Destillat();
        fad.addDestillat(destillat);
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
    void destillatAddModningTest() {
        Fad testFad = new Fad(80);
        destillat.omhældDestillat(testFad);
        assertTrue(destillat.getFad().equals(testFad));
        assertTrue(destillat.getModningsHistorik().get(0).getFad().equals(fad));
        Fad testFad2 = new Fad(60);
        destillat.omhældDestillat(testFad2);
        assertTrue(destillat.getFad().equals(testFad2));

        assertTrue(destillat.getModningsHistorik().get(2).getFad().equals(testFad2));

        assertEquals(3, destillat.getModningsHistorik().size());
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
        Destillat destillatTest = new Destillat();
        destillat.getModningsHistorik().get(0).setStartDato(LocalDate.of(2020, 10, 10));
        assertTrue(destillat.destillatKlar());
    }

    @Test
    void testSetters() {
        Destillat destillat = new Destillat();

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
        destillat.getPåfyldninger();
        destillat.getPåfyldningsDato();
        destillat.getModningsHistorik();
        assertTrue(destillat.getPåfyldninger().contains(påfyldning));
        assertTrue(destillat.getPåfyldningsDato().equals(LocalDate.now()));
        Fad fad = new Fad(40);
        fad.createFadHistorik("test", "test", LocalDate.now(), LocalDate.now(), "test");
        fad.addDestillat(destillat);
        assertTrue(destillat.getModningsHistorik().get(0).getFad().equals(this.fad));
    }

    @Test
    void testFadKlasse() {
        Fad fadTest = new Fad(100);
        FadHistorik fadHistorik = fadTest.createFadHistorik("Sherry", "Spanien", LocalDate.of(2020, 10, 10), LocalDate.of(2024, 1, 1), "TestLeverandør");
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
    @Test
    void testGenererHistorie() {
        WhiskyProdukt whiskyProdukt = new WhiskyProdukt("Sample Whisky");

        Fad fad = new Fad(40);
        fad.createFadHistorik("Sherry", "Spanien", LocalDate.of(2010,10,10), LocalDate.of(2020, 10, 10), "LeverandørTest");
        Destillat destillat1 = new Destillat();
        destillat1.createPåfyldning("Jens", 20, destillering);

        fad.addDestillat(destillat1);
        FadTapning fadTapning = whiskyProdukt.createFadTapning("Sample Medarbejder", 10, fad);

        String generatedHistory = whiskyProdukt.genererHistorie();

        String expectedHistory = "\nSkabt af egne hænder med Lars' økologiske Variant Sort, "
                + "\nSået og høstet fra den jyske muld på Lars' marker Mark, "
                + "\nMæsket ved håndkraft og fermenteret i 48"
                + "\nModnet i 0 år i omhyggeligt udvalgte ex-Sample Fad barrels."
                + "\nØkologisk Single Malt\n& Single Farm Whisky\n 100cl. 50% Vol.";

        assertEquals(expectedHistory, generatedHistory);
    }
}