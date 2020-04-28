package comparators;

import interfaces.Space;

import java.util.Comparator;

public class SpaceComparator implements Comparator<Space> {

    @Override
    public int compare(Space o1, Space o2) {
        return o2.getRoomCount() - o1.getRoomCount();
    }
}
