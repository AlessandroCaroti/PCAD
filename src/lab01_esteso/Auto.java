package lab01_esteso;

public class Auto extends Thread {

    private boolean nelParcheggio;
    private int serialParcheggio;
    private int id_auto;
    private Parcheggio p;
    private static int id = 0;

    public Auto(Parcheggio p)
    {
        Auto.id++;
        id_auto = id;
        this.p = p;
    }

    public boolean isIn(int IdParcheggio)
    {
        if(this.serialParcheggio == IdParcheggio)
            return this.nelParcheggio;
        else
            return false;
    }

    public void sonoEntrato(int IdParcheggio)
    {
        System.out.println("L'auto" + id_auto +" è ENTRATA");
        nelParcheggio = true;
        this.serialParcheggio =IdParcheggio;
    }

    public void sonoUscito()
    {
        System.out.println("L'auto" + id_auto +" è USCITA");
        nelParcheggio = false;
    }

    @Override
    public void run()
    {
        try {
            for(int i = 0; i < 2 ; i++)
            {
                Thread.sleep((long)(Math.random() * 50));
                p.enter(this);
                Thread.sleep((long)(Math.random() * 1000));
                p.exit(this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
