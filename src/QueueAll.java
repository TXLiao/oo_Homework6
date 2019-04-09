import com.oocourse.elevator2.PersonRequest;

public class QueueAll {
    private static People[] queueUp = new People[19];
    private static People[] queueDown = new People[19];
    private static int count = 0;
    private Boolean inputEnd = false;

    public void init() {
        int i = 0;
        for (i = 0;i < 19;i++) {
            queueUp[i] = new People();
            queueDown[i] = new People();
        }
    }

    public synchronized PersonRequest getPerson(int i, int j) throws Exception {
        notifyAll();
        if (j == 1) {
            if (i < 0) {
                return queueUp[i + 3].getPerson();
            } else {
                return queueUp[i + 2].getPerson();
            }
        } else {
            if (i < 0) {
                return queueDown[i + 3].getPerson();
            } else {
                return queueDown[i + 2].getPerson();
            }
        }
    }

    public synchronized void
        setPerson(int i, int j, PersonRequest a) throws Exception {
        notifyAll();
        if (j == 1) {
            count++;
            if (i < 0) {
                queueUp[i + 3].setPerson(a);
            } else {
                queueUp[i + 2].setPerson(a);
            }
        } else {
            count++;
            if (i < 0) {
                queueDown[i + 3].setPerson(a);
            } else {
                queueDown[i + 2].setPerson(a);
            }
        }
    }

    public boolean isEmptyQueue() {
        return count == 0;
    }

    public void setInputEnd(boolean a) {
        inputEnd = a;
    }

    public boolean getInputEnd() {
        return inputEnd;
    }

    public synchronized boolean isEmptyFloor(int stop) {
        if (stop < 0) {
            return (queueUp[stop + 3].isEmpty() &&
                    queueDown[stop + 3].isEmpty());
        } else {
            return (queueUp[stop + 2].isEmpty() &&
                    queueDown[stop + 2].isEmpty());
        }
    }


    public synchronized boolean isEmptyFloorEach(int stop,int i) {
        if (i > 0) {
            if (stop < 0) {
                return queueUp[stop + 3].isEmpty();
            } else {
                return queueUp[stop + 2].isEmpty();
            }
        } else {
            if (stop < 0) {
                return queueDown[stop + 3].isEmpty();
            } else {
                return queueDown[stop + 2].isEmpty();
            }
        }
    }


    public void subCount() {
        count--;
    }

    public int getInitPerson(int stop) {
        int  i;
        int changeStop;
        if (stop < 0) {
            changeStop = stop + 3;
        } else {
            changeStop = stop + 2;
        }
        for (i = 0; i < 19; i++) {
            if (!(queueUp[i].isEmpty() && queueDown[i].isEmpty())) {
                break;
            }
        }
        if (i < changeStop) {
            return -1;
        } else {
            return 1;
        }
    }
}
