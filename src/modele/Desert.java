package modele;

public class Desert extends Case{
	/**
	 * M�thode qui d�finit une action / un changement sur l'aventurier.
	 */
	
	public void action (Aventurier a)
		{
			
			a.setPV(a.getPV()-20);
		
		
		
		
		
	} /*----- Fin de la classe desert -----*/
}


