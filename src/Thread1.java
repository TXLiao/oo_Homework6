import com.oocourse.elevator2.ElevatorInput;
import com.oocourse.elevator2.PersonRequest;

public class Thread1 extends Thread {
    private final QueueAll temp;

    public Thread1(QueueAll m) {
        this.temp = m;
    }

    public synchronized void run() {
        temp.init();
        try {
            ElevatorInput elevatorInput = new ElevatorInput(System.in);
            while (true) {
                PersonRequest request = elevatorInput.nextPersonRequest();
                if (request == null) {
                    break;
                } else {
                    int j = 0;
                    if (request.getFromFloor() < request.getToFloor()) {
                        j = 1;
                    }
                    temp.setPerson(request.getFromFloor(),j,request);
                }
            }
            Thread.sleep(200);
            temp.setInputEnd(true);
            Thread.sleep(200);
            elevatorInput.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
