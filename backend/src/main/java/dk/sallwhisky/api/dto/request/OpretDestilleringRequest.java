package dk.sallwhisky.api.dto.request;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record OpretDestilleringRequest(
        @NotBlank String maltBatch,
        @NotNull UUID kornId,
        @NotBlank String medarbejder,
        @Positive double antalLiter,
        @Positive @Max(100) double alkoholProcent,
        String rygemateriale,
        String kommentar
) {}
