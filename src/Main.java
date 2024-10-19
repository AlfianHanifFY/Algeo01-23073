import java.io.IOException;
import java.util.*;

import lib.InterpolasiPolinomial;
import lib.RegresiBerganda;
import lib.BicubicSplineInterpolation;
import lib.Matrix;
import lib.SPL;
import lib.determinan;
import lib.invers;
import lib.IO;
import lib.ImageResizing;

public class Main {
    static Scanner inputScanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException, IOException {
        clearScreen();
        loadingUI();
        Thread.sleep(300);

        menuUI();
    }

    public static void loadingUI() throws InterruptedException {
        int progressBarLength = 30;
        int totalSteps = 100;

        System.out.println(
                "███████╗██╗   ██╗██████╗ ███████╗███████╗     █████╗ ██╗      ██████╗ ███████╗ ██████╗        \n" + //
                        "╚══██╔══╝██║   ██║██╔══██╗██╔════╝██╔════╝    ██╔══██╗██║     ██╔════╝ ██╔════╝██╔═══██╗       \n"
                        + //
                        "   ██║   ██║   ██║██████╔╝█████╗  ███████╗    ███████║██║     ██║  ███╗█████╗  ██║   ██║       \n"
                        + //
                        "   ██║   ██║   ██║██╔══██╗██╔══╝  ╚════██║    ██╔══██║██║     ██║   ██║██╔══╝  ██║   ██║       \n"
                        + //
                        "   ██║   ╚██████╔╝██████╔╝███████╗███████║    ██║  ██║███████╗╚██████╔╝███████╗╚██████╔╝       \n"
                        + //
                        "   ╚═╝    ╚═════╝ ╚═════╝ ╚══════╝╚══════╝    ╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚══════╝ ╚═════╝        \n"
                        + //
                        "                                                                                               \n"
                        + //
                        "██████╗  █████╗ ██████╗ ██╗  ██╗██╗██████╗  █████╗ ███╗   ██╗    ███████╗██╗██████╗ ██╗██╗     \n"
                        + //
                        "██╔══██╗██╔══██╗██╔══██╗██║ ██╔╝██║██╔══██╗██╔══██╗████╗  ██║    ██╔════╝██║██╔══██╗██║██║     \n"
                        + //
                        "██████╔╝███████║██████╔╝█████╔╝ ██║██████╔╝███████║██╔██╗ ██║    ███████╗██║██████╔╝██║██║     \n"
                        + //
                        "██╔═══╝ ██╔══██║██╔══██╗██╔═██╗ ██║██╔══██╗██╔══██║██║╚██╗██║    ╚════██║██║██╔═══╝ ██║██║     \n"
                        + //
                        "██║     ██║  ██║██║  ██║██║  ██╗██║██║  ██║██║  ██║██║ ╚████║    ███████║██║██║     ██║███████╗\n"
                        + //
                        "╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝    ╚══════╝╚═╝╚═╝     ╚═╝╚══════╝\n"
                        + //
                        "                                                                                               ");
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

    public static int inputTypeUI() throws IOException, InterruptedException {
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

    public static void splUI() throws IOException, InterruptedException {
        int choice;
        boolean run = true, valid = true;
        String[] s;

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

                if (choice >= 1 && choice <= 4) {
                    int inputType;
                    String fileName;

                    inputType = inputTypeUI();

                    Matrix A, B, m;
                    if (inputType == 1) {
                        // Baca dari Keyboard
                        // Ax = B
                        // kalo input matrix A berlebih bakal langsung masuk ke matrix B
                        System.out.println("\nMatrix A : ");
                        A = IO.readMatrixFromKeyboard();

                        System.out.println("\nMatrix B : ");
                        B = IO.readMxNMatrixFromKeyboard(A.getRow(), 1);

                        // augmented matrix
                        m = Matrix.createAugmented(A, B);
                        System.out.println("\nMatrix Augmented A dan B : ");
                        m.printMatrix();

                    } else {
                        // Baca dari File
                        fileName = IO.readFileName();
                        IO io = new IO(fileName);
                        System.out.println();
                        // augmented
                        m = io.readMatrixFromFile();

                        // Ax = B
                        A = Matrix.disassembleAugmented(m, true);
                        B = Matrix.disassembleAugmented(m, false);
                        // kalau mau tes
                        // m.printMatrix();
                        // System.exit(0);
                    }

                    switch (choice) {
                        case 1:
                            // header
                            // function gauss
                            s = SPL.gaussSolution(m);
                            IO.saveFile(s);
                            break;
                        case 2:
                            // header
                            // function gauss-jordan
                            s = SPL.gaussJordanSolution(m);
                            IO.saveFile(s);
                            break;
                        case 3:
                            // header
                            // function balikan
                            // cek A kotak ga , cek dulu A punya balikan ga
                            String text = new String("");
                            Matrix eselon = new Matrix(m.getRow(), m.getCol());
                            Matrix.copyMatrix(m, eselon);
                            eselon.generateEselon();
                            if (!Matrix.haveInverse(A) || !SPL.isUnique(eselon)) {
                                valid = false;
                                if (!Matrix.haveInverse(A)) {
                                    System.out.println("\nMatrix A tidak memiliki inverse !");
                                    text += "Matrix tidak memiliki inverse !";
                                } else if (!SPL.isUnique(eselon)) {
                                    System.out.println("\nMatrix tidak memiliki solusi unik !");
                                    text += "Matrix tidak memiliki solusi unik !";
                                } else {
                                    System.out.println("\nMatrix tidak memiliki inverse dan solusi tidak unik!");
                                    text += "Matrix tidak memiliki inverse dan solusi tidak unik !";
                                }

                            }
                            if (valid) {
                                s = SPL.balikanSolution(A, B);
                            } else {
                                System.out.print("\nMatrix tidak dapat diselesaikan dengan metode ini !");
                                text += "\nMatrix tidak dapat diselesaikan dengan metode ini !";
                                s = IO.returnStringArr(text);
                            }
                            IO.saveFile(s);
                            break;
                        case 4:
                            // header
                            // function cramer
                            String text2 = new String("");
                            Matrix eselon2 = new Matrix(m.getRow(), m.getCol());
                            Matrix.copyMatrix(m, eselon2);
                            eselon2.generateEselon();
                            if (!Matrix.haveInverse(A) || !SPL.isUnique(eselon2)) {
                                valid = false;
                                if (!Matrix.haveInverse(A)) {
                                    System.out.println("\nMatrix A tidak memiliki inverse !");
                                    text2 += "Matrix tidak memiliki inverse !";
                                } else if (!SPL.isUnique(eselon2)) {
                                    System.out.println("\nMatrix tidak memiliki solusi unik !");
                                    text2 += "Matrix tidak memiliki solusi unik !";
                                } else {
                                    System.out.println("\nMatrix tidak memiliki inverse dan solusi tidak unik!");
                                    text2 += "Matrix tidak memiliki inverse dan solusi tidak unik !";
                                }

                            }
                            if (valid) {
                                s = SPL.cramerSolution(A, B);
                            } else {
                                System.out.print("\nMatrix tidak dapat diselesaikan dengan metode ini !");
                                text2 += "\nMatrix tidak dapat diselesaikan dengan metode ini !";
                                s = IO.returnStringArr(text2);
                            }
                            IO.saveFile(s);
                            break;
                    }

                } else if (choice == 5) {
                    run = false;
                    break;
                } else {
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

    public static void determinantUI() throws IOException, InterruptedException {
        int choice;
        boolean run = true;
        String[] s;
        double det;
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

                if (choice >= 1 && choice <= 2) {
                    int inputType;
                    String fileName;

                    inputType = inputTypeUI();

                    Matrix m;
                    if (inputType == 1) {
                        // Baca dari Keyboard
                        m = IO.readMatrixFromKeyboard();
                    } else {
                        // Baca dari File
                        fileName = IO.readFileName();
                        IO io = new IO(fileName);
                        System.out.println();
                        m = io.readMatrixFromFile();

                        // kalau mau tes
                        // m.printMatrix();
                        // System.exit(0);
                    }

                    switch (choice) {
                        case 1:
                            // header
                            // function det reduksi baris
                            det = determinan.determinanReduksi(m);
                            System.out.println("\nDeterminan matrix = " + det);
                            s = IO.returnStringArr("Determinan matrix = " + det);
                            IO.saveFile(s);
                            break;
                        case 2:
                            // header
                            // function det kofaktor
                            det = determinan.getDeterminanKofaktor(m);
                            System.out.println("\nDeterminan matrix = " + det);
                            s = IO.returnStringArr("Determinan matrix = " + det);
                            IO.saveFile(s);
                            break;
                    }
                } else if (choice == 3) {
                    run = false;
                    break;
                } else {
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

    public static void inverseMatrixUI() throws IOException, InterruptedException {
        int choice;
        boolean run = true;
        String[] s;

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

                if (choice >= 1 && choice <= 2) {
                    int inputType;
                    String fileName;

                    inputType = inputTypeUI();

                    Matrix m;
                    if (inputType == 1) {
                        // Baca dari Keyboard
                        m = IO.readMatrixFromKeyboard();

                    } else {
                        // Baca dari File
                        fileName = IO.readFileName();
                        IO io = new IO(fileName);
                        System.out.println();
                        m = io.readMatrixFromFile();

                        // kalau mau tes
                        // m.printMatrix();
                        // System.exit(0);
                    }

                    switch (choice) {
                        case 1:
                            // header
                            // function inverse OBE
                            if (Matrix.haveInverse(m)) {
                                invers.showInversOBE(m);
                                s = IO.matrixToStringArr(invers.getInversOBE(m));
                            } else {
                                System.out.println("Matrix tidak memiliki inverse !");
                                s = IO.returnStringArr("Matrix tidak memiliki inverse !");
                            }
                            IO.saveFile(s);
                            break;
                        case 2:
                            // header
                            // function inverse adjoin
                            if (Matrix.haveInverse(m)) {
                                invers.showInversAdjoin(m);
                                s = IO.matrixToStringArr(invers.getInversAdjoin(m));
                            } else {
                                System.out.println("Matrix tidak memiliki inverse !");
                                s = IO.returnStringArr("Matrix tidak memiliki inverse !");
                            }
                            IO.saveFile(s);
                            break;
                    }

                } else if (choice == 3) {
                    run = false;
                    break;
                } else {
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
        String[] s;
        s = InterpolasiPolinomial.main(inputType);
        IO.saveFile(s);
    }

    public static void bicubicSplineInterpolationUI() throws IOException, InterruptedException {
        clearScreen();
        // Header
        int inputType = inputTypeUI();

        double[] temp;

        String[] s;

        if (inputType == 1) {
            IO io = new IO("");
            temp = io.readBicubicSplineDataFromKeyboard();
        } else {
            String fileName = IO.readFileName();
            IO f = new IO(fileName);
            temp = f.readBicubicSplineDataFromFile();
        }
        s = BicubicSplineInterpolation.mainBicubicInterpolation(temp);
        IO.saveFile(s);
        Thread.sleep(500);
    }

    public static void regressionUI() throws IOException, InterruptedException {
        clearScreen();
        int choice;
        boolean run = true;
        while (run) {
            clearScreen();
            System.out.print("""
                    PILIH REGRESI
                    1. Regresi Linier Berganda
                    2. Regresi Kuadratik Berganda
                    3. Kembali
                    """);
            System.out.println();
            System.out.print("Masukkan pilihan: ");
            try {
                choice = inputScanner.nextInt();
                System.out.println();

                if (choice >= 1 && choice <= 2) {
                    int inputType = inputTypeUI();
                    String[] s;

                    if (choice == 1) {
                        s = RegresiBerganda.RegresiLinierBerganda(inputType);
                        IO.saveFile(s);
                    } else {
                        s = RegresiBerganda.RegresiKuadratikBerganda(inputType);
                        IO.saveFile(s);
                    }

                } else if (choice == 3) {
                    run = false;
                    break;
                } else {
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

    public static void imageInterpolationUI() throws IOException, InterruptedException {
        clearScreen();
        // Header
        ImageResizing.mainImageResizing();
        Thread.sleep(1000);
    }

    public static void headerUI() {

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
