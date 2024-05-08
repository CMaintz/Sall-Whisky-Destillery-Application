package application.models;

import java.util.ArrayList;
import java.util.List;

public class WhiskyProdukt {
    private String navn;
    private int flaskeNr;
    private Destillering destillering;
    private double alkoholProcent;
    private String beskrivelse;
    private String type;
    private List<Fad> fade = new ArrayList<>();

    public WhiskyProdukt(String navn, int flaskeNr, Destillering destillering, double alkoholProcent, String beskrivelse, String type) {
        this.navn = navn;
        this.flaskeNr = flaskeNr;
        this.destillering = destillering;
        this.alkoholProcent = alkoholProcent;
        this.beskrivelse = beskrivelse;
        this.type = type;
    }

    public List<Fad> getFade() {
        return fade;
    }

    public void addFad(Fad fad) {
        this.fade.add(fad);
//        fad.getWhiskyProdukter().add(this);
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

    public Destillering getDestillering() {
        return destillering;
    }

    public void setDestillering(Destillering destillering) {
        this.destillering = destillering;
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
