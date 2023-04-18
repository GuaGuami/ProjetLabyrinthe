package modele;


/**
 * Classe qui repr�sente une zone d'espace libre.
 */
public class Espace extends Case
{
	/**
	 * M�thode qui définit une action / un changement sur l'aventurier.
	 */
	@Override
	public void action (Aventurier a)
		{
		a.setPV(a.getPV()-1);
		}

} /*----- Fin de la classe Espace -----*/
