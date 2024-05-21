package application.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FadHistorik implements Serializable {
    private String tidligereIndhold;
    private String land;
    private LocalDate fraÅr;
    private LocalDate tilÅr;
    private String leverandør;
    private List<Destillat> tidligereDestillater;

    FadHistorik(String tidligereIndhold, String land, LocalDate fraÅr, String leverandør) {
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

    public void addDestillat(Destillat destillat) {
        if (!tidligereDestillater.contains(destillat)) {
            tidligereDestillater.add(destillat);
        }
    }


    @Override
    public String toString() {
        return "fadtype: " + tidligereIndhold + "\nland: " + land
                + "\nfra " + fraÅr + "\ntil " + tilÅr + "\nleverandør: " + leverandør;
    }
}
