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

    public Source(int m_abcisse, int m_ordonnee, double m_pheromone, Case[] m_case_adj, double m_nourriture) {
        super(m_abcisse, m_ordonnee, m_pheromone, m_case_adj);
        this.m_nourriture = m_nourriture;
    }

    public Source(double m_nourriture) {
    	super();
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
            return nourr;
        }
        else
        {
            this.setM_nourriture(nourr - d);
            return d;
        }

    }

    public boolean Penetrable() {
        return true;
    }
}
