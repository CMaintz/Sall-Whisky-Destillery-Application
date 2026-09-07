import { Component, ElementRef, EventEmitter, inject, Output, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SallWhiskyService } from '../../../core/services/sall-whisky.service';
import { Lager } from '../../../core/models/models';

@Component({
  selector: 'app-opret-lager-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './opret-lager-modal.component.html'
})
export class OpretLagerModalComponent {
  @ViewChild('modalEl') modalEl!: ElementRef;
  @Output() oprettet = new EventEmitter<Lager>();

  private readonly service = inject(SallWhiskyService);
  private bsModal?: any;

  navn = '';
  antalReoler = 3;
  hylderPerReol = 5;
  saving = false;
  error = '';

  open(): void {
    this.navn = '';
    this.antalReoler = 3;
    this.hylderPerReol = 5;
    this.saving = false;
    this.error = '';
    this.bsModal = new (window as any).bootstrap.Modal(this.modalEl.nativeElement);
    this.bsModal.show();
  }

  close(): void { this.bsModal?.hide(); }

  submit(): void {
    if (!this.navn) { this.error = 'Angiv lagernavnet'; return; }
    if (this.antalReoler < 1 || this.hylderPerReol < 1) { this.error = 'Antal skal være mindst 1'; return; }
    this.saving = true;
    this.error = '';
    this.service.opretLager({ navn: this.navn, antalReoler: this.antalReoler, hylderPerReol: this.hylderPerReol }).subscribe({
      next: lager => { this.oprettet.emit(lager); this.close(); this.saving = false; },
      error: () => { this.error = 'Fejl ved oprettelse'; this.saving = false; }
    });
  }
}
