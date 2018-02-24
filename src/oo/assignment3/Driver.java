package oo.assignment3;

import java.util.Optional;
import java.util.Scanner;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Driver {
    private final Scanner scanner;
    private final GeometricCollection collection;

    public Driver(GeometricCollection collection) {
        scanner = new Scanner(System.in);
        this.collection = collection;
    }

    private void listElements() {
        System.out.println("\nThe collection contains:");
        for(int i = 0; i < collection.getNumElements(); ++i) {
            System.out.printf("i: %2d %s\n", i, collection.getElements()[i]);
        }
    }

    private void cmdCircle() {
        double x = scanner.nextDouble();
        double y = scanner.nextDouble();
        double r = scanner.nextDouble();

        if(collection.canAdd()) {
            collection.add(new Circle(x, y, r));
        } else {
            System.err.println("Cannot add, array is full");
        }
    }

    private void cmdRectangle() {
        double x = scanner.nextDouble();
        double y = scanner.nextDouble();
        double w = scanner.nextDouble();
        double h = scanner.nextDouble();

        if(collection.canAdd()) {
            collection.add(new Rectangle(x, y, w, h));
        } else {
            System.err.println("Cannot add, array is full");
        }
    }

    private void cmdMove() {
        int i = scanner.nextInt();
        double dx = scanner.nextDouble();
        double dy = scanner.nextDouble();

        Optional<Geometric> obj = collection.get(i);

        if(obj.isPresent()) {
            obj.get().move(dx, dy);
        } else {
            System.err.printf("Invalid index: '%d'\n", i);
        }
    }

    private void cmdRemove() {
        int i = scanner.nextInt();

        collection.remove(i);
    }

    private void cmdSort() {
        String arg = scanner.nextLine().trim();

        if(arg.isEmpty()) {
            collection.sort(SortOrder.Area);
        } else if(arg.equalsIgnoreCase("x")) {
            collection.sort(SortOrder.Left);
        } else if(arg.equalsIgnoreCase("y")) {
            collection.sort(SortOrder.Bottom);
        } else {
            System.err.printf("Invalid sort argument: '%s'\n", arg);
        }
    }

    public void run() {
        boolean keepRunning = true;

        do {
            System.out.print("\n[cmd]>");
            String command = scanner.next();

            if(command.equalsIgnoreCase("quit")) {
                keepRunning = false;
            } else if(command.equalsIgnoreCase("show")) {
                listElements();
            } else if(command.equalsIgnoreCase("circle")) {
                cmdCircle();
                listElements();
            } else if(command.equalsIgnoreCase("rectangle")) {
                cmdRectangle();
                listElements();
            } else if(command.equalsIgnoreCase("move")) {
                cmdMove();
                listElements();
            } else if(command.equalsIgnoreCase("remove")) {
                cmdRemove();
                listElements();
            } else if(command.equalsIgnoreCase("sort")) {
                cmdSort();
                listElements();
            } else {
                System.err.printf("Unknown command: '%s'\n", command);
                scanner.nextLine(); // Consume buffered input
            }
        } while(keepRunning);
    }
}
