package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import modele.Aventurier;
import modele.Labyrinthe;

/**
 * Fenêtre de visualisation du labyrinthe.
 */
public class Vue extends JFrame
{
	/*------------*/
	/* Propriétés */
	/*------------*/

	/**
	 * Référence vers le labyrinthe que la classe Vue va visualiser.
	 */
	private final Labyrinthe labyrinthe;

	/*----- Barre d'état de la fenêtre -----*/
	private final JLabel barre_etat;

	/*----- Déclarations des variables pour le chronomètre -----*/
	public int h = 0;//heure
	public int m = 0;//minute
	public int s = 0;//seconde
	
	private int delais =1000;//pas plus de 1000 secondes dans le jeu
	private JLabel chrono = new JLabel (h+":"+m+":"+s);
	private ActionListener tache_timer;
	public static Timer timer1;
	
	
	/*----- Zone de dessin -----*/
	Dessin dessin;
	
	/*----- Déclaration pour afficher les PV dans la fenêtre -----*/
	private JLabel affichePV;


	/*--------------*/
	/* Constructeur */
	/*--------------*/

	public Vue (int x, int y, Labyrinthe labyrinthe)
		{
		/*----- Lien avec le labyrinthe -----*/
		this.labyrinthe = labyrinthe;

		/*----- Paramètres de la fenêtre -----*/
		this.setTitle("Labyrinthe");//titre
		this.setResizable(false);//on ne peut pas réajuster la taille de la fenêtre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// croix rouge qui quitte
		this.setLocation(x,y);//position de la vue
		this.setLayout(new BorderLayout());//les bordures

		/*----- Zone de dessin -----*/
		this.dessin = new Dessin(600);//la taille de la fenêtre
		this.dessin.setFocusable(true);
		
		/*----- Attachement des écouteurs des événements souris et clavier -----*/
		this.dessin.addMouseListener(this.dessin);//faire que la souris soit interactive dans le jeu
		this.dessin.addMouseMotionListener(this.dessin);//mouvement de la souris
		this.dessin.addKeyListener(this.dessin);//faire que le clavier soit interactif dans le jeu
		this.add(this.dessin, BorderLayout.CENTER);

		/*----- Barre d'état de la fenêtre -----*/
		this.barre_etat = new JLabel("Barre d'état");
		this.add(this.barre_etat, BorderLayout.SOUTH);
		
		/*----- Affichage du chrono en haut -----*/
		this.chrono = new JLabel(h+":"+m+":"+s);
		this.add(this.chrono, BorderLayout.NORTH);
		dessin.chrono();
		
		
	
		
	
		/*----- Pour ajuster la fenêtre à son contenu et la rendre visible -----*/
		this.pack();
		this.setVisible(true);
		}

	/*----------------*/
	/* Classe interne */
	/*----------------*/

	class Dessin extends JPanel implements KeyListener, MouseListener, MouseMotionListener
		{
		
		/*----- Propriétés de la classe interne -----*/
		int largeur;
		int taille_case;
		
		/*----- Constructeur de la classe interne -----*/
		public Dessin (int larg)
			{
			/*----- Initialisation des données -----*/
			this.taille_case = larg / labyrinthe.getTaille();
			this.largeur = this.taille_case * labyrinthe.getTaille();

			/*----- Paramètre du JPanel -----*/
			this.setPreferredSize(new Dimension(this.largeur, this.largeur));
			}

		/**
		 * 
		 * Méthode pour ajouter un chrono
		 *
		 **/
		public void chrono() 
		{
			tache_timer = new ActionListener() 
			{
				public void actionPerformed(ActionEvent e1) 
				{
					s++;
					if(s==60) {
						s=0;
						m++;
					}
					if(m==60) {
						m=0;
						h++;
					}
					chrono.setText(h+":"+m+":"+s);
				}
			};
			
			/**Création et temps d'exécution de la tâche**/
			timer1=new Timer(delais,tache_timer);
		}
	 
		
		/**
		 * Méthode qui permet de dessiner ou redessinner le labyrinthe lorsque
		 * la méthode repaint() est appelée sur la classe Dessin.
		 */
		@Override
		public void paint (Graphics g)
			{
			/*----- On efface le dessin en entier -----*/
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0,0,this.largeur,this.largeur);

			/*----- Affichage des cases du labyrinthe -----*/
			for (int i=0; i < labyrinthe.getTaille(); i++)
				for (int j=0; j < labyrinthe.getTaille(); j++)
					{
					/*----- Couleur de la case -----*/
					if (labyrinthe.getCase(i,j).getClassName().equals("Mur"))		g.setColor(Color.BLACK);
					if (labyrinthe.getCase(i,j).getClassName().equals("Espace")) 	g.setColor(Color.WHITE);
					if (labyrinthe.getCase(i,j).getClassName().equals("Arrive"))	g.setColor(Color.RED);
						
					if(labyrinthe.getCase(i,j).getClassName().equals("Mer")) 
					{
						g.setColor(Color.WHITE);
						if(labyrinthe.getAventurier().getX() == i && labyrinthe.getAventurier().getY() == j) 
							g.setColor(Color.BLUE);
					}
					if (labyrinthe.getCase(i,j).getClassName().equals("Jungle"))
					{
						g.setColor(Color.WHITE);
						if(labyrinthe.getAventurier().getX() == i && labyrinthe.getAventurier().getY() == j) 
							g.setColor(Color.GREEN);
					}
					
					if (labyrinthe.getCase(i,j).getClassName().equals("Desert"))
					{
						g.setColor(Color.WHITE);
						if(labyrinthe.getAventurier().getX() == i && labyrinthe.getAventurier().getY() == j) 
							g.setColor(Color.YELLOW);
					}
					
					if (labyrinthe.getCase(i,j).getClassName().equals("Portail"))
					{
						g.setColor(Color.WHITE);
						if(labyrinthe.getAventurier().getX() == i && labyrinthe.getAventurier().getY() == j) 
							g.setColor(Color.CYAN);
					}
					
					

					/*----- Affichage de la case sous forme d'un rectangle plein -----*/
					g.fillRect(taille_case*j, taille_case*i, taille_case, taille_case);
					}

			/*----- Affichage de l'aventurier -----*/
			Aventurier aventurier = labyrinthe.getAventurier();

			g.setColor(Color.MAGENTA);
			g.fillOval(taille_case*aventurier.getY() + taille_case/4, taille_case*aventurier.getX() + taille_case/4, taille_case/2, taille_case/2);
			}


		/**
		 * Gestion des interactions souris et clavier sur le labyrinthe.
		 */
		@Override
		public void mouseClicked (MouseEvent e)
			{
			/**Arrêt du temps pour que l'aventurier puisse voir ses stats***/
			timer1.stop();
			
			/*----- Lecture de la position de la souris lors du clic sur l'objet Dessin -----*/
			int x = e.getX();
			int y = e.getY();

			/*----- Recherche des coordonnées de la case du labyrinthe sur laquelle le joueur a cliquée -----*/
			int ligne = y / this.taille_case;
			int colonne = x / this.taille_case;

			/*----- On regarde si l'aventurier est sur la case sur laquelle on vient de cliquer -----*/
			String msgAventurier = "";
			if (labyrinthe.getAventurier().getX() == ligne && labyrinthe.getAventurier().getY() == colonne)
				msgAventurier = "\nL'aventurier est sur cette case.";

			/*----- Etat de la case -----*/
			if(labyrinthe.getCase(ligne,colonne).getClassName().equals("Mur")) 
			{
				JOptionPane.showMessageDialog(this, "Vous avez cliqué sur la case (" + ligne + "," + colonne +").\nC'est un(e) "
						+ labyrinthe.getCase(ligne, colonne).getClassName()
						+ "." + msgAventurier);
			}
			else if(labyrinthe.getCase(ligne,colonne).getClassName().equals("Arrive")) 
			{
				JOptionPane.showMessageDialog(this, "Vous avez cliqué sur la case (" + ligne + "," + colonne +").\nC'est un(e) "
						+ labyrinthe.getCase(ligne, colonne).getClassName()
						+ "." + msgAventurier);
			}
			else if((labyrinthe.getAventurier().getX() == ligne && labyrinthe.getAventurier().getY() == colonne)&&(labyrinthe.getCase(ligne,colonne).getClassName().equals("Desert")||labyrinthe.getCase(ligne,colonne).getClassName().equals("Mer")||labyrinthe.getCase(ligne,colonne).getClassName().equals("Jungle")))
			{
				JOptionPane.showMessageDialog(this, "Vous avez cliqué sur la case (" + ligne + "," + colonne +").\nC'est un(e) "
						+ labyrinthe.getCase(ligne, colonne).getClassName()
						+ "." + msgAventurier);
			}
			else if((labyrinthe.getAventurier().getX() == ligne && labyrinthe.getAventurier().getY() == colonne)&&labyrinthe.getCase(ligne,colonne).getClassName().equals("Espace")){
				JOptionPane.showMessageDialog(this, "Vous avez cliqué sur la case (" + ligne + "," + colonne +").\nC'est un(e) "
						+ labyrinthe.getCase(ligne, colonne).getClassName()
						+ "." + msgAventurier);
			}
			else if(labyrinthe.getCase(ligne,colonne).getClassName().equals("Espace")&&labyrinthe.getObjets(ligne,colonne).getClassName().equals("Antidote")||labyrinthe.getObjets(ligne,colonne).getClassName().equals("Voyageur")||labyrinthe.getObjets(ligne,colonne).getClassName().equals("Poison")||labyrinthe.getObjets(ligne,colonne).getClassName().equals("Piegeloup")||(labyrinthe.getObjets(ligne,colonne).getClassName().equals("Nourriture")||labyrinthe.getObjets(ligne,colonne).getClassName().equals("Cle")||labyrinthe.getObjets(ligne,colonne).getClassName().equals("Voleur")||labyrinthe.getObjets(ligne,colonne).getClassName().equals("Bateau")||labyrinthe.getObjets(ligne,colonne).getClassName().equals("BouEau")||labyrinthe.getObjets(ligne,colonne).getClassName().equals("Soigneur"))) {
				JOptionPane.showMessageDialog(this, "Vous avez cliqué sur la case (" + ligne + "," + colonne +").\nC'est un(e) "
						+ labyrinthe.getCase(ligne, colonne).getClassName()
						+ "." + msgAventurier+"\nUne surprise vous attend !!!");
			}
			else {
				JOptionPane.showMessageDialog(this, "Vous avez cliqué sur la case (" + ligne + "," + colonne +").\nC'est un Espace ");
			}

			
			/**Remise en marche du chrono après que l'aventurier ait lu ses stats**/
			timer1.start();
			}

		@Override
		public void mousePressed (MouseEvent e) { }

		@Override
		public void mouseReleased (MouseEvent e) { }

		@Override
		public void mouseEntered (MouseEvent e) { }

		@Override
		public void mouseExited (MouseEvent e) { }

		@Override
		public void mouseDragged (MouseEvent e) { }

		@Override
		public void mouseMoved (MouseEvent e)
			{
			barre_etat.setText(" Position de la souris : " + e.getX() + " " + e.getY());
			}

		@Override
		public void keyTyped (KeyEvent e) {
			
		}

		@Override
		public void keyPressed (KeyEvent e) 
		{ 
			/**Activation du timer lorsque l'aventurier interagit avec la fenêtre**/
			timer1.start();
		}

		@Override
		public void keyReleased (KeyEvent e)
		{
		/**
		 * Déplacement de l'aventurier dans le labyrinthe.
		 */
		int pos;
		int pos2;

		/*----- Vers le bas -----*/
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
			pos = labyrinthe.getAventurier().getX();
			pos2 = labyrinthe.getAventurier().getY();
			
			if(labyrinthe.getCase(pos+1,pos2).getClass().getSimpleName().equals("Mur"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous ne pouvez pas traverser le mur");
					timer1.start();
				}	
			else
			{
				labyrinthe.getAventurier().setX(pos+1);//L'aventurier se déplace
				//
				labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).action(labyrinthe.getAventurier());//grâce à la position de l'aventurier, on cherche sur quelle case il se trouve
				labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).action(labyrinthe.getAventurier());
				timer1.stop();
				
				/********* POUR OBJETS *********/
				//voleur
				if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Voleur"))
				{
					JOptionPane.showMessageDialog(this, "HAHA! Je suis un voleur!"+"\n" + " J'ai voler un truc sur ton inventaire! " +"\n" +  "Votre nbCle : "+ labyrinthe.getAventurier().getnbCle() + "\n"+ "votre nbAntidote : "+ labyrinthe.getAventurier().getnbAntidote() + "\n" + "votre nbBateau : "+ labyrinthe.getAventurier().getnbBateau());
					timer1.start();
				}
				//nourriture
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Nourriture"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Bataeu
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Bateau"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre PV : "+ labyrinthe.getAventurier().getPV() +"\nVotre bateau : "+ labyrinthe.getAventurier().getnbBateau());
					timer1.start();
				}
				//Bouteille d'eau
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("BouEau"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre PV : "+ labyrinthe.getAventurier().getPV() +"\nVotre bouteille d'eau : "+ labyrinthe.getAventurier().getnbBouEau());
					timer1.start();
				}
				//Soigneur
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Soigneur"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez vu un soigneur qui soigner vos blessures." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Piege à loup
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Piegeloup"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez été blessé par un piège à loup." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Poison
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Poison"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) mage(e) méchant(e) "+"\nVous avez été empoisonné." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Cle
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Cle"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre clé : "+ labyrinthe.getAventurier().getnbCle());
					timer1.start();
				}
				
				
				/********* POUR AVRRIVEE *********/
				else if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Arrive")) 
				{
					JOptionPane.showMessageDialog(this, "Félicitations ! Vous avez franchi la ligne d'"+labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\n Votre PV est de "+labyrinthe.getAventurier().getPV()+"\n Votre chrono total est de "+h+":"+m+":"+s);
					setVisible(false);
				}
				/******* autre et temps ********/
				else {
				JOptionPane.showMessageDialog(this, "Votre PV : "+ labyrinthe.getAventurier().getPV() + "\nVous êtes dans un(e) " + labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName());//On affiche le PV de l'aventurier et la case sur laquelle il est
				timer1.start();
				if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Jungle")) 
				{
					s=s+3;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				else if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Mer")) 
				{
					s=s+20;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}	
				}
				else if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Desert")) 
				{
					s=s+30;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				//voyageur
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Voyageur")) 
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous avez vu un voyageur" + "\n" + "Il vous donne 30s pour traverser la labyrinthe." );
					timer1.start();
					s=s-30;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				//temps piege a loup
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Piegeloup")) 
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous avez blessé par un piège à loup" + "\n" + "Vous avez besoin de 20 secondes de temps de récupération." );
					timer1.start();
					s=s+20;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
			}
			}
			}

		/*----- Vers le haut -----*/
		if (e.getKeyCode() == KeyEvent.VK_UP)
			{
			pos = labyrinthe.getAventurier().getX();
			pos2 = labyrinthe.getAventurier().getY();
			
			if(labyrinthe.getCase(pos-1,pos2).getClass().getSimpleName().equals("Mur"))
			{
				timer1.stop();
				JOptionPane.showMessageDialog(this, "Vous ne pouvez pas traverser le mur");
				timer1.start();
			}
			else
			{
				labyrinthe.getAventurier().setX(pos-1);
				labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).action(labyrinthe.getAventurier());//grâce à la position de l'aventurier, on cherche sur quelle case il se trouve
				labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).action(labyrinthe.getAventurier());
				timer1.stop();
				
				/********* POUR OBJETS *********/
				if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Voleur"))
				{
					JOptionPane.showMessageDialog(this, "HAHA! Je suis un voleur!"+"\n" + " J'ai voler un truc sur ton inventaire! " +"\n" +  "Votre nbCle : "+ labyrinthe.getAventurier().getnbCle() + "\n"+ "votre nbAntidote : "+ labyrinthe.getAventurier().getnbAntidote() + "\n" + "votre nbBateau : "+ labyrinthe.getAventurier().getnbBateau());
					timer1.start();
				}
				//nourriture
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Nourriture"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Bataeu
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Bateau"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre PV : "+ labyrinthe.getAventurier().getPV() +"\nVotre bateau : "+ labyrinthe.getAventurier().getnbBateau());
					timer1.start();
				}
				//Bouteille d'eau
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("BouEau"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre PV : "+ labyrinthe.getAventurier().getPV() +"\nVotre bouteille d'eau : "+ labyrinthe.getAventurier().getnbBouEau());
					timer1.start();
				}
				//Soigneur
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Soigneur"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez vu un soigneur qui soigner vos blessures." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Piege à loup
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Piegeloup"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez été blessé par un piège à loup." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Poison
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Poison"))
					{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) mage(e) méchant(e) "+"\nVous avez été empoisonné." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Cle
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Cle"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre clé : "+ labyrinthe.getAventurier().getnbCle());
					timer1.start();
				}
				
				else 
				{
				JOptionPane.showMessageDialog(this, "Votre PV : "+ labyrinthe.getAventurier().getPV() + "\nVous êtes dans un(e) " + labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName());//On affiche le PV de l'aventurier et la case sur laquelle il est
				timer1.start();
				}
				
				//temps
				if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Jungle")) 
				{
					s=s+3;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				else if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Mer")) 
				{
					s=s+20;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				else if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Desert")) 
				{
					s=s+30;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				//voyageur
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Voyageur")) 
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous avez vu un voyageur" + "\n" + "Il vous donne 30s pour traverser la labyrinthe." );
					timer1.start();
					s=s-30;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				//temps piege a loup
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Piegeloup")) 
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous avez blessé par un piège à loup" + "\n" + "Vous avez besoin de 20 secondes de temps de récupération." );
					timer1.start();
					s=s+20;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
			}
			}

		/*----- Vers la droite -----*/
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
			pos = labyrinthe.getAventurier().getX();
			pos2 = labyrinthe.getAventurier().getY();
			if(labyrinthe.getCase(pos,pos2+1).getClass().getSimpleName().equals("Mur"))
			{
				timer1.stop();
				JOptionPane.showMessageDialog(this, "Vous ne pouvez pas traverser le mur");
				timer1.start();
			}

			else
			{
				labyrinthe.getAventurier().setY(pos2+1);
				labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).action(labyrinthe.getAventurier());//grâce à la position de l'aventurier, on cherche sur quelle case il se trouve
				labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).action(labyrinthe.getAventurier());
				timer1.stop();
				
				/********* POUR OBJETS *********/
				if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Voleur"))
				{
					JOptionPane.showMessageDialog(this, "HAHA! Je suis un voleur!"+"\n" + " J'ai voler un truc sur ton inventaire! " +"\n" +  "Votre nbCle : "+ labyrinthe.getAventurier().getnbCle() + "\n"+ "votre nbAntidote : "+ labyrinthe.getAventurier().getnbAntidote() + "\n" + "votre nbBateau : "+ labyrinthe.getAventurier().getnbBateau());
					timer1.start();
				}
				//nourriture
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Nourriture"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Bataeu
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Bateau"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre PV : "+ labyrinthe.getAventurier().getPV() +"\nVotre bateau : "+ labyrinthe.getAventurier().getnbBateau());
					timer1.start();
				}
				//Bouteille d'eau
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("BouEau"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre PV : "+ labyrinthe.getAventurier().getPV() +"\nVotre bouteille d'eau : "+ labyrinthe.getAventurier().getnbBouEau());
					timer1.start();
				}
				//Soigneur
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Soigneur"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez vu un soigneur qui soigner vos blessures." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Piege à loup
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Piegeloup"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez été blessé par un piège à loup." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Poison
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Poison"))
					{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) mage(e) méchant(e) "+"\nVous avez été empoisonné." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Cle
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Cle"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre clé : "+ labyrinthe.getAventurier().getnbCle());
					timer1.start();
				}
				// autres
				else 
				{
				JOptionPane.showMessageDialog(this, "Votre PV : "+ labyrinthe.getAventurier().getPV() + "\nVous êtes dans un(e) " + labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName());//On affiche le PV de l'aventurier et la case sur laquelle il est
				timer1.start();
				}
				// temps
				if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Jungle")) 
				{
					s=s+3;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				else if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Mer")) 
				{
					s=s+20;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				else if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Desert")) 
				{
					s=s+30;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				//voyageur
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Voyageur")) 
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous avez vu un voyageur" + "\n" + "Il vous donne 30s pour traverser la labyrinthe." );
					timer1.start();
					s=s-30;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				//temps piege a loup
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Piegeloup")) 
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous avez blessé par un piège à loup" + "\n" + "Vous avez besoin de 20 secondes de temps de récupération." );
					timer1.start();
					s=s+20;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
			}
			}

		/*----- Vers la gauche -----*/
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
			pos = labyrinthe.getAventurier().getX();
			pos2 = labyrinthe.getAventurier().getY();
			if(labyrinthe.getCase(pos,pos2-1).getClass().getSimpleName().equals("Mur"))
			{
				timer1.stop();
				JOptionPane.showMessageDialog(this, "Vous ne pouvez pas traverser le mur");
				timer1.start();
			}
			else
			{
				labyrinthe.getAventurier().setY(pos2-1);
				labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).action(labyrinthe.getAventurier());//grâce à la position de l'aventurier, on cherche sur quelle case il se trouve
				labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).action(labyrinthe.getAventurier());
				timer1.stop();
				
				/********* POUR OBJETS *********/
				if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Voleur"))
				{
					JOptionPane.showMessageDialog(this, "HAHA! Je suis un voleur!"+"\n" + " J'ai voler un truc sur ton inventaire! " +"\n" +  "Votre nbCle : "+ labyrinthe.getAventurier().getnbCle() + "\n"+ "votre nbAntidote : "+ labyrinthe.getAventurier().getnbAntidote() + "\n" + "votre nbBateau : "+ labyrinthe.getAventurier().getnbBateau());
					timer1.start();
				}
				//nourriture
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Nourriture"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Bataeu
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Bateau")&& true)
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV() +"\nVotre bateau : "+ labyrinthe.getAventurier().getnbBateau());
					timer1.start();
				}
				//Bouteille d'eau
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("BouEau"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV() +"\nVotre bouteille d'eau : "+ labyrinthe.getAventurier().getnbBouEau());
					timer1.start();
				}
				//Soigneur
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Soigneur"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez vu un soigneur qui soigner vos blessures." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Piege à loup
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Piegeloup"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez blesseé par un piège à loup." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Poison
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Poison"))
					{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) mage(e) méchant(e) "+"\nVous avez été empoisonné." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV());
					timer1.start();
				}
				//Cle
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Cle"))
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous êtes dans un(e) "+ labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVous avez trouvé l'objet : " + labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName()+"\nVotre clé : "+ labyrinthe.getAventurier().getnbCle());
					timer1.start();
				}
				//autres
				else 
				{
				JOptionPane.showMessageDialog(this, "Votre PV : "+ labyrinthe.getAventurier().getPV() + "\nVous êtes dans un(e) " + labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName());//On affiche le PV de l'aventurier et la case sur laquelle il est
				timer1.start();
				}
				//temps
				if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Jungle")) 
				{
					s=s+3;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				else if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Mer")) 
				{
					s=s+20;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				else if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Desert")) 
				{
					s=s+30;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				//voyageur
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Voyageur")) 
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous avez vu un voyageur" + "\\n" + "Il vous donne 30s pour traverser la labyrinthe." );
					timer1.start();
					s=s-30;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
				//temps piege a loup
				else if (labyrinthe.getObjets(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Piegeloup")) 
				{
					timer1.stop();
					JOptionPane.showMessageDialog(this, "Vous avez blessé par un piège à loup" + "\n" + "Vous avez besoin de 20 secondes de temps de récupération." );
					timer1.start();
					s=s+20;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
			}
			}
		
		
		/*----- Vers la B pour bateau -----*/
		if (e.getKeyCode() == KeyEvent.VK_B)
			{
			pos = labyrinthe.getAventurier().getX();
			pos2 = labyrinthe.getAventurier().getY();
			
			if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Mer"))
			{
				if (labyrinthe.getAventurier().getnbBateau()>0);
				{
				labyrinthe.getAventurier().setnbBateau(labyrinthe.getAventurier().getnbBateau()-1);
				timer1.stop();
				JOptionPane.showMessageDialog(this, "Vous avez bien utilisé le bateau" + "\\n" + "Vous avez bien économicé 15s." + "\nVotre bateau : " + labyrinthe.getAventurier().getnbBateau());
				timer1.start();
				}
				if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Mer")) 
				{
					s=s-15;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}
			}
		}
		
		/*----- Vers la E pour bouteille -----*/
		if (e.getKeyCode() == KeyEvent.VK_E)
			{
			pos = labyrinthe.getAventurier().getX();
			pos2 = labyrinthe.getAventurier().getY();
			
			if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Desert"))
			{
				if (labyrinthe.getAventurier().getnbBouEau()>0);
				{
				labyrinthe.getAventurier().setnbBouEau(labyrinthe.getAventurier().getnbBouEau()-1);
				timer1.stop();
				JOptionPane.showMessageDialog(this, "Vous avez bien utilisé le bouteille d'eau" + "\n" + "Vous avez bien économicé 10s." + "\nVotre bouteille d'eau : " + labyrinthe.getAventurier().getnbBouEau());
				timer1.start();
				}
				if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Desert")) 
				{
					s=s-10;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}	
			}
		}
		
		/*----- Vers la A pour antidote -----*/
		if (e.getKeyCode() == KeyEvent.VK_A)
			{
			pos = labyrinthe.getAventurier().getX();
			pos2 = labyrinthe.getAventurier().getY();
			
			
				if (labyrinthe.getAventurier().getnbAntidote()>0);
				{
				labyrinthe.getAventurier().setnbAntidote(labyrinthe.getAventurier().getnbAntidote()-1);
				timer1.stop();
				JOptionPane.showMessageDialog(this, "Vous avez bien utilisé l'antidote" + "\n" + "\nVous avez ajouté le 40 de PV." + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV()+ "\nVous restez : "+ labyrinthe.getAventurier().getnbAntidote()+" antidote.");
				timer1.start();
					
			}				
		}
		
		/*----- Vers la C pour cle -----*/
		if (e.getKeyCode() == KeyEvent.VK_C)
			{
			pos = labyrinthe.getAventurier().getX();
			pos2 = labyrinthe.getAventurier().getY();
			
			if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Portail"))
			{
				if (labyrinthe.getAventurier().getnbCle()>0);
				{
				labyrinthe.getAventurier().setnbCle(labyrinthe.getAventurier().getnbCle()-1);
				timer1.stop();
				JOptionPane.showMessageDialog(this, "Vous avez bien utilisé un clé" + "\n" + "Vous avez bien économicé 25s."  + "\nVotre nouveau PV : "+ labyrinthe.getAventurier().getPV()+ "\nVous avez resté des clé : " + labyrinthe.getAventurier().getnbCle());
				timer1.start();
				}
				if(labyrinthe.getCase(labyrinthe.getAventurier().getX(),labyrinthe.getAventurier().getY()).getClassName().equals("Portail")) 
				{
					s=s-25;
					if(s>=60) 
					{
						s=s-60;
						m++;
					}
					if(m>60) 
					{
						m=60-m;
						h++;
					}
				}	
			}
		}
			
		/*----- Vers la Shift pour regarder l'inventaire -----*/
		if (e.getKeyCode() == KeyEvent.VK_SHIFT)
			{
			pos = labyrinthe.getAventurier().getX();
			pos2 = labyrinthe.getAventurier().getY();
			
			
				timer1.stop();
				JOptionPane.showMessageDialog(this, "Votre inventaire : " +"\n" +  "Votre nbCle : "+ labyrinthe.getAventurier().getnbCle() + "\n"+ "votre nbAntidote : "+ labyrinthe.getAventurier().getnbAntidote() + "\n" + "votre nbBateau : "+ labyrinthe.getAventurier().getnbBateau());
				timer1.start();
					
			}				
		
		
		
		
			
			
		
		
		//tu mort
		
		
		if (labyrinthe.getAventurier().getPV()  <= 0) 
		{
			JOptionPane.showMessageDialog(this, "GAME OVER");
			this.setVisible(false);
			timer1.stop();
			JOptionPane.showMessageDialog(this, "Le défi a échoué\n" + "Temps total est de "+h+":"+m+":"+s);
		}
		
		
/*----- On refait le dessin -----*/
		

		/*----- On refait le dessin -----*/
		repaint();
		}

		}
}

			


