package modele;

public class Objets {
	
	public final String getClassName() { return this.getClass().getSimpleName(); }


	/**
	 * Méthode abstraite qui permettra de définir une action / un changement
	 * sur l'aventurier.
	 */
	public void action (Aventurier p) {}


	public Objets() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
	