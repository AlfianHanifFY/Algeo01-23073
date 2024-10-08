package lib;

import java.util.Scanner;

public class Matrix {
    double[][] matrix;
    int rows;
    int cols;

    // ==== ini buat nge tes co ==== //
    // public static void main(String[] args) {
    // Matrix m1 = new Matrix(3, 3);
    // m1.readMatrix();
    // System.out.println();
    // m1.printMatrix();
    // }

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
                System.out.print(matrix[i][j] + " ");
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

}
