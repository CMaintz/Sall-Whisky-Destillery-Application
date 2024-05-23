package application.models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The type Modnings historik.
 */
public class ModningsHistorik implements Serializable {
    private Fad fad;
    private LocalDate startDato;
    private LocalDate slutDato;

    /**
     * Instantiates a new Modnings historik.
     *
     * @param fad       the fad
     * @param startDato the start dato
     */
    ModningsHistorik(Fad fad, LocalDate startDato) {
        this.fad = fad;
        this.startDato = startDato;
        this.slutDato = null;
    }

    /**
     * Sets start dato.
     *
     * @param startDato the start dato
     */
    public void setStartDato(LocalDate startDato) {
        this.startDato = startDato;
    }

    /**
     * Gets fad.
     *
     * @return the fad
     */
    public Fad getFad() {
        return fad;
    }

    /**
     * Sets slut dato.
     *
     * @param slutDato the slut dato
     */
    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }

    /**
     * Gets start dato.
     *
     * @return the start dato
     */
    public LocalDate getStartDato() {
        return startDato;
    }

    /**
     * Gets slut dato.
     *
     * @return the slut dato
     */
    public LocalDate getSlutDato() {
        if (slutDato == null) {
            return LocalDate.now();
        }
        return slutDato;
    }
}
