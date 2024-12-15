package org.example.denemeson;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MusteriEkraniController implements Initializable {


    @FXML
    private AnchorPane ana_ekran;

    @FXML
    private TextField sepetÜrünId;

    @FXML
    private Button close;

    @FXML
    private Button cıkıs;

    @FXML
    private Button fişGüncelleBTN;

    @FXML
    private Button fişKaldırBTN;

    @FXML
    private Label fişToplam;

    @FXML
    private Spinner<Integer> fişUrunMiktar;

    @FXML
    private Button fişÖdemeBTN;

    @FXML
    private Button minimize;

    @FXML
    private TableColumn<Product, String> purchase_co_fiyat;

    @FXML
    private TableColumn<Product, String> purchase_co_isim;

    @FXML
    private TableColumn<Product, String> purchase_co_urunId;

    @FXML
    private TableColumn<Product, String> purchase_co_miktar;

    @FXML
    private Button purchase_eklebtn;

    @FXML
    private TextField purchase_ürünId;

    @FXML
    private Spinner<Integer> purchase_miktar;

    @FXML
    private Label purchase_musteriId;

    @FXML
    private Button purchase_odeme;

    @FXML
    private TableView<Product> purchase_tableview;

    @FXML
    private Label purchase_toplam;

    @FXML
    private Label purchase_toplam1;

    @FXML
    private AnchorPane sepet;

    @FXML
    private Button anasayfaBtn;

    @FXML
    private AnchorPane anasayfaForm;

    @FXML
    private Button sepetBtn;

    @FXML
    private Label sepetÜnlem;

    @FXML
    private Label sepetBoş;

    @FXML
    private TableView<Product> sepetTablo;

    @FXML
    private TableColumn<Product, String> sepetTablo_fiyat;

    @FXML
    private TableColumn<Product, String> sepetTablo_urunID;

    @FXML
    private TableColumn<Product, String> sepetTablo_urunMiktar;

    @FXML
    private TableColumn<Product, String> sepetTablo_urunİsmi;

    @FXML
    private Button satınAlBTN;

    @FXML
    private TextField sepetÜrünİsmi;

    @FXML
    private TextField purchase_ürünİsmi;

    @FXML
    private AnchorPane satınAlım;


    private SpinnerValueFactory spinner;

    ObservableList<Product> müşteriProducts = FXCollections.observableArrayList();
    public void müşteriEkranıProductSeçme(){
        ürünDurumKontrolü(AdminEkranıController.veri.root,müşteriProducts);
    }

    public void sepetUyarı(){
        if(sepetProductList.isEmpty()){
            sepetÜnlem.setVisible(false);
        }else{
            sepetÜnlem.setVisible(true);
        }
    }

    public void sepetGüncellemeMiktar(UrunKutusu node, ObservableList<Product> productList,int i){
        if (i == 0){
            productList.clear();
        }
        if (node != null) {
            ürünDurumKontrolü(node.sol, productList);
            if (Objects.equals(node.product.getDurum(), "Satışta")) {
                // Ürün zaten listede değilse ekle
                if (!ürünListedeVarMı(node.product, productList)) {
                    productList.add(node.product);
                }
            }
        }
    }

    public void ürünDurumKontrolü(UrunKutusu node, ObservableList<Product> productList){
        if (node != null) {
            ürünDurumKontrolü(node.sol, productList);
            if (Objects.equals(node.product.getDurum(), "Satışta" )) {
                if(!(Objects.equals(node.product.getStock(), 0 ))){
                    // Ürün zaten listede değilse ekle
                    if (!ürünListedeVarMı(node.product, productList)) {
                        productList.add(node.product);
                    }
                }
            }
            ürünDurumKontrolü(node.sağ, productList);
        }
    }

    public boolean ürünListedeVarMı(Product product, ObservableList<Product> productList) {
        // Listenin içinde ürünün olup olmadığını kontrol et
        return productList.stream().anyMatch(p -> Objects.equals(p.getName(), product.getName()));
    }

    public void satınAlımTabloMiktarSeçimi(){
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 1);
        purchase_miktar.setValueFactory(spinner);
    }

    public void tablodanUrunSeçme(){

            Product prod = purchase_tableview.getSelectionModel().getSelectedItem();
            int num = purchase_tableview.getSelectionModel().getSelectedIndex();
            if((num-1) < -1){
                return;
            }

            purchase_ürünId.setText(String.valueOf(prod.getId()));
            purchase_ürünId.setEditable(false);
            purchase_ürünİsmi.setText(prod.getName());
            purchase_ürünİsmi.setEditable(false);

            int miktar = prod.getStock();

            spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, miktar, 0);
            purchase_miktar.setValueFactory(spinner);


    }

    public void sepettenUrunSeçne(){
        Product prod = sepetTablo.getSelectionModel().getSelectedItem();
        int num = sepetTablo.getSelectionModel().getSelectedIndex();
        if((num-1) < -1){
            return;
        }

        sepetÜrünId.setText(String.valueOf(prod.getId()));
        sepetÜrünId.setEditable(false);
        sepetÜrünİsmi.setText(prod.getName());
        sepetÜrünİsmi.setEditable(false);

        int miktar = prod.getStock();

        fişUrunMiktar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, miktar,miktar));
    }

    VeriIşlemleri sepetVerisi = new VeriIşlemleri();

    ObservableList<Product> sepetProductList = FXCollections.observableArrayList();
    public void sepeteEkleBtn(){
        try {
            Alert alert;
            //ürün ekleme verilerinin eksik olup olmadığını kontrol ediyoruz
            if (purchase_ürünId.getText().isEmpty() || purchase_ürünİsmi.getText().isEmpty()|| purchase_miktar.getValue()==0)   {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen Tüm Alanları Doldurduğunuzdan Emin Olunuz");
                alert.showAndWait();
            }else{
                //bu ID de ürün kayıtlımı diye kontrol ediyoruz
                if (!idKontrol(AdminEkranıController.veri.root,Integer.parseInt(purchase_ürünId.getText()))){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Seçilen ID de ürün bulunmamaktadır");
                    alert.showAndWait();
                }else{

                    UrunKutusu işaretci = AdminEkranıController.veri.root;
                    while(işaretci!=null){
                        if(işaretci.product.getId()==Integer.parseInt(purchase_ürünId.getText())){
                            break;
                        }
                        if(işaretci.product.getId()>Integer.parseInt(purchase_ürünId.getText())){
                            işaretci = işaretci.sol;
                        }else{
                            işaretci = işaretci.sağ;
                        }
                    }





                    if(idKontrol(sepetVerisi.root,işaretci.product.getId())){
                        UrunKutusu işaretci3 = sepetVerisi.root;

                        while(işaretci3!=null){
                            if(işaretci3.product.getId()==Integer.parseInt(purchase_ürünId.getText())){
                                break;
                            }
                            if(işaretci3.product.getId()>Integer.parseInt(purchase_ürünId.getText())){
                                işaretci3 = işaretci3.sol;
                            }else{
                                işaretci3 = işaretci3.sağ;
                            }
                        }
                        işaretci3.product.setStock(işaretci3.product.getStock()+purchase_miktar.getValue());
                    }else{
                        UrunKutusu işaretci2 = new UrunKutusu(işaretci.product.getId(), işaretci.product.getName(), işaretci.product.getPrice(), işaretci.product.getStock(), işaretci.product.getDurum());
                        işaretci2.product.setStock(purchase_miktar.getValue());
                        sepetVerisi.root = sepetVerisi.urunEkle(sepetVerisi.root,işaretci2.product.getId(),işaretci2.product.getName(),işaretci2.product.getPrice(),işaretci2.product.getStock(),işaretci2.product.getDurum());
                    }


                    int i = (işaretci.product.getStock())-(purchase_miktar.getValue());
                    işaretci.product.setStock(i);



                    AdminEkranıController.ağacıDolaşma(sepetVerisi.root,sepetProductList,0);
                    müşteriEkranıProductSeçme();


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Ürün Başarıyla Sepete Eklendi");
                    alert.showAndWait();

                    textleriTemizle();


                    purchase_tableview.refresh();
                    tablodaUrunGösterimi();
                    purchaseToplamGoster();
                    sepetUyarı();
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean idKontrol(UrunKutusu node, int id){

        UrunKutusu işaretci = node;
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



    public void displayMusteriId() {
        purchase_musteriId.setText(kullaniciAdiVerme.employee);
    }

    public void textleriTemizle() {
        purchase_ürünId.clear();
        purchase_ürünİsmi.clear();
        purchase_miktar.getValueFactory().setValue(0);
    }

    private double x=0;
    private double y=0;

    public void logout(){
        try{
            Alert alert;
            if(!(sepetProductList.isEmpty())){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Mesaj");
                alert.setHeaderText(null);
                alert.setContentText("Sepetinizde Ürünler Var");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Bilgilendirici Mesaj");
                alert.setHeaderText(null);
                alert.setContentText("Çıkış Yapmak İstediğinden Emin Misin?");


                Optional<ButtonType> option = alert.showAndWait();



                if(option.get().equals(ButtonType.OK)){

                    cıkıs.getScene().getWindow().hide();

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
            }





        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage)ana_ekran.getScene().getWindow();
        stage.setIconified(true);
    }

    public void ekranDeğiş(ActionEvent event){
        if(event.getSource() == satınAlBTN){
            sepet.setVisible(false);
            satınAlım.setVisible(true);
            anasayfaForm.setVisible(false);
            satınAlBTN.setStyle("-fx-background-color:linear-gradient(to top right,#4336d7,#bab8d4)");
            sepetBtn.setStyle("-fx-background-color:Transparent");
            anasayfaBtn.setStyle("-fx-background-color:transparent");
            müşteriEkranıProductSeçme();
            sepetTxtTemizleme();
            textleriTemizle();
        }else if(event.getSource() == sepetBtn || event.getSource() == purchase_odeme){
            sepet.setVisible(true);
            satınAlım.setVisible(false);
            anasayfaForm.setVisible(false);
            if(sepetProductList.isEmpty()){
                sepetBoş.setVisible(true);
                sepetTablo.setVisible(false);
            }else{
                sepetBoş.setVisible(false);
                sepetTablo.setVisible(true);
            }
            sepetBtn.setStyle("-fx-background-color:linear-gradient(to top right,#4336d7,#bab8d4)");
            satınAlBTN.setStyle("-fx-background-color:Transparent");
            anasayfaBtn.setStyle(("-fx-background-color:transparent"));
            müşteriEkranıProductSeçme();
            sepetTxtTemizleme();
            textleriTemizle();
        } else if (event.getSource() == anasayfaBtn) {
            sepet.setVisible(false);
            satınAlım.setVisible(false);
            anasayfaForm.setVisible(true);
            sepetBtn.setStyle("-fx-background-color:Transparent");
            satınAlBTN.setStyle("-fx-background-color:Transparent");
            anasayfaBtn.setStyle(("-fx-background-color:linear-gradient(to top right,#4336d7,#bab8d4)"));
            müşteriEkranıProductSeçme();
            sepetTxtTemizleme();
            textleriTemizle();

            
        }

    }

    public void sepetGüncelle() {
        int arananId = Integer.parseInt(sepetÜrünId.getText());

        UrunKutusu işaretci = sepetVerisi.root;
        while (işaretci != null) {
            if (işaretci.product.getId() == arananId) {
                break;
            }
            if (işaretci.product.getId() > arananId) {
                işaretci = işaretci.sol;
            } else {
                işaretci = işaretci.sağ;
            }
        }

        UrunKutusu işaretci2 = AdminEkranıController.veri.root;
        while (işaretci2 != null) {
            if (işaretci2.product.getId() == arananId) {
                break;
            }
            if (işaretci2.product.getId() > arananId) {
                işaretci2 = işaretci2.sol;
            } else {
                işaretci2 = işaretci2.sağ;
            }
        }

        int miktar = fişUrunMiktar.getValue();

        if (miktar == işaretci.product.getStock()) {
            sepetVerisi.root = sepetVerisi.urunSil(sepetVerisi.root,arananId);
            işaretci2.product.setStock(işaretci2.product.getStock() + miktar);
        } else {
            işaretci2.product.setStock(işaretci2.product.getStock() + miktar);
            işaretci.product.setStock(işaretci.product.getStock() - miktar);
        }

        sepetÜrünId.clear();
        sepetÜrünİsmi.clear();
        fişUrunMiktar.getValueFactory().setValue(0);

        // Tabloyu yeniden güncelle
        AdminEkranıController.ağacıDolaşma(sepetVerisi.root, sepetProductList, 0);
        sepetGüncellemeMiktar(sepetVerisi.root, sepetProductList, 0);
        purchase_tableview.refresh();
        sepetTablo.refresh();
        System.out.println(sepetProductList);

        sepettablodaUrunGösterimi();
        purchaseToplamGoster();
        sepetUyarı();
        if(sepetProductList.isEmpty()){
            sepetBoş.setVisible(true);
            sepetTablo.setVisible(false);
        }else{
            sepetBoş.setVisible(false);
            sepetTablo.setVisible(true);
        }


    }

    ProductStorage yazmaOkuma = new ProductStorage();

    public void sepetKaldırBtn(){
        int arananId = Integer.parseInt(sepetÜrünId.getText());
        int miktar = fişUrunMiktar.getValue();

        UrunKutusu işaretci = sepetVerisi.root;
        while (işaretci != null) {
            if (işaretci.product.getId() == arananId) {
                break;
            }
            if (işaretci.product.getId() > arananId) {
                işaretci = işaretci.sol;
            } else {
                işaretci = işaretci.sağ;
            }
        }

        UrunKutusu işaretci2 = AdminEkranıController.veri.root;
        while (işaretci2 != null) {
            if (işaretci2.product.getId() == arananId) {
                break;
            }
            if (işaretci2.product.getId() > arananId) {
                işaretci2 = işaretci2.sol;
            } else {
                işaretci2 = işaretci2.sağ;
            }
        }

        sepetÜrünId.clear();
        sepetÜrünİsmi.clear();
        fişUrunMiktar.getValueFactory().setValue(0);


        sepetVerisi.root = sepetVerisi.urunSil(sepetVerisi.root,arananId);
        işaretci2.product.setStock(işaretci2.product.getStock() + miktar);

        AdminEkranıController.ağacıDolaşma(sepetVerisi.root, sepetProductList, 0);
        sepetGüncellemeMiktar(sepetVerisi.root, sepetProductList, 0);
        purchase_tableview.refresh();
        sepetTablo.refresh();
        System.out.println(sepetProductList);

        sepettablodaUrunGösterimi();
        sepetUyarı();
        purchaseToplamGoster();
        if(sepetProductList.isEmpty()){
            sepetBoş.setVisible(true);
            sepetTablo.setVisible(false);
        }else{
            sepetBoş.setVisible(false);
            sepetTablo.setVisible(true);
        }


    }

    public void sepetTxtTemizleme(){
        sepetÜrünId.clear();
        sepetÜrünİsmi.clear();
        fişUrunMiktar.getValueFactory().setValue(0);

    }

    public void ödemeBtn(){
        Alert alert;
        if(sepetProductList.isEmpty()){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Sepet Boş");
            alert.showAndWait();
        }else{

            sepetVerisi.root = null;

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Ödeme İşlemi Başarılı");
            alert.showAndWait();

            sepetÜrünId.clear();
            sepetÜrünİsmi.clear();
            fişUrunMiktar.getValueFactory().setValue(0);

            AdminEkranıController.ağacıDolaşma(sepetVerisi.root, sepetProductList, 0);
            sepetGüncellemeMiktar(sepetVerisi.root, sepetProductList, 0);
            purchase_tableview.refresh();
            sepetTablo.refresh();
            System.out.println(sepetProductList);

            sepettablodaUrunGösterimi();
            purchaseToplamGoster();
            yazmaOkuma.yazmaIslemiCalistir(AdminEkranıController.veri.root);
            sepetUyarı();
            if(sepetProductList.isEmpty()){
                sepetBoş.setVisible(true);
                sepetTablo.setVisible(false);
            }else{
                sepetBoş.setVisible(false);
                sepetTablo.setVisible(true);
            }


        }
    }






    public void tablodaUrunGösterimi(){


        purchase_co_urunId.setCellValueFactory(new PropertyValueFactory<>("id"));
        purchase_co_miktar.setCellValueFactory(new PropertyValueFactory<>("stock"));
        purchase_co_isim.setCellValueFactory(new PropertyValueFactory<>("name"));
        purchase_co_fiyat.setCellValueFactory(new PropertyValueFactory<>("price"));

        purchase_tableview.setItems(müşteriProducts);

    }

    public void sepettablodaUrunGösterimi(){

        sepetTablo_urunID.setCellValueFactory(new PropertyValueFactory<>("id"));
        sepetTablo_urunİsmi.setCellValueFactory(new PropertyValueFactory<>("name"));
        sepetTablo_urunMiktar.setCellValueFactory(new PropertyValueFactory<>("stock"));
        sepetTablo_fiyat.setCellValueFactory(new PropertyValueFactory<>("price"));

        sepetTablo.setItems(sepetProductList);
    }

    public void purchaseToplamGoster() {
        double toplam = 0;

        // Tablodaki ürünlerin fiyatlarını al ve toplamı hesapla
        for (Product product : sepetTablo.getItems()) {
            toplam += product.getPrice() * product.getStock(); // Fiyat * Miktar
        }

        // Toplam fiyatı Label üzerinde göster
        purchase_toplam.setText(String.format(toplam+"₺"));
        purchase_toplam1.setText(String.format(toplam+"₺"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        purchaseToplamGoster();
        displayMusteriId();
        tablodaUrunGösterimi();
        satınAlımTabloMiktarSeçimi();
        sepetUyarı();
        sepettablodaUrunGösterimi();
    }
}
