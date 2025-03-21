package org.example.denemeson;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.*;

public class AdminEkranıController implements Initializable {

    @FXML
    private Button adminArayüzü_solPencere_dashboard;

    @FXML
    private Button adminArayüzü_solPencere_ürünEkle;

    @FXML
    private AnchorPane adminMain_Form;

    @FXML
    private Button geriÇıkış;

    @FXML
    private Label adminAdi;

    @FXML
    private Button kaydetBtn;

    @FXML
    private AnchorPane istatistikEkranı;

    @FXML
    private Button pencereyiKapatBTN;

    @FXML
    private TableView<Product> productEkleme_Tablo;

    @FXML
    private Button productEkleme_ekleBTN;

    @FXML
    private AnchorPane productEkleme_form;

    @FXML
    private TextField productEkleme_price;

    @FXML
    private TextField productEkleme_productID;

    @FXML
    private TextField productEkleme_productName;

    @FXML
    private TextField productEkleme_searcBTN;

    @FXML
    private Button productEkleme_silBTN;

    @FXML
    private ComboBox<String> productEkleme_durum;

    @FXML
    private TextField productEkleme_stok;

    @FXML
    private TableColumn<Product, String> productEkleme_tablo_durum;

    @FXML
    private TableColumn<Product, String> productEkleme_tablo_fiyat;

    @FXML
    private TableColumn<Product, String> productEkleme_tablo_productID;

    @FXML
    private TableColumn<Product, String> productEkleme_tablo_productName;

    @FXML
    private TableColumn<Product, String> productEkleme_tablo_stok;

    @FXML
    private Button productEkleme_güncelleBTN;

    @FXML
    private Button ürünlerBTN;

    @FXML
    private Button çıkışBTN;

    @FXML
    private Label kaydetUnlem;

    @FXML
    private Button aramaSil;

    private double x = 0;
    private double y = 0;

    public static VeriIşlemleri veri = new VeriIşlemleri();

    ProductStorage yazmaOkuma = new ProductStorage();

    ObservableList<String> durumlar = FXCollections.observableArrayList("Satışta","Satıştan Kaldırıldı");

    public void urunDurumBelirleme(){

        productEkleme_durum.setItems(durumlar);

    }

    public void kaydetUyarı(){
        kaydetUnlem.setVisible(true);
    }

    public void urunEklemeBTN(ActionEvent event) {
        try {
            if (event.getSource() == productEkleme_ekleBTN) {
                tabloyaUrunEkleme();
                değişiklik = true;

            }
        }catch (Exception e){
            e.printStackTrace();}
    }

    private boolean değişiklik = false;
    public void kaydetBtn() {
        if(değişiklik){
            yazmaOkuma.yazmaIslemiCalistir(veri.root);
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bilgilendirici Mesaj");
            alert.setHeaderText(null);
            alert.setContentText("Ürün Başarıyla Kaydedildi");
            alert.showAndWait();
            değişiklik = false;
            textleriTemizle();
            kaydetUnlem.setVisible(false);
        }else{
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bilgilendirici Mesaj");
            alert.setHeaderText(null);
            alert.setContentText("Herhangi bir değişiklik yapılmadı");
            alert.showAndWait();
        }

    }

    public void urunSilBtn(){
        try {
            Alert alert;
            //ürün silme verilerinin eksik olup olmadığını kontrol ediyoruz
            if (productEkleme_productID.getText().isEmpty() || productEkleme_productName.getText().isEmpty() || productEkleme_stok.getText().isEmpty() || productEkleme_price.getText().isEmpty() || productEkleme_durum.getSelectionModel().getSelectedItem()==null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Silinecek Ürünün Bilgileri Eksik ");
                alert.showAndWait();
            }else{
                veri.root = veri.urunSil(veri.root,Integer.parseInt(productEkleme_productID.getText()));

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bilgilendirici Mesaj");
                alert.setHeaderText(null);
                alert.setContentText("Ürün Başarıyla silindi");
                alert.showAndWait();

                ağacıDolaşma(veri.root,productList,0);
                tablodaGörüntüleme();
                değişiklik = true;
                textleriTemizle();
                kaydetUyarı();


            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void tabloyaUrunEkleme() {
        try {
            Alert alert;

            // Girişlerin eksiksiz doldurulduğunu kontrol et
            if (productEkleme_productID.getText().isEmpty() ||
                    productEkleme_productName.getText().isEmpty() ||
                    productEkleme_stok.getText().isEmpty() ||
                    productEkleme_price.getText().isEmpty() ||
                    productEkleme_durum.getSelectionModel().getSelectedItem() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR Message");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen tüm alanları doldurduğunuzdan emin olunuz.");
                alert.showAndWait();
                return; // Eksik giriş varsa işlemi sonlandır
            }

            // ID uzunluğunu kontrol et
            String productIDString = productEkleme_productID.getText();
            if (productIDString.length() != 3) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR Message");
                alert.setHeaderText(null);
                alert.setContentText("Ürün ID'si 3 karakter uzunluğunda olmalıdır.");
                alert.showAndWait();
                return; // Hatalı uzunlukta ID varsa işlemi sonlandır
            }

            // ID'nin önceden kayıtlı olup olmadığını kontrol et
            int productID = Integer.parseInt(productEkleme_productID.getText());
            if (idKontrol(productID)) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR Message");
                alert.setHeaderText(null);
                alert.setContentText("Seçilen ID önceden kayıtlı.");
                alert.showAndWait();
                return; // ID zaten kayıtlıysa işlemi sonlandır
            }

            // Verileri ekleme işlemi
            String productName = productEkleme_productName.getText();
            double price = Double.parseDouble(productEkleme_price.getText());
            int stock = Integer.parseInt(productEkleme_stok.getText());
            String status = productEkleme_durum.getSelectionModel().getSelectedItem();

            // Ürünü ağaca ekle
            agacaUrunEkleme(productID, productName, price, stock, status);

            // Ağacı dolaşarak ürün listesini güncelle
            ağacıDolaşma(veri.root, productList, 0);

            // Kullanıcıya başarı mesajı göster
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bilgilendirici Mesaj");
            alert.setHeaderText(null);
            alert.setContentText("Ürün başarıyla kaydedildi.");
            alert.showAndWait();
            textleriTemizle();

            // Tabloyu güncelle ve verileri kaydet
            tablodaGörüntüleme();
            kaydetUyarı();

        } catch (NumberFormatException e) {
            // Sayı formatı hatalarını yakala (örneğin, price veya stock yanlış girilmişse)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen Tüm Alanları Gereken Şekilde Doldurunuz!!");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adminAdıYazdirma(){
        adminAdi.setText(kullaniciAdiVerme.admin);
    }

    public boolean idKontrol(int id){

        UrunKutusu işaretci = veri.root;
        while(işaretci!=null){
            if(işaretci.product.getId()==id){
                return true;
            }
            if(işaretci.product.getId()>id){
                işaretci = işaretci.sol;
            }else{
                işaretci = işaretci.sağ;
            }
        }
        return false;
    }

    public void geriÇıkış(){
        if(kaydetUnlem.isVisible()){
            try {

                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error Message");
                alert1.setHeaderText(null);
                alert1.setContentText("Yapılan değişiklikler kaydedilmedi!!");

                Optional<ButtonType> option1 = alert1.showAndWait();

                if (option1.get().equals(ButtonType.OK)) {
                    return;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        } else {
                    try {

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Bilgilendirici Mesaj");
                        alert.setHeaderText(null);
                        alert.setContentText("Çıkış Yapmak İstediğinden Emin Misin?");

                        Optional<ButtonType> option = alert.showAndWait();

                        if (option.get().equals(ButtonType.OK)) {

                            geriÇıkış.getScene().getWindow().hide();

                            Parent root = FXMLLoader.load(getClass().getResource("arayüzSınıfları/girişEkranı.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);

                            root.setOnMousePressed((MouseEvent event1) -> {
                                x = event1.getSceneX();
                                y = event1.getSceneY();
                            });

                            root.setOnMouseDragged((MouseEvent event1) -> {
                                stage.setX(event1.getScreenX() - x);
                                stage.setY(event1.getScreenY() - y);
                            });

                            stage.initStyle(StageStyle.TRANSPARENT);

                            stage.setScene(scene);
                            stage.show();

                        } else return;


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

    public void close(){
        if (kaydetUnlem.isVisible()) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error Message");
            alert1.setHeaderText(null);
            alert1.setContentText("Yapılan değişiklikler kaydedilmedi!!");

            Optional<ButtonType> option1 = alert1.showAndWait();

            if (option1.get().equals(ButtonType.OK)) {
                return;
            }
        }
        System.exit(0);
    }


    public void aşağıAl(){
        Stage stage = (Stage)adminMain_Form.getScene().getWindow();
        stage.setIconified(true);
    }


    public void tablodaGörüntüleme(){
        productEkleme_tablo_productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productEkleme_tablo_stok.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productEkleme_tablo_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productEkleme_tablo_durum.setCellValueFactory(new PropertyValueFactory<>("durum"));
        productEkleme_tablo_fiyat.setCellValueFactory(new PropertyValueFactory<>("price"));

        productEkleme_Tablo.setItems(productList);
    }

    public void agacaUrunEkleme(int id , String name, double price, int stock, String durum){
        veri.root = veri.urunEkle(veri.root,id,name,price,stock,durum);
    }


    static ObservableList<Product> productList = FXCollections.observableArrayList();


    static public void ağacıDolaşma(UrunKutusu node, ObservableList<Product> productList,int i){
        if (i == 0){
            productList.clear();
        }
        if (node != null) {
            ağacıDolaşma(node.sol, productList,i++);
            if(node.product.getStock()==0){
                node.product.setDurum("Satıştan Kaldırıldı");
            }
            productList.add(node.product);
            ağacıDolaşma(node.sağ, productList,i++);
        }
    }

    public void tablodanUrunSeçme(){
        Product prod = productEkleme_Tablo.getSelectionModel().getSelectedItem();
        int num = productEkleme_Tablo.getSelectionModel().getSelectedIndex();
        if((num-1) < -1){
            return;
        }

        productEkleme_productID.setText(String.valueOf(prod.getId()));
        productEkleme_productID.setEditable(false);
        productEkleme_stok.setText(String.valueOf(prod.getStock()));
        productEkleme_productName.setText(prod.getName());
        productEkleme_durum.setValue(prod.getDurum());
        productEkleme_price.setText(String.valueOf(prod.getPrice()));
    }

    public void urunGüncellemeBTN(){
        try {
            Alert alert;
            //ürün güncellemenin verilerinin eksik olup olmadığını kontrol ediyoruz
            if (productEkleme_productID.getText().isEmpty() || productEkleme_productName.getText().isEmpty() || productEkleme_stok.getText().isEmpty() || productEkleme_price.getText().isEmpty() || productEkleme_durum.getSelectionModel().getSelectedItem()==null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen Tüm Alanları Doldurduğunuzdan Emin Olunuz");
                alert.showAndWait();
            }else{

                UrunKutusu işaretci = veri.root;
                while(işaretci!=null){
                    if(işaretci.product.getId()==Integer.parseInt(productEkleme_productID.getText())){
                        break;
                    }

                    if(işaretci.product.getId()>Integer.parseInt(productEkleme_productID.getText())){
                        işaretci = işaretci.sol;
                    }else{
                        işaretci = işaretci.sağ;
                    }
                }




                işaretci.product.setStock(Integer.parseInt(productEkleme_stok.getText()));
                işaretci.product.setPrice(Double.parseDouble(productEkleme_price.getText()));
                işaretci.product.setName(productEkleme_productName.getText());
                işaretci.product.setDurum((String)productEkleme_durum.getSelectionModel().getSelectedItem());

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bilgilendirici Mesaj");
                alert.setHeaderText(null);
                alert.setContentText("Ürün Başarıyla güncellendi");
                alert.showAndWait();

                ağacıDolaşma(veri.root,productList,0);
                tablodaGörüntüleme();
                değişiklik = true;
                kaydetUyarı();
                textleriTemizle();



            }

        } catch (NumberFormatException e) {
            // Sayı formatı hatalarını yakala (örneğin, price veya stock yanlış girilmişse)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen Tüm Alanları Gereken Şekilde Doldurunuz!!");
            alert.showAndWait();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void textleriTemizle() {
        productEkleme_productID.clear();
        productEkleme_productName.clear();
        productEkleme_price.clear();
        productEkleme_stok.clear();
        productEkleme_durum.setValue(null);;
    }

    public void urunArama() {
        String aramaMetni = productEkleme_searcBTN.getText().toLowerCase();

        if (aramaMetni.isEmpty()) {
            productEkleme_Tablo.setItems(productList);
            aramaSil.setVisible(false);
        } else {
            ObservableList<Product> filteredList = FXCollections.observableArrayList();

            for (Product product : productList) {
                if (product.getName().toLowerCase().contains(aramaMetni) ||
                        String.valueOf(product.getId()).contains(aramaMetni) ||
                        product.getDurum().toLowerCase().contains(aramaMetni)) {
                    filteredList.add(product);
                }
            }
            aramaSil.setVisible(true);
            productEkleme_Tablo.setItems(filteredList);
        }
    }

    public void urunAramaSil() {
        productEkleme_searcBTN.setText("");
        productEkleme_Tablo.setItems(productList);
        aramaSil.setVisible(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablodaGörüntüleme();
        urunDurumBelirleme();
        adminAdıYazdirma();


    }
}