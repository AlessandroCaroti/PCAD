package nano_shared;

import java.io.Serializable;
import java.net.URL;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote,Serializable {
    public String connect(ClentInterface c) throws RemoteException;
    public int request(String url) throws RemoteException;
}
