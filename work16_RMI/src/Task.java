import java.io.Serializable;

public class Task implements Serializable {

    enum Operation {SUM, SUB, MUL, DIV}

    private double a;
    private double b;
    private final Operation operation;
    private double result;

    public Task(double a, double b, Operation operation) {
        this.a = a;
        this.b = b;
        this.operation = operation;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getResult() {
        return result;
    }

    public Operation getOperation() {
        return operation;
    }

    public void calculate() {
        if (operation == Operation.SUM)
            result = a + b;
        else if (operation == Operation.SUB)
            result = a - b;
        else if (operation == Operation.MUL)
            result = a * b;
        else result = a / b;
    }
}
