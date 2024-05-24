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
     * @param sort     sort
     * @param variant  variant
     * @param markNavn mark navn
     */
    public Korn(String sort, String variant, String markNavn) {
        this.sort = sort;
        this.variant = variant;
        this.markNavn = markNavn;
    }

    /**
     * Gets sort.
     *
     * @return sort
     */
    public String getSort() {
        return sort;
    }

    /**
     * Gets variant.
     *
     * @return variant
     */
    public String getVariant() {
        return variant;
    }

    /**
     * Gets mark navne.
     *
     * @return mark navne
     */
    public String getMarkNavne() {
        return markNavn;
    }

    public String toString() {
        return sort + " - " + variant + " - " + markNavn;
    }

}
