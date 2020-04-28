package office;

import interfaces.Floor;
import interfaces.Space;
import exceptions.SpaceIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Iterator;

public class OfficeFloor implements Floor {

    private class Node implements Serializable {
        Space space;
        Node next;
        Node(Space space) {
            this.space = space;
        }
    }

    private Node head;
    private int spaceCount;

    private Node getNodeByIndex(int index) {
        Node node = head;
        for(int i = 0; i < index; ++i)
            node = node.next;
        return node;
    }

    private void addNodeByIndex(int index, Node newNode) {
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node currNode = head;
            for (int i = 0; i < index - 1; ++i)
                currNode = currNode.next;
            newNode.next = currNode.next;
            currNode.next = newNode;
        }
    }

    private void deleteNodeByIndex(int index) {
        if(index == 0) {
            head = head.next;
            getNodeByIndex(spaceCount - 1).next = head;
        } else {
            Node node = head;
            for (int i = 0; i < index - 1; ++i)
                node = node.next;
            node.next = node.next.next;
        }
    }

    public OfficeFloor(int spaceCount) {
        head = new Node(new Office());
        Node node = head;
        for(int i = 1; i < spaceCount; ++i) {
            node.next = new Node(new Office());
            node = node.next;
        }
        node.next = head;
        this.spaceCount = spaceCount;
    }

    public OfficeFloor(Space...spaces) {
        head = new Node(spaces[0]);
        Node node = head;
        for(int i = 1; i < spaces.length; ++i) {
            node.next = new Node(spaces[i]);
            node = node.next;
        }
        node.next = head;
        this.spaceCount = spaces.length;
    }

    public int getSpaceCount() {
        return spaceCount;
    }

    public double getTotalSpaceArea() {
        double totalAreaOfSpaces = 0;
        Node node = head;
        for (int i = 0; i < spaceCount; ++i) {
            totalAreaOfSpaces += node.space.getArea();
            node = node.next;
        }
        return totalAreaOfSpaces;
    }

    public int getTotalRoomCount() {
        int totalRoomCount = 0;
        Node node = head;
        for (int i = 0; i < spaceCount; ++i) {
            totalRoomCount += node.space.getRoomCount();
            node = node.next;
        }
        return totalRoomCount;
    }

    public Space[] getSpaces() {
        Space[] spaces = new Space[spaceCount];
        Node node = head;
        for(int i = 0; i < spaceCount; ++i) {
            spaces[i] = node.space;
            node = node.next;
        }
        return spaces;
    }

    public Space getSpaceByIndex(int index) {
        if (index < 0 || spaceCount <= index)
            throw new SpaceIndexOutOfBoundsException();
        return getNodeByIndex(index).space;
    }

    public void setSpaceByIndex(int index, Space space) {
        if (index < 0 || spaceCount <= index)
            throw new SpaceIndexOutOfBoundsException();
        getNodeByIndex(index).space = space;
    }

    public void addSpaceByIndex(int index, Space space) {
        if (index < 0 || spaceCount < index)
            throw new SpaceIndexOutOfBoundsException();
        addNodeByIndex(index, new Node(space));
        ++spaceCount;
    }

    public void deleteSpaceByIndex(int index) {
        if (index < 0 || spaceCount <= index)
            throw new SpaceIndexOutOfBoundsException();
        deleteNodeByIndex(index);
        --spaceCount;
    }

    public Space getBestSpace() {
        Node node = head;
        Space bestSpace = node.space;
        for(int i = 1; i < spaceCount; ++i) {
            node = node.next;
            if(node.space.getArea() > bestSpace.getArea())
                bestSpace = node.space;
        }
        return bestSpace;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OfficeFloor");
        Node node = head;
        for (int i = 0; i < getSpaceCount(); ++i) {
            builder.append(" ");
            builder.append(node.space.toString());
            node = node.next;
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof OfficeFloor))
            return false;
        OfficeFloor officeFloor = (OfficeFloor) obj;
        if (officeFloor.spaceCount != spaceCount)
            return false;
        Node node1 = head;
        Node node2 = officeFloor.head;
        for (int i = 0; i < spaceCount; ++i) {
            if (!node1.space.equals(node2.space))
                return false;
            node1 = node1.next;
            node2 = node2.next;
        }
        return true;

    }

    @Override
    public int hashCode() {
        int h = spaceCount;
        Node node = head;
        for (int i = 0; i < spaceCount; ++i) {
            h ^= node.space.hashCode();
            node = node.next;
        }
        return h;
    }

    @Override
    public Object clone() {
        Space[] spaces = new Space[spaceCount];
        Node node = head;
        for (int i = 0; i < spaceCount; ++i) {
            spaces[i] = (Space) node.space.clone();
            node = node.next;
        }
        return new OfficeFloor(spaces);
    }

    @Override
    public Iterator<Space> iterator() {
        return new Iterator<>() {
            Node node = head;
            int index = 0;
            @Override
            public boolean hasNext() {
                return (index < spaceCount);
            }

            @Override
            public Space next() {
                Space space = node.space;
                node = node.next;
                ++index;
                return space;
            }
        };
    }

    @Override
    public int compareTo(Floor o) {
        return spaceCount - o.getSpaceCount();
    }
}
