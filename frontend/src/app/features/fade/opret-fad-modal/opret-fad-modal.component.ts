import { Component, ElementRef, EventEmitter, inject, Output, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SallWhiskyService } from '../../../core/services/sall-whisky.service';
import { Fad } from '../../../core/models/models';

@Component({
  selector: 'app-opret-fad-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './opret-fad-modal.component.html'
})
export class OpretFadModalComponent {
  @ViewChild('modalEl') modalEl!: ElementRef;
  @Output() oprettet = new EventEmitter<Fad>();

  private readonly service = inject(SallWhiskyService);
  private bsModal?: any;

  literKapacitet = 100;
  tidligereIndhold = '';
  land = '';
  fraAar = new Date().getFullYear() - 5;
  leverandoer = '';
  saving = false;
  error = '';

  open(): void {
    this.reset();
    this.bsModal = new (window as any).bootstrap.Modal(this.modalEl.nativeElement);
    this.bsModal.show();
  }

  close(): void {
    this.bsModal?.hide();
  }

  submit(): void {
    if (!this.tidligereIndhold || !this.land || !this.leverandoer) {
      this.error = 'Udfyld alle felter';
      return;
    }
    this.saving = true;
    this.error = '';
    this.service.opretFad({
      literKapacitet: this.literKapacitet,
      tidligereIndhold: this.tidligereIndhold,
      land: this.land,
      fraAar: `${this.fraAar}-01-01`,
      leverandoer: this.leverandoer
    }).subscribe({
      next: fad => { this.oprettet.emit(fad); this.close(); this.saving = false; },
      error: () => { this.error = 'Fejl ved oprettelse'; this.saving = false; }
    });
  }

  private reset(): void {
    this.literKapacitet = 100;
    this.tidligereIndhold = '';
    this.land = '';
    this.fraAar = new Date().getFullYear() - 5;
    this.leverandoer = '';
    this.saving = false;
    this.error = '';
  }
}
