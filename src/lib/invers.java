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

    public static boolean isInversValid(Matrix leftM) {
        // m adalah matrix yang sudah di invers melalui OBE
        int i;
        for (i = 0; i < leftM.getRow(); i++) {
            if (leftM.rowLength(i) == leftM.getRow()) {
                return false;
            }
        }
        return true;
    }
}
