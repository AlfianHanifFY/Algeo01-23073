package lib;

public class BicubicSplineInterpolation {
    
    public static void main(String[] args) {
        // Variable declaration
        String fileName = IO.promptFileName();
        IO f = new IO(fileName);
        double[] temp = f.readBicubicSplineData();
        
        // Print each element in the double array
        System.out.println("Bicubic Spline Data:");
        for (double value : temp) {
            System.out.print(value);
            System.out.print(" ");
        }
    }
}