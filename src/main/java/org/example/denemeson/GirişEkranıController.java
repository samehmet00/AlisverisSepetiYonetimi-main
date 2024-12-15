package org.example.denemeson;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GirişEkranıController {
    @FXML
    private AnchorPane AdminHesabıOluşturma;

    @FXML
    private TextField AdminOluşturma_Adı;

    @FXML
    private ComboBox<String> AdminOlşturma_FavoriRenginiz;

    @FXML
    private Button AdminOlşturma_oluşturBtn;

    @FXML
    private Button AdminOlşturma_çıkışBtn;

    @FXML
    private PasswordField AdminOlşturma_Şifre;

    @FXML
    private Label HesabaGirişYapMTN;

    @FXML
    private Button HesapOluşturmaBTN;

    @FXML
    private Label HesapOluşturmaMTN;

    @FXML
    private AnchorPane KayanEkran;

    @FXML
    private AnchorPane KullanıcıHesabıOluşturma;

    @FXML
    private ComboBox<String> KullanıcıOlşturma_FavoriRenginiz;

    @FXML
    private Label KullanıcıOlşturma_kullanıcıAdı;

    @FXML
    private Label KullanıcıOlşturma_kullanıcıAdı1;

    @FXML
    private Button KullanıcıOlşturma_oluşturBtn;

    @FXML
    private Button KullanıcıOlşturma_çıkışBtn;

    @FXML
    private PasswordField KullanıcıOlşturma_Şifre;

    @FXML
    private AnchorPane admin_form;

    @FXML
    private Hyperlink admin_hyperlink;

    @FXML
    private Button admin_login;

    @FXML
    private PasswordField admin_password;

    @FXML
    private TextField admin_username;

    @FXML
    private TextField employee_ID;

    @FXML
    private AnchorPane employee_form;

    @FXML
    private Hyperlink employee_hyperLink;

    @FXML
    private Button employee_login;

    @FXML
    private PasswordField employee_password;

    @FXML
    private Button hesabaGirişYapBTN;

    @FXML
    private TextField kullanıcıOluşturma_Adı;

    @FXML
    private AnchorPane main_form;

    public void close(){
        System.exit(0);
    }


    private double x = 0;
    private double y = 0;


    private String[] renkler = {"Kırmızı","Turuncu","Sarı","Yeşil","Mor","Mavi","Siyah","Beyaz","Gri","Pembe","Kahverengi"};

    public void hesapOluşturmaRenkler(){
        List<String> lists = new ArrayList<>();

        for(String renk:renkler){
            lists.add(renk);
        }
        ObservableList renklerSeçenekler = FXCollections.observableList(lists);
        KullanıcıOlşturma_FavoriRenginiz.setItems(renklerSeçenekler);
        AdminOlşturma_FavoriRenginiz.setItems(renklerSeçenekler);
    }

    //Admin ve Employee girişleri arasında geçiş yapılıyor
    public void girişDeğiştirme(ActionEvent event){
        if(event.getSource() == admin_hyperlink){
            admin_form.setVisible(false);
            employee_form.setVisible(true);
        }else if(event.getSource() == employee_hyperLink){
            admin_form.setVisible(true);
            employee_form.setVisible(false);
        }
    }

    private Alert alert;
    LoginManager loginManager = new LoginManager();

    //Admin girişi kontrol yapılıyor
    public void adminLogin(ActionEvent event) {
        try {
            if (event.getSource() == admin_login) {
                if (loginManager.adminDoğrulama(admin_username.getText(), admin_password.getText())) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Admin girişi başarılı");
                    alert.showAndWait();

                    ProductStorage.urunOkuma();
                    AdminEkranıController.ağacıDolaşma(AdminEkranıController.veri.root, AdminEkranıController.productList,0);

                    kullaniciAdiVerme.admin = admin_username.getText();

                    admin_login.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("arayüzSınıfları/adminEkrani.fxml"));

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
                    stage.show();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Admin girişi başarısız");
                    alert.showAndWait();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Employee girişi kontrol ediliyor
    public void employeeLogin(ActionEvent event) {
        try {
            if (event.getSource() == employee_login) {
                if (loginManager.employeeDoğrulama(employee_ID.getText(), employee_password.getText())) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Kullanıcı girişi başarılı");
                    alert.showAndWait();

                    ProductStorage.urunOkuma();
//                    AdminEkranıController.müşteriEkranıProductSeçme();

                    admin_login.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("arayüzSınıfları/employeeEkrani.fxml"));

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

                    stage.setScene(scene);
                    stage.show();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Kullanıcı girişi başarısız");
                    alert.showAndWait();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ekranKayması(ActionEvent event){
        TranslateTransition slider = new TranslateTransition();
        if(event.getSource() == HesapOluşturmaBTN){
            slider.setNode(KayanEkran);
            slider.setToX(400);
            slider.setDuration(Duration.seconds(.5));

            KayanEkran.toFront();

            slider.setOnFinished((ActionEvent e) ->{
                hesabaGirişYapBTN.setVisible(true);
                HesapOluşturmaBTN.setVisible(false);
                HesabaGirişYapMTN.setVisible(true);
                HesapOluşturmaMTN.setVisible(false);

                if(employee_form.isVisible()){
                    employee_form.setVisible(false);
                    KullanıcıHesabıOluşturma.setVisible(true);
                }else{
                    admin_form.setVisible(false);
                    AdminHesabıOluşturma.setVisible(true);
                }

                hesapOluşturmaRenkler();
                textleriTemizle();

            });

            slider.play();
        } else if (event.getSource() == hesabaGirişYapBTN) {
            slider.setNode(KayanEkran);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            KayanEkran.toFront();

            slider.setOnFinished((ActionEvent e) ->{
                hesabaGirişYapBTN.setVisible(false);
                HesapOluşturmaBTN.setVisible(true);
                HesabaGirişYapMTN.setVisible(false);
                HesapOluşturmaMTN.setVisible(true);

                if(KullanıcıHesabıOluşturma.isVisible()){
                    employee_form.setVisible(true);
                    KullanıcıHesabıOluşturma.setVisible(false);
                }else{
                    admin_form.setVisible(true);
                    AdminHesabıOluşturma.setVisible(false);
                }

                textleriTemizle();
            });

            slider.play();
        }

    }


    public void KullanıcıHesabıOluşturma(){
        if(kullanıcıOluşturma_Adı.getText().isEmpty() || KullanıcıOlşturma_Şifre.getText().isEmpty() || KullanıcıOlşturma_FavoriRenginiz.getSelectionModel().getSelectedItem()==null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen Tüm Alanları Doldurduğunuzdan Emin Olunuz");
            alert.showAndWait();


        }else{
            if(loginManager.employeeKullanıcıEkle(kullanıcıOluşturma_Adı.getText(),KullanıcıOlşturma_Şifre.getText(),KullanıcıOlşturma_FavoriRenginiz.getValue())){
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Kullanıcı Kaydı Başarılı");
                alert.showAndWait();

                textleriTemizle();

            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Kullanıcı Zaten Aktif");
                alert.showAndWait();
            }
        }



    }

    public void adminHesabıOluşturma(){
        if(AdminOluşturma_Adı.getText().isEmpty() || AdminOlşturma_Şifre.getText().isEmpty() || AdminOlşturma_FavoriRenginiz.getSelectionModel().getSelectedItem()==null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen Tüm Alanları Doldurduğunuzdan Emin Olunuz");
            alert.showAndWait();


        }else{
            if(loginManager.adminKullanıcıEkle(AdminOluşturma_Adı.getText(),AdminOlşturma_Şifre.getText(),AdminOlşturma_FavoriRenginiz.getValue())){
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Admin Kaydı Başarılı");
                alert.showAndWait();

                textleriTemizle();

            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Admin Zaten Aktif");
                alert.showAndWait();
            }
    }}

    public void textleriTemizle(){
        admin_username.clear();
        admin_password.clear();
        employee_ID.clear();
        employee_password.clear();
        KullanıcıOlşturma_FavoriRenginiz.setValue(null);
        kullanıcıOluşturma_Adı.clear();
        KullanıcıOlşturma_FavoriRenginiz.setValue(null);
        KullanıcıOlşturma_Şifre.clear();
    }

    public void initialize(){

    }

}