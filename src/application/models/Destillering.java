package application.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import application.models.Fad;

//Når en destillering af whisky foretages, skal det registreres i systemet.
//adminstrator indtaster detaljer om destilleringen, såsom startdato, slutdato, maltbatch, kornsort, medarbejder, mængde væske og alkholprocent.
//Systemet skal kunne vise en liste over alle destilleringer, og det skal være muligt at søge i listen.
public class Destillering {
    private String maltBatch;
    private Korn korn;
    private String medarbejder;
    private double antalLiter;
    private double currentAntalLiter;
    private double alkoholProcent;
    private String rygeMateriale;
    private String kommentar;


    public Destillering(String maltBatch, Korn korn, String medarbejder, double mængdeVæske, double alkoholProcent, String rygeMateriale, String kommentar) {
        this.maltBatch = maltBatch;
        this.korn = korn;
        this.medarbejder = medarbejder;
        this.antalLiter = mængdeVæske;
        this.alkoholProcent = alkoholProcent;
        this.rygeMateriale = rygeMateriale;
        this.kommentar = kommentar;
        currentAntalLiter = antalLiter;
    }

    public String getMaltBatch() {
        return maltBatch;
    }

    public void setMaltBatch(String maltBatch) {
        this.maltBatch = maltBatch;
    }

    public Korn getKornSort() {
        return korn;
    }

    public void setKornSort(Korn korn) {
        this.korn = korn;
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

    public void setCurrentAntalLiter(double antalLiterTappet) {
        currentAntalLiter -= antalLiterTappet;
    }

    public double getCurrentAntalLiter() {
        return currentAntalLiter;
    }
}
