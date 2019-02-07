package ru.atom.geometry;

/**
 * ^ Y
 * |
 * |
 * |
 * |          X
 * .---------->
 */

public final class Geometry implements Collider {

    int xl;
    int yl;
    int xr;
    int yr;

    public int getXl() {

        return xl;

    }

    public int getYl() {

        return yl;

    }

    public int getXr() {

        return xr;

    }

    public int getYr() {

        return yr;

    }

    private Geometry(int xl, int yl, int xr, int yr) {

        this.xl = xl;
        this.yl = yl;
        this.xr = xr;
        this.yr = yr;

    }

    /**
     * Bar is a rectangle, which borders are parallel to coordinate axis
     * Like selection bar in desktop, this bar is defined by two opposite corners
     * Bar is not oriented
     * (It is not relevant, which opposite corners you choose to define bar)
     * @return new Bar
     */
    public static Collider createBar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {

        int xl;
        int yl;
        int xr;
        int yr;

        if (firstCornerX < secondCornerX) {

            xl = firstCornerX;
            xr = secondCornerX;

        } else {

            xr = firstCornerX;
            xl = secondCornerX;

        }

        if (firstCornerY < secondCornerY) {

            yl = firstCornerY;
            yr = secondCornerY;

        } else {

            yr = firstCornerY;
            yl = secondCornerY;

        }

        return new Geometry(xl, yl, xr, yr);
    }

    /**
     * 2D point
     * @return new Point
     */
    public static Collider createPoint(int x, int y) {
        return new Point(x, y);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null) return false;
        if (other instanceof Point) {
            Point point = (Point) other;
            return (point.getX() >= this.getXl()
                    & point.getX() <= this.getXr()
                    & point.getY() >= this.getYl()
                    & point.getY() <= this.getYr());
        }
        if (other instanceof Geometry) {
            int xl;
            int xr;
            int yl;
            int yr;
            Geometry bar = (Geometry) other;
            if (bar.getXl() < bar.getXr()) {
                xl = bar.getXl();
                xr = bar.getXr();
            } else {
                xl = bar.getXl();
                xr = bar.getXr();
            }
            if (bar.getYl() < bar.getYr()) {
                yl = bar.getYl();
                yr = bar.getYr();
            } else {
                yl = bar.getYr();
                yr = bar.getYl();
            }

            if (xl < getXl() & xr < getXl()) return false;
            if (xl > getXr() & xr > getXr()) return false;
            if (yl < getYl() & yr < getYl()) return false;
            if (yl > getYr() & yr > getYr()) return false;
            return true;
        }
        return false;
    }
}