package dk.sallwhisky.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OpretKornRequest(
        @NotBlank String sort,
        @NotBlank String variant,
        @NotBlank String markNavne
) {}
