package lib;

import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageResizing {

    public static void mainImageResizing() throws IOException {
        // Declare
        Matrix X = new Matrix(16, 16);
        Matrix D = new Matrix(16, 16);
        Matrix multiplier = new Matrix(16, 16);

        // Construct X and D
        BicubicSplineInterpolation.constructX(X);
        constructD(D);

        // inverse matrix and save it to inverseX
        Matrix invX = invers.getInversOBE(X);

        // multiply X and D and save it to multiplier
        multiplier = Matrix.multiplyMatrix(invX, D);
        for (int i = 0; i < multiplier.getRow(); i++) {
            for (int j = 0; j < multiplier.getCol(); j++) {
                multiplier.setElMT(i, j, multiplier.getElmt(i, j) / 4);
            }
        }

        // get image file
        String fileName = IO.readFileName();
        File file = new File(fileName);
        BufferedImage img = ImageIO.read(file);

        Matrix red = new Matrix(img.getHeight() + 4, img.getWidth() + 4);
        Matrix blue = new Matrix(img.getHeight() + 4, img.getWidth() + 4);
        Matrix green = new Matrix(img.getHeight() + 4, img.getWidth() + 4);
        Matrix tempRed = new Matrix(16, 1);
        Matrix tempBlue = new Matrix(16, 1);
        Matrix tempGreen = new Matrix(16, 1);

        Matrix[][] redRes = new Matrix[img.getHeight() + 1][img.getWidth() + 1];
        Matrix[][] blueRes = new Matrix[img.getHeight() + 1][img.getWidth() + 1];
        Matrix[][] greenRes = new Matrix[img.getHeight() + 1][img.getWidth() + 1];

        for (int i = 2; i < img.getHeight() + 2; i++) {
            for (int j = 2; j < img.getWidth() + 2; j++) {
                int pixel = img.getRGB(j - 2, i - 2);
                Color color = new Color(pixel, true);
                red.setElMT(i, j, color.getRed());
                blue.setElMT(i, j, color.getBlue());
                green.setElMT(i, j, color.getGreen());
            }
        }

        for (int i = 2; i < img.getHeight() + 2; i++) {
            red.setElMT(i, 0, red.getElmt(i, 2));
            green.setElMT(i, 0, green.getElmt(i, 2));
            blue.setElMT(i, 0, blue.getElmt(i, 2));
            red.setElMT(i, 1, red.getElmt(i, 2));
            green.setElMT(i, 1, green.getElmt(i, 2));
            blue.setElMT(i, 1, blue.getElmt(i, 2));
            red.setElMT(i, img.getWidth() + 2, red.getElmt(i, img.getWidth() + 1));
            green.setElMT(i, img.getWidth() + 2, green.getElmt(i, img.getWidth() + 1));
            blue.setElMT(i, img.getWidth() + 2, blue.getElmt(i, img.getWidth() + 1));
            red.setElMT(i, img.getWidth() + 3, red.getElmt(i, img.getWidth() + 1));
            green.setElMT(i, img.getWidth() + 3, green.getElmt(i, img.getWidth() + 1));
            blue.setElMT(i, img.getWidth() + 3, blue.getElmt(i, img.getWidth() + 1));
        }

        for (int j = 2; j < img.getWidth() + 2; j++) {
            red.setElMT(0, j, red.getElmt(2, j));
            green.setElMT(0, j, green.getElmt(2, j));
            blue.setElMT(0, j, blue.getElmt(2, j));
            red.setElMT(1, j, red.getElmt(2, j));
            green.setElMT(1, j, green.getElmt(2, j));
            blue.setElMT(1, j, blue.getElmt(2, j));
            red.setElMT(img.getHeight() + 2, j, red.getElmt(img.getHeight() + 1, j));
            green.setElMT(img.getHeight() + 2, j, green.getElmt(img.getHeight() + 1, j));
            blue.setElMT(img.getHeight() + 2, j, blue.getElmt(img.getHeight() + 1, j));
            red.setElMT(img.getHeight() + 3, j, red.getElmt(img.getHeight() + 1, j));
            green.setElMT(img.getHeight() + 3, j, green.getElmt(img.getHeight() + 1, j));
            blue.setElMT(img.getHeight() + 3, j, blue.getElmt(img.getHeight() + 1, j));
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                red.setElMT(i, j, red.getElmt(2, 2));
                green.setElMT(i, j, green.getElmt(2, 2));
                blue.setElMT(i, j, blue.getElmt(2, 2));
            }
        }

        for (int i = img.getHeight() + 2; i < img.getHeight() + 4; i++) {
            for (int j = 0; j < 2; j++) {
                red.setElMT(i, j, red.getElmt(img.getHeight() + 1, 2));
                green.setElMT(i, j, green.getElmt(img.getHeight() + 1, 2));
                blue.setElMT(i, j, blue.getElmt(img.getHeight() + 1, 2));
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = img.getWidth() + 2; j < img.getWidth() + 4; j++) {
                red.setElMT(i, j, red.getElmt(2, img.getWidth() + 1));
                green.setElMT(i, j, green.getElmt(2, img.getWidth() + 1));
                blue.setElMT(i, j, blue.getElmt(2, img.getWidth() + 1));
            }
        }

        for (int i = img.getHeight() + 2; i < img.getHeight() + 4; i++) {
            for (int j = img.getWidth() + 2; j < img.getWidth() + 4; j++) {
                red.setElMT(i, j, red.getElmt(img.getHeight() + 1, img.getWidth() + 1));
                green.setElMT(i, j, green.getElmt(img.getHeight() + 1, img.getWidth() + 1));
                blue.setElMT(i, j, blue.getElmt(img.getHeight() + 1, img.getWidth() + 1));
            }
        }
        for (int i = 1; i <= img.getHeight() + 1; i++) {
            for (int j = 1; j <= img.getWidth() + 1; j++) {
                int idx = 0;
                for (int y = j - 1; y < j + 3; y++) {
                    for (int x = i - 1; x < i + 3; x++) {
                        tempRed.setElMT(idx, 0, red.getElmt(x, y));
                        tempGreen.setElMT(idx, 0, green.getElmt(x, y));
                        tempBlue.setElMT(idx, 0, blue.getElmt(x, y));
                        idx++;
                    }
                }
                redRes[i - 1][j - 1] = Matrix.multiplyMatrix(multiplier, tempRed);
                greenRes[i - 1][j - 1] = Matrix.multiplyMatrix(multiplier, tempGreen);
                blueRes[i - 1][j - 1] = Matrix.multiplyMatrix(multiplier, tempBlue);
            }
        }
        System.out.print("Masukkan perbesaran gambar: ");
        double k = IO.inputScanner.nextDouble();

        long newHeight = Math.round(k * img.getHeight());
        long newWidth = Math.round(k * img.getWidth());
        BufferedImage newImage = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {

                double conX = ((double) img.getHeight() / (double) newHeight) * ((double) (2 * i + 1) / (double) 2)
                        + 1.5;
                double conY = ((double) img.getWidth() / (double) newWidth) * ((double) (2 * j + 1) / (double) 2) + 1.5;
                int x = (int) Math.floor(conX) - 1;
                conX -= Math.floor(conX);
                int y = (int) Math.floor(conY) - 1;
                conY -= Math.floor(conY);
                double r = calcRGB(redRes[x][y], conX, conY);
                double g = calcRGB(greenRes[x][y], conX, conY);
                double b = calcRGB(blueRes[x][y], conX, conY);
                int rgb = 0;
                if (r >= 0 && r <= 255)
                    rgb += (int) Math.round(r);
                else if (r > 255)
                    rgb += 255;
                rgb <<= 8;
                if (g >= 0 && g <= 255)
                    rgb += (int) Math.round(g);
                else if (g > 255) {
                    rgb += 255;
                }
                rgb <<= 8;
                if (b >= 0 && b <= 255)
                    rgb += (int) Math.round(b);
                else if (b > 255) {
                    rgb += 255;
                }
                newImage.setRGB(j, i, rgb);
            }
        }
        
        while (true) {
            try {
                System.out.print("Masukkan nama file keluaran: ");
                if (IO.inputScanner.hasNextLine()) {
                    IO.inputScanner.nextLine();
                }
    
                String outputFileName = IO.inputScanner.nextLine();
                File outputImg = new File("test/output/" + outputFileName);
                ImageIO.write(newImage, "png", outputImg);
                System.out.println("\nGambar berhasil dibuat di directory test/output.");
                break;
            } catch (Exception err) {
                System.out.println("Gagal membuat gambar.");
            }
        }
    }

    public static double calcRGB(Matrix mult, double x, double y) {
        double res = 0;
        int idx = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                res += (mult.getElmt(idx, 0) * Math.pow(x, i) * Math.pow(y, j));
                idx++;
            }
        }
        return res;
    }

    public static void constructD(Matrix D) {
        int row = 0, col = 0;
        // f(x,y)
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                col = 0;
                for (int j = -1; j < 3; j++) {
                    for (int i = -1; i < 3; i++) {
                        if (x == i && y == j)
                            D.setElMT(row, col, 4);
                        else
                            D.setElMT(row, col, 0);
                        col++;
                    }
                }
                row++;
            }
        }
        // fx(x, y)
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                col = 0;
                for (int j = -1; j < 3; j++) {
                    for (int i = -1; i < 3; i++) {
                        if (x + 1 == i && y == j)
                            D.setElMT(row, col, 2);
                        else if (x - 1 == i && y == j)
                            D.setElMT(row, col, -2);
                        else
                            D.setElMT(row, col, 0);
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
                for (int j = -1; j < 3; j++) {
                    for (int i = -1; i < 3; i++) {
                        if (x == i && y + 1 == j)
                            D.setElMT(row, col, 2);
                        else if (x == i && y - 1 == j)
                            D.setElMT(row, col, -2);
                        else
                            D.setElMT(row, col, 0);
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
                for (int j = -1; j < 3; j++) {
                    for (int i = -1; i < 3; i++) {
                        if ((x + 1 == i && y + 1 == j) || (x == i && y == j))
                            D.setElMT(row, col, 1);
                        else if ((x - 1 == i && y == j) || (x == i && y - 1 == j))
                            D.setElMT(row, col, -1);
                        else
                            D.setElMT(row, col, 0);
                        col++;
                    }
                }
                row++;
            }
        }
    }
}
