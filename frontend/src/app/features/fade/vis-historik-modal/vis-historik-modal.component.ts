import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Fad } from '../../../core/models/models';

@Component({
  selector: 'app-vis-historik-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './vis-historik-modal.component.html'
})
export class VisHistorikModalComponent {
  @ViewChild('modalEl') modalEl!: ElementRef;

  fad: Fad | null = null;
  private bsModal?: any;

  open(fad: Fad): void {
    this.fad = fad;
    this.bsModal = new (window as any).bootstrap.Modal(this.modalEl.nativeElement);
    this.bsModal.show();
  }

  close(): void { this.bsModal?.hide(); }
}
