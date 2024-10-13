package lib;

import java.util.*;
import java.io.*;

public class IO {
    private String fileName;
    private Scanner fileScanner;
    private static final Scanner inputScanner = new Scanner(System.in); // General input scanner
    
    // Constructor
    public IO(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    // Open & Close File
    public void openFile() {
        try {
            File file = new File(getFileName());
            this.fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(">> File not found: " + getFileName());
            System.exit(0);
        }
    }

    public void closeFile() {
        if (fileScanner != null) {
            fileScanner.close();
        }
    }

    // Read file name from input
    public static String promptFileName() {
        String fileName = "";
        while (true) {
            try {
                System.out.print(">> Enter file name: ");
                fileName = inputScanner.nextLine();
                File file = new File("test/input/" + fileName);
                Scanner tempScanner = new Scanner(file); // Check if file exists
                tempScanner.close(); // Close immediately after validation
                break;
            } catch (FileNotFoundException e) {
                System.out.println(">> File not found: " + fileName);
            }
        }
        return "test/input/" + fileName;
    }

    // Count number of rows in the file
    public int getRowCount() {
        int rowCount = 0;
        openFile();
        while (fileScanner.hasNextLine()) {
            fileScanner.nextLine();
            rowCount++;
        }
        closeFile();
        return rowCount;
    }

    // Count number of columns in the first row
    public int getColCount() {
        int colCount = 0;
        openFile();
        if (fileScanner.hasNextLine()) {
            Scanner colScanner = new Scanner(fileScanner.nextLine());
            while (colScanner.hasNextDouble()) {
                colCount++;
                colScanner.nextDouble();
            }
            colScanner.close();
        }
        closeFile();
        return colCount;
    }

    // Read matrix from file
    public Matrix readMatrixFromFile() {
        int row = getRowCount();
        int col = getColCount();
        Matrix matrix = new Matrix(row, col);

        openFile();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (fileScanner.hasNextDouble()) {
                    matrix.setElMT(i, j, fileScanner.nextDouble());
                }
            }
        }
        closeFile();
        return matrix;
    }

    // Read points from file 
    public Matrix readPointsFromFile() {
        int row = getRowCount();
        Matrix points = new Matrix(row, 2);

        openFile();
        for (int i = 0; i < row - 1; i++) {
            for (int j = 0; j < 2; j++) {
                if (fileScanner.hasNextDouble()) {
                    points.setElMT(i, j, fileScanner.nextDouble());
                }
            }
        }
        // Handle the last element separately
        if (fileScanner.hasNextDouble()) {
            points.setElMT(row - 1, 0, fileScanner.nextDouble());
        }
        closeFile();
        return points;
    }

    // Read bicubic spline data
    public double[] readBicubicSplineData() {
        double[] splineData = new double[18];

        openFile();
        for (int i = 0; i < 18; i++) {
            if (fileScanner.hasNextDouble()) {
                splineData[i] = fileScanner.nextDouble();
            } else {
                System.out.println(">> Invalid data format encountered at index " + i);
                break;
            }
        }
        closeFile();
        return splineData;
    }

    // Write string array to file
    public void writeStringArrayToFile(String[] content) {
        try {
            System.out.print(">> Enter output file name: ");
            String outputFileName = inputScanner.nextLine();
            File outputFile = new File("test/output/" + outputFileName);

            // Ensure file creation
            if (outputFile.createNewFile() || outputFile.exists()) {
                FileWriter writer = new FileWriter(outputFile);
                for (int i = 0; i < content.length; i++) {
                    writer.write(content[i]);
                    if (i != content.length - 1) {
                        writer.write("\n");
                    }
                }
                writer.close();
                System.out.println(">> Output written to " + outputFileName);
            }
        } catch (IOException e) {
            System.out.println(">> Error writing to file.");
        }
    }
}
