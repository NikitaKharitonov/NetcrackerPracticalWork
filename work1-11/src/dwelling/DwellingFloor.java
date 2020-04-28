package dwelling;

import interfaces.Floor;
import interfaces.Space;
import exceptions.SpaceIndexOutOfBoundsException;
import dwelling.hotel.HotelFloor;

import java.util.Iterator;

public class DwellingFloor implements Floor {

    private Space[] spaces;

    public DwellingFloor(int spaceCount) {
        spaces = new Space[spaceCount];
        for (int i = 0; i < spaces.length; ++i)
            spaces[i] = new Flat();
    }

    public DwellingFloor(Space...spaces) {
        this.spaces = spaces;
    }

    public int getSpaceCount() {
        return spaces.length;
    }

    public double getTotalSpaceArea() {
        double totalSpaceArea = 0;
        for(Space space: spaces)
            totalSpaceArea += space.getArea();
        return totalSpaceArea;
    }

    public int getTotalRoomCount() {
        int totalRoomCount = 0;
        for(Space space: spaces)
            totalRoomCount += space.getRoomCount();
        return totalRoomCount;
    }

    public Space[] getSpaces() {
        return spaces;
    }

    public Space getSpaceByIndex(int index) {
        if (index < 0 || spaces.length <= index)
            throw new SpaceIndexOutOfBoundsException();
        return spaces[index];
    }

    public void setSpaceByIndex(int index, Space space) {
        if (index < 0 || spaces.length <= index)
            throw new SpaceIndexOutOfBoundsException();
        spaces[index] = space;
    }

    public void addSpaceByIndex(int index, Space space) {
        if (index < 0 || spaces.length <= index)
            throw new SpaceIndexOutOfBoundsException();
        Space[] spaces = new Space[this.spaces.length + 1];
        System.arraycopy(this.spaces, 0, spaces, 0, index);
        spaces[index] = space;
        System.arraycopy(this.spaces, index, spaces, index + 1, this.spaces.length - index);
        this.spaces = spaces;
    }

    public void deleteSpaceByIndex(int index) {
        if (index < 0 || spaces.length <= index)
            throw new SpaceIndexOutOfBoundsException();
        Space[] spaces = new Flat[this.spaces.length - 1];
        System.arraycopy(this.spaces, 0, spaces, 0, index);
        System.arraycopy(this.spaces, index + 1, spaces, index, this.spaces.length - index - 1);
        this.spaces = spaces;
    }

    public Space getBestSpace() {
        Space bestSpace = spaces[0];
        for(int i = 1; i < spaces.length; ++i)
            if(spaces[i].getArea() > bestSpace.getArea())
                bestSpace = spaces[i];
        return bestSpace;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DwellingFloor");
        for (Space space: spaces) {
            builder.append(' ');
            builder.append(space.toString());
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof DwellingFloor) || obj instanceof HotelFloor)
            return false;
        DwellingFloor dwellingFloor = (DwellingFloor) obj;
        if (dwellingFloor.spaces.length != spaces.length)
            return false;
        for (int i = 0; i < spaces.length; ++i)
            if (!spaces[i].equals(dwellingFloor.spaces[i]))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        int h = spaces.length;
        for (Space space: spaces) {
            h ^= space.hashCode();
        }
        return h;
    }

    @Override
    public Object clone() {
        Space[] spaces = new Space[this.spaces.length];
        for (int i = 0; i < this.spaces.length; ++i)
            spaces[i] = (Space)this.spaces[i].clone();
        return new DwellingFloor(spaces);
    }

    @Override
    public Iterator<Space> iterator() {
        return new Iterator<>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return (index < spaces.length);
            }

            @Override
            public Space next() {
                return spaces[index++];
            }
        };
    }

    @Override
    public int compareTo(Floor o) {
        return spaces.length - o.getSpaceCount();
    }
}
