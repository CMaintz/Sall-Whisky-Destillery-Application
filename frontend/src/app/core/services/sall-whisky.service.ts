import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Destillering, Fad, Korn, Lager, WhiskyProdukt } from '../models/models';

const API = 'http://localhost:8080/api';

@Injectable({ providedIn: 'root' })
export class SallWhiskyService {
  private readonly http = inject(HttpClient);

  getFade(): Observable<Fad[]> {
    return this.http.get<Fad[]>(`${API}/fade`);
  }

  getTommeFade(): Observable<Fad[]> {
    return this.http.get<Fad[]>(`${API}/fade/tomme`);
  }

  getFyldteFade(): Observable<Fad[]> {
    return this.http.get<Fad[]>(`${API}/fade/fyldte`);
  }

  getFadeKlar(): Observable<Fad[]> {
    return this.http.get<Fad[]>(`${API}/fade/klar`);
  }

  opretFad(data: {
    literKapacitet: number;
    tidligereIndhold: string;
    land: string;
    fraAar: string;
    leverandoer: string;
  }): Observable<Fad> {
    return this.http.post<Fad>(`${API}/fade`, data);
  }

  sletFad(id: string): Observable<void> {
    return this.http.delete<void>(`${API}/fade/${id}`);
  }

  paafyldFad(id: string, data: {
    paafyldninger: { destilleringId: string; liter: number; medarbejder: string }[]
  }): Observable<Fad> {
    return this.http.post<Fad>(`${API}/fade/${id}/destillat`, data);
  }

  flytFad(id: string, hyldeId: string): Observable<Fad> {
    return this.http.put<Fad>(`${API}/fade/${id}/flyt`, { hyldeId });
  }

  omhaeldDestillat(fadFraId: string, fadTilId: string): Observable<void> {
    return this.http.post<void>(`${API}/fade/${fadFraId}/omhaeld/${fadTilId}`, {});
  }

  getDestilleringer(): Observable<Destillering[]> {
    return this.http.get<Destillering[]>(`${API}/destilleringer`);
  }

  opretDestillering(data: {
    maltBatch: string;
    kornId: string;
    medarbejder: string;
    antalLiter: number;
    alkoholProcent: number;
    rygemateriale: string;
    kommentar: string;
  }): Observable<Destillering> {
    return this.http.post<Destillering>(`${API}/destilleringer`, data);
  }

  getKorn(): Observable<Korn[]> {
    return this.http.get<Korn[]>(`${API}/korn`);
  }

  opretKorn(data: { sort: string; variant: string; markNavne: string }): Observable<Korn> {
    return this.http.post<Korn>(`${API}/korn`, data);
  }

  getLagre(): Observable<Lager[]> {
    return this.http.get<Lager[]>(`${API}/lagre`);
  }

  opretLager(data: { navn: string; antalReoler: number; hylderPerReol: number }): Observable<Lager> {
    return this.http.post<Lager>(`${API}/lagre`, data);
  }

  tilfoejReoler(lagerId: string, data: { antalReoler: number; hylderPerReol: number }): Observable<Lager> {
    return this.http.post<Lager>(`${API}/lagre/${lagerId}/reoler`, data);
  }

  getWhiskyProdukter(): Observable<WhiskyProdukt[]> {
    return this.http.get<WhiskyProdukt[]>(`${API}/whisky`);
  }

  opretWhiskyProdukt(navn: string): Observable<WhiskyProdukt> {
    return this.http.post<WhiskyProdukt>(`${API}/whisky`, { navn });
  }

  tapFad(whiskyId: string, fadId: string, medarbejder: string): Observable<WhiskyProdukt> {
    return this.http.post<WhiskyProdukt>(`${API}/whisky/${whiskyId}/tap`, { fadId, medarbejder });
  }

  tilfoejVand(whiskyId: string, liter: number): Observable<WhiskyProdukt> {
    return this.http.post<WhiskyProdukt>(`${API}/whisky/${whiskyId}/vand`, { liter });
  }

  opretFlasker(whiskyId: string): Observable<WhiskyProdukt> {
    return this.http.post<WhiskyProdukt>(`${API}/whisky/${whiskyId}/flasker`, {});
  }
}
