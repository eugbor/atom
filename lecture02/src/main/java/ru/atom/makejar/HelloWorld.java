package ru.atom.makejar;

import ru.atom.geometry.Collider;
import ru.atom.geometry.Point;

/**
 * Class just to test fat jar packing
 */
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println(getHelloWorld());

//        Collider collider = new Point(1,2);
//
//        Point point = new Point(1,2);
        }

    public static String getHelloWorld() {
        return "Hello, World!";
    }
}
