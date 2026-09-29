package com.uqo.appgui;

import com.uqo.applogique.AppGps;
import com.uqo.appmain.AppConstantes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**Classe: AppGrille
 * Description:
 * Le but de cette classe est de dessiner la grille (la carte) et d'être impliquée dans tout ce qui ajoute ou retire des composants visuels à celle-ci.
 **/

public class AppGrille extends JPanel {

	// Initialisations necessaires au foncitonnement du code de cette classe
	
    private int[][] grille;
    private Point choisiPointDepart;
    private Point choisiPointFin;
    private AppVoiture voiture=null;
    private AppGps gps;
    public void setGps(AppGps gps) {
        this.gps = gps;
    }

    private List<Point> chemin = new ArrayList<>();

    // constructeur de la methode AppGrille
    public AppGrille(int[][] grille) {
        
    	this.grille = grille;           // Initialization de la grille
        
        // Initialisation du GPS (algorithme de calcul)
        this.gps = new AppGps(grille);  
        setGps(gps);  

        // L'ordre du dessin est crucial (sinon, les routes et points de depart/arrivee ne seront plus visibles
        fillTerrain();          			
        dessinerRoutesPredefinies();  		
        setPointsDepartFin(); 				
    }

    
/*METHODES POUR LE DESSIN INITIAL DU TERRAIN, DES ROUTES, ET POINTS DE DEPART/ARRIVEE*/
    
    // Methode qui permet de dessiner le terrain
    private void fillTerrain() {
        for (int row = 0; row < grille.length; row++) {
            for (int col = 0; col < grille[0].length; col++) {
                grille[row][col] = AppConstantes.CASE_TERRAIN;
            }
        }
    }

    // Methode qui definit les coordonnees des routes a dessiner
    private void dessinerRoutesPredefinies() {
        
    	// Les coordonnees suivent ce format : (x debut, y debut, x arrivee, y arrivee)
    	
    	// Routes horizontales
        dessinerRoutes(0, 0, 0, 13); dessinerRoutes(0, 14, 0, 45); dessinerRoutes(5, 0, 5, 40);
        dessinerRoutes(5, 42, 5, 45); dessinerRoutes(7, 17, 7, 26); dessinerRoutes(7, 28, 7, 45);
        dessinerRoutes(10, 0, 10, 1); dessinerRoutes(10, 3, 10, 12); dessinerRoutes(11, 41, 11, 44);
        dessinerRoutes(13, 0, 13, 29); dessinerRoutes(13, 31, 13, 45); dessinerRoutes(15, 12, 15, 16);
        dessinerRoutes(15, 18, 15, 30); dessinerRoutes(19, 0, 19, 41); dessinerRoutes(19, 43, 19, 45);
        dessinerRoutes(26, 1, 26, 25); dessinerRoutes(26, 27, 26, 45); dessinerRoutes(11, 45, 11, 45);

        // Routes verticales
        dessinerRoutes(0, 0, 26, 0); dessinerRoutes(5, 12, 19, 12); dessinerRoutes(5, 2, 9, 2);
        dessinerRoutes(10, 2, 19, 2); dessinerRoutes(19, 7, 26, 7); dessinerRoutes(1, 13, 5, 13);
        dessinerRoutes(0, 17, 26, 17); dessinerRoutes(16, 26, 26, 26);
        dessinerRoutes(0, 27, 7, 27); dessinerRoutes(8, 27, 15, 27); dessinerRoutes(5, 30, 12, 30);
        dessinerRoutes(13, 30, 19, 30); dessinerRoutes(0, 35, 26, 35); dessinerRoutes(0, 41, 5, 41);
        dessinerRoutes(6, 41, 13, 41); dessinerRoutes(13, 42, 18, 42); dessinerRoutes(19, 42, 26, 42);
        dessinerRoutes(0, 45, 6, 45); dessinerRoutes(7, 45, 10, 45); dessinerRoutes(8, 45, 26, 45);
        dessinerRoutes(12, 45, 19, 45);
    }

    // Methode qui dessine les routes
    public void dessinerRoutes(int startRow, int startCol, int endRow, int endCol) {
        if (startRow == endRow) {
            for (int x = Math.min(startCol, endCol); x <= Math.max(startCol, endCol); x++) {
                grille[startRow][x] = AppConstantes.CASE_ROUTE;
            }
        } else if (startCol == endCol) {
            for (int y = Math.min(startRow, endRow); y <= Math.max(startRow, endRow); y++) {
                grille[y][startCol] = AppConstantes.CASE_ROUTE;
            }
        }
    }
    
    
    // Methode qui definit et dessine les coordonnees des points de depart et d'arrivee
    public void setPointsDepartFin() {
        int[][] departPoints = {{26, 1}, {26, 23}, {26, 45}, {11, 45}, {19, 7}};
        int[][] finPoints = {{10, 4}, {0, 19}, {15, 16}, {13, 31}, {5, 22}, {19, 38}};

        for (int[] point : departPoints) {
            if (estNavigable(point[0], point[1])) {
                grille[point[0]][point[1]] = AppConstantes.CASE_DEBUT;
            }
        }

        for (int[] point : finPoints) {
            if (estNavigable(point[0], point[1])) {
                grille[point[0]][point[1]] = AppConstantes.CASE_FIN;
            }
        }
    }

/*LOGIQUE DU DESSIN*/
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int tileWidth = getWidth() / AppConstantes.LARGEUR_GRILLE;
        int tileHeight = getHeight() / AppConstantes.HAUTEUR_GRILLE;
        int tileSize = Math.min(tileWidth, tileHeight);

        for (int y = 0; y < AppConstantes.HAUTEUR_GRILLE; y++) {
            for (int x = 0; x < AppConstantes.LARGEUR_GRILLE; x++) {
                g.setColor(getCouleurCase(grille[y][x]));
                g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                g.setColor(Color.BLACK);
                g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
        
        // Dessiner la voiture seulement si elle est initialisee
        if (voiture != null) {
            voiture.dessinerVoiture(g, tileSize);  
        }
    }

    // Methode qui permet de recuperer les couleurs de chaque type de case
    private Color getCouleurCase(int type) {
        return switch (type) {
            case AppConstantes.CASE_ROUTE -> Color.decode(AppConstantes.COULEUR_ROUTE);
            case AppConstantes.CASE_TERRAIN -> Color.decode(AppConstantes.COULEUR_TERRAIN);
            case AppConstantes.CASE_DEBUT -> Color.decode(AppConstantes.COULEUR_DEBUT);
            case AppConstantes.CASE_FIN -> Color.decode(AppConstantes.COULEUR_FIN);
            case AppConstantes.CASE_ACCIDENT -> Color.decode(AppConstantes.COULEUR_ACCIDENT);
            case AppConstantes.CASE_TRAFIC -> Color.decode(AppConstantes.COULEUR_TRAFIC);
            case AppConstantes.CASE_POINT_CHOISI_DEBUT -> Color.decode(AppConstantes.COULEUR_POINT_CHOISI_DEBUT);
            case AppConstantes.CASE_POINT_CHOISI_FIN -> Color.decode(AppConstantes.COULEUR_POINT_CHOISI_FIN);
            case AppConstantes.CASE_CHEMIN_CALCULE -> Color.decode(AppConstantes.COULEUR_CHEMIN_CALCULE);
            default -> Color.WHITE;
        };
    }

/*LOGIQUE D'INTERACTION DE LA GRILLE*/
   
    // Methode qui permet de determiner si la voiture peut passer sur les types de cases differents
    boolean estNavigable(int row, int col) {
        int type = grille[row][col];
        return switch (type) {
            case AppConstantes.CASE_ROUTE,
                 AppConstantes.CASE_POINT_CHOISI_DEBUT,
                 AppConstantes.CASE_POINT_CHOISI_FIN,
                 AppConstantes.CASE_DEBUT,
                 AppConstantes.CASE_FIN,
                 AppConstantes.CASE_CHEMIN_CALCULE -> true;  
            default -> false;
        };
    }
    
    // Methode qui permet la representation visuelle de selection des points de debut par l'utilisateur
    public void setChoisiPointDepart(int row, int col) {
        
    	if (!estNavigable(row, col)) return;

        Point clicked = new Point(col, row);

        // Effacement de la selection
        if (choisiPointDepart != null) {
            grille[choisiPointDepart.y][choisiPointDepart.x] = AppConstantes.CASE_DEBUT;
        }

        // Selection
        choisiPointDepart = clicked;
        grille[row][col] = AppConstantes.CASE_POINT_CHOISI_DEBUT;
        repaint();
    }

/* METHODES QUI EFFACENT*/
    
    // Methode qui permet la representation visuelle de selection des points d'arivee par l'utilisateur
    public void setChoisiPointFin(int row, int col) {
        if (!estNavigable(row, col)) return;

        Point clicked = new Point(col, row);

     
        // Effacement de la selection
        if (choisiPointFin != null) {
            grille[choisiPointFin.y][choisiPointFin.x] = AppConstantes.CASE_FIN;
        }

        // Selection
        choisiPointFin = clicked;
        grille[row][col] = AppConstantes.CASE_POINT_CHOISI_FIN;
        repaint();
    }

    // Methode qui permet de reinitialiser les cases selectionnees apres la fin de navigation
    public void resetPointsDepartFin() {

        if (choisiPointDepart != null) {
            grille[choisiPointDepart.y][choisiPointDepart.x] = AppConstantes.CASE_DEBUT;
        }
        if (choisiPointFin != null) {
            grille[choisiPointFin.y][choisiPointFin.x] = AppConstantes.CASE_FIN;
        }

        choisiPointDepart = null;
        choisiPointFin = null;

        repaint();
        
    }
    
    // Methode qui permet d'effacer la voiture de la carte
    public void clearCar() {
        this.voiture = null;
        clearChemin();  			
        this.repaint();  		
    }
    
    // Methode qui permet d'effacer les accidents  s'il y a lieu
    public void clearAccidents() {
        for (int row = 0; row < grille.length; row++) {
            for (int col = 0; col < grille[0].length; col++) {
               
                if (grille[row][col] == AppConstantes.CASE_ACCIDENT) {
                    grille[row][col] = AppConstantes.CASE_ROUTE;  
                }
            }
        }
        repaint();  		
    }

    // Methode qui permet d'effacer le chemin calcule 
    public void clearChemin() {
        if (chemin != null) {
            for (Point p : chemin) {
                if (grille[p.y][p.x] == AppConstantes.CASE_CHEMIN_CALCULE) {
                    grille[p.y][p.x] = AppConstantes.CASE_ROUTE;
                }
            }
            chemin = null;
            repaint();
        }
    }

    // Methode qui permet d'effacer le trafic
    public void clearTrafic() {
        for (int y = 0; y < grille.length; y++) {
            for (int x = 0; x < grille[y].length; x++) {
                if (grille[y][x] == AppConstantes.CASE_TRAFIC) {
                    grille[y][x] = AppConstantes.CASE_ROUTE;
                }
            }
        }
    }
    
    // Methode qui permet de reinitialiser la grille
    public void resetNavigation() {
        this.clearChemin();  // Clear the path from the grid
        this.voiture = null;  // Remove the car
        this.repaint();  // Redraw the grid with no car and no path
    }
    
/* GETTERS, SETTERS , ET METHODES QUI DESSINENT*/
    
    // Methode qui permet de dessiner la voiture
    public void spawnVoiture(Point start) {
        
    	// Le if permet de prevenir un bug qui effacait la route des le debut de la navigation
        if (voiture == null) {
            voiture = new AppVoiture(start.x, start.y, this, this.chemin); 
            this.repaint();  
        }
    }

/*GETTERS ET SETTERS ESSENTIELS*/
    
    public int[][] getGrille() {
        return grille;
    }

    public Point getChoisiPointDepart() {
        return choisiPointDepart;
    }

    public Point getChoisiPointFin() {
        return choisiPointFin;
    }

    public void setVoiture(AppVoiture voiture) {
        this.voiture = voiture;
    }
    
    // Dessin du chemin calcule
    public void setChemin(List<Point> path) {
        this.chemin = path;

        for (Point p : path) {
            if (choisiPointDepart != null && p.equals(choisiPointDepart)) continue;
            if (choisiPointFin != null && p.equals(choisiPointFin)) continue;

            grille[p.y][p.x] = AppConstantes.CASE_CHEMIN_CALCULE;
        }

        repaint();
    }

    // Dessin du trafic
    public List<Point> getTrafficBlocks() {
        List<Point> traficBlocks = new ArrayList<>();

        for (int row = 0; row < grille.length; row++) {
            for (int col = 0; col < grille[0].length; col++) {
                if (grille[row][col] == AppConstantes.CASE_TRAFIC) {
                    traficBlocks.add(new Point(col, row)); // Add the traffic block to the list
                }
            }
        }

        return traficBlocks;
    }
    
 /* CHEMIN APRES RECALCULATION*/
    
    public void updateCheminApresTrafic(boolean traficPlaced) {
    	
    	// Prevention d'erreurs reliees au null 
    	if (this.getChoisiPointDepart() != null && this.getChoisiPointFin() != null) {
    	    gps.setChoisiPointDepart(this.getChoisiPointDepart());
    	    gps.setChoisiPointFin(this.getChoisiPointFin());
    	} else {
    	    return;
    	}

    	if (!traficPlaced) {
            return;
        }

        if (gps == null) {
            return;
        }

        // Recalcul du chemin si le trafic est present; effacement du vieux chemin s'il y a lieu
        List<Point> traficBlocks = getTrafficBlocks(); 
        List<Point> newPath = gps.recalculerCheminTrafic(true, traficBlocks); 
        
        if (gps.estRecalculTriggered() && newPath != null && !newPath.isEmpty()) {
            clearChemin(); 
            setChemin(newPath); 
        } 
    }
  
    //Methode qui permet de voir une cellule de la grille
    public int getTile(int row, int col) {
        return grille[row][col];
    }
}
