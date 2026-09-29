package com.uqo.appmain;

import com.uqo.appgui.AppFenetrePrincipale;

/**Classe: AppMain
 * Description:
 * Cette classe sert simplement a initialiser le programme en ouvrant la fenetre principale
 **/

public class AppMain {
    public static void main(String[] args) {
        System.out.println("Bienvenue!");
        javax.swing.SwingUtilities.invokeLater(() -> new AppFenetrePrincipale());
    }
}