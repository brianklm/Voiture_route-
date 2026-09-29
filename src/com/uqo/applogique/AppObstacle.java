package com.uqo.applogique;

import com.uqo.appgui.AppGrille;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe: AppObstacle
 * Description:
 * Cette classe contient la logique des obstacles (le trafic).
 **/

public class AppObstacle {
    private static List<Point> trafic = new ArrayList<>();
    
    // Coordonnees du trafic predeterminees
    static {
    	trafic.add(new Point(27, 5));
    	trafic.add(new Point(0, 19));
    	trafic.add(new Point(10, 12));
    	trafic.add(new Point(17, 12));
    	trafic.add(new Point(42, 19));
    	trafic.add(new Point(13, 0));
    	trafic.add(new Point(27, 0));
    	trafic.add(new Point(2, 20));
    	trafic.add(new Point(27, 15));
    	trafic.add(new Point(35, 19));
      	trafic.add(new Point(12, 15));
    	trafic.add(new Point(45, 16));
     	trafic.add(new Point(17, 5));
    	trafic.add(new Point(17, 19));
    	trafic.add(new Point(17, 13));
    	trafic.add(new Point(35, 13));
    	trafic.add(new Point(2, 10));


    }

    // Methode pour verifier si le premier chemin calcule contient du trafic
    public static List<Point> getPointsDeTraficDuChemin(List<Point> chemin) {
        List<Point> traficSurChemin = new ArrayList<>();
        for (Point point : chemin) {
            if (trafic.contains(point)) {
                traficSurChemin.add(point);
            }
        }
        return traficSurChemin;
    }

    // Methode pour verifier la presence de plusieurs points de trafic, ou d'aucun 
    public static Point selectPointTraficRandom(List<Point> path) {
        List<Point> traficChemin2 = getPointsDeTraficDuChemin(path);
        
        // Pas de trafic
        if (traficChemin2.isEmpty()) {
            return null;  
        }

        // Choix d'un point de trafic au hasard s'il y en a plusieurs
        int index = (int)(Math.random() * traficChemin2.size());
        return traficChemin2.get(index);

    }
    private static boolean cheminRecalcule = false;

    // Methode pour causer le recalcul du chemin plus court
    public static void triggerRecalculChemin(List<Point> chemin, AppGps gps, AppGrille grille) {
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                if (!cheminRecalcule) {
                    Point trafficPoint = selectPointTraficRandom(chemin);
                    List<Point> nouveauChemin = null;

                    if (trafficPoint != null) {
                        nouveauChemin = gps.recalculerCheminTrafic(false, getPointsDeTraficDuChemin(chemin));
                    } 

                    // Effacer le vieux chemin
                    grille.clearChemin();

                    // Creation du nouveau chemin
                    if (nouveauChemin != null && !nouveauChemin.isEmpty()) {
                        
                        grille.setChemin(nouveauChemin);
                        grille.repaint();
                    }

                    cheminRecalcule = true;
                }
            }
        }, 3000);
    }
}

