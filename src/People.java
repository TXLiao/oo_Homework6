import com.oocourse.elevator2.PersonRequest;

import java.util.LinkedList;
import java.util.Queue;

public class People {
    private static boolean inputState = false;
    private Queue<PersonRequest> person =
            new LinkedList<PersonRequest>();

    public synchronized PersonRequest getPerson() throws Exception {
        notifyAll();
        return person.poll();
    }

    public synchronized void setPerson(PersonRequest a) throws Exception {
        person.offer(a);
        notifyAll();
    }

    public boolean isEmpty() {
        return person.isEmpty();
    }
}
