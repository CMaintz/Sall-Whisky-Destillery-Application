package application.models;

public class FadHistorik {
    private String tidligereIndhold;
    private String land;
    private int fraÅr;
    private int tilÅr;
    private String leverandør;

    public FadHistorik(String tidligereIndhold, String land, int fraÅr, int tilÅr, String leverandør) {
        this.tidligereIndhold = tidligereIndhold;
        this.land = land;
        this.fraÅr = fraÅr;
        this.tilÅr = tilÅr;
        this.leverandør = leverandør;
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

    public int getFraÅr() {
        return fraÅr;
    }

    public void setFraÅr(int fraÅr) {
        this.fraÅr = fraÅr;
    }

    public int getTilÅr() {
        return tilÅr;
    }

    public void setTilÅr(int tilÅr) {
        this.tilÅr = tilÅr;
    }

    public String getLeverandør() {
        return leverandør;
    }

    public void setLeverandør(String leverandør) {
        this.leverandør = leverandør;
    }
}
