package nano_shared;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NanoSheredRMI extends Remote,Serializable {
    String sayHello(String name) throws RemoteException;
}
