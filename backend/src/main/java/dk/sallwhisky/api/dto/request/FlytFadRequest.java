package dk.sallwhisky.api.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FlytFadRequest(@NotNull UUID hyldeId) {}
