package application.models;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class WhiskyProdukt implements Serializable {
    private String navn;
    private double alkoholProcent;
    private final List<FadTapning> fadTapninger;
    private final List<WhiskyFlaske> fyldteFlasker;
    private double literVandTilføjet;
    private double antalLiter;

    public WhiskyProdukt(String navn) {
        this.navn = navn;
        this.antalLiter = 0;
        this.literVandTilføjet = 0;
        this.fadTapninger = new ArrayList<>();
        this.fyldteFlasker = new ArrayList<>();
    }

    public FadTapning createFadTapning(String medarbejderNavn, double literTappet, Fad fad) {
        FadTapning ft = new FadTapning(medarbejderNavn, literTappet, fad);
        fadTapninger.add(ft);
        antalLiter += literTappet;
        udregnAlkoholprocent();
        return ft;
    }

    public void addFadTapning(FadTapning fadTapning) {
        if (!fadTapninger.contains(fadTapning)) {
            this.fadTapninger.add(fadTapning);
            antalLiter += fadTapning.getLiterTappet();
        }
    }


    public void setAntalLiter(int liter) {
        this.antalLiter = liter;
    }

    public WhiskyFlaske createWhiskyFlaske(String produktHistorie) {
        String hist = navn + "\n" + "Flaske #" + (fyldteFlasker.size() + 1) + " af " + ((int) antalLiter) + "\n" + produktHistorie;
        WhiskyFlaske flaske = new WhiskyFlaske(fyldteFlasker.size() + 1, this, hist);
        fyldteFlasker.add(flaske);
        return flaske;
    }

    public List<WhiskyFlaske> getFyldteFlasker() {
        return new ArrayList<>(fyldteFlasker);
    }

    public String getNavn() {
        return navn;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    private void udregnAlkoholprocent() {
        int literEthanol = 0;
        for (FadTapning fadTapning : fadTapninger) {
            literEthanol += (fadTapning.getDestillat().getAlkoholprocent() / 100) * fadTapning.getLiterTappet();
        }
        alkoholProcent = ((literEthanol / antalLiter) * 100);
    }

    public void tilføjVand(int literVand) {
        this.literVandTilføjet += literVand;
        antalLiter += literVand;
        udregnAlkoholprocent();
    }

    public String whiskyType() {
        if (fadTapninger.size() == 1 && fadTapninger.get(0).getDestillat().getModningsHistorik().size() == 1) {
            return literVandTilføjet == 0 ? "Cask Strength" : "Single Cask";
        } else {
            return "Single Malt";
        }
    }

    public double getAntalLiter() {
        return antalLiter;
    }

    public double getLiterVandTilføjet() {
        return literVandTilføjet;
    }

    public String genererHistorie() {
        DecimalFormat df = new DecimalFormat("#.00");
        StringBuilder sb = new StringBuilder();
        sb.append("\nSkabt af egne hænder med Lars' økologiske ");
        ArrayList<String> korn = new ArrayList<>(historieKorn());
        for (String s : korn) {
            sb.append(s + "\n");
        }
        sb.append("Sået og høstet fra den jyske muld på Lars' marker ");
        ArrayList<String> marker = new ArrayList<>(historieMarker());
        for (String s : marker) {
            sb.append(s + "\n");
        }
        sb.append("Mæsket ved håndkraft og fermenteret i " + historieDestilleringstid());
        sb.append("\nDobbeltdestilleret langtsomt i direct fired kobber pot stills.");
        sb.append("\nModnet i " + historieModningstid());
        sb.append(" år i omhyggeligt udvalgte ex-" + fadTapninger.get(0).getDestillat().getFad().getFadHistorik().getTidligereIndhold()
                + " barrels.\n\n");
        String rygemateriale = fadTapninger.get(0).getDestillat().getPåfyldninger().get(0).getDestillering().getRygeMateriale();
        sb.append(rygemateriale != null ? (rygemateriale + ", Økologisk ") : ("Økologisk "));
        sb.append(whiskyType() + "\n& Single Farm Whisky" + "\n 100cl. " + df.format(alkoholProcent) + "% Vol.");

        return sb.toString();
    }

    private ArrayList<String> historieMarker() {
        ArrayList<String> toReturn = new ArrayList<>();
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

    private ArrayList<String> historieKorn() {
        ArrayList<String> toReturn = new ArrayList<>();

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

    private String historieModningstid() {
        Period måneder = null;
        Destillat destillat = fadTapninger.get(0).getDestillat();
        måneder = Period.between(destillat.getPåfyldningsDato(), destillat.getModningsHistorik().get(destillat.getModningsHistorik().size() - 1).getSlutDato().plusDays(1));
        String[] tal = new String[]{"nul", "et", "to", "tre", "fire", "fem", "seks", "syv", "otte", "ni", "ti", "elleve", "tolv", "tretten", "fjorten", "femten", "seksten", "sytten", "atten", "nitten", "tyve"};
        return tal[måneder.getYears()];
    }


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
        return navn + " " + numberFormatter.format(alkoholProcent) + " % Vol.";
    }
}
