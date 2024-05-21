package application.models;

public class Korn {
    private String sort;
    private String variant;
    private String markNavn;

    public Korn(String sort, String variant, String markNavn) {
        this.sort = sort;
        this.variant = variant;
        this.markNavn = markNavn;
    }

    public String getSort() {
        return sort;
    }

    public String getVariant() {
        return variant;
    }

    public String getMarkNavne() {
        return markNavn;
    }

    public String toString() {
        return sort + " - " + variant + " - " + markNavn;
    }

}
