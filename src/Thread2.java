import com.oocourse.elevator2.PersonRequest;

public class Thread2 extends Thread {
    private final QueueAll temp;
    private int stop = 1;
    private int upOrDown = 1;   //up 1   down -1

    public Thread2(QueueAll m) {
        this.temp = m;
    }

    public synchronized void run() {
        try {
            while (!temp.getInputEnd()) {
                Thread.sleep(1);
                while (!temp.isEmptyQueue()) {
                    PersonRequest current = temp.getPerson(stop,upOrDown);
                    ElevatorRun elevator = new ElevatorRun();
                    while (current == null) {
                        elevator.elevatorMove(upOrDown);
                        stop = elevator.getStop();
                        current = temp.getPerson(stop,upOrDown);
                        changeUpDown();
                    }
                    temp.subCount();
                    elevator.doorOpen(stop);
                    elevator.elevatorGetIn(current);
                    while (!temp.isEmptyFloor(stop)) {
                        current = temp.getPerson(stop,upOrDown);
                        temp.subCount();
                        elevator.elevatorGetIn(current);
                    }
                    elevator.doorClose(stop);
                    while (!elevator.isEmptyElevator()) {
                        elevator.elevatorMove(upOrDown);
                        stop = elevator.getStop();
                        changeUpDown();
                    }
                }
                changeUpDown();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void changeUpDown() {
        if (stop == 16) {
            upOrDown = -1;
        }
        if (stop == -3) {
            upOrDown = 1;
        }
    }

}
