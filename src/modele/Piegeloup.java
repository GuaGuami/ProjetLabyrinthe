package modele;



public class Piegeloup extends Objets {
	
	
	

	public Piegeloup() {
		super();
		
	}
	
	//METHODE
	public void action (Aventurier a) 
	{
		a.setPV(a.getPV()-28);
	}

}
