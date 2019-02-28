package views;

import enums.Commands;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

public class TextEditor extends JFrame {

    JTextArea editor;
    String currentFilePath;

    public TextEditor(){
        super();
        Menu datei = new Menu("Datei", Arrays.asList(open, save, close));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Text Editor 101");
        setSize(920, 640);
        setLayout(new BorderLayout());
        editor = eingabebereich();
        add(new ToolBar(Arrays.asList(newFile,open,save, cut(), copy(), paste() )), BorderLayout.PAGE_START);
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

    Action open = new Action("Öffnen",Commands.OPEN,"src/assets/open.gif"){
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

    Action close = new Action("Beenden",Commands.CLOSE,null){
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    };

    Action newFile = new Action("Neu",Commands.CLOSE,"src/assets/new.gif"){
        public void actionPerformed(ActionEvent e){
            currentFilePath = null;
            editor.setText("");
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
