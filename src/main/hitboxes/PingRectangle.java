package main.hitboxes;

import java.util.ArrayList;
import java.util.Vector;

public class PingRectangle {

    public double x, y, width, height, angle;
    public Vector<Double> origin;

    public PingRectangle(double x1, double y1, double width1, double height1) {
        x = x1;
        y = y1;
        width = width1;
        height = height1;
        angle = 0;
        origin = new Vector<>();
        origin.add(width / 2);
        origin.add(height / 2);
    }

    public PingRectangle(double x1, double y1, double width1, double height1, double angle1) {
        x = x1;
        y = y1;
        width = width1;
        height = height1;
        angle = angle1;
    }

    public boolean intersects(PingRectangle rect1) {
        ArrayList<Vector<Double>> aRectAxis = new ArrayList<>();

        //This rect
        Vector<Double> upperRight = upperRightCorner();
        Vector<Double> lowerRight = lowerRightCorner();
        Vector<Double> upperLeft = upperLeftCorner();

        Vector<Double> upperRightMinusUpperLeft = new Vector<>();
        upperRightMinusUpperLeft.add(upperRight.get(0) - upperLeft.get(0));
        upperRightMinusUpperLeft.add(upperRight.get(1) - upperLeft.get(1));

        Vector<Double> upperRightMinusLowerRight = new Vector<>();
        upperRightMinusLowerRight.add(upperRight.get(0) - lowerRight.get(0));
        upperRightMinusLowerRight.add(upperRight.get(0) - lowerRight.get(0));

        //Rect1
        Vector<Double> rect1UpperLeft = rect1.upperLeftCorner();
        Vector<Double> rect1UpperRight = rect1.upperRightCorner();
        Vector<Double> rect1LowerLeft = rect1.lowerLeftCorner();

        Vector<Double> rect1UpperLeftMinusLowerLeft = new Vector<>();
        rect1UpperLeftMinusLowerLeft.add(rect1UpperLeft.get(0) - rect1LowerLeft.get(0));
        rect1UpperLeftMinusLowerLeft.add(rect1UpperLeft.get(1) - rect1LowerLeft.get(1));

        Vector<Double> rect1UpperLeftMinusUpperRight =  new Vector<>();
        rect1UpperLeftMinusUpperRight.add(rect1UpperLeft.get(0) - rect1UpperRight.get(0));
        rect1UpperLeftMinusUpperRight.add(rect1UpperLeft.get(1) - rect1UpperRight.get(1));


        aRectAxis.add(upperRightMinusUpperLeft);
        aRectAxis.add(upperRightMinusLowerRight);

        aRectAxis.add(rect1UpperLeftMinusLowerLeft);
        aRectAxis.add(rect1UpperLeftMinusUpperRight);

        for (Vector<Double> axis:
             aRectAxis) {
            if (!isAxisCollision(rect1, axis)) {
                return false;
            }
        }

        return true;
    }

    private boolean isAxisCollision(PingRectangle rect1, Vector<Double> aAxis) {
        int[] aRectangleAScalars = new int[4];
        aRectangleAScalars[0] = (generateScalar(rect1.upperLeftCorner(), aAxis));
        aRectangleAScalars[1] = (generateScalar(rect1.upperRightCorner(), aAxis));
        aRectangleAScalars[2] = (generateScalar(rect1.lowerLeftCorner(), aAxis));
        aRectangleAScalars[3] = (generateScalar(rect1.lowerRightCorner(), aAxis));

        //Project the corners of the current Rectangle on to the Axis and
        //get a scalar value of that project we can then use for comparison
        int[] aRectangleBScalars = new int[4];
        aRectangleBScalars[0] = (generateScalar(upperLeftCorner(), aAxis));
        aRectangleBScalars[1] = (generateScalar(upperRightCorner(), aAxis));
        aRectangleBScalars[2] = (generateScalar(lowerLeftCorner(), aAxis));
        aRectangleBScalars[3] = (generateScalar(lowerRightCorner(), aAxis));

        //Get the Maximum and Minium Scalar values for each of the Rectangles
        int aRectangleAMinimum = getMin(aRectangleAScalars);
        int aRectangleAMaximum = getMax(aRectangleAScalars);
        int aRectangleBMinimum = getMin(aRectangleBScalars);
        int aRectangleBMaximum = getMax(aRectangleBScalars);

        //If we have overlaps between the Rectangles (i.e. Min of B is less than Max of A)
        //then we are detecting a collision between the rectangles on this Axis
        if (aRectangleBMinimum <= aRectangleAMaximum && aRectangleBMaximum >= aRectangleAMaximum)
        {
            return true;
        }
        else return aRectangleAMinimum <= aRectangleBMaximum && aRectangleAMaximum >= aRectangleBMaximum;
    }

    private int generateScalar(Vector<Double> rectCorner, Vector<Double> axis) {
        double aNumerator = (rectCorner.get(0) * axis.get(0)) + (rectCorner.get(1) * axis.get(1));
        double aDenominator = (axis.get(0) * axis.get(0)) + (axis.get(1) * axis.get(1));
        double aDivisionResult = aNumerator / aDenominator;
        Vector<Double> aCornerProjected = new Vector<Double>();
        aCornerProjected.add(aDivisionResult * axis.get(0));
        aCornerProjected.add(aDivisionResult * axis.get(1));

        double aScalar = (axis.get(0) * aCornerProjected.get(0)) + (axis.get(1) * aCornerProjected.get(1));
        return (int) aScalar;
    }

    private Vector<Double> rotatePoint(Vector<Double> point, Vector<Double> origin, double rot) {
        Vector<Double> rotatedPoint = new Vector<>();

        rotatedPoint.add(origin.get(0) + (point.get(0) - origin.get(0)) * Math.cos(rot)
                - (point.get(1) - origin.get(1)) * Math.sin(rot));

        rotatedPoint.add(origin.get(1) + (point.get(1) - origin.get(1)) * Math.cos(rot)
                + (point.get(0) - origin.get(0)) * Math.sin(rot));
        return rotatedPoint;
    }

    public Vector<Double> upperLeftCorner() {
        Vector<Double> aUpperLeft = new Vector<>();
        aUpperLeft.add(x);
        aUpperLeft.add(y);

        Vector<Double> upperLeftPlusOrigin = new Vector<>();
        upperLeftPlusOrigin.add(aUpperLeft.get(0) + origin.get(0));
        upperLeftPlusOrigin.add(aUpperLeft.get(1) + origin.get(1));

        aUpperLeft = rotatePoint(aUpperLeft, upperLeftPlusOrigin, angle);

        return aUpperLeft;
    }
    public Vector<Double> upperRightCorner() {
        Vector<Double> aUpperRight = new Vector<>();
        aUpperRight.add(x + width);
        aUpperRight.add(y);

        Vector<Double> upperRightPlusOrigin = new Vector<>();
        upperRightPlusOrigin.add(aUpperRight.get(0) - origin.get(0));
        upperRightPlusOrigin.add(aUpperRight.get(1) + origin.get(1));

        aUpperRight = rotatePoint(aUpperRight, upperRightPlusOrigin, angle);

        return aUpperRight;
    }
    public Vector<Double> lowerLeftCorner() {
        Vector<Double> aLowerLeft = new Vector<>();
        aLowerLeft.add(x);
        aLowerLeft.add(y + height);

        Vector<Double> lowerLeftPlusOrigin = new Vector<>();
        lowerLeftPlusOrigin.add(aLowerLeft.get(0) + origin.get(0));
        lowerLeftPlusOrigin.add(aLowerLeft.get(1) - origin.get(1));

        aLowerLeft = rotatePoint(aLowerLeft, lowerLeftPlusOrigin, angle);

        return aLowerLeft;
    }
    public Vector<Double> lowerRightCorner() {
        Vector<Double> aLowerRight = new Vector<>();
        aLowerRight.add(x + width);
        aLowerRight.add(y + height);

        Vector<Double> lowerRightMinusOrigin = new Vector<>();
        lowerRightMinusOrigin.add(aLowerRight.get(0) - origin.get(0));
        lowerRightMinusOrigin.add(aLowerRight.get(1) - origin.get(1));

        aLowerRight = rotatePoint(aLowerRight, lowerRightMinusOrigin, angle);

        return aLowerRight;
    }

    private int getMin(int[] a) {
        int min = a[0];
        for (int i:
             a) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }

    private int getMax(int[] a) {
        int max = a[0];
        for (int i:
                a) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

}
