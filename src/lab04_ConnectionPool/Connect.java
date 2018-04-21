package lab04_ConnectionPool;

import java.util.concurrent.Callable;

public class Connect implements Callable<Integer> {
    private String url;
    public Connect(String s)
    {
        url = s;
    }

    @Override
    public Integer call() throws Exception {
        return null;
    }
}
