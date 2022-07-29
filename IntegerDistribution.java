import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Random;

public class IntegerDistribution {
    private double[] values;

    private double[] cumulativeProbabilities;

    private double[] probabilities;

    private Random random;

    public IntegerDistribution(String distribution) {
        random = new Random();

        String[] valueWithProbabilities = distribution.split(",");

        int size = valueWithProbabilities.length;
        values = new double[size];
        cumulativeProbabilities = new double[size];
        probabilities = new double[size];

        for (int i = 0; i < valueWithProbabilities.length; i++) {
            String[] arr = valueWithProbabilities[i].split("=");
            values[i] = Double.parseDouble(arr[1]);
            probabilities[i] = Double.parseDouble(arr[0]);
        }

        sortArrayBaseOnAnother(values, probabilities);

        cumulativeProbabilities = new double[probabilities.length];
        double sum = 0;
        for (int i = 0; i < probabilities.length; i++) {
            sum += probabilities[i];
            cumulativeProbabilities[i] = sum;
        }
    }

    private void sortArrayBaseOnAnother(double[] A1, double[] A2) {
        final double[][] res = new double[A1.length][2];
        for (int i = 0; i < res.length; i++) {
            res[i] = new double[]{A1[i], A2[i]};
        }
        Arrays.sort(res, (a, b) -> Double.compare(a[1], b[1]));
        for (int i = 0; i < res.length; i++) {
            A1[i] = res[i][0];
            A2[i] = res[i][1];
        }
    }

    public Double getRandom() {
        final double randomValue = random.nextDouble();

        int index = Arrays.binarySearch(cumulativeProbabilities, randomValue);
        if (index < 0) {
            index = -index - 1;
        }

        if (index >= 0 &&
                index < probabilities.length &&
                randomValue < cumulativeProbabilities[index]) {
            return values[index];
        }

        return values[values.length - 1];
    }

    public static void main(String[] args) {
        IntegerDistribution integerDistribution = new IntegerDistribution("0.97=1000,0.01=5000,0.01=6000,0.01=8000");
        int a1000 = 0;
        int a5000 = 0;
        int a6000 = 0;
        int a8000 = 0;

        for (int i = 0; i < 1000; i++) {
            if (integerDistribution.getRandom() == 1000) a1000++;
            if (integerDistribution.getRandom() == 5000) a5000++;
            if (integerDistribution.getRandom() == 6000) a6000++;
            if (integerDistribution.getRandom() == 8000) a8000++;
        }


        System.out.println("a1000=" + a1000);
        System.out.println("a5000=" + a5000);
        System.out.println("a6000=" + a6000);
        System.out.println("a8000=" + a8000);


    }
}
