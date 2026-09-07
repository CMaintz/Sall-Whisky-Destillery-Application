package dk.sallwhisky.api.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record DestilleringResponse(
        UUID id,
        int batchNummer,
        String maltBatch,
        KornSummary korn,
        String medarbejder,
        double antalLiter,
        double alkoholProcent,
        String rygemateriale,
        String kommentar,
        LocalDateTime startTidspunkt,
        LocalDateTime slutTidspunkt,
        long destilleringsTidTimer
) {
    public record KornSummary(UUID id, String sort, String variant, String markNavne) {}
}
