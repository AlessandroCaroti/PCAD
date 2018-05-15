package lab05_ServerJava;

public class Test {
    private static int nClient=3;
    private static int nRep=1;
    public static void main(String[] args) {
        Client c[] = new Client[nClient];
        Server currCerver = new Server();
        currCerver.start();
        for (int i = 0;i<nClient; i++)
            c[i]= new Client();
        for (int i = 0;i<nRep;i++)
            for (int j = 0;j<nClient;j++)
            {
                c[j].sendRequest();
            }

    }
}
