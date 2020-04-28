package threads;

import interfaces.Floor;

public class SequentalRepairer extends Thread implements Runnable {
    private FloorSemaphore semaphore;
    private Floor floor;

    public SequentalRepairer (FloorSemaphore semaphore, Floor floor) {
        this.semaphore = semaphore;
        this.floor = floor;
    }

    @Override
    public void run() {
        for (int i = 0; i < floor.getSpaceCount(); ++i) {
            semaphore.beginRepairing();
            System.out.println("Repairing space number " + i + " with total area " + floor.getSpaceByIndex(i).getArea() + " square meters.");
            semaphore.endRepairing();
        }
        System.out.println("Repairing of the floor completed");
    }


}
