package org.assignment.geometry;

import org.assignment.metrics.Metrics;

import java.util.Arrays;

public final class ClosestPair {
    public static double bruteForce(Point2D[] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++)
            for (int j = i+1; j < pts.length; j++)
                best = Math.min(best, Math.sqrt(pts[i].dist2(pts[j])));
        return best;
    }

    public static double closest(Point2D[] pts, Metrics m) {
        if (pts == null || pts.length < 2) return 0.0;
        Point2D[] byX = pts.clone();
        Arrays.sort(byX);
        Point2D[] byY = byX.clone();
        // stable sort by y using lambda - but we must not rely on java.util heavy features; Arrays.sort with comparator is fine
        Arrays.sort(byY, (a,b) -> Double.compare(a.y, b.y));
        return Math.sqrt(closestRec(byX, byY, 0, pts.length - 1, m));
    }

    private static double closestRec(Point2D[] byX, Point2D[] byY, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= 3) {
            double best = Double.POSITIVE_INFINITY;
            for (int i = lo; i <= hi; i++) for (int j = i+1; j <= hi; j++)
                best = Math.min(best, byX[i].dist2(byX[j]));
            return best;
        }
        int mid = lo + (n/2) - 1;
        Point2D midPoint = byX[mid];

        // split byY into left and right arrays
        Point2D[] leftY = new Point2D[mid - lo + 1];
        Point2D[] rightY = new Point2D[hi - mid];
        int li = 0, ri = 0;
        for (Point2D p : byY) {
            if (p.x <= midPoint.x) leftY[li++] = p;
            else rightY[ri++] = p;
        }

        double dl = closestRec(byX, leftY, lo, mid, m);
        double dr = closestRec(byX, rightY, mid+1, hi, m);
        double d = Math.min(dl, dr);

        // build strip
        Point2D[] strip = new Point2D[byY.length];
        int stripCount = 0;
        double dSqr = d;
        for (Point2D p : byY) {
            if (Math.abs(p.x - midPoint.x) < Math.sqrt(dSqr)) strip[stripCount++] = p;
        }
        double best = dSqr;
        for (int i = 0; i < stripCount; i++) {
            for (int j = i+1; j < stripCount && (strip[j].y - strip[i].y) * (strip[j].y - strip[i].y) < best; j++) {
                best = Math.min(best, strip[i].dist2(strip[j]));
            }
        }
        return best;
    }
}

