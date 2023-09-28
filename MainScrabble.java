
public class MainScrabble {
	public static void main(String args[]) {

		int nbJoueurs = 0;
		while (nbJoueurs < 1 || nbJoueurs > 4) {
			System.out.println("Combien de joueurs souhaitent participer ? (maximum: 4)");
			System.out.print("Nombre de joueurs: "); nbJoueurs = Ut.saisirEntier(); //l utilisateur entre le nombre de joueurs qui participent
		}
		System.out.println();

		String[] nomJoueur = new String[nbJoueurs];
		Joueur[] joueurDansLaPartie = new Joueur[nbJoueurs];
		int i = 0;

		System.out.println("Veuillez saisir les noms des participants : ");
		while (i < nbJoueurs) {
			System.out.print("Nom du joueur n°" + (i+1) + ": ");
			nomJoueur[i] = Ut.saisirChaine(); //l utilisateur entre les noms des joueurs
			joueurDansLaPartie[i] = new Joueur(nomJoueur[i]);
			i++;
		}
		
		Scrabble s = new Scrabble(joueurDansLaPartie);
		s.partie();
	}

}
