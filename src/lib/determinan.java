package lib;

import java.util.Scanner;
import java.lang.Math;

public class determinan {
    public static void main(String[] args) {
        Matrix m = new Matrix(3, 3);
        int x;
        Scanner scanner = new Scanner(System.in);
        m.readMatrix(scanner);
        System.out.println(getDeterminanKofaktor(m));
        System.out.println(determinanReduksi(m));
    }

    public static double determinanReduksi(Matrix m) {
        int i, swapCount, j;
        double k, determinan = 1;
        swapCount = m.sortRowByZero();
        for (i = 1; i < m.getRow(); i++) {
            if (m.rowLength(i) == m.rowLength(i - 1) && m.rowLength(i) != m.getCol()) {
                k = m.getElmt(i, m.rowLength(i)) / m.getElmt(i - 1, m.rowLength(i - 1));
                m.plusKRow(i, -k, i - 1);
                swapCount += m.sortRowByZero();
                i--;
            }
        }
        for (i = 0; i < m.getRow(); i++) {
            for (j = 0; j < m.getCol(); j++) {
                if (i == j) {
                    determinan *= m.getElmt(i, j);
                }
            }
        }
        determinan *= Math.pow(-1, swapCount);
        // di buletin biar ga keos angkanya
        return Math.round(determinan * 10000) / 10000;
    }

    // Function to calculate the determinant of a 2x2 matrix
    public static double determinan2x2(Matrix m) {
        return m.getElmt(0, 0) * m.getElmt(1, 1) - m.getElmt(0, 1) * m.getElmt(1, 0);
    }
}