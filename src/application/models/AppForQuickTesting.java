package application.models;

import java.time.LocalDate;

public class AppForQuickTesting {
    public static void main(String[] args) {
        Fad fad = new Fad(200);
        FadHistorik fadHistorik = new FadHistorik("Sherry", "Spanien", LocalDate.of(2004, 1, 1), "Leverandørgutten");
        fad.createFadHistorik("Sherry", "Spanien", LocalDate.of(2004, 1, 1), "Leverandørgutten");

        System.out.println(fad.getFadHistorik());

        Korn korn = new Korn("Byg", "Evergreen", "Highland");
        Destillering destillering = new Destillering("Malthuset", korn, "Chris", 1000, 50, "Bøgeflis", "N/A");
        Destillat destillat = new Destillat("Bedste shit");
        destillat.createPåfyldning("Chris", 200, destillering);
        fad.setDestillat(destillat);

        System.out.println(fad.getDestillat().getPåfyldningsDato());
        System.out.println(fad.getDestillat().getAntalLiter());

        FadTapning ft = new FadTapning("Chris", 80, fad);

        System.out.println(fad.getDestillat().getAntalLiter());

        WhiskyProdukt whiskyProdukt = new WhiskyProdukt("Highlander bryg");
        whiskyProdukt.createFadTapning("Maintz", 20, fad);

        System.out.println(whiskyProdukt.getAntalLiter());

        whiskyProdukt.addFadTapning(ft);

        System.out.println(fad.getDestillat().getAntalLiter());

        System.out.println(whiskyProdukt.getAntalLiter());

        String hist = "Her er en historie om produktet";
        whiskyProdukt.createWhiskyFlaske(hist);
        System.out.println(whiskyProdukt.getAntalLiter());
        System.out.println(whiskyProdukt.getFyldteFlasker());


        Lager lager = new Lager("Lars' lade");
        lager.createReol(4);
        System.out.println(lager.getReoler());
        lager.getReoler().get(0).addFad(fad, 1);

        System.out.println(lager.getReoler().get(0).getAlleFade());

    }
}
