package dk.sallwhisky.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TapFadRequest(
        @NotNull UUID fadId,
        @NotBlank String medarbejder
) {}
