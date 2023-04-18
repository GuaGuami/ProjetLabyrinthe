package jeu;

import vue.Vue;
import modele.Espace;
import modele.Jungle;
import modele.Mur;
import modele.Nourriture;
import modele.Portail;
import modele.Soigneur;
import modele.Voleur;
import modele.Voyageur;
import modele.Antidote;
import modele.Arrive;
import modele.Aventurier;
import modele.Bateau;
import modele.BouEau;
import modele.Cle;
import modele.Desert;
import modele.Labyrinthe;
import modele.Mer;
import modele.Objets;
import modele.Piegeloup;
import modele.Poison;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;



/**
 * Application labyrinthe.
 */
public class AppLabyrinthe
{
	/**
	 * Noms des fichiers contenant des labyrinthes.
	 */
	private static final String LAB_1 = "data" + File.separator + "labyrinthe_1.csv";
	private static final String LAB_2 = "data" + File.separator + "labyrinthe_2.csv";


	/**
	 * Chargement du labyrinthe et de l'aventurier.
	 *
	 * A partir d'un fichier csv, cette m�thode cr�e le labyrinthe et
	 * l'aventurier sur la case de d�part du labyrinthe.
	 */
	public static Labyrinthe chargeLabyrinthe (String fichier)
		{
		Labyrinthe lab = null;
		
		try (Scanner scanner = new Scanner(new FileInputStream(fichier)))
			{
			/*----- Lecture de la taille du labyrinthe -----*/
			int taille = Integer.valueOf(scanner.nextLine());

			/*----- Initialisation du labyrinthe -----*/
			lab = new Labyrinthe(taille);

			/*----- Lecture du fichier et des types de cases composant le labyrinthe -----*/
			for (int i=0; i<taille; i++)
				{
				/*----- Lecture d'une ligne du fichier -----*/
				String[] liste = scanner.nextLine().trim().split(";");
				
				int type_case;
				for (int j=0; j<taille; j++)
					{
					
					type_case = Integer.valueOf(liste[j]);
					lab.setObjets(i,j,new Objets());
					lab.setObjets(3,9,new Voleur());
					lab.setObjets(7,1,new Nourriture());
					lab.setObjets(3,1,new Bateau());
					lab.setObjets(7,5,new BouEau());
					lab.setObjets(5,5,new BouEau());
					lab.setObjets(6,6,new Soigneur());
					lab.setObjets(7,9,new Voyageur());
					lab.setObjets(3,5,new Piegeloup());
					lab.setObjets(2,7,new Poison());
					lab.setObjets(1,1,new Nourriture());
					lab.setObjets(1,3,new Piegeloup());
					lab.setObjets(1,7,new Bateau());
					lab.setObjets(1,8,new Voyageur());
					lab.setObjets(2,7,new Antidote());
					lab.setObjets(3,5,new Nourriture());
					lab.setObjets(8,5,new Poison());
					lab.setObjets(9,5,new Voleur());
					lab.setObjets(1,9,new Soigneur());
					lab.setObjets(5,1,new Cle());
					
					
					/*----- Type 0 --> "Espace" -----*/
					if (type_case == 0) lab.setCase(i, j, new Espace());

					/*----- Type 1 --> "Mur" -----*/
					if (type_case == 1) lab.setCase(i, j, new Mur());
					
					/*----- Type 2 --> "Mer" -----*/
					if (type_case == 2) lab.setCase(i, j, new Mer());
					
					/*----- Type 3 --> "Desert" -----*/
					if (type_case == 3) lab.setCase(i, j, new Desert());
					
					/*----- Type 4 --> "Jungle" -----*/
					if (type_case == 4) lab.setCase(i, j, new Jungle());
					
					/*----- Type 6 --> "PORTE" -----*/
					if (type_case == 6) lab.setCase(i, j, new Portail());
					
					/*----- Type 8 --> "Arriv�" -----*/
					if (type_case == 8) lab.setCase(i, j, new Arrive());
					
					/*----- Type 9 --> "Espace de d�part" et "Aventurier" -----*/
					if (type_case == 9)
						{
						lab.setCase(i, j, new Espace());
						lab.setAventurier(new Aventurier(i, j));
						}
					}
				}
			}
		catch (FileNotFoundException ex)
			{
			
			System.err.println("Erreur lors de la lecture du fichier : " + fichier + " - " + ex.getMessage());
			}

		return lab;
		}


	/*---------------------*/
	/* Programme principal */
	/*---------------------*/

	public static void main (String[] s) throws InterruptedException
		{
		
		/*----- Chargement du labyrinthe -----*/
		Labyrinthe labyrinthe = chargeLabyrinthe(LAB_1);
		
		/**INTRODUCTION**/
		JOptionPane.showMessageDialog(null,"Bienvenue dans le jeu Labyrinthe.");
		JOptionPane.showMessageDialog(null,"Votre but est de trouver la sortie en rouge le plus vite possible avec le plus de points de vie (PV) possibles."
		
		+"\n "
		+"\nAu d�but, vos PV = 100."+"\nMais vos d�placements dans le labyrinthe vont r�duire vos PV."
		
		+"\n "
		+"\nIl existe diff�rents types de cases : Espace ,Jungle, Mer, D�sert et Portail."
		+"\nSi vous �tes dans un Espace, votre d�placement va vous co�ter 1 PV. Il n�a pas d�impact sur le chrono."
		+"\nSi vous �tes dans la Jungle, la traverser va vous prendre 3 secondes et va vous co�ter 5 de vos PV."
		+"\nSi vous �tes dans la Mer, la traverser va vous prendre 20 secondes et va vous co�ter 15 de vos PV."
		+"\nSi vous �tes dans le D�sert, la traverser va vous prendre 30 secondes et va vous co�ter 20 de vos PV."
		+"\nSi vous �tes face � un Portail, il vous faut la Cl� pour trouver un tr�sor."
        
		+"\n "
		+"\nVous allez �galement tomber sur diff�rents objets : Nourriture, Bateau, Bouteille d'Eau, Antidote et Cl�."
		+"\nSi vous tombez sur Nourriture, vous gagnerez 10 PV."
		+"\nLe Bateau va vous permettre de traverser la Mer en 5 secondes. Pour l'utiliser, appuyez sur la touche ''B'' de votre clavier."
		+"\nLa Bouteille d'Eau va vous permettre de traverser le D�sert en 20 secondes. Pour l'utiliser, appuyez sur la touche ''E'' de votre clavier."
		+"\nL�Antidote vous gu�rit du Poison. Elle vous rend 40 de vos PV. Pour l'utiliser, appuyez sur la touche ''A'' de votre clavier."
		+"\nLa Cl� vous permet d�ouvrir le Portail. Pour l'utiliser, appuyez sur la touche ''C'' de votre clavier."
		
		
        +"\n "
		+"\nVous pourriez tomber sur des pi�ges : Poison et Pi�ge � Loup."
		+"\nLe Poison enl�ve 40 de vos PV."
		+"\nLe Pi�ge � Loup vous blessera et prendra 28 de vos PV et augmentera de 20 secondes votre chrono."
		
		+"\n "
		+"\nEnfin, vous pourriez tomber sur des personnages : Soigneur, Voleur et Voyageur."
		+"\nLe Soigneur gu�rit les blessures engendr�es par le Pi�ge � Loup et remet vos PV � 100."
		+"\nLe Voleur vole 1 des objets de votre inventaire : Bateau, Cl� et Antidote."
		+"\nLe Voyageur du temps vous fait gagner 30 secondes."
		
		+"\n "
		+"\nAppuyez sur la touche ''SHIFT'' pour regarder votre inventaire."
		
		+"\n "
		+"\nAttention !!!"
		+"\nVous pouvez tomber sur un objet, un pi�ge ou un personnage dans un m�me espace !"
		+"\nLe chrono se d�clenche en touchant le clavier ou en cliquant sur une case !");
		
		JOptionPane.showMessageDialog(null,"C'est � vous de jouer !");
		
		



		
		/*----- Création de la fenêtre de visualisation du labyrinthe et affichage -----*/
		new Vue(300, 10, labyrinthe);
		
		
		}
		
		
		 
		
		

} /*----- Fin de ma classe AppLabyrinthe -----*/