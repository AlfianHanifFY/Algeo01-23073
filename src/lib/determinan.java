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

}
