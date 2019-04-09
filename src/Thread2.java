import com.oocourse.elevator2.PersonRequest;

public class Thread2 extends Thread {
    private final QueueAll temp;
    private int stop = 1;
    private int upOrDown = 1;   //up 1   down -1
    private ElevatorRun elevator = new ElevatorRun();

    public Thread2(QueueAll m) {
        this.temp = m;
    }

    public synchronized void run() {
        try {
            while (!temp.getInputEnd()) {
                Thread.sleep(1);
                while (!temp.isEmptyQueue()) {
                    PersonRequest current = temp.getPerson(stop,upOrDown);
                    while (current == null) {
                        upOrDown = temp.getInitPerson(stop);
                        elevator.elevatorMove(upOrDown);
                        stop = elevator.getStop();
                        current = temp.getPerson(stop,upOrDown);
                        if (current == null) {
                            current = temp.getPerson(stop,-upOrDown);
                            if (current != null) {
                                upOrDown = -upOrDown;
                            }
                        }
                        changeUpDown();
                    }
                    temp.subCount();
                    elevator.doorOpen(stop);
                    elevator.elevatorGetIn(current);

                    moreThanOne(stop);
                    elevator.doorClose(stop);
                    while (!elevator.isEmptyElevator()) {
                        elevator.elevatorMove(upOrDown);
                        stop = elevator.getStop();
                        judgeFloor(stop,upOrDown);
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

    void judgeFloor(int i,int direction) {
        try {
            PersonRequest a = temp.getPerson(i, direction);
            if (a != null) {
                elevator.doorOpen(i);
                elevator.elevatorGetIn(a);
                temp.subCount();
                moreThanOne(stop);
                elevator.doorClose(i);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void moreThanOne(int i) {
        try {
            while (!temp.isEmptyFloor(i)) {
                PersonRequest current = temp.getPerson(i, upOrDown);
                if (current != null) {
                    temp.subCount();
                    elevator.elevatorGetIn(current);
                }
            }
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
