package lib;

import java.util.Scanner;

public class invers {
    // ga punya atribut

    // buat nge tes
    public static void main(String[] args) {
        Matrix m = new Matrix(3, 3);
        Scanner scanner = new Scanner(System.in);
        m.readMatrix(scanner);
        showInversOBE(m);
    }

}
