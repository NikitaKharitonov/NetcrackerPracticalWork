package dwelling.hotel;

import interfaces.Space;
import dwelling.DwellingFloor;

public class HotelFloor extends DwellingFloor {
    private int starCount;
    private final int STAR_COUNT = 1;

    public HotelFloor(Space... spaces) {
        super(spaces);
        starCount = STAR_COUNT;
    }

    public HotelFloor(int spaceCount) {
        super(spaceCount);
        starCount = STAR_COUNT;
    }

    int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("HotelFloor (");
        builder.append(starCount);
        builder.append(" ");
        builder.append(getSpaceCount());
        builder.append(" ");
        for (int i = 0 ; i < getSpaceCount(); ++i) {
            builder.append(" ");
            builder.append(getSpaceByIndex(i).toString());
        }
        builder.append(")");
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof HotelFloor))
            return false;
        HotelFloor hotelFloor = (HotelFloor) obj;
        if (starCount != hotelFloor.starCount)
            return false;
        if (hotelFloor.getSpaceCount() != getSpaceCount())
            return false;
        for (int i = 0; i < getSpaceCount(); ++i)
            if (!getSpaceByIndex(i).equals(hotelFloor.getSpaceByIndex(i)))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ (31 * starCount);
    }
}
