import java.awt.Color;

import processing.core.PApplet;
import processing.core.PFont;

public class Main extends PApplet {
	private final int MARGE = 10;
	private PFont arial90;
	private PFont policePong;
	
	private Color bleuJoueur, orangeOrdi;
	
	private enum État {ATTENTE_LANCEMENT, EN_COURS, TRANSITION_MANCHES, AFFICHAGE_RESULTAT}
	private État étatJeu;
	
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
		
		this.étatJeu = État.ATTENTE_LANCEMENT;
		
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
		switch (this.étatJeu) {
		case ATTENTE_LANCEMENT:
			this.afficherDécorations();
			this.filet.afficher();
			this.afficherOverlay("Appuyez sur ENTRÉE pour commencer à jouer !");
			if(this.appuiToucheEntrée()) {
				this.balle.lancer();
				this.étatJeu = État.EN_COURS;
				this.indicateurBoostJoueur.réinitialiser();
				this.indicateurBoostOrdi.réinitialiser();
			}
			break;
		case EN_COURS:
			this.afficherOverlay();
			this.afficherDécorations();
			this.filet.afficher();
			this.gérerDéplacementsJoueur();
			this.animerRaquettes();
			this.afficherRaquettes();
			this.balle.animer();
			this.gérerLimites();
			this.actualiserPPosYRaquettes();
			this.gérerOrdinateur();
			this.balle.afficher();
			this.afficherScores();
			this.afficherIndicateursBoosts();
			this.actualiserIndicateursBoosts();
			if(this.controleurLimites.isSortieBalle()) {
				this.raquetteJoueur.réinitialiser();
				this.raquetteOrdi.réinitialiser();
				if(this.scoreJoueur.getValeur() >= 10 || this.scoreOrdi.getValeur() >= 10)
					this.étatJeu = État.AFFICHAGE_RESULTAT;
				else
					this.étatJeu = État.TRANSITION_MANCHES;
				this.réinitialiserIndicateursBoost();
			}
			break;
		case TRANSITION_MANCHES:
			this.afficherOverlay();
			this.filet.animer();
			this.filet.afficher();
			this.afficherDécorations();
			this.balle.afficher();
			this.afficherRaquettes();
			this.afficherScores();
			if(this.filet.getPosCache() >= this.height) {
				this.filet.initialiserAnimation();
				this.étatJeu = État.EN_COURS;
			}
			break;
		case AFFICHAGE_RESULTAT:
			this.afficherOverlay("Appuyez sur ENTRÉE pour recommencer à jouer !");
			this.afficherDécorations();
			this.filet.afficher();
			this.afficherScores();
			this.afficherIssueMatch();
			if(this.appuiToucheEntrée()) {
				this.balle.lancer();
				this.indicateurBoostJoueur.réinitialiser();
				this.indicateurBoostOrdi.réinitialiser();
				this.scoreJoueur.réinitialiser();
				this.scoreOrdi.réinitialiser();
				this.étatJeu = État.EN_COURS;
			}
			break;
		default:
			throw new IllegalArgumentException("Cas inconnu");
		}
		//this.save("Capture/image" + this.frameCount + ".png");
	}

	private void afficherDécorations() {
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
		this.raquetteJoueur.actualiserPosYPrecédente();
		this.raquetteOrdi.actualiserPosYPrecédente();
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
			this.text("Vous avez gagné :)", this.width/2, this.height/4);
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
	private void réinitialiserIndicateursBoost() {
		this.indicateurBoostJoueur.réinitialiser();
		this.indicateurBoostOrdi.réinitialiser();
	}
	
	private void gérerLimites() {
		this.controleurLimites.traiterCollisions();
		this.controleurLimites.traiterSorties();
	}
	
	private void gérerDéplacementsJoueur() {
		if(this.keyPressed) {
			if(this.raquetteJoueur.getÉtatAnim() == Raquette.ÉtatsAnimation.REPOS) {
				if(this.keyCode == UP)
					this.raquetteJoueur.déplacerHaut(MARGE);
				if(this.keyCode == DOWN)
					this.raquetteJoueur.déplacerBas(MARGE);
			}
			if(this.key == ' ' && this.indicateurBoostJoueur.boostPret()) {
				this.indicateurBoostJoueur.réinitialiser();
				this.raquetteJoueur.booster(1);
			}
		}
	}
	
	private void gérerOrdinateur() {
		this.controleurOrdinateur.actualiserPosition(MARGE);
		if(this.indicateurBoostOrdi.boostPret())
			this.controleurOrdinateur.gérerBoost(this.indicateurBoostOrdi);
	}
	
	private boolean appuiToucheEntrée() {
		if(this.keyPressed) {
			if(this.key == ENTER)
				return true;
		}
		return false;
	}
}
