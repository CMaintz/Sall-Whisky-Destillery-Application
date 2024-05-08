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
    private Destillat destillat;
    private ArrayList<Destillat> historik = new ArrayList<>();


    public Fad(int størrelse, int alder) {
        this.størrelse = størrelse;
        this.alder = alder;
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

    public Destillat createDestillat(ArrayList<Påfyldning> påfyldninger, String navn) {
        if (destillat != null) {
            historik.add(destillat);
        }
        destillat = new Destillat(påfyldninger, navn);
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
