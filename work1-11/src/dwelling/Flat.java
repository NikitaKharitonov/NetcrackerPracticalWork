package dwelling;

import exceptions.InvalidRoomsCountException;
import exceptions.InvalidSpaceAreaException;
import interfaces.Space;

public class Flat implements Space {

    private double area;
    private int roomCount;
    private final double AREA = 50;
    private final int ROOM_COUNT = 2;

    public Flat() {
        area = AREA;
        roomCount = ROOM_COUNT;
    }

    public Flat(double area) {
        if (area <= 0)
            throw new InvalidSpaceAreaException();
        this.area = area;
        roomCount = ROOM_COUNT;
    }

    public Flat(double area, int roomCount) {
        if (area <= 0)
            throw new InvalidSpaceAreaException();
        if (roomCount <= 0)
            throw new InvalidRoomsCountException();
        this.roomCount = roomCount;
        this.area = area;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        if (roomCount <= 0)
            throw new InvalidRoomsCountException();
        this.roomCount = roomCount;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        if (area <= 0)
            throw new InvalidSpaceAreaException();
        this.area = area;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Flat");
        builder.append(" ");
        builder.append(area);
        builder.append(" ");
        builder.append(roomCount);
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Flat))
            return false;
        Flat flat = (Flat) obj;
        return (Double.compare(flat.area, area) == 0 && flat.roomCount == roomCount);
    }

    @Override
    public int hashCode() {
        long area = Double.doubleToRawLongBits(this.area);
        int h1 = (int)area;
        int h2 = (int)(area >> 32);
        return h1 ^ h2 ^ roomCount;
    }

    @Override
    public Object clone() {
        return new Flat(area, roomCount);
    }

    @Override
    public int compareTo(Space o) {
        return (int)Math.signum(area - o.getArea());
    }
}
