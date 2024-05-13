package application.models;

import java.util.ArrayList;
import java.util.List;

public class WhiskyProdukt {
    private String navn;
    private double alkoholProcent;
    private String beskrivelse;
    private String type;
    private ArrayList<FadTapning> fadTapninger = new ArrayList<>();
    private double antalLiterAlkohol;
    private double antalLiterVand;

    public WhiskyProdukt(String navn, ArrayList<FadTapning> fadTapninger, String beskrivelse, String type) {
        this.navn = navn;
        this.alkoholProcent = alkoholProcent;
        this.beskrivelse = beskrivelse;
        this.type = type;
        this.fadTapninger = fadTapninger;
        for (FadTapning fadTapning : fadTapninger) {
            antalLiterAlkohol += fadTapning.getLiterTappet();
        }
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
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
    private void setAlkoholprocent() {
        double result = 0;
        for (FadTapning fadTapning : fadTapninger) {
            result += fadTapning.getDestillat().getAlkoholprocent();
        }
        alkoholProcent = result / fadTapninger.size();
    }

    public void tilføjVand(int liter) {
        antalLiterVand = liter;
        alkoholProcent = (antalLiterAlkohol * alkoholProcent) / (antalLiterAlkohol + antalLiterVand);
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSamletAntalLiter() {
        return antalLiterAlkohol + antalLiterVand;
    }
}
