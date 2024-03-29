package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Nota {
    private int idNota;
    private String paket;
    private Member member;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady;
    private static int banyakNota = 0;

    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        idNota = banyakNota;
        this.paket = paket;
        this.member = member;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        // inisialisasi sisa hari berdasarkan paket
        if (paket.toLowerCase().equals("express")) {
            this.sisaHariPengerjaan = 1;
        } else if (paket.toLowerCase().equals("fast")) {
            this.sisaHariPengerjaan = 2;
        } else if (paket.toLowerCase().equals("reguler")) {
            this.sisaHariPengerjaan = 3;
        }
        isReady = false;
        banyakNota++;
    }

    public static int getBanyakNota(){
        return banyakNota;
    }
    
    public boolean getIsReady() {
        return isReady;
    }

    public String getStatus() {
        // get status berdasarkan isReady
        if (isReady) {
            return "Sudah dapat diambil!";
        } else {
            return "Belum bisa diambil :(";
        }
    }

    public int getIdNota() {
        return idNota;
    }

    public void nextDay() {
        // mengurangi sisa hari sebanyak 1, paling sedikit menjadi 0
        this.sisaHariPengerjaan = Math.max(0, this.sisaHariPengerjaan - 1);
        // mengubah status ketika sisa hari = 0
        if (sisaHariPengerjaan == 0) {
            isReady = true;
        }
    }
}
