package application.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import application.models.Påfyldning;

public class Fad {
    private static int fadNr;
    private String fadId;
    private int størrelse;
    private int alder;
    private Hylde hylde;
    private Destillat destillat;
    private FadHistorik fadHistorik;


    public Fad(int størrelse, int alder, FadHistorik fadHistorik) {
        this.størrelse = størrelse;
        this.alder = alder;
        this.fadHistorik = fadHistorik;
        fadNr++;
        this.fadId = fadNr + "";
    }

    public String getFadId() {
        return fadId;
    }

    public int getStørrelse() {
        return størrelse;
    }

    public int getAlder() {
        return alder;
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
            fadHistorik.addDestillatHistorik(destillat);
            this.destillat = destillat;
            destillat.setFad(this);
        }
        return destillat;
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
