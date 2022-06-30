import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputManager {
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        greet();
        Matrix matrix = createMatrix();
        if (matrix.getElements().isEmpty()) return;
        System.out.println("Матрица:");
        for (int i = 0; i < matrix.getSize(); i++) {
            matrix.getElements().get(i).forEach(element -> System.out.print(element + " "));
            System.out.println();
        }
        Result result = ComputationalMethod.compute(matrix, readAccuracy());
        if (null == result) System.out.println("Невозможно достичь диагонального преобладания.");
        else {
            for (int i = 0; i < result.getVariables().size(); i++) {
                System.out.println("x" + (i + 1) + " = " + result.getVariables().get(i));
            }
            System.out.println("Количество итераций, за которое было найдено решение: " + result.getCount());
            for (int i = 0; i < result.getVariables().size(); i++) {
                System.out.println("Погрешность x" + (i + 1) + ": " + result.getErrors().get(i));
            }
        }
    }

    private void greet() {
        System.out.println("Решение СЛАУ: метод Гаусса-Зейделя.");
        System.out.println("Элементы матрицы необходимо вводить построчно, через пробел: a1 a2 ... an b1");
    }

    private int readMethod() {
        int method = 0;
        boolean correct = false;
        while (!correct) {
            System.out.println("Выберите метод ввода данных:\n[1] - ввод вручную.\n[2] - ввод из файла.\n[3] - генерация случайной матрицы.");
            try {
                method = Integer.parseInt(scanner.nextLine());
                if (method >= 1 && method <= 3) {
                    correct = true;
                } else throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Введено неверное значение.");
            }
        }
        return method;
    }

    private void setScannerFile() {
        boolean correct = false;
        while (!correct) {
            System.out.println("Введите имя файла:");
            try {
                File file = new File(scanner.nextLine());
                scanner = new Scanner(file);
                correct = true;
            } catch (FileNotFoundException e) {
                System.out.println("Введено неверное имя файла.");
            }
        }
    }

    private int readSize(boolean fromFile) {
        int size = 0;
        if (!fromFile) {
            boolean correct = false;
            while (!correct) {
                System.out.println("Введите размер системы от 1 до 20:");
                try {
                    size = Integer.parseInt(scanner.nextLine());
                    if (size >= 1 && size <= 20) {
                        correct = true;
                    } else throw new IllegalArgumentException();
                } catch (IllegalArgumentException e) {
                    System.out.println("Введено неверное значение размера системы.");
                }
            }
        } else {
            try {
                size = Integer.parseInt(scanner.nextLine());
                if (size < 1 || size > 20) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Указано неверное значение размера системы.\nРазмер системы может принимать значения от 1 до 20.");
            }
        }
        return size;
    }

    private ArrayList<ArrayList<Double>> readElements(boolean fromFile, int size) {
        ArrayList<ArrayList<Double>> elements = new ArrayList<>();
        if (!fromFile) {
            for (int i = 0; i < size; i++) {
                boolean correct = false;
                elements.add(new ArrayList<>());
                while (!correct) {
                    System.out.println("Введите элементы " + (i + 1) + " строки матрицы:");
                    try {
                        String[] input = scanner.nextLine().replace(',', '.').split(" ");
                        if (input.length != size + 1) {
                            throw new IllegalArgumentException();
                        }
                        for (String element : input) elements.get(i).add(Double.parseDouble(element));
                        correct = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Введено неверное значение.");
                        elements.get(i).clear();
                    } catch (IllegalArgumentException e) {
                        System.out.println("В строке должно быть " + (size + 1) + " элементов.");
                    }
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                elements.add(new ArrayList<>());
                try {
                    String[] input = scanner.nextLine().replace(',', '.').split(" ");
                    if (input.length != size + 1) {
                        throw new IllegalArgumentException();
                    }
                    for (String element : input) elements.get(i).add(Double.parseDouble(element));
                } catch (IllegalArgumentException e) {
                    System.out.println("Данные в файле некорректны.");
                    elements.clear();
                    break;
                }
            }
        }
        return elements;
    }


    private double readAccuracy() {
        double accuracy = 0;
        boolean correct = false;
        while (!correct) {
            System.out.println("Введите точность от 0 до 1:");
            try {
                accuracy = Double.parseDouble(scanner.nextLine());
                if (accuracy > 0 && accuracy < 1) {
                    correct = true;
                } else throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Введено неверное значение.");
            }
        }
        return accuracy;
    }

    private Matrix createMatrix() {
        ArrayList<ArrayList<Double>> elements = new ArrayList<>();
        int size = 0;
        int method = readMethod();
        switch (method) {
            case (1) -> {
                size = readSize(false);
                elements = readElements(false, size);
            }
            case (2) -> {
                setScannerFile();
                size = readSize(true);
                elements = readElements(true, size);
                scanner = new Scanner(System.in);
            }
            case (3) -> {
                size = readSize(false);
                elements = ElementsGenerator.generate(size);
            }
        }
        return new Matrix(size, elements);
    }
}
