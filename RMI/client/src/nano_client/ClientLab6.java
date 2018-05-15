package nano_client;

import nano_shared.ServerInterface;
import nano_shared.ClentInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientLab6 implements ClentInterface{
    public static void main(String[] args) {
        try {
            System.setProperty("java.security.policy", "file:./server.policy");
            System.setProperty("java.rmi.server.codebase", "file:${workspace_loc}/MyServer/");
            System.setProperty("java.rmi.server.hostname", "localhost");
            Registry r = LocateRegistry.getRegistry("130.251.36.239",9667);
            System.err.println("Registro trovato");

            ClientLab6 client = new ClientLab6();
            ClentInterface stubClient = (ClentInterface) UnicastRemoteObject.exportObject(client, 0);
            r.rebind("ClientReg", stubClient);
            System.err.println("Client ready");

            //connect to the server
            ServerInterface ServerStub = (ServerInterface) r.lookup("ServerReg");
            String s = ServerStub.connect(stubClient);
            System.err.println("Server trovato. Risposta connesione: "+s);
            System.out.println("\nWaiting...");
            System.in.read();
            for (String ss:args)
            {
                Thread.sleep(500);
                ServerStub.request(ss);
            }
            System.out.println("finito");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            System.exit(-1);
        }
    }
    @Override
    public void notify(int resp)throws RemoteException {
        System.out.println(resp);
    }
}
