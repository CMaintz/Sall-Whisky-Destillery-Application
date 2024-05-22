package application.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Fad implements Serializable {
    private static int antalFade;
    private String fadNr;
    private int literKapacitet;
    private Destillat destillat;
    private FadHistorik fadHistorik;

    public Fad(int literKapacitet) {
        this.literKapacitet = literKapacitet;
        antalFade++;
        this.fadNr = antalFade + "";
    }

    public FadHistorik createFadHistorik(String tidligereIndhold, String land, LocalDate fraÅr, LocalDate tilÅr, String leverandør) {
        FadHistorik fh = new FadHistorik(tidligereIndhold, land, fraÅr, tilÅr, leverandør);
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

    public void setDestillat(Destillat destillat) {
        this.destillat = destillat;
    }

    public Destillat addDestillat(Destillat destillat) {
        if (destillat != null) {
            this.fadHistorik.addDestillat(destillat);
            this.destillat = destillat;
            destillat.setFad(this);
        }
        return destillat;
    }

    public String getType() {
        return fadHistorik.getTidligereIndhold();
    }

    public Period getAlder() {
        return this.fadHistorik.getFraÅr().until(LocalDate.now());
    }

    public FadHistorik getFadHistorik() {
        return fadHistorik;
    }

    @Override
    public String toString() {
        return "#" + fadNr + " " + literKapacitet + "L " + getType();
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
