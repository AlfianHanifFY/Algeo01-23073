package lib;

import java.util.Scanner;
import java.util.Locale;

public class InterpolasiPolinomial {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // Set Locale ke US agar input desimal menggunakan titik (.)
        Scanner scanner = new Scanner(System.in);

        // Inisialisasi array
        double[] x = new double[2];
        double[] y = new double[2];

        // Input kumpulan titik dan x yang ingin ditaksir
        System.out.println("Masukkan titik-titik (x y) dan akhiri dengan satu nilai x:"); 

        int count = 0; // Jumlah titik yang dimasukkan
        double xi = 0.0; // x yang ingin dicari nilai y-nya
        
        while (true) {
            String input = scanner.nextLine().trim();
            String[] splitInput = input.split("\\s"); // Memecah hasil input string menjadi array
            if (splitInput.length == 2) {
                if (isNumeric(splitInput[0]) && isNumeric(splitInput[1])) {
                    if (count == x.length) {
                        // Menambah ukuran array jika sudah penuh
                        x = resizeArray(x);
                        y = resizeArray(y);
                    }
                    x[count] = Double.parseDouble(splitInput[0]);
                    y[count] = Double.parseDouble(splitInput[1]);
                    count++;
                } else {
                    System.out.println("Input tidak valid. Masukkan titik (x y) atau satu nilai x untuk mengakhiri.");
                }
            } else if (splitInput.length == 1) {
                if (isNumeric(splitInput[0])) {
                    if (count < 2) {
                        // Validasi: Setidaknya dua titik harus dimasukkan sebelum xi
                        System.out.println("Masukkan setidaknya dua titik (x y) sebelum memasukkan nilai x untuk ditaksir.");
                    } else {
                        xi = Double.parseDouble(splitInput[0]);
                        break;
                    }
                } else {
                    System.out.println("Input tidak valid. Masukkan titik (x y) atau satu nilai x untuk mengakhiri.");
                }
            } else {
                System.out.println("Input tidak valid. Masukkan titik (x y) atau satu nilai x untuk mengakhiri.");
            }
        }

        // Membuat matrix augmented
        Matrix m = new Matrix(count, count+1);
        for (int i = 0; i < count; i++) {
            double elmt = 1;
            for (int j = 0; j < count; j++) {
                m.setElMT(i, j, elmt);
                elmt = elmt * x[i];
            m.setElMT(i, count, y[i]);
            }
        }
        
        double[] solutions = SPL.getSolution(m); // Mencari variabel-variabel solusi
    
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
            } else if (solutions[solutions.length - 1] == 1){
                polynomial.append(String.format("x^%d", solutions.length - 1));
            } else if (solutions[solutions.length - 1] == -1){
                polynomial.append(String.format("-x^%d", solutions.length - 1));
            }
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

        System.out.printf("%s, f(%.1f) = %.4f%n", polynomial, xi, result);

        scanner.close();
        }
    
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str); // Mencoba memparsing string ke double
            return true; // Jika berhasil, berarti input valid
        } catch (NumberFormatException e) {
            return false; // Jika gagal, berarti input bukan angka
        }
    }

    private static double[] resizeArray(double[] oldArray) {
        // Menambah ukuran array
        int newSize = oldArray.length + 1;
        double[] newArray = new double[newSize];
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }
    
}
