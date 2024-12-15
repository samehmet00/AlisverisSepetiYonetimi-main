package org.example.denemeson;

import javafx.collections.ObservableList;

public class VeriIşlemleri {
    UrunKutusu root;

    public UrunKutusu urunEkle(UrunKutusu node, int id, String name, double price, int stock, String durum) {

        if (node == null)
            return (new UrunKutusu(id, name, price, stock, durum));

        if (id < node.product.getId())
            node.sol = urunEkle(node.sol,id, name, price, stock, durum);
        else if (id > node.product.getId())
            node.sağ = urunEkle(node.sağ, id, name, price, stock, durum);
        else
            return node;

        node.height = 1 + max(height(node.sol), height(node.sağ));

        int balance = dengeBoyu(node);

        if (balance > 1 && id < node.sol.product.getId())
            return sağRotasyon(node);

        if (balance < -1 && id > node.sağ.product.getId())
            return solRotasyon(node);

        if (balance > 1 && id > node.sol.product.getId()) {
            node.sol = solRotasyon(node.sol);
            return sağRotasyon(node);
        }

        if (balance < -1 && id < node.sağ.product.getId()) {
            node.sağ = sağRotasyon(node.sağ);
            return solRotasyon(node);
        }

        return node;
    }

    public UrunKutusu urunSil(UrunKutusu root, int id) {
        if (root == null)
            return root;

        if (id < root.product.getId())
            root.sol = urunSil(root.sol, id);
        else if (id > root.product.getId())
            root.sağ = urunSil(root.sağ, id);
        else {
            if ((root.sol == null) || (root.sağ == null)) {
                UrunKutusu temp = null;
                if (temp == root.sol)
                    temp = root.sağ;
                else
                    temp = root.sol;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;
            } else {
                UrunKutusu temp = minimumDeğer(root.sağ);
                root.product.setId(temp.product.getId());
                root.sağ = urunSil(root.sağ, temp.product.getId());
            }
        }

        if (root == null)
            return root;

        root.height = max(height(root.sol), height(root.sağ)) + 1;
        int balance = dengeBoyu(root);

        if (balance > 1 && dengeBoyu(root.sol) >= 0)
            return sağRotasyon(root);

        if (balance > 1 && dengeBoyu(root.sağ) < 0) {
            root.sol = solRotasyon(root.sol);
            return sağRotasyon(root);
        }

        if (balance < -1 && dengeBoyu(root.sağ) <= 0)
            return solRotasyon(root);

        if (balance < -1 && dengeBoyu(root.sağ) > 0) {
            root.sağ = sağRotasyon(root.sağ);
            return solRotasyon(root);
        }

        return root;
    }

    public void urunGuncelle(UrunKutusu node, int bulunacakId, String name, double price, int stock, String durum){
        while(node!=null){
            if(node.product.getId()==bulunacakId){
                node.product.setName(name);
                node.product.setPrice(price);
                node.product.setStock(stock);
                node.product.setDurum(durum);
            }
            if(node.product.getId()>bulunacakId){
                node = node.sol;
            }else{
                node = node.sağ;
            }
        }}


    public void ağacıDolaşma(UrunKutusu node){

        if (node != null) {
            ağacıDolaşma(node.sol);
            System.out.println(node.product.getId());
            ağacıDolaşma(node.sağ);
        }
    }




    public int height(UrunKutusu yeni){
        if (yeni == null) {
            return 0;
        }else {
            return yeni.height;
        }
    }

    public int max(int a, int b){
        if(a<b){
            return b;
        }return a;
    }

    public int dengeBoyu(UrunKutusu yeni){
        if(yeni == null) {
            return 0;
        }return height(yeni.sol)-height(yeni.sağ);
    }

    public UrunKutusu sağRotasyon(UrunKutusu y){
        UrunKutusu x = y.sol;
        UrunKutusu T2 = x.sağ;

        x.sağ = y;
        y.sol = T2;

        y.height = max(height(y.sol), height(y.sağ)) + 1;
        x.height = max(height(x.sol), height(x.sağ)) + 1;

        return x;
    }

    public UrunKutusu solRotasyon(UrunKutusu x) {
        UrunKutusu y = x.sağ;
        UrunKutusu T2 = y.sol;

        y.sol = x;
        x.sağ = T2;

        x.height = max(height(x.sol), height(x.sağ)) + 1;
        y.height = max(height(y.sol), height(y.sağ)) + 1;

        return y;
    }

    public UrunKutusu minimumDeğer(UrunKutusu node) {
        UrunKutusu işaretci = node;
        while (işaretci.sol != null)
            işaretci = işaretci.sol;
        return işaretci;
    }
}
