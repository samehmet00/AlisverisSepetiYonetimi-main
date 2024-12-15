package org.example.denemeson;

public class Product {

    private String name;
    private double price;
    private int stock;
    private int id;
    private String durum;

    public Product( int id, String name, double price, int stock, String durum) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.id = id;
        this.durum = durum;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}
