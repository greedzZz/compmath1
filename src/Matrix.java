import java.util.ArrayList;

public class Matrix {
    private final int size;
    private final ArrayList<ArrayList<Double>> elements;

    public Matrix(int size, ArrayList<ArrayList<Double>> elements) {
        this.size = size;
        this.elements = elements;
    }

    public int getSize() {
        return this.size;
    }

    public ArrayList<ArrayList<Double>> getElements() {
        return this.elements;
    }
}
