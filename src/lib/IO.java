package lib;

import java.util.*;
import java.io.*;

public class IO {
    public String fileName;
    public Scanner fileScanner;
    public static Scanner inputScanner = new Scanner(System.in);

    public static void main(String[] args) {
        IO io = new IO("");
        double[] temp = io.readBicubicSplineDataFromKeyboard();

        for (int i = 0; i < 18; i++) {
            System.out.println(temp[i]);
        }

    }
    // Konstruktor
    public IO(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    // Open & Close File
    public void openFile() {
        try {
            File file = new File(getFileName());
            this.fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File " + getFileName() + "tidak ditemukan di directory test/input.");
            System.exit(0);
        }
    }

    public void closeFile() {
        if (fileScanner != null) {
            fileScanner.close();
        }
    }

    // Read file name from input
    public static String readFileName() {
        String fileName = "";
        while (true) {
            try {
                System.out.print("\nMasukan nama file: ");
                fileName = inputScanner.nextLine();
                File file = new File("test/input/" + fileName);
                Scanner tempScanner = new Scanner(file); // Check if file exists
                tempScanner.close(); // Close immediately after validation
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File " + fileName + " tidak ditemukan di directory test/input.");
            }
        }
        return "test/input/" + fileName;
    }

    // Count number of rows in the file
    public int getRowCount() {
        int rowCount = 0;
        openFile();
        while (fileScanner.hasNextLine()) {
            fileScanner.nextLine();
            rowCount++;
        }
        closeFile();
        return rowCount;
    }

    // Count number of columns in the first row
    public int getColCount() {
        int colCount = 0;
        openFile();
        if (fileScanner.hasNextLine()) {
            Scanner colScanner = new Scanner(fileScanner.nextLine());
            while (colScanner.hasNextDouble()) {
                colCount++;
                colScanner.nextDouble();
            }
            colScanner.close();
        }
        closeFile();
        return colCount;
    }

    // Read matrix from keyboard
    public static Matrix readMatrixFromKeyboard() {
        int row = 0, col = 0;
        while (true) {
            try {
                System.out.print("Masukkan jumlah baris: ");
                row = inputScanner.nextInt();
                System.out.print("Masukkan jumlah kolom: ");
                col = inputScanner.nextInt();

                if (row > 0 && col > 0) {
                    break;
                } else {
                    System.out.println("Masukan harus lebih dari 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Masukan harus berupa bilangan bulat lebih dari 0.");
                inputScanner.next();
            }
        }

        Matrix m = new Matrix(row, col);
        System.out.println("\nMasukkan matriks: ");
        m.readMatrix();
        
        return m;
    }

    // Read matrix from file
    public Matrix readMatrixFromFile() {
        int row = getRowCount();
        int col = getColCount();
        Matrix matrix = new Matrix(row, col);

        openFile();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (fileScanner.hasNextDouble()) {
                    matrix.setElMT(i, j, fileScanner.nextDouble());
                }
            }
        }
        closeFile();
        return matrix;
    }

    // Read points from file
    public Matrix readPointsFromFile() {
        int row = getRowCount();
        Matrix points = new Matrix(row, 2);

        openFile();
        for (int i = 0; i < row - 1; i++) {
            for (int j = 0; j < 2; j++) {
                if (fileScanner.hasNextDouble()) {
                    points.setElMT(i, j, fileScanner.nextDouble());
                }
            }
        }
        // Handle the last element separately
        if (fileScanner.hasNextDouble()) {
            points.setElMT(row - 1, 0, fileScanner.nextDouble());
        }
        closeFile();
        return points;
    }

    // Read bicubic spline data from keyboard
    public double[] readBicubicSplineDataFromKeyboard() {
        Matrix m = new Matrix(4,4);
        double[] temp = new double[18];
        
        // Set locale to US to ensure proper decimal point handling
        inputScanner.useLocale(Locale.US);

        System.out.println("\nMasukkan matriks 4x4: ");
        m.readMatrix();
        
        int i,j,k = 0;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                temp[k] = m.getElmt(i, j);
                k++;
            }
        }
        System.out.println();

        double a, b;
        while (true){
            try {
                System.out.print("Masukkan a: ");
                a = inputScanner.nextDouble();
                System.out.print("Masukkan b: ");
                b = inputScanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Silakan masukkan angka!\n");
                inputScanner.next();
            }
        }
        
        temp[16] = a;
        temp[17] = b;

        return temp;
    }

    // Read bicubic spline data from file
    public double[] readBicubicSplineData() {
        double[] splineData = new double[18];

        openFile();

        // Set locale to US to ensure proper decimal point handling
        fileScanner.useLocale(Locale.US);
        
        for (int i = 0; i < 18; i++) {
            if (fileScanner.hasNextDouble()) {
                splineData[i] = fileScanner.nextDouble();
            } else {
                System.out.println("Data tidak valid pada index " + i);
                System.exit(0);
            }
        }
        closeFile();
        return splineData;
    }

    // Write string array to file
    public void writeStringArrayToFile(String[] content) {
        try {
            System.out.print("Masukkan nama file keluaran: ");

            if (inputScanner.hasNextLine()) {
                inputScanner.nextLine();
            }

            String outputFileName = inputScanner.nextLine();

            File outputFile = new File("test/output/" + outputFileName);

            if (outputFile.exists() || outputFile.createNewFile()) {
                FileWriter writer = new FileWriter(outputFile);
                for (int i = 0; i < content.length; i++) {
                    writer.write(content[i]);
                    if (i != content.length - 1) {
                        writer.write("\n");
                    }
                }
                writer.close();
                System.out.println("File " + outputFileName + " berhasil dibuat di directory test/output.");
            } else {
                System.out.println("File" + outputFileName + "gagal dibuat.");
            }
        } catch (IOException e) {
            System.out.println("Error dalam menulis file.");
        }
    }

    // Save file
    public void saveFile(String[] stringArray) {
        while (true) {
            System.out.print("""
                    Apakah ingin menyimpan file?
                    1. Ya
                    2. Tidak
                    """);
            System.out.println();
            System.out.print("Masukkan pilihan: ");

            try {
                int choice = inputScanner.nextInt(); 
                if (choice == 1) {
                    writeStringArrayToFile(stringArray);
                    break; 
                } else if (choice == 2) {
                    break;
                } else {
                    System.out.println("\nInput tidak valid. Silakan masukkan angka (1-2)!\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInput tidak valid. Silakan masukkan angka!\n");
                inputScanner.next(); 
            }
        }
    }

}
