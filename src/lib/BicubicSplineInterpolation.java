package lib;

import java.lang.Math;


public class BicubicSplineInterpolation {

    public static String[] main(double[] temp) {
        Matrix M = new Matrix(16, 1);
        Matrix X = new Matrix(16, 16);
        Matrix res = new Matrix(16, 1);
        double a, b, val = 0;

        constructX(X);

        for (int i = 0; i < 16; i++) {
            M.setElMT(i, 0, temp[i]);
        }
        a = temp[16];
        b = temp[17];

        // Inverse X and multiply with M
        Matrix invX = invers.getInversOBE(X);
        res = Matrix.multiplyMatrix(invX, M);

        // solve for a and b
        int idx = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                val += res.getElmt(idx, 0) * Math.pow(a, i) * Math.pow(b, j);
                idx++;
            }
        }
        System.out.println();
        String[] s = new String[1];
        s[0] = "f(" + a + "," + b + ") = " + val;
        System.out.println(s[0]);
        System.out.println();
        return s;
    }

    // Construct Matrix X
    public static void constructX(Matrix X) {
        int row = 0, col = 0;
        // f(x, y)
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                col = 0;
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 4; i++) {
                        X.setElMT(row, col, Math.pow(x, i) * Math.pow(y, j));
                        col++;
                    }
                }
                row++;
            }
        }
        // fx(x, y);
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                col = 0;
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 4; i++) {
                        if (i != 0)
                            X.setElMT(row, col, i * Math.pow(x, i - 1) * Math.pow(y, j));
                        else
                            X.setElMT(row, col, 0);
                        col++;
                    }
                }
                row++;
            }
        }
        // fy(x, y)
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                col = 0;
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 4; i++) {
                        if (j != 0)
                            X.setElMT(row, col, j * Math.pow(x, i) * Math.pow(y, j - 1));
                        else
                            X.setElMT(row, col, 0);
                        col++;
                    }
                }
                row++;
            }
        }
        // fxy(x, y)
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                col = 0;
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 4; i++) {
                        if (i != 0 && j != 0)
                            X.setElMT(row, col, i * j * Math.pow(x, i - 1) * Math.pow(y, j - 1));
                        else
                            X.setElMT(row, col, 0);
                        col++;
                    }
                }
                row++;
            }
        }
    }
}