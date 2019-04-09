import com.oocourse.TimableOutput;
import com.oocourse.elevator2.PersonRequest;

import java.util.ArrayList;

public class ElevatorRun {
    private static int stop = 1;
    static final long OPEN = 200;
    static final long CLOSE = 200;
    static final long PERFLOOR = 400;

    private static boolean outputState = false;
    private ArrayList<PersonRequest> elevatorQueue =
            new ArrayList<PersonRequest>();

    synchronized void elevatorGetIn(PersonRequest a) {
        if (a != null) {
            elevatorQueue.add(a);
            moveIn(stop, a.getPersonId());
        }
    }

    synchronized void elevatorMove(int upOrDown) {
        if (upOrDown == 1) {
            elevatorUp();
        } else {
            elevatorDown();
        }
        if (!elevatorQueue.isEmpty()) {
            int check = checkFloor(stop);
            PersonRequest a;
            if (check != -1) {
                a = elevatorQueue.get(check);
                doorOpen(stop);
                moveOut(stop, a.getPersonId());
                elevatorQueue.remove(check);
                check = checkFloor(stop);
                while (check != -1) {
                    a = elevatorQueue.get(check);
                    moveOut(stop, a.getPersonId());
                    elevatorQueue.remove(check);
                    check = checkFloor(stop);
                }
                doorClose(stop);
            }
        }
    }

    void elevatorUp() {
        stop = stop + 1;
        if (stop == 0) {
            stop++;
        }
        elevatorArrive(stop);
    }

    void elevatorDown() {
        stop = stop - 1;
        if (stop == 0) {
            stop--;
        }
        elevatorArrive(stop);
    }

    synchronized void elevatorArrive(int a) {
        try {
            Thread.sleep(PERFLOOR);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        TimableOutput.println("ARRIVE-" + a);
    }

    synchronized void doorOpen(int stop) {
        TimableOutput.println("OPEN-" + stop);
        try {
            Thread.sleep(OPEN);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    synchronized void doorClose(int stop) {
        try {
            Thread.sleep(CLOSE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        TimableOutput.println("CLOSE-" + stop);
    }

    void moveIn(int stop,int id) {
        TimableOutput.println(String.format("IN-%d-%d", id, stop));
    }

    void moveOut(int stop,int id) {
        TimableOutput.println(String.format("OUT-%d-%d", id, stop));
    }

    public int getStop() {
        return stop;
    }

    int checkFloor(int stop) {
        int i = 0;
        while (i < elevatorQueue.size()) {
            PersonRequest a = elevatorQueue.get(i);
            if (stop == a.getToFloor()) {
                return i;
            } else {
                i++;
            }
        }
        return -1;
    }

    public boolean isEmptyElevator() {
        return elevatorQueue.isEmpty();
    }
}
