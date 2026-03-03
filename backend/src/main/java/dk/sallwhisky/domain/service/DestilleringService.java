package dk.sallwhisky.domain.service;

import dk.sallwhisky.api.dto.request.OpretDestilleringRequest;
import dk.sallwhisky.api.dto.response.DestilleringResponse;
import dk.sallwhisky.domain.entity.Destillering;
import dk.sallwhisky.domain.entity.Korn;
import dk.sallwhisky.domain.repository.DestilleringRepository;
import dk.sallwhisky.domain.repository.KornRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DestilleringService {

    private final DestilleringRepository destilleringRepository;
    private final KornRepository kornRepository;

    public DestilleringResponse opretDestillering(OpretDestilleringRequest req) {
        Korn korn = kornRepository.findById(req.kornId())
                .orElseThrow(() -> new EntityNotFoundException("Korn not found: " + req.kornId()));

        int batchNummer = destilleringRepository.countBy() + 1;

        Destillering destillering = new Destillering(
                batchNummer,
                req.maltBatch(),
                korn,
                req.medarbejder(),
                req.antalLiter(),
                req.alkoholProcent(),
                req.rygemateriale(),
                req.kommentar()
        );

        return toResponse(destilleringRepository.save(destillering));
    }

    @Transactional(readOnly = true)
    public List<DestilleringResponse> getAlleDestilleringer() {
        return destilleringRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public DestilleringResponse getDestilleringById(UUID id) {
        return destilleringRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Destillering not found: " + id));
    }

    public DestilleringResponse toResponse(Destillering d) {
        DestilleringResponse.KornSummary kornSummary = new DestilleringResponse.KornSummary(
                d.getKorn().getId(),
                d.getKorn().getSort(),
                d.getKorn().getVariant(),
                d.getKorn().getMarkNavne()
        );
        return new DestilleringResponse(
                d.getId(),
                d.getBatchNummer(),
                d.getMaltBatch(),
                kornSummary,
                d.getMedarbejder(),
                d.getAntalLiter(),
                d.getAlkoholProcent(),
                d.getRygemateriale(),
                d.getKommentar(),
                d.getStartTidspunkt(),
                d.getSlutTidspunkt(),
                d.getDestilleringsTid()
        );
    }
}
