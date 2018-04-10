package lab01_esteso;

public class testParcheggio {
    private static final int num_auto = 15;
    private static final int num_posti = 8;

    public static void main(String[] args) {
        Parcheggio p = new Parcheggio(num_posti);
        Auto[] a = new Auto[num_auto];
        for(int i = 0; i<num_auto;i++)
            a[i] = new Auto(p);
        for(Auto i : a)
            i.start();
    }
}
