import java.util.ArrayList;

public class ElementsGenerator {

    public static ArrayList<ArrayList<Double>> generate(int size) {
        ArrayList<ArrayList<Double>> elements = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            elements.add(new ArrayList<>());
            double rowSum = 0;
            for (int j = 0; j < size + 1; j++) {
                elements.get(i).add(generateElement());
                rowSum += Math.abs(elements.get(i).get(j));
            }
            elements.get(i).set(i, Math.round(rowSum * 100) / 100.0);
        }
        return elements;
    }

    private static double generateElement() {
        return Math.round((2 * Math.random() * 1000 - Math.random() * 1000) * 100.0) / 100.0;
    }
}
