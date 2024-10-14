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

        System.out.println("Masukkan titik-titik (x y) dan akhiri dengan satu nilai x:");

        int count = 0; // Jumlah titik yang dimasukkan
        double xi = 0.0; // x yang ingin dicari nilai y-nya
        
        while (true) {
            String input = scanner.nextLine().trim();
            String[] splitInput = input.split("\\s"); // Memecah hasil input string menjadi array
            
            if (splitInput.length == 2) {
                if (count == x.length) {
                    // Menambah ukuran array jika sudah penuh
                    x = resizeArray(x);
                    y = resizeArray(y);
                }
                x[count] = Double.parseDouble(splitInput[0]);
                y[count] = Double.parseDouble(splitInput[1]);
                count++;
            }

            else if (splitInput.length == 1) {
                xi = Double.parseDouble(splitInput[0]);
                break;
            }
            
            else {
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
        
        double[] solutions = SPL.getSolution(m);
    
        // Mencari nilai y dari xi
        double result = 0;
        double pangkatXi = 1;
        for (int i = 0; i < solutions.length; i++) {
            result += solutions[i] * pangkatXi;
            pangkatXi *= xi;
        }
    
        System.out.printf("%f%n", result);

        // Test generate gaussJordan: SPL.gaussJordanSolution(m);
        // Test hitung count: System.out.printf("%d%n", count);

        scanner.close();
    }


    private static double[] resizeArray(double[] oldArray) {
        // Menambah ukuran array
        int newSize = oldArray.length + 1;
        double[] newArray = new double[newSize];
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }
}