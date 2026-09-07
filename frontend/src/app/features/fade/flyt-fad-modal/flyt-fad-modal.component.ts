import { Component, ElementRef, EventEmitter, inject, Output, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SallWhiskyService } from '../../../core/services/sall-whisky.service';
import { Fad, Lager } from '../../../core/models/models';

@Component({
  selector: 'app-flyt-fad-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './flyt-fad-modal.component.html'
})
export class FlytFadModalComponent {
  @ViewChild('modalEl') modalEl!: ElementRef;
  @Output() flyttet = new EventEmitter<Fad>();

  private readonly service = inject(SallWhiskyService);
  private bsModal?: any;

  fad: Fad | null = null;
  lagre: Lager[] = [];
  valgtLagerId = '';
  valgtReolId = '';
  valgtHyldeId = '';
  saving = false;
  error = '';

  get valgtLager() { return this.lagre.find(l => l.id === this.valgtLagerId); }
  get valgtReol() { return this.valgtLager?.reoler.find(r => r.id === this.valgtReolId); }
  get ledigeHylder() { return this.valgtReol?.hylder.filter(h => h.erLedig) ?? []; }

  open(fad: Fad): void {
    this.fad = fad;
    this.valgtLagerId = '';
    this.valgtReolId = '';
    this.valgtHyldeId = '';
    this.saving = false;
    this.error = '';
    this.service.getLagre().subscribe(l => this.lagre = l);
    this.bsModal = new (window as any).bootstrap.Modal(this.modalEl.nativeElement);
    this.bsModal.show();
  }

  close(): void { this.bsModal?.hide(); }

  onLagerChange(): void { this.valgtReolId = ''; this.valgtHyldeId = ''; }
  onReolChange(): void { this.valgtHyldeId = ''; }

  submit(): void {
    if (!this.valgtHyldeId) { this.error = 'Vælg en hylde'; return; }
    if (!this.fad) return;
    this.saving = true;
    this.error = '';
    this.service.flytFad(this.fad.id, this.valgtHyldeId).subscribe({
      next: fad => { this.flyttet.emit(fad); this.close(); this.saving = false; },
      error: () => { this.error = 'Fejl ved flytning'; this.saving = false; }
    });
  }
}
