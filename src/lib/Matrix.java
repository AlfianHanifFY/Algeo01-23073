package lib;

import java.util.Scanner;
import java.util.Locale;

public class Matrix {
    double[][] matrix;
    int rows;
    int cols;

    // ==== ini buat nge tes co ==== //
    public static void main(String[] args) {
        Matrix m1 = new Matrix(3, 4);
        int a, b;
        m1.readMatrix();
        System.out.println();
        m1.printMatrix();
        System.out.println();
        m1.generateEselonReduksi();
        m1.printMatrix();
        // System.out.println();
        // a = m1.colLength(0);
        // b = m1.rowLength(0);
        // System.out.println("list legth = " + b + " " + a);
        // m1.sortRowByZero();
        // m1.printMatrix();
        // System.out.println("plusKRow");
        // m1.plusKRow(0, 2, 1);
        // m1.printMatrix();
        // System.out.println("transpose");
        // m1.transpose();
        // m1.printMatrix();

    }

    // Konstruktor
    public Matrix(int _rows, int _cols) {
        this.rows = _rows;
        this.cols = _cols;
        this.matrix = new double[_rows][_cols];
    }

    // Selektor
    public double[] getRowContent(int row) {
        double[] result = new double[rows];
        for (int i = 0; i < this.cols; i++) {
            result[i] = matrix[row][i];
        }
        return result;

    }

    public double[] getColContent(int col) {
        double[] result = new double[this.cols];
        for (int i = 0; i < this.cols; i++) {
            result[i] = this.matrix[i][col];
        }
        return result;
    }

    public void readMatrix() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        scanner.close();
    }

    public void printMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // format 2 angka dibelakang koma
                System.out.print(String.format(Locale.US, "%.2f ", matrix[i][j]));
            }
            System.out.print("\n");
        }
    }

    public double getElmt(int row, int col) {
        return this.matrix[row][col];
    }

    public int getCol() {
        return this.cols;
    }

    public int getRow() {
        return this.rows;
    }

    public void setElMT(int row, int col, double val) {
        this.matrix[row][col] = val;
    }

    // OBE

    // swap lane
    public void swapRow(int row1, int row2) {
        double temp;
        int i;
        for (i = 0; i < getCol(); i++) {
            temp = getElmt(row1, i);
            setElMT(row1, i, getElmt(row2, i));
            setElMT(row2, i, temp);
        }

    }

    // plusKRow
    public void plusKRow(int row, double k, int krow) {
        int i;
        for (i = 0; i < getCol(); i++) {
            setElMT(row, i, getElmt(row, i) + getElmt(krow, i) * k);
        }
    }

    public void divideRow(int row, double k) {
        // ngebagi row pake k
        int i;
        for (i = 0; i < getCol(); i++) {
            if (getElmt(row, i) != 0) {
                setElMT(row, i, getElmt(row, i) / k);
            }

        }
    }

    // transpose
    public void transpose() {
        Matrix newM = new Matrix(getRow(), getCol());
        int i, j;
        for (i = 0; i < getRow(); i++) {
            for (j = 0; j < getCol(); j++) {
                newM.matrix[i][j] = getElmt(j, i);
            }
        }
        this.matrix = newM.matrix;
    }

    // cek elemen baris yang ga 0
    public int rowLength(int row) {
        int i, sum = 0;
        boolean valid = true;
        for (i = 0; i < getCol(); i++) {
            if (getElmt(row, i) == 0 && valid) {
                sum += 1;
            } else {
                valid = false;
            }
        }
        return sum;
    }

    public int colLength(int col) {
        int i, sum = 0;
        boolean valid = true;
        for (i = 0; i < getRow(); i++) {
            if (getElmt(i, col) == 0 && valid) {
                sum += 1;
            } else {
                valid = false;
            }
        }
        return sum;
    }

    public void sortRowByZero() {
        // pokoknya nanti urut berdasarkan jumlah 0 dari yang dikit sampe banyak
        int i, j;
        for (i = 0; i < getRow(); i++) {
            for (j = i; j < getRow(); j++) {
                if (rowLength(i) > rowLength(j)) {
                    swapRow(i, j);
                }
            }
        }
    }

    public void generateEselon() {
        int i;
        double k;
        sortRowByZero();
        for (i = 1; i < getRow(); i++) {
            if (rowLength(i) == rowLength(i - 1) && rowLength(i) != getCol()) {
                k = getElmt(i, rowLength(i)) / getElmt(i - 1, rowLength(i - 1));
                plusKRow(i, -k, i - 1);
                sortRowByZero();
                i--;
                printMatrix();
                System.out.println();
            }
        }
        for (i = 0; i < getRow(); i++) {
            if (rowLength(i) != getCol()) {
                k = getElmt(i, rowLength(i));
                divideRow(i, k);
            }

        }

    }

    public void generateEselonReduksi() {
        int i, j;
        double k;
        generateEselon();
        for (i = 1; i < getRow(); i++) {
            for (j = i - 1; j >= 0; j--) {
                printMatrix();
                System.out.println();
                k = getElmt(j, rowLength(i));
                plusKRow(j, -k, i);
            }
        }

    }
}
