import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private ArrayList<Point> fromList = new ArrayList<>();
    private ArrayList<Point> toList = new ArrayList<>();
    private int size = 0;
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (!checkArgs(points)) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i == j) {
                    continue;
                }
                for (int k = 0; k < points.length; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    double iToJ = points[i].slopeTo(points[j]);
                    double jToK = points[j].slopeTo(points[k]);
                    int ordered = Double.compare(iToJ, jToK);
                    if (ordered != 0) {
                        continue;
                    }

                    for (int l = 0; l < points.length; l++) {
                        if (l == i || l == j || l == k) {
                            continue;
                        }
                        double kTol = points[k].slopeTo(points[l]);
                        if (Double.compare(jToK, kTol) == 0) {

                            boolean contains = false;
                            for (int pi = 0; pi < size; pi++) {
                                if ((fromList.get(pi).compareTo(points[i]) == 0 &&
                                    toList.get(pi).compareTo(points[l]) == 0) ||
                                    (fromList.get(pi).compareTo(points[l]) == 0 &&
                                    toList.get(pi).compareTo(points[i]) == 0)) {
                                    contains = true;
                                    break;
                                }
                            }
                            if (!contains) {
                                fromList.add(points[i]);
                                toList.add(points[l]);
                                size++;
                            }
                        }
                    }
                }
            }
        }
        segments = new LineSegment[size];
        for(int i = 0; i < size; i++) {
            segments[i] = new LineSegment(fromList.get(i), toList.get(i));
        }
    }

    private boolean checkArgs (Point[] points) {
        if (points == null) {
            return false;
        }
        for (Point point: points) {
            if (point == null) {
                return false;
            }
        }
        Point[] sorted = points.clone();
        Arrays.sort(sorted, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.compareTo(o2);
            }
        });
        for (int i = 0; i < sorted.length - 1; i++) {
            if (sorted[i] == sorted[i + 1]) {
                return false;
            }
        }
        return true;
    }

    // the number of line segments
    public int numberOfSegments(){
        return size;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

}