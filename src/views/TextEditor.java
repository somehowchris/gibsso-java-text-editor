package views;

import enums.Commands;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;

public class TextEditor extends JFrame {

    JTextArea editor;
    public TextEditor(){
        super();
        Menu datei = new Menu("Datei", Arrays.asList(open, save, close));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Text Editor 101");
        setSize(920, 640);
        setLayout(new BorderLayout());
        editor = eingabebereich();
        add(new ToolBar(Arrays.asList(open,save, cut(), copy(), paste() )), BorderLayout.PAGE_START);
        Menu bearbeiten = new Menu("Bearbeiten", Arrays.asList(cut(),copy(),paste(),selectAll()));
        setJMenuBar(new MenuBar(Arrays.asList(datei,bearbeiten)));
    }

    public JTextArea eingabebereich(){
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane pane = new JScrollPane(textArea);
        add(pane, BorderLayout.CENTER);
        return textArea;
    }

    Action save = new Action("Speichern", Commands.SAVE,"src/assets/save.gif"){
        public void actionPerformed(ActionEvent e){

        }
    };

    Action open = new Action("Öffnen",Commands.OPEN,"src/assets/open.gif"){
        public void actionPerformed(ActionEvent e){
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                System.out.println(selectedFile.getAbsolutePath());
                editor.read
            }
        }
    };
    Action close = new Action("Beenden",Commands.CLOSE,null){
        public void actionPerformed(ActionEvent e){

        }
    };

    private javax.swing.Action selectAll(){
        javax.swing.Action selectAll = editor.getActionMap().get(DefaultEditorKit.selectAllAction);
        selectAll.putValue(Action.NAME, "Alles markieren");
        return selectAll;
    }

    public javax.swing.Action copy(){
        javax.swing.Action copy = editor.getActionMap().get(DefaultEditorKit.copyAction);
        copy.putValue(Action.NAME, "Kopieren");
        copy.putValue(Action.SMALL_ICON, new ImageIcon("src/assets/copy.gif"));
        return copy;
    }

    public javax.swing.Action cut(){
        javax.swing.Action cut = editor.getActionMap().get(DefaultEditorKit.cutAction);
        cut.putValue(Action.NAME, "Ausschneiden");
        cut.putValue(Action.SMALL_ICON, new ImageIcon("src/assets/cut.gif"));
        return cut;
    }

    public javax.swing.Action paste(){
        javax.swing.Action paste = editor.getActionMap().get(DefaultEditorKit.pasteAction);
        paste.putValue(Action.NAME, "Einfügen");
        paste.putValue(Action.SMALL_ICON, new ImageIcon("src/assets/paste.gif"));
        return paste;
    }

}
