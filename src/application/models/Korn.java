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

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getMarkNavn() {
        return markNavn;
    }

    public void setMarkNavn(String markNavn) {
        this.markNavn = markNavn;
    }
}
