package interfaces;

import java.io.Serializable;

public interface Building extends Serializable, Iterable<Floor> {
    int getFloorCount();
    int getSpaceCount();
    double getTotalSpaceArea();
    int getTotalRoomCount();
    Floor[] getFloors();
    Floor getFloorByIndex(int index);
    void setFloorByIndex(int index, Floor floor);
    Space getSpaceByIndex(int index);
    void setSpaceByIndex(int index, Space space);
    void addSpaceByIndex(int index, Space space);
    void deleteSpaceByIndex(int index);
    Space getBestSpace();
    Space[] getSpacesByAreaDescending();
    Object clone();
}
