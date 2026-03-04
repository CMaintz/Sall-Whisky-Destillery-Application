package dk.sallwhisky.config;

import dk.sallwhisky.api.dto.request.*;
import dk.sallwhisky.domain.entity.*;
import dk.sallwhisky.domain.repository.*;
import dk.sallwhisky.domain.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Seeds the in-memory H2 database with representative demo data on startup.
 * Mirrors the original App.initStorage() from the JavaFX version.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final KornRepository kornRepository;
    private final FadRepository fadRepository;
    private final DestilleringRepository destilleringRepository;
    private final LagerService lagerService;
    private final DestilleringService destilleringService;
    private final FadService fadService;
    private final WhiskyService whiskyService;

    @Override
    @Transactional
    public void run(String... args) {
        if (fadRepository.count() > 0) return; // already initialized

        log.info("Seeding demo data...");

        // --- Grain types ---
        Korn korn1 = kornRepository.save(new Korn("Vårbyg", "Evergreen", "Highland og Stenhøj"));
        Korn korn2 = kornRepository.save(new Korn("Vårbyg", "Laureate", "Mosevang og Stadsgaard"));
        Korn korn3 = kornRepository.save(new Korn("Vårbyg", "Focus", "Dagmarlund og Skovsø"));

        // --- Distillation runs ---
        var dest1Resp = destilleringService.opretDestillering(new OpretDestilleringRequest(
                "Malthuset", korn1.getId(), "Chris", 1500, 81, "Bøgeflisrøget", ""));
        var dest2Resp = destilleringService.opretDestillering(new OpretDestilleringRequest(
                "Malthuset", korn2.getId(), "Chris", 800, 50, "", ""));
        var dest3Resp = destilleringService.opretDestillering(new OpretDestilleringRequest(
                "Malthuset", korn3.getId(), "Chris", 5000, 55, "Tørverøget", ""));

        // Set realistic start times so destillerings-tid is meaningful
        Destillering dest1 = destilleringRepository.findById(dest1Resp.id()).orElseThrow();
        Destillering dest2 = destilleringRepository.findById(dest2Resp.id()).orElseThrow();
        Destillering dest3 = destilleringRepository.findById(dest3Resp.id()).orElseThrow();
        dest1.setStartTidspunkt(LocalDateTime.now().minusHours(127));
        dest1.setSlutTidspunkt(LocalDateTime.now());
        dest2.setStartTidspunkt(LocalDateTime.now().minusHours(152));
        dest2.setSlutTidspunkt(LocalDateTime.now());
        dest3.setStartTidspunkt(LocalDateTime.now().minusHours(171));
        dest3.setSlutTidspunkt(LocalDateTime.now());
        destilleringRepository.saveAll(List.of(dest1, dest2, dest3));

        // --- Warehouses ---
        var lade = lagerService.opretLager(new OpretLagerRequest("Lars' lade", 3, 3));
        var container = lagerService.opretLager(new OpretLagerRequest("Baggårds container", 2, 3));

        // --- Barrels ---
        var fad1 = fadService.opretFad(new OpretFadRequest(40, "Sherry", "Spanien", LocalDate.of(2004, 1, 1), "Fadpusheren"));
        var fad2 = fadService.opretFad(new OpretFadRequest(30, "Sherry", "Spanien", LocalDate.of(2001, 1, 1), "Fadpusheren"));
        var fad3 = fadService.opretFad(new OpretFadRequest(20, "Rødvin", "Frankrig", LocalDate.of(2002, 1, 1), "Le leverandeur"));
        var fad4 = fadService.opretFad(new OpretFadRequest(20, "Bourbon", "USA", LocalDate.of(2012, 1, 1), "Yeehaw leverandør"));
        var fad5 = fadService.opretFad(new OpretFadRequest(20, "Bourbon", "USA", LocalDate.of(2012, 1, 1), "Yeehaw leverandør"));
        var fad6 = fadService.opretFad(new OpretFadRequest(20, "Rødvin", "Frankrig", LocalDate.of(2000, 1, 1), "Le leverandeur"));
        var fad7 = fadService.opretFad(new OpretFadRequest(50, "Rødvin", "Frankrig", LocalDate.of(2000, 1, 1), "Le leverandeur"));
        var fad8 = fadService.opretFad(new OpretFadRequest(50, "Rødvin", "Frankrig", LocalDate.of(1999, 1, 1), "Le leverandeur"));
        var fad9 = fadService.opretFad(new OpretFadRequest(50, "Sherry", "Spanien", LocalDate.of(2009, 1, 1), "Fadpusheren"));

        // --- Fill barrels (creates Destillat entities) ---
        fadService.paafyldFad(fad1.id(), new PaafyldFadRequest(List.of(
                new PaafyldFadRequest.PaafyldningItem(dest1Resp.id(), 20, "Maintz"),
                new PaafyldFadRequest.PaafyldningItem(dest2Resp.id(), 20, "Maintz"))));

        fadService.paafyldFad(fad2.id(), new PaafyldFadRequest(List.of(
                new PaafyldFadRequest.PaafyldningItem(dest1Resp.id(), 15, "Maintz"),
                new PaafyldFadRequest.PaafyldningItem(dest3Resp.id(), 15, "Maintz"))));

        fadService.paafyldFad(fad4.id(), new PaafyldFadRequest(List.of(
                new PaafyldFadRequest.PaafyldningItem(dest3Resp.id(), 20, "Maintz"))));

        fadService.paafyldFad(fad5.id(), new PaafyldFadRequest(List.of(
                new PaafyldFadRequest.PaafyldningItem(dest3Resp.id(), 20, "Maintz"))));

        fadService.paafyldFad(fad6.id(), new PaafyldFadRequest(List.of(
                new PaafyldFadRequest.PaafyldningItem(dest2Resp.id(), 20, "Chris"))));

        fadService.paafyldFad(fad7.id(), new PaafyldFadRequest(List.of(
                new PaafyldFadRequest.PaafyldningItem(dest2Resp.id(), 50, "Chris"))));

        // Backdate some destillater so they appear "ready" (3+ years old)
        backdateDestillat(fad1.id(), LocalDate.now().minusYears(4));
        backdateDestillat(fad2.id(), LocalDate.of(2020, 1, 1));
        backdateDestillat(fad4.id(), LocalDate.now().minusYears(3));
        backdateDestillat(fad5.id(), LocalDate.now().minusYears(5));
        backdateDestillat(fad6.id(), LocalDate.of(2019, 1, 1));

        // Backdate fad6 så det er klar
        backdateDestillat(fad6.id(), LocalDate.of(2019, 6, 1));

        // --- Extra ready barrels (untapped — visible as "Klar" in demo) ---
        // fad8: 50L Rødvin, high-ABV spirit from dest1 (81% ABV, bøgeflisrøget)
        fadService.paafyldFad(fad8.id(), new PaafyldFadRequest(List.of(
                new PaafyldFadRequest.PaafyldningItem(dest1Resp.id(), 40, "Maintz"))));
        backdateDestillat(fad8.id(), LocalDate.now().minusYears(4).minusMonths(3));

        // fad9: 50L Sherry, medium-ABV spirit from dest3 (55% ABV, tørverøget)
        fadService.paafyldFad(fad9.id(), new PaafyldFadRequest(List.of(
                new PaafyldFadRequest.PaafyldningItem(dest3Resp.id(), 40, "Chris"))));
        backdateDestillat(fad9.id(), LocalDate.of(2021, 3, 15));

        // --- Create a finished whisky product from the ready barrels ---
        var toerv = whiskyService.opretWhiskyProdukt(new OpretWhiskyProduktRequest("TØRV"));
        whiskyService.tapFad(toerv.id(), new TapFadRequest(fad4.id(), "Chris"));
        whiskyService.tapFad(toerv.id(), new TapFadRequest(fad5.id(), "Chris"));
        whiskyService.opretFlasker(toerv.id());

        // --- Whisky under modning — ikke flasket endnu, knapper aktive ---
        var muld = whiskyService.opretWhiskyProdukt(new OpretWhiskyProduktRequest("MULD"));
        whiskyService.tapFad(muld.id(), new TapFadRequest(fad1.id(), "Chris"));

        var singleMalt = whiskyService.opretWhiskyProdukt(new OpretWhiskyProduktRequest("MOSEDAL SINGLE MALT"));
        whiskyService.tapFad(singleMalt.id(), new TapFadRequest(fad2.id(), "Chris"));
        whiskyService.tapFad(singleMalt.id(), new TapFadRequest(fad6.id(), "Chris"));
        whiskyService.tilfoejVand(singleMalt.id(), 5.0);

        log.info("Demo data seeded successfully.");
    }

    private void backdateDestillat(java.util.UUID fadId, LocalDate newStartDato) {
        Fad fad = fadRepository.findById(fadId).orElseThrow();
        if (fad.getDestillat() != null) {
            Destillat d = fad.getDestillat();
            d.setStartDato(newStartDato);
            if (!d.getModningsHistorik().isEmpty()) {
                d.getModningsHistorik().get(0).setPaafyldningsDato(newStartDato);
            }
        }
    }
}
