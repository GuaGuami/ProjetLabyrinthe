package modele;


/**
 * Classe qui repr�sente une case du labyrinthe.
 */
public abstract class Case
{
	
	public Case() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * M�thode qui retourne le nom de la classe de l'objet.
	 */
	public final String getClassName() { return this.getClass().getSimpleName(); }


	/**
	 * M�thode abstraite qui permettra de d�finir une action / un changement
	 * sur l'aventurier.
	 */
	public abstract void action (Aventurier a);

} /*----- Fin de la classe Case -----*/
