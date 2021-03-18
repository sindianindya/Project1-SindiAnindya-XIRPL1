/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import classes.*;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * @author ASUS
 */
public class MainAplikasiKasir {
    public static double PAJAK_PPN = 0.10;
    public static double BIAYA_SERVICE = 0.05;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String no_transaksi, nama_pemesan, tanggal, no_meja = "";
        String transaksi_lagi = "", pesan_lagi = "", keterangan = "", makan_ditempat;
        MainAplikasiKasir app = new MainAplikasiKasir();
        app.generateDaftarMenu();
        
        System.out.println("======== TRANSAKSI ========");
        
        do{
            // Ambil data transaksi
            System.out.println("No Transaksi : ");
            no_transaksi = input.next();
            System.out.println("Pemesan : ");
            nama_pemesan = input.next();
            System.out.println("Tanggal : [dd-mm-yyyy] ");
            tanggal = input.next();
            System.out.println("Makan ditempat? [Y/N] ");
            makan_ditempat = input.next();

            if(makan_ditempat.equalsIgnoreCase("Y")) {
                System.out.println("Nomor Meja : ");
                no_meja = input.next();
            }

            // Buat transaksi baru
            Transaksi trans = new Transaksi(no_transaksi, nama_pemesan, tanggal, no_meja);
            System.out.println("======== TRANSAKSI ========");
            do {
                // Ambil menu berdasarkan nomor urut yang dipilih
                Menu menu_yang_dipilih = app.daftarMenu.pilihMenu();
                int jumlah_pesanan = (int) app.cekInputNumber("Jumlah : ");

                // Buat pesanan
                Pesanan pesanan = new Pesanan(menu_yang_dipilih, jumlah_pesanan);
                trans.tambahPesanan(pesanan);

                // Khusus untuk menu ramen, pesanan kuahnya langsung diinput juga
                if(menu_yang_dipilih.getKategori().equalsIgnoreCase("Ramen")){
                    // Looping sesuai jumlah pesanan ramen
                    int jumlah_ramen = jumlah_pesanan;
                    do{
                        // Ambil objek menu berdasarkan nomor yang dipilih
                        Menu kuah_yang_dipilih = app.daftarMenu.pilihKuah();

                        System.out.println("Level : [0-5] ");
                        String level = input.next();

                        // Validasi jumlah kuah tidak boleh lebih besar dari ramen
                        int jumlah_kuah = 0;
                        do{
                            jumlah_kuah = (int) app.cekInputNumber("Jumlah : ");

                            if(jumlah_kuah > jumlah_ramen) {
                                System.out.println("[Err] Jumlah kuah melebih jumlah ramen yang sudah dipesan");
                            } else {
                                break;
                            }
                        } while(jumlah_kuah > jumlah_ramen);

                        // Set pesanan kuah
                        Pesanan pesanan_kuah = new Pesanan(kuah_yang_dipilih, jumlah_kuah);
                        pesanan_kuah.setKeterangan("Level " + level);

                        // Tambahkan pesanan kuah ke transaksi
                        trans.tambahPesanan(pesanan_kuah);

                        // Hitung jumlah ramen yang belum dipesan kuah nya
                        jumlah_ramen -= jumlah_kuah;  
                    } while(jumlah_ramen > 0);
                } else {
                    // Jika keterangan tidak diisi tulis -
                    System.out.println("Keterangan : [- jika kosong] ");
                    keterangan = input.next();
                }

                // Cek jika keterangan diisi selain "-" set ke pesanan
                if(!keterangan.equalsIgnoreCase("-")){
                    pesanan.setKeterangan(keterangan);
                }

                // Konfirmasi, mau tambah pesanan atau tidak
                System.out.println("Tambah pesanan lagi? [Y/N] ");
                pesan_lagi = input.next();
            } while (pesan_lagi.equalsIgnoreCase("Y"));

            // Cetak struk
            trans.cetakStruk();

            // Hitung total harga
            double total_pesanan = trans.hitungTotalPesanan();
            System.out.println("============================");
            System.out.println("Total : \t\t" + total_pesanan);

            // Hitung pajak
            trans.setPajak(PAJAK_PPN);
            double ppn = trans.hitungPajak();
            System.out.println("Pajak 10% : \t\t" + ppn);

            // Hitung biaya service
            // Jika makan ditempat, biaya pajak = 10% + 5% service
            double biaya_service = 0;
            if(makan_ditempat.equalsIgnoreCase("Y")){
                trans.setBiayaService(BIAYA_SERVICE);
                biaya_service = trans.hitungBiayaService();
                System.out.println("Biaya Service 5% : \t" + biaya_service);
            }

            // Tampilkan total bayar
            System.out.println("Total : \t\t" + trans.hitungTotalBayar(ppn, biaya_service));

            // Cek uang bayar, apakah > total bayar atau tidak
            double kembalian = 0;
            do{
                // Ambil input uang bayar
                double uang_bayar = app.cekInputNumber("Uang Bayar : \t\t");

                kembalian = trans.hitungKembalian(uang_bayar);
                if(kembalian < 0){
                    System.out.println("[Err] Uang anda kurang");
                } else{
                    System.out.println("Kembalian \t\t" + kembalian);
                }
            } while(kembalian < 0);

            System.out.println("Lakukan Transaksi Lagi? [Y/N] ");
            transaksi_lagi = input.next();
        } while(transaksi_lagi.equalsIgnoreCase("Y"));
        
        System.out.println("====== TERIMA KASIH ========");
    }

    public DaftarMenu daftarMenu;

    public void generateDaftarMenu(){
        daftarMenu = new DaftarMenu();
        daftarMenu.tambahMenu(new Ramen("Ramen Seafood", 25000));
        daftarMenu.tambahMenu(new Ramen("Ramen Original", 18000));
        daftarMenu.tambahMenu(new Ramen("Ramen Vegetarian", 22000));
        daftarMenu.tambahMenu(new Ramen("Ramen Karnivor", 28000));
        daftarMenu.tambahMenu(new Kuah("Kuah Orisinil"));
        daftarMenu.tambahMenu(new Kuah("Kuah Internasional"));
        daftarMenu.tambahMenu(new Kuah("Kuah Spicy Lada"));
        daftarMenu.tambahMenu(new Kuah("Kuah Soto Padang"));
        daftarMenu.tambahMenu(new Toping("Crab Stick Bakar", 6000));
        daftarMenu.tambahMenu(new Toping("Chicken Katsu", 8000));
        daftarMenu.tambahMenu(new Toping("Gyoza Goreng", 4000));
        daftarMenu.tambahMenu(new Toping("Bakso Goreng", 7000));
        daftarMenu.tambahMenu(new Toping("Enoki Goreng", 5000));
        daftarMenu.tambahMenu(new Minuman("Jus Aplukat SPC", 10000));
        daftarMenu.tambahMenu(new Minuman("Jus Stroberi", 11000));
        daftarMenu.tambahMenu(new Minuman("Cappucino Coffee", 15000));
        daftarMenu.tambahMenu(new Minuman("Vietnam Dripp", 14000));
        daftarMenu.tampilDaftarMenu();
    }
    
    public double cekInputNumber(String label){
       try {
           Scanner get_input = new Scanner(System.in);
           System.out.println(label);
           double nilai = get_input.nextDouble();
           
           return nilai;
       } catch(InputMismatchException ex) {
            System.out.println("Harap masukkan angka");
            return cekInputNumber(label);
        }
    }
    
}
