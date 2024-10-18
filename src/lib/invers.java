package lib;

import java.util.Scanner;

public class invers {
    // ga punya atribut

    // buat nge tes
    public static void main(String[] args) {
        Matrix m = new Matrix(3, 3);
        Scanner scanner = new Scanner(System.in);
        m.readMatrix();
        showInversOBE(m);
        showInversAdjoin(m);
    }

    // func and proc
    public static Matrix getInversOBE(Matrix m) {
        // m.col harus == m.row
        // bakal ngirim hasil OBE buat dapet invers
        // kalo ga punya invers bakal kirim matrix kosong
        Matrix M, IM;
        IM = Matrix.createMatrixIdentitas(m.getCol());
        M = Matrix.createAugmented(m, IM);
        M.generateEselonReduksi();
        if (isInversValid(Matrix.getHalfLeft(M))) {
            M = Matrix.getHalfRigth(M);
        } else {
            M = Matrix.createMatrixKosong(m.getCol());
        }
        return M;
    }

    public static void showInversOBE(Matrix m) {
        // buat nge print invers matrix
        Matrix M;
        M = getInversOBE(m);
        if (isInversValid(M)) {
            M.printMatrix();
        } else {
            System.out.println("matrix tidak memiliki invers !");
        }

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

    public static void showInversAdjoin(Matrix m) {
        Matrix M;
        // A' = 1/det(A) * adj(A)
        M = Matrix.multiplyMatrixByConst(determinan.getAdjoin(m), (1 / determinan.determinanReduksi(m)));
        M.printMatrix();
    }

    public static Matrix getInversAdjoin(Matrix m) {
        Matrix M;
        // A' = 1/det(A) * adj(A)
        M = Matrix.multiplyMatrixByConst(determinan.getAdjoin(m), (1 / determinan.determinanReduksi(m)));
        return M;
    }

}
