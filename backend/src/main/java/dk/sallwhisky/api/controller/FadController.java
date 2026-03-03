package dk.sallwhisky.api.controller;

import dk.sallwhisky.api.dto.request.FlytFadRequest;
import dk.sallwhisky.api.dto.request.OpretFadRequest;
import dk.sallwhisky.api.dto.request.PaafyldFadRequest;
import dk.sallwhisky.api.dto.response.FadResponse;
import dk.sallwhisky.domain.service.FadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fade")
@RequiredArgsConstructor
public class FadController {

    private final FadService fadService;

    @GetMapping
    public List<FadResponse> getAlleFade() {
        return fadService.getAlleFade();
    }

    @GetMapping("/tomme")
    public List<FadResponse> getTommeFade() {
        return fadService.getTommeFade();
    }

    @GetMapping("/klar")
    public List<FadResponse> getFadeKlar() {
        return fadService.getFadeKlar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FadResponse opretFad(@RequestBody @Valid OpretFadRequest req) {
        return fadService.opretFad(req);
    }

    @PostMapping("/{id}/destillat")
    public FadResponse paafyldFad(@PathVariable UUID id, @RequestBody @Valid PaafyldFadRequest req) {
        return fadService.paafyldFad(id, req);
    }

    @PutMapping("/{id}/flyt")
    public FadResponse flytFad(@PathVariable UUID id, @RequestBody @Valid FlytFadRequest req) {
        return fadService.flytFad(id, req);
    }

    @GetMapping("/fyldte")
    public List<FadResponse> getFyldteFade() {
        return fadService.getFyldteFade();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sletFad(@PathVariable UUID id) {
        fadService.sletFad(id);
    }

    @PostMapping("/{fadFraId}/omhaeld/{fadTilId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void omhaeldDestillat(@PathVariable UUID fadFraId, @PathVariable UUID fadTilId) {
        fadService.omhaeldDestillat(fadFraId, fadTilId);
    }
}
