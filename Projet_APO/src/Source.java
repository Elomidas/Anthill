/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Source extends Case {

    private double m_nourriture;

    //Constructeur
    public Source()
    {
    	super();
    	this.m_nourriture=0;
    }

    /* Constructeur surcharge
     * parametres :
     *  > int : coordonnée des abcisses de la source
     *  > int : coordonnée des ordonnées de la source
     *  > double : nourriture initiale de la source
     */
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

    /* Retourne la nourriture de la source et décrémente la nourriture de la source q'une quantité donnée
	 * parametres :
	 *  > double : quantité de nourriture à enlever
	 * retour :
	 *  > double : quantité nourriture de la source
	 */
    public double DecrementeNourriture(double d){

        double nourr = getM_nourriture();
        // Si quantité nourriture à décrementeur supérieur à nourriture présente dans la source
        if (d >= nourr)
        {
            // On met la quantité de la source à 0
            this.setM_nourriture(0);
            // On dit comme quoi il n'y a plus de nourriture dans la source
            System.out.println("Il ne reste plus de nourriture dans la source, elle va �tre supprim�e.");
            //On retourne 0
            return nourr;
        }
        else
        {
            // on met la quantité de la source à sa quantité  d'avant moins la quantité d demandée par la fourmie
            this.setM_nourriture(nourr - d);
            System.out.println("Il reste " + this.getM_nourriture() + " nourriture dans la source.");
            // on retourne la quantité de nourrtiture demandée.
            return d;
        }

    }

    //La source est pénétrable
    public boolean Penetrable() {
        return true;
    }

    //Affiche la source par un S
    @Override
    public void Afficher() {
        System.out.print(" S ");
    }
}
