package application.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class WhiskyProdukt {
    private String navn;
    private double alkoholProcent;
    private List<FadTapning> fadTapninger;
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
        WhiskyFlaske flaske = new WhiskyFlaske(fyldteFlasker.size() + 1, this, produktHistorie);
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
        double literEthanol = 0;
        for (FadTapning fadTapning : fadTapninger) {
            literEthanol += (fadTapning.getDestillat().getAlkoholprocent() / 100) * fadTapning.getLiterTappet();
        }
        alkoholProcent = (literEthanol / antalLiter) * 100;
    }

    public void tilføjVand(int literVand) {
        this.literVandTilføjet += literVand;
        antalLiter += literVand;
        udregnAlkoholprocent();
    }

    public String whiskyType() {
        if (fadTapninger.size() == 1) {
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
        //TODO skal den gemme det den genererer, som beskrivelse? Eller bare smide den ind i Controlleren
        // som lokalvariabel og så sender man den med ind i flaskens constructor?
        // Og skal historien kunne gemmes til en fil?
        String toReturn;

        //TODO er det nemmere med en StringBuilder? Hmm
        String korn = "Skabt af egne hænder med Lars' økologiske ";
        String mark = "\nSået og høstet fra den jyske muld på Lars' marker ";
        //lav to lokale variabler til at finde ud af hvor længe mæskningen er sket; en til den mæskning med færrest timer,
        //og en til den mæskning med flest timer, som bruges mens man itererer gennem destilleringer.

//        TODO skal holde styr på forskellige fade hvis der er omhældt..? Såååå... Jfc. En
//         metode i destillat som laver en beskrivelse eller sådan noget, omkring dens historik?
//         jfc
        String modningOgFad = "\nModnet i " + modningsHistorie()
                + " år i omhyggeligt udvalgte ex-" + fadTapninger.get(0).getDestillat().getFad().getFadHistorik().getTidligereIndhold()
                + " barrels.";
        String typeOgProcent = "\nØkologisk " + whiskyType() + "\n& Single Farm Whisky" + "\n 100cl. " + alkoholProcent + "% Vol.";
//        if (indhold.size() > 1) {
        int lavestAntalTimer;
        int højesteAntalTimer;

//        Det her kunne være sin egen metode..?
//        String mæsket = "\nMæsket ved håndkraft og fermenteret i ";
//        String destilleret = "\nDobbeltdestilleret langtsomt i direct fired kobber pot stills.";


        for (FadTapning ft : fadTapninger) {
            for (Påfyldning pf : ft.getDestillat().getPåfyldninger()) {
                String tempVariant = pf.getDestillering().getKornSort().getVariant();
                String tempSort = pf.getDestillering().getKornSort().getSort();
                if (!korn.contains(tempVariant)) {
                    korn += tempVariant + " " + tempSort + ", ";
                }
                if (!mark.contains(pf.getDestillering().getKornSort().getMarkNavne())) {
                    mark += pf.getDestillering().getKornSort().getMarkNavne() + ", ";
                }
            }
        }
//                if (ft.getDestillat().getPåfyldninger().size() > 1) {
//            }
//        }

        for (FadTapning ft : fadTapninger) {
            for (ModningsHistorik dh : ft.getDestillat().getModningsHistorik()) {
//                toReturn += dh.getFad();
                dh.getStartDato().until(dh.getSlutDato()).getMonths();
            }
        }


        toReturn = korn + mark + genererHistorieTimer() + modningOgFad + typeOgProcent;
        return toReturn;
    }

    private String modningsHistorie() {
        if (fadTapninger.get(0).getDestillat().getPåfyldningsDato().until(LocalDate.now()).getYears() < 3) {
            String modningsTid = "";
            int måneder = 0;
            for (FadTapning ft : fadTapninger) {
                for (ModningsHistorik modningsHistorik : ft.getDestillat().getModningsHistorik()) {
                    måneder += modningsHistorik.getStartDato().until(modningsHistorik.getSlutDato(), ChronoUnit.MONTHS);
                }
            }
//            return måneder / 12;
        }
        String fade = fadTapninger.get(0).getDestillat().getFad().getType();
        fadTapninger.get(0).getDestillat().getPåfyldningsDato().until(LocalDate.now());
//TODO kan vi ikke bare sige pre: newFad.getType == oldFad.getType,
// så man ikke skal tjekke fadTyper i det mindste?
// tror jeg nu næppe... SPØRG MARGRETHE
        String modningsTid = "";
        for (FadTapning ft : fadTapninger) {
            String temp;
            if (ft.getDestillat().getModningsHistorik().size() > 0) {
                for (ModningsHistorik modningsHistorik : ft.getDestillat().getModningsHistorik()) {
                    temp = modningsHistorik.getFad().getType();
                    if (!fade.contains(temp)) {
                        fade += " og " + temp;
                    }
                    modningsTid += (modningsHistorik.getStartDato().until(modningsHistorik.getSlutDato(), ChronoUnit.MONTHS)) / 12 + " og ";
                }
            }
        }
        String modningOgFad = "\nModnet i " + modningsTid
                + " år i omhyggeligt udvalgte ex-" + fadTapninger.get(0).getDestillat().getFad().getFadHistorik().getTidligereIndhold()
                + " barrels.";

        return "\nModnet i " + modningsTid + " år i omhyggeligt udvalgte ex-" + fade + " barrels.";
    }

    private String genererHistorieTimer() {
        int lavestAntalTimer = fadTapninger.get(0).getDestillat().getPåfyldninger().get(0).getDestillering().getDestilleringsTid();
        int højesteAntalTimer = lavestAntalTimer;
        for (FadTapning ft : fadTapninger) {
            for (Påfyldning pf : ft.getDestillat().getPåfyldninger()) {
                int tempTimer = pf.getDestillering().getDestilleringsTid();
                if (tempTimer > højesteAntalTimer) {
                    højesteAntalTimer = tempTimer;
                } else if (tempTimer < lavestAntalTimer) {
                    lavestAntalTimer = tempTimer;
                }
            }
        }
        if (lavestAntalTimer == højesteAntalTimer) {
            return "\nMæsket ved håndkraft og fermenteret i " + lavestAntalTimer + "\nDobbeltdestilleret langtsomt i direct fired kobber pot stills.";
        }
        return "\nMæsket ved håndkraft og fermenteret i " + lavestAntalTimer + " til " + højesteAntalTimer + "\nDobbeltdestilleret langtsomt i direct fired kobber pot stills.";
    }

}
