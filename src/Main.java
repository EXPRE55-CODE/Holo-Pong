import java.awt.Color;

import processing.core.PApplet;
import processing.core.PFont;

public class Main extends PApplet {
	private final int MARGE = 10;
	private PFont arial90;
	private PFont policePong;
	
	private Color bleuJoueur, orangeOrdi;
	
	private enum �tat {ATTENTE_LANCEMENT, EN_COURS, TRANSITION_MANCHES, AFFICHAGE_RESULTAT}
	private �tat �tatJeu;
	
	private Score scoreJoueur, scoreOrdi;
	private IndicateurBoost indicateurBoostJoueur;
	private IndicateurBoost indicateurBoostOrdi;

	private Raquette raquetteJoueur, raquetteOrdi;
	private Balle balle;
	private Filet filet;
	
	private ControleurLimites controleurLimites;
	private Ordinateur controleurOrdinateur;
	
	public static void main(String[] args) {
		PApplet.main(new String[] {"--present", "Main"});
	}
	
	public void settings() {
		size(this.displayWidth, this.displayHeight);
	}
	
	public void setup() {
		this.arial90 = this.createFont("Arial", 90);
		this.policePong = this.createFont("res/police_pong.ttf", 180);
		
		this.bleuJoueur = new Color(133, 199, 239);
		this.orangeOrdi = new Color(236, 185, 94);
		
		this.�tatJeu = �tat.ATTENTE_LANCEMENT;
		
		this.scoreJoueur = new Score(this, this.width/4, MARGE * 2, this.policePong, this.bleuJoueur);
		this.scoreOrdi = new Score(this, 3 * this.width/4, MARGE * 2, this.policePong, this.orangeOrdi);
		
		this.indicateurBoostJoueur = new IndicateurBoost(this, 0, this.bleuJoueur, MARGE);
		this.indicateurBoostOrdi = new IndicateurBoost(this, 180, this.orangeOrdi, MARGE);
		
		this.raquetteJoueur = new Raquette(this, MARGE + 5, this.height/2, this.bleuJoueur);
		this.raquetteOrdi = new Raquette(this, this.width - MARGE - this.raquetteJoueur.getDimX() - 5, this.height/2, this.orangeOrdi);
		this.balle = new Balle(this, this.width/2, this.height/2);
		this.filet = new Filet(this, this.width/2 - MARGE/2, 0, MARGE, MARGE);
		
		this.controleurLimites = new ControleurLimites(this.balle, this.raquetteJoueur, this.raquetteOrdi, this.scoreJoueur, this.scoreOrdi, this.MARGE, this.height);
		this.controleurOrdinateur = new Ordinateur(this.raquetteOrdi, this.balle);
				
		this.colorMode(HSB);
		this.background(0);
	}
	
	public void draw() {
		switch (this.�tatJeu) {
		case ATTENTE_LANCEMENT:
			this.afficherD�corations();
			this.filet.afficher();
			this.afficherOverlay("Appuyez sur ENTR�E pour commencer � jouer !");
			if(this.appuiToucheEntr�e()) {
				this.balle.lancer();
				this.�tatJeu = �tat.EN_COURS;
				this.indicateurBoostJoueur.r�initialiser();
				this.indicateurBoostOrdi.r�initialiser();
			}
			break;
		case EN_COURS:
			this.afficherOverlay();
			this.afficherD�corations();
			this.filet.afficher();
			this.g�rerD�placementsJoueur();
			this.animerRaquettes();
			this.afficherRaquettes();
			this.balle.animer();
			this.g�rerLimites();
			this.actualiserPPosYRaquettes();
			this.g�rerOrdinateur();
			this.balle.afficher();
			this.afficherScores();
			this.afficherIndicateursBoosts();
			this.actualiserIndicateursBoosts();
			if(this.controleurLimites.isSortieBalle()) {
				this.raquetteJoueur.r�initialiser();
				this.raquetteOrdi.r�initialiser();
				if(this.scoreJoueur.getValeur() >= 10 || this.scoreOrdi.getValeur() >= 10)
					this.�tatJeu = �tat.AFFICHAGE_RESULTAT;
				else
					this.�tatJeu = �tat.TRANSITION_MANCHES;
				this.r�initialiserIndicateursBoost();
			}
			break;
		case TRANSITION_MANCHES:
			this.afficherOverlay();
			this.filet.animer();
			this.filet.afficher();
			this.afficherD�corations();
			this.balle.afficher();
			this.afficherRaquettes();
			this.afficherScores();
			if(this.filet.getPosCache() >= this.height) {
				this.filet.initialiserAnimation();
				this.�tatJeu = �tat.EN_COURS;
			}
			break;
		case AFFICHAGE_RESULTAT:
			this.afficherOverlay("Appuyez sur ENTR�E pour recommencer � jouer !");
			this.afficherD�corations();
			this.filet.afficher();
			this.afficherScores();
			this.afficherIssueMatch();
			if(this.appuiToucheEntr�e()) {
				this.balle.lancer();
				this.indicateurBoostJoueur.r�initialiser();
				this.indicateurBoostOrdi.r�initialiser();
				this.scoreJoueur.r�initialiser();
				this.scoreOrdi.r�initialiser();
				this.�tatJeu = �tat.EN_COURS;
			}
			break;
		default:
			throw new IllegalArgumentException("Cas inconnu");
		}
		//this.save("Capture/image" + this.frameCount + ".png");
	}

	private void afficherD�corations() {
		this.fill(255);
		this.noStroke();
		this.rect(0, 0, this.width, MARGE);
		this.rect(this.width - MARGE, 0, MARGE, this.height);
		this.rect(0, this.height - MARGE, this.width, MARGE);
		this.rect(0, 0, MARGE, this.height);
	}
	
	private void afficherRaquettes() {
		this.raquetteJoueur.afficher();
		this.raquetteOrdi.afficher();
	}
	private void actualiserPPosYRaquettes() {
		this.raquetteJoueur.actualiserPosYPrec�dente();
		this.raquetteOrdi.actualiserPosYPrec�dente();
	}
	private void animerRaquettes() {
		this.raquetteJoueur.animer();
		this.raquetteOrdi.animer();
	}
	
	private void afficherScores() {
		this.scoreJoueur.afficher();
		this.scoreOrdi.afficher();		
	}
	
	private void afficherOverlay() {
		this.fill(0, 32);
		this.rect(0, 0, this.width, this.height);
	}
	private void afficherOverlay(String message) {
		this.fill(0, 32);
		this.rect(0, 0, this.width, this.height);
		
		this.fill(0, 0, 155 + Math.abs((this.frameCount * 2)%200 - 100));
		this.textFont(this.arial90, 80);
		this.textAlign(CENTER, CENTER);
		this.text(message, this.width/2, this.height/2);
	}
	
	private void afficherIssueMatch() {
		int tailleHalo = 50;
		int tailleArrondi = 10;
		float saturation = 155 + Math.abs((this.frameCount * 2)%200 - 100);
		
		int largeurJoueur = 600;
		int hauteurJoueur = 150;
		
		int largeurOrdi = 600;
		int hauteurOrdi = 150;
		
		this.textFont(this.arial90, 65);
		this.textAlign(CENTER, CENTER);
		this.rectMode(CENTER);
		if(this.scoreJoueur.getValeur() > this.scoreOrdi.getValeur()) {
			this.fill(this.hue(this.bleuJoueur.getRGB()), saturation, 255);
			this.rect(this.width/2, this.height/4, largeurJoueur, hauteurJoueur, tailleArrondi);
			this.noFill();
			for (int i = 0; i < tailleHalo; i++) {
				this.stroke(this.hue(this.bleuJoueur.getRGB()), saturation, 255, 255 - i/(tailleHalo * 1F) * 255);
				this.rect(this.width/2, this.height/4, largeurJoueur + i, hauteurJoueur + i, tailleArrondi);
			}
			this.fill(255);
			this.text("Vous avez gagn� :)", this.width/2, this.height/4);
		} else {
			this.fill(this.hue(this.orangeOrdi.getRGB()), saturation, 255);
			this.rect(this.width/2, this.height/4, largeurOrdi, hauteurOrdi, tailleArrondi);
			this.noFill();
			for (int i = 0; i < tailleHalo; i++) {
				this.stroke(this.hue(this.orangeOrdi.getRGB()), saturation, 255, 255 - i/(tailleHalo * 1F) * 255);
				this.rect(this.width/2, this.height/4, largeurOrdi + i, hauteurOrdi + i, tailleArrondi);
			}
			this.fill(255);
			this.text("Vous avez perdu :(", this.width/2, this.height/4);
		}
		this.rectMode(CORNER);
	}
	
	private void afficherIndicateursBoosts() {
		this.indicateurBoostJoueur.afficher();
		this.indicateurBoostOrdi.afficher();
	}
	private void actualiserIndicateursBoosts() {
		this.indicateurBoostJoueur.actualiserVariateur();
		this.indicateurBoostOrdi.actualiserVariateur();
	}
	private void r�initialiserIndicateursBoost() {
		this.indicateurBoostJoueur.r�initialiser();
		this.indicateurBoostOrdi.r�initialiser();
	}
	
	private void g�rerLimites() {
		this.controleurLimites.traiterCollisions();
		this.controleurLimites.traiterSorties();
	}
	
	private void g�rerD�placementsJoueur() {
		if(this.keyPressed) {
			if(this.raquetteJoueur.get�tatAnim() == Raquette.�tatsAnimation.REPOS) {
				if(this.keyCode == UP)
					this.raquetteJoueur.d�placerHaut(MARGE);
				if(this.keyCode == DOWN)
					this.raquetteJoueur.d�placerBas(MARGE);
			}
			if(this.key == ' ' && this.indicateurBoostJoueur.boostPret()) {
				this.indicateurBoostJoueur.r�initialiser();
				this.raquetteJoueur.booster(1);
			}
		}
	}
	
	private void g�rerOrdinateur() {
		this.controleurOrdinateur.actualiserPosition(MARGE);
		if(this.indicateurBoostOrdi.boostPret())
			this.controleurOrdinateur.g�rerBoost(this.indicateurBoostOrdi);
	}
	
	private boolean appuiToucheEntr�e() {
		if(this.keyPressed) {
			if(this.key == ENTER)
				return true;
		}
		return false;
	}
}
