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
        // [0] : NW, [1]: N, [2]: NE, [3]: W,[4]:  E, SW,[5]:  S,[6]:  SE
    }

    public int getM_abcisse() {
        return m_abcisse;
    }

    public void setM_abcisse(int abcisse) {
        this.m_abcisse = abcisse;
    }

    public int getM_ordonnee() {
        return m_ordonnee;
    }

    public void setM_ordonnee(int ordonnee) {
        this.m_ordonnee = ordonnee;
    }

    public double getM_pheromone() {
        return m_pheromone;
    }

    public void setM_pheromone(double pheromone) {
        this.m_pheromone = pheromone;
    }

    public Case getM_case_adj(int i) {
        return m_case_adj[i];
    }

    public void setM_case_adj(Case case_adj, int i) {
        this.m_case_adj[i] = case_adj;
    }


    public void IncrementePheromone(){
        setM_pheromone(this.m_pheromone + PHERO);
    }

    public void DecrementePheromone(){
        setM_pheromone(this.m_pheromone - PHERO);
    }

    public boolean Penetrable() {
        return true;
    }

    public Case CaseVoisine(int direc)
    {
        switch(direc)
        {
            case 1:
                return m_case_adj[5];

            case 2:
                return m_case_adj[3];

            case 3:
                return m_case_adj[0];

            case 4:
                return m_case_adj[1];

            case -1:
                return m_case_adj[2];

            case -2:
                return m_case_adj[4];

            case -3:
                return m_case_adj[7];

            case -4:
                return m_case_adj[6];

            default: return this;
        }
    }

}
