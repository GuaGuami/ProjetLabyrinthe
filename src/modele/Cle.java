package modele;

public class Cle extends Objets 
{
	
	
	public Cle () 
	{
		super();
		
	}
	
	public void action  (Aventurier a )
	{
		
	
			a.setnbCle(a.getnbCle() + 1);
			a.setPV(a.getPV()+25);
			
		}
}
