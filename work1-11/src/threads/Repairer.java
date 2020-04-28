package threads;

import interfaces.Floor;

public class Repairer extends Thread {
    private Floor floor;

    public Repairer(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        for (int i = 0; i < floor.getSpaceCount(); ++i)
            System.out.println("Repairing space number " + i + " with total area " + floor.getSpaceByIndex(i).getArea() + " square meters.");
        System.out.println("Repairing of the floor completed");
    }
}
