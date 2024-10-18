package lib;

import java.util.Scanner;
import java.util.Locale;

public class RegresiBerganda {
    public static String[] RegresiLinierBerganda(int pilihan) {
        Locale.setDefault(Locale.US); // Set Locale ke US agar input desimal menggunakan titik (.)
        Scanner scanner = new Scanner(System.in);

        Matrix matrix;
        int n;
        int m;
        double[] xk;

        if (pilihan == 1) {
            // Input jumlah peubah x dan jumlah sampel
            System.out.print("\nMasukkan jumlah peubah x: ");
            n = validasiInputInteger(scanner); // Input jumlah peubah x
            System.out.print("Masukkan jumlah sampel: ");
            m = validasiInputInteger(scanner); // Input jumlah sampel

            // Input matrix
            matrix = new Matrix(m, n+1);
            System.out.print("\nMasukkan " + m + " nilai-nilai x1, x2, ..., xn, dan y:\n");
            matrix.readMatrix();

            // Input nilai-nilai xk
            System.out.print("\nMasukkan nilai-nilai xk yang ingin ditaksir: \n");
            xk = new double[n];
            xk = validasiInputDoubleArray(scanner, n);

        } else {
            // Input dari file
            String fileName = IO.readFileName();
            IO io = new IO(fileName);
            n = io.getColCount() - 1; // Assign jumlah peubah x
            m = io.getRowCount() - 1; // Assign jumlah sampel
            double[] data = new double[(m * (n+1)) + n]; // Array sementara berisi nilai-nilai dalam file

            io.openFile();
            io.fileScanner.useLocale(Locale.US);

            // Assign array sementara
            for (int i = 0; i < (m * (n+1)) + n; i++) {
                if (io.fileScanner.hasNextDouble()) {
                    data[i] = io.fileScanner.nextDouble();
                } else {
                    System.out.println("Data tidak valid pada index " + i);
                    System.exit(0);
                }
            }

            // Assign matrix
            matrix = new Matrix(m, n+1);
            int k = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n+1; j++) {
                    matrix.setElMT(i, j, data[k]);
                    k += 1;
                }
            }

            // Assign nilai-nilai xk
            xk = new double[n];
            for (int i = 0; i < n; i++){
                xk[i] = data[(m * (n+1)) + i];
            }
            io.closeFile();
        }

        double[] arrayFirstRow = new double[n+2];
        double[][] matrixNextRow = new double[n][n+2];
        // Array berisi baris pertama Normal Estimation Equation
        arrayFirstRow[0] = n;
        for (int i = 1; i <= n; i++) {
            double sumX = 0;
            for (int j = 0; j < m; j++){
                sumX = sumX + matrix.getElmt(j, i-1);
            }
            arrayFirstRow[i] = sumX;
        }
        double sumY = 0;
        for (int i = 0; i < m; i++){
            sumY = sumY + matrix.getElmt(i, n);
        arrayFirstRow[n+1] = sumY;
        }

        // Matrix berisi baris kedua hingga terakhir dari Normal Estimation Equation
        for (int i = 0; i < n; i++){ // Kolom pertama
            matrixNextRow[i][0] = arrayFirstRow[i+1];
        }
        for (int i = 0; i < n; i++) { 
            for (int j = 1; j < (n+1); j++){ // Kolom kedua hingga kedua terakhir
                double sumXX = 0;
                for (int k = 0; k < m; k++){
                    sumXX = sumXX + (matrix.getElmt(k, i) * matrix.getElmt(k, j-1));
                }
                matrixNextRow[i][j] = sumXX;
            }
            double sumXY = 0; // Kolom terakhir
            for (int k = 0; k < m; k++){
                sumXY = sumXY + (matrix.getElmt(k, i) * matrix.getElmt(k, n));
            }
            matrixNextRow[i][n+1] = sumXY;
        }

        // Matrix augmented
        Matrix matrixAugmented = new Matrix(n+1, n+2);
        for (int i = 0; i < n + 2; i++) {
            matrixAugmented.setElMT(0, i, arrayFirstRow[i]);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 2; j++) {
                matrixAugmented.setElMT(i+1, j, matrixNextRow[i][j]);
            }
        }
        // Tes: matrixAugmented.printMatrix();

        // Solusi
        double[] solutions = SPL.getSolution(matrixAugmented);
        
        // StringBuilder untuk menyimpan output
        StringBuilder outputString = new StringBuilder();

        // Menyusun output f(x)
        outputString.append(String.format("f(x) = %.4f", solutions[0]));
        for (int i = 1; i < solutions.length; i++) {
            if (solutions[i] >= 0) {
                outputString.append(String.format(" + %.4f X%d", solutions[i], i));
            } else {
                outputString.append(String.format(" - %.4f X%d", -solutions[i], i));
            }
        }

        // Menyusun output nilai taksiran f(xk)
        double f_xk = solutions[0];
        for (int i = 0; i < n; i++) {
            f_xk += solutions[i + 1] * xk[i];
        }
        outputString.append(String.format(", f(xk) = %.4f", f_xk));

        // Output
        System.out.println();
        System.out.println(outputString);
        System.out.println();

        // Mengembalikan hasil dalam bentuk String[]
        return new String[]{outputString.toString()};
    }

        
    public static String[] RegresiKuadratikBerganda(int pilihan) {
        Locale.setDefault(Locale.US); // Set Locale ke US agar input desimal menggunakan titik (.)
        Scanner scanner = new Scanner(System.in);

        Matrix matrix;
        int n;
        int m;
        double[] xk;

        if (pilihan == 1) {
            // Input jumlah peubah x dan jumlah sampel
            System.out.print("\nMasukkan jumlah peubah x: ");
            n = validasiInputInteger(scanner); // Input jumlah peubah x
            System.out.print("Masukkan jumlah sampel: ");
            m = validasiInputInteger(scanner); // Input jumlah sampel
            
            // Input matrix
            matrix = new Matrix(m, n+1);
            System.out.print("\nMasukkan " + m + " nilai-nilai  x1, x2, ..., xn, dan y :\n");
            matrix.readMatrix();
            // Tes: matrix.printMatrix();

            // Input nilai-nilai xk
            System.out.print("\nMasukkan nilai-nilai xk yang ingin ditaksir: \n");
            xk = new double[n];
            xk = validasiInputDoubleArray(scanner, n);

        } else {
            // Input dari file
            String fileName = IO.readFileName();
            IO io = new IO(fileName);
            n = io.getColCount() - 1; // Assign jumlah peubah x
            m = io.getRowCount() - 1; // Assign jumlah sampel
            double[] data = new double[(m * (n+1)) + n]; // Array sementara berisi nilai-nilai dalam file

            io.openFile();
            io.fileScanner.useLocale(Locale.US);

            // Assign array sementara
            for (int i = 0; i < (m * (n+1)) + n; i++) {
                if (io.fileScanner.hasNextDouble()) {
                    data[i] = io.fileScanner.nextDouble();
                } else {
                    System.out.println("Data tidak valid pada index " + i);
                    System.exit(0);
                }
            }

            // Assign matrix
            matrix = new Matrix(m, n+1);
            int k = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n+1; j++) {
                    matrix.setElMT(i, j, data[k]);
                    k += 1;
                }
            }

            // Assign nilai-nilai xk
            xk = new double[n];
            for (int i = 0; i < n; i++){
                xk[i] = data[(m * (n+1)) + i];
            }
            io.closeFile();
        }

        int suku = (n * (n + 3)) / 2;
        Matrix matrixPembantu = new Matrix(suku, m);
        /* Isi matrixPembantu:
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
                matrixPembantu.setElMT(i, j, matrix.getElmt(j, i));
            }
        }
        // Isi kuadrat dari x1^2, x2^2, ..., xn^2
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrixPembantu.setElMT((n+i), j, (matrix.getElmt(j, i) * matrix.getElmt(j, i)));
            }
        }
        // Isi x1x2, x1x3, ..., xnxn
        int index = n + n;
        for (int i = 0; i < n; i++) {
            for (int k = i + 1; k < n; k++) {
                for (int j = 0; j < m; j++) {
                    matrixPembantu.setElMT(index, j, (matrix.getElmt(j, i) * matrix.getElmt(j, k)));
                }
                index++;
            }
        }
        
        // Matrix kiri
        Matrix matrix1 = new Matrix(suku + 1, suku + 1);
        matrix1.setElMT(0, 0, n); // Elemen pertama
        // Baris dan kolom pertama 
        for (int i = 0; i < suku; i++){
            double sum = 0;
            for (int j = 0; j < m; j++){
                sum = sum + matrixPembantu.getElmt(i, j);
            }
            matrix1.setElMT(0, i+1, sum);
        }
        for (int i = 1; i < (suku+1); i++){
            matrix1.setElMT(i, 0, matrix1.getElmt(0, i));
        }
        // Baris dan kolom dalam
        for (int i = 1; i < (suku+1); i++){
            for (int l = 0; l < m; l++){
                for (int j = 0; j < suku; j++){
                    double sum = 0;
                    for (int k = 0; k < m; k++){
                        sum = sum + (matrixPembantu.getElmt(i-1, k) * matrixPembantu.getElmt(j, k));
                    }
                    matrix1.setElMT(i, j+1, sum);
                }
            }
        }

        // Matrix kanan
        Matrix matrix2 = new Matrix(suku + 1, 1);
        double sum = 0;
        for (int i = 0; i < m; i++) {
            sum = sum + matrix.getElmt(i, n);
        }
        matrix2.setElMT(0, 0, sum); // Elemen pertama
        for (int i = 1; i < (suku+1); i++) {
            sum = 0;
            for (int j = 0; j < m; j++){
                sum = sum + (matrix.getElmt(j, n) * matrixPembantu.getElmt(i-1, j));
            matrix2.setElMT(i, 0, sum);
            }
        }

        // Matrix augmented
        Matrix matrixAugmented = Matrix.createAugmented(matrix1, matrix2);
        // Tes: matrixAugmented.printMatrix();

        // Solusi
        double[] solutions = SPL.getSolution(matrixAugmented);
        // Tes: System.out.println(Arrays.toString(solutions));

        // StringBuilder untuk menyimpan output
        StringBuilder outputString = new StringBuilder();

        
        // Menyusun output penjelasan kombinasi ke output
        outputString.append(printCombinations(n));

        // Menyusun output f(x)
        outputString.append(String.format("f(x) = %.4f", solutions[0]));
        for (int i = 1; i < solutions.length; i++) {
            if (solutions[i] >= 0) {
                outputString.append(String.format(" + %.4f X%d", solutions[i], i));
            } else {
                outputString.append(String.format(" - %.4f X%d", -solutions[i], i));
            }
        }

        // Menghitung nilai taksiran f(xk)
        double f_xk = solutions[0];
        index = n;
        // Menambahkan variabel linier
        for (int i = 0; i < n; i++) {
            f_xk += solutions[i + 1] * xk[i];
        }
        // Menambahkan variabel kuadrat
        for (int i = 0; i < n; i++) {
            f_xk += solutions[index + 1] * xk[i] * xk[i];
            index++;
        }
        // Menambahkan variabel interaksi
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                f_xk += solutions[index + 1] * xk[i] * xk[j];
                index++;
            }
        }

        // Menyusun output f(xk)
        outputString.append(String.format(", f(xk) = %.4f", f_xk));

        // Output
        System.out.println();
        System.out.println(outputString);
        System.out.println();

        // Mengembalikan hasil dalam bentuk String[]
        return new String[]{outputString.toString()};
    }
    

    public static String printCombinations(int n) {
        StringBuilder combinations = new StringBuilder();
        // Variabel linier
        for (int i = 1; i <= n; i++) {
            combinations.append(String.format("X%d = X%d\n", i, i));
        }
        // Variabel kuadrat
        for (int i = 1; i <= n; i++) {
            combinations.append(String.format("X%d = X%d^2\n", (n+i), i));
        }
        // Cetak variabel interaksi
        int index = 2 * n + 1;
        for (int i = 1; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                combinations.append(String.format("X%d = X%dX%d\n", index, i, j)); // Contoh interaksi
                index++;
            }
        }
        return combinations.toString();
    }


    // Validasi input integer
    public static int validasiInputInteger(Scanner scanner) {
        int value;
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.matches("\\d+")) {
                value = Integer.parseInt(input);
                if (value > 0) break;
                else System.out.println("Input harus lebih dari 0.");
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
    public static double[] validasiInputDoubleArray(Scanner scanner, int expectedLength) {
        double[] values = new double[expectedLength];
        while (true) {
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


}
