package lib;

import java.util.Scanner;
import java.util.Locale;

public class RegresiBerganda {
    public static void RegresiLinierBerganda(String[] args) {
        Locale.setDefault(Locale.US); // Set Locale ke US agar input desimal menggunakan titik (.)

        Scanner scanner = new Scanner(System.in);

        // Validasi jumlah peubah x dan jumlah sampel
        int n = validasiInputInteger(scanner, "Jumlah peubah x = ");
        int m = validasiInputInteger(scanner, "Jumlah sampel = ");

        double[][] x = new double[n][m];
        double[] y = new double[m];
        // Input nilai x dan y
        for (int j = 0; j < m; j++) {
            validasiInputX(scanner, x, n, j);
            validasiInputY(scanner, y, j);
        }

        // Input nilai xk
        double[] xk = validasiInputDoubleArray(scanner, n,
                "Masukkan " + n + " nilai xk yang ingin ditaksir (pisahkan dengan spasi): ");

        double[] arrayFirstRow = new double[n + 2];
        double[][] matrixNextRow = new double[n][n + 2];
        // Array berisi baris pertama Normal Estimation Equation
        arrayFirstRow[0] = n;
        for (int i = 1; i <= n; i++) {
            double sumX = 0;
            for (int j = 0; j < m; j++) {
                sumX = sumX + x[i - 1][j];
                arrayFirstRow[i] = sumX;
            }
        }
        double sumY = 0;
        for (int i = 0; i < m; i++) {
            sumY = sumY + y[i];
            arrayFirstRow[n + 1] = sumY;
        }

        // Matrix berisi baris kedua hingga terakhir dari Normal Estimation Equation
        for (int i = 0; i < n; i++) { // Kolom pertama
            matrixNextRow[i][0] = arrayFirstRow[i + 1];
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n + 1); j++) { // Kolom kedua hingga kedua terakhir
                double sumXX = 0;
                for (int k = 0; k < m; k++) {
                    sumXX = sumXX + (x[i][k] * x[j - 1][k]);
                }
                matrixNextRow[i][j] = sumXX;
            }
            double sumXY = 0; // Kolom terakhir
            for (int k = 0; k < m; k++) {
                sumXY = sumXY + (x[i][k] * y[k]);
            }
            matrixNextRow[i][n + 1] = sumXY;
        }

        // Matrix augmented
        Matrix matrixAugmented = new Matrix(n + 1, n + 2);
        for (int i = 0; i < n + 2; i++) {
            matrixAugmented.setElMT(0, i, arrayFirstRow[i]);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 2; j++) {
                matrixAugmented.setElMT(i + 1, j, matrixNextRow[i][j]);
            }
        }

        // Solusi
        double[] solutions = SPL.getSolution(matrixAugmented);
        System.out.printf("f(x) = %.4f", solutions[0]);
        for (int i = 1; i < solutions.length; i++) {
            if (solutions[i] >= 0) {
                System.out.printf(" + %.4f X%d", solutions[i], i);
            } else {
                solutions[i] = solutions[i] * (-1);
                System.out.printf(" - %.4f X%d", solutions[i], i);
            }
        }

        // Menghitung nilai taksiran f(xk)
        double f_xk = solutions[0];
        for (int i = 0; i < n; i++) {
            f_xk += solutions[i + 1] * xk[i];
        }
        System.out.printf(", f(xk) = %.4f\n", f_xk);

        scanner.close();
    }
    // Test case:
    // X1 = 10, 16, 20, 26; X2 = 8, 10, 16, 22; Y = 20, 30, 40, 36

    public static void RegresiKuadratikBerganda(String[] args) {
        Locale.setDefault(Locale.US); // Set Locale ke US agar input desimal menggunakan titik (.)

        Scanner scanner = new Scanner(System.in);

        // Input jumlah peubah x dan jumlah sampel
        int n = validasiInputInteger(scanner, "Jumlah peubah x = ");
        int m = validasiInputInteger(scanner, "Jumlah sampel = ");

        double[][] x = new double[n][m];
        double[] y = new double[m];

        // Input nilai x dan y
        for (int j = 0; j < m; j++) {
            validasiInputX(scanner, x, n, j);
            validasiInputY(scanner, y, j);
        }

        // Input nilai xk
        double[] xk = validasiInputDoubleArray(scanner, n, "Masukkan " + n + " nilai xk yang ingin ditaksir: ");

        int suku = (n * (n + 3)) / 2;
        Matrix matrixPembantu = new Matrix(suku, m);

        /*
         * Isi matrixPembantu:
         * x1[1], x1[2], x1[3], ...
         * x2[1], x2[2], x2[3], ...
         * ...
         * x1^2[1], x1^2[2], x1^2[3], ...
         * ...
         * xn^2[1], xn^2[2], xn^2[3], ...
         * ...
         * x1[1].x2[1], x1[2].x2[2], x1[3].x2[3], ...
         * ...
         * x(n-1)[1].xn[1], x(n-1)[2].xn[2], x(n-1)[3].xn[3], ...
         */

        // Mengisi matrix pembantu
        // Isi x1, x2, ..., xn
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrixPembantu.setElMT(i, j, x[i][j]);
            }
        }
        // Isi kuadrat dari x1^2, x2^2, ..., xn^2
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrixPembantu.setElMT((n + i), j, (x[i][j] * x[i][j]));
            }
        }
        // Isi x1x2, x1x3, ..., xnxn
        int index = n + n;
        for (int i = 0; i < n; i++) {
            for (int k = i + 1; k < n; k++) {
                for (int j = 0; j < m; j++) {
                    matrixPembantu.setElMT(index, j, (x[i][j] * x[k][j]));
                }
                index++;
            }
        }

        // Matrix kiri
        Matrix matrix1 = new Matrix(suku + 1, suku + 1);
        matrix1.setElMT(0, 0, n); // Elemen pertama
        // Baris dan kolom pertama
        for (int i = 0; i < suku; i++) {
            double sum = 0;
            for (int j = 0; j < m; j++) {
                sum = sum + matrixPembantu.getElmt(i, j);
            }
            matrix1.setElMT(0, i + 1, sum);
        }
        for (int i = 1; i < (suku + 1); i++) {
            matrix1.setElMT(i, 0, matrix1.getElmt(0, i));
        }
        // Baris dan kolom dalam
        for (int i = 1; i < (suku + 1); i++) {
            for (int l = 0; l < m; l++) {
                for (int j = 0; j < suku; j++) {
                    double sum = 0;
                    for (int k = 0; k < m; k++) {
                        sum = sum + (matrixPembantu.getElmt(i - 1, k) * matrixPembantu.getElmt(j, k));
                    }
                    matrix1.setElMT(i, j + 1, sum);
                }
            }
        }

        // Matrix kanan
        Matrix matrix2 = new Matrix(suku + 1, 1);
        double sum = 0;
        for (int i = 0; i < m; i++) {
            sum = sum + y[i];
        }
        matrix2.setElMT(0, 0, sum); // Elemen pertama
        for (int i = 1; i < (suku + 1); i++) {
            sum = 0;
            for (int j = 0; j < m; j++) {
                sum = sum + (y[j] * matrixPembantu.getElmt(i - 1, j));
                matrix2.setElMT(i, 0, sum);
            }
        }

        // Matrix augmented
        Matrix matrixAugmented = Matrix.createAugmented(matrix1, matrix2);

        // Solusi
        double[] solutions = SPL.getSolution(matrixAugmented);
        System.out.printf("f(x) = %.4f", solutions[0]);
        for (int i = 1; i < solutions.length; i++) {
            if (solutions[i] >= 0) {
                System.out.printf(" + %.4f X%d", solutions[i], i);
            } else {
                solutions[i] = solutions[i] * (-1);
                System.out.printf(" - %.4f X%d", solutions[i], i);
            }
        }

        // Menghitung nilai taksiran f(xk)
        double f_xk = solutions[0];
        index = n;
        // Tambahkan komponen linier
        for (int i = 0; i < n; i++) {
            f_xk += solutions[i + 1] * xk[i];
        }
        // Tambahkan komponen kuadrat
        for (int i = 0; i < n; i++) {
            f_xk += solutions[index + 1] * xk[i] * xk[i];
            index++;
        }
        // Tambahkan komponen interaksi
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                f_xk += solutions[index + 1] * xk[i] * xk[j];
                index++;
            }
        }
        System.out.printf(", f(xk) = %.4f\n", f_xk);
        printCombinations(n);
        scanner.close();
    }

    public static void printCombinations(int numVariables) {
        // Cetak variabel linier
        for (int i = 1; i <= numVariables; i++) {
            System.out.println("X" + i + " = X" + i);
        }
        // Cetak variabel kuadrat
        for (int i = 1; i <= numVariables; i++) {
            System.out.println("X" + (numVariables + i) + " = X" + i + "^2");
        }
        // Cetak variabel interaksi
        int index = 2 * numVariables + 1;
        for (int i = 1; i < numVariables; i++) {
            for (int j = i + 1; j <= numVariables; j++) {
                System.out.println("X" + index + " = X" + i + "X" + j);
                index++;
            }
        }
    }

    // Validasi input integer
    public static int validasiInputInteger(Scanner scanner, String message) {
        int value;
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (input.matches("\\d+")) {
                value = Integer.parseInt(input);
                if (value > 0)
                    break;
                else
                    System.out.println("Input harus lebih dari 0.");
            } else {
                System.out.println("Input tidak valid. Masukkan angka bulat.");
            }
        }
        return value;
    }

    // Validasi input double
    public static double validasiInputDouble(Scanner scanner, String message) {
        double value;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                break; // Valid input, keluar dari loop
            } else {
                System.out.println("Input tidak valid. Masukkan angka.");
                scanner.next(); // Clear invalid input
            }
        }
        return value;
    }

    // Validasi input double dengan jumlah elemen yang sesuai dengan n
    public static double[] validasiInputDoubleArray(Scanner scanner, int expectedLength, String message) {
        double[] values = new double[expectedLength];
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            String[] splitInput = input.split("\\s+");
            if (splitInput.length == expectedLength) {
                try {
                    for (int i = 0; i < expectedLength; i++) {
                        values[i] = Double.parseDouble(splitInput[i]);
                    }
                    break; // Jika semua elemen valid, keluar dari loop
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid. Masukkan angka desimal.");
                }
            } else {
                System.out.println("Input harus berjumlah " + expectedLength + " angka.");
            }
        }
        return values;
    }

    // Validasi input x
    public static void validasiInputX(Scanner scanner, double[][] x, int n, int j) {
        double[] inputX = validasiInputDoubleArray(scanner, n,
                "Masukkan " + n + " nilai x untuk sampel ke-" + (j + 1) + ": ");
        for (int i = 0; i < n; i++) {
            x[i][j] = inputX[i];
        }
    }

    // Validasi input y
    public static void validasiInputY(Scanner scanner, double[] y, int j) {
        y[j] = validasiInputDoubleArray(scanner, 1, "Masukkan nilai y untuk sampel ke-" + (j + 1) + ": ")[0];
    }

}
