package application.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * The type Destillering.
 */
//Når en destillering af whisky foretages, skal det registreres i systemet.
//adminstrator indtaster detaljer om destilleringen, såsom startdato, slutdato, maltbatch, kornsort, medarbejder, mængde væske og alkholprocent.
//Systemet skal kunne vise en liste over alle destilleringer, og det skal være muligt at søge i listen.
public class Destillering implements Serializable {
    private static int antalDestilleringer = 0;
    private int newSpiritbatchNr;
    private String maltBatch;
    private Korn korn;
    private String medarbejderNavn;
    private double antalLiter;
    private double alkoholProcent;
    private String rygemateriale;
    private String kommentar;
    private LocalDateTime startTidspunkt;
    private LocalDateTime slutTidspunkt;


    /**
     * Instantiates a new Destillering.
     *
     * @param maltBatch      the malt batch
     * @param korn           the korn
     * @param medarbejderNavn    the medarbejder
     * @param antalLiter     the antal liter
     * @param alkoholProcent the alkohol procent
     * @param rygemateriale  the ryge materiale
     * @param kommentar      the kommentar
     */
    public Destillering(String maltBatch, Korn korn, String medarbejderNavn, double antalLiter, double alkoholProcent, String rygemateriale, String kommentar) {
        antalDestilleringer++;
        this.newSpiritbatchNr = antalDestilleringer;
        this.maltBatch = maltBatch;
        this.korn = korn;
        this.medarbejderNavn = medarbejderNavn;
        this.antalLiter = antalLiter;
        this.alkoholProcent = alkoholProcent;
        this.rygemateriale = rygemateriale;
        this.kommentar = kommentar;
        startTidspunkt = LocalDateTime.now();
    }

    /**
     * Gets antal destilleringer.
     *
     * @return the antal destilleringer
     */
    public static int getAntalDestilleringer() {
        return antalDestilleringer;
    }

    /**
     * Sets antal destilleringer.
     *
     * @param antalDestilleringer the antal destilleringer
     */
    public static void setAntalDestilleringer(int antalDestilleringer) {
        Destillering.antalDestilleringer = antalDestilleringer;
    }

    /**
     * Gets malt batch.
     *
     * @return the malt batch
     */
    public String getMaltBatch() {
        return maltBatch;
    }

    /**
     * Gets korn sort.
     *
     * @return the korn sort
     */
    public Korn getKornSort() {
        return korn;
    }

    /**
     * Gets medarbejder.
     *
     * @return the medarbejder
     */
    public String getMedarbejderNavn() {
        return medarbejderNavn;
    }

    /**
     * Gets antal liter.
     *
     * @return the antal liter
     */
    public double getAntalLiter() {
        return antalLiter;
    }

    /**
     * Gets alkohol procent.
     *
     * @return the alkohol procent
     */
    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    /**
     * Gets ryge materiale.
     *
     * @return the ryge materiale
     */
    public String getRygemateriale() {
        return rygemateriale;
    }

    /**
     * Gets kommentar.
     *
     * @return the kommentar
     */
    public String getKommentar() {
        return kommentar;
    }

    /**
     * Sets kommentar.
     *
     * @param kommentar the kommentar
     */
    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    /**
     * Fjern antal liter.
     *
     * @param antalLiterTappet the antal liter tappet
     */
    public void fjernAntalLiter(double antalLiterTappet) {
        this.antalLiter -= antalLiterTappet;
    }

    /**
     * Sets slut dato.
     *
     * @param slutTidspunkt the slut dato
     */
    public void setSlutTidspunkt(LocalDateTime slutTidspunkt) {
        this.slutTidspunkt = slutTidspunkt;
    }

    /**
     * Sets start dato.
     *
     * @param startTidspunkt the start dato
     */
    public void setStartTidspunkt(LocalDateTime startTidspunkt) {
        this.startTidspunkt = startTidspunkt;
    }

    /**
     * Gets slut dato.
     *
     * @return the slut dato
     */
    public LocalDateTime getSlutTidspunkt() {
        return slutTidspunkt;
    }

    /**
     * Gets destillerings tid.
     *
     * @return the destillerings tid
     */
    public long getDestilleringsTid() {
        return startTidspunkt.until(slutTidspunkt, ChronoUnit.HOURS) + 1;
    }

    /**
     * Gets detaljer.
     *
     * @return the detaljer
     */
    public String getDetaljer() {
        String toReturn = "New spiritbatch nr: " + newSpiritbatchNr + "\nMaltbatch: " + maltBatch + "\nKorn: " + korn + "\nmedarbejder: " + medarbejderNavn + "\n" + antalLiter + "L, " + alkoholProcent + "% Vol.";
        if (rygemateriale != null) {
            toReturn += ", rygemateriale: " + rygemateriale;
        }
        if (kommentar != null) {
            toReturn += "\nkommentar:" + kommentar;
        }
    return toReturn;
    }

    @Override
    public String toString() {
        return newSpiritbatchNr + ", " + maltBatch + ", " + korn + ", " + medarbejderNavn + ", " + antalLiter + ", " + alkoholProcent + ", " + rygemateriale + ", " + kommentar;
    }
}
