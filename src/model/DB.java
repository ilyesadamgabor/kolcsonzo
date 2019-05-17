package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * kapcsolódások az adatbázishoz
 * @author Ilyés Ádám
 */
public class DB {

    private final String db = "jdbc:mysql://localhost:3306/kolcsonzo"
            + "?useUnicode=true&characterEncoding=UTF-8";
    private final String user = "kolcsonzo";
    private final String pass = "kolcsonzo";

    /**
     * Autók lekérdezése
     * @param tabla autók minden adatával van feltöltve
     * @param lista az autók tipusát tartalmazza, ha nincs bérelve
     */
    public void autokBe(ObservableList<Auto> tabla, ObservableList<String> lista) {
        /**
         * SQL lekérdezés
         */
        String s = "SELECT * FROM autok ORDER BY tipus;";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ekp.executeQuery();
            tabla.clear();
            lista.clear();

            while (eredmeny.next()) {
                tabla.add(new Auto(
                        eredmeny.getInt("id"),
                        eredmeny.getString("tipus"),
                        eredmeny.getString("szin"),
                        eredmeny.getInt("evjarat"),
                        eredmeny.getString("rendszam"),
                        eredmeny.getInt("napidij"))
                );
                if (eredmeny.getInt("berelve") == 0) {
                    lista.add((eredmeny.getString("tipus")));

                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            hiba("hiba", ex.getMessage());
        }

    }

    /**
     * adatbázisban az autok táblában új rekor hozzáadása
     * input adatok:
     * @param tipus autó tipusa
     * @param szin autó szine
     * @param evjarat autó gyártási éve
     * @param rendszam autó rendszáma
     * @param napidij autó napi bérleti díja
     * @return sikeres rögzítés esetén nem ad vissza értéket hiba esetén a hibaüzenettel tér vissza
     */
    public String autok_hozzaad(String tipus, String szin, Integer evjarat, String rendszam, Integer napidij) {
        String s = "INSERT INTO autok (tipus, szin, evjarat, rendszam, berelve, napidij) VALUES (?,?,?,?,0,?);";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, tipus);
            ekp.setString(2, szin);
            ekp.setInt(3, evjarat);
            ekp.setString(4, rendszam);
            ekp.setInt(5, napidij);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    /**
     * Bérlők lekérdezése és listába töltése
     *
     * @param tabla a bérlők minden adatával van feltöltve
     * @param lista a bérlők neveit tartalmazza
     */
    public void berlokBe(ObservableList<Berlo> tabla, ObservableList<String> lista) {
        String s = "SELECT * FROM berlok ORDER BY nev, jogositvanyszam;";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ekp.executeQuery();
            tabla.clear();
            lista.clear();

            while (eredmeny.next()) {
                tabla.add(new Berlo(
                        eredmeny.getInt("id"),
                        eredmeny.getString("nev"),
                        eredmeny.getString("jogositvanyszam"),
                        eredmeny.getString("telefonszam"),
                        eredmeny.getString("cim"))
                );
                lista.add(eredmeny.getString("nev"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Adatbázisban a bérlők táblához új rekord hozzáadása az azonosító automatikus kerül megadásra
     * inputok:
     * @param nev bérlő neve
     * @param jogositvanyszam bérlő jogosítvány száma
     * @param telefonszam bérlő telefonszáma
     * @param cim bérlő címe /irányítószám, város, utca házszám
     * @return sikeres rögzítés esetén nem ad vissza értéket hiba esetén a hibaüzenettel tér vissza
     */
    public String berlo_hozzad(String nev, String jogositvanyszam, String telefonszam, String cim) {
        String s = "INSERT INTO berlok (nev, jogositvanyszam, telefonszam, cim) VALUES (?,?,?,?);";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, nev);
            ekp.setString(2, jogositvanyszam);
            ekp.setString(3, telefonszam);
            ekp.setString(4, cim);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    /**
     * A kölcsönzések rögzítése
     * inputok:
     * @param autoid a bérlendő autó azonoítója
     * @param berloid a bérlő autó azonosítója
     * @param kezdet a bérlet kezdetének dátuma
     * @return sikeres rögzítés esetén nem ad vissza értéket hiba esetén a hibaüzenettel tér vissza
     */
    public String kolcsonzes_hozzad(Integer autoid, Integer berloid, String kezdet) {
        String s = "INSERT INTO kolcsonzesek (autoid, berloid, kezdete) "
                + "VALUES (?,?,?);";
        String v = "UPDATE autok SET berelve=1 WHERE id=?;";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s);
                PreparedStatement ekp2 = kapcs.prepareStatement(v)) {
            ekp.setInt(1, autoid);
            ekp.setInt(2, berloid);
            ekp.setString(3, kezdet);
            ekp.executeUpdate();
            ekp2.setInt(1, autoid);
            ekp2.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    /**
     * Adatbázisba a meglévő bérlő adatainak módosítása
     * inputok:
     * @param id azonosító
     * @param nev bérlő neve
     * @param jogositvanyszam
     * @param telefonszam
     * @param cim
     * @return sikeres rögzítés esetén nem ad vissza értéket hiba esetén a hibaüzenettel tér vissza
     */
    public String berlo_modosit(int id, String nev, String jogositvanyszam,
            String telefonszam, String cim) {
        String s = "UPDATE berlok SET nev=?, jogositvanyszam=?, telefonszam=?, "
                + "cim=? WHERE id=?";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, nev);
            ekp.setString(2, jogositvanyszam);
            ekp.setString(3, telefonszam);
            ekp.setString(4, cim);
            ekp.setInt(5, id);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }
    
    /**
     * Adatbázisban a meglévő autók adatainak módosítása
     * inputok:
     * @param id azonosító
     * @param tipus autó tipusa
     * @param szin autó szine
     * @param evjarat autó gyártási éve
     * @param rendszam autó egyedi rendszáma
     * @param napidij autó napi bérleti díja
     * @return sikeres rögzítés esetén nem ad vissza értéket hiba esetén a hibaüzenettel tér vissza
     */
    public String auto_modosit(int id, String tipus, String szin, int evjarat,
            String rendszam, int napidij) {
        String s = "UPDATE autok SET tipus=?, szin=?, evjarat=?, "
                + "rendszam=?, napidij=? WHERE id=?";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, tipus);
            ekp.setString(2, szin);
            ekp.setInt(3, evjarat);
            ekp.setString(4, rendszam);
            ekp.setInt(5, napidij);
            ekp.setInt(6, id);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    /**
     * A folyamatban lévő kölcsönzések lekérdezése és táblába töltése
     * @param tabla A kölcsönzések minden adatával van feltöltve
     */
    public void kolcsonzesekBe(ObservableList<Kolcsonzes> tabla) {
        String s = "SELECT kolcsonzesek.id, autok.tipus, autok.rendszam, autok.napidij, berlok.nev, berlok.jogositvanyszam, kolcsonzesek.kezdete FROM kolcsonzesek "
                + "JOIN autok ON kolcsonzesek.autoid=autok.id JOIN berlok ON kolcsonzesek.berloid=berlok.id WHERE kolcsonzesek.vege IS NULL;";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ekp.executeQuery();
            tabla.clear();

            while (eredmeny.next()) {
                tabla.add(new Kolcsonzes(
                        eredmeny.getInt("id"),
                        eredmeny.getString("tipus"),
                        eredmeny.getString("rendszam"),
                        eredmeny.getString("nev"),
                        eredmeny.getString("jogositvanyszam"),
                        eredmeny.getString("kezdete"),
                        eredmeny.getInt("napidij")
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * A folyamatban lévő kölcsönzések lezárására, rögzíti az inputokat
     * inputok:
     * @param id kölcsönzés azonosítója
     * @param datum visszaadás dátuma
     * @param fizetett a bérleti díj amit fizetett az ügyfél
     * @return sikeres rögzítés esetén nem ad vissza értéket hiba esetén a hibaüzenettel tér vissza
     */
    public String kolcsonzesVissza(Integer id, String datum, Integer fizetett) {
        String s = "UPDATE kolcsonzesek SET vege=?, fizetett=?"
                + " WHERE kolcsonzesek.id=?";
        
        String v = "UPDATE autok JOIN kolcsonzesek ON autok.id=kolcsonzesek.autoid SET berelve=0 WHERE kolcsonzesek.id=?;";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s);
                PreparedStatement ekp2 = kapcs.prepareStatement(v)) {
            ekp.setString(1, datum);
            ekp.setInt(2, fizetett);
            ekp.setInt(3, id);
            ekp.executeUpdate();
            ekp2.setInt(1, id);
            ekp2.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    /**
     * Összesítés
     * @param tabla az összes ügylet adataival van feltöltve
     * @param s SQL lekérdező parancs
     */
    public void osszesBe(ObservableList<Osszes> tabla, String s) {

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ekp.executeQuery();
            tabla.clear();

            while (eredmeny.next()) {
                tabla.add(new Osszes(
                        eredmeny.getInt("id"),
                        eredmeny.getString("tipus"),
                        eredmeny.getString("rendszam"),
                        eredmeny.getString("nev"),
                        eredmeny.getString("jogositvanyszam"),
                        eredmeny.getString("kezdete"),
                        eredmeny.getString("vege"),
                        eredmeny.getInt("fizetett")
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void hiba(String cim, String uzenet) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.setContentText(uzenet);
        alert.showAndWait();
    }
}
