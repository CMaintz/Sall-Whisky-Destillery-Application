package application.models;

import java.util.ArrayList;
import java.util.List;

public class WhiskyProdukt {
    private String navn;
    private int flaskeNr;
    private double alkoholProcent;
    private String beskrivelse;
    private String type;
    private ArrayList<FadTapning> fadTapninger = new ArrayList<>();

    public WhiskyProdukt(String navn, int flaskeNr, ArrayList<FadTapning> fadTapninger, double alkoholProcent, String beskrivelse, String type) {
        this.navn = navn;
        this.flaskeNr = flaskeNr;
        this.alkoholProcent = alkoholProcent;
        this.beskrivelse = beskrivelse;
        this.type = type;
        this.fadTapninger = fadTapninger;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getFlaskeNr() {
        return flaskeNr;
    }

    public void setFlaskeNr(int flaskeNr) {
        this.flaskeNr = flaskeNr;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public void setAlkoholProcent(double alkoholProcent) {
        this.alkoholProcent = alkoholProcent;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
