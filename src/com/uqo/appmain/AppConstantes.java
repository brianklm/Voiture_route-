package com.uqo.appmain;

/**
 * Classe: AppConstantes
 * Description:
 * Cette classe contient les constantes utilisées dans le reste des classes
 * Ces constantes sont utilisées pour définir des paramètres fixes dans l'application,
 * tels que les dimensions du tableau et les couleurs des cases.
 **/

public class AppConstantes {

    // Dimensions de la grille (graphe representant la carte)
    public static final int LARGEUR_GRILLE = 46;  
    public static final int HAUTEUR_GRILLE = 27; 

    // Types de cases dans la grille		
    public static final int CASE_TERRAIN = 0; 
    public static final int CASE_ROUTE = 1;  	   
    public static final int CASE_DEBUT = 2;  	
    public static final int CASE_FIN = 3;   	 	
    public static final int CASE_ACCIDENT = 4; 		
    public static final int CASE_TRAFIC = 5;		
    public static final int CASE_POINT_CHOISI_DEBUT = 6;
    public static final int CASE_POINT_CHOISI_FIN = 7;
    public static final int CASE_CHEMIN_CALCULE = 8; 

    // Couleurs (utilisées dans l'interface graphique)
    public static final String COULEUR_TERRAIN = "#B9F75C";  			// Vert
    public static final String COULEUR_ROUTE = "#C3C3C3"; 				// Gris 
    public static final String COULEUR_DEBUT = "#4E11BF"; 				// Bleu
    public static final String COULEUR_FIN = "#9D05AD";   				// Mauve
    public static final String COULEUR_TRAFIC = "#FFF200"; 				// Jaune 
    public static final String COULEUR_ACCIDENT = "#FF7F27"; 			// Orange 
    public static final String COULEUR_POINT_CHOISI_DEBUT = "#23FCF9"; 	// Turquoise
    public static final String COULEUR_POINT_CHOISI_FIN = "#FF80D2";   	// Rose
    public static final String COULEUR_CHEMIN_CALCULE = "#F1FFC9"; 		// Vert-jaune tres pale  

}



