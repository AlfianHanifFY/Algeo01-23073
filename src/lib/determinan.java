package lib;

import java.util.Scanner;

public class determinan {
    public static void main(String[] args) {
        Matrix m = new Matrix(3, 3);
        int x;
        Scanner scanner = new Scanner(System.in);
        m.readMatrix(scanner);
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
}
