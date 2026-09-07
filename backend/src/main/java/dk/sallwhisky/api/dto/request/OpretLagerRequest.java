package dk.sallwhisky.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OpretLagerRequest(
        @NotBlank String navn,
        @Positive int antalReoler,
        @Positive int hylderPerReol
) {}
