/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author ASUS
 */
public class Pesanan {
    private Menu menu;
    private int jumlah;
    private String keterangan;

    public Pesanan (Menu menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
    }
    
    public Menu getMenu () {
        return this.menu;
    }
    
    public int getJumlah () {
        return this.jumlah;
    }
  
    public void setKeterangan (String keterangan) {}
    
    public String getKeterangan () {
        return this.keterangan;
    }
}
