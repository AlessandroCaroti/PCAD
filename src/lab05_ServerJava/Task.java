package lab05_ServerJava;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.in;

public class Task implements Runnable
{
    private Socket client;
    static ConcurrentHashMap<String,Boolean> IDlist;

    static void setIdList(ConcurrentHashMap<String,Boolean> l)
    {
        IDlist = l;
    }

    public Task(Socket s)
    {
        client = s;
    }

    @Override
    public void run() {
        String client_ID= "";
        char c=0;
        try {
            //CREAZIONE INPUT E OUTOUT ASSOCIATI AL SOCKET
            PrintWriter out = new PrintWriter(client.getOutputStream());
            BufferedInputStream in = new BufferedInputStream(client.getInputStream());
//            BufferedOutputStream out = new BufferedOutputStream(connSocket.getOutputStream());

            //LETTURA DELL'ID DEL CLIENT UN CARATTERE ALLA VOLTA
            while (in.available()>0)
            {
                c=(char) in.read();
                if(!Character.isAlphabetic(c))
                    break;
                client_ID = client_ID + String.valueOf(c);
            }

            //SE L'ID PASSATO (NON E' UNA STRINGA VUOTA) E (L'ID NON E' GIA NELLA LISTA)
            if(!client_ID.isEmpty() && IDlist.putIfAbsent(client_ID, true) == null)
            {
                out.print("OK");
                out.flush();

                //STAMPA MESSAGIO CON LE INFORMAZIONI DI COSA HA LETTO E COSA HA SCRITTO
                System.out.println("[SERVER] Read: \'"+client_ID+"\' -Write: \'OK\'");
            }
            else
            {
                out.print("Fail");
                out.flush();
                System.out.println("[SERVER] Read: \'"+client_ID+"\' -Write: \'Fail\'");
            }
            out.flush();

            //CHIUSURA DELLA CONNESIONE
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
