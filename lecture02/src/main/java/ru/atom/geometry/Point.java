package ru.atom.geometry;

import java.util.Objects;

/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {

    // fields
    int x;
    int y;

    // and methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!(o instanceof Point)) return false;

        // cast from Object to Point
        Point point = (Point) o;

        // your code here
        //throw new UnsupportedOperationException();
        //if (o instanceof Point) return (this.x == ((Point) o).getX() & this.y == ((Point) o).getY()); //Andrey
        return getX() == point.getX() & getY() == point.getY();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        if (other instanceof Point) {
            Point point = (Point) other;
            return getX() == point.getX() &
                    getY() == point.getY();
        }
        if (other instanceof Geometry) {
            Geometry bar = (Geometry) other;
            return (getX() >= bar.getXl() &
                    getX() <= bar.getXr() &
                    getY() >= bar.getYl() &
                    getY() <= bar.getYr());
        }
        return false;
    }
}