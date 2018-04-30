package lab02_diverso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestClass {
    public static  void computePrimeNumber(String arg, int nThreads)
    {
        int N;
        ExecutorService threadExecutor;
        Runnable fst_task;
        N = strToInt(arg);
        fst_task = new Crivello_Eratostene(3);
        threadExecutor = Executors.newFixedThreadPool(nThreads);
        Crivello_Eratostene.initExecutor(N, threadExecutor);
        threadExecutor.execute(fst_task);
    }

    private static Integer strToInt(String num)
    {
        Integer N = 0;
        try {
            N = Integer.parseInt(num);
        }
        catch (NumberFormatException e){
            System.out.println("Formato argomento 1 errato");
            System.exit(-1);
        }
        if(N<2 || N > Integer.MAX_VALUE) {
            System.out.println("Il numero passato deve essere > 2 e < di " + Integer.MAX_VALUE);
            System.exit(-1);
        }
        return N;
    }

}
