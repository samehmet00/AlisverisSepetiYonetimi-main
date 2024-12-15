package org.example.denemeson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ProductStorage {
    private static final String FILE_PATH = "/Users/mehmetsa00/Downloads/AlisverisSepetiY-netimi-main/src/main/resources/org/example/denemeson/productStorage/ProductStorage.txt";

    //Ürünleri dosyadan oku
    public static void urunOkuma() {


        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                int productId = Integer.parseInt(parts[0]);
                String productName = (parts[1]);
                double productPrice = Double.parseDouble(parts[2]);
                int productStock = Integer.parseInt(parts[3]);
                String productDurum = (parts[4]);

                AdminEkranıController.veri.root = AdminEkranıController.veri.urunEkle(AdminEkranıController.veri.root,productId,productName,productPrice,productStock,productDurum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void ağacıDolaşma(UrunKutusu node){

        if (node != null) {
            ağacıDolaşma(node.sol);
            ürünleriTxtYaz(node.product);
            ağacıDolaşma(node.sağ);
        }
    }

    public void yazmaIslemiCalistir(UrunKutusu node){
        txtTemizleme();
        ağacıDolaşma(node);
    }

    public static void txtTemizleme(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/emirh/OneDrive/Masaüstü/DenemeSon/src/main/resources/org/example/denemeson/productStorage/ProductStorage.txt"))) {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Ürünleri dosyaya yaz
    public static void ürünleriTxtYaz(Product product) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH,true))){

                String productName = product.getName();
                String productPrice = String.valueOf(product.getPrice());
                String productStock = String.valueOf(product.getStock());
                String productDurum = product.getDurum();
                String productId = String.valueOf(product.getId());

                writer.write(productId +","+productName +","+ productPrice+","+productStock +","+productDurum+"\n");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Yeni Ürün ekle
    public static void ürünEkle(Product product) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(product.toString());
            writer.newLine();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }




}


