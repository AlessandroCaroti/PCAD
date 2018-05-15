package nano_client;

import nano_shared.NanoSheredRMI;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class NanoClientRMI {
    public static void main(String[] args) {
        //String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",9666);
            NanoSheredRMI stub = (NanoSheredRMI) registry.lookup("REG");
            String response = stub.sayHello(" HEY SONO IL PIPPO");
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
