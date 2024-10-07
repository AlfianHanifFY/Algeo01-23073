import java.util.Scanner;

public class mainApp {
    public void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welkam to TUBES ALGEO 1 - PARKIRAN SIPIL");
        System.out.println("MENU\n" + //
                "1. Sistem Persamaaan Linier\n" + //
                "2. Determinan\n" + //
                "3. Matriks balikan\n" + //
                "4. Interpolasi Polinom\n" + //
                "5. Interpolasi Bicubic Spline\n" + //
                "6. Regresi linier dan kuadratik berganda\n" + //
                "7. Interpolasi Gambar (Bonus)\n" + //
                "8. Keluar\n" + //
                "");
        int choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("Pilihanmu adalah menu " + choice);
        } else if (choice == 2) {
            System.out.println("Pilihanmu adalah menu " + choice);
        } else if (choice == 3) {
            System.out.println("Pilihanmu adalah menu " + choice);
        } else if (choice == 4) {
            System.out.println("Pilihanmu adalah menu " + choice);
        } else if (choice == 5) {
            System.out.println("Pilihanmu adalah menu " + choice);
        } else if (choice == 6) {
            System.out.println("Pilihanmu adalah menu " + choice);
        } else if (choice == 7) {
            System.out.println("Pilihanmu adalah menu " + choice);
        } else if (choice == 8) {
            System.out.println("Pilihanmu adalah menu " + choice);
        } else {
            System.out.println("Pilihan tidak valid. Silakan pilih antara 1 sampai 8.");
        }

        scanner.close();
    }
}
