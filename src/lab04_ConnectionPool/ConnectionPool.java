package lab04_ConnectionPool;

import java.net.*;
import java.util.concurrent.*;

public class ConnectionPool {
    private ExecutorService pool;
    private boolean stopped;
    private ConcurrentHashMap<String,HttpURLConnection> cache;

    ConnectionPool()
    {
        pool = Executors.newCachedThreadPool()  ;
        stopped = false;
        cache = new ConcurrentHashMap<>();
    }

    ConnectionPool(int n)
    {
        pool = Executors.newFixedThreadPool(n);
        stopped = false;
        cache = new ConcurrentHashMap<>();
    }

    Future<Integer> OpenConnection(String url_str)
    {
        if(isStopped()) {
            System.err.println("[ERROR] The ThreadPool is down!");
            throw new RuntimeException();
        }

        Callable<Integer> task = () -> {//lambda linguaggio funzionale
            if(cache.containsKey(url_str)) {
                System.out.println("\turl "+ url_str +" already cached");
                return cache.get(url_str).getResponseCode();
            }
            try {
                URL url = new URL(url_str);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                cache.putIfAbsent(url_str, connection);
                return connection.getResponseCode();
            }catch (MalformedURLException e) {
                System.err.println("[ERROR-ThreadPool]: Malformed URL has occurred: no legal protocol could be found in a specification string or the string could not be parsed.");
                return null;
            }
            catch(UnknownHostException e){
                System.err.println("[ERROR-ThreadPool]: Impossible create a connection to the host " + url_str);
                return null;
            }
        };

        return pool.submit(task);
    }

    void StopPool()
    {
        stopped = true;
        cache.clear();
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    public boolean isStopped()
    {
        return stopped;
    }
}
