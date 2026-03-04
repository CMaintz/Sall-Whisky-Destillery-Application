export interface Fad {
  id: string;
  fadNummer: string;
  literKapacitet: number;
  tidligereIndhold: string;
  land: string;
  fraAar: string;
  leverandoer: string;
  alderAar: number;
  destillat: DestillatSummary | null;
  hylde: HyldeInfo | null;
  erKlar: boolean;
}

export interface DestillatSummary {
  id: string;
  antalLiter: number;
  alkoholProcent: number;
  startDato: string;
  erKlar: boolean;
  modningsHistorik: ModningsHistorikItem[];
}

export interface ModningsHistorikItem {
  fadNummer: string;
  tidligereIndhold: string;
  paafyldningsDato: string;
  slutDato: string | null;  // null = currently in this barrel
}

export interface HyldeInfo {
  hyldeId: string;
  position: number;
  reolNummer: number;
  lagerNavn: string;
}

export interface Destillering {
  id: string;
  batchNummer: number;
  maltBatch: string;
  korn: KornSummary;
  medarbejder: string;
  antalLiter: number;
  alkoholProcent: number;
  rygemateriale: string;
  kommentar: string;
  startTidspunkt: string;
  slutTidspunkt: string;
  destilleringsTidTimer: number;
}

export interface KornSummary {
  id: string;
  sort: string;
  variant: string;
  markNavne: string;
}

export interface Korn {
  id: string;
  sort: string;
  variant: string;
  markNavne: string;
}

export interface WhiskyProdukt {
  id: string;
  navn: string;
  alkoholProcent: number;
  antalLiter: number;
  literVandTilfojet: number;
  whiskyType: string;
  antalFlasker: number;
  fadTapninger: FadTapningSummary[];
  flasker: WhiskyFlaskeSummary[];
}

export interface WhiskyFlaskeSummary {
  id: string;
  flaskeNummer: number;
  produktHistorie: string;
  flaskningsDato: string;
}

export interface FadTapningSummary {
  fadId: string;
  fadNummer: string;
  tidligereIndhold: string;
  literTappet: number;
  tapningsDato: string;
}

export interface Lager {
  id: string;
  navn: string;
  reoler: Reol[];
}

export interface Reol {
  id: string;
  reolNummer: number;
  hylder: Hylde[];
}

export interface Hylde {
  id: string;
  position: number;
  erLedig: boolean;
  fad: FadSummary | null;
}

export interface FadSummary {
  id: string;
  fadNummer: string;
  literKapacitet: number;
  tidligereIndhold: string;
  harDestillat: boolean;
  erKlar: boolean;
}
