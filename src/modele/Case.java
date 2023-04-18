package modele;


/**
 * Classe qui représente une case du labyrinthe.
 */
public abstract class Case
{
	
	public Case() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Méthode qui retourne le nom de la classe de l'objet.
	 */
	public final String getClassName() { return this.getClass().getSimpleName(); }


	/**
	 * Méthode abstraite qui permettra de définir une action / un changement
	 * sur l'aventurier.
	 */
	public abstract void action (Aventurier a);

} /*----- Fin de la classe Case -----*/
