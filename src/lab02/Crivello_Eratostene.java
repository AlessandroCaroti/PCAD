package lab02;
import java.util.concurrent.LinkedBlockingQueue;

public class Crivello_Eratostene extends Thread{
    static private LinkedBlockingQueue<Integer> PrimeNumber = new LinkedBlockingQueue<Integer>();
    final private Integer myNum;
    private LinkedBlockingQueue<Integer> queue_in;
    private LinkedBlockingQueue<Integer> queue_out;

    public Crivello_Eratostene(Crivello_Eratostene prev_thread)
    {
        Integer tmp = null;
        queue_out = new LinkedBlockingQueue<Integer>();
        queue_in = prev_thread.get_queue();
        try {
            tmp = queue_in.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(tmp == null)
            myNum = tmp;
        else {
            myNum = 0;
            throw new NullPointerException();
        }
        try {
            PrimeNumber.put(myNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private LinkedBlockingQueue<Integer> get_queue()
    {
        return queue_out;
    }

    private void algorithm()
    {
        Integer curr=0;
        do {
            try {
                curr = queue_in.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(curr%myNum != 0) {
                try {
                    queue_out.put(curr);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }while (curr!=-1);
        try {
            queue_out.put(-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        System.out.println("[ThreadInfo_START]: " + myNum);
        algorithm();
        System.out.println("[ThreadInfo_END]: " + myNum);
    }
    public void printNumber()
    {
        int c= 0;
        while (!PrimeNumber.isEmpty())
        {
            c++;
            try {
                System.out.print(PrimeNumber.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(c==15)
            {
                System.out.println();
                c=0;
            }
        }
    }
}
