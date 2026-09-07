package dk.sallwhisky.api.controller;

import dk.sallwhisky.api.dto.request.OpretWhiskyProduktRequest;
import dk.sallwhisky.api.dto.request.TapFadRequest;
import dk.sallwhisky.api.dto.request.TilfoejVandRequest;
import dk.sallwhisky.api.dto.response.WhiskyProduktResponse;
import dk.sallwhisky.domain.service.WhiskyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/whisky")
@RequiredArgsConstructor
public class WhiskyController {

    private final WhiskyService whiskyService;

    @GetMapping
    public List<WhiskyProduktResponse> getAlleWhiskyProdukter() {
        return whiskyService.getAlleWhiskyProdukter();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WhiskyProduktResponse opretWhiskyProdukt(@RequestBody @Valid OpretWhiskyProduktRequest req) {
        return whiskyService.opretWhiskyProdukt(req);
    }

    @PostMapping("/{id}/tap")
    public WhiskyProduktResponse tapFad(@PathVariable UUID id, @RequestBody @Valid TapFadRequest req) {
        return whiskyService.tapFad(id, req);
    }

    @PostMapping("/{id}/vand")
    public WhiskyProduktResponse tilfoejVand(@PathVariable UUID id, @RequestBody @Valid TilfoejVandRequest req) {
        return whiskyService.tilfoejVand(id, req.liter());
    }

    @PostMapping("/{id}/flasker")
    public WhiskyProduktResponse opretFlasker(@PathVariable UUID id) {
        return whiskyService.opretFlasker(id);
    }
}
