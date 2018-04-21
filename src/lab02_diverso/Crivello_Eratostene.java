package lab02_diverso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Crivello_Eratostene implements Runnable{
    static private int limit;
    static private int sqrt_limit;
    static private boolean[] is_prime;
    static private AtomicInteger cont = new AtomicInteger(1);
    static private ExecutorService threadExecutor;
    static long start_time;
    static long end_time;
    private int prev_primeNum;
    private int myNum;

    Crivello_Eratostene(int i)
    {
        prev_primeNum = i;
    }

    private void algorithm()
    {
        for (int i = myNum * myNum; i<is_prime.length;i+=myNum)
            is_prime[i] = false;
    }

    @Override
    public void run()
    {
        while (prev_primeNum <= limit && !is_prime[prev_primeNum]) prev_primeNum++;
        myNum = prev_primeNum;
        cont.incrementAndGet();
        if(myNum < sqrt_limit)
        {
            Runnable nextWorker = new Crivello_Eratostene(prev_primeNum + 1);
            threadExecutor.execute(nextWorker);
            algorithm();
        }
        else {
            countRemaining();
            end_time = System.currentTimeMillis();
//            printNumbers();
            printCount();
            StopPool();
        }
    }

    static private void printCount()
    {
        System.out.println("\nFind " + cont.get() + " prime numbers");
        //stampa del tempo di escuzione
        long res = (end_time-start_time);
        long res_sec = TimeUnit.MILLISECONDS.toSeconds(res);
        long res_mill = res - 1000*res_sec;
        System.out.println("\nExecution time: " +res_sec+"sec "+ res_mill+"ms");
    }
    static private void printNumbers()
    {
        for (int i = 0; i <is_prime.length;i++)
            if(is_prime[i])
                System.out.print(i + " ");
    }

    static void initExecutor(boolean[] b, Integer n, ExecutorService e)
    {
        threadExecutor = e;
        is_prime = b;
        limit = n;
        double d = n.doubleValue();
        sqrt_limit = (int) Math.sqrt((d));
        start_time = System.currentTimeMillis();
    }

    private void countRemaining()
    {
        int aux = prev_primeNum+1;
        while (aux<is_prime.length) {
            if (is_prime[aux])
                cont.incrementAndGet();
            aux++;
        }
    }

    private void StopPool()
    {
        threadExecutor.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!threadExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
                threadExecutor.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!threadExecutor.awaitTermination(10, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            threadExecutor.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
