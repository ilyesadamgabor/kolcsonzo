package kolcsonzo;

import model.DB;
import model.Osszes;
import model.Kolcsonzes;
import model.Berlo;
import model.Auto;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Ilyés Ádám Autókölcsönző asztali alkalmazás
 */
public class KolcsonzoFXMLDocumentController implements Initializable {
    
    @FXML
    private TableColumn<Auto, Integer> oAnapidij;

    @FXML
    private TableColumn<Auto, String> oArendszam;

    @FXML
    private TableColumn<Auto, String> oAtipus;

    @FXML
    private TableColumn<Auto, Integer> oAevjarat;

    @FXML
    private TableColumn<Auto, String> oAszin;

    @FXML
    private TableView<Auto> tblAutok;

    @FXML
    private TextField txtAtipus;

    @FXML
    private TextField txtArendszam;

    @FXML
    private TextField txtAszin;

    @FXML
    private TextField txtAevjarat;

    @FXML
    private TextField txtAnapidij;

    /**
     * Az autók lapon az új gombhoz tartozó parancsok Kitröli a szövegbeviteli
     * mezők tartalmát A tipsubevitelre viszi a fokuszt
     */
    @FXML
    void btnAuj() {
        txtAtipus.requestFocus();
        txtAtipus.clear();
        txtArendszam.clear();
        txtAszin.clear();
        txtAevjarat.clear();
        txtAnapidij.clear();
    }

    /**
     * Az autók lapon a ment gombhoz tartozó parancsok Ellenőrzi az adatok
     * meglétét és helyességét, amennyiben hibát talál felugró hiba ablakban
     * tájékoztatja a felhasználót. Ellenőrző kérdés, hogy biztosan menteni
     * szeretne, ha igen rögzíti az adatbázisban az adatokat
     */
    @FXML
    void btnAment() {
        String tipus = txtAtipus.getText().trim();
        if (tipus.isEmpty() || tipus.length() > 100) {
            hiba("Hiba", "Add meg az autó tipusát, ami maximum 100 karakter lehet!");
            txtAtipus.requestFocus();
            return;
        }

        String szin = txtAszin.getText().trim();
        if (szin.isEmpty() || szin.length() > 50) {
            hiba("Hiba", "Add meg az autó szinét ami maximum 50 karakter lehet!!");
            txtAszin.requestFocus();
            return;
        }

        if (txtAevjarat.getText().isEmpty()) {
            hiba("Hiba", "Add meg a gyártási évet!");
            txtAevjarat.requestFocus();
            return;
        }

        Integer evjarat;

        int ev = LocalDate.now().getYear();
        
        try {
            evjarat = Integer.parseInt(txtAevjarat.getText().trim());
            if (evjarat < 2000 || evjarat > ev) {
                hiba("Hiba", "Az autó gyártási éve 2000 és" + ev + " intervallumban lehet!");
                txtAevjarat.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) {
            hiba("Hiba!", "Az évjárat nem szám!");
            txtAevjarat.requestFocus();
            return;
        }

        String rendszam = txtArendszam.getText().trim();
        if (rendszam.length() != 7) {
            hiba("Hiba", "A rendszám 7 karakterből áll!");
            txtArendszam.requestFocus();
            return;
        }

        if (txtAnapidij.getText().isEmpty()) {
            hiba("Hiba", "Add meg a napi bérleti díjat!");
            txtAnapidij.requestFocus();
            return;
        }

        Integer napidij;

        try {
            napidij = Integer.parseInt(txtAnapidij.getText().trim());
            if (napidij < 3000) {
                hiba("Hiba", "Az autó napi bérleti díja nem lehet 3000 Ft-nál kevesebb!");
                txtAnapidij.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) {
            hiba("Hiba!", "Az ár nem szám!");
            txtAnapidij.requestFocus();
            return;
        }

        if (!igennem("Mentés", "Mented az új autó adatait?")) {
            return;
        }

        String v = ab.autok_hozzaad(tipus, szin, evjarat, rendszam, napidij);

        if (v.isEmpty()) {
            ab.autokBe(tblAutok.getItems(), cbxKtipus.getItems());
        } else {
            hiba("Hiba", v);
        }
    }
    
    @FXML
    void btnAmodosit() {
        int index = tblAutok.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            hiba("Hiba", "Nincs kiválasztva a módosítandó gépjármű!");
            return;
        }
        
        String tipus = txtAtipus.getText().trim();
        if (tipus.isEmpty() || tipus.length() > 100) {
            hiba("Hiba", "Add meg az autó tipusát, ami maximum 100 karakter lehet!");
            txtAtipus.requestFocus();
            return;
        }
        
        String szin = txtAszin.getText().trim();
        if (szin.isEmpty() || szin.length() > 50) {
            hiba("Hiba", "Add meg az autó szinét ami maximum 50 karakter lehet!!");
            txtAszin.requestFocus();
            return;
        }

        if (txtAevjarat.getText().isEmpty()) {
            hiba("Hiba", "Add meg a gyártási évet!");
            txtAevjarat.requestFocus();
            return;
        }

        Integer evjarat;

        int ev = LocalDate.now().getYear();
        
        try {
            evjarat = Integer.parseInt(txtAevjarat.getText().trim());
            if (evjarat < 2000 || evjarat > ev) {
                hiba("Hiba", "Az autó gyártási éve 2000 és " + ev + " intervallumban lehet!");
                txtAevjarat.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) {
            hiba("Hiba!", "Az évjárat nem szám!");
            txtAevjarat.requestFocus();
            return;
        }

        String rendszam = txtArendszam.getText().trim();
        if (rendszam.length() != 7) {
            hiba("Hiba", "A rendszám 7 karakterből áll!");
            txtArendszam.requestFocus();
            return;
        }

        if (txtAnapidij.getText().isEmpty()) {
            hiba("Hiba", "Add meg a napi bérleti díjat!");
            txtAnapidij.requestFocus();
            return;
        }

        Integer napidij;

        try {
            napidij = Integer.parseInt(txtAnapidij.getText().trim());
            if (napidij < 3000) {
                hiba("Hiba", "Az autó napi bérleti díja nem lehet 3000 Ft-nál kevesebb!");
                txtAnapidij.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) {
            hiba("Hiba!", "Az ár nem szám!");
            txtAnapidij.requestFocus();
            return;
        }

        int id = tblAutok.getItems().get(index).getId();
        
        if (!igennem("Mentés", "Mented a módisított adatokat")) {
            return;
        }
        
        String v = ab.auto_modosit(id, tipus, szin, evjarat, rendszam, napidij);
        
        if (v.isEmpty()) {
            ab.autokBe(tblAutok.getItems(), cbxKtipus.getItems());
            beolvas();
            for (int i = 0; i < tblAutok.getItems().size(); i++) {
                if (tblAutok.getItems().get(i).getId() == id) {
                    tblAutok.getSelectionModel().select(i);
                    break;
                }
            }
        } else {
            hiba("hiba", v);
        }
    }


    @FXML
    private TableView<Berlo> tblBerlok;

    @FXML
    private TableColumn<Berlo, String> oBtelefonszam;

    @FXML
    private TableColumn<Berlo, String> oBcim;

    @FXML
    private TableColumn<Berlo, String> oBnev;

    @FXML
    private TableColumn<Berlo, String> oBjogositvanyszam;

    @FXML
    private TextField txtBjogositvanyszam;

    @FXML
    private TextField txtBcim;

    @FXML
    private TextField txtBtelefonszam;

    @FXML
    private TextField txtBnev;

    /**
     * A bérlők lapon az új gombhoz tartozó parancsok Kitröli a szövegbeviteli
     * mezők tartalmát A név mezőre viszi a fokuszt
     */
    @FXML
    void btnBuj() {
        txtBnev.requestFocus();
        txtBnev.clear();
        txtBcim.clear();
        txtBjogositvanyszam.clear();
        txtBtelefonszam.clear();
    }

    /**
     * A bérlők lapon a ment gombhoz tartozó parancsok Ellenőrzi az adatok
     * meglétét és helyességét külön metodusokban, amennyiben hibát talál
     * felugró hiba ablakban tájékoztatja a felhasználót. Ellenőrző kérdés, hogy
     * biztosan menteni szeretne, ha igen rögzíti az adatbázisban az adatokat
     */
    @FXML
    void btnBment() {
        String nev = txtBnev.getText();
        if (nevHiba(nev) == false) {
            return;
        }

        String jogositvanyszam = txtBjogositvanyszam.getText();
        if (jogsiHiba(jogositvanyszam) == false) {
            return;
        }

        String telefonszam = txtBtelefonszam.getText();
        if (telefonszamHiba(telefonszam) == false) {
            return;
        }

        String cim = txtBcim.getText();
        if (cimHiba(cim) == false) {
            return;
        }

        if (!igennem("Mentés", "Mented az új bérlő adataid?")) {
            return;
        }

        String v = ab.berlo_hozzad(nev, jogositvanyszam, telefonszam, cim);

        if (v.isEmpty()) {
            ab.berlokBe(tblBerlok.getItems(), cbxKnev.getItems());
            cbxKtipus.getSelectionModel().selectedIndexProperty().addListener(
                    (o, regi, uj) -> tipusRendszam(uj.intValue()));
        } else {
            hiba("Hiba", v);
        }

    }

    /**
     * A bérlők lapon a hozzáad gombhoz tartozó parancsok Ellenőrzi az adatok
     * meglétét és helyességét külön metodusokban, amennyiben hibát talál
     * felugró hiba ablakban tájékoztatja a felhasználót. Ellenőrző kérdés, hogy
     * biztosan menteni szeretne, ha igen módosítja az adatbázisban az adatokat
     */
    @FXML
    void btnBmodosit() {
        int index = tblBerlok.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            hiba("Hiba", "Nincs kiválasztva a módosítandó ügyvél!");
            return;
        }
        String nev = txtBnev.getText().trim();
        if (nevHiba(nev) == false) {
            return;
        }

        String jogositvanyszam = txtBjogositvanyszam.getText().trim();
        if (jogsiHiba(jogositvanyszam) == false) {
            return;
        }

        String telefonszam = txtBtelefonszam.getText().trim();
        if (telefonszamHiba(telefonszam) == false) {
            return;
        }

        String cim = txtBcim.getText().trim();
        if (cimHiba(cim) == false) {
            return;
        }

        int id = tblBerlok.getItems().get(index).getId();

        if (!igennem("Módosítás", "Biztosan módosítod a bérlő adataid?")) {
            return;
        }

        String v = ab.berlo_modosit(id, nev, jogositvanyszam, telefonszam, cim);

        if (v.isEmpty()) {
            ab.berlokBe(tblBerlok.getItems(), cbxKnev.getItems());
            beolvas();
            for (int i = 0; i < tblBerlok.getItems().size(); i++) {
                if (tblBerlok.getItems().get(i).getId() == id) {
                    tblBerlok.getSelectionModel().select(i);
                    break;
                }
            }
        } else {
            hiba("hiba", v);
        }
    }

    @FXML
    private TableView<Kolcsonzes> tblKolcsonzes;

    @FXML
    private TableColumn<Kolcsonzes, String> oKjogositvanyszam;

    @FXML
    private TableColumn<Kolcsonzes, String> oKrendszam;

    @FXML
    private TableColumn<Kolcsonzes, String> oKkezdet;

    @FXML
    private TableColumn<Kolcsonzes, String> oKnev;

    @FXML
    private TableColumn<Kolcsonzes, String> oKtipus;

    @FXML
    private ComboBox<String> cbxKnev;

    @FXML
    private TextField cbxKjogositvanyszam;

    @FXML
    private ComboBox<String> cbxKrendszam;

    @FXML
    private ComboBox<String> cbxKtipus;

    /**
     * A kölcsönzések lapon az új gombhoz tartozó parancsok A ComboBox-ot
     * visszaállítja az alapértelmezettre A név választóra viszi a fokuszt
     */
    @FXML
    void btnKuj() {
        cbxKnev.requestFocus();
        cbxKnev.setValue(null);
        cbxKjogositvanyszam.setText(null);
        cbxKrendszam.setValue(null);
        cbxKtipus.setValue(null);

    }

    /**
     * A kölcsönzések lapon a ment gombhoz tartozó parancsok Ellenőrzi az adatok
     * meglétét, amennyiben hibát talál felugró hiba ablakban tájékoztatja a
     * felhasználót. Ellenőrző kérdés, hogy biztosan menteni szeretne, ha igen
     * rögzíti az adatbázisban az adatokat. Frissíti a bérelhető és a
     * visszahozandó autók listáját
     */
    @FXML
    void btnKment() {
        if (cbxKnev.getValue() == null) {
            hiba("Hiba", "Válaszd ki a bérlőt!");
            cbxKnev.requestFocus();
            return;
        }

        if (cbxKtipus.getValue() == null) {
            hiba("Hiba", "Válaszd ki a kölcsönözendő autót!");
            cbxKtipus.requestFocus();
            return;
        }

        if (cbxKrendszam.getValue() == null) {
            hiba("Hiba", "Válaszd ki a kölcsönözendő autó rendszámát!");
            cbxKrendszam.requestFocus();
            return;
        }

        int tipusID = get_tipusId(cbxKrendszam.getValue());
        int berloID = get_nevId(cbxKjogositvanyszam.getText());

        String datum = LocalDate.now().toString();

        if (!igennem("Mentés", "Mented az új kölcsönzést?")) {
            return;
        }

        String v = ab.kolcsonzes_hozzad(tipusID, berloID, datum);

        if (v.isEmpty()) {
            ab.kolcsonzesekBe(tblKolcsonzes.getItems());
            ab.kolcsonzesekBe(tblVisszaadas.getItems());
            beolvas();
            ab.autokBe(tblAutok.getItems(), cbxKtipus.getItems());
            cbxKtipus.setValue(null);
            cbxKrendszam.setValue(null);
            cbxKtipus.getSelectionModel().selectedIndexProperty().addListener(
                    (o, regi, uj) -> tipusRendszam(uj.intValue()));

        } else {
            hiba("Hiba", v);
        }

    }

    @FXML
    private TableView<Kolcsonzes> tblVisszaadas;

    @FXML
    private TableColumn<Kolcsonzes, String> oVrendszam;

    @FXML
    private TableColumn<Kolcsonzes, String> oVkezdet;

    @FXML
    private TableColumn<Kolcsonzes, String> oVtipus;

    @FXML
    private TableColumn<Kolcsonzes, String> oVnev;

    @FXML
    private TableColumn<Kolcsonzes, String> oVjogositvanyszam;

    @FXML
    private TextField txtVfizetett;

    @FXML
    private TextField txtVnev;

    @FXML
    private TextField txtVtipus;

    @FXML
    private Label lblVdij;

    /**
     * A visszaadás lapon a ment gombhoz tartozó parancsok Ellenőrzi az adatok
     * meglétét, amennyiben hibát talál felugró hiba ablakban tájékoztatja a
     * felhasználót. Ellenőrző kérdés, hogy biztosan menteni szeretne, ha igen
     * rögzíti az adatbázisban az adatokat. Frissíti a bérelhető és a
     * visszahozandó autók listáját Törli a beviteli mező tartalmát
     */
    @FXML
    void btnVment() {
        if (txtVnev.getText().isEmpty()) {
            hiba("Hiba!", "Válaszd ki a visszahozottat");
            return;
        }
        int l = txtVnev.getText().length();
        int id = get_id(txtVnev.getText().substring(l - 8));

        Integer ar;

        if (txtVfizetett.getText().isEmpty()) {
            hiba("Hiba", "Tölsd ki a fizetett bérleti díjat");
            txtVfizetett.requestFocus();
            return;
        } else {
            try {
                ar = Integer.parseInt(txtVfizetett.getText());
                if (ar < dij) {
                    hiba("Hiba!", "A bérleti díj nem lehet kevesebb számítottnál!");
                    txtVfizetett.requestFocus();
                    return;
                }
            } catch (NumberFormatException ex) {
                hiba("Hiba!", "Az ár nem szám!");
                txtVfizetett.requestFocus();
                return;
            }
        }

        String datum = LocalDate.now().toString();

        if (!igennem("Mentés", "A fizetés megtörtént,\na gépjárművel minden rendben van\nés biztosan menteni szeretnél!")) {
            return;
        }

        String v = ab.kolcsonzesVissza(id, datum, ar);

        if (v.isEmpty()) {
            ab.kolcsonzesekBe(tblVisszaadas.getItems());
            ab.kolcsonzesekBe(tblKolcsonzes.getItems());
            beolvas();
            ab.autokBe(tblAutok.getItems(), cbxKtipus.getItems());
            cbxKtipus.getSelectionModel().selectedIndexProperty().addListener(
                    (o, regi, uj) -> tipusRendszam(uj.intValue()));
            lblVdij.setText("Bérleti díj");
        } else {
            hiba("Hiba", v);
        }

        txtVnev.clear();
        txtVtipus.clear();
        txtVfizetett.clear();
    }

    @FXML
    private TableColumn<Osszes, String> oOrendszam;

    @FXML
    private TableColumn<Osszes, Integer> oOfizetett;

    @FXML
    private TableColumn<Osszes, String> oOtipus;

    @FXML
    private TableColumn<Osszes, String> oOnev;

    @FXML
    private TableColumn<Osszes, String> oOkezdete;

    @FXML
    private TableColumn<Osszes, String> oOvege;

    @FXML
    private TableColumn<Osszes, String> oOjogositvanyszam;

    @FXML
    private TableView<Osszes> tblOsszes;

    @FXML
    private TextField txtOtipus;

    @FXML
    private TextField txtOrendszam;

    @FXML
    private TextField txtOnev;

    @FXML
    private TextField txtOjogsi;

    @FXML
    private TextField txtOelvitte;

    @FXML
    private TextField txtOvissza;

    @FXML
    private TextField txtOfizetett;

    DB ab = new DB();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Autók, grafikus felületén az adatok betöltése
         */
        oArendszam.setCellValueFactory(new PropertyValueFactory<>("rendszam"));
        oAtipus.setCellValueFactory(new PropertyValueFactory<>("tipus"));
        oAevjarat.setCellValueFactory(new PropertyValueFactory<>("evjarat"));
        oAszin.setCellValueFactory(new PropertyValueFactory<>("szin"));
        oAnapidij.setCellValueFactory(new PropertyValueFactory<>("napidij"));

        ab.autokBe(tblAutok.getItems(), cbxKtipus.getItems());

        tblAutok.getSelectionModel().selectedIndexProperty().addListener(
                (o, regi, uj) -> autokTablabol(uj.intValue()));

        /**
         * Bérlők felületen az adatok betöltése
         */
        oBnev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oBjogositvanyszam.setCellValueFactory(new PropertyValueFactory<>("jogositvanyszam"));
        oBtelefonszam.setCellValueFactory(new PropertyValueFactory<>("telefonszam"));
        oBcim.setCellValueFactory(new PropertyValueFactory<>("cim"));

        ab.berlokBe(tblBerlok.getItems(), cbxKnev.getItems());

        tblBerlok.getSelectionModel().selectedIndexProperty().addListener(
                (o, regi, uj) -> berlokTablabol(uj.intValue()));

        /**
         * kölcsönzések felületen a táblázat adatainak betöltése
         */
        oKtipus.setCellValueFactory(new PropertyValueFactory<>("tipus"));
        oKrendszam.setCellValueFactory(new PropertyValueFactory<>("rendszam"));
        oKnev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oKjogositvanyszam.setCellValueFactory(new PropertyValueFactory<>("jogositvanyszam"));
        oKkezdet.setCellValueFactory(new PropertyValueFactory<>("kezdete"));

        ab.kolcsonzesekBe(tblKolcsonzes.getItems());

        cbxKnev.getSelectionModel().selectedIndexProperty().addListener(
                (o, regi, uj) -> berloJogsi(uj.intValue()));

        cbxKtipus.getSelectionModel().selectedIndexProperty().addListener(
                (o, regi, uj) -> tipusRendszam(uj.intValue()));

        /**
         * visszaadás felületen a táblázat adatainak betöltése
         */
        oVtipus.setCellValueFactory(new PropertyValueFactory<>("tipus"));
        oVrendszam.setCellValueFactory(new PropertyValueFactory<>("rendszam"));
        oVnev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oVjogositvanyszam.setCellValueFactory(new PropertyValueFactory<>("jogositvanyszam"));
        oVkezdet.setCellValueFactory(new PropertyValueFactory<>("kezdete"));

        ab.kolcsonzesekBe(tblVisszaadas.getItems());

        tblVisszaadas.getSelectionModel().selectedIndexProperty().addListener(
                (o, regi, uj) -> visszaadasTablalbol(uj.intValue()));

        /**
         * összesített táblázat adatainak betöltése
         */
        oOtipus.setCellValueFactory(new PropertyValueFactory<>("tipus"));
        oOrendszam.setCellValueFactory(new PropertyValueFactory<>("rendszam"));
        oOnev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oOjogositvanyszam.setCellValueFactory(new PropertyValueFactory<>("jogositvanyszam"));
        oOkezdete.setCellValueFactory(new PropertyValueFactory<>("kezdete"));
        oOvege.setCellValueFactory(new PropertyValueFactory<>("vege"));
        oOfizetett.setCellValueFactory(new PropertyValueFactory<>("fizetett"));

        /**
         * a kereső eseménykezelője
         */
        beolvas();
        txtOtipus.textProperty().addListener(e -> beolvas());
        txtOrendszam.textProperty().addListener(e -> beolvas());
        txtOnev.textProperty().addListener(e -> beolvas());
        txtOjogsi.textProperty().addListener(e -> beolvas());
        txtOelvitte.textProperty().addListener(e -> beolvas());
        txtOvissza.textProperty().addListener(e -> beolvas());
        txtOfizetett.textProperty().addListener(e -> beolvas());
               
    }

    /**
     * A kapott index alapján meghatározza az autók táblában kijelölt sort és az
     * adatokat megjeleníti a szövegbeviteli mezőben, ha a kapott érték -1 kilép
     *
     * @param i
     */
    private void autokTablabol(int i) {
        if (i == -1) {
            return;
        }
        Auto a = tblAutok.getItems().get(i);
        txtAtipus.setText(a.getTipus());
        txtArendszam.setText(a.getRendszam());
        txtAszin.setText(a.getSzin());
        txtAevjarat.setText("" + a.getEvjarat());
        txtAnapidij.setText("" + a.getNapidij());
    }

    /**
     * A kapott index alapján meghatározza a bérlők táblában kijelölt sort és az
     * adatokat megjeleníti a szövegbeviteli mezőben, ha a kapott érték -1 kilép
     *
     * @param i
     */
    private void berlokTablabol(int i) {
        if (i == -1) {
            return;
        }
        Berlo b = tblBerlok.getItems().get(i);
        txtBnev.setText(b.getNev());
        txtBjogositvanyszam.setText(b.getJogositvanyszam());
        txtBtelefonszam.setText(b.getTelefonszam());
        txtBcim.setText(b.getCim());
    }

    /**
     * A kapott index alapján meghatározza és beállítja a bérlő
     * jogosítványszámát a kiadásnál
     *
     * @param i
     */
    private void berloJogsi(int i) {
        if (i == -1) {
            return;
        }
        cbxKjogositvanyszam.setText(tblBerlok.getItems().get(i).getJogositvanyszam());
    }

    /**
     * A kapott index alapján meghatározza a lehetséges rendszámokat a
     * kiválasztott autó tipushoz és a kiadásnál a combobox-ban listázza az
     * eredményt
     *
     * @param i
     */
    private void tipusRendszam(int i) {
        if (i == -1) {
            return;
        }
        cbxKrendszam.getItems().clear();
        for (int j = 0; j < tblAutok.getItems().size(); j++) {
            Auto a = tblAutok.getItems().get(j);
            if (cbxKtipus.getValue().equals(a.getTipus())) {
                cbxKrendszam.getItems().add(a.getRendszam());
            }
        }

    }

    /**
     * Feluró hibaablak
     *
     * @param cim A felugró ablak címe
     * @param uzenet A kiírandó hibaüzenet
     */
    private void hiba(String cim, String uzenet) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.setContentText(uzenet);
        alert.showAndWait();
    }

    /**
     * Hibaüzenet ha a név 3 karakternél kevesebb
     *
     * @param nev
     */
    private boolean nevHiba(String nev) {
        if (nev.length() < 3 || nev.length() > 100) {
            hiba("Hiba", "A név minimum 3 és maximum 100 karakter lehet!");
            txtBnev.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Hibaüzenet ha a jogsi száma nem 8 kareakterből áll
     *
     * @param jogsi
     */
    private boolean jogsiHiba(String jogsi) {
        if (jogsi.length() != 8) {
            hiba("Hiba", "A jogosatvány száma 8 karakter!");
            txtBjogositvanyszam.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * hibaüzenet ha a telefonszám kevesebb mint 7 karakterből áll
     *
     * @param tel
     */
    private boolean telefonszamHiba(String tel) {
        if (tel.length() < 7 || tel.length() > 25) {
            hiba("Hiba", "Add meg a telefonszámot!");
            txtBtelefonszam.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Hibaüzenet ha a cím 8 karakternél rövidebb
     *
     * @param cim
     */
    private boolean cimHiba(String cim) {
        if (cim.length() < 8 || cim.length() > 150) {
            hiba("Hiba", "Add meg a pontos címet!");
            txtBcim.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * @param rendszam adatbázisban keresendő rendszám
     * @return autó azonosítójával (id) tér vissza
     */
    private int get_tipusId(String rendszam) {
        int i = 0;

        while (!tblAutok.getItems().get(i).getRendszam().equals(rendszam)) {
            i++;
        }
        return tblAutok.getItems().get(i).getId();
    }

    /**
     * kölcsönzés lapon
     *
     * @param jogositvanyszam adatbázisban keresendő jogsiszám
     * @return a bérlő azonosítójával (id) tér vissza
     */
    private int get_nevId(String jogositvanyszam) {
        int i = 0;

        while (!tblBerlok.getItems().get(i).getJogositvanyszam().equals(jogositvanyszam)) {
            i++;
        }

        return tblBerlok.getItems().get(i).getId();

    }

    long dij;

    /**
     * A visszaadás lapon a kijelölt sor adatainak feldolgozása, beírja a
     * szövegmezőbe az adatokat kiszámolja a bérlet díjat az eltelt napok és a
     * napidíjból
     *
     * @param i
     */
    private void visszaadasTablalbol(int i) {
        if (i == -1) {
            return;
        }

        Kolcsonzes k = tblVisszaadas.getItems().get(i);
        txtVnev.setText(k.getNev() + " / " + k.getJogositvanyszam());
        txtVtipus.setText(k.getTipus() + " / " + k.getRendszam());

        LocalDate mettol = LocalDate.parse(k.getKezdete());
        LocalDate meddig = LocalDate.now();
        long napok = ChronoUnit.DAYS.between(mettol, meddig) + 1;
        dij = napok * k.getNapidij();
        lblVdij.setText("A(z) " + napok + " napos bérelt alapján a bérleti díj: " + dij + " Ft");
        txtVfizetett.setText(dij + "");
    }

    /**
     * visszaadás lapon
     *
     * @param jogositvanyszam adatbázisban keresendő jogsiszám
     * @return a bérlő azonosítójával (id) tér vissza
     */
    private int get_id(String jogositvanyszam) {
        int i = 0;

        while (!tblVisszaadas.getItems().get(i).getJogositvanyszam().equals(jogositvanyszam)) {
            i++;
        }

        return tblVisszaadas.getItems().get(i).getId();
    }

    /**
     * igen vagy nem választó felugró ablak
     *
     * @param cim felugró ablak címe
     * @param uzenet kiírandó üzenet
     * @return true vagy false
     */
    private boolean igennem(String cim, String uzenet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.setContentText(uzenet);
        ButtonType btIgen = new ButtonType("Igen");
        ButtonType btNem = new ButtonType("Nem");
        alert.getButtonTypes().setAll(btIgen, btNem);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == btIgen;
    }

    /**
     * Összes bérlés táblázat adatainak beolvasása szűrővel
     */
    private void beolvas() {
        String sz1 = "'%" + txtOtipus.getText() + "%' ";
        String sz2 = "'%" + txtOrendszam.getText() + "%' ";
        String sz3 = "'%" + txtOnev.getText() + "%' ";
        String sz4 = "'%" + txtOjogsi.getText() + "%' ";
        String sz5 = "'%" + txtOelvitte.getText() + "%' ";
        String sz6 = "'%" + txtOvissza.getText() + "%' ";
        String sz7 = "'%" + txtOfizetett.getText() + "%' ";
        
        String s = "SELECT kolcsonzesek.id, autok.tipus, autok.rendszam, berlok.nev, "
                + "berlok.jogositvanyszam, kolcsonzesek.kezdete, kolcsonzesek.vege, "
                + "kolcsonzesek.fizetett FROM kolcsonzesek "
                + "JOIN autok ON kolcsonzesek.autoid=autok.id "
                + "JOIN berlok ON kolcsonzesek.berloid=berlok.id "
                + "WHERE autok.tipus LIKE " + sz1
                + "AND autok.rendszam LIKE " + sz2
                + "AND berlok.nev LIKE " + sz3
                + "AND berlok.jogositvanyszam LIKE " + sz4
                + "AND kolcsonzesek.kezdete LIKE " + sz5;
        
        if (!txtOvissza.getText().isEmpty() || !txtOfizetett.getText().isEmpty()) {
            s += "AND kolcsonzesek.vege LIKE " + sz6
                    + "AND kolcsonzesek.fizetett LIKE " + sz7;
        }

        s += "ORDER BY autok.tipus, berlok.nev, kolcsonzesek.kezdete DESC;";

        ab.osszesBe(tblOsszes.getItems(), s);
    }
}
