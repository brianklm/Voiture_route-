package com.uqo.appgui;

import com.uqo.appmain.AppConstantes;

import javax.swing.*;
import java.awt.*;

/**
 * Classe: AppFenetrePrincipale
 * Description:
 * Cette classe est la fenêtre principale de l'application.
 * Elle contient le panneau de la grille, et initialise l'interface utilisateur.
 **/

public class AppFenetrePrincipale extends JFrame {

    public AppFenetrePrincipale() {
        super("Simulation de Système de Guidage");

        // Creation de la grille (carte)
        int[][] grille = new int[AppConstantes.HAUTEUR_GRILLE][AppConstantes.LARGEUR_GRILLE];
        AppGrille grillePanel = new AppGrille(grille);

        // Creation du panneau contenant les boutons, options, etc
        AppOptions optionsPanel = new AppOptions(new Dimension(200, 700), grillePanel); // Initialize optionsPanel here

        // Division de l'ecran
        JSplitPane diviserPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, grillePanel, optionsPanel);
        diviserPane.setResizeWeight(0.8);

        // Effacement de l'option d'elargir le panneau d'options
        diviserPane.setEnabled(false);
        diviserPane.setDividerSize(1); 
        add(diviserPane);

        // Taille standard de la fenetre principale
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}



