package model;

/**
 * Kölcsönző személyek adatai
 * @author Ilyés Ádám
 */
public class Berlo {
    private Integer id;
    private String nev;
    private String jogositvanyszam;
    private String telefonszam;
    private String cim;

    public Berlo(Integer id, String nev, String jogositvanyszam, String telefonszam, String cim) {
        this.id = id;
        this.nev = nev;
        this.jogositvanyszam = jogositvanyszam;
        this.telefonszam = telefonszam;
        this.cim = cim;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTelefonszam() {
        return telefonszam;
    }

    public void setTelefonszam(String telefonszam) {
        this.telefonszam = telefonszam;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }
        
}
