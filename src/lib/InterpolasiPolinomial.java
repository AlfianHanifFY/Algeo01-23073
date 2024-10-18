package lib;

import java.lang.Math;
import java.util.Scanner;
import java.util.Locale;

public class InterpolasiPolinomial {
    public static String[] main(int pilihan) {
        Locale.setDefault(Locale.US); // Set Locale ke US agar input desimal menggunakan titik (.)
        Scanner scanner = new Scanner(System.in);

        Matrix matrix;
        int n;
        double xi;

        if (pilihan == 1) {
            // Input dari keyboard
            System.out.print("\nMasukkan jumlah titik: ");
            n = validasiInputJumlahTitik(scanner); // Input jumlah titik

            // Input matrix
            matrix = new Matrix(n, 2);
            System.out.print("\nMasukkan " + n + " titik (x y):\n");
            matrix.readMatrix();

            // Input nilai x yang ingin ditaksir
            System.out.print("\nMasukkan nilai x yang ingin ditaksir: ");
            xi = validasiInputDouble(scanner);

        } else {
            // Input dari file
            String fileName = IO.readFileName();
            IO io = new IO(fileName);
            n = io.getRowCount() - 1; // Assign jumlah titik
            double[] data = new double[(n * 2) + 1]; // Array sementara berisi nilai-nilai dalam file

            io.openFile();
            io.fileScanner.useLocale(Locale.US);

            // Assign array sementara
            for (int i = 0; i < (n * 2) + 1; i++) {
                if (io.fileScanner.hasNextDouble()) {
                    data[i] = io.fileScanner.nextDouble();
                } else {
                    System.out.println("Data tidak valid pada index " + i);
                    System.exit(0);
                }
            }

            // Assign matrix
            matrix = new Matrix(n, 2);
            int k = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 2; j++) {
                    matrix.setElMT(i, j, data[k]);
                    k += 1;
                }
            }

            // Assign nilai x yang ingin ditaksir
            xi = data[(n * 2)];
            io.closeFile();
        }

        // Membuat matrix augmented
        Matrix matrixAugmented = new Matrix(n, n + 1);
        for (int i = 0; i < n; i++) {
            double elmt = 1;
            for (int j = 0; j < n; j++) {
                matrixAugmented.setElMT(i, j, elmt);
                elmt = elmt * matrix.getElmt(i, 0);
            }
            matrixAugmented.setElMT(i, n, matrix.getElmt(i, 1));
        }

        double[] solutions = SPL.getSolution(matrixAugmented); // Mencari variabel-variabel solusi

        // Mencari nilai y dari xi
        double result = 0;
        double pangkatXi = 1;
        for (int i = 0; i < solutions.length; i++) {
            result += solutions[i] * pangkatXi;
            pangkatXi *= xi;
        }

        // Output
        StringBuilder polynomial = new StringBuilder("f(x) = ");

        // x dengan pangkat tertinggi
        if (solutions.length != 2 && solutions.length != 1){ // Agar tidak tumpang tindih dengan kasus x^1 dan x^0
            if ((solutions[solutions.length - 1] != 0) && (solutions[solutions.length - 1] != 1) && (solutions[solutions.length - 1] != -1)) {
                polynomial.append(String.format("%.4fx^%d", solutions[solutions.length - 1], solutions.length - 1));
            } else if (solutions[solutions.length - 1] == 1){ // Jika koefisiennya bernilai 1, koefisien tidak perlu ditulis
                polynomial.append(String.format("x^%d", solutions.length - 1));
            } else if (solutions[solutions.length - 1] == -1){ // Jika koefisiennya bernilai -1, koefisiennya tidak perlu ditulis (cukup tanda minus)
                polynomial.append(String.format("-x^%d", solutions.length - 1));
            }
            // Catatan: Jika koefisiennya bernilai 0, maka tidak ditulis apapun
        }

        // x sisa (kecuali x^1 dan x^0)
        for (int i = solutions.length - 2; i > 1; i--) {
            if ((solutions[i] < 0) && (solutions[i] != -1)) {
                if (polynomial.length() == 7) { // Kasus sebagai variabel pertama
                    polynomial.append(String.format("%.4fx^%d", solutions[i], i));
                } else {
                    polynomial.append(String.format(" - %.4fx^%d", Math.abs(solutions[i]), i));
                }
            } else if ((solutions[i] > 0) && (solutions[i] != 1)) {
                if (polynomial.length() == 7) { // Kasus sebagai variabel pertama
                    polynomial.append(String.format("%.4fx^%d", solutions[i], i));
                } else {
                    polynomial.append(String.format(" + %.4fx^%d", solutions[i], i));
                }
            } else if (solutions[i] == 1){
                if (polynomial.length() == 7) { // Kasus sebagai variabel pertama
                    polynomial.append(String.format("x^%d", i));
                } else {
                    polynomial.append(String.format(" + x^%d", i));
                }
            } else if (solutions[i] == -1){
                if (polynomial.length() == 7) { // Kasus sebagai variabel pertama
                    polynomial.append(String.format("-x^%d", i));
                } else {
                    polynomial.append(String.format(" - x^%d", i));
                }
            }
        }

        // x^1
        if ((solutions[1] < 0) && (solutions[1] != -1)) {
            if (polynomial.length() == 7) { // Kasus sebagai variabel pertama
                polynomial.append(String.format("%.4fx", solutions[1]));
            } else {
                polynomial.append(String.format(" - %.4fx", Math.abs(solutions[1])));
            }
        } else if ((solutions[1] > 0) && (solutions[1] != 1)) {
            if (polynomial.length() == 7) { // Kasus sebagai variabel pertama
                polynomial.append(String.format("%.4fx", solutions[1]));
            } else {
                polynomial.append(String.format(" + %.4fx", solutions[1]));
            }
        } else if (solutions[1] == 1){
            if (polynomial.length() == 7) { // Kasus sebagai variabel pertama
                polynomial.append(String.format("x"));
            } else {
                polynomial.append(String.format(" + x"));
            }
        } else if (solutions[1] == -1){
            if (polynomial.length() == 7) { // Kasus sebagai variabel pertama
                polynomial.append(String.format("-x"));
            } else {
                polynomial.append(String.format(" - x"));
            }
        }

        // x^0
        if (solutions[0] < 0) {
            if (polynomial.length() == 7) { // Kasus sebagai variabel pertama
                polynomial.append(String.format("%.4f", solutions[0]));
            } else {
                polynomial.append(String.format(" - %.4f", Math.abs(solutions[0])));
            }
        } else if (solutions[0] > 0) {
            if (polynomial.length() == 7) {  // Kasus sebagai variabel pertama
                polynomial.append(String.format("%.4f", solutions[0]));
            } else {
                polynomial.append(String.format(" + %.4f", solutions[0]));
            }
        } 

        // Menyimpan hasil dalam array String[]
        String[] resultArray = new String[2];
        resultArray[0] = polynomial.toString();
        resultArray[1] = String.format(", f(%.1f) = %.4f", xi, result);

        // Menggabungkan hasil menjadi satu string
        String combinedResult = resultArray[0] + resultArray[1];

        System.out.println();
        System.out.println(combinedResult);
        System.out.println();

        return new String[]{combinedResult};

    }


    // Validasi input integer
    public static int validasiInputJumlahTitik(Scanner scanner) {
        int value;
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.matches("\\d+")) {
                value = Integer.parseInt(input);
                if (value > 1) break;
                else System.out.println("Input harus lebih dari 1.");
            } else {
                System.out.println("Input tidak valid. Masukkan angka bulat.");
            }
        }
        return value;
    }


    // Validasi input double
    public static double validasiInputDouble(Scanner scanner) {
        double value;
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.matches("-?\\d+(\\.\\d+)?")) { // Regex untuk validasi double
                value = Double.parseDouble(input);
                if (value > 0) break; // Pastikan nilai lebih dari 0
                else System.out.println("Input harus lebih dari 0.");
            } else {
                System.out.println("Input tidak valid. Masukkan angka desimal.");
            }
        }
        return value;
    }


}
