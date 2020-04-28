package dwelling;

import interfaces.Floor;
import interfaces.Space;
import exceptions.SpaceIndexOutOfBoundsException;

import java.util.Iterator;

public class SynchronizedFloor implements Floor {
    private Space[] spaces;

    public SynchronizedFloor(int spaceCount) {
        spaces = new Space[spaceCount];
        for (int i = 0; i < spaces.length; ++i)
            spaces[i] = new Flat();
    }

    public SynchronizedFloor(Space...spaces) {
        this.spaces = spaces;
    }

    public synchronized int getSpaceCount() {
        return spaces.length;
    }

    public synchronized double getTotalSpaceArea() {
        double totalSpaceArea = 0;
        for(int i = 0; i < spaces.length; ++i)
            totalSpaceArea += spaces[i].getArea();
        return totalSpaceArea;
    }

    public synchronized int getTotalRoomCount() {
        int totalRoomCount = 0;
        for(int i = 0; i < spaces.length; ++i)
            totalRoomCount += spaces[i].getRoomCount();
        return totalRoomCount;
    }

    public synchronized Space[] getSpaces() {
        return spaces;
    }

    public synchronized Space getSpaceByIndex(int index) {
        if (index < 0 || spaces.length <= index)
            throw new SpaceIndexOutOfBoundsException();
        return spaces[index];
    }

    public synchronized void setSpaceByIndex(int index, Space space) {
        if (index < 0 || spaces.length <= index)
            throw new SpaceIndexOutOfBoundsException();
        spaces[index] = space;
    }

    public synchronized void addSpaceByIndex(int index, Space space) {
        if (index < 0 || spaces.length <= index)
            throw new SpaceIndexOutOfBoundsException();
        Space[] spaces = new Space[this.spaces.length + 1];
        System.arraycopy(this.spaces, 0, spaces, 0, index);
        spaces[index] = space;
        System.arraycopy(this.spaces, index, spaces, index + 1, this.spaces.length - index);
        this.spaces = spaces;
    }

    public synchronized void deleteSpaceByIndex(int index) {
        if (index < 0 || spaces.length <= index)
            throw new SpaceIndexOutOfBoundsException();
        Space[] spaces = new Flat[this.spaces.length - 1];
        System.arraycopy(this.spaces, 0, spaces, 0, index);
        System.arraycopy(this.spaces, index + 1, spaces, index, this.spaces.length - index - 1);
        this.spaces = spaces;
    }

    public synchronized Space getBestSpace() {
        Space bestSpace = spaces[0];
        for(int i = 1; i < spaces.length; ++i)
            if(spaces[i].getArea() > bestSpace.getArea())
                bestSpace = spaces[i];
        return bestSpace;
    }

    @Override
    public synchronized String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DwellingFloor");
        for (int i = 0; i < spaces.length; ++i)
            stringBuffer.append(' ' + spaces[i].toString());
        return stringBuffer.toString();
    }

    @Override
    public synchronized boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof SynchronizedFloor))
            return false;
        SynchronizedFloor floor = (SynchronizedFloor) obj;
        if (floor.spaces.length != spaces.length)
            return false;
        for (int i = 0; i < spaces.length; ++i)
            if (!spaces[i].equals(floor.spaces[i]))
                return false;
        return true;
    }

    @Override
    public synchronized int hashCode() {
        int h = spaces.length;
        for (int i = 0; i < spaces.length; ++i) {
            h ^= spaces[i].hashCode();
        }
        return h;
    }

    @Override
    public synchronized Object clone() {
        Space[] spaces = new Space[this.spaces.length];
        for (int i = 0; i < this.spaces.length; ++i)
            spaces[i] = (Space)this.spaces[i].clone();
        return new DwellingFloor(spaces);
    }

    @Override
    public synchronized Iterator<Space> iterator() {
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
    public synchronized int compareTo(Floor o) {
        return spaces.length - o.getSpaceCount();
    }
}
