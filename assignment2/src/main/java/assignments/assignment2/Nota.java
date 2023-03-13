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
        banyakNota++;
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
    }

    public static int getBanyakNota(){
        return banyakNota;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
}
