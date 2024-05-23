package application.models;


import java.io.Serializable;

/**
 * The type Hylde.
 */
public class Hylde implements Serializable {
    private Fad fad;
    private int nummer;

    /**
     * Instantiates a new Hylde.
     *
     * @param nummer hyldenummeret
     */
    public Hylde(int nummer) {
        this.nummer = nummer;
    }


    /**
     * Placer fad på hylde
     * Pre: hylde indeholder ikke et fad
     *
     * @param fad faddet
     */
    public void placerFad(Fad fad) {
        this.fad = fad;
    }

    /**
     * Fjern fad boolean.
     *
     * @return the boolean
     */
    public boolean fjernFad() {
        this.fad = null;
        return true;
    }

    /**
     * Gets fad.
     *
     * @return the fad
     */
    public Fad getFad() {
        return fad;
    }

    @Override
    public String toString() {
        return "Hylde: " + nummer;
    }
}
