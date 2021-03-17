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
public class Ramen extends Menu{
    
    //parameter nsma_ramen diganti menjadi nama_ramen (typo)
    public Ramen(String nama_ramen, double harga) {
        setNama_menu(nama_ramen); 
        setHarga(harga);
        setKategori("Ramen");
    }    
}
