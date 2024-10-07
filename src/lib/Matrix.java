package lib;

import java.util.Scanner;

public class Matrix {
    float[][] content;
    int rows;
    int cols;

    // ==== ini buat nge tes co ==== //
    // public static void main(String[] args) {
    // Matrix m1 = new Matrix(3, 3);
    // m1.readMatrix();
    // System.out.println();
    // m1.printMatrix();
    // }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        content = new float[rows][cols];
    }

}
