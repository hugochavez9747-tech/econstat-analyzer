import java.util.Scanner;

public class EconStatAnalyzer {

    
    static final int    MAX_SIZE         = 100;
    static final double STABLE_RATIO     = 0.20;
    static final double VOLATILE_RATIO   = 0.75;
    static final double ANOMALY_THRESH   = 2.0;

    static Scanner scanner = new Scanner(System.in);
    static int count = 0;

    
    public static void main(String[] args) {

        displayMenu();
        double[] data = getIndicatorData();

        int choice = -1;

        
        while (choice != 0) {
            System.out.println("\n─────────────────────────────────────");
            System.out.println("  1 - Mean");
            System.out.println("  2 - Median");
            System.out.println("  3 - Mode");
            System.out.println("  4 - Range");
            System.out.println("  5 - Full Report");
            System.out.println("  0 - Quit");
            System.out.print("Choose an option: ");

          
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid. Enter a number (0-5): ");
                scanner.next();
            }
            choice = scanner.nextInt();

          
            if (choice == 1) {
                System.out.printf("Mean:   %.2f%%%n", calcMean(data));
            } else if (choice == 2) {
                System.out.printf("Median: %.2f%%%n", calcMedian(data));
            } else if (choice == 3) {
                double mode = calcMode(data);
                if (mode == -1) {
                    System.out.println("Mode: No mode detected");
                } else {
                    System.out.printf("Mode: %.2f%%%n", mode);
                }
            } else if (choice == 4) {
                System.out.printf("Range:  %.2f%%%n", calcRange(data));
            } else if (choice == 5) {
                displayReport(data);
            } else if (choice != 0) {
                System.out.println("Invalid choice. Please enter 0 through 5.");
            }
        }

        System.out.println("\nThank you for using EconStat Analyzer.");
        scanner.close();
    }

    
    public static void displayMenu() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        EconStat Analyzer             ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("Select an indicator to analyze:");
        System.out.println("  1. GDP Growth Rate (%)");
        System.out.println("  2. Inflation Rate (%)");
        System.out.println("  3. Unemployment Rate (%)");
        System.out.print("Your choice: ");

        while (!scanner.hasNextInt()) {
            System.out.print("Invalid. Enter 1, 2, or 3: ");
            scanner.next();
        }

        int indicatorChoice = scanner.nextInt();

        if (indicatorChoice == 1) {
            System.out.println("\nAnalyzing: GDP Growth Rate (%)");
        } else if (indicatorChoice == 2) {
            System.out.println("\nAnalyzing: Inflation Rate (%)");
        } else if (indicatorChoice == 3) {
            System.out.println("\nAnalyzing: Unemployment Rate (%)");
        } else {
            System.out.println("\nAnalyzing: Custom Economic Indicator (%)");
        }
    }

    public static double[] getIndicatorData() {
        double[] data = new double[MAX_SIZE];
        String inputStr = "";
        int index = 0;

        System.out.println("\nEnter annual values one at a time.");
        System.out.println("Type 'done' when finished.\n");

        while (!inputStr.equalsIgnoreCase("done") && index < MAX_SIZE) {
            System.out.print("Enter value (or 'done'): ");
            inputStr = scanner.next();

            while (!inputStr.equalsIgnoreCase("done") && !isNumeric(inputStr)) {
                System.out.print("Invalid. Enter a numeric value or 'done': ");
                inputStr = scanner.next();
            }

            if (!inputStr.equalsIgnoreCase("done")) {
                data[index] = Double.parseDouble(inputStr);  
                index++;
            }
        }

        count = index;

        if (count == 0) {
            System.out.println("No data entered. Exiting.");
            System.exit(0);
        }

        System.out.println(count + " value(s) recorded.\n");
        return data;
    }

    public static double calcMean(double[] data) {
        double totalSum = 0.0;

        
        for (int i = 0; i < count; i++) {
            totalSum = totalSum + data[i];   
        }

        return totalSum / count;
    }

    
    public static double[] sortArray(double[] data) {
        double[] sorted = new double[count];
        for (int i = 0; i < count; i++) {
            sorted[i] = data[i];
        }

        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - 1 - i; j++) {
                if (sorted[j] > sorted[j + 1]) {
                    double temp    = sorted[j];
                    sorted[j]      = sorted[j + 1];
                    sorted[j + 1]  = temp;
                }
            }
        }

        return sorted;
    }

    public static double calcMedian(double[] data) {
        double[] sorted = sortArray(data);
        int mid = count / 2;

        if (count % 2 == 0) {
            return (sorted[mid - 1] + sorted[mid]) / 2.0;
        } else {
            return sorted[mid];
        }
    }

    public static double calcMode(double[] data) {
        int    maxCount  = 0;
        double modeValue = -1;

        for (int i = 0; i < count; i++) {
            int frequency = 0;

            for (int j = 0; j < count; j++) {
                if (data[j] == data[i]) {
                    frequency++;
                }
            }

            if (frequency > 1 && frequency > maxCount) {
                maxCount  = frequency;
                modeValue = data[i];
            }
        }

        return modeValue;
    }

    public static double calcRange(double[] data) {
        double minVal = data[0];
        double maxVal = data[0];

        for (int i = 1; i < count; i++) {
            if (data[i] < minVal) { minVal = data[i]; }
            if (data[i] > maxVal) { maxVal = data[i]; }
        }

        return maxVal - minVal;
    }

    public static String classifyVolatility(double range, double mean) {
        if (range < mean * STABLE_RATIO) {
            return "Stable";
        } else if (range > mean * VOLATILE_RATIO) {
            return "Volatile";
        } else {
            return "Moderate";
        }
    }

    public static void flagAnomalies(double[] data, double mean) {
        boolean foundAny = false;

        for (int i = 0; i < count; i++) {
            if (data[i] > mean * ANOMALY_THRESH) {
                System.out.printf("  %.2f%% flagged as economic anomaly%n", data[i]);
                foundAny = true;
            }
        }

        if (!foundAny) {
            System.out.println("  None detected");
        }
    }

    public static void displayReport(double[] data) {
        double mean       = calcMean(data);
        double median     = calcMedian(data);
        double mode       = calcMode(data);
        double range      = calcRange(data);
        String volatility = classifyVolatility(range, mean);

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║      Economic Summary Report         ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.printf("  Years Entered : %d%n",    count);
        System.out.printf("  Mean          : %.2f%%%n", mean);
        System.out.printf("  Median        : %.2f%%%n", median);

        if (mode == -1) {
            System.out.println("  Mode          : No mode detected");
        } else {
            System.out.printf("  Mode          : %.2f%%%n", mode);
        }

        System.out.printf("  Range         : %.2f%%%n", range);
        System.out.printf("  Volatility    : %s%n",     volatility);
        System.out.println("  Anomalies:");
        flagAnomalies(data, mean);
        System.out.println("──────────────────────────────────────");
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

