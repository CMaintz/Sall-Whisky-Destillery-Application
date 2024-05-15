package application.models;


public class Hylde {
    private Fad fad;
    private int nummer;

    public Hylde(int nummer) {
        this.nummer = nummer;
    }

//    PRE: der er ikke et fad på hylden allerede, dvs. pre: fad == null
//    Eller måske er det ikke nødvendigt..? Man kan kun se hylder der er tomme..?

    public void placerFad(Fad fad) {
        this.fad = fad;
    }

    public void fjernFad() {
        this.fad = null;
    }

    public Fad getFad() {
        return fad;
    }
}
