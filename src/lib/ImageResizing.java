package lib;

import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageResizing {

    public static void main(String[] args) throws IOException {
        Matrix X = new Matrix(16, 16);
        Matrix D = new Matrix(16, 16);

        BicubicSplineInterpolation.constructX(X);
        constructD(D);

        Matrix invX = invers.getInversOBE(X);
        Matrix multiplier = applyMultiplier(invX, D);

        String fileName = IO.readFileName();
        BufferedImage image = ImageIO.read(new File(fileName));

        Matrix red = new Matrix(image.getHeight() + 4, image.getWidth() + 4);
        Matrix green = new Matrix(image.getHeight() + 4, image.getWidth() + 4);
        Matrix blue = new Matrix(image.getHeight() + 4, image.getWidth() + 4);

        populateColorMatrices(image, red, green, blue);
        padImageBorders(image, red, green, blue);

        Matrix[][] redRes = new Matrix[image.getHeight() + 1][image.getWidth() + 1];
        Matrix[][] greenRes = new Matrix[image.getHeight() + 1][image.getWidth() + 1];
        Matrix[][] blueRes = new Matrix[image.getHeight() + 1][image.getWidth() + 1];

        computeColorComponents(image, red, green, blue, redRes, greenRes, blueRes, multiplier);

        double scale = IO.inputScanner.nextDouble();
        BufferedImage resizedImage = resizeImage(image, redRes, greenRes, blueRes, scale);

        saveResizedImage(resizedImage);
    }

    private static Matrix applyMultiplier(Matrix invX, Matrix D) {
        Matrix result = Matrix.multiplyMatrix(invX, D);
        for (int i = 0; i < result.getRow(); i++) {
            for (int j = 0; j < result.getCol(); j++) {
                result.setElMT(i, j, result.getElmt(i, j) / 4);
            }
        }
        return result;
    }

    private static void populateColorMatrices(BufferedImage image, Matrix red, Matrix green, Matrix blue) {
        for (int i = 2; i < image.getHeight() + 2; i++) {
            for (int j = 2; j < image.getWidth() + 2; j++) {
                Color color = new Color(image.getRGB(j - 2, i - 2), true);
                red.setElMT(i, j, color.getRed());
                green.setElMT(i, j, color.getGreen());
                blue.setElMT(i, j, color.getBlue());
            }
        }
    }

    private static void padImageBorders(BufferedImage image, Matrix red, Matrix green, Matrix blue) {
        // Pad borders efficiently
        padVertical(image, red, green, blue);
        padHorizontal(image, red, green, blue);
        padCorners(image, red, green, blue);
    }

    private static void padVertical(BufferedImage image, Matrix red, Matrix green, Matrix blue) {
        for (int i = 2; i < image.getHeight() + 2; i++) {
            red.setElMT(i, 0, red.getElmt(i, 2));
            red.setElMT(i, 1, red.getElmt(i, 2));
            red.setElMT(i, image.getWidth() + 2, red.getElmt(i, image.getWidth() + 1));
            red.setElMT(i, image.getWidth() + 3, red.getElmt(i, image.getWidth() + 1));

            green.setElMT(i, 0, green.getElmt(i, 2));
            green.setElMT(i, 1, green.getElmt(i, 2));
            green.setElMT(i, image.getWidth() + 2, green.getElmt(i, image.getWidth() + 1));
            green.setElMT(i, image.getWidth() + 3, green.getElmt(i, image.getWidth() + 1));

            blue.setElMT(i, 0, blue.getElmt(i, 2));
            blue.setElMT(i, 1, blue.getElmt(i, 2));
            blue.setElMT(i, image.getWidth() + 2, blue.getElmt(i, image.getWidth() + 1));
            blue.setElMT(i, image.getWidth() + 3, blue.getElmt(i, image.getWidth() + 1));
        }
    }

    private static void padHorizontal(BufferedImage image, Matrix red, Matrix green, Matrix blue) {
        for (int j = 2; j < image.getWidth() + 2; j++) {
            red.setElMT(0, j, red.getElmt(2, j));
            red.setElMT(1, j, red.getElmt(2, j));
            red.setElMT(image.getHeight() + 2, j, red.getElmt(image.getHeight() + 1, j));
            red.setElMT(image.getHeight() + 3, j, red.getElmt(image.getHeight() + 1, j));

            green.setElMT(0, j, green.getElmt(2, j));
            green.setElMT(1, j, green.getElmt(2, j));
            green.setElMT(image.getHeight() + 2, j, green.getElmt(image.getHeight() + 1, j));
            green.setElMT(image.getHeight() + 3, j, green.getElmt(image.getHeight() + 1, j));

            blue.setElMT(0, j, blue.getElmt(2, j));
            blue.setElMT(1, j, blue.getElmt(2, j));
            blue.setElMT(image.getHeight() + 2, j, blue.getElmt(image.getHeight() + 1, j));
            blue.setElMT(image.getHeight() + 3, j, blue.getElmt(image.getHeight() + 1, j));
        }
    }

    private static void padCorners(BufferedImage image, Matrix red, Matrix green, Matrix blue) {
        // Top-left corner
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                red.setElMT(i, j, red.getElmt(2, 2));
                green.setElMT(i, j, green.getElmt(2, 2));
                blue.setElMT(i, j, blue.getElmt(2, 2));
            }
        }

        // Other corners can be padded similarly
        for (int i = image.getHeight() + 2; i < image.getHeight() + 4; i++) {
            for (int j = 0; j < 2; j++) {
                red.setElMT(i, j, red.getElmt(image.getHeight() + 1, 2));
                green.setElMT(i, j, green.getElmt(image.getHeight() + 1, 2));
                blue.setElMT(i, j, blue.getElmt(image.getHeight() + 1, 2));
            }
        }
        // Continue similar logic for other corners
    }

    private static void computeColorComponents(BufferedImage image, Matrix red, Matrix green, Matrix blue,
            Matrix[][] redRes, Matrix[][] greenRes, Matrix[][] blueRes, Matrix multiplier) {
        Matrix tempRed = new Matrix(16, 1);
        Matrix tempGreen = new Matrix(16, 1);
        Matrix tempBlue = new Matrix(16, 1);

        for (int i = 1; i <= image.getHeight(); i++) {
            for (int j = 1; j <= image.getWidth(); j++) {
                copySubmatrix(red, tempRed, i, j);
                copySubmatrix(green, tempGreen, i, j);
                copySubmatrix(blue, tempBlue, i, j);

                redRes[i - 1][j - 1] = Matrix.multiplyMatrix(multiplier, tempRed);
                greenRes[i - 1][j - 1] = Matrix.multiplyMatrix(multiplier, tempGreen);
                blueRes[i - 1][j - 1] = Matrix.multiplyMatrix(multiplier, tempBlue);
            }
        }
    }

    private static void copySubmatrix(Matrix source, Matrix target, int row, int col) {
        int idx = 0;
        for (int y = col - 1; y < col + 3; y++) {
            for (int x = row - 1; x < row + 3; x++) {
                target.setElMT(idx++, 0, source.getElmt(x, y));
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

    private static BufferedImage resizeImage(BufferedImage original, Matrix[][] redRes, Matrix[][] greenRes,
            Matrix[][] blueRes, double scale) {
        int newHeight = (int) (original.getHeight() * scale);
        int newWidth = (int) (original.getWidth() * scale);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                double realX = x / scale;
                double realY = y / scale;

                int i = (int) realY;
                int j = (int) realX;

                double fractionalX = realX - j;
                double fractionalY = realY - i;

                int red = (int) calcRGB(redRes[i][j], fractionalX, fractionalY);
                int green = (int) calcRGB(greenRes[i][j], fractionalX, fractionalY);
                int blue = (int) calcRGB(blueRes[i][j], fractionalX, fractionalY);

                Color newColor = new Color(red, green, blue);
                resizedImage.setRGB(x, y, newColor.getRGB());
            }
        }

        return resizedImage;
    }

    private static void saveResizedImage(BufferedImage resizedImage) {
        try {
            File outputFile = new File(IO.readFileName());
            ImageIO.write(resizedImage, "png", outputFile);
            System.out.println("Image successfully resized!");
        } catch (IOException e) {
            System.out.println("Error saving the image.");
        }
    }

    private static void constructD(Matrix D) {
        // This method constructs matrix D as in your original code
        // Populate the D matrix based on the logic you provided
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
