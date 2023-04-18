package modele;

public class Antidote extends Objets {

	public Antidote() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void action (Aventurier a) 
	{
		a.setPV(a.getPV()+40);
	}

}
