package assignments.assignment3.nota.service;


public class AntarService implements LaundryService{
    // variabel untuk menandai status dari service
    private boolean done = false;

    @Override
    public String doWork() {
        done = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public long getHarga(int berat) {
        return Math.max(500 * berat, 2000);
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
