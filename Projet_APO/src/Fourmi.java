import java.awt.List;
import java.util.ArrayList;

/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Fourmi {

    protected List<Integer> m_chemin;
    protected double m_nourr_transp;
    protected enum m_etat {AVANCE , ARRET, TRANSPORTE } ;



    public Fourmi(int[] m_chemin, double m_nourr_transp) {
        this.m_chemin = new ArrayList<Integer>;
        this.m_nourr_transp = m_nourr_transp;
        m_etat m_e = m_etat.ARRET;
    }

    public int[] getM_chemin() {
        return m_chemin;
    }

    public void setM_chemin(int[] m_chemin) {
        this.m_chemin = m_chemin;
    }

    public double getM_nourr_transp() {
        return m_nourr_transp;
    }

    public void setM_nourr_transp(double m_nourr_transp) {
        this.m_nourr_transp = m_nourr_transp;
    }
    
    public void IncrementeNourriture(double nbr)
}
