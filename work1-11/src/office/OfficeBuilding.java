package office;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import exceptions.FloorIndexOutOfBoundsException;
import exceptions.SpaceIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Iterator;

public class OfficeBuilding implements Building {

    private class Node implements Serializable {
        Floor floor;
        Node next;
        Node prev;
        Node(Floor floor) {
            this.floor = floor;
        }
    }

    private Node head;
    private int floorCount;

    private Node getNodeByIndex(int index) {
        Node node = head;
        for(int i = 0; i < index; ++i)
            node = node.next;
        return node;
    }

    private void addNodeByIndex(int index, Node newNode) {
        if (index == 0) {
            newNode.next = head;
            newNode.prev = head.prev;
            head = newNode;
        } else {
            Node currNode = head;
            for (int i = 0; i < index - 1; ++i) {
                currNode = currNode.next;
            }
            newNode.next = currNode.next;
            newNode.prev = currNode;
            currNode.next.prev = newNode;
            currNode.next = newNode;
        }
    }

    private void deleteNodeByIndex(int index) {
        if(index == 0) {
            head.prev.next = head.next;
            head.next.prev = head.prev;
            head = head.next;
        } else {
            Node node = head;
            for (int i = 0; i < index - 1; ++i) {
                node = node.next;
            }
            node.next.next.prev = node;
            node.next = node.next.next;
        }
    }

    public OfficeBuilding(int floorCount, int...spaceCounts) {
        head = new Node(new OfficeFloor(spaceCounts[0]));
        Node node = head;
        for(int i = 1; i < floorCount; ++i) {
            node.next = new Node(new OfficeFloor(spaceCounts[i]));
            node.next.prev = node;
            node = node.next;
        }
        node.next = head;
        head.prev = node;
        this.floorCount = floorCount;
    }

    public OfficeBuilding(Floor...floors) {
        head = new Node(floors[0]);
        Node node = head;
        for(int i = 1; i < floors.length; ++i) {
            node.next = new Node(floors[i]);
            node.next.prev = node;
            node = node.next;
        }
        node.next = head;
        head.prev = node;
        floorCount = floors.length;
    }

    public int getFloorCount() {
        return floorCount;
    }

    public int getSpaceCount() {
        int spaceCount = 0;
        Node node = head;
        for (int i = 0; i < floorCount; ++i) {
            spaceCount += node.floor.getSpaceCount();
            node = node.next;
        }
        return spaceCount;
    }

    public double getTotalSpaceArea() {
        double totalAreaOfOffices = 0;
        Node node = head;
        for (int i = 0; i < floorCount; ++i) {
            totalAreaOfOffices += node.floor.getTotalSpaceArea();
            node = node.next;
        }
        return totalAreaOfOffices;
    }

    public int getTotalRoomCount() {
        int totalRoomCount = 0;
        Node node = head;
        for (int i = 0; i < floorCount; ++i) {
            totalRoomCount += node.floor.getTotalRoomCount();
            node = node.next;
        }
        return totalRoomCount;
    }

    public Floor[] getFloors() {
        Floor[] floors = new Floor[floorCount];
        Node node = head;
        for (int i = 0; i < floorCount; ++i) {
            floors[i] = node.floor;
            node = node.next;
        }
        return floors;
    }

    public Floor getFloorByIndex(int index) {
        if (index < 0 || floorCount <= index)
            throw new FloorIndexOutOfBoundsException();
        return getNodeByIndex(index).floor;
    }

    public void setFloorByIndex(int index, Floor floor) {
        if (index < 0 || floorCount <= index)
            throw new FloorIndexOutOfBoundsException();
        getNodeByIndex(index).floor = floor;
    }

    public Space getSpaceByIndex(int index) {
        if (index < 0 || getSpaceCount() <= index)
            throw new SpaceIndexOutOfBoundsException();
        Node node = head;
        for (int i = 0; i < floorCount; ++i) {
            if (index > node.floor.getSpaceCount())
                index -= node.floor.getSpaceCount();
            else
                return node.floor.getSpaceByIndex(index);
        }
        return null;
    }

    public void setSpaceByIndex(int index, Space space) {
        if (index < 0 || getSpaceCount() <= index)
            throw new SpaceIndexOutOfBoundsException();
        Node node = head;
        for (int i = 0; i < floorCount; ++i) {
            if (index > node.floor.getSpaceCount())
                index -= node.floor.getSpaceCount();
            else
                node.floor.setSpaceByIndex(index, space);
        }

    }

    public void addSpaceByIndex(int index, Space space) {
        if (index < 0 || getSpaceCount() < index)
            throw new SpaceIndexOutOfBoundsException();
        Node node = head;
        for (int i = 0; i < floorCount; ++i) {
            if (index > node.floor.getSpaceCount())
                index -= node.floor.getSpaceCount();
            else {
                node.floor.addSpaceByIndex(index, space);
                break;
            }
        }
        ++floorCount;

    }

    public void deleteSpaceByIndex(int index) {
        if (index < 0 || getSpaceCount() <= index)
            throw new SpaceIndexOutOfBoundsException();
        Node node = head;
        for (int i = 0; i < floorCount; ++i) {
            if (index > node.floor.getSpaceCount())
                index -= node.floor.getSpaceCount();
            else {
                node.floor.deleteSpaceByIndex(index);
                break;
            }
        }
        --floorCount;
    }

    public Space getBestSpace() {
        Node node = head;
        Space bestSpace = node.floor.getBestSpace();
        for (int i = 1; i < floorCount; ++i) {
            node = node.next;
            if (node.floor.getBestSpace().getArea() > bestSpace.getArea())
                bestSpace = node.floor.getBestSpace();
        }
        return bestSpace;
    }

    public Space[] getSpacesByAreaDescending() {
        // get array of spaces
        Space[] spaces = new Space[getSpaceCount()];
        Node node = head;
        int destPos = 0;
        for (int i = 0; i < floorCount; ++i) {
            System.arraycopy(node.floor.getSpaces(), 0, spaces, destPos, node.floor.getSpaceCount());
            destPos += node.floor.getSpaceCount();
            node = node.next;
        }
        // bubble sort
        boolean isChange;
        do {
            isChange = false;
            for (int i = 1; i < spaces.length; ++i) {
                if(spaces[i - 1].getArea() < spaces[i].getArea()) {
                    Space temp = spaces[i - 1];
                    spaces[i - 1] = spaces[i];
                    spaces[i] = temp;
                    isChange = true;
                }
            }
        } while (isChange);
        return spaces;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("OfficeBuilding");
        Node node = head;
        for (int i = 0; i < floorCount; ++i) {
            builder.append(" ");
            builder.append(node.floor.toString());
            node = node.next;
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof OfficeBuilding))
            return false;
        OfficeBuilding officeBuilding = (OfficeBuilding) obj;
        if (officeBuilding.floorCount != floorCount)
            return false;
        Node node1 = head;
        Node node2 = officeBuilding.head;
        for (int i = 0; i < floorCount; ++i)
            if (!node1.floor.equals(node2.floor))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        int h = floorCount;
        Node node = head;
        for (int i = 0; i < floorCount; ++i) {
            h ^= node.floor.hashCode();
            node = node.next;
        }
        return h;
    }

    @Override
    public Object clone() {
        Floor[] floors = new Floor[floorCount];
        Node node = head;
        for (int i = 0; i < floorCount; ++i) {
            floors[i] = (Floor) node.floor.clone();
            node = node.next;
        }
        return new OfficeBuilding(floors);
    }

    @Override
    public Iterator<Floor> iterator() {
        return new Iterator<>() {
            Node node = head;
            int index = 0;

            @Override
            public boolean hasNext() {
                return (index < floorCount);
            }

            @Override
            public Floor next() {
                Floor floor = node.floor;
                node = node.next;
                ++index;
                return floor;
            }
        };
    }
}
