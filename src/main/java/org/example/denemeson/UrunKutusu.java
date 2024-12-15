package org.example.denemeson;

public class UrunKutusu {
    int height;
    UrunKutusu sol;
    UrunKutusu sağ;

    Product product;

    UrunKutusu(int id, String name, double price, int stock, String durum){
        this.height = 1;
        this.sol = null;
        this.sağ = null;
        this.product = new Product(id,name, price, stock,durum);
    }
}
