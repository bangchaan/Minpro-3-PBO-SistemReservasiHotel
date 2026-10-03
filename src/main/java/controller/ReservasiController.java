package controller;

import java.util.ArrayList;
import java.util.Scanner;
import model.DapatDidenda;
import model.Reservasi;
import model.ReservasiStandard;
import model.ReservasiVIP;
import view.ReservasiView;

public class ReservasiController {

    private static final int MAKS_ID = 10000;
    private ArrayList<Reservasi> daftarReservasi = new ArrayList<>();
    private ReservasiView view;
    private Scanner scanner;
    private int idBerikutnya = 1;

    public ReservasiController(ReservasiView view, Scanner scanner) {
        this.view = view;
        this.scanner = scanner;
        isiDummyData();
    }

    private void isiDummyData() {
        buatStandard("Karina", 2);

        buatVip("Bang Chan", 3, ReservasiVIP.PENYAMBUTAN_LOUNGE).checkIn();

        Reservasi selesai = buatStandard("Honey bee", 1);
        selesai.checkIn();
        selesai.checkOut();
    }

    private Reservasi buatStandard(String namaTamu, int jumlahMalam) {
        Reservasi r = new ReservasiStandard(idBerikutnya, namaTamu, jumlahMalam);
        idBerikutnya++;
        daftarReservasi.add(r);
        return r;
    }

    private Reservasi buatVip(String namaTamu, int jumlahMalam, String jenisPenyambutan) {
        Reservasi r = new ReservasiVIP(idBerikutnya, namaTamu, jumlahMalam, jenisPenyambutan);
        idBerikutnya++;
        daftarReservasi.add(r);
        return r;
    }

    public void jalankan() {
        boolean berjalan = true;

        while (berjalan) {
            view.tampilkanMenu();
            int pilihan = inputAngka("Pilih menu: ", 0, 6);

            switch (pilihan) {
                case 1:
                    tambahReservasi();
                    break;
                case 2:
                    menuTampilkanReservasi();
                    break;
                case 3:
                    checkInReservasi();
                    break;
                case 4:
                    checkOutReservasi();
                    break;
                case 5:
                    updateReservasi();
                    break;
                case 6:
                    hapusReservasi();
                    break;
                case 0:
                    berjalan = false;
                    view.tampilkanPesan("Terima kasih!");
                    break;
            }
        }
    }

    private void menuTampilkanReservasi() {
    boolean kembali = false;

    while (!kembali) {
        view.tampilkanMenuTampilkan();
        int pilihan = inputAngka("Pilih: ", 0, 2);

        switch (pilihan) {
            case 1:
                view.tampilkanDaftar(daftarReservasi);
                break;
            case 2:
                tampilkanBerdasarkanStatus();
                break;
            case 0:
                kembali = true;
                break;
        }
    }
}

    private void tampilkanBerdasarkanStatus() {
    boolean kembali = false;

    while (!kembali) {
        view.tampilkanMenuStatus();
        int pilihan = inputAngka("Pilih status: ", 0, 3);

        String status = "";

        switch (pilihan) {
            case 1:
                status = Reservasi.MENUNGGU;
                break;
            case 2:
                status = Reservasi.CHECK_IN;
                break;
            case 3:
                status = Reservasi.CHECK_OUT;
                break;
            case 0:
                kembali = true;
                continue;
        }

        if (!kembali) {
            ArrayList<Reservasi> hasil = filterBerdasarkanStatus(status);
            view.tampilkanDaftar(hasil, status);
        }
    }
}

    private ArrayList<Reservasi> filterBerdasarkanStatus(String status) {
        ArrayList<Reservasi> hasil = new ArrayList<>();

        for (Reservasi r : daftarReservasi) {
            if (r.getStatus().equals(status)) {
                hasil.add(r);
            }
        }
        return hasil;
    }

    private String pilihJenisPenyambutan() {
        view.tampilkanPilihanLayanan();
        int pilihan = inputAngka("Pilih layanan (1-3): ", 1, 3);

        switch (pilihan) {
            case 2:
                return ReservasiVIP.PENYAMBUTAN_LOUNGE;
            case 3:
                return ReservasiVIP.PENYAMBUTAN_AIRPORT;
            default:
                return ReservasiVIP.PENYAMBUTAN_REGULER;
        }
    }

    private void tambahReservasi() {
        System.out.println("\n--- TAMBAH RESERVASI ---");
        view.tampilkanPilihanTipe();

        int tipe = inputAngka("Pilih tipe (1-2): ", 1, 2);
        String nama = inputTeks("Nama tamu: ");
        int malam = inputAngka("Jumlah malam (1-30): ", 1, 30);

        Reservasi baru;

        if (tipe == 1) {
            baru = buatStandard(nama, malam);
        } else {
            String jenis = pilihJenisPenyambutan();
            baru = buatVip(nama, malam, jenis);
        }

        view.tampilkanPesan(">> Reservasi berhasil! ID: " + baru.getIdReservasi());
    }

    private void checkInReservasi() {
        int id = inputAngka("ID reservasi: ", 1, MAKS_ID);
        Reservasi r = cari(id);

        if (r != null && r.checkIn()) {
            view.tampilkanPesan(">> Check-in berhasil.");
        } else {
            view.tampilkanPesan(">> Gagal: ID tidak ditemukan atau status bukan Menunggu.");
        }
    }

    private void checkOutReservasi() {
        int id = inputAngka("ID reservasi: ", 1, MAKS_ID);
        Reservasi r = cari(id);

        if (r != null && r.checkOut()) {
            view.tampilkanPesan(">> Check-out berhasil.");
        } else {
            view.tampilkanPesan(">> Gagal: ID tidak ditemukan atau belum check-in.");
        }
    }

    private void updateReservasi() {
        int id = inputAngka("ID reservasi: ", 1, MAKS_ID);
        Reservasi r = cari(id);

        if (r == null) {
            view.tampilkanPesan(">> ID tidak ditemukan.");
            return;
        }

        if (Reservasi.CHECK_OUT.equals(r.getStatus())) {
            view.tampilkanPesan(">> Gagal: tamu sudah check-out.");
            return;
        }

        boolean vip = r instanceof ReservasiVIP;
        view.tampilkanMenuUpdate(vip);

        int batas = vip ? 3 : 2;
        int pilihan = inputAngka("Pilih: ", 0, batas);


        switch (pilihan) {
            case 1:
                String namaBaru = inputTeks("Nama baru: ");
                r.setNamaTamu(namaBaru);
                view.tampilkanPesan(">> Nama berhasil diperbarui.");
                break;
            case 2:
                int malamBaru = inputAngka("Jumlah malam baru (1-30): ", 1, 30);
                r.setJumlahMalam(malamBaru);
                view.tampilkanPesan(">> Jumlah malam berhasil diperbarui.");
                break;
            case 3:
                ReservasiVIP reservasiVIP = (ReservasiVIP) r;
                reservasiVIP.setJenisPenyambutan(pilihJenisPenyambutan());
                view.tampilkanPesan(">> Layanan tambahan berhasil diperbarui.");
                break;
        }
    }

    private void hapusReservasi() {
    int id = inputAngka("ID reservasi: ", 1, MAKS_ID);
    Reservasi r = cari(id);

    if (r == null) {
        view.tampilkanPesan(">> ID tidak ditemukan.");
        return;
    }

    if (r instanceof DapatDidenda) {
        if (!Reservasi.CHECK_OUT.equals(r.getStatus())) {
            int denda = ((DapatDidenda) r).hitungDenda();
            view.tampilkanPesan(">> Denda pembatalan: Rp" + String.format("%,d", denda));
        }
    }

    daftarReservasi.remove(r);
    view.tampilkanPesan(">> Reservasi berhasil dihapus.");
}

    private Reservasi cari(int id) {
        for (Reservasi r : daftarReservasi) {
            if (r.getIdReservasi() == id) {
                return r;
            }
        }
        return null;
    }

    private int inputAngka(String pesan, int min, int max) {
    while (true) {
        System.out.print(pesan);

        if (scanner.hasNextInt()) {          
            int angka = scanner.nextInt();  
            scanner.nextLine();              

            if (angka >= min && angka <= max) {
                return angka;                
            }

            System.out.println(">> Angka harus " + min + " sampai " + max + ".");

        } else {
            scanner.nextLine();              
            System.out.println(">> Input harus berupa angka.");
        }
    }
}
    

    private String inputTeks(String pesan) {
        while (true) {
            System.out.print(pesan);
            String teks = scanner.nextLine().trim();

            if (teks.isEmpty()) {
                System.out.println(">> Input tidak boleh kosong.");
            } else if (teks.length() > Reservasi.MAKS_NAMA) {
                System.out.println(">> Nama maksimal " + Reservasi.MAKS_NAMA + " karakter.");
            } else {
                return teks;
            }
        }
    }
}