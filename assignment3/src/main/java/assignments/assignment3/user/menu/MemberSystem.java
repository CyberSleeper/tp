package assignments.assignment3.user.menu;
import assignments.assignment3.user.Member;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        // TODO
        if (choice == 1) {

        } else if (choice == 2) {

        } else if (choice == 3) {
            return true;
        } else {

        }
        return false;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        Member[] newMemberList = new Member[memberList.length + 1];
        for (int i = 0; i < memberList.length; i++) {
            newMemberList[i] = memberList[i];
        }
        newMemberList[memberList.length] = member;
        memberList = newMemberList;
    }
}