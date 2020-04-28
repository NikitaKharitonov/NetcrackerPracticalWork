package threads;

class FloorSemaphore {

    enum State {CLEANING, REPAIRING}

    private State state = State.REPAIRING;

    synchronized void beginCleaning() {
        try {
            while (this.state != State.CLEANING)
                this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void endCleaning() {
        this.state = State.REPAIRING;
        this.notifyAll();
    }

    synchronized void beginRepairing() {
        try {
            while(this.state != State.REPAIRING) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void endRepairing() {
        this.state = State.CLEANING;
        this.notifyAll();
    }



}
