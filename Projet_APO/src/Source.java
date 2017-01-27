/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Source extends Case {

    private double m_nourriture;
    
    public Source()
    {
    	super();
    	this.m_nourriture=0;
    }

    public Source(int m_abcisse, int m_ordonnee, double m_nourriture) {
        super(m_abcisse, m_ordonnee);
        this.m_nourriture = m_nourriture;
    }

    public double getM_nourriture() {
        return m_nourriture;
    }

    public void setM_nourriture(double m_nourriture) {
        this.m_nourriture = m_nourriture;
    }
    
    public double DecrementeNourriture(double d){

        double nourr = getM_nourriture();
        if (d >= nourr)
        {
            this.setM_nourriture(0);
            System.out.println("Il ne reste plus de nourriture dans la source, elle va être supprimée.");
            return nourr;
        }
        else
        {
            this.setM_nourriture(nourr - d);
            System.out.println("Il reste " + this.getM_nourriture() + " nourriture dans la source.");
            return d;
        }

    }

    public boolean Penetrable() {
        return true;
    }

    @Override
    public void Afficher() {
        System.out.print("S");
    }
}
