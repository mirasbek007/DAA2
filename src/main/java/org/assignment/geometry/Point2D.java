package org.assignment.geometry;

public final class Point2D implements Comparable<Point2D> {
    public final double x, y;
    public Point2D(double x, double y) { this.x = x; this.y = y; }
    public double dist2(Point2D other) {
        double dx = x - other.x; double dy = y - other.y; return dx*dx + dy*dy;
    }
    @Override
    public int compareTo(Point2D o) {
        int cmp = Double.compare(this.x, o.x);
        if (cmp != 0) return cmp;
        return Double.compare(this.y, o.y);
    }
    @Override
    public String toString() { return "(" + x + "," + y + ")"; }
}

