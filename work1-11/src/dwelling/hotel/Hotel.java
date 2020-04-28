package dwelling.hotel;

import interfaces.Floor;
import interfaces.Space;
import dwelling.Dwelling;
import dwelling.Flat;

public class Hotel extends Dwelling {

    private double getCoeff(int starCount) {
        switch (starCount) {
            case 1:
                return 0.25;
            case 2:
                return 0.5;
            case 3:
                return 1;
            case 4:
                return 1.25;
            case 5:
                return 1.5;
        }
        return 0;
    }
    public Hotel (Floor... floors) {
        super(floors);
    }

    public Hotel (int floorCount, int... spaceCounts) {
        super(floorCount, spaceCounts);
    }

    public int getHotelStarCount() {
        int hotelStarCount = 0;
        Floor[] floors = getFloors();
        for (Floor floor: floors)
            if (floor instanceof HotelFloor) {
                int starCount = ((HotelFloor) floor).getStarCount();
                if (starCount > hotelStarCount)
                    hotelStarCount = starCount;
            }
        return hotelStarCount;
    }

    @Override
    public Space getBestSpace() {
        Space bestSpace = new Flat(0, 0);
        double bestScore = 0;
        Floor[] floors = getFloors();
        for (Floor floor: floors) {
            if (floor instanceof HotelFloor) {
                HotelFloor hotelFloor = (HotelFloor)floor;
                for (int j = 0; j < hotelFloor.getSpaceCount(); ++j) {
                    double score = hotelFloor.getSpaceByIndex(j).getArea() * getCoeff(hotelFloor.getStarCount());
                    if (score > bestScore) {
                        bestScore = score;
                        bestSpace = hotelFloor.getSpaceByIndex(j);
                    }
                }
            }
        }
        return bestSpace;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Hotel (");
        builder.append(getHotelStarCount());
        builder.append(" ");
        builder.append(getFloorCount());
        builder.append(" ");
        for (int i = 0; i < getFloorCount(); ++i)
            builder.append(getFloorByIndex(i).toString());
        builder.append(")");
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Hotel))
            return false;
        Hotel hotel = (Hotel) obj;
        if (hotel.getFloorCount() != getFloorCount())
            return false;
        for (int i = 0; i < getFloorCount(); ++i)
            if (!getFloorByIndex(i).equals(hotel.getFloorByIndex(i)))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
