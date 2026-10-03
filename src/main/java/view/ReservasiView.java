package view;

import java.util.ArrayList;
import model.Reservasi;

public class ReservasiView {

    public void tampilkanMenu() {
        System.out.println("\n==============================");
        System.out.println("   TRACKING RESERVASI HOTEL");
        System.out.println("==============================");
        System.out.println("1. Tambah Reservasi");
        System.out.println("2. Tampilkan Reservasi");
        System.out.println("3. Check-In Tamu");
        System.out.println("4. Check-Out Tamu");
        System.out.println("5. Update Reservasi");
        System.out.println("6. Hapus Reservasi");
        System.out.println("0. Keluar");
    }

    public void tampilkanMenuTampilkan() {
        System.out.println("\n--- TAMPILKAN RESERVASI ---");
        System.out.println("1. Tampilkan Semua Reservasi");
        System.out.println("2. Tampilkan Berdasarkan Status");
        System.out.println("0. Kembali");
    }

    public void tampilkanMenuStatus() {
        System.out.println("\n--- PILIH STATUS ---");
        System.out.println("1. Menunggu");
        System.out.println("2. Check-In");
        System.out.println("3. Check-Out");
        System.out.println("0. Kembali");
    }

    public void tampilkanPilihanTipe() {
        System.out.println("1. Standard (Rp300.000/malam, denda batal 20%)");
        System.out.println("2. VIP (Rp750.000/malam + biaya penyambutan)");
    }

    public void tampilkanPilihanLayanan() {
        System.out.println("1. Reguler");
        System.out.println("2. Executive Lounge (+Rp100.000)");
        System.out.println("3. Airport Pickup + Lounge (+Rp200.000)");
    }

    public void tampilkanMenuUpdate(boolean vip) {
        System.out.println("\n--- UPDATE RESERVASI ---");
        System.out.println("1. Update Nama");
        System.out.println("2. Update Jumlah Malam");
        if (vip) {
            System.out.println("3. Update Layanan Tambahan");
        }
        System.out.println("0. Kembali");
    }

    // OVERLOADING versi 1: hanya menerima daftar, menampilkan semua data
    public void tampilkanDaftar(ArrayList<Reservasi> daftar) {
        System.out.println("\n--- DAFTAR RESERVASI ---");
        if (daftar.isEmpty()) {
            System.out.println("Belum ada reservasi.");
            return;
        }
        cetakTabel(daftar);
    }

    public void tampilkanDaftar(ArrayList<Reservasi> daftar, String status) {
        System.out.println("\n--- RESERVASI " + status.toUpperCase() + " ---");
        if (daftar.isEmpty()) {
            System.out.println("Tidak ada reservasi dengan status " + status + ".");
            return;
        }
        cetakTabel(daftar);
    }

    private void cetakTabel(ArrayList<Reservasi> daftar) {
        System.out.printf("%-4s | %-15s | %-9s | %-6s | %-9s | %-12s | %s%n",
                "ID", "Nama Tamu", "Tipe", "Malam", "Status", "Total Biaya", "Detail");
        System.out.println("--------------------------------------------------------------------------------");

        for (Reservasi r : daftar) {
            System.out.printf("%-4d | %-15s | %-9s | %-6d | %-9s | Rp%-10d | %s%n",
                    r.getIdReservasi(), r.getNamaTamu(), r.getTipe(),
                    r.getJumlahMalam(), r.getStatus(), r.hitungTotalBiaya(), r.getDetailTambahan());
        }
    }

    public void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }
}