package dk.sallwhisky.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OpretWhiskyProduktRequest(@NotBlank String navn) {}
