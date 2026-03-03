import { Component, OnInit, inject, signal, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SallWhiskyService } from '../../../core/services/sall-whisky.service';
import { Fad } from '../../../core/models/models';
import { OpretFadModalComponent } from '../opret-fad-modal/opret-fad-modal.component';
import { PaafyldFadModalComponent } from '../paafyld-fad-modal/paafyld-fad-modal.component';
import { FlytFadModalComponent } from '../flyt-fad-modal/flyt-fad-modal.component';
import { VisHistorikModalComponent } from '../vis-historik-modal/vis-historik-modal.component';

type FadeFilter = 'alle' | 'tomme' | 'fyldte' | 'klar';

@Component({
  selector: 'app-fade-list',
  standalone: true,
  imports: [
    CommonModule,
    OpretFadModalComponent,
    PaafyldFadModalComponent,
    FlytFadModalComponent,
    VisHistorikModalComponent
  ],
  templateUrl: './fade-list.component.html'
})
export class FadeListComponent implements OnInit {
  @ViewChild('opretFadModal') opretFadModal!: OpretFadModalComponent;
  @ViewChild('paafyldModal') paafyldModal!: PaafyldFadModalComponent;
  @ViewChild('flytModal') flytModal!: FlytFadModalComponent;
  @ViewChild('historikModal') historikModal!: VisHistorikModalComponent;

  private readonly service = inject(SallWhiskyService);

  fade = signal<Fad[]>([]);
  filter = signal<FadeFilter>('alle');
  loading = signal(true);
  omhaeldKilde: Fad | null = null;

  ngOnInit(): void { this.load(); }

  setFilter(f: FadeFilter): void {
    this.filter.set(f);
    this.load();
  }

  private load(): void {
    this.loading.set(true);
    const obs$ = {
      alle: this.service.getFade(),
      tomme: this.service.getTommeFade(),
      fyldte: this.service.getFyldteFade(),
      klar: this.service.getFadeKlar()
    }[this.filter()];

    obs$.subscribe({
      next: fade => { this.fade.set(fade); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  onFadOprettet(fad: Fad): void {
    this.fade.update(list => [...list, fad]);
  }

  onFadPaafyldt(fad: Fad): void {
    this.fade.update(list => list.map(f => f.id === fad.id ? fad : f));
    // refresh to respect current filter
    this.load();
  }

  onFadFlyttet(fad: Fad): void {
    this.fade.update(list => list.map(f => f.id === fad.id ? fad : f));
  }

  sletFad(fad: Fad): void {
    if (!confirm(`Slet fad ${fad.fadNummer}?`)) return;
    this.service.sletFad(fad.id).subscribe({
      next: () => this.fade.update(list => list.filter(f => f.id !== fad.id)),
      error: err => alert('Fejl ved sletning: ' + (err.error?.message ?? 'Ukendt fejl'))
    });
  }

  startOmhaeld(fad: Fad): void {
    this.omhaeldKilde = fad;
    alert(`Kilde-fad valgt: ${fad.fadNummer}. Klik nu "Omhæld til" på et tomt fad.`);
    this.setFilter('tomme');
  }

  omhaeldTil(fad: Fad): void {
    if (!this.omhaeldKilde) return;
    if (!confirm(`Omhæld destillat fra ${this.omhaeldKilde.fadNummer} til ${fad.fadNummer}?`)) return;
    this.service.omhaeldDestillat(this.omhaeldKilde.id, fad.id).subscribe({
      next: () => { this.omhaeldKilde = null; this.load(); },
      error: err => alert('Fejl: ' + (err.error?.message ?? 'Ukendt fejl'))
    });
  }
}
