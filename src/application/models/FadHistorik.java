package application.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class FadHistorik {
    private String tidligereIndhold;
    private String land;
    private LocalDate fraÅr;
//   TODO Måske en antalÅrBrugt i stedet for?
    private String leverandør;
    private List<Destillat> tidligereDestillater;

    FadHistorik(String tidligereIndhold, String land, LocalDate fraÅr, String leverandør) {
//        TODO skal man kunne indlæse historik fra en tekstfil?
        this.tidligereIndhold = tidligereIndhold;
        this.land = land;
        this.fraÅr = fraÅr;
        this.leverandør = leverandør;
        this.tidligereDestillater = new ArrayList<>();
    }

    public String getTidligereIndhold() {
        return tidligereIndhold;
    }

    public void setTidligereIndhold(String tidligereIndhold) {
        this.tidligereIndhold = tidligereIndhold;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public LocalDate getFraÅr() {
        return fraÅr;
    }


    public String getLeverandør() {
        return leverandør;
    }

    public void setLeverandør(String leverandør) {
        this.leverandør = leverandør;
    }

    public List<Destillat> getTidligereDestillater() {
        return tidligereDestillater;
    }

    public void addTidligereDestillat(Destillat destillat) {
        if (!tidligereDestillater.contains(destillat)) {
            tidligereDestillater.add(destillat);
        }
    }


    @Override
    public String toString() {
        return "FadHistorik{" +
                "tidligereIndhold='" + tidligereIndhold + '\'' +
                ", land='" + land + '\'' +
                ", fraÅr=" + fraÅr +
                ", leverandør='" + leverandør + '\'' +
                '}';
    }
}
