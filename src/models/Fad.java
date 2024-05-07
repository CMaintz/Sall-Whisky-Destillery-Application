package models;

public class Fad {
    private static int fadNr;
    private String fadId;
    private int størrelse;
    private int alder;
    private Påfyldning påFyldning;

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


}
