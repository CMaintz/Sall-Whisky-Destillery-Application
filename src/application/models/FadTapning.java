package application.models;

public class FadTapning {
    private String medarbejderNavn;
    private double literTappet;
    private Fad fad;
    private Destillat destillat;

//    TODO: Burde ikke have tapning metoden; vi bør slet ikke kunne oprette
//     en tapning hvis der ikke er liter til det i fadet
    public FadTapning(String medarbejderNavn, double literTappet, Fad fad) {
        this.medarbejderNavn = medarbejderNavn;
        this.fad = fad;
        destillat = fad.getDestillat();
        tapning(literTappet);
    }

    private void tapning(double literTappet) {
        if (fad.getDestillat().getAntalLiter() >= literTappet) {
            this.literTappet = literTappet;
            fad.getDestillat().fjernAntalLiter(literTappet);
        }
    }

    public Destillat getDestillat() {
        return destillat;
    }

    public double getLiterTappet() {
        return literTappet;
    }

}
