import { Component, ElementRef, EventEmitter, inject, Output, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SallWhiskyService } from '../../../core/services/sall-whisky.service';
import { Lager } from '../../../core/models/models';

@Component({
  selector: 'app-opret-reol-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './opret-reol-modal.component.html'
})
export class OpretReolModalComponent {
  @ViewChild('modalEl') modalEl!: ElementRef;
  @Output() oprettet = new EventEmitter<Lager>();

  private readonly service = inject(SallWhiskyService);
  private bsModal?: any;

  lagerId = '';
  lagerNavn = '';
  antalReoler = 1;
  hylderPerReol = 5;
  saving = false;
  error = '';

  open(lagerId: string, lagerNavn: string): void {
    this.lagerId = lagerId;
    this.lagerNavn = lagerNavn;
    this.antalReoler = 1;
    this.hylderPerReol = 5;
    this.saving = false;
    this.error = '';
    this.bsModal = new (window as any).bootstrap.Modal(this.modalEl.nativeElement);
    this.bsModal.show();
  }

  close(): void { this.bsModal?.hide(); }

  submit(): void {
    if (this.antalReoler < 1 || this.hylderPerReol < 1) { this.error = 'Antal skal være mindst 1'; return; }
    this.saving = true;
    this.error = '';
    this.service.tilfoejReoler(this.lagerId, { antalReoler: this.antalReoler, hylderPerReol: this.hylderPerReol }).subscribe({
      next: lager => { this.oprettet.emit(lager); this.close(); this.saving = false; },
      error: () => { this.error = 'Fejl ved oprettelse'; this.saving = false; }
    });
  }
}
