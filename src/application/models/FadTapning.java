package application.models;

public class FadTapning {
    private String medarbejderNavn;
    private int literTappet;
    private Fad fad;
    private Destillat destillat;

    public FadTapning(String medarbejderNavn, int literTappet, Fad fad) {
        this.medarbejderNavn = medarbejderNavn;
        this.literTappet = literTappet;
        this.fad = fad;
        destillat = fad.getDestillat();
    }

}
