package lab02_diverso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestClass {
    static final private int nThreads = 10;
    public static  void main(String[] args)
    {
        int N;
        ExecutorService threadExecutor;
        Runnable fst_task;
        if(args.length < 1) {
            System.out.println("Numero di argomenti errati");
            return;
        }
        N = getMax(args[0]);
        fst_task = new Crivello_Eratostene(3);
        threadExecutor = Executors.newFixedThreadPool(nThreads);
        boolean[] aux = initArray(N);
        Crivello_Eratostene.initExecutor(aux, N, threadExecutor);
        threadExecutor.execute(fst_task);
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
        if(N<2 || N > Integer.MAX_VALUE) {
            System.out.println("Il numero passato deve essere > 2 e < di " + Integer.MAX_VALUE);
            System.exit(-1);
        }
        return N;
    }

    private static boolean[] initArray(int n)
    {
        boolean[] ar = new boolean[n];
        ar[1] = ar[2]=true;
        for (int i = 3 ; i<n ; i+=2)
            ar[i] = true;
        return ar;
    }
}
