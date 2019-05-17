package model;

/**
 * Összesítéshez tárolt adatok
 * @author Ilyés Ádám
 */
public class Osszes {
    private Integer id;
    private String tipus;
    private String rendszam;
    private String nev;
    private String jogositvanyszam;
    private String kezdete;
    private String vege;
    private Integer fizetett;

    public Osszes(Integer id, String tipus, String rendszam, String nev, String jogositvanyszam, String kezdete, String vege, Integer fizetett) {
        this.id = id;
        this.tipus = tipus;
        this.rendszam = rendszam;
        this.nev = nev;
        this.jogositvanyszam = jogositvanyszam;
        this.kezdete = kezdete;
        this.vege = vege;
        this.fizetett = fizetett;
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

    public String getVege() {
        return vege;
    }

    public void setVege(String vege) {
        this.vege = vege;
    }

    public Integer getFizetett() {
        return fizetett;
    }

    public void setFizetett(Integer fizetett) {
        this.fizetett = fizetett;
    }
        
}
