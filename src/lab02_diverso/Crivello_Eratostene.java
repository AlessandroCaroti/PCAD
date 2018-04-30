package lab02_diverso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Crivello_Eratostene implements Runnable{
    private int myNum;
    private int prev_primeNum;

    static private boolean isRunning;
    static private int limit;
    static private int sqrt_limit;
    static private boolean[] is_prime;
    static private AtomicInteger cont = new AtomicInteger(1);
    static private ExecutorService threadExecutor;
    static private long start_time;
    static private long end_time;
    static private final Lock lock = new ReentrantLock();
    static private final Condition cond = lock.newCondition();

    Crivello_Eratostene(int i) { prev_primeNum = i; }

    static void initExecutor(Integer n, ExecutorService e)
    {
        is_prime = initArray(n);
        limit = n;
        sqrt_limit = (int) Math.sqrt(limit);
        cont.set(1);
        isRunning = true;
        threadExecutor = e;
        start_time = System.currentTimeMillis();
    }

    private static boolean[] initArray(int n)
    {
        boolean[] ar = new boolean[n];
        ar[1] = ar[2]=true;
        for (int i = 3 ; i<n ; i+=2)
            ar[i] = true;
        return ar;
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
        } else {
            countRemaining();
            end_time = System.currentTimeMillis();
            StopPool();
            lock.lock();
            isRunning = false;
            cond.signal();
            lock.unlock();
            System.out.println("TASK FINITO:\nlimit: "+limit+"\ncount: "+cont.get()+"\nExecutionTime: "+getExecutionTime()+"ms");
        }
    }

    private void algorithm()
    {
        for (int i = myNum * myNum; i<is_prime.length;i+=myNum)
            is_prime[i] = false;
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
        threadExecutor.shutdown();
        try {
            if (!threadExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
                threadExecutor.shutdownNow(); // Cancel currently executing tasks
                if (!threadExecutor.awaitTermination(10, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            threadExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    static private void waitTerminatio(){
        try {
            lock.lock();
            while (isRunning)
                    cond.await();
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //GETTER METHOD
    static public int getLimit() { return limit;}
    static int getCount() {waitTerminatio(); return cont.get();}
    static long getExecutionTime() {waitTerminatio(); return end_time-start_time;}
    static String getExecutionTimeString(boolean min, boolean sec)
    {
        long res = getExecutionTime();
        long res_min = TimeUnit.MILLISECONDS.toMinutes(res);
        long res_sec = TimeUnit.MILLISECONDS.toSeconds(res);
        long res_mill = res - 1000*res_sec;
        if(min)
        {
            return res_min + " min, "+res_sec+" sec, " + res_mill + " ms";
        }
        else if(sec)
            return res_min*60+res_sec+" sec," + res_mill + " ms";
        return res+" ms";
    }

    static private void printNumbers()
    {
        for (int i = 0; i <is_prime.length;i++)
            if(is_prime[i])
                System.out.print(i + " ");
    }


}
