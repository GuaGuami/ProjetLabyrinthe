package modele;

public class Poison extends Objets{
	
	
	

	public Poison() {
		super();
		
	}
	
	//METHODE
	public void action (Aventurier a) 
	{
		a.setPV(a.getPV()-40);
	}
}

