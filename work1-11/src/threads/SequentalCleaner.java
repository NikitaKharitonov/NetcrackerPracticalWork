package threads;

import interfaces.Floor;

public class SequentalCleaner extends Thread implements Runnable {
    private FloorSemaphore semaphore;
    private Floor floor;

    public SequentalCleaner (FloorSemaphore semaphore, Floor floor) {
        this.semaphore = semaphore;
        this.floor = floor;
    }

    @Override
    public void run() {
        for (int i = 0; i < floor.getSpaceCount(); ++i) {
            semaphore.beginCleaning();
            System.out.println("Cleaning room number " + i + " with total area " + floor.getSpaceByIndex(i).getArea() + " square meters.");
            semaphore.endCleaning();
        }
        System.out.println("Cleaning of the floor completed.");
    }
}
