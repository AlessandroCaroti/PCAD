package nano_server;

import nano_shared.NanoSheredRMI;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class NanoServerRMI implements NanoSheredRMI {

    public static void main(String[] args) {
        try {
            System.setProperty("java.security.policy", "file:./server.policy");
            System.setProperty("java.rmi.server.codebase", "file:${workspace_loc}/MyServer/");
            /*if (System.getSecurityManager() == null)
                System.setSecurityManager(new SecurityManager());*/
            System.setProperty("java.rmi.server.hostname", "localhost");
            Registry r;
            try{
                r = LocateRegistry.createRegistry(9666);
            }catch (RemoteException e)
            {
                r = LocateRegistry.getRegistry(9666);
            }
            System.err.print("Registro trovato");
            NanoServerRMI server = new NanoServerRMI();
            NanoSheredRMI stubServer = (NanoSheredRMI) UnicastRemoteObject.exportObject(server, 0);
            r.bind("REG", stubServer);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public String sayHello(String name) throws RemoteException {

        if(name!=null)
            return "hello!"+name;
        return "hello!";
    }
}
