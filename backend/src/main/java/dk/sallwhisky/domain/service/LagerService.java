package dk.sallwhisky.domain.service;

import dk.sallwhisky.api.dto.request.OpretLagerRequest;
import dk.sallwhisky.api.dto.request.OpretReolRequest;
import dk.sallwhisky.api.dto.response.LagerResponse;
import jakarta.persistence.EntityNotFoundException;
import dk.sallwhisky.domain.entity.Hylde;
import dk.sallwhisky.domain.entity.Lager;
import dk.sallwhisky.domain.entity.Reol;
import dk.sallwhisky.domain.repository.LagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LagerService {

    private final LagerRepository lagerRepository;

    public LagerResponse tilfoejReoler(java.util.UUID lagerId, OpretReolRequest req) {
        Lager lager = lagerRepository.findById(lagerId)
                .orElseThrow(() -> new EntityNotFoundException("Lager not found: " + lagerId));
        for (int i = 0; i < req.antalReoler(); i++) {
            Reol reol = lager.addReol();
            for (int j = 0; j < req.hylderPerReol(); j++) {
                reol.addHylde();
            }
        }
        return toResponse(lagerRepository.save(lager));
    }

    public LagerResponse opretLager(OpretLagerRequest req) {
        Lager lager = new Lager(req.navn());

        for (int i = 0; i < req.antalReoler(); i++) {
            Reol reol = lager.addReol();
            for (int j = 0; j < req.hylderPerReol(); j++) {
                reol.addHylde();
            }
        }

        return toResponse(lagerRepository.save(lager));
    }

    @Transactional(readOnly = true)
    public List<LagerResponse> getAlleLagre() {
        return lagerRepository.findAll().stream().map(this::toResponse).toList();
    }

    public LagerResponse toResponse(Lager lager) {
        List<LagerResponse.ReolResponse> reoler = lager.getReoler().stream()
                .map(reol -> new LagerResponse.ReolResponse(
                        reol.getId(),
                        reol.getReolNummer(),
                        reol.getHylder().stream()
                                .map(hylde -> new LagerResponse.HyldeResponse(
                                        hylde.getId(),
                                        hylde.getPosition(),
                                        hylde.erLedig(),
                                        hylde.getFad() == null ? null : fadSummary(hylde)
                                ))
                                .toList()
                ))
                .toList();

        return new LagerResponse(lager.getId(), lager.getNavn(), reoler);
    }

    private LagerResponse.FadSummary fadSummary(Hylde hylde) {
        var fad = hylde.getFad();
        return new LagerResponse.FadSummary(
                fad.getId(),
                fad.getFadNummer(),
                fad.getLiterKapacitet(),
                fad.getTidligereIndhold(),
                fad.harDestillat(),
                fad.erKlar()
        );
    }
}
