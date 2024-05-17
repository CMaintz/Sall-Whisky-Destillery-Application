package application.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WhiskyProdukt {
    private String navn;
    private double alkoholProcent;
    private List<FadTapning> indhold;
    private final List<WhiskyFlaske> fyldteFlasker;
    private double literVandTilføjet;
    private double literTotal;

    public WhiskyProdukt(String navn) {
        this.navn = navn;
        this.literTotal = 0;
        this.literVandTilføjet = 0;
        this.indhold = new ArrayList<>();
        this.fyldteFlasker = new ArrayList<>();
    }

    public FadTapning createFadTapning(String medarbejderNavn, double literTappet, Fad fad) {
        FadTapning ft = new FadTapning(medarbejderNavn, literTappet, fad);
        indhold.add(ft);
        literTotal += literTappet;
        udregnAlkoholprocent();
        return ft;
    }

    public void addFadTapning(FadTapning fadTapning) {
        if (!indhold.contains(fadTapning)) {
            this.indhold.add(fadTapning);
            literTotal += fadTapning.getLiterTappet();
        }
    }


    public void setAntalLiter(int liter) {
        this.literTotal = liter;
    }

    public WhiskyFlaske fyldPåFlasker() {
        WhiskyFlaske flaske = new WhiskyFlaske(fyldteFlasker.size() + 1, this);
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
        for (FadTapning fadTapning : indhold) {
            literEthanol += (fadTapning.getDestillat().getAlkoholprocent() / 100) * fadTapning.getLiterTappet();
        }
        alkoholProcent = (literEthanol / literTotal) * 100;
    }

    public void tilføjVand(int literVandTilføjet) {
        this.literVandTilføjet += literVandTilføjet;
        literTotal += literVandTilføjet;
        udregnAlkoholprocent();
    }

    public String whiskyType() {
        if (indhold.size() == 1) {
            return literVandTilføjet == 0 ? "Cask Strength" : "Single Cask";
//            if (literVandTilføjet == 0) {
//                return "Cask Strength";
//            }
//            return "Single Cask";
        } else {
            return "Single Malt";
        }
    }

    public double getLiterTotal() {
        return literTotal;
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
        String modningOgFad = "\nModnet i " + indhold.get(0).getDestillat().getPåfyldningsDato().until(LocalDate.now())
                + " år i omhyggeligt udvalgte ex-" + indhold.get(0).getDestillat().getFad().getFadHistorik().getTidligereIndhold()
                + " barrels.";
        String typeOgProcent = "\nØkologisk " + whiskyType() + "\n& Single Farm Whisky" + "\n 100cl. " + alkoholProcent + "% Vol.";
//        if (indhold.size() > 1) {
        int lavestAntalTimer;
        int højesteAntalTimer;

//        Det her kunne være sin egen metode..?
//        String mæsket = "\nMæsket ved håndkraft og fermenteret i ";
//        String destilleret = "\nDobbeltdestilleret langtsomt i direct fired kobber pot stills.";


        for (FadTapning ft : indhold) {
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

        for (FadTapning ft : indhold) {
            for (DestillatHistorik dh : ft.getDestillat().getModningsHistorik()) {
//                toReturn += dh.getFad();
                dh.getStartDato().until(dh.getSlutDato()).getMonths();
            }
        }


        toReturn = korn + mark + genererHistorieTimer() + modningOgFad + typeOgProcent;
        return toReturn;
    }

    private String genererHistorieTimer() {
        int lavestAntalTimer = indhold.get(0).getDestillat().getPåfyldninger().get(0).getDestillering().getTimerDestilleret();
        int højesteAntalTimer = indhold.get(0).getDestillat().getPåfyldninger().get(0).getDestillering().getTimerDestilleret();
        for (FadTapning ft : indhold) {
            for (Påfyldning pf : ft.getDestillat().getPåfyldninger()) {
                int tempTimer = pf.getDestillering().getTimerDestilleret();
                if (tempTimer > højesteAntalTimer) {
                    højesteAntalTimer = tempTimer;
                } else if (tempTimer < lavestAntalTimer) {
                    lavestAntalTimer = tempTimer;
                }
            }
        }
        if (lavestAntalTimer == højesteAntalTimer) {
            return "\nMæsket ved håndkraft og fermenteret i " + lavestAntalTimer + "\nDobbeltdestilleret langtsomt i direct fired kobber pot stills.";
        } else {
            return "\nMæsket ved håndkraft og fermenteret i " + lavestAntalTimer + " til " + højesteAntalTimer + "\nDobbeltdestilleret langtsomt i direct fired kobber pot stills.";
        }
    }

}
