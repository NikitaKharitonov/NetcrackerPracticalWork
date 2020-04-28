package interfaces;

import java.io.Serializable;

public interface Space extends Serializable, Comparable<Space> {
    int getRoomCount();
    void setRoomCount(int roomCount);
    double getArea();
    void setArea(double area);
    Object clone();
}
