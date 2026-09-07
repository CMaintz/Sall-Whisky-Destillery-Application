import { Component, OnInit, inject, signal, ViewChild} from '@angular/core';
import { CommonModule } from '@angular/common';
import { SallWhiskyService } from '../../core/services/sall-whisky.service';
import { Fad, Hylde, Lager, Reol} from '../../core/models/models';
import { OpretLagerModalComponent } from './opret-lager-modal/opret-lager-modal.component';
import { OpretReolModalComponent } from './opret-reol-modal/opret-reol-modal.component';
import { FlytFadModalComponent } from '../fade/flyt-fad-modal/flyt-fad-modal.component';

@Component({
  selector: 'app-lagerstyring',
  standalone: true,
  imports: [CommonModule, OpretLagerModalComponent, OpretReolModalComponent, FlytFadModalComponent],
  templateUrl: './lagerstyring.component.html'
})
export class LagerstyringComponent implements OnInit {
  @ViewChild('opretLagerModal') opretLagerModal!: OpretLagerModalComponent;
  @ViewChild('opretReolModal') opretReolModal!: OpretReolModalComponent;
  @ViewChild('flytFadModal') flytFadModal!: FlytFadModalComponent;

  private readonly service = inject(SallWhiskyService);

  lagre = signal<Lager[]>([]);
  valgtLager = signal<Lager | null>(null);
  valgtReol = signal<Reol | null>(null);
  valgtHylde = signal<Hylde | null>(null);
  loading = signal(true);

  ngOnInit(): void { this.load(); }

  private load(): void {
    this.loading.set(true);
    this.service.getLagre().subscribe({
      next: l => { this.lagre.set(l); this.loading.set(false); this.syncSelection(); },
      error: () => this.loading.set(false)
    });
  }

  /** After reload, restore selected lager/reol if still present */
  private syncSelection(): void {
    const prevLagerId = this.valgtLager()?.id;
    const prevReolId = this.valgtReol()?.id;
    if (prevLagerId) {
      const lager = this.lagre().find(l => l.id === prevLagerId) ?? null;
      this.valgtLager.set(lager);
      if (lager && prevReolId) {
        const reol = lager.reoler.find(r => r.id === prevReolId) ?? null;
        this.valgtReol.set(reol);
      }
    }
  }

  vaelgLager(l: Lager): void {
    this.valgtLager.set(this.valgtLager()?.id === l.id ? null : l);
    this.valgtReol.set(null);
    this.valgtHylde.set(null);
  }

  vaelgReol(r: Reol): void {
    this.valgtReol.set(this.valgtReol()?.id === r.id ? null : r);
    this.valgtHylde.set(null);
  }

  vaelgHylde(h: Hylde): void {
    this.valgtHylde.set(this.valgtHylde()?.id === h.id ? null : h);
  }

  onLagerOprettet(lager: Lager): void {
    this.lagre.update(list => [...list, lager]);
    this.valgtLager.set(lager);
    this.valgtReol.set(null);
    this.valgtHylde.set(null);
  }

  onReolOprettet(updatedLager: Lager): void {
    this.lagre.update(list => list.map(l => l.id === updatedLager.id ? updatedLager : l));
    this.valgtLager.set(updatedLager);
  }

  onFadFlyttet(fad: Fad): void {
    this.load();
  }

  antalOptagede(r: Reol): number {
    return r.hylder.filter(h => !h.erLedig).length;
  }

  /** Find a Fad by id to pass to FlytFadModal */
  openFlytFad(hylde: Hylde): void {
    if (!hylde.fad) return;
    this.service.getFade().subscribe(alle => {
      const fad = alle.find(f => f.id === hylde.fad!.id);
      if (fad) this.flytFadModal.open(fad);
    });
  }
}
