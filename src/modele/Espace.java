package modele;


/**
 * Classe qui représente une zone d'espace libre.
 */
public class Espace extends Case
{
	/**
	 * Méthode qui dÃ©finit une action / un changement sur l'aventurier.
	 */
	@Override
	public void action (Aventurier a)
		{
		a.setPV(a.getPV()-1);
		}

} /*----- Fin de la classe Espace -----*/
