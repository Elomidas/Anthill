/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Case {
    protected int m_abcisse;
    protected int m_ordonnee;
    protected double m_pheromone;
    protected Case[] m_case_adj;

    final double PHERO = 10;


    public Case() {
        this.m_abcisse = 0;
        this.m_ordonnee = 0;
        this.m_pheromone = 0;
        this.m_case_adj = new Case[8];
    }

    /* Constructeur surcharge
     * parametres :
     *  > int : coordonnée des abcisses de la case
     *  > int : coordonnée des ordonnées de la case
     */
    public Case(int m_abcisse, int m_ordonnee) {
        this.m_abcisse = m_abcisse;
        this.m_ordonnee = m_ordonnee;
        this.m_pheromone = 0;
        this.m_case_adj = new Case[8];
        // [0] : NW, [1]: N, [2]: NE, [3]: W,[4]:  E,[5]: SW,[6]:  S,[7]:  SE
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

    //Incrémentation des phéromones de la case
    public void IncrementePheromone(){
        setM_pheromone(this.m_pheromone + PHERO);
    }

    //Décrémentation des phéromones de la case
    public void DecrementePheromone(){
        setM_pheromone(this.m_pheromone - 0.01*PHERO);
    }

    // Par défaut une case est pénétrable
    public boolean Penetrable() {
        return true;
    }

    //
	/* Retourne la case voisine correspondant à la direction entrée en paramètre
	 * parametres :
	 *  > int : direction donnée
	 * retour :
	 *  > Case : retourne la case correspondante du tableau des cases adjacentes
	 */
    public Case CaseVoisine(int direc)
    {
        switch(direc)
        {
            case 1:
                return m_case_adj[5];// SW

            case 2:
                return m_case_adj[3];// W

            case 3:
                return m_case_adj[0];// NW

            case 4:
                return m_case_adj[1];// N

            case -1:
                return m_case_adj[2];// NE

            case -2:
                return m_case_adj[4]; // E

            case -3:
                return m_case_adj[7]; // SE

            case -4:
                return m_case_adj[6]; // S

            default: return this;
        }
    }

    //Affiche la case par un espace.
    public void Afficher ()
    {
        System.out.print(" ");
    }


}
