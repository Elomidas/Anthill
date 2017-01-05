/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Source extends Case {

    private double m_nourriture;
    private static final double m_capafourmi = 50;
    
    public Source()
    {
    	super();
    	this.m_nourriture=0;
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
    
    
}
