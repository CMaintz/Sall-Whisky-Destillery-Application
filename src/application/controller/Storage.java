package application.controller;

import application.models.*;

import java.util.List;

/**
 * The interface Storage.
 */
public interface Storage {

    /**
     * Gets fade.
     *
     * @return the fade
     */
    public List<Fad> getFade();

    /**
     * Gets lagre.
     *
     * @return the lagre
     */
    public List<Lager> getLagre();

    /**
     * Gets destilleringer.
     *
     * @return the destilleringer
     */
    public List<Destillering> getDestilleringer();

    /**
     * Gets whisky produkter.
     *
     * @return the whisky produkter
     */
    public List<WhiskyProdukt> getWhiskyProdukter();

    /**
     * Gets korntyper.
     *
     * @return the korntyper
     */

    public List<Korn> getKorntyper();

    /**
     * Add fad.
     *
     * @param fad the fad
     */

    public void addFad(Fad fad);

    /**
     * Add korntype.
     *
     * @param korn the korn
     */

    public void addKorntype(Korn korn);

    /**
     * Add lager.
     *
     * @param lager the lager
     */

    public void addLager(Lager lager);

    /**
     * Add whisky produkt.
     *
     * @param whiskyProdukt the whisky produkt
     */

    public void addWhiskyProdukt(WhiskyProdukt whiskyProdukt);

    /**
     * Add destillering.
     *
     * @param destillering the destillering
     */

    public void addDestillering(Destillering destillering);
    public void removeFad(Fad fad);

    /**
     * Sets antal fade oprettet.
     */

    public void setAntalFadeOprettet();

    /**
     * Gets antal fade oprettet.
     *
     * @return the antal fade oprettet
     */

    public int getAntalFadeOprettet();

    /**
     * Sets antal destilleringer oprettet.
     */

    public void setAntalDestilleringerOprettet();

    /**
     * Gets antal destilleringer oprettet.
     *
     * @return the antal destilleringer oprettet
     */

    public int getAntalDestilleringerOprettet();
}
