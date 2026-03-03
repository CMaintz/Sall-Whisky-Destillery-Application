package dk.sallwhisky.domain.service;

import dk.sallwhisky.api.dto.request.OpretFadRequest;
import dk.sallwhisky.api.dto.request.PaafyldFadRequest;
import dk.sallwhisky.api.dto.response.FadResponse;
import dk.sallwhisky.domain.entity.*;
import dk.sallwhisky.domain.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FadServiceTest {

    @Mock FadRepository fadRepository;
    @Mock DestillatRepository destillatRepository;
    @Mock DestilleringRepository destilleringRepository;
    @Mock HyldeRepository hyldeRepository;

    @InjectMocks FadService fadService;

    private Fad tomFad;
    private Destillering destillering;

    @BeforeEach
    void setUp() {
        tomFad = new Fad();
        tomFad.setFadNummer("F-001");
        tomFad.setLiterKapacitet(40);
        tomFad.setTidligereIndhold("Sherry");
        tomFad.setLand("Spanien");
        tomFad.setFraAar(LocalDate.of(2004, 1, 1));
        tomFad.setLeverandoer("Fadpusheren");

        Korn korn = new Korn("Vårbyg", "Evergreen", "Highland");
        destillering = new Destillering(1, "TestBatch", korn, "Chris", 100, 65, null, null);
    }

    @Test
    void destillat_erKlar_naar_startDato_er_mindst_3_aar_gammel() {
        Destillat destillat = new Destillat();
        destillat.setStartDato(LocalDate.now().minusYears(3).minusDays(1));

        assertThat(destillat.erKlar()).isTrue();
    }

    @Test
    void destillat_ikke_klar_naar_under_3_aar() {
        Destillat destillat = new Destillat();
        destillat.setStartDato(LocalDate.now().minusYears(2));

        assertThat(destillat.erKlar()).isFalse();
    }

    @Test
    void destillat_ikke_klar_naar_startDato_er_null() {
        Destillat destillat = new Destillat();
        assertThat(destillat.erKlar()).isFalse();
    }

    @Test
    void paafyldning_beregner_alkoholprocent_korrekt() {
        // 20L @ 80% + 20L @ 60% = 40L @ 70%
        Korn korn = new Korn("Sort", "Variant", "Mark");
        Destillering d1 = new Destillering(1, "B1", korn, "Chris", 100, 80, null, null);
        Destillering d2 = new Destillering(2, "B2", korn, "Chris", 100, 60, null, null);

        Destillat destillat = new Destillat();
        destillat.tilfoejPaafyldning(new Paafyldning("Chris", 20, d1));
        destillat.tilfoejPaafyldning(new Paafyldning("Chris", 20, d2));

        assertThat(destillat.getAntalLiter()).isEqualTo(40.0);
        assertThat(destillat.getAlkoholProcent()).isEqualTo(70.0);
    }

    @Test
    void whiskyType_cask_strength_naar_single_barrel_ingen_vand_ingen_omhaeld() {
        WhiskyProdukt wp = new WhiskyProdukt("TEST");

        Destillat destillat = new Destillat();
        Fad fad = new Fad();
        ModningsHistorik historik = new ModningsHistorik(destillat, fad, LocalDate.now().minusYears(4));
        destillat.getModningsHistorik().add(historik);

        FadTapning tapning = new FadTapning("Chris", 30, fad, destillat, wp);
        wp.getFadTapninger().add(tapning);

        assertThat(wp.whiskyType()).isEqualTo("Cask Strength");
    }

    @Test
    void whiskyType_single_malt_naar_flere_fade_er_tappet() {
        WhiskyProdukt wp = new WhiskyProdukt("TEST");

        Destillat d1 = new Destillat();
        Destillat d2 = new Destillat();
        Fad fad1 = new Fad();
        Fad fad2 = new Fad();

        d1.getModningsHistorik().add(new ModningsHistorik(d1, fad1, LocalDate.now().minusYears(4)));
        d2.getModningsHistorik().add(new ModningsHistorik(d2, fad2, LocalDate.now().minusYears(4)));

        wp.getFadTapninger().add(new FadTapning("Chris", 20, fad1, d1, wp));
        wp.getFadTapninger().add(new FadTapning("Chris", 20, fad2, d2, wp));

        assertThat(wp.whiskyType()).isEqualTo("Single Malt");
    }

    @Test
    void opretFad_kaster_exception_naar_fad_allerede_har_destillat() {
        Destillat eksisterendeDestillat = new Destillat();
        tomFad.setDestillat(eksisterendeDestillat);

        UUID fadId = UUID.randomUUID();
        when(fadRepository.findById(fadId)).thenReturn(Optional.of(tomFad));

        PaafyldFadRequest req = new PaafyldFadRequest(List.of(
                new PaafyldFadRequest.PaafyldningItem(UUID.randomUUID(), 20, "Chris")));

        assertThatThrownBy(() -> fadService.paafyldFad(fadId, req))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already contains");
    }
}
