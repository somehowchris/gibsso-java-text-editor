package views;

import enums.Commands;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Action extends AbstractAction {

    public Commands cmd;
    public Action(String name, Commands cmd, String iconPath){
        putValue(Action.NAME, name);
        putValue(Action.ACTION_COMMAND_KEY, cmd.getCommand());

        this.cmd = cmd;

        if(iconPath != null){
            putValue(Action.SMALL_ICON, new ImageIcon(iconPath));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
