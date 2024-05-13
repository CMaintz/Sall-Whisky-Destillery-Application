package application.models;

public class FadTapning {
    private String medarbejderNavn;
    private double literTappet;
    private Fad fad;
    private Destillat destillat;

    public FadTapning(String medarbejderNavn, double literTappet, Fad fad) {
        this.medarbejderNavn = medarbejderNavn;
        this.fad = fad;
        destillat = fad.getDestillat();
        tapning(literTappet);
    }

    private void tapning(double literTappet) {
        if (fad.getDestillat().getAntalLiter() >= literTappet) {
            this.literTappet += literTappet;
            fad.getDestillat().setAntalLiter(literTappet);
        }
    }

    public Destillat getDestillat() {
        return destillat;
    }

    public double getLiterTappet() {
        return literTappet;
    }
}
