package nano_shared;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClentInterface extends Remote,Serializable {
    public void notify(int resp) throws RemoteException;
}
