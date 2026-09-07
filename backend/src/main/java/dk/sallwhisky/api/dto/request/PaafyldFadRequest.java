package dk.sallwhisky.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.UUID;

public record PaafyldFadRequest(
        @NotEmpty @Valid List<PaafyldningItem> paafyldninger
) {
    public record PaafyldningItem(
            @NotNull UUID destilleringId,
            @Positive double liter,
            @NotBlank String medarbejder
    ) {}
}
