package dk.sallwhisky.api.controller;

import dk.sallwhisky.api.dto.request.OpretLagerRequest;
import dk.sallwhisky.api.dto.request.OpretReolRequest;
import dk.sallwhisky.api.dto.response.LagerResponse;
import dk.sallwhisky.domain.service.LagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lagre")
@RequiredArgsConstructor
public class LagerController {

    private final LagerService lagerService;

    @GetMapping
    public List<LagerResponse> getAlleLagre() {
        return lagerService.getAlleLagre();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LagerResponse opretLager(@RequestBody @Valid OpretLagerRequest req) {
        return lagerService.opretLager(req);
    }

    @PostMapping("/{id}/reoler")
    public LagerResponse tilfoejReoler(@PathVariable UUID id, @RequestBody @Valid OpretReolRequest req) {
        return lagerService.tilfoejReoler(id, req);
    }
}
