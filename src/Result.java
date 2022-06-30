import java.util.ArrayList;

public class Result {
    private final ArrayList<Double> variables;
    private final int count;
    private final ArrayList<Double> errors;


    public Result(ArrayList<Double> variables, int count, ArrayList<Double> errors) {
        this.variables = variables;
        this.count = count;
        this.errors = errors;
    }

    public ArrayList<Double> getVariables() {
        return variables;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<Double> getErrors() {
        return errors;
    }
}
