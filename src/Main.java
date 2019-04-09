import com.oocourse.TimableOutput;

public class Main {
    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();
        QueueAll queue = new QueueAll();
        Thread1 input = new Thread1(queue);
        Thread2 output = new Thread2(queue);
        input.start();
        output.start();
        try {
            input.join();
            output.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
