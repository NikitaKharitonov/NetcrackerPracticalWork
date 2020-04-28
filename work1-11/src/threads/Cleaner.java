package threads;

import interfaces.Floor;

public class Cleaner extends Thread {
    private Floor floor;

    public Cleaner(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        for (int i = 0; i < floor.getSpaceCount(); ++i)
            System.out.println("Cleaning room number " + i + " with total area " + floor.getSpaceByIndex(i).getArea() + " square meters.");
        System.out.println("Cleaning of the floor completed.");
    }
}
