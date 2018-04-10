package lab01_esteso;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Parcheggio {

    static int c = 0;
    final private int serial;
    final private int MAXP;
    private int CURRP;
    private boolean close;

    private final Lock lock = new ReentrantLock();
    private final Condition q1 = lock.newCondition();
    private final Condition q2 = lock.newCondition();


    public Parcheggio(int maxp)
    {
        Parcheggio.c++;
        serial = c;
        MAXP = maxp;
        CURRP = 0;
        close = false;
    }

    public void enter(Auto a)
    {
        if(close) {
            System.out.println("Il parcheggio è chiuso!");
            return;
        }
        if(a.isIn(serial)) {
            System.out.println("Sei già nel parcheggio");
            return;
        }
        lock.lock();
        if (CURRP < MAXP) {
            CURRP++;
        }
        else {
            try {
                while (CURRP >= MAXP) {
                    System.out.println("\t\t\t\tin attesa di un posto");
                    q1.await();
                }
                    CURRP++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
        a.sonoEntrato(serial);
    }

    public void exit(Auto a)
    {
        lock.lock();
        if(!a.isIn(serial))
        {
            System.out.println("Non puoi uscire da un parcheggio in cui non sei dentro");
            return;
        }
        CURRP--;
        a.sonoUscito();
        q1.signal();
        lock.unlock();
    }

    public void close()
    {
        close  = true;
    }

    public boolean isClose()
    {
        return  this.close;
    }

}
