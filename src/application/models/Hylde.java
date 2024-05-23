package application.models;


import java.io.Serializable;

public class Hylde implements Serializable {
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

    public boolean fjernFad() {
        this.fad = null;
        return true;
    }

    public Fad getFad() {
        return fad;
    }
}
