import { Component, ElementRef, EventEmitter, inject, Output, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SallWhiskyService } from '../../../core/services/sall-whisky.service';
import { Korn } from '../../../core/models/models';

@Component({
  selector: 'app-opret-korn-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './opret-korn-modal.component.html'
})
export class OpretKornModalComponent {
  @ViewChild('modalEl') modalEl!: ElementRef;
  @Output() oprettet = new EventEmitter<Korn>();

  private readonly service = inject(SallWhiskyService);
  private bsModal?: any;

  sort = '';
  variant = '';
  markNavne = '';
  saving = false;
  error = '';

  open(): void {
    this.sort = '';
    this.variant = '';
    this.markNavne = '';
    this.saving = false;
    this.error = '';
    this.bsModal = new (window as any).bootstrap.Modal(this.modalEl.nativeElement);
    this.bsModal.show();
  }

  close(): void { this.bsModal?.hide(); }

  submit(): void {
    if (!this.sort || !this.variant || !this.markNavne) {
      this.error = 'Udfyld alle felter';
      return;
    }
    this.saving = true;
    this.error = '';
    this.service.opretKorn({ sort: this.sort, variant: this.variant, markNavne: this.markNavne }).subscribe({
      next: korn => { this.oprettet.emit(korn); this.close(); this.saving = false; },
      error: () => { this.error = 'Fejl ved oprettelse'; this.saving = false; }
    });
  }
}
