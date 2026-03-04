package dk.sallwhisky.domain.service;

import dk.sallwhisky.api.dto.request.OpretWhiskyProduktRequest;
import dk.sallwhisky.api.dto.request.TapFadRequest;
import dk.sallwhisky.api.dto.response.WhiskyProduktResponse;
import dk.sallwhisky.domain.entity.*;
import dk.sallwhisky.domain.repository.FadRepository;
import dk.sallwhisky.domain.repository.WhiskyProduktRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class WhiskyService {

    private final WhiskyProduktRepository whiskyProduktRepository;
    private final FadRepository fadRepository;

    public WhiskyProduktResponse opretWhiskyProdukt(OpretWhiskyProduktRequest req) {
        return toResponse(whiskyProduktRepository.save(new WhiskyProdukt(req.navn())));
    }

    /**
     * Taps a barrel into a whisky product.
     * - Closes the barrel's maturation history
     * - Transfers all liquid from the barrel to the product
     * - Empties the barrel (fad.destillat = null)
     * - Removes the barrel from its shelf
     */
    public WhiskyProduktResponse tapFad(UUID whiskyId, TapFadRequest req) {
        WhiskyProdukt whiskyProdukt = whiskyProduktRepository.findById(whiskyId)
                .orElseThrow(() -> new EntityNotFoundException("WhiskyProdukt not found: " + whiskyId));

        Fad fad = fadRepository.findById(req.fadId())
                .orElseThrow(() -> new EntityNotFoundException("Fad not found: " + req.fadId()));

        if (!fad.harDestillat()) {
            throw new IllegalStateException("Barrel " + fad.getFadNummer() + " is empty");
        }

        Destillat destillat = fad.getDestillat();
        if (!destillat.erKlar()) {
            throw new IllegalStateException(
                "Destillat in barrel " + fad.getFadNummer() + " has not matured for 3 years yet " +
                "(filled: " + destillat.getStartDato() + ")"
            );
        }

        destillat.getModningsHistorik().stream()
                .filter(h -> h.getSlutDato() == null)
                .forEach(h -> h.setSlutDato(LocalDate.now()));

        FadTapning tapning = new FadTapning(
                req.medarbejder(),
                destillat.getAntalLiter(),
                fad,
                destillat,
                whiskyProdukt
        );
        whiskyProdukt.getFadTapninger().add(tapning);
        whiskyProdukt.setAntalLiter(whiskyProdukt.getAntalLiter() + destillat.getAntalLiter());
        whiskyProdukt.udregnAlkoholProcent();

        fad.setDestillat(null);
        if (fad.getHylde() != null) {
            fad.getHylde().setFad(null);
        }
        fadRepository.save(fad);

        return toResponse(whiskyProduktRepository.save(whiskyProdukt));
    }

    /**
     * Adds water to dilute the whisky to a target ABV.
     * Dropping below cask strength is what separates Single Cask from Cask Strength.
     */
    public WhiskyProduktResponse tilfoejVand(UUID whiskyId, double liter) {
        WhiskyProdukt whiskyProdukt = whiskyProduktRepository.findById(whiskyId)
                .orElseThrow(() -> new EntityNotFoundException("WhiskyProdukt not found: " + whiskyId));

        whiskyProdukt.tilfoejVand(liter);
        return toResponse(whiskyProduktRepository.save(whiskyProdukt));
    }

    /**
     * Bottles the product - creates one WhiskyFlaske per litre.
     * Resets antalLiter to 0 after bottling (the liquid is now in bottles).
     */
    public WhiskyProduktResponse opretFlasker(UUID whiskyId) {
        WhiskyProdukt whiskyProdukt = whiskyProduktRepository.findById(whiskyId)
                .orElseThrow(() -> new EntityNotFoundException("WhiskyProdukt not found: " + whiskyId));

        int antalFlasker = (int) whiskyProdukt.getAntalLiter();
        int startNummer = whiskyProdukt.getFlasker().size() + 1;

        for (int i = 0; i < antalFlasker; i++) {
            WhiskyFlaske flaske = new WhiskyFlaske(
                    startNummer + i,
                    whiskyProdukt.getNavn() + " – flaske " + (startNummer + i) + " af " + (startNummer + antalFlasker - 1),
                    whiskyProdukt
            );
            whiskyProdukt.getFlasker().add(flaske);
        }

        whiskyProdukt.setAntalLiter(0);
        return toResponse(whiskyProduktRepository.save(whiskyProdukt));
    }

    @Transactional(readOnly = true)
    public List<WhiskyProduktResponse> getAlleWhiskyProdukter() {
        return whiskyProduktRepository.findAll().stream().map(this::toResponse).toList();
    }

    public WhiskyProduktResponse toResponse(WhiskyProdukt wp) {
        List<WhiskyProduktResponse.FadTapningSummary> tapninger = wp.getFadTapninger().stream()
                .map(ft -> new WhiskyProduktResponse.FadTapningSummary(
                        ft.getFad() != null ? ft.getFad().getId() : null,
                        ft.getFad() != null ? ft.getFad().getFadNummer() : null,
                        ft.getFad() != null ? ft.getFad().getTidligereIndhold() : null,
                        ft.getLiterTappet(),
                        ft.getTapningsDato()
                ))
                .toList();

        List<WhiskyProduktResponse.FlaskeSummary> flasker = wp.getFlasker().stream()
                .map(f -> new WhiskyProduktResponse.FlaskeSummary(
                        f.getId(), f.getFlaskeNummer(), f.getProduktHistorie(), f.getFlaskningsDato()
                ))
                .toList();

        return new WhiskyProduktResponse(
                wp.getId(),
                wp.getNavn(),
                wp.getAlkoholProcent(),
                wp.getAntalLiter(),
                wp.getLiterVandTilfojet(),
                wp.whiskyType(),
                wp.getFlasker().size(),
                tapninger,
                flasker
        );
    }
}
