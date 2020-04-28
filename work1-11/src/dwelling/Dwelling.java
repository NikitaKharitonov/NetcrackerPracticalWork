package dwelling;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import exceptions.FloorIndexOutOfBoundsException;
import dwelling.hotel.Hotel;
import exceptions.SpaceIndexOutOfBoundsException;

import java.util.Iterator;

public class Dwelling implements Building {

    private Floor[] floors;

    public Dwelling(int floorCount, int...spaceCounts) {
        if (spaceCounts.length < floorCount)
            floorCount = spaceCounts.length;
        floors = new Floor[floorCount];
        for (int i = 0; i < floorCount; ++i)
            floors[i] = new DwellingFloor(spaceCounts[i]);
    }

    public Dwelling(Floor...floors) {
        this.floors = floors;
    }

    public int getFloorCount() {
        return floors.length;
    }

    public int getSpaceCount() {
        int spaceCount = 0;
        for (Floor floor: floors)
            spaceCount += floor.getSpaceCount();
        return spaceCount;
    }

    public double getTotalSpaceArea() {
        double totalAreaOfFlats = 0;
        for (Floor floor: floors)
            totalAreaOfFlats += floor.getTotalSpaceArea();
        return totalAreaOfFlats;
    }

    public int getTotalRoomCount() {
        int totalRoomCount = 0;
        for (Floor floor: floors)
            totalRoomCount = floor.getTotalRoomCount();
        return totalRoomCount;
    }

    public Floor[] getFloors() {
        return floors;
    }

    public Floor getFloorByIndex(int index) {
        if (index < 0 || floors.length <= index)
            throw new FloorIndexOutOfBoundsException();
        return floors[index];
    }

    public void setFloorByIndex(int index, Floor floor) {
        if (index < 0 || floors.length <= index)
            throw new FloorIndexOutOfBoundsException();
        floors[index] = floor;
    }

    public Space getSpaceByIndex(int index) {
        if (index < 0 || getSpaceCount() <= index)
            throw new SpaceIndexOutOfBoundsException();
        for (Floor floor: floors)
            if(index > floor.getSpaceCount())
                index -= floor.getSpaceCount();
            else
                return floor.getSpaceByIndex(index);
        return null;
    }

    public void setSpaceByIndex(int index, Space space) {
        if (index < 0 || getSpaceCount() <= index)
            throw new SpaceIndexOutOfBoundsException();
        for (Floor floor: floors)
            if(index > floor.getSpaceCount())
                index -= floor.getSpaceCount();
            else
                floor.setSpaceByIndex(index, space);
    }

    public void addSpaceByIndex(int index, Space space) {
        if (index < 0 || getSpaceCount() <= index)
            throw new SpaceIndexOutOfBoundsException();
        for (Floor floor: floors)
            if(index > floor.getSpaceCount())
                index -= floor.getSpaceCount();
            else {
                floor.addSpaceByIndex(index, space);
                break;
            }
    }

    public void deleteSpaceByIndex(int index) {
        if (index < 0 || getSpaceCount() <= index)
            throw new SpaceIndexOutOfBoundsException();
        for (Floor floor: floors)
            if(index >= floor.getSpaceCount())
                index -= floor.getSpaceCount();
            else {
                floor.deleteSpaceByIndex(index);
                break;
            }
    }

    public Space getBestSpace() {
        Space bestSpace = floors[0].getBestSpace();
        for(int i = 1; i < floors.length; ++i)
            if(floors[i].getBestSpace().getArea() > bestSpace.getArea())
                bestSpace = floors[i].getBestSpace();
        return bestSpace;
    }

    public Space[] getSpacesByAreaDescending() {
        // get array of spaces
        Space[] spaces = new Space[getSpaceCount()];
        int destPos = 0;
        for(Floor floor: floors) {
            System.arraycopy(floor.getSpaces(), 0, spaces, destPos, floor.getSpaceCount());
            destPos += floor.getSpaceCount();
        }
        // bubble sort
        boolean isChange;
        do {
            isChange = false;
            for (int i = 1; i < spaces.length; ++i) {
                if (spaces[i - 1].getArea() < spaces[i].getArea()) {
                    Space temp = spaces[i - 1];
                    spaces[i - 1] = spaces[i];
                    spaces[i] = temp;
                    isChange = true;
                }
            }
        } while(isChange);
        return spaces;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Dwelling");
        for (Floor floor: floors) {
            builder.append(" ");
            builder.append(floor.toString());
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Dwelling) || obj instanceof Hotel)
            return false;
        Dwelling dwelling = (Dwelling) obj;
        if (dwelling.floors.length != floors.length)
            return false;
        for (int i = 0; i < floors.length; ++i)
            if (!dwelling.floors[i].equals(floors[i]))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        int h = floors.length;
        for (Floor floor: floors)
            h ^= floor.hashCode();
        return h;
    }

    @Override
    public Object clone() {
        Floor[] floors = new Floor[this.floors.length];
        for (int i = 0; i < this.floors.length; ++i)
            floors[i] = (Floor) this.floors[i].clone();
        return new Dwelling(floors);
    }

    @Override
    public Iterator<Floor> iterator() {
        return new Iterator<>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return (index < floors.length);
            }

            @Override
            public Floor next() {
                return floors[index++];
            }
        };
    }
}
