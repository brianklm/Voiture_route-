package com.uqo.appgui;

import com.uqo.applogique.AppGps;
import com.uqo.applogique.AppObstacle;
import com.uqo.appmain.AppConstantes;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Classe: AppVoiture
 * Description:
 * Cette classe contient la logique de la voiture.
 **/

public class AppVoiture {
    private int x, y; 
    private int moveCount;
    private boolean traficPresent = false;
    public void resetTraficPresent() {
        this.traficPresent = false;
    }
    
    private boolean euAccident = false;
    public boolean euAccident() {
        return euAccident;
    }
    
    private AppGps gps;
    private AppGrille grille;
    
    private List<Point> path = null;
    private int cheminIndex = 0;

    // Methode principale de la voiture
    public AppVoiture(int x, int y, AppGrille grille, List<Point> chemin) {
        this.x = x;
        this.y = y;
        this.grille = grille;
        this.path = chemin;
        this.cheminIndex = 0;

        if (!chemin.isEmpty()) {
            this.x = chemin.get(0).x;
            this.y = chemin.get(0).y;
        }

        grille.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                AppVoiture.this.bougerVoiture(e);
            }
        });

        grille.setFocusable(true);
        grille.requestFocusInWindow();
    }
    
    // Methode definissant le mouvement de la voiture
    public void bougerVoiture(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int dx = 0, dy = 0;
        if (euAccident) return;
        switch (keyCode) {
            case KeyEvent.VK_UP -> dy = -1;
            case KeyEvent.VK_DOWN -> dy = 1;
            case KeyEvent.VK_LEFT -> dx = -1;
            case KeyEvent.VK_RIGHT -> dx = 1;
        }

        moveCount++;
        if (moveCount == 3 && !traficPresent && path != null) {
            for (Point p : AppObstacle.getPointsDeTraficDuChemin(path)) {
                if (path.contains(p)) {
                    grille.getGrille()[p.y][p.x] = AppConstantes.CASE_TRAFIC;
                }
            }
            traficPresent = true;
            grille.repaint();
            grille.updateCheminApresTrafic(traficPresent);
        }


        int newX = x + dx;
        int newY = y + dy;

        // Check if new position is out of bounds
        if (newX < 0 || newY < 0 || newX >= AppConstantes.LARGEUR_GRILLE || newY >= AppConstantes.HAUTEUR_GRILLE)
            return;

        int[][] grid = grille.getGrille();

        
     // Bloc if pour detecter l'arrivee au point final
        if (grid[newY][newX] == AppConstantes.CASE_POINT_CHOISI_FIN ) { 
   
            // Message de succes
            new Thread(() -> {
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                EventQueue.invokeLater(() -> {
                    javax.swing.JOptionPane.showMessageDialog(grille, "🏁 Félicitations ! Vous êtes arrivés à votre destination ! Vous pouvez quitter la navigation, ou continuer à vous promener.");
                });
            }).start();
            grille.repaint();
        }

        
        
        // Bloc if pour detecter un accident
        if (grid[newY][newX] == AppConstantes.CASE_TRAFIC) {
            if (!euAccident) {
                euAccident = true; 
            }
            // Dessiner l'accident
            grid[newY][newX] = AppConstantes.CASE_ACCIDENT;

            // Bloquer le mouvement de l'utilisateur
            int trapX = x;  
            int trapY = y;
            if (trapX >= 0 && trapY >= 0 && trapX < AppConstantes.LARGEUR_GRILLE && trapY < AppConstantes.HAUTEUR_GRILLE) {
                grid[trapY][trapX] = AppConstantes.CASE_TRAFIC;
            }
            
            // Message pour l'utilisateur
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                EventQueue.invokeLater(() -> {
                    javax.swing.JOptionPane.showMessageDialog(grille, "💥 Oups ! Vous avez eu un accident dans le trafic. Veuillez quitter la navigation.");
                   
                });
            }).start();
            grille.repaint();
            euAccident = true;
        }

        // Bloc if pour le mouvement normal de la voiture
        if (grille.estNavigable(newY, newX)) {
            x = newX;
            y = newY;

            if (path != null && cheminIndex + 1 < path.size()) {
                Point next = path.get(cheminIndex + 1);
                if (next.x == x && next.y == y) {
                    cheminIndex++;
                }
            }

            grille.repaint();
        }
    }

    // Methode pour dessiner la voiture sur la grille
    public void dessinerVoiture(Graphics g, int tileSize) {
        g.setColor(Color.RED);
        g.fillOval(x * tileSize, y * tileSize, tileSize, tileSize);
    }
}
