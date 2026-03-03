package dk.sallwhisky.api.controller;

import dk.sallwhisky.api.dto.request.OpretKornRequest;
import dk.sallwhisky.domain.entity.Korn;
import dk.sallwhisky.domain.repository.KornRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/korn")
@RequiredArgsConstructor
public class KornController {

    private final KornRepository kornRepository;

    @GetMapping
    public List<Korn> getAlleKorn() {
        return kornRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Korn opretKorn(@RequestBody @Valid OpretKornRequest req) {
        return kornRepository.save(new Korn(req.sort(), req.variant(), req.markNavne()));
    }
}
