package modele;

public class Voleur extends Objets 
{
	
	private int Cle;
	private int Pioche;
	private int Bazooka;
	
	
	public Voleur () 
	{
		super();
	}
	
	//Methode
	
	//montrer y a combian d'objet
  //afficheInven1(inventaire(nbCle, nbPioche, nbBazooka));
	
	
	
	//Le table d' objets 
	/*	public static String[] tabObj () 
		{
			String [] tabObj = new String [3];
			tabObj [0] ="Cle";
			tabObj [1] ="Pioche";
			tabObj [2] ="Bazooka";
			
			return tabObj;	
		}
		
		//convertit les objets en chiffres pour faire le random de voleur
		public static int [] conversionObj(String[]tabObj)
		{
			int []ChiffreObj=new int[3];
			for(int j = 0; j<3;j++) {
				if (tabObj[j].equals("Cle")) 
				{
					ChiffreObj[j] = 11;
				}
				else if (tabObj[j].equals("Pioche")) 
				{
					ChiffreObj[j] = 12;
				}
				else if (tabObj[j].equals("Bazooka")) 
				{
					ChiffreObj[j] = 13;
				}
					
			}
			return ChiffreObj;
		}
		
	//random pour voler
		public static int [] volerRandom(int []conversionObj) 
		{
			int taille = 1;
			int binf = 11;
			int bsup = 13;
			int[]ChiffreObj = new int [taille]; 
			for (int i = 0; i < taille; i++) 
			{
				ChiffreObj[i] =  binf + (int)( Math.random() * ((bsup - binf) + 1) ); //formule qui permet d'avoir des chiffres random dans un intervalle donné.
			}
			return ChiffreObj;
		}



			//affiche ChiffreObj
			public static void affiche (int [] conversionObj) 
			{
				//System.out.print("[");
				for (int i = 0; i < conversionObj.length -1; i++)
				{
					System.out.print(conversionObj[i] + ",");
				}
				System.out.println(conversionObj[conversionObj.length -1] /*+ "]");
			}

		//convertit le chiffre de random en mot
			public static String [] convertionVoler(int[]volerRandom)
			{
				String[]convertionVoler = new String[1];
				for(int i=0; i<1;i++) {
				if(volerRandom[i]==11)
					convertionVoler[i]="Cle";
				else if (volerRandom[i]==12)
					convertionVoler[i]="Pioche";
				else if (volerRandom[i]==13)
					convertionVoler[i]="Bazooka";
				}
				return convertionVoler;
			}
			
			
			
			public void voler (String []convertionVoler,Aventurier a )
			{	
			if (convertionVoler[0]=="Cle")
			{
				a.getnbCle();
				a.setnbCle(a.getnbCle() - 1); 
				System.out.println("Votre clé a voler par un voleur!");
				
			}
			
			else if (convertionVoler[0]=="Pioche")
			{
				a.getnbPioche();
				a.setnbPioche(a.getnbPioche() - 1); 
				System.out.println("Votre pioche a voler par un voleur!");
			}
			
			else if (convertionVoler[0]=="Bazooka")
			{
				a.getnbBazooka();
				a.setnbBazooka(a.getnbBazooka() - 1); 
				System.out.println("Votre bazooka a voler par un voleur!");
			}
		
			 
			//apres voler
			
			
		}*/
			

			
			public void action  (Aventurier a )
			{
				if  (a.getnbBateau()>0)
				{
					a.setnbBateau(a.getnbBateau() - 1);
				}
				else if (a.getnbBateau()<1 && a.getnbCle()>0)
				{
					a.setnbCle(a.getnbCle() - 1);
				}
				else if (a.getnbBateau()<1 && a.getnbCle()<1 &&  a.getnbAntidote()>0)
				{
					a.setnbAntidote(a.getnbAntidote() - 1);
				}
			}
			
			
}
		
	
		
		
		

	
	
	
