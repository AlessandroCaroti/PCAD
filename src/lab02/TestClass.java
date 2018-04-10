package lab02;

import java.util.concurrent.LinkedBlockingQueue;

public class TestClass {
    public static  void main(String[] args)
    {
        int N;
        Crivello_Eratostene fst_thread;
        if(args.length < 1)
        {
            System.out.println("Numero di argomenti errati");
            return;
        }
        try {
            N = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e){
            System.out.println("Formato argomento 1 errato");
            return;
        }
    //        System.out.println(N);
        LinkedBlockingQueue<Integer> allNum = new LinkedBlockingQueue<>();
        for (Integer i = 0; i<N;i++) {
            try {
                allNum.put(i);
            } catch (InterruptedException e) {
                System.out.println("Main_error:");
                e.printStackTrace();
            }
        }
        fst_thread = new Crivello_Eratostene(null);
        fst_thread.start();
    }
}
