import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        loadingUI();
    }

    public static void loadingUI() throws InterruptedException, IOException {
        int progressBarLength = 30; 
        int totalSteps = 100;

        System.out.println();
        System.out.print("Loading: [");

        for (int step = 1; step <= totalSteps; step++) {
            int completedLength = (step * progressBarLength) / totalSteps;

            System.out.print("\rLoading: [");
            for (int i = 0; i < progressBarLength; i++) {
                if (i < completedLength) {
                    System.out.print("#"); 
                } else {
                    System.out.print("-");
                }
            }
            System.out.print("] " + step + "%");
            Thread.sleep(50);
        }
        System.out.println("\n");
    }

}
