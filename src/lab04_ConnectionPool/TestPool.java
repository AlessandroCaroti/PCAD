package lab04_ConnectionPool;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TestPool {
    private static final String[] urlS = {  "http://www.google.it",
                                            "http://www.facebook.com",
                                            "http://www.google.it",
                                            "http://www.google.it",
                                            "http://www.google.it"};
    private static final Future<Integer>[] f = new Future[urlS.length];

    public static void main(String[] args)
    {
        ConnectionPool cp = new ConnectionPool(3);
        for (int i = 0;i<urlS.length;i++)
            f[i] = cp.OpenConnection(urlS[i]);
        try {
            for (int i = 0;i<urlS.length;i++)
            {
                Integer res = f[i].get();
                if(res!=null)
                    System.out.println("Status code " + res + " from " + urlS[i]);
                else
                    System.err.println("[WORNING-MAIN] No response code for " + urlS[i]);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        cp.StopPool();
    }
}
