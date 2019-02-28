package helpers;

import javax.swing.*;
import javax.swing.Action;
import java.util.List;

public class ToolBar extends JToolBar {
    public ToolBar(List<Action> actions){
        super();
        actions.forEach(el -> add(el));
    }
}
