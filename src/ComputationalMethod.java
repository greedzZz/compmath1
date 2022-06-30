import java.util.ArrayList;

public class ComputationalMethod {
    private static boolean diagonalDominance(Matrix matrix) {
        int size = matrix.getSize();
        ArrayList<ArrayList<Double>> elements = matrix.getElements();
        int[] maxIndexes = new int[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.abs(elements.get(i).get(j)) > Math.abs(elements.get(i).get(maxIndexes[i]))) maxIndexes[i] = j;
            }
        }
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (maxIndexes[i] == maxIndexes[j]) return false;
            }
        }
        boolean strictCondition = false;
        for (int i = 0; i < size; i++) {
            double rowSum = 0;
            for (int j = 0; j < size; j++) {
                if (j != maxIndexes[i]) rowSum += Math.abs(elements.get(i).get(j));
            }
            if (Math.abs(elements.get(i).get(maxIndexes[i])) >= rowSum) {
                if (Math.abs(elements.get(i).get(maxIndexes[i])) > rowSum) {
                    strictCondition = true;
                }
            } else return false;
        }
        if (strictCondition) {
            for (int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size; j++) {
                    if (maxIndexes[i] > maxIndexes[j]) {
                        int tmpIndex = maxIndexes[j];
                        maxIndexes[j] = maxIndexes[i];
                        maxIndexes[i] = tmpIndex;
                        ArrayList<Double> tmpArray = elements.get(j);
                        elements.set(j, elements.get(i));
                        elements.set(i, tmpArray);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static Result compute(Matrix matrix, double accuracy) {
        if (!diagonalDominance(matrix)) return null;
        int size = matrix.getSize();
        ArrayList<ArrayList<Double>> elements = matrix.getElements();
        ArrayList<Double> variables = new ArrayList<>(size);
        int count = 0;
        ArrayList<Double> errors = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            variables.add(0.0);
        }
        ArrayList<Double> previousVariables;
        boolean computed;
        do {
            count++;
            previousVariables = new ArrayList<>(variables);
            for (int i = 0; i < size; i++) {
                double sum = 0;
                for (int j = 0; j < size; j++) {
                    if (i != j) sum += elements.get(i).get(j) * variables.get(j);
                }
                variables.set(i, 1 / elements.get(i).get(i) * (elements.get(i).get(size) - sum));
            }
            computed = true;
            for (int i = 0; i < size; i++) {
                if (accuracy < Math.abs(variables.get(i) - previousVariables.get(i))) {
                    computed = false;
                    break;
                }
            }
        } while (!computed);
        for (int i = 0; i < size; i++) {
            errors.add(variables.get(i) - previousVariables.get(i));
        }
        return new Result(variables, count, errors);
    }
}