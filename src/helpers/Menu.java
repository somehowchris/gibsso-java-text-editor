package helpers;

import javax.swing.*;
import javax.swing.Action;
import java.util.List;

public class Menu extends JMenu{
    public Menu(String title, List<Action> actions){
        super(title);
        actions.forEach(el -> add(el));
    }
}
