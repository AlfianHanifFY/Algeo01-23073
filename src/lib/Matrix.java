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
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new double[rows][cols];
    }

    // Selektor
    public double[] getRow(int row) {
        double[] result = new double[rows];
        for (int i = 0; i < cols; i++) {
            result[i] = matrix[row][i];
        }
        return result;

    }

    public double[] getCol(int col) {
        double[] result = new double[cols];
        for (int i = 0; i < cols; i++) {
            result[i] = matrix[i][col];
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

}
