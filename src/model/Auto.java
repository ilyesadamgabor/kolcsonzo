package model;

/**
 * Kölcsönözhető gépjárművek adatai
 * @author Ilyés Ádám
 */
public class Auto {
    private Integer id;
    private String tipus;
    private String szin;
    private Integer evjarat;
    private String rendszam;
    private Integer napidij;

    public Auto(Integer id, String tipus, String szin, Integer evjarat, String rendszam, Integer napidij) {
        this.id = id;
        this.tipus = tipus;
        this.szin = szin;
        this.evjarat = evjarat;
        this.rendszam = rendszam;
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

    public String getSzin() {
        return szin;
    }

    public void setSzin(String szin) {
        this.szin = szin;
    }

    public Integer getEvjarat() {
        return evjarat;
    }

    public void setEvjarat(Integer evjarat) {
        this.evjarat = evjarat;
    }

    public String getRendszam() {
        return rendszam;
    }

    public void setRendszam(String rendszam) {
        this.rendszam = rendszam;
    }
    
    
}
