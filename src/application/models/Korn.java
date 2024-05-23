package application.models;

import java.io.Serializable;

/**
 * The type Korn.
 */
public class Korn implements Serializable {
    private String sort;
    private String variant;
    private String markNavn;

    /**
     * Instantiates a new Korn.
     *
     * @param sort     the sort
     * @param variant  the variant
     * @param markNavn the mark navn
     */
    public Korn(String sort, String variant, String markNavn) {
        this.sort = sort;
        this.variant = variant;
        this.markNavn = markNavn;
    }

    /**
     * Gets sort.
     *
     * @return the sort
     */
    public String getSort() {
        return sort;
    }

    /**
     * Gets variant.
     *
     * @return the variant
     */
    public String getVariant() {
        return variant;
    }

    /**
     * Gets mark navne.
     *
     * @return the mark navne
     */
    public String getMarkNavne() {
        return markNavn;
    }

    public String toString() {
        return sort + " - " + variant + " - " + markNavn;
    }

}
