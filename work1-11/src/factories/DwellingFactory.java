package factories;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import dwelling.Dwelling;
import dwelling.DwellingFloor;
import dwelling.Flat;

public class DwellingFactory implements BuildingFactory {

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
        return new DwellingFloor(spaceCount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new DwellingFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorCount, int[] spaceCounts) {
        return new Dwelling(floorCount, spaceCounts);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new Dwelling(floors);
    }
}
