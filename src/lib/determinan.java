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

    // Function to get the minor matrix by removing a specified row and column
    public static Matrix getMinorEntri(Matrix m, int row, int col) {
        int minorRow = 0, minorCol;

        Matrix M = new Matrix(m.getRow() - 1, m.getCol() - 1);

        for (int i = 0; i < m.getRow(); i++) {
            if (i == row)
                continue;
            minorCol = 0;
            for (int j = 0; j < m.getCol(); j++) {
                if (j == col)
                    continue;
                M.setElMT(minorRow, minorCol, m.getElmt(i, j));
                minorCol++;
            }
            minorRow++;
        }

        return M;
    }

    public static Matrix getMatrixKofaktor(Matrix m) {
        Matrix M = new Matrix(m.getRow(), m.getCol()), mt;
        double val;
        int i, j;
        for (i = 0; i < m.getRow(); i++) {
            for (j = 0; j < m.getCol(); j++) {
                mt = getMinorEntri(m, i, j);
                val = getDeterminanKofaktor(mt);
                if (((i + 1) + (j + 1)) % 2 != 0) {
                    val *= -1;
                }
                M.setElMT(i, j, val);
            }
        }
        return M;
    }

    // Function to calculate the determinant using the cofactor expansion
    public static double getDeterminanKofaktor(Matrix m) {
        double det = 0;

        // Base case: if it's a 1x1 matrix, return the only element
        if (m.getCol() == 1 && m.getRow() == 1) {
            return m.getElmt(0, 0);
        }

        // Base case: if it's a 2x2 matrix, use the simple determinant formula
        if (m.isSquare2x2()) {
            return determinan2x2(m);
        }

        // Recursive case: expand along the first column
        for (int i = 0; i < m.getRow(); i++) {
            // Get the minor matrix for the element at (i, 0)
            Matrix minor = getMinorEntri(m, i, 0);

            // Calculate the determinant recursively and apply the cofactor
            det += Math.pow(-1, i) * m.getElmt(i, 0) * getDeterminanKofaktor(minor);
        }

        return det;
    }

}