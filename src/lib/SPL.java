package lib;

import java.util.Locale;
import java.util.Scanner;

public class SPL {
    // ga punya atribut

    // buat nge tes
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[] res;
        int i;
        Matrix m1 = new Matrix(3, 4);
        Matrix A = new Matrix(3, 3);
        Matrix B = new Matrix(3, 1);
        m1.readMatrix(scanner);
        A.readMatrix(scanner);
        B.readMatrix(scanner);
        // gaussSolution(m1);
        gaussJordanSolution(m1);
        balikanSolution(A, B);
        scanner.close();
    }

    public static double[] getSolution(Matrix m) {
        // solusi harus unik
        int i;
        double[] res = new double[m.getCol() - 1];
        m.generateEselonReduksi();
        if (isUnique(m)) {
            for (i = 0; i < m.getRow(); i++) {
                res[i] = m.getElmt(i, m.getCol() - 1);
            }
        }
        return res;
    }

    // func and proc
    public static void gaussJordanSolution(Matrix m) {
        // co ini harus dalam bentuk matrix augmented
        int i, j, ex = 0, n;
        double val;
        m.generateEselonReduksi();
        // karna udh eselon reduksi, jadi tinggal spam elmt terakhir row
        if (isNoSulution(m)) {
            System.out.println("Tidak ada solusi ! ");
        } else if (isLotSolution(m)) {
            System.out.println("Banyak Solusi : ");
            for (i = 0; i < m.getCol() - 1; i++) {
                n = i - ex;
                if (m.rowLength(n) == m.getCol()) {
                    System.out.print("x" + (n + 1) + " = " + (char) (97 + i));
                } else if (i != m.rowLength(n)) {
                    System.out.print("x" + (n + 1) + " = " + (char) (97 + i));
                    ex += 1;
                } else {
                    val = m.getElmt(n, m.getCol() - 1);
                    System.out.print("x" + (m.rowLength(n) + 1) + " = " + val);
                    for (j = m.rowLength(n) + 1; j < m.getCol() - 1; j++) {
                        if (m.getElmt(n, j) != 0) {
                            val = m.getElmt(n, j) * (-1);
                            if (val > 0) {
                                System.out.print(" + " + val + "" + (char) (97 + j));
                            } else {
                                System.out.print(" - " + (val * -1) + "" + (char) (97 + j));
                            }

                        }
                    }

                }
                System.out.println();
            }
        } else if (isUnique(m)) {
            System.out.println("Solusi unik : ");
            for (i = 0; i < m.getRow(); i++) {
                // format biar ga floating point ... (keos)
                System.out.printf(Locale.US, "x%d = %.4f%n", (i + 1), m.getElmt(i, m.getCol() - 1));
            }
        }

    }

    public static boolean isUnique(Matrix m) {
        // m adalah matrix augmented
        int i;
        for (i = 0; i < m.getRow(); i++) {
            if (m.rowLength(i) == m.getCol()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLotSolution(Matrix m) {
        // m adalah matrix augmented
        int i;
        if (m.getRow() < m.getCol() - 1) {
            return true;
        }
        for (i = 0; i < m.getRow(); i++) {
            if (m.rowLength(i) == m.getCol()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoSulution(Matrix m) {
        // m adalah matrix augmented
        int i;
        for (i = 0; i < m.getRow(); i++) {
            if (m.rowLength(i) == m.getCol() - 1) {
                return true;
            }
        }
        return false;
    }

    public static void gaussSolution(Matrix m) {
        // SOLUSI BANYAKNYA MASIH KEOS -- NANTI DI CEK
        int i, j, n, ex = 0;
        double[] res = new double[m.getCol() - 1];
        double val;
        m.generateEselon();
        m.printMatrix();
        if (isNoSulution(m)) {
            System.out.println("Tidak ada solusi ! ");
        } else if (isLotSolution(m)) {
            System.out.println("Banyak Solusi : ");
            m.generateEselonReduksi();
            for (i = 0; i < m.getCol() - 1; i++) {
                n = i - ex;
                if (m.rowLength(n) == m.getCol()) {
                    System.out.print("x" + (n + 1) + " = " + (char) (97 + i));
                } else if (i != m.rowLength(n)) {
                    System.out.print("x" + (n + 1) + " = " + (char) (97 + i));
                    ex += 1;
                } else {
                    val = m.getElmt(n, m.getCol() - 1);
                    System.out.print("x" + (m.rowLength(n) + 1) + " = " + val);
                    for (j = m.rowLength(n) + 1; j < m.getCol() - 1; j++) {
                        if (m.getElmt(n, j) != 0) {
                            val = m.getElmt(n, j) * (-1);
                            if (val > 0) {
                                System.out.print(" + " + val + "" + (char) (97 + j));
                            } else {
                                System.out.print(" - " + (val * -1) + "" + (char) (97 + j));
                            }

                        }
                    }

                }
                System.out.println();
            }
        } else if (isUnique(m)) {
            System.out.println("Solusi unik : ");
            for (i = m.getRow() - 1; i >= 0; i--) {
                val = 0;
                for (j = m.rowLength(i) + 1; j < m.getCol() - 1; j++) {
                    val -= m.getElmt(i, j) * res[j];
                }
                res[i] = m.getElmt(i, m.getCol() - 1) + val;
            }
            for (i = 0; i < m.getCol() - 1; i++) {
                System.out.println("x" + (i + 1) + " = " + res[i]);
            }
        }
    }

    // metode balikan
    public static void balikanSolution(Matrix A, Matrix B) {
        // Ax = B
        // x = A'B
        Matrix x;
        int i;

        // invers matrix A
        A = invers.getInversOBE(A);

        // kalikan invers A dengan B
        x = Matrix.multiplyMatrix(A, B);

        // x adalah solusi
        // print x
        for (i = 0; i < x.getRow(); i++) {
            System.out.printf(Locale.US, "x%d = %.4f%n", (i + 1), x.getElmt(i, 0));
        }
    }
}
