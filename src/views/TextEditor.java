package views;

import enums.Commands;
import helpers.ToolBar;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

public class TextEditor extends JFrame {

    JTextArea editor;
    String currentFilePath;
    UndoManager manager;

    public TextEditor(){
        super();
        helpers.Menu datei = new helpers.Menu("Datei", Arrays.asList(open, save, close));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Text Editor 101");
        setSize(920, 640);
        setLayout(new BorderLayout());
        editor = eingabebereich();
        manager = new UndoManager();
        editor.getDocument().addUndoableEditListener(manager);
        add(new ToolBar(Arrays.asList(newFile,open,save,undo,redo, cut(), copy(), paste() )), BorderLayout.PAGE_START);
        helpers.Menu bearbeiten = new helpers.Menu("Bearbeiten", Arrays.asList(cut(),copy(),paste(),selectAll()));
        setJMenuBar(new helpers.MenuBar(Arrays.asList(datei,bearbeiten)));
    }

    public JTextArea eingabebereich(){
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane pane = new JScrollPane(textArea);
        add(pane, BorderLayout.CENTER);
        return textArea;
    }

    helpers.Action save = new helpers.Action("Speichern", Commands.SAVE,"src/assets/save.gif"){
        public void actionPerformed(ActionEvent e){
            if(currentFilePath != null){
                writeFile(editor.getText(), new File(currentFilePath));
            }else{
                JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                do {
                    int returnValue = chooser.showSaveDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = chooser.getSelectedFile();
                        if (selectedFile.getName().endsWith(".txt")) {
                            writeFile(editor.getText(), selectedFile);
                            return;
                        }
                        JOptionPane.showMessageDialog(
                                null, "Please enter a valid name with a .txt ending", "Failure", JOptionPane.ERROR_MESSAGE);
                    }
                }while(chooser.getSelectedFile().getName().endsWith(".txt") == false);
            }
        }
    };

    public boolean writeFile(String content, File file){
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.flush();
            currentFilePath = file.getAbsolutePath();
            return true;
        } catch (Exception exc){
            JOptionPane.showMessageDialog(
                    null, "Failed to save the file", "Failure", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    helpers.Action open = new helpers.Action("Öffnen",Commands.OPEN,"src/assets/open.gif"){
        public void actionPerformed(ActionEvent e){
            JFileChooser chooser = new JFileChooser(currentFilePath == null ? FileSystemView.getFileSystemView().getHomeDirectory() : new File(currentFilePath));
            chooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(".txt");
                }

                @Override
                public String getDescription() {
                    return "Text file (.txt)";
                }
            });
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                currentFilePath = selectedFile.getAbsolutePath();
                try {
                    editor.read(new FileReader(selectedFile), "test");
                }catch(Exception exc){
                    JOptionPane.showMessageDialog(
                            null, "Failed to open this file", "Failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };

    helpers.Action close = new helpers.Action("Beenden",Commands.CLOSE,null){
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    };

    helpers.Action newFile = new helpers.Action("Neu",Commands.CLOSE,"src/assets/new.gif"){
        public void actionPerformed(ActionEvent e){
            currentFilePath = null;
            editor.setText("");
        }
    };

    helpers.Action redo = new helpers.Action("Forwärts",Commands.CLOSE,"src/assets/redo.gif"){
        public void actionPerformed(ActionEvent e){
            try {
                manager.redo();
            }catch(Exception exc){}
        }
    };

    helpers.Action undo = new helpers.Action("Rückwärts",Commands.CLOSE,"src/assets/undo.gif"){
        public void actionPerformed(ActionEvent e){
            try {
                manager.undo();
            }catch(Exception exc){}
        }
    };

    private javax.swing.Action selectAll(){
        javax.swing.Action selectAll = editor.getActionMap().get(DefaultEditorKit.selectAllAction);
        selectAll.putValue(helpers.Action.NAME, "Alles markieren");
        return selectAll;
    }

    public javax.swing.Action copy(){
        javax.swing.Action copy = editor.getActionMap().get(DefaultEditorKit.copyAction);
        copy.putValue(helpers.Action.NAME, "Kopieren");
        copy.putValue(helpers.Action.SMALL_ICON, new ImageIcon("src/assets/copy.gif"));
        return copy;
    }

    public javax.swing.Action cut(){
        javax.swing.Action cut = editor.getActionMap().get(DefaultEditorKit.cutAction);
        cut.putValue(helpers.Action.NAME, "Ausschneiden");
        cut.putValue(helpers.Action.SMALL_ICON, new ImageIcon("src/assets/cut.gif"));
        return cut;
    }

    public javax.swing.Action paste(){
        javax.swing.Action paste = editor.getActionMap().get(DefaultEditorKit.pasteAction);
        paste.putValue(helpers.Action.NAME, "Einfügen");
        paste.putValue(helpers.Action.SMALL_ICON, new ImageIcon("src/assets/paste.gif"));
        return paste;
    }

}
