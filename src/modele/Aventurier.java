package modele;


/**
 * Classe qui représente l'aventurier qui traverse le labyrinthe.
 */
public class Aventurier
{
	/*------------*/
	/* Propriétés */
	/*------------*/

	
	/*----- Sa position dans le labyrinthe -----*/
	private int x;
	private int y;
	/*----- Sa PV -----*/
	private int PV = 100 ;
	
	private int nbCle = 0;
	private int nbPioche = 0;
	private int nbBazooka = 0;
	private int nbBateau = 0;
	private int nbBouEau = 0;
	private int nbAntidote = 0;
	
	
	

	/*--------------*/
	/* Constructeur */
	/*--------------*/

	public Aventurier(int x, int y)
		{
		this.x = x;
		this.y = y;
		
		}
	

	/*----------*/
	/* Méthodes */
	/*----------*/

	public int getX () { return this.x; }
	public void setX (int x) { this.x = x; }

	public int getY () { return this.y; }
	public void setY (int y) { this.y = y; }
	
	public int getPV() {return this.PV;}
	public void setPV(int PV) {this.PV=PV;}
	
	public int getnbCle() {return this.nbCle;}
	public void setnbCle(int nbCle) {this.nbCle=nbCle;}
	
	public int getnbPioche() {return this.nbPioche;}
	public void setnbPioche(int nbPioche) {this.nbPioche=nbPioche;}
	
	public int getnbBazooka() {return this.nbBazooka;}
	public void setnbBazooka(int nbBazooka) {this.nbBazooka=nbBazooka;}
	
	public int getnbBateau() {return this.nbBateau;}
	public void setnbBateau(int nbBateau) {this.nbBateau=nbBateau;}
	
	public int getnbBouEau() {return this.nbBouEau;}
	public void setnbBouEau(int nbBouEau) {this.nbBouEau=nbBouEau;}
	
	public int getnbAntidote() {return this.nbAntidote;}
	public void setnbAntidote(int nbAntidote) {this.nbAntidote=nbAntidote;}
	
	
	
	
	
	
	
	
	
	
	
	
	/*les objets de l'aventurier mais pas afficher*/
	public String[] inventaire (int nbCle, int nbPioche, int nbBazooka) 
	{
		String [] inventaire = new String [3];
		inventaire [0] ="NbCle:"+ nbCle;
		inventaire [1] = "NbPioche:"+nbPioche;
		inventaire [2] = "NbBazooka:"+nbBazooka;
		
		return inventaire;
		
	}
	
	/*les objets de l'aventurier pour afficher avant voler*/
	public static void afficheInven1 (String [] inventaire) 
	{
		//System.out.print("[");
		for (int i = 0; i < inventaire.length -1; i++)
		{
			System.out.print(inventaire[i] + ",");
		}
		System.out.println(inventaire[inventaire.length -1] /*+ "]"*/);
	}
	


} /*----- Fin de la classe Aventurier -----*/
