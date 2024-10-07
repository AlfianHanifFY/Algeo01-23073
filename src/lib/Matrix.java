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

    public void readMatrix() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                content[i][j] = scanner.nextFloat();
            }
        }
        scanner.close();
    }

    public void printMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(content[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

}
