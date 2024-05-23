package application.controller;

import application.models.*;

import java.util.List;

public interface Storage {

    public List<Fad> getFade();
    public List<Lager> getLagre();
    public List<Destillering> getDestilleringer();
    public List<WhiskyProdukt> getWhiskyProdukter();
    public List<Korn> getKorntyper();
    public void addFad(Fad fad);
    public void addKorntype(Korn korn);
    public void addLager(Lager lager);
    public void addWhiskyProdukt(WhiskyProdukt whiskyProdukt);
    public void addDestillering(Destillering destillering);
    public void removeFad(Fad fad);


}
