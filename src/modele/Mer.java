package modele;


public class Mer extends Case{
	
	
		/**
		 * M�thode qui d�finit une action / un changement sur l'aventurier.
		 */
		
		public void action (Aventurier a)
			{
			
			a.getPV();
			a.setPV(a.getPV()-15);
			
			
			
			
			//System.out.println("Je suis un mer !");
			

	} /*----- Fin de la classe Mer -----*/

}
