package application.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Fad {
    private static int antalFade;
    private String fadNr;
    private int literKapacitet;
    private Hylde hylde;
    private Destillat destillat;
    private FadHistorik fadHistorik;

    public Fad(int literKapacitet) {
        this.literKapacitet = literKapacitet;
        antalFade++;
        this.fadNr = antalFade + "";
    }

    public FadHistorik createFadHistorik(String tidligereIndhold, String land, LocalDate fraÅr, String leverandør){
        FadHistorik fh = new FadHistorik(tidligereIndhold, land, fraÅr, leverandør);
        this.fadHistorik = fh;
        return fh;
    }
    public String getFadNr() {
        return fadNr;
    }

    public int getLiterKapacitet() {
        return literKapacitet;
    }

    public int getAlderMåneder() {
        return (int) fadHistorik.getFraÅr().until(LocalDate.now(), ChronoUnit.MONTHS);
    }

    public Destillat getDestillat() {
        return destillat;
    }

    public void setHylde(Hylde hylde) {
        this.hylde = hylde;
    }

    public void setDestillat(Destillat destillat) {
        this.destillat = destillat;
    }

    public Destillat addDestillat(Destillat destillat) {
        if (destillat == null) {
//            fadHistorik.addDestillatHistorik(destillat);
            this.destillat = destillat;
            destillat.setFad(this);
        }
        return destillat;
    }

    public FadHistorik getFadHistorik() {
        return fadHistorik;
    }

//    public boolean erWhiskyKlar() {
//        if (påFyldning != null) {
//            Period period = Period.between(påFyldning.getPåfyldningsDato(), LocalDate.now());
//            if (period.getYears() >= 3) {
//                return true;
//            }
//        }
//        return false;
//    }
}
