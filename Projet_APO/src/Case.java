/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Case {
    protected int m_abcisse;
    protected int m_ordonnee;
    protected double m_pheromone;
    protected Case[] m_case_adj;

    final double PHERO = 1;

    public Case() {
        this.m_abcisse = 0;
        this.m_ordonnee = 0;
        this.m_pheromone = 0;
        this.m_case_adj = new Case[8];
    }

    public Case(int m_abcisse, int m_ordonnee, double m_pheromone, Case[] m_case_adj) {
        this.m_abcisse = m_abcisse;
        this.m_ordonnee = m_ordonnee;
        this.m_pheromone = m_pheromone;
        this.m_case_adj = m_case_adj;
    }

    public int getM_abcisse() {
        return m_abcisse;
    }

    public void setM_abcisse(int m_abcisse) {
        this.m_abcisse = m_abcisse;
    }

    public int getM_ordonnee() {
        return m_ordonnee;
    }

    public void setM_ordonnee(int m_ordonnee) {
        this.m_ordonnee = m_ordonnee;
    }

    public double getM_pheromone() {
        return m_pheromone;
    }

    public void setM_pheromone(double m_pheromone) {
        this.m_pheromone = m_pheromone;
    }

    public Case[] getM_case_adj() {
        return m_case_adj;
    }

    public void setM_case_adj(Case[] m_case_adj) {
        this.m_case_adj = m_case_adj;
    }


    public void IncrementePheromone(){
        setM_pheromone(this.m_pheromone + PHERO);
    }

    public boolean Penetrable() {
        if (this instanceof Obstacle ) {
            return false ;
        }
        return true ;
    }

}
