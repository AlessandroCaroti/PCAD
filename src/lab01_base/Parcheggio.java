package lab01_base;
/*
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
*/

public class Parcheggio {

    static int c = 0;
    final private int serial;
    final private int MAXP;
    private int CURRP;
    private boolean close;

    //private Lock lock = new ReentrantLock();
    //private Condition isFull = lock.newCondition();


    public Parcheggio(int maxp)
    {
        Parcheggio.c++;
        serial = c;
        MAXP = maxp;
        CURRP = 0;
        close = false;
    }

    synchronized public void enter(Auto a)
    {
        if(close) {
            System.out.println("Il parcheggio è chiuso!");
            return;
        }
        if(a.isIn(serial)) {
            System.out.println("Sei già nel parcheggio");
            return;
        }
        if (CURRP < MAXP) {
            CURRP++;
        }
        else {
            try {
                while (CURRP >= MAXP) {
                    System.out.println("\t\t\t\tin attesa di un posto");
                    wait();
                }
                    CURRP++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        a.sonoEntrato(serial);
    }

    synchronized public void exit(Auto a)
    {
        if(!a.isIn(serial))
        {
            System.out.println("Non puoi uscire da un parcheggio in cui non sei dentro");
            return;
        }
        CURRP--;
        a.sonoUscito();
        notifyAll();
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
