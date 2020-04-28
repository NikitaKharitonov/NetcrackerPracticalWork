package factories;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import office.Office;
import office.OfficeBuilding;
import office.OfficeFloor;

public class OfficeFactory implements BuildingFactory {
    @Override
    public Space createSpace(double area) {
        return new Office(area);
    }

    @Override
    public Space createSpace(double area, int roomCount) {
        return new Office(area, roomCount);
    }

    @Override
    public Floor createFloor(int spaceCount) {
        return new OfficeFloor(spaceCount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new OfficeFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorCount, int[] spaceCounts) {
        return new OfficeBuilding(floorCount, spaceCounts);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new OfficeBuilding(floors);
    }
}
