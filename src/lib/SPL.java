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
        // m1.readMatrix(scanner);
        A.readMatrix();
        B.readMatrix();
        // gaussSolution(m1);
        // gaussJordanSolution(m1);
        balikanSolution(A, B);
        cramerSolution(A, B);
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
    public static String[] gaussJordanSolution(Matrix m) {
        // co ini harus dalam bentuk matrix augmented
        int i, j, ex = 0, n;
        double val;
        m.generateEselonReduksi();
        // karna udh eselon reduksi, jadi tinggal spam elmt terakhir row
        if (isNoSulution(m)) {
            String[] txt = new String[1];
            txt[0] = "Tidak ada solusi ! ";
            System.out.println("Tidak ada solusi ! ");
            return txt;
        } else if (isLotSolution(m)) {
            String[] txt = new String[m.getCol()];
            System.out.println("Banyak Solusi : ");
            m.generateEselonReduksi();
            for (i = 0; i < m.getCol() - 1; i++) {
                n = i - ex;
                if (m.rowLength(n) == m.getCol()) {
                    txt[n] = String.format("x" + (n + 1) + " = " + (char) (97 + i));
                    System.out.print("x" + (n + 1) + " = " + (char) (97 + i));
                } else if (i != m.rowLength(n)) {
                    txt[n] = String.format("x" + (n + 1) + " = " + (char) (97 + i));
                    System.out.print("x" + (n + 1) + " = " + (char) (97 + i));
                    ex += 1;
                } else {
                    val = m.getElmt(n, m.getCol() - 1);
                    String restxt = new String();
                    restxt = String.format("x" + (m.rowLength(n) + 1) + " = " + val);
                    System.out.print("x" + (m.rowLength(n) + 1) + " = " + val);
                    for (j = m.rowLength(n) + 1; j < m.getCol() - 1; j++) {
                        if (m.getElmt(n, j) != 0) {
                            val = m.getElmt(n, j) * (-1);
                            if (val > 0) {
                                restxt += String.format(" + " + val + "" + (char) (97 + j));
                                System.out.print(" + " + val + "" + (char) (97 + j));
                            } else {
                                restxt += String.format(" - " + val + "" + (char) (97 + j));
                                System.out.print(" - " + (val * -1) + "" + (char) (97 + j));
                            }

                        }
                    }
                    txt[n] = restxt;
                }
                System.out.println();
            }
            return txt;
        } else if (isUnique(m)) {
            String[] txt = new String[m.getRow()];
            System.out.println("Solusi unik : ");
            for (i = 0; i < m.getRow(); i++) {
                // format biar ga floating point ... (keos)
                txt[i] = String.format(Locale.US, "x%d = %.4f%n", (i + 1), m.getElmt(i, m.getCol() - 1));
                System.out.printf(Locale.US, "x%d = %.4f%n", (i + 1), m.getElmt(i, m.getCol() - 1));
            }
            return txt;
        }
        return null;
    }

    public static boolean isUnique(Matrix m) {
        // m adalah matrix augmented eselon baris
        int i;
        for (i = 0; i < m.getRow(); i++) {
            if (m.rowLength(i) == m.getCol()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLotSolution(Matrix m) {
        // m adalah matrix augmented eselon baris
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
        // m adalah matrix augmented eselon baris
        int i;
        for (i = 0; i < m.getRow(); i++) {
            if (m.rowLength(i) == m.getCol() - 1) {
                return true;
            }
        }
        return false;
    }

    public static String[] gaussSolution(Matrix m) {
        // SOLUSI BANYAKNYA MASIH KEOS -- NANTI DI CEK
        int i, j, n, ex = 0;
        double[] res = new double[m.getCol() - 1];
        double val;
        m.generateEselon();

        if (isNoSulution(m)) {
            String[] txt = new String[1];
            txt[0] = "Tidak ada solusi ! ";
            System.out.println("Tidak ada solusi ! ");
            return txt;
        } else if (isLotSolution(m)) {
            String[] txt = new String[m.getCol()];
            System.out.println("Banyak Solusi : ");
            m.generateEselonReduksi();
            for (i = 0; i < m.getCol() - 1; i++) {
                n = i - ex;
                if (m.rowLength(n) == m.getCol()) {
                    txt[n] = String.format("x" + (n + 1) + " = " + (char) (97 + i));
                    System.out.print("x" + (n + 1) + " = " + (char) (97 + i));
                } else if (i != m.rowLength(n)) {
                    txt[n] = String.format("x" + (n + 1) + " = " + (char) (97 + i));
                    System.out.print("x" + (n + 1) + " = " + (char) (97 + i));
                    ex += 1;
                } else {
                    val = m.getElmt(n, m.getCol() - 1);
                    String restxt = new String();
                    restxt = String.format("x" + (m.rowLength(n) + 1) + " = " + val);
                    System.out.print("x" + (m.rowLength(n) + 1) + " = " + val);
                    for (j = m.rowLength(n) + 1; j < m.getCol() - 1; j++) {
                        if (m.getElmt(n, j) != 0) {
                            val = m.getElmt(n, j) * (-1);
                            if (val > 0) {
                                restxt += String.format(" + " + val + "" + (char) (97 + j));
                                System.out.print(" + " + val + "" + (char) (97 + j));
                            } else {
                                restxt += String.format(" - " + val + "" + (char) (97 + j));
                                System.out.print(" - " + (val * -1) + "" + (char) (97 + j));
                            }

                        }
                    }
                    txt[n] = restxt;
                }
                System.out.println();
            }
            return txt;
        } else if (isUnique(m)) {
            String[] txt = new String[m.getRow()];
            System.out.println("Solusi unik : ");
            for (i = m.getRow() - 1; i >= 0; i--) {
                val = 0;
                for (j = m.rowLength(i) + 1; j < m.getCol() - 1; j++) {
                    val -= m.getElmt(i, j) * res[j];
                }
                res[i] = m.getElmt(i, m.getCol() - 1) + val;
            }
            for (i = 0; i < m.getCol() - 1; i++) {
                txt[i] = String.format("x" + (i + 1) + " = " + res[i]);
                System.out.println("x" + (i + 1) + " = " + res[i]);
            }
            return txt;
        }
        return null;
    }

    // metode balikan
    public static String[] balikanSolution(Matrix A, Matrix B) {
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
        String[] res = new String[x.getRow()];
        for (i = 0; i < x.getRow(); i++) {
            res[i] = String.format(Locale.US, "x%d = %.4f%n", (i + 1), x.getElmt(i, 0));
            System.out.printf(Locale.US, "x%d = %.4f%n", (i + 1), x.getElmt(i, 0));
        }
        return res;
    }

    // metode cramer
    // khusus n peubah dan n persamaan
    public static String[] cramerSolution(Matrix A, Matrix B) {
        // Ax = B

        // solusi :
        // xn = det(An)/det(A)

        int j;
        double detA, detAn, x;

        Matrix An;
        detA = determinan.getDeterminanKofaktor(A);
        String[] res = new String[A.getCol()];
        for (j = 0; j < A.getCol(); j++) {
            An = Matrix.changeCol(A, j, B);
            detAn = determinan.getDeterminanKofaktor(An);
            x = detAn / detA;
            res[j] = String.format(Locale.US, "x%d = %.4f%n", (j + 1), x);
            System.out.printf(Locale.US, "x%d = %.4f%n", (j + 1), x);
        }
        return res;

    }
}
