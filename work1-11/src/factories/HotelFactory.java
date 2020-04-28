package factories;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import dwelling.Flat;
import dwelling.hotel.Hotel;
import dwelling.hotel.HotelFloor;

public class HotelFactory implements BuildingFactory {
    @Override
    public Space createSpace(double area) {
        return new Flat(area);
    }

    @Override
    public Space createSpace(double area, int roomCount) {
        return new Flat(area, roomCount);
    }

    @Override
    public Floor createFloor(int spaceCount) {
        return new HotelFloor(spaceCount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new HotelFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorCount, int[] spaceCounts) {
        return new Hotel(floorCount, spaceCounts);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new Hotel(floors);
    }
}
