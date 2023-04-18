package modele;

public class Soigneur extends Objets {
	

	public Soigneur () 
	{
		super();
	}
	
	//METHODE
	
	public void action (Aventurier a) 
	{
		a.setPV(a.getPV()+(100-a.getPV()));
	}

	


}
