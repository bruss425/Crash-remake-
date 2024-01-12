import java.util.Random;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class crashGame {

    public static void main(String[] args) {
        // eventually will replace with user imput 
        double userBet = 100.0;// example for testing 
         System.out.println(bettingTest(userBet, 1.5, 500));

        // testing 100 dollar unit, 1.2 cash out and 100 test
        // user was, +440, -160, -760, - 640, +80 
        
        // testing 100 dollar unit, 1.5 cash out and 100 test 
        // user was, -700, -1000, +350, -2650, -2800

        // testing 100 dollar unit, 5 cash out, 100 test
        // user was, -500, -7000, -1000, -1500, + 4000

        // testing 100 dollar unit, 19 cash out, 100 test
        // user was, -4300, -2400, -2400, -4300, 1400

         // testing 100 dollar unit, 200 cash out, 100 test
        // user was, +10000, -10000, -10000, +10000, +10000

         // testing 100 dollar unit, 500 cash out, 500 test
        // user was: -50,000

         // testing 100 dollar unit, 2 cash out, 500 test
        // user was: -10,800

        // ^^^ this is before I changed the odds 
        // odds are in line with those numbers 






       // find average of 10 medians at 50 tests per median 
       // gives me 500 samples    
       // System.out.println(averageTest(50, 10));
      
       
      
    }

    // Method to generate a random multiplier based on specified probabilities
    public static double generateRandomMultiplier() {
        // Create an instance of the Random class
        // helps create new random seed 
        Random random = new Random(System.currentTimeMillis());

        // Generate a random number between 0 and 1
        double randomNumber = random.nextDouble();

        // Define probability ranges and corresponding multipliers
        double[][] probabilityRanges = {
                {1.00, 1.04, 0.047},    // Range 1.00 to 1.04
                {1.05, 1.15, 0.069},    // Range 1.05 to 1.15
                {1.16, 1.25, 0.066},    // Range 1.16 to 1.25
                {1.26, 1.35, 0.086},    // Range 1.26 to 1.35
                {1.36, 1.45, 0.065},    // Range 1.36 to 1.45
                {1.46, 1.55, 0.064},    // Range 1.46 to 1.55
                {1.56, 1.65, 0.05},     // Range 1.56 to 1.65
                {1.66, 1.75, 0.052},    // Range 1.66 to 1.75
                {1.76, 1.85, 0.046},    // Range 1.76 to 1.85
                {1.86, 1.95, 0.032},    // Range 1.86 to 1.95
                {1.96, 2.05, 0.037},    // Range 1.96 to 2.05
                {2.06, 2.15, 0.041},    // Range 2.06 to 2.15
                {2.16, 2.25, 0.037},    // Range 2.16 to 2.25
                {2.26, 2.35, 0.037},    // Range 2.26 to 2.35
                {2.36, 2.45, 0.032},    // Range 2.36 to 2.45
                {2.46, 2.55, 0.035},    // Range 2.46 to 2.55
                {2.56, 2.65, 0.027},    // Range 2.56 to 2.65
                {2.66, 2.75, 0.043},    // Range 2.66 to 2.75
                {2.75, 10.0, 0.06},    // Range 2.75 to 10.0
                {10.0, 20.0, 0.041},    // Range 10.0 to 20.0
                {20.0, 50.0, 0.025},     // Range 20.0 to 50.0
                {50.0, 200.0, 0.0062},   // Range 50.0 to 200.0
                {200.0, 500.0, 0.001},  // Range 200.0 to 500.0
                {500.0, 1000.0, 0.0008}  // Range 500.0 to 1000.0
        };

        // Calculate the cumulative probability and find the corresponding range
        double cumulativeProbability = 0;
        for (double[] range : probabilityRanges) {
            cumulativeProbability += range[2];
            if (randomNumber <= cumulativeProbability) {
                // Map the random number within the range to a random multiplier
                double lowerBound = range[0];
                double upperBound = range[1];
                // create number
                double value = random.nextDouble() * (upperBound - lowerBound) + lowerBound;
                // rounding
                value = round(value, 2);
                return value;
            }
        }

        // If no specific range is matched, return 1.0 as a default multiplier
        return 1.0;
    }

    // from stackoverflow.com
    // https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    private static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
    }



    // make a test to find the median multiplier of a certain sample range to see where my math leads 
    public static double medianTest (int sampleSize){
        double numArr[] = new double[sampleSize];
        for(int i = 0; i < sampleSize; i++){
            try {
                Thread.sleep(361);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            double multiplier = generateRandomMultiplier();
            numArr[i] = multiplier;
        }

        // sort the numbers into least to greatest 
        Arrays.sort(numArr);

        // now find the median (middle term)
        double median = numArr[sampleSize / 2];
       //  System.out.println(Arrays.toString(numArr));


        return median;
    }
    // method for taking the median of a set of runs and then averaging them out 
    public static double averageTest(int sampleSize, int numTests){
        double average = 0;
        double sum = 0;
        for(int i = 0; i < numTests; i++){
            sum += medianTest(sampleSize);
        }
        average = sum / numTests;
        return average; 
    }
    // method for seeing where 
    public static double bettingTest(double unitSize, double cashoOutValue, int numTests){
        double dollars = 0;
        // example $100 x 1.5 = $150
        double winAmount = (unitSize * cashoOutValue) - unitSize;
        for(int i = 0; i < numTests; i++){
            try {
                Thread.sleep(271);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            double multiplier = generateRandomMultiplier();
            System.out.println("multiplier = " + multiplier);
            System.out.println("Current value = $" + dollars);

            // if our multiplier is more than our cash out then add our win amount to totoal 
            if(multiplier >= cashoOutValue){
                dollars += winAmount;
            }
            // else take away our unit size 
            else{
                dollars -= unitSize;
            }
        }
        

        return dollars; 
    }
}
