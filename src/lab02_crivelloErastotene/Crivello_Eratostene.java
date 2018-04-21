package lab02_crivelloErastotene;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

public class Crivello_Eratostene implements Runnable{
    static private LinkedBlockingQueue<Integer> PrimeNumber = new LinkedBlockingQueue<>();
    static private ExecutorService threadExecutor;
    static private Integer sqrt_limit;

    private Integer myNum;
    private LinkedBlockingQueue<Integer> queue_in;
    private LinkedBlockingQueue<Integer> queue_out;

    private Crivello_Eratostene(Crivello_Eratostene prev_thread)
    {
        queue_out = new LinkedBlockingQueue<>();
        queue_in = prev_thread.get_queue();
    }

    Crivello_Eratostene(LinkedBlockingQueue<Integer> q)
    {
        queue_out = new LinkedBlockingQueue<>();
        queue_in = q;
    }

    private LinkedBlockingQueue<Integer> get_queue()
    {
        return queue_out;
    }

    private void algorithm()
    {
        Integer curr;
        try {
            do {
                curr = queue_in.take();
                if(curr%myNum != 0)
                        queue_out.put(curr);
            }while (curr!=-1);
            queue_out.put(-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        myNum = getMyNum();
        try {
            PrimeNumber.put(myNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(myNum > sqrt_limit)
            insertAll();
        if(endOfQueue())
        {
            Crivello_Eratostene.printNumber();
            threadExecutor.shutdown();
        }
        else
        {
            Runnable next_task = new Crivello_Eratostene(this);
            threadExecutor.execute(next_task);
            algorithm();
        }
    }

    static private void printNumber()
    {
        System.out.println("Find " + PrimeNumber.size() + " prime numbers");
        /*
        try {
            while (!PrimeNumber.isEmpty())
                System.out.print(PrimeNumber.take() + " ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }

    private boolean endOfQueue()
    {
        while (queue_in.isEmpty());
        return queue_in.peek() == -1;
    }

    private Integer getMyNum()
    {
        Integer tmp = null;
        try {
            tmp = queue_in.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    private void insertAll()
    {
        Integer i;
        try {
            while ((i=queue_in.take()) != -1)
            {
                PrimeNumber.put(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void initExecutor(ExecutorService e, Integer n)
    {
        threadExecutor = e;
        double d = n.doubleValue();
        sqrt_limit = (int) Math.sqrt((d));
    }
}
