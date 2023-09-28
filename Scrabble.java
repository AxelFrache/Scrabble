public class Scrabble {
	private static char [] lettresJeton = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	private Joueur[] joueurs;
	private int numJoueur; // joueur courant (entre 0 et joueurs.length-1)
	private Plateau plateau;
	private MEE sac;
	private static int [] nbPointsJeton = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 10, 1, 2, 1, 1, 3, 8, 1, 1, 1, 1, 4, 10, 10, 10, 10};

	public Scrabble (Joueur[] joueurs) {
		this.joueurs = joueurs;
		this.numJoueur = Ut.randomMinMax(0, joueurs.length-1);
		this.plateau = new Plateau();
		int[] sacTab = {9, 2, 2, 3, 15, 2, 2, 2, 8, 1, 1, 5, 3, 6, 6, 2, 1, 6, 6, 6, 6, 2, 1, 1, 1, 1};
		this.sac = new MEE(sacTab); 
	}

	
	public String toString() {
		System.out.println(this.plateau.toString());
		System.out.println("Le joueur " + this.joueurs[this.numJoueur].getMot() + " a la main.");
		System.out.print("Chevalet: ");
		System.out.print(this.joueurs[this.numJoueur].getChevalet().lettreDansMEE(lettresJeton));
		System.out.println();
		System.out.println("Score actuel: " + this.joueurs[this.numJoueur].getScore());
		return "\n";
	}

	public void partie(){
		int i = 0, tourPasse =0;
		int[] Vainqueurs = new int[this.joueurs.length];

		//DISTRIBUTION DES JETONS AUX JOUEURS
		for(i = 0; i<this.joueurs.length;i++){
			this.joueurs[i].prendJetons(sac, 7); //chaque joueurs recupere 7 jetons dans son chevalet
		}
		
		i = Ut.randomMinMax(0, this.joueurs.length-1); //on choisit le numero du joueur qui va commencer la partie

		//DEBUT DE LA PARTIE
		while((this.sac.estVide() == false || this.joueurs[this.numJoueur].getChevalet().estVide() == false) && (tourPasse < this.joueurs.length)){ //conditions de fin de partie
			System.out.print(this.toString());
			if(this.joueurs[this.numJoueur].joue(plateau,sac,nbPointsJeton) == -1){
				tourPasse++;
			}
			else{
				tourPasse = 0;
			}

			this.numJoueur++;

			if( this.numJoueur > this.joueurs.length-1){ //fin d'un tour de table engendre un nouveau tour
				this.numJoueur=0;
			}
		}
		// FIN DE LA PARTIE

		//SI JOUEUR N'A PLUS DE JETONS ET QUE LE SAC EST VIDE :
		if(this.joueurs[this.numJoueur].getChevalet().estVide() == true && this.sac.estVide() == true){
			int joueurCourant = this.numJoueur;

			for(int l = 0; l<this.joueurs.length;l++){
				if(l != joueurCourant){

					//donne au joueur courant les points des jetons restant de chaque autre joueur
					this.joueurs[joueurCourant].ajouteScore(this.joueurs[l].nbPointsChevalet(nbPointsJeton)); 

					//on retire au joueur l le nombre de points de ses jetons restants
					this.joueurs[l].ajouteScore(-this.joueurs[l].nbPointsChevalet(nbPointsJeton));
				}
			}
		}
		//SI TOUS LES JOUEURS ONT PASSE LEUR TOUR :
		else if(tourPasse == this.joueurs.length){
			for(int m = 0; m<this.joueurs.length;m++){
				//on retire a tous les joueurs le nombres de points de leur jetons restants
				this.joueurs[m].ajouteScore(-this.joueurs[m].nbPointsChevalet(nbPointsJeton));
			}
		}

		int scoreMax=this.joueurs[0].getScore();
		System.out.println(scoreMax);
		//CALCUL DES SCORES FINAUX
		for(int j = 0; j<this.joueurs.length;j++){ //determine le score max
			System.out.println(this.joueurs[j].getMot() + " : " + this.joueurs[j].getScore()); //score de chaque joueur

			if(this.joueurs[j].getScore() > scoreMax){
				scoreMax = this.joueurs[j].getScore();
			}
			System.out.println(scoreMax);
		}

		//AFFICHAGE DES GAGNANTS
		for(int k = 0; k<this.joueurs.length;k++){ //attribue la valeur 1 a tous les joueurs disposant du score max (les gagnants si ex aequo)	
			if(this.joueurs[k].getScore() == scoreMax){
				Vainqueurs[k] = 1; 
				System.out.println("Le joueur " + this.joueurs[k].getMot() + " a gagné la partie.");
			}	
		}

	}
	
}