/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Fourmiliere extends Case{
    private double m_nourr;

    //Constructeur surcharg�
    public Fourmiliere(int m_abcisse, int m_ordonnee) {
        super(m_abcisse, m_ordonnee);
        this.m_nourr = 0;
    }
    
    //Constructeur de base
    public Fourmiliere() {
        super();
        this.m_nourr = 0;
    }

    //Setter et getter
    public double getM_nourr() {
        return m_nourr;
    }

    public void setM_nourr(double m_nourr) {
        this.m_nourr = m_nourr;
    }

    public boolean Penetrable() {
        return true;
    }

    //Fonction qui incr�mente la nourriture de la fourmili�re � l'�cran
    public void IncrementerNourriture(double d)
    {
        setM_nourr(getM_nourr() + d);
        System.out.println("La fourmi vient de d�poser " + d +" nourriture dans la source.");
    }

    //Affichage de la fourmili�re
    @Override
    public void Afficher() {
        System.out.print(" F ");
    }
}
