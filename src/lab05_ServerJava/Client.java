package lab05_ServerJava;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private String id;
    private static int next_id=0;
    public Client()
    {
        next_id++;
        id = String.valueOf(next_id);
    }
    public void sendRequest()
    {
        try {
            //CREAZIONE DELLA CONNESIONE
            Socket s = new Socket(InetAddress.getByName("localhost").getHostAddress(),9999);

            //CREAZIONE INPUT E OUTPUT ASSOCIATI AL SERVER
            //BufferedOutputStream out = new BufferedOutputStream(s.getOutputStream());
            PrintWriter out2 = new PrintWriter(s.getOutputStream(), true);
            BufferedInputStream in = new BufferedInputStream(s.getInputStream());

            //INVIO DELL'ID AL SERVER
            out2.println(id);
            /*
            out.write(id.getBytes());
            out.write("\n".getBytes());
            out.flush();
            */

            //STAMPA DELLE INFO
            System.out.print("\t\t\t\t\t\t\t\t\t\t\t\t[CLIENT-"+id+"] Write: \'"+id+"\' -Read: ");

            //LETTURA DELLA RISPOSTA DAL SERVER
            while (in.available()>0)
                System.out.print((char) in.read());
            System.out.println();

            //CHIUSURA DEL SOCKET
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
