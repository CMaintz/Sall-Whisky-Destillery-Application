package dk.sallwhisky.api.dto.request;

import jakarta.validation.constraints.Min;

public record OpretReolRequest(
        @Min(1) int antalReoler,
        @Min(1) int hylderPerReol
) {}
