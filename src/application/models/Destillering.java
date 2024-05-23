package application.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

//Når en destillering af whisky foretages, skal det registreres i systemet.
//adminstrator indtaster detaljer om destilleringen, såsom startdato, slutdato, maltbatch, kornsort, medarbejder, mængde væske og alkholprocent.
//Systemet skal kunne vise en liste over alle destilleringer, og det skal være muligt at søge i listen.
public class Destillering implements Serializable {
    private static int antalDestilleringer;
    private int newSpiritbatchNr;
    private String maltBatch;
    private Korn korn;
    private String medarbejder;
    private double antalLiter;
    private double alkoholProcent;
    private String rygeMateriale;
    private String kommentar;
    private LocalDateTime startDato;
    private LocalDateTime slutDato;


    public Destillering(String maltBatch, Korn korn, String medarbejder, double antalLiter, double alkoholProcent, String rygeMateriale, String kommentar) {
        antalDestilleringer++;
        this.newSpiritbatchNr = antalDestilleringer;
        this.maltBatch = maltBatch;
        this.korn = korn;
        this.medarbejder = medarbejder;
        this.antalLiter = antalLiter;
        this.alkoholProcent = alkoholProcent;
        this.rygeMateriale = rygeMateriale;
        this.kommentar = kommentar;
        startDato = LocalDateTime.now();
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

    public void setSlutDato(LocalDateTime slutDato) {
        this.slutDato = slutDato;
    }

    public void setStartDato(LocalDateTime startDato) {
        this.startDato = startDato;
    }

    public LocalDateTime getSlutDato() {
        return slutDato;
    }

    public long getDestilleringsTid() {
        return startDato.until(slutDato, ChronoUnit.HOURS) + 1;
    }

    public String getDetaljer() {
        String toReturn = "Maltbatch: " + maltBatch + "\nKorn: " + korn + "\nmedarbejder: " + medarbejder + "\n" + antalLiter + "L, " + alkoholProcent + "% Vol.";
        if (rygeMateriale != null) {
            toReturn += ", rygemateriale: " + rygeMateriale;
        }
        if (kommentar != null) {
            toReturn += "\nkommentar:" + kommentar;
        }
    return toReturn;
    }

    @Override
    public String toString() {
        return maltBatch + ", " + korn + ", " + medarbejder + ", " + antalLiter + ", " + alkoholProcent + ", " + rygeMateriale + ", " + kommentar;
    }
}
