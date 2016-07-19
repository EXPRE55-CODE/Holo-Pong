Comment voir et modifier le code source ?

1. Télécharger le code source
  1. Rendez-vous à cette adresse : https://github.com/EXPRE55-CODE/Holo_Pong
  2. Cliquez sur le menu vert déroulant sur la droite appelé "Clone or download".
  3. Cliquez sur "Download ZIP" et c'est ok !
  4. Sur votre ordinateur, créez un dossier appelé "Eclipse" à l'endroit souhaité et copiez l'adresse de ce dernier.
        ==> Attention, si vous le déplacez ensuite, il faudra le relocaliser.
  5. Extrayez le dossier contenu dans l'archive téléchargée dans le dossier créé et renommez-le "Holo Pong".
  
2. Installer l'IDE Éclipse
  1. Téléchargez l'installeur ici : https://www.eclipse.org/downloads/download.php?file=/oomph/epp/neon/R/eclipse-inst-win64.exe.
  2. Ouvrez l'installeur et sélectionnez "Eclipse IDE for Java Developers".
  3. Cliquez sur "INSTALL" et attendre.
      ==> Si ça ne marche pas, cliquez sur le bouton menu en haut à droite, puis "ADVANCED MODE..." > "Yes" > "OK" > "Next" X3 > "Finish".
  4. Quand c'est fini, quittez l'installeur et ouvrez Eclipse.

3. Ouvrir le projet Holo Pong
  1. Une petite fenêtre apparaît, dans le champ "Workspace", collez l'adresse du dossier (précédemment copiée) et cliquez sur "ok".
  2. Si une fenêtre concernant la compatibilité s'ouvre, cliquez sur "Yes" (oui on s'en fout lol).
  3. Le Grand Eclipse s'ouvre !
  4. [Facultatif] Bon perso je vous conseille le thème sombre. Pour l'activer, allez sur "Window" (barre de Menus) > "Preferences"
     et déroulez "General" puis cliquez sur "Appearance". Une fois dans ce menu, déroulez le menu déroulant "Theme", choisissez "Dark"
     et cliquez sur "OK".
  5. On va charger le projet maintenant ! Allez sur "File" (barre de menus) > "New" > "Java Project" et entrez "Holo Pong" dans le champ
     "Poject name", normalement, une partie de l'interface devrait se griser.
  6. Cliquez sur "Finish", la fenêtre se ferme. Fermez l'onglet central, normalement un item "Holo Pong" devrait apparaitre dans le menu de gauche (appelé
     "Package Explorer"). Le projet est ouvert :)
  7. Là il doit y avoir une petite croix sur l'icône du projet qui signale une erreur, c'est parfaitement normal, il ne sait pas où est Processing :/
  
4. Localiser la bibliothèque Processing
  1. Faites un clic droit sur l'item du projet appelé "Holo Pong" et allez dans "Build Path" > "Add Libraries".
  2. Dans la petite fenêtre qui vient de s'ouvrir, cliquez sur "User Library" et sur "Suivant".
  3. Dans le menu suivant, cliquez sur le bouton "User Libraries..." à droite (oui encore^^).
  4. Alors là on en est à... 3 fenêtre oui ! Ben on va en ouvrir une 4ème en cliquant sur "New..." et en entrant "Processing" dans le champ "User Library Name".
  5. Appuyez sur "OK" puis sur le bouton "Add External JARs..." sur la droite.
  6. Dans le sélecteur de fichiers, rendez-vous là où vous avez extrait le dossier "Holo Pong" puis dans "lib".
  7. Sélectionnez tous les fichiers et appuyez sur "Ouvrir".
  8. On a localisé Processing ! Maintenant on va cliquer sur "OK" puis on va simplement fermer la fenêtre mère si le bouton "Finish" est grisé.
  
5. Premier lancement
	1. Déroulez l'item "Holo Pong" puis "src", c'est là que se trouve le code du jeu.
	2. Déroulez (default package) si ce n'est pas déjà fait, puis cliquez sur l'item "Main.java", qui est en quelque sorte le "début" du programme.
	3. Sauvegardez la classe en appuyant sur ctrl+s et appuyez sur la flèche verte dans la barre d'outils pour lancer le jeu.
	4. Sélectionnez "Java Application" dans la 1ère petite pop-up puis cliquez sur "ok" dans le seconde. Appuyez sur ECHAP pour quitter le jeu.

6. Modifier le jeu
	1. Bon ben là vous allez pouvoir vous amuser, tenez, on va rendre la balle rose ! Pour cela, double-cliquez sur l'item "Balle.java" qui correspond à la classe "Balle" (ce que j'appelle "module" dans la vidéo")
	2. Vous venez d'ouvrir le code source de la balle^^
	3. Dans le volet "Outline" à droite, cliquez sur l'item "Afficher", cela correspond à une méthode (un bout de code en fait), là, c'est celui qui va littéralement dessiner la balle.
	4. Je ne vais pas trop détailler, sachez juste que la couleur des éléments est définit par l'appel de la fonction "fill" de Processing.
	5. Du coup, au-dessus de la ligne 71, insérez une ligne et marquez : "this.p.colorMode(PApplet.RGB)"
	6. Remplacez le ligne 72 par this.p.fill(255, 127, 255);
	7. En-dessous de la ligne 72, insérez une ligne et marquez : "this.p.colorMode(PApplet.HSB)"
	8. Voilà donc là on vient de changer la couleur de remplissage par une couleur qui contient 100% de rouge, 50% de vert et 100% de bleu.
	9. Si vous voulez en apprendre plus sur Processing, je vous conseille d'aller faire un tour ici : https://processing.org/
