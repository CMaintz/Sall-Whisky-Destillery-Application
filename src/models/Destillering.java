package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Når en destillering af whisky foretages, skal det registreres i systemet.
//adminstrator indtaster detaljer om destilleringen, såsom startdato, slutdato, maltbatch, kornsort, medarbejder, mængde væske og alkholprocent.
//Systemet skal kunne vise en liste over alle destilleringer, og det skal være muligt at søge i listen.
public class Destillering {
    private LocalDate startDato;
    private LocalDate slutDato;
    private String maltBatch;
    private String kornSort;
    private String medarbejder;
    private double antalLiter;
    private double alkoholProcent;
    private String rygeMateriale;
    private String kommentar;
    private List<Fad> fade = new ArrayList<>();


    public Destillering(LocalDate startDato, LocalDate slutDato, String maltBatch, String kornSort, String medarbejder, double mængdeVæske, double alkoholProcent, String rygeMateriale, String kommentar) {
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.maltBatch = maltBatch;
        this.kornSort = kornSort;
        this.medarbejder = medarbejder;
        this.antalLiter = mængdeVæske;
        this.alkoholProcent = alkoholProcent;
        this.rygeMateriale = rygeMateriale;
        this.kommentar = kommentar;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public void setStartDato(LocalDate startDato) {
        this.startDato = startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }

    public String getMaltBatch() {
        return maltBatch;
    }

    public void setMaltBatch(String maltBatch) {
        this.maltBatch = maltBatch;
    }

    public String getKornSort() {
        return kornSort;
    }

    public void setKornSort(String kornSort) {
        this.kornSort = kornSort;
    }

    public String getMedarbejder() {
        return medarbejder;
    }

    public void setMedarbejder(String medarbejder) {
        this.medarbejder = medarbejder;
    }

    public double getAntalLiter() {
        return antalLiter;
    }

    public void setAntalLiter(double antalLiter) {
        this.antalLiter = antalLiter;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public void setAlkoholProcent(double alkoholProcent) {
        this.alkoholProcent = alkoholProcent;
    }

    public String getRygeMateriale() {
        return rygeMateriale;
    }

    public void setRygeMateriale(String rygeMateriale) {
        this.rygeMateriale = rygeMateriale;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public List<Fad> getFade() {
        return fade;
    }

    public void addFad(Fad fad) {
        this.fade.add(fad);
        fad.getDestilleringer().add(this);
    }
}
