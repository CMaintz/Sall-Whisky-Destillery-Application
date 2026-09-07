import { Component, OnInit, inject, signal, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SallWhiskyService } from '../../../core/services/sall-whisky.service';
import { Destillering, Korn } from '../../../core/models/models';
import { OpretDestilleringModalComponent } from '../opret-destillering-modal/opret-destillering-modal.component';
import { OpretKornModalComponent } from '../opret-korn-modal/opret-korn-modal.component';

@Component({
  selector: 'app-destillering-list',
  standalone: true,
  imports: [CommonModule, OpretDestilleringModalComponent, OpretKornModalComponent],
  templateUrl: './destillering-list.component.html'
})
export class DestilleringListComponent implements OnInit {
  @ViewChild('opretDestilleringModal') opretDestilleringModal!: OpretDestilleringModalComponent;
  @ViewChild('opretKornModal') opretKornModal!: OpretKornModalComponent;

  private readonly service = inject(SallWhiskyService);

  destilleringer = signal<Destillering[]>([]);
  valgtDestillering = signal<Destillering | null>(null);
  loading = signal(true);

  ngOnInit(): void {
    this.service.getDestilleringer().subscribe({
      next: d => { this.destilleringer.set(d); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  onDestilleringOprettet(d: Destillering): void {
    this.destilleringer.update(list => [...list, d]);
    this.valgtDestillering.set(d);
  }

  onKornOprettet(_k: Korn): void {}

  vaelg(d: Destillering): void {
    this.valgtDestillering.set(this.valgtDestillering()?.id === d.id ? null : d);
  }

  destilleringstid(d: Destillering): string {
    if (d.destilleringsTidTimer != null) return `${d.destilleringsTidTimer}t`;
    if (d.startTidspunkt && d.slutTidspunkt) {
      const diff = new Date(d.slutTidspunkt).getTime() - new Date(d.startTidspunkt).getTime();
      return `${Math.round(diff / 3600000)}t`;
    }
    return '—';
  }
}
