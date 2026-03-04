import { Component, OnInit, inject, signal, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SallWhiskyService } from '../../../core/services/sall-whisky.service';
import { WhiskyFlaskeSummary, WhiskyProdukt } from '../../../core/models/models';
import { OpretWhiskyModalComponent } from '../opret-whisky-modal/opret-whisky-modal.component';
import { TilfoejVandModalComponent } from '../tilfoej-vand-modal/tilfoej-vand-modal.component';

@Component({
  selector: 'app-whisky-list',
  standalone: true,
  imports: [CommonModule, OpretWhiskyModalComponent, TilfoejVandModalComponent],
  templateUrl: './whisky-list.component.html'
})
export class WhiskyListComponent implements OnInit {
  @ViewChild('opretWhiskyModal') opretWhiskyModal!: OpretWhiskyModalComponent;
  @ViewChild('tilfoejVandModal') tilfoejVandModal!: TilfoejVandModalComponent;

  private readonly service = inject(SallWhiskyService);

  produkter = signal<WhiskyProdukt[]>([]);
  valgtProdukt = signal<WhiskyProdukt | null>(null);
  valgtFlaske = signal<WhiskyFlaskeSummary | null>(null);
  loading = signal(true);
  flaskningBusy = signal<string | null>(null);

  ngOnInit(): void {
    this.service.getWhiskyProdukter().subscribe({
      next: p => { this.produkter.set(p); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  vaelgProdukt(wp: WhiskyProdukt): void {
    this.valgtProdukt.set(this.valgtProdukt()?.id === wp.id ? null : wp);
    this.valgtFlaske.set(null);
  }

  vaelgFlaske(f: WhiskyFlaskeSummary): void {
    this.valgtFlaske.set(this.valgtFlaske()?.id === f.id ? null : f);
  }

  onWhiskyOprettet(wp: WhiskyProdukt): void {
    this.produkter.update(list => [...list, wp]);
  }

  onVandTilfoejt(wp: WhiskyProdukt): void {
    this.produkter.update(list => list.map(p => p.id === wp.id ? wp : p));
    if (this.valgtProdukt()?.id === wp.id) this.valgtProdukt.set(wp);
  }

  opretFlasker(wp: WhiskyProdukt): void {
    if (!confirm(`Opret ${Math.floor(wp.antalLiter)} flasker af ${wp.navn}?`)) return;
    this.flaskningBusy.set(wp.id);
    this.service.opretFlasker(wp.id).subscribe({
      next: updated => {
        this.produkter.update(list => list.map(p => p.id === updated.id ? updated : p));
        if (this.valgtProdukt()?.id === updated.id) this.valgtProdukt.set(updated);
        this.flaskningBusy.set(null);
      },
      error: () => this.flaskningBusy.set(null)
    });
  }

  exportLabel(wp: WhiskyProdukt): void {
    const lines = [
      `=== ${wp.navn} ===`,
      `Type: ${wp.whiskyType}`,
      `ABV: ${wp.alkoholProcent.toFixed(1)}%`,
      `Antal liter: ${wp.antalLiter.toFixed(1)}L`,
      `Vand tilsat: ${wp.literVandTilfojet.toFixed(1)}L`,
      `Antal flasker: ${wp.antalFlasker}`,
      '',
      'Tappet fra fade:',
      ...wp.fadTapninger.map(ft =>
        `  - Fad ${ft.fadNummer} (${ft.tidligereIndhold}): ${ft.literTappet.toFixed(1)}L, ${ft.tapningsDato}`
      )
    ];
    const blob = new Blob([lines.join('\n')], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `${wp.navn.replace(/\s+/g, '_')}_etiket.txt`;
    a.click();
    URL.revokeObjectURL(url);
  }

  fadNumre(wp: WhiskyProdukt): string {
    return wp.fadTapninger.map(ft => ft.fadNummer).join(', ');
  }

  exportFlaskeLabel(flaske: WhiskyFlaskeSummary, produktNavn: string): void {
    const blob = new Blob([flaske.produktHistorie], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `${produktNavn.replace(/\s+/g, '_')}_flaske${flaske.flaskeNummer}.txt`;
    a.click();
    URL.revokeObjectURL(url);
  }
}
