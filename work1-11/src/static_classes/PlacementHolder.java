package static_classes;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import exceptions.InexchangeableFloorsException;
import exceptions.InexchangeableSpacesException;
import exceptions.SpaceIndexOutOfBoundsException;

public class PlacementHolder {

    private static boolean isExchangeable(Space space1, Space space2) {
        return (Double.compare(space1.getArea(), space2.getArea()) == 0
        && space1.getRoomCount() == space2.getRoomCount());
    }

    private static boolean isExchangeable(Floor floor1, Floor floor2) {
        return (Double.compare(floor1.getTotalSpaceArea(), floor2.getTotalSpaceArea()) == 0
                && floor1.getTotalRoomCount() == floor2.getTotalRoomCount());
    }

    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2, int index2) throws InexchangeableSpacesException, SpaceIndexOutOfBoundsException {
        if ((index1 < 0 || floor1.getSpaceCount() <= index1) || (index2 < 0 || floor2.getSpaceCount() <= index2))
            throw new SpaceIndexOutOfBoundsException();
        if (!isExchangeable(floor1.getSpaceByIndex(index1), floor2.getSpaceByIndex(index2)))
            throw new InexchangeableSpacesException();
        Space temp = floor1.getSpaceByIndex(index1);
        floor1.setSpaceByIndex(index1, floor2.getSpaceByIndex(index2));
        floor2.setSpaceByIndex(index2, temp);
    }

    public static void exchangeBuildingFloors(Building building1, int index1, Building building2, int index2) throws InexchangeableFloorsException, SpaceIndexOutOfBoundsException {
        if ((index1 < 0 || building1.getSpaceCount() <= index1) || (index2 < 0 || building2.getSpaceCount() <= index2))
            throw new SpaceIndexOutOfBoundsException();
        if (!isExchangeable(building1.getFloorByIndex(index1), building2.getFloorByIndex(index2)))
            throw new InexchangeableFloorsException();
        Floor temp = building1.getFloorByIndex(index1);
        building1.setFloorByIndex(index1, building2.getFloorByIndex(index2));
        building2.setFloorByIndex(index2, temp);
    }
}
