package application.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

//Når en destillering af whisky foretages, skal det registreres i systemet.
//adminstrator indtaster detaljer om destilleringen, såsom startdato, slutdato, maltbatch, kornsort, medarbejder, mængde væske og alkholprocent.
//Systemet skal kunne vise en liste over alle destilleringer, og det skal være muligt at søge i listen.
public class Destillering {
    private String maltBatch;
    private Korn korn;
    private String medarbejder;
    private double antalLiter;
    private double alkoholProcent;
    private String rygeMateriale;
    private String kommentar;
    private LocalDate startDato;
    private LocalDate slutDato;


    public Destillering(String maltBatch, Korn korn, String medarbejder, double antalLiter, double alkoholProcent, String rygeMateriale, String kommentar) {
        this.maltBatch = maltBatch;
        this.korn = korn;
        this.medarbejder = medarbejder;
        this.antalLiter = antalLiter;
        this.alkoholProcent = alkoholProcent;
        this.rygeMateriale = rygeMateriale;
        this.kommentar = kommentar;
        startDato = LocalDate.now();
    }

    public String getMaltBatch() {
        return maltBatch;
    }

    public Korn getKornSort() {
        return korn;
    }

    public String getMedarbejder() {
        return medarbejder;
    }

    public double getAntalLiter() {
        return antalLiter;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public String getRygeMateriale() {
        return rygeMateriale;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public void fjernAntalLiter(double antalLiterTappet) {
        this.antalLiter -= antalLiterTappet;
    }

    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }
    public int getDestilleringsTid() {
        return (int) startDato.until(slutDato, ChronoUnit.HOURS);
    }


    //toString
    @Override
    public String toString() {
        return "Destillering{" +
                "maltBatch='" + maltBatch + '\'' +
                ", korn=" + korn +
                ", medarbejder='" + medarbejder + '\'' +
                ", antalLiter=" + antalLiter +
                ", alkoholProcent=" + alkoholProcent +
                ", rygeMateriale='" + rygeMateriale + '\'' +
                ", kommentar='" + kommentar + '\'' +
                ", startDato=" + startDato;
    }
}
