package storage;

import java.util.ArrayList;
import application.models.Fad;

public class Storage {
    private static ArrayList<Fad> fade = new ArrayList<>();

    public static ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    public static void addFad(Fad fad) {
        fade.add(fad);
    }
}
