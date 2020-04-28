package interfaces;

import java.io.Serializable;

public interface Floor extends Serializable, Iterable<Space>, Comparable<Floor> {
    int getSpaceCount();
    double getTotalSpaceArea();
    int getTotalRoomCount();
    Space[] getSpaces();
    Space getSpaceByIndex(int index);
    void setSpaceByIndex(int index, Space space);
    void addSpaceByIndex(int index, Space space);
    void deleteSpaceByIndex(int index);
    Space getBestSpace();
    Object clone();
}
