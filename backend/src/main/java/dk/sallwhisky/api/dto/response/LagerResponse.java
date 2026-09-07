package dk.sallwhisky.api.dto.response;

import java.util.List;
import java.util.UUID;

public record LagerResponse(
        UUID id,
        String navn,
        List<ReolResponse> reoler
) {
    public record ReolResponse(
            UUID id,
            int reolNummer,
            List<HyldeResponse> hylder
    ) {}

    public record HyldeResponse(
            UUID id,
            int position,
            boolean erLedig,
            FadSummary fad  // null if shelf is empty
    ) {}

    public record FadSummary(
            UUID id,
            String fadNummer,
            int literKapacitet,
            String tidligereIndhold,
            boolean harDestillat,
            boolean erKlar
    ) {}
}
