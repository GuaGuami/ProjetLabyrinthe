package modele;

public class Bateau extends Objets  {
	
	public Bateau () 
	{
		super();
	}


		public void action  (Aventurier a )
		{
			
		
				a.setnbBateau(a.getnbBateau() + 1);
				
			}
			
}
	

	

	

