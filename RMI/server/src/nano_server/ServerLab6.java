package nano_server;

import nano_shared.ServerInterface;
import nano_shared.ClentInterface;

import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerLab6 implements ServerInterface {
    LinkedBlockingQueue<ClentInterface> clients;

    public static void main(String[] args) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("IP Address:- " + inetAddress.getHostAddress());
            System.out.println("Host Name:- " + inetAddress.getHostName());

            System.setProperty("java.security.policy", "file:./server.policy");
            //System.setProperty("java.rmi.server.codebase", "file:${workspace_loc}/MyServer/");
            System.setProperty ("java.rmi.server.codebase", "http://130.251.36.239/hello.jar");
            System.setProperty("java.rmi.server.hostname", "localhost");
            Registry r;
            try{
                r = LocateRegistry.createRegistry(9667);
            }catch (RemoteException e) {
                r = LocateRegistry.getRegistry("130.251.36.239",9667);
            }
            System.err.println("Registro trovato");
            ServerLab6 server = new ServerLab6();
            ServerInterface stubServer = (ServerInterface) UnicastRemoteObject.exportObject(server, 0);
            r.bind("ServerReg", stubServer);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            System.exit(-1);
        }
    }




    public ServerLab6()
    {
        clients = new LinkedBlockingQueue<>();
    }

    @Override
    public String connect(ClentInterface c) throws RemoteException {
        ClentInterface newClent = c;
        if(newClent == null)
            return "ERR";
        try {
            clients.put(newClent);
            System.err.println("New client connected");
        } catch (InterruptedException e) {
            return "ERR";
        }
        return "OK";
    }

    @Override
    public int request(String url_str) throws RemoteException {
        int response;
        try {
            URL url = new URL(url_str);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            response = connection.getResponseCode();
        }catch (MalformedURLException e) {
            System.err.println("[ERROR-Server]: Malformed URL has occurred: no legal protocol could be found in a specification string or the string could not be parsed.");
            response = -1;
        }
        catch(UnknownHostException e){
            System.err.println("[ERROR-Server]: Impossible create a connection to the host " + url_str);
            response = -2;
        } catch (IOException e) {
            response = -3;
        }
        notifyAll(response);
        return response;
    }

    private void notifyAll(int r) throws RemoteException
    {
        Iterator iterator = clients.iterator();
        while (iterator.hasNext()) {
            ((ClentInterface)iterator.next()).notify(r);
        }
    }
}
