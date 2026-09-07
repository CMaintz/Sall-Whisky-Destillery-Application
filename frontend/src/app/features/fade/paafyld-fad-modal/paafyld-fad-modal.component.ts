import { Component, ElementRef, EventEmitter, inject, Output, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SallWhiskyService } from '../../../core/services/sall-whisky.service';
import { Destillering, Fad, Lager } from '../../../core/models/models';

interface PaafyldningItem {
  destillering: Destillering;
  liter: number;
}

@Component({
  selector: 'app-paafyld-fad-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './paafyld-fad-modal.component.html'
})
export class PaafyldFadModalComponent {
  @ViewChild('modalEl') modalEl!: ElementRef;
  @Output() paafyldt = new EventEmitter<Fad>();

  private readonly service = inject(SallWhiskyService);
  private bsModal?: any;

  fad: Fad | null = null;
  destilleringer: Destillering[] = [];
  valgte: PaafyldningItem[] = [];
  medarbejder = '';
  lagre: Lager[] = [];
  valgtLagerId = '';
  valgtReolId = '';
  valgtHyldeId = '';
  saving = false;
  loading = false;
  error = '';

  get valgtLager() { return this.lagre.find(l => l.id === this.valgtLagerId); }
  get valgtReol() { return this.valgtLager?.reoler.find(r => r.id === this.valgtReolId); }
  get ledigeHylder() { return this.valgtReol?.hylder.filter(h => h.erLedig) ?? []; }

  open(fad: Fad): void {
    this.fad = fad;
    this.valgte = [];
    this.medarbejder = '';
    this.valgtLagerId = '';
    this.valgtReolId = '';
    this.valgtHyldeId = '';
    this.saving = false;
    this.error = '';
    this.loading = true;
    this.bsModal = new (window as any).bootstrap.Modal(this.modalEl.nativeElement);
    this.bsModal.show();

    this.service.getDestilleringer().subscribe(d => {
      this.destilleringer = d;
      this.loading = false;
    });
    this.service.getLagre().subscribe(l => this.lagre = l);
  }

  close(): void { this.bsModal?.hide(); }

  toggleDestillering(d: Destillering): void {
    const idx = this.valgte.findIndex(v => v.destillering.id === d.id);
    if (idx >= 0) {
      this.valgte.splice(idx, 1);
    } else {
      this.valgte.push({ destillering: d, liter: d.antalLiter });
    }
  }

  isValgt(d: Destillering): boolean {
    return this.valgte.some(v => v.destillering.id === d.id);
  }

  onLagerChange(): void {
    this.valgtReolId = '';
    this.valgtHyldeId = '';
  }

  onReolChange(): void {
    this.valgtHyldeId = '';
  }

  submit(): void {
    if (this.valgte.length === 0) { this.error = 'Vælg mindst én destillering'; return; }
    if (!this.medarbejder) { this.error = 'Angiv medarbejder'; return; }
    if (!this.fad) return;

    this.saving = true;
    this.error = '';

    this.service.paafyldFad(this.fad.id, {
      paafyldninger: this.valgte.map(v => ({
        destilleringId: v.destillering.id,
        liter: v.liter,
        medarbejder: this.medarbejder
      }))
    }).subscribe({
      next: fad => {
        if (this.valgtHyldeId) {
          this.service.flytFad(fad.id, this.valgtHyldeId).subscribe({
            next: moved => { this.paafyldt.emit(moved); this.close(); this.saving = false; },
            error: () => { this.paafyldt.emit(fad); this.close(); this.saving = false; }
          });
        } else {
          this.paafyldt.emit(fad);
          this.close();
          this.saving = false;
        }
      },
      error: () => { this.error = 'Fejl ved påfyldning'; this.saving = false; }
    });
  }
}
