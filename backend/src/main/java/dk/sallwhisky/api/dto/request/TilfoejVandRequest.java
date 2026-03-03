package dk.sallwhisky.api.dto.request;

import jakarta.validation.constraints.Positive;

public record TilfoejVandRequest(@Positive double liter) {}
