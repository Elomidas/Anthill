/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Fourmiliere extends Case{
    private double m_nourr;

    public Fourmiliere(int m_abcisse, int m_ordonnee) {
        super(m_abcisse, m_ordonnee);
        this.m_nourr = 0;
    }
    
    public Fourmiliere() {
        super();
        this.m_nourr = 0;
    }

    public double getM_nourr() {
        return m_nourr;
    }

    public void setM_nourr(double m_nourr) {
        this.m_nourr = m_nourr;
    }

    public boolean Penetrable() {
        return true;
    }

    public void IncrementerNourriture(double d)
    {
        setM_nourr(getM_nourr() + d);
        System.out.println("La fourmi vient de d�poser " + d +" nourriture dans la source.");
    }

    @Override
    public void Afficher() {
        System.out.print("F");
    }
}
