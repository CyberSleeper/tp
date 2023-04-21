package assignments.assignment3.nota;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Nota {
    private Member member;
    private String paket;
    private LaundryService[] services;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private int berat;
    private int id;
    private String tanggalMasuk;
    private String tanggalSelesai;
    private boolean isDone;
    static public int totalNota;

    public Nota(Member member, int berat, String paket, String tanggal) {
        id = totalNota;
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.tanggalMasuk = tanggal;
        // inisialisasi sisa hari berdasarkan paket
        if (paket.toLowerCase().equals("express")) {
            this.baseHarga = 12000;
            this.sisaHariPengerjaan = 1;
        } else if (paket.toLowerCase().equals("fast")) {
            this.baseHarga = 10000;
            this.sisaHariPengerjaan = 2;
        } else if (paket.toLowerCase().equals("reguler")) {
            this.baseHarga = 7000;
            this.sisaHariPengerjaan = 3;
        }
        tanggalSelesai = tanggalMasuk;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(tanggalSelesai));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, sisaHariPengerjaan);
        tanggalSelesai = sdf.format(c.getTime());
        isDone = false;
        totalNota++;
        services = new LaundryService[]{new CuciService()};
    }

    public void addService(LaundryService service){
        LaundryService[] newServices = new LaundryService[services.length + 1];
        for (int i = 0; i < services.length; i++) {
            newServices[i] = services[i];
        }
        newServices[services.length] = service;
        services = newServices;
    }

    public String kerjakan(){
        String output = "Nota " + id + " : Sudah selesai.";
        for (LaundryService service:services) {
            if (!service.isDone()) {
                output = "Nota " + id + " : " + service.doWork();
                break;
            }
        }
        this.isDone = services[services.length - 1].isDone();
        return output;
    }
    public void toNextDay() {
        if (isDone) {
            if (sisaHariPengerjaan > 0) {
                sisaHariPengerjaan--;
            }
        } else {
            sisaHariPengerjaan--;
        }
    }

    public long calculateHarga(){
        long harga = baseHarga * berat;
        for (LaundryService service:services) {
            harga += service.getHarga(berat);
        }
        if (sisaHariPengerjaan < 0) {
            harga += 2000L * sisaHariPengerjaan;
        }
        return Math.max(0, harga);
    }

    public String getNotaStatus(){
        String output = "Nota " + id + " : ";
        if (isDone) {
            output += "Sudah selesai.";
        } else {
            output += "Belum selesai.";
        }
        return output;
    }

    @Override
    public String toString(){
        String output = String.format("""
            [ID Nota = %d]
            ID    : %s
            Paket : %s
            Harga :
            %d kg x %d = %d
            tanggal terima  : %s
            tanggal selesai : %s
            --- SERVICE LIST ---"""
            , id, member.getId(), paket, berat, baseHarga, berat*baseHarga, tanggalMasuk, tanggalSelesai);
        for (LaundryService service:services) {
            output += "\n-" + service.getServiceName() + " @ Rp." + service.getHarga(berat);
        }
        output += "\nHarga Akhir: " + calculateHarga();
        if (sisaHariPengerjaan < 0) {
            output +=  " Ada kompensasi keterlambatan " + (-sisaHariPengerjaan) + " * 2000 hari";
        }
        output += "\n";
        return output;
    }

    // Dibawah ini adalah getter

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        return isDone;
    }

    public LaundryService[] getServices(){
        return services;
    }
}
