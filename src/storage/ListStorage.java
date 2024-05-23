package storage;

import application.controller.Storage;
import application.models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage, Serializable {
    private final ArrayList<Fad> fade = new ArrayList<>();
    private final ArrayList<Korn> korntyper = new ArrayList<>();
    private final ArrayList<Destillering> destilleringer = new ArrayList<>();
    private final ArrayList<Lager> lagre = new ArrayList<>();
    private final ArrayList<WhiskyProdukt> whiskyProdukter = new ArrayList<>();
    private int antalFade;
    private int antalDestilleringer;

    //-------------------------------------------------------------------

    public static ListStorage loadStorage() {
        String fileName = "storage.srl";
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objIn = new ObjectInputStream(fileIn)
        ) {
            Object obj = objIn.readObject();
            ListStorage storage = (ListStorage) obj;
            System.out.println("Storage loaded from file " + fileName);
            Destillering.setAntalDestilleringer(storage.getAntalDestilleringerOprettet());
            Fad.setAntalFade(storage.getAntalFadeOprettet());
            return storage;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing storage");
            System.out.println(e);
            return null;
        }
    }


    public static void saveStorage(Storage storage) {
        String fileName = "storage.srl";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)
        ) {
            storage.setAntalFadeOprettet();
            storage.setAntalDestilleringerOprettet();
            objOut.writeObject(storage);
            System.out.println("Storage saved in file " + fileName);
        } catch (IOException e) {
            System.out.println("Error serializing storage");
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    @Override
    public int getAntalFadeOprettet() {
        return this.antalFade;
    }
    public void setAntalFadeOprettet() {
        this.antalFade = Fad.getAntalFade();
    }

    public int getAntalDestilleringerOprettet() {
        return antalDestilleringer;
    }

    public void setAntalDestilleringerOprettet() {
        this.antalDestilleringer = Destillering.getAntalDestilleringer();
    }
    @Override
    public List<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    @Override
    public List<Lager> getLagre() {
        return new ArrayList<>(lagre);
    }

    @Override
    public List<Destillering> getDestilleringer() {
        return new ArrayList<>(destilleringer);
    }

    @Override
    public List<WhiskyProdukt> getWhiskyProdukter() {
        return new ArrayList<>(whiskyProdukter);
    }

    @Override
    public List<Korn> getKorntyper() {
        return new ArrayList<>(korntyper);
    }

    @Override
    public void addFad(Fad fad) {
        fade.add(fad);
    }

    @Override
    public void addKorntype(Korn korn) {
        korntyper.add(korn);
    }

    @Override
    public void addLager(Lager lager) {
        lagre.add(lager);
    }

    @Override
    public void addWhiskyProdukt(WhiskyProdukt whiskyProdukt) {
        whiskyProdukter.add(whiskyProdukt);
    }

    @Override
    public void addDestillering(Destillering destillering) {
        destilleringer.add(destillering);
    }
}
