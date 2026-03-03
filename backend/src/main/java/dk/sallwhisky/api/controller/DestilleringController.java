package dk.sallwhisky.api.controller;

import dk.sallwhisky.api.dto.request.OpretDestilleringRequest;
import dk.sallwhisky.api.dto.response.DestilleringResponse;
import dk.sallwhisky.domain.service.DestilleringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/destilleringer")
@RequiredArgsConstructor
public class DestilleringController {

    private final DestilleringService destilleringService;

    @GetMapping
    public List<DestilleringResponse> getAlleDestilleringer() {
        return destilleringService.getAlleDestilleringer();
    }

    @GetMapping("/{id}")
    public DestilleringResponse getDestilleringById(@PathVariable UUID id) {
        return destilleringService.getDestilleringById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DestilleringResponse opretDestillering(@RequestBody @Valid OpretDestilleringRequest req) {
        return destilleringService.opretDestillering(req);
    }
}
