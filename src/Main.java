import java.io.IOException;
import java.util.*;

import lib.BicubicSplineInterpolation;

public class Main {
    static Scanner inputScanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException, IOException {
        clearScreen();
        loadingUI();
        Thread.sleep(300);

        menuUI();
    }

    public static void loadingUI() throws InterruptedException{
        int progressBarLength = 30; 
        int totalSteps = 100;

        System.out.println();
        System.out.print("Loading: [");

        for (int step = 1; step <= totalSteps; step++) {
            int completedLength = (step * progressBarLength) / totalSteps;

            System.out.print("\rLoading: [");
            for (int i = 0; i < progressBarLength; i++) {
                if (i < completedLength) {
                    System.out.print("#"); 
                } else {
                    System.out.print("-");
                }
            }
            System.out.print("] " + step + "%");
            Thread.sleep(50);
        }

        System.out.println();
    }

    public static void clearScreen() throws IOException, InterruptedException {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static int inputTypeUI() throws IOException, InterruptedException{
        boolean type = true;
        int inputType = 0;

        while (type) {
            clearScreen();

            System.out.print("""
                    PILIH TIPE MASUKAN
                    1. Keyboard
                    2. File
                    """);
            System.out.print("Masukkan pilihan: ");
            try {
                inputType = inputScanner.nextInt();

                if (inputType == 1 || inputType == 2) {
                    type = false;
                } else {
                    System.out.println("\nInput tidak valid. Silakan masukkan angka (1-2)!\n");
                    inputScanner.nextLine();
                    Thread.sleep(1000);
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInput tidak valid. Silakan masukkan angka (1-2)!\n");
                inputScanner.nextLine();
                Thread.sleep(1000);
            }
        }
        return inputType;
    }

    public static void splUI() throws IOException, InterruptedException{
        int choice;
        boolean run = true;

        while (run) {
            clearScreen();
            System.out.print("""
                    PILIH METODE
                    1. Metode Eliminasi Gauss
                    2. Metode Eliminasi Gauss-Jordan
                    3. Metode Matriks balikan
                    4. Kaidah Cramer
                    5. Kembali
                    """);
            System.out.println();
            System.out.print("Masukkan pilihan: ");

            try {
                choice = inputScanner.nextInt();
                System.out.println();

                int inputType;
                switch (choice) {
                    case 1:
                        System.out.println("Gauss");
                        inputType = inputTypeUI();

                        if (inputType == 1) {
                            // Gauss dari Keyboard
                            System.out.println("Keyboard Gauss");
                            System.exit(0);
                        } else {
                            // Gauss dari File
                            System.out.println("File Gauss");
                            System.exit(0);
                        }
                        break;
                    case 2:
                        System.out.println("Gauss-Jordan");
                        inputType = inputTypeUI();

                        if (inputType == 1) {
                            // Gauss-Jordan dari Keyboard
                            System.out.println("Keyboard Gauss-Jordan");
                            System.exit(0);
                        } else {
                            // Gauss-Jordan dari File
                            System.out.println("File Gauss-Jprdan");
                            System.exit(0);
                        }
                        break;
                    case 3:
                        System.out.println("Matriks Balikan");
                        inputType = inputTypeUI();

                        if (inputType == 1) {
                            // Balikan dari Keyboard
                            System.out.println("Keyboard Balikan");
                            System.exit(0);
                        } else {
                            // Balikan dari File
                            System.out.println("File Balikan");
                            System.exit(0);
                        }
                        break;
                    case 4:
                        System.out.println("Cramer");
                        inputType = inputTypeUI();

                        if (inputType == 1) {
                            // Crammer dari Keyboard
                            System.out.println("Keyboard Crammer");
                            System.exit(0);
                        } else {
                            // Crammer dari File
                            System.out.println("File Crammer");
                            System.exit(0);
                        }
                        break;
                    case 5:
                        run = false;
                        break;
                    default:
                        System.out.println("Input tidak valid. Silakan masukkan angka (1-5)!\n");
                        Thread.sleep(1000);
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInput tidak valid. Silakan masukkan angka (1-5)!\n");
                inputScanner.nextLine();
                Thread.sleep(1000);
            }
        }
    }

    public static void determinantUI() throws IOException, InterruptedException{
        int choice;
        boolean run = true;

        while (run) {
            clearScreen();
            System.out.print("""
                    PILIH METODE
                    1. Metode Reduksi Baris
                    2. Metode Ekspansi Kofaktor
                    3. Kembali
                    """);
            System.out.println();
            System.out.print("Masukkan pilihan: ");

            try {
                choice = inputScanner.nextInt();
                System.out.println();

                int inputType;
                switch (choice) {
                    case 1:
                        System.out.println("Reduksi Baris");
                        inputType = inputTypeUI();

                        if (inputType == 1) {
                            // Determinan Reduksi Baris dari Keyboard
                            System.out.println("Keyboard Determinan Reduksi");
                            System.exit(0);
                        } else {
                            // Determinan Reduksi Baris dari File
                            System.out.println("File Determinan Reduksi");
                            System.exit(0);
                        }
                        break;
                    case 2:
                        System.out.println("Ekspansi Kofaktor");
                        inputType = inputTypeUI();

                        if (inputType == 1) {
                            // Determinan Ekspansi Kofaktor dari Keyboard
                            System.out.println("Keyboard Determinan Ekspansi Kofaktor");
                            System.exit(0);
                        } else {
                            // Determinan Ekspansi Kofaktor dari File
                            System.out.println("File Determinan Ekspansi Kofaktor");
                            System.exit(0);
                        }
                        break;
                    case 3:
                        run = false;
                        break;
                    default:
                        System.out.println("Input tidak valid. Silakan masukkan angka (1-3)!\n");
                        Thread.sleep(1000);
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInput tidak valid. Silakan masukkan angka (1-3)!\n");
                inputScanner.nextLine();
                Thread.sleep(1000);
            }
        }
    }

    public static void inverseMatrixUI() throws IOException, InterruptedException{
        int choice;
        boolean run = true;

        while (run) {
            clearScreen();
            System.out.print("""
                    PILIH METODE
                    1. Metode OBE
                    2. Metode Matriks Adjoin
                    3. Kembali
                    """);
            System.out.println();
            System.out.print("Masukkan pilihan: ");

            try {
                choice = inputScanner.nextInt();
                System.out.println();

                int inputType;
                switch (choice) {
                    case 1:
                        System.out.println("OBE");
                        inputType = inputTypeUI();

                        if (inputType == 1) {
                            // Invers OBE dari Keyboard
                            System.out.println("Keyboard Invers OBE");
                            System.exit(0);
                        } else {
                            // Invers OBE dari File
                            System.out.println("File Invers OBE");
                            System.exit(0);
                        }
                        break;
                    case 2:
                        System.out.println("Matriks Adjoin");
                        inputType = inputTypeUI();

                        if (inputType == 1) {
                            // Invers Adjoin dari Keyboard
                            System.out.println("Keyboard Invers Adjoin");
                            System.exit(0);
                        } else {
                            // Invers Adjoin dari File
                            System.out.println("File Invers Adjoin");
                            System.exit(0);
                        }
                        break;
                    case 3:
                        run = false;
                        break;
                    default:
                        System.out.println("Input tidak valid. Silakan masukkan angka (1-3)!\n");
                        Thread.sleep(1000);
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInput tidak valid. Silakan masukkan angka (1-3)!\n");
                inputScanner.nextLine();
                Thread.sleep(1000);
            }
        }
    }

    public static void polynomialInterpolationUI() throws IOException, InterruptedException {
        clearScreen();
        int inputType = inputTypeUI();

        if (inputType == 1) {
            // Interpolasi Polinom dari Keyboard
            System.out.println("Keyboard Interpolasi Polinom");
            System.exit(0);
        } else {
            // Interpolasi Polinom dari File
            System.out.println("File Interpolasi Polinom");
            System.exit(0);
        }
    }

    public static void bicubicSplineInterpolationUI() throws IOException, InterruptedException {
        clearScreen();
        BicubicSplineInterpolation.main();
    }

    public static void regressionUI() throws IOException, InterruptedException {
        clearScreen();
        int inputType = inputTypeUI();

        if (inputType == 1) {
            // Regresi dari Keyboard
            System.out.println("Keyboard Regresi");
            System.exit(0);
        } else {
            // Regresi dari File
            System.out.println("File Regresi");
            System.exit(0);
        }
    }

    public static void imageInterpolationUI() throws IOException, InterruptedException{
        clearScreen();
    }

    public static void headerUI(){
        
    }

    public static void menuUI() throws IOException, InterruptedException {
        int choice;
        boolean run = true;

        while (run) {
            clearScreen();
            System.out.print("""
                    MENU
                    1. Sistem Persamaan Linier
                    2. Determinan
                    3. Matriks Balikan
                    4. Interpolasi Polinom
                    5. Interpolasi Bicubic Spline
                    6. Regresi Linier dan Kuadratik Berganda
                    7. Interpolasi Gambar
                    8. Keluar
                    """);
            System.out.println();
            System.out.print("Masukkan pilihan: ");

            try {
                choice = inputScanner.nextInt();
                System.out.println();

                switch (choice) {
                    case 1:
                        splUI();
                        break;
                    case 2:
                        determinantUI();
                        break;
                    case 3:
                        inverseMatrixUI();
                        break;
                    case 4:
                        polynomialInterpolationUI();
                        break;
                    case 5:
                        bicubicSplineInterpolationUI();
                        break;
                    case 6:
                        regressionUI();
                        break;
                    case 7:
                        imageInterpolationUI();
                        break;
                    case 8:
                        System.out.println("Keluar dari program ...");
                        run = false;
                        break;
                    default:
                        System.out.println("Input tidak valid. Silakan masukkan angka (1-8)!\n");
                        Thread.sleep(1000);
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInput tidak valid. Silakan masukkan angka (1-8)!\n");
                inputScanner.nextLine();
                Thread.sleep(1000);
            }
        }
    }

}
