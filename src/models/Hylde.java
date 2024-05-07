package models;

public class Hylde {
    private Fad fad;
    private int nummer;

    public Hylde(int nummer) {
        this.nummer = nummer;
    }

    public void setFad(Fad fad) {
        this.fad = fad;
    }

    public Fad getFad() {
        return fad;
    }
}
