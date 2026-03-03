package dk.sallwhisky.api.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record WhiskyProduktResponse(
        UUID id,
        String navn,
        double alkoholProcent,
        double antalLiter,
        double literVandTilfojet,
        String whiskyType,
        int antalFlasker,
        List<FadTapningSummary> fadTapninger,
        List<FlaskeSummary> flasker
) {
    public record FadTapningSummary(
            UUID fadId,
            String fadNummer,
            String tidligereIndhold,
            double literTappet,
            LocalDate tapningsDato
    ) {}

    public record FlaskeSummary(
            UUID id,
            int flaskeNummer,
            String produktHistorie,
            LocalDate flaskningsDato
    ) {}
}
