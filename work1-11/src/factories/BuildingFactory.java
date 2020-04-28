package factories;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;

public interface BuildingFactory {
    Space createSpace(double area);
    Space createSpace(double area, int roomCount);
    Floor createFloor(int spaceCount);
    Floor createFloor(Space[] spaces);
    Building createBuilding(int floorCount, int[] spaceCounts);
    Building createBuilding(Floor[] floors);
}
