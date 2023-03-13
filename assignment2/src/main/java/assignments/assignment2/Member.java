package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;
    public Member(String nama, String noHp) {
        this.nama = nama;
        this.noHp = noHp;
        this.id = NotaGenerator.generateId(nama, noHp);
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    public String getId() {
        return this.id;
    
    }
}
