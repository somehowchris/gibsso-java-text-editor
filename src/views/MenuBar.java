package views;

//	MyMenu2.java
//	******************************************************************************************************************************
//	Dieses Beispielprogramm erzeugt :
//		- eine Menuleiste (JMenuBar)  mit zwei Menus und mehreren Menueinträgen je Menu
//		- eine Werkzeugleiste (JToolBar) mit drei Symbolen
//
//	In dieser Variante werden zuerst Actions (mit Bild und Text) definiert, welche dann direkt sowohl als Menueinträge als auch
//	als Symbole (Funktionen) auf der Werzeugleiste verwendet werden können.
//
//	Diese Ansatz ist interessant, wenn (wie bei heutigen GUI-Anwendungen üblich) hinter Menueinträgen und Symbolen
//	diesselben Funktionen stecken. Die Action (Klasse AbstractAction als Defaultimplementation der Schnittstelle Action) beinhalten
//	den Text, das Bild und die auszuführende Aktion selbst.
//										// Importieren aller Klassen aus java.awt
import enums.Commands;

import javax.swing.*;
import java.util.List;

public class MenuBar extends JMenuBar {

    public MenuBar(List<Menu> menus) {
        menus.forEach(el->add(el));
    }
}
