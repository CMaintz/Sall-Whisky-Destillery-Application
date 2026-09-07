import { Component, ElementRef, EventEmitter, inject, Output, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SallWhiskyService } from '../../../core/services/sall-whisky.service';
import { WhiskyProdukt } from '../../../core/models/models';

@Component({
  selector: 'app-tilfoej-vand-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tilfoej-vand-modal.component.html'
})
export class TilfoejVandModalComponent {
  @ViewChild('modalEl') modalEl!: ElementRef;
  @Output() tilfoejt = new EventEmitter<WhiskyProdukt>();

  private readonly service = inject(SallWhiskyService);
  private bsModal?: any;

  whisky: WhiskyProdukt | null = null;
  liter = 1;
  saving = false;
  error = '';

  get nytAbv(): number {
    if (!this.whisky || this.whisky.antalLiter === 0) return 0;
    const alkLiter = this.whisky.antalLiter * (this.whisky.alkoholProcent / 100);
    const nytTotal = this.whisky.antalLiter + this.liter;
    return nytTotal > 0 ? (alkLiter / nytTotal) * 100 : 0;
  }

  open(whisky: WhiskyProdukt): void {
    this.whisky = whisky;
    this.liter = 1;
    this.saving = false;
    this.error = '';
    this.bsModal = new (window as any).bootstrap.Modal(this.modalEl.nativeElement);
    this.bsModal.show();
  }

  close(): void { this.bsModal?.hide(); }

  submit(): void {
    if (!this.whisky || this.liter <= 0) { this.error = 'Angiv gyldigt antal liter'; return; }
    this.saving = true;
    this.error = '';
    this.service.tilfoejVand(this.whisky.id, this.liter).subscribe({
      next: wp => { this.tilfoejt.emit(wp); this.close(); this.saving = false; },
      error: () => { this.error = 'Fejl ved tilføjelse af vand'; this.saving = false; }
    });
  }
}
