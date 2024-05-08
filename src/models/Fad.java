package models;

import java.time.LocalDate;
import java.time.Period;

public class Fad {
    private static int fadNr;
    private String fadId;
    private int størrelse;
    private int alder;

//    private PåFyldning påFyldning?

//    private Historik historik;
//    private String fadType eller FadType fadType?

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

    public void setPåfyldning(Påfyldning påFyldning) {
        this.påFyldning = påFyldning;
    }

    public Påfyldning getPåFyldning() {
        return påFyldning;
    }

    public boolean erWhiskyKlar() {
        if (påFyldning != null) {
            Period period = Period.between(påFyldning.getPåfyldningsDato(), LocalDate.now());
            if (period.getYears() >= 3) {
                return true;
            }
        }
        return false;
    }
}
