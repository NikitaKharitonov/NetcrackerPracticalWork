package comparators;

import interfaces.Floor;

import java.util.Comparator;

public class FloorComparator implements Comparator<Floor> {

    @Override
    public int compare(Floor o1, Floor o2) {
        return (int)Math.signum(o2.getTotalSpaceArea() - o1.getTotalSpaceArea());
    }
}
