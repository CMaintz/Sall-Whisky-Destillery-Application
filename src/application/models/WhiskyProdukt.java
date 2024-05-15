package application.models;

import java.util.ArrayList;
import java.util.List;

public class WhiskyProdukt {
    private String navn;
    private double alkoholProcent;
    private String beskrivelse; //TODO Ved ikke om vi skal beholde den her eller bare lave det til en metode?
    private List<FadTapning> fadTapninger;
    private final List<WhiskyFlaske> fyldteFlasker;
    private double literVandTilføjet;
    private double literWhisky;

    public WhiskyProdukt(String navn, String beskrivelse, int literVandTilføjet) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
//        this.udregnAlkoholprocent();
        this.tilføjVand(literVandTilføjet);

        this.literWhisky = 0;
        this.fadTapninger = new ArrayList<>();
        this.fyldteFlasker = new ArrayList<>();

    }

    public FadTapning createFadTapning(String medarbejderNavn, double literTappet, Fad fad) {
        FadTapning ft = new FadTapning(medarbejderNavn, literTappet, fad);
        fadTapninger.add(ft);
        literWhisky += literTappet;
        udregnAlkoholprocent();
        return ft;
    }

    public void addFadTapning(FadTapning fadTapning) {
        if (!fadTapninger.contains(fadTapning)) {
            this.fadTapninger.add(fadTapning);
            literWhisky += fadTapning.getLiterTappet();
        }
    }



    public void setAntalLiter(int liter) {
        this.literWhisky = liter;
    }

    public WhiskyFlaske fyldPåFlasker() {
        WhiskyFlaske flaske = new WhiskyFlaske(fyldteFlasker.size() + 1, this);
        fyldteFlasker.add(flaske);
        literWhisky--;
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

    public String getBeskrivelse() {
        return beskrivelse;
    }

    private void udregnAlkoholprocent() {
        double literEthanol = 0;
        for (FadTapning fadTapning : fadTapninger) {
            literEthanol += (fadTapning.getDestillat().getAlkoholprocent() / 100) * fadTapning.getLiterTappet();
        }
        alkoholProcent = (literEthanol / literWhisky) * 100;
    }

    public void tilføjVand(int literVandTilføjet) {
        literWhisky += literVandTilføjet;
        udregnAlkoholprocent();
    }

    public String whiskyType() {
        String type = "";
        if (fadTapninger.size() == 1) {
            type = "SINGLE CASK";
        } else {
            type = "SINGLE MALT";
        }
        if (literVandTilføjet != 0) {
            type += ", CASK STRENGTH";
        }
        return type;
    }

    public double getSamletAntalLiter() {
//        return antalLiterAlkohol + literVandTilføjet;
        return literWhisky;
    }

    public String genererHistorie() {
        //TODO - Er det i stedet for en beskrivelse?
        // Eller skal den gemme det den genererer, som beskrivelsen?
        // Og skal historien kunne gemmes til en fil?

        String toReturn = "";


        toReturn += "\n" + whiskyType();
        return toReturn;
    }

}
