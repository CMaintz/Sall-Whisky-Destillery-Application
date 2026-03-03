import { Component, ElementRef, EventEmitter, inject, Output, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SallWhiskyService } from '../../../core/services/sall-whisky.service';
import { Fad, WhiskyProdukt } from '../../../core/models/models';

@Component({
  selector: 'app-opret-whisky-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './opret-whisky-modal.component.html'
})
export class OpretWhiskyModalComponent {
  @ViewChild('modalEl') modalEl!: ElementRef;
  @Output() oprettet = new EventEmitter<WhiskyProdukt>();

  private readonly service = inject(SallWhiskyService);
  private bsModal?: any;

  navn = '';
  klareFade: Fad[] = [];
  valgteFade: Fad[] = [];
  medarbejder = '';
  vandLiter = 0;
  loading = false;
  saving = false;
  error = '';

  get totalLiter(): number {
    return this.valgteFade.reduce((sum, f) => sum + (f.destillat?.antalLiter ?? 0), 0);
  }

  get beregnetAbv(): number {
    const total = this.totalLiter;
    if (total === 0) return 0;
    const alkLiter = this.valgteFade.reduce(
      (sum, f) => sum + (f.destillat?.antalLiter ?? 0) * (f.destillat?.alkoholProcent ?? 0) / 100,
      0
    );
    const totalMedVand = total + this.vandLiter;
    return totalMedVand > 0 ? (alkLiter / totalMedVand) * 100 : 0;
  }

  open(): void {
    this.navn = '';
    this.valgteFade = [];
    this.medarbejder = '';
    this.vandLiter = 0;
    this.saving = false;
    this.error = '';
    this.loading = true;
    this.service.getFadeKlar().subscribe(f => {
      this.klareFade = f;
      this.loading = false;
    });
    this.bsModal = new (window as any).bootstrap.Modal(this.modalEl.nativeElement);
    this.bsModal.show();
  }

  close(): void { this.bsModal?.hide(); }

  isValgt(fad: Fad): boolean {
    return this.valgteFade.some(f => f.id === fad.id);
  }

  tilfoejFad(fad: Fad): void {
    if (!this.isValgt(fad)) this.valgteFade = [...this.valgteFade, fad];
  }

  fjernFad(fad: Fad): void {
    this.valgteFade = this.valgteFade.filter(f => f.id !== fad.id);
  }

  submit(): void {
    if (!this.navn) { this.error = 'Angiv produktnavn'; return; }
    if (this.valgteFade.length === 0) { this.error = 'Vælg mindst ét fad'; return; }
    if (!this.medarbejder) { this.error = 'Angiv medarbejder'; return; }

    this.saving = true;
    this.error = '';

    this.service.opretWhiskyProdukt(this.navn).subscribe({
      next: whisky => {
        this.tapAllBarrels(whisky.id, [...this.valgteFade])
          .then(whiskyId => {
            if (this.vandLiter > 0) {
              this.service.tilfoejVand(whiskyId, this.vandLiter).subscribe({
                next: final => { this.oprettet.emit(final); this.close(); this.saving = false; },
                error: () => { this.error = 'Tapning ok, men fejl ved vand'; this.saving = false; }
              });
            } else {
              this.service.getWhiskyProdukter().subscribe(all => {
                const final = all.find(w => w.id === whiskyId);
                if (final) this.oprettet.emit(final);
                this.close();
                this.saving = false;
              });
            }
          })
          .catch(() => { this.error = 'Fejl ved tapning af fad'; this.saving = false; });
      },
      error: () => { this.error = 'Fejl ved oprettelse af whisky'; this.saving = false; }
    });
  }

  private tapAllBarrels(whiskyId: string, fade: Fad[]): Promise<string> {
    return fade.reduce(
      (chain, fad) => chain.then(() => this.tapOneFad(whiskyId, fad.id)),
      Promise.resolve()
    ).then(() => whiskyId);
  }

  private tapOneFad(whiskyId: string, fadId: string): Promise<void> {
    return new Promise((resolve, reject) => {
      this.service.tapFad(whiskyId, fadId, this.medarbejder).subscribe({
        next: () => resolve(),
        error: reject
      });
    });
  }
}
