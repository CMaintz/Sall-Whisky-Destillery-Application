package application.models;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Type Whisky produkt.
 */
public class WhiskyProdukt implements Serializable {
    private String navn;
    private double alkoholprocent;
    private final List<FadTapning> fadTapninger;
    private final List<WhiskyFlaske> fyldteFlasker;
    private double literVandTilføjet;
    private double antalLiter;

    /**
     * Instantiates a new Whisky produkt.
     *
     * @param navn navn
     */
    public WhiskyProdukt(String navn) {
        this.navn = navn;
        this.antalLiter = 0;
        this.literVandTilføjet = 0;
        this.fadTapninger = new ArrayList<>();
        this.fyldteFlasker = new ArrayList<>();
    }

    /**
     * Create fad tapning.
     *
     * @param medarbejderNavn medarbejder navn
     * @param literTappet     liter tappet
     * @param fad             fad
     * @return fad tapning
     */
    public FadTapning createFadTapning(String medarbejderNavn, double literTappet, Fad fad) {
        FadTapning ft = new FadTapning(medarbejderNavn, literTappet, fad);
        fadTapninger.add(ft);
        antalLiter += literTappet;
        udregnAlkoholprocent();
        return ft;
    }

    /**
     * Add fad tapning.
     *
     * @param fadTapning fad tapning
     */
    public void addFadTapning(FadTapning fadTapning) {
        if (!fadTapninger.contains(fadTapning)) {
            this.fadTapninger.add(fadTapning);
            antalLiter += fadTapning.getLiterTappet();
        }
    }


    /**
     * Sets antal liter.
     *
     * @param liter liter
     */
    public void setAntalLiter(int liter) {
        this.antalLiter = liter;
    }

    /**
     * Create whisky flaske whisky flaske.
     *
     * @param produktHistorie produkt historie
     * @return whisky flaske
     */
    public WhiskyFlaske createWhiskyFlaske(String produktHistorie) {
        String hist = navn + "\n" + "Flaske #" + (fyldteFlasker.size() + 1) + " af " + ((int) antalLiter) + "\n" + "Flaskningsdato: " + LocalDate.now() + "\n" + produktHistorie;
        WhiskyFlaske flaske = new WhiskyFlaske(fyldteFlasker.size() + 1, this, hist);
        fyldteFlasker.add(flaske);
        return flaske;
    }

    /**
     * Gets fyldte flasker.
     *
     * @return fyldte flasker
     */
    public List<WhiskyFlaske> getFyldteFlasker() {
        return new ArrayList<>(fyldteFlasker);
    }

    /**
     * Gets navn.
     *
     * @return navn
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Gets alkoholprocent.
     *
     * @return alkoholprocent
     */
    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    private void udregnAlkoholprocent() {
        int literEthanol = 0;
        for (FadTapning fadTapning : fadTapninger) {
            literEthanol += (fadTapning.getDestillat().getAlkoholprocent() / 100) * fadTapning.getLiterTappet();
        }
        alkoholprocent = ((literEthanol / antalLiter) * 100);
    }

    /**
     * Tilføj vand.
     *
     * @param literVand liter vand
     */
    public void tilføjVand(int literVand) {
        this.literVandTilføjet += literVand;
        antalLiter += literVand;
        udregnAlkoholprocent();
    }

    /**
     * Gets antal liter.
     *
     * @return antal liter
     */
    public double getAntalLiter() {
        return antalLiter;
    }

    /**
     * Whisky type string.
     *
     * @return string
     */
    public String whiskyType() {
        if (fadTapninger.size() == 1 && fadTapninger.get(0).getDestillat().getModningsHistorik().size() == 1) {
            return literVandTilføjet == 0 ? "Cask Strength" : "Single Cask";
        }
        return "Single Malt";
    }

    /**
     * Get liter vand tilføjet double.
     *
     * @return double
     */
    public double getLiterVandTilføjet() {
        return literVandTilføjet;
    }

    /**
     * Generer historie string.
     *
     * @return string
     */
    public String genererHistorie() {
        DecimalFormat df = new DecimalFormat("#.00");
        StringBuilder sb = new StringBuilder();
        sb.append("\nSkabt af egne hænder med Lars' økologiske ");

        sb.append(String.join("\n", historieKorn()) + "\n");
        sb.append("Sået og høstet fra den jyske muld på Lars' marker ");
        sb.append(String.join("\n", historieMarker()) + "\n");
        sb.append("Mæsket ved håndkraft og fermenteret i " + historieDestilleringstid());
        sb.append("\nDobbeltdestilleret langtsomt i direct fired kobber pot stills.");
        sb.append("\nModnet i " + historieModningstid());
        sb.append(" år i omhyggeligt udvalgte ex-" + fadTapninger.get(0).getDestillat().getFad().getFadHistorik().getTidligereIndhold()
                + " barrels.\n\n");
        String rygemateriale = fadTapninger.get(0).getDestillat().getPåfyldninger().get(0).getDestillering().getRygemateriale();
        sb.append(rygemateriale != null ? (rygemateriale + ", Økologisk ") : ("Økologisk "));
        sb.append(whiskyType() + "\n& Single Farm Whisky" + "\n 100cl. " + df.format(alkoholprocent) + "% Vol.");

        return sb.toString();
    }

    /**
     * Retrieves a list of unique strings representing the names of the fields used in the whisky production process.
     *
     * @return a list of strings containing the names of the fields used in the whisky production process
     */
    private List<String> historieMarker() {
        List<String> toReturn = new ArrayList<>();
        for (FadTapning ft : fadTapninger) {
            for (Påfyldning pf : ft.getDestillat().getPåfyldninger()) {
                String temp = pf.getDestillering().getKornSort().getMarkNavne();
                if (!toReturn.contains(temp)) {
                    toReturn.add(temp);
                }
            }
        }
        return toReturn;
    }

    /**
     * Retrieves a list of unique strings representing the variant and sort of each corn used in the whisky production process.
     *
     * @return a list of strings containing the variant and sort of each corn used in the whisky production process
     */
    private List<String> historieKorn() {
        List<String> toReturn = new ArrayList<>();

        for (FadTapning ft : fadTapninger) {
            for (Påfyldning pf : ft.getDestillat().getPåfyldninger()) {
                String temp = pf.getDestillering().getKornSort().getVariant() + " " + pf.getDestillering().getKornSort().getSort();
                if (!toReturn.contains(temp)) {
                    toReturn.add(temp);
                }
            }
        }
        return toReturn;
    }

    /**
     * Calculates the number of months between the date of bottling and the last modification date of the first destillation in the whiskyProdukt.
     *
     * @return a string representing the number of years. If the number of years is less than 12, returns the corresponding Danish word.
     */
    private String historieModningstid() {
        Period måneder = null;
        Destillat destillat = fadTapninger.get(0).getDestillat();
        måneder = Period.between(destillat.getPåfyldningsDato(), destillat.getModningsHistorik().get(destillat.getModningsHistorik().size() - 1).getSlutDato().plusDays(1));
        String[] tal = new String[]{"nul", "et", "to", "tre", "fire", "fem", "seks", "syv", "otte", "ni", "ti", "elleve", "tolv", "tretten", "fjorten", "femten", "seksten", "sytten", "atten", "nitten", "tyve"};
        return tal[måneder.getYears()];
    }

    /**
     * Calculates the range of destilleringstid (destillation time) for all the fadTapninger in the whiskyProdukt.
     *
     * @return a string representing the range of destilleringstid in hours. If the lowest and highest values are the same,
     * returns the value followed by "timer". Otherwise, returns the lowest value followed by "til" and the highest value,
     * both followed by "timer".
     */
    private String historieDestilleringstid() {
        long lavestAntalTimer = fadTapninger.get(0).getDestillat().getPåfyldninger().get(0).getDestillering().getDestilleringsTid();
        long højesteAntalTimer = lavestAntalTimer;
        for (FadTapning ft : fadTapninger) {
            for (Påfyldning pf : ft.getDestillat().getPåfyldninger()) {
                long tempTimer = pf.getDestillering().getDestilleringsTid();
                if (tempTimer > højesteAntalTimer) {
                    højesteAntalTimer = tempTimer;
                } else if (tempTimer < lavestAntalTimer) {
                    lavestAntalTimer = tempTimer;
                }
            }
        }
        return lavestAntalTimer == højesteAntalTimer ? lavestAntalTimer + " timer" : lavestAntalTimer + " til " + højesteAntalTimer + " timer";
    }

    /**
     * Gets detaljer.
     *
     * @return detaljer
     */
    public String getDetaljer() {
        StringBuilder sb = new StringBuilder();
        sb.append("Destillater: \n");
        for (FadTapning ft : fadTapninger) {
            sb.append(ft.getDestillat().getDetaljer());
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        DecimalFormat numberFormatter = new DecimalFormat("#.00");
        return navn + " " + numberFormatter.format(alkoholprocent) + "% Vol.";
    }
}
