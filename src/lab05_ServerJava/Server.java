package lab05_ServerJava;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread{
    private ExecutorService pool;
    private ServerSocket serverSocket;
    private ConcurrentHashMap<String,Boolean> IDlist;

    public Server()
    {
        //CREAZIONE DEL POOL
        pool = Executors.newFixedThreadPool(5);
        //CREAZIONE STRUTTURA DATI PER SALVARSI I CLIENT CHE SI NONO CONNESSI
        IDlist = new ConcurrentHashMap<>();
        //PASSAGGIO DELLA STRUTTURA AI TASK
        Task.setIdList(IDlist);
        try {
            //CREAZIONE DI UN SOCKET IN ASCOLTO
            serverSocket = new ServerSocket(9999,2);
            serverSocket.setSoTimeout(2*1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws IOException {
        Socket clientSocket;
        try {
            while (true)
            {
                //ACCETAZIONE DI UNA NUOVA CONNEZIONE
                clientSocket = serverSocket.accept();
                //PASSAGIO DELLE INFORMAZIONI AD UN NUOVO TASK
                Task task = new Task(clientSocket);
                //ESECUZIONE DEL TASK
                pool.execute(task);
            }
        } catch (SocketTimeoutException e){
            //STAMPA DEGLI UTENTI CHE SI SONO CONESSI
            printIDlist();
            //CHIUSURA DEL POOL
            pool.shutdown();
        }
    }
@Override
    public void run()
    {
        System.out.println("[STARTING SERVER]");
        try {
            startServer();
        } catch (IOException e) {
            System.err.println("Fail during creation the server");
        }
        System.out.println("[STOPPPING SERVER]");
    }

    //STAMPA DEI CLIENT CHE HANNO ACCEDUTO AL CLIENT
    private void printIDlist()
    {
        Iterator iterator = IDlist.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            System.out.println(key+" ");
        }
    }

}



