/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Fourmiliere extends Case{
    private double m_nourr;

    public Fourmiliere(int m_abcisse, int m_ordonnee, double m_pheromone, Case[] m_case_adj, double m_nourr) {
        super(m_abcisse, m_ordonnee, m_pheromone, m_case_adj);
        this.m_nourr = m_nourr;
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
    }

    @Override
    public void Afficher() {
        System.out.print("F");
    }
}
