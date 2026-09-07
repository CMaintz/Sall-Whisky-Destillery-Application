import { Component, ElementRef, EventEmitter, inject, Output, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SallWhiskyService } from '../../../core/services/sall-whisky.service';
import { Destillering, Korn } from '../../../core/models/models';

@Component({
  selector: 'app-opret-destillering-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './opret-destillering-modal.component.html'
})
export class OpretDestilleringModalComponent {
  @ViewChild('modalEl') modalEl!: ElementRef;
  @Output() oprettet = new EventEmitter<Destillering>();

  private readonly service = inject(SallWhiskyService);
  private bsModal?: any;

  kornListe: Korn[] = [];
  maltBatch = '';
  kornId = '';
  medarbejder = '';
  antalLiter = 100;
  alkoholProcent = 70;
  rygemateriale = '';
  kommentar = '';
  saving = false;
  loading = false;
  error = '';

  open(): void {
    this.maltBatch = '';
    this.kornId = '';
    this.medarbejder = '';
    this.antalLiter = 100;
    this.alkoholProcent = 70;
    this.rygemateriale = '';
    this.kommentar = '';
    this.saving = false;
    this.error = '';
    this.loading = true;
    this.service.getKorn().subscribe(k => { this.kornListe = k; this.loading = false; });
    this.bsModal = new (window as any).bootstrap.Modal(this.modalEl.nativeElement);
    this.bsModal.show();
  }

  close(): void { this.bsModal?.hide(); }

  submit(): void {
    if (!this.maltBatch || !this.kornId || !this.medarbejder) {
      this.error = 'Udfyld alle påkrævede felter';
      return;
    }
    this.saving = true;
    this.error = '';
    this.service.opretDestillering({
      maltBatch: this.maltBatch,
      kornId: this.kornId,
      medarbejder: this.medarbejder,
      antalLiter: this.antalLiter,
      alkoholProcent: this.alkoholProcent,
      rygemateriale: this.rygemateriale,
      kommentar: this.kommentar
    }).subscribe({
      next: d => { this.oprettet.emit(d); this.close(); this.saving = false; },
      error: () => { this.error = 'Fejl ved oprettelse'; this.saving = false; }
    });
  }
}
