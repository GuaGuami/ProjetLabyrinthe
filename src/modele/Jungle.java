package modele;

public class Jungle extends Case{

	/**
	 * M�thode qui d�finit une action / un changement sur l'aventurier.
	 */
	
	public void action (Aventurier a)
		{
		a.getPV();
		a.setPV(a.getPV()-5);
		
		
		
		//System.out.println("Je suis un jungle !");
		
	} /*----- Fin de la classe Jungle -----*/
}
