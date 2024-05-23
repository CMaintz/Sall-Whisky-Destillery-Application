package application.models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The type Modnings historik.
 */
public class ModningsHistorik implements Serializable {
    private Fad fad;
    private LocalDate påfyldningsDato;
    private LocalDate slutDato;

    /**
     * Instantiates a new Modnings historik.
     *
     * @param fad       the fad
     * @param påfyldningsDato the start dato
     */
    ModningsHistorik(Fad fad, LocalDate påfyldningsDato) {
        this.fad = fad;
        this.påfyldningsDato = påfyldningsDato;
        this.slutDato = null;
    }

    /**
     * Sets start dato.
     *
     * @param påfyldningsDato the start dato
     */
    public void setPåfyldningsDato(LocalDate påfyldningsDato) {
        this.påfyldningsDato = påfyldningsDato;
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
    public LocalDate getPåfyldningsDato() {
        return påfyldningsDato;
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
