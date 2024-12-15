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

    private double x = 0;
    private double y = 0;

    public static VeriIşlemleri veri = new VeriIşlemleri();

    ProductStorage yazmaOkuma = new ProductStorage();

    private String[] durumListe = {"Satışta","Satıştan Kaldırıldı"};

//    public static void ürünDurumKontrolü(UrunKutusu node, ObservableList<Product> productList){
//        if (node != null) {
//            ürünDurumKontrolü(node.sol, productList);
//            if(Objects.equals(node.product.getDurum(), "Satışta")){
//                productList.add(node.product);
//            }
//            ürünDurumKontrolü(node.sağ, productList);
//        }
//    }
//
//    static ObservableList<Product> müşteriProducts = FXCollections.observableArrayList();
//    public static void müşteriEkranıProductSeçme(){
//        ürünDurumKontrolü(veri.root,müşteriProducts);
//    }




    public void urunDurumBelirleme(){
        List<String> lists = new ArrayList<>();

        for(String durumlar:durumListe){
            lists.add(durumlar);
        }
        ObservableList durumlarVeri = FXCollections.observableList(lists);
        productEkleme_durum.setItems(durumlarVeri);

    }

    public void urunEklemeBTN(ActionEvent event) {
        try {
            if (event.getSource() == productEkleme_ekleBTN) {
                tabloyaUrunEkleme();
                textleriTemizle();
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
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Ürün Başarıyla Kaydedildi");
            alert.showAndWait();
            değişiklik = false;
        }else{
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
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
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Ürün Başarıyla silindi");
                alert.showAndWait();

                ağacıDolaşma(veri.root,productList,0);
                tablodaGörüntüleme();
                değişiklik = true;



            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void tabloyaUrunEkleme(){
        try {
            Alert alert;
            //ürün ekleme verilerinin eksik olup olmadığını kontrol ediyoruz
            if (productEkleme_productID.getText().isEmpty() || productEkleme_productName.getText().isEmpty() || productEkleme_stok.getText().isEmpty() || productEkleme_price.getText().isEmpty() || productEkleme_durum.getSelectionModel().getSelectedItem()==null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen Tüm Alanları Doldurduğunuzdan Emin Olunuz");
                alert.showAndWait();
            }else{
                if (idKontrol(Integer.parseInt(productEkleme_productID.getText()))){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Seçilen ID önceden kayıtlı");
                    alert.showAndWait();
                }else{
                    agacaUrunEkleme(Integer.parseInt(productEkleme_productID.getText()),productEkleme_productName.getText(),Double.parseDouble(productEkleme_price.getText()),Integer.parseInt(productEkleme_stok.getText()),(String)productEkleme_durum.getSelectionModel().getSelectedItem());
                    ağacıDolaşma(veri.root, productList,0);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Ürün Başarıyla Kaydedildi");
                    alert.showAndWait();

                    tablodaGörüntüleme();
                }


            }
        }catch (Exception e){
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
        try{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bilgilendirici Mesaj");
            alert.setHeaderText(null);
            alert.setContentText("Çıkış Yapmak İstediğinden Emin Misin?");

            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){

                geriÇıkış.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("arayüzSınıfları/girişEkranı.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event1) ->{
                    x = event1.getSceneX();
                    y = event1.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event1) ->{
                    stage.setX(event1.getScreenX() - x);
                    stage.setY(event1.getScreenY() - y);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();;

            }else return;



        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
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
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Ürün Başarıyla güncellendi");
                    alert.showAndWait();

                    ağacıDolaşma(veri.root,productList,0);
                    tablodaGörüntüleme();
                    değişiklik = true;



                }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablodaGörüntüleme();
        urunDurumBelirleme();
        adminAdıYazdirma();

        
    }
}
