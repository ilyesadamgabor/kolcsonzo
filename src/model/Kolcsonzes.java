package model;

/**
 * Kölcsönzésről tárol adatok
 * @author Ilyés Ádám
 */
public class Kolcsonzes {
    private Integer id;
    private String tipus;
    private String rendszam;
    private String nev;
    private String jogositvanyszam;
    private String kezdete;
    private Integer napidij;

    public Kolcsonzes(Integer id, String tipus, String rendszam, String nev, String jogositvanyszam, String kezdete, Integer napidij) {
        this.id = id;
        this.tipus = tipus;
        this.rendszam = rendszam;
        this.nev = nev;
        this.jogositvanyszam = jogositvanyszam;
        this.kezdete = kezdete;
        this.napidij = napidij;
    }

    public Integer getNapidij() {
        return napidij;
    }

    public void setNapidij(Integer napidij) {
        this.napidij = napidij;
    }   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getRendszam() {
        return rendszam;
    }

    public void setRendszam(String rendszam) {
        this.rendszam = rendszam;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getJogositvanyszam() {
        return jogositvanyszam;
    }

    public void setJogositvanyszam(String jogositvanyszam) {
        this.jogositvanyszam = jogositvanyszam;
    }

    public String getKezdete() {
        return kezdete;
    }

    public void setKezdete(String kezdete) {
        this.kezdete = kezdete;
    }
    
    
}
