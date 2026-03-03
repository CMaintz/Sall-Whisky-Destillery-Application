package dk.sallwhisky.api.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record FadResponse(
        UUID id,
        String fadNummer,
        int literKapacitet,
        String tidligereIndhold,
        String land,
        LocalDate fraAar,
        String leverandoer,
        int alderAar,
        DestillatSummary destillat,  // null if barrel is empty
        HyldeInfo hylde,             // null if not placed in warehouse
        boolean erKlar
) {
    public record DestillatSummary(
            UUID id,
            double antalLiter,
            double alkoholProcent,
            LocalDate startDato,
            boolean erKlar,
            List<ModningsHistorikItem> modningsHistorik
    ) {}

    /** One entry per barrel the destillat has aged in */
    public record ModningsHistorikItem(
            String fadNummer,
            String tidligereIndhold,
            LocalDate paafyldningsDato,
            LocalDate slutDato        // null = current barrel
    ) {}

    public record HyldeInfo(
            UUID hyldeId,
            int position,
            int reolNummer,
            String lagerNavn
    ) {}
}
