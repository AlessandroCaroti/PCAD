package lab02_crivelloErastotene;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestClass {
    static final private int nThreads = 6;//6
    public static  void main(String[] args)
    {
        int N;
        long start_time, stop_time;
        Runnable fst_task;
        if(args.length < 1) {
            System.out.println("Numero di argomenti errati");
            return;
        }

        N = getMax(args[0]);
        LinkedBlockingQueue<Integer> allNum = initQueue(N);
        ExecutorService threadExecutor = Executors.newFixedThreadPool(nThreads);
        Crivello_Eratostene.initExecutor(threadExecutor, N);
        fst_task = new Crivello_Eratostene(allNum);
        start_time = System.currentTimeMillis();
        threadExecutor.execute(fst_task);
        try {
            threadExecutor.awaitTermination(15, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop_time = System.currentTimeMillis();
        long res = (stop_time-start_time);
        long res_sec = TimeUnit.MILLISECONDS.toSeconds(res);
        long res_mill = res - 1000*res_sec;
        System.out.println("\nExecution time: " +res_sec+"sec "+ res_mill+"ms");
    }

    private static Integer getMax(String num)
    {
        Integer N = 0;
        try {
            N = Integer.parseInt(num);
        }
        catch (NumberFormatException e){
            System.out.println("Formato argomento 1 errato");
            System.exit(-1);
        }
        if(N<2) {
            System.out.println("Il numero passato deve essere > 2");
            System.exit(-1);
        }
        return N;
    }

    private static LinkedBlockingQueue initQueue(int n)
    {
        LinkedBlockingQueue<Integer> lbq = new LinkedBlockingQueue<>();
        try {
            for (int i = 3;i <n;i+=2)
                lbq.put(i);
            lbq.put(-1); }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lbq;
    }
}
