package views;

import javax.swing.*;
import javax.swing.Action;
import java.util.List;

public class ToolBar extends JToolBar {
    public ToolBar(List<Action> actions){
        actions.forEach(el -> add(el));
    }
}
