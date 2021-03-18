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

import java.util.ArrayList;


public class Transaksi {
    private String noTransaksi;
    private String namaPemesan;
    private String tanggal;
    private String noMeja;
    private ArrayList<Pesanan> pesanan;
    private double uangBayar;
    private double pajak;
    private double biayaService;
    private double totalBayar;

    public Transaksi(String noTransaksi, String namaPemesan, String tanggal, String noMeja){
        this.noTransaksi = noTransaksi;
        this.namaPemesan = namaPemesan;
        this.tanggal = tanggal;
        this.noMeja = noMeja;
        
        pesanan = new ArrayList<>();
    }

    public void tambahPesanan (Pesanan pesanan) {
        this.pesanan.add(pesanan);
    }
    
//    public Pesanan getPesanan () {
//        return null;
//    }
    
    public ArrayList<Pesanan> getSemuaPesanan () {
        return this.pesanan;
    }
    
    public void cetakStruk () {
        System.out.println("\n======== Ryo Ramen ========");
        System.out.println("No Transaksi : " + noTransaksi);
        System.out.println("Pemesan : " + this.namaPemesan);
        System.out.println("Tanggal : " + this.tanggal);
        
        // Cek jika nomor meja kosong, berarti take away
        if(this.noMeja.equals("")){
            this.noMeja = "Take Away";
        }
        
        System.out.println("Meja : " + this.noMeja);
        System.out.println("============================");
        for (int i = 0; i < pesanan.size(); i++) {
            Pesanan psn = pesanan.get(i);
            Menu m = psn.getMenu();
            String pesananStruk = psn.getJumlah() + " " + m.getNama_menu() + "\t" + (m.getHarga() * psn.getJumlah());
            
            // Jika pesanan kuah, tambah spasi di awal 2
            if(m.getKategori().equals("Kuah")){
                pesananStruk = "  " + pesananStruk;
            }
            
            // Tampilkan pesanan
            System.out.println(pesananStruk);
        }
    }
    
    // Tambahkan
    public void setBiayaService(double biayaService){
        this.biayaService = biayaService;
    }
    
    public void setPajak(double pajak){
        this.pajak = pajak;
    }
    
    public double hitungTotalPesanan(){
        for (int i = 0; i < pesanan.size(); i++) {
            Pesanan psn = pesanan.get(i);
            double harga = psn.getMenu().getHarga();
            this.totalBayar += (harga * psn.getJumlah());
        }
        
        return this.totalBayar;
    }
    
    public double hitungPajak(){
        return this.totalBayar * this.pajak;
    }
    
    public double hitungBiayaService(){
        return this.totalBayar * this.biayaService;
    }
    
    public double hitungTotalBayar(double pajak, double biayaService){
        this.totalBayar = this.totalBayar + pajak + biayaService;
        return this.totalBayar;
    }
    
    public double hitungKembalian (double uangBayar) {
        return uangBayar - this.totalBayar;
    }
}
