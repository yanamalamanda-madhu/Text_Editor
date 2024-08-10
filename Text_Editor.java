import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.awt.print.*;
//import java.awt.print.PageFormat;
//import java.awt.print.PrinterJob;
import java.io.*;
//import java.util.Arrays;

class Text_Editor implements ActionListener
{
    JTextArea textArea=new JTextArea(1310,700);
    JCheckBoxMenuItem WordWrap;
    JFrame frame;

    //Creating array values for font names, styles, and sizes
/*    private static final String[] FONT_NAMES = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    private static final String[] FONT_STYLES = { "Plain", "Bold", "Italic", "Bold Italic" };
    private static final Integer[] FONT_SIZES = { 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40,42,44,46,48,50,52,54,56,58,60,62,64,66,68,70,72 };
    private String selectedFontName = "Arial";
    private int selectedFontStyle = Font.PLAIN;
    private int selectedFontSize = 12;
*/
    public static void main(String args[]){
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                new Text_Editor();
            }
        });
    }

    Text_Editor(){
// JFrame frame=new JFrame("Text Editor");
        frame=new JFrame("Text Editor");
        frame.setSize(1320,710);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

//Creating MenuBar
        JMenuBar jmb=new JMenuBar();

//Creating File menu
        JMenu File=new JMenu("File");
        JMenuItem New=new JMenuItem("New");
        JMenuItem Open=new JMenuItem("Open");
        JMenuItem SaveAs=new JMenuItem("Save As");
        JMenuItem Setup=new JMenuItem("Page Setup");
        JMenuItem Print=new JMenuItem("Print");
        JMenuItem Exit=new JMenuItem("Exit");
        File.add(New);
        File.add(Open);
        File.add(SaveAs);
        File.add(Setup);
        File.add(Print);
        File.add(Exit);
        jmb.add(File);

//Creating Edit Menu
        JMenu Edit=new JMenu("Edit");
        JMenuItem Cut=new JMenuItem("Cut");
        JMenuItem Copy=new JMenuItem("Copy");
        JMenuItem Paste=new JMenuItem("Paste");
        JMenuItem SelectAll=new JMenuItem("Select All");
        Edit.add(Cut);
        Edit.add(Copy);
        Edit.add(Paste);
        Edit.add(SelectAll);
        jmb.add(Edit);

//Creating Format Menu
        JMenu Format=new JMenu("Format");
        WordWrap=new JCheckBoxMenuItem("WordWrap");
        
/*        JMenu Font=new JMenu("Font");
        JMenu FontNames=new JMenu("Font Names");
        JMenu FontStyles=new JMenu ("Font Styles");
        JMenu FontSizes=new JMenu ("Font Sizes");
        Font.add(FontNames);
        Font.add(FontStyles);
        Font.add(FontSizes);
        Format.add(Font);
        */

        Format.add(WordWrap);
        jmb.add(Format);

//Creating View Menu
        JMenu View=new JMenu("View");
        JMenu Zoom=new JMenu("Zoom");
        JMenuItem ZoomIn=new JMenuItem("Zoom In");
        JMenuItem ZoomOut=new JMenuItem("Zoom Out");
        Zoom.add(ZoomIn);
        Zoom.add(ZoomOut);
        View.add(Zoom);
        jmb.add(View);

//Creating Help Menu
        JMenu Help=new JMenu("Help");
        JMenuItem About=new JMenuItem("About");
        Help.add(About);
        jmb.add(Help);

//Adding shortcut keys for menu items
        New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        SaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        Print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
        Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
        Cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        Paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        SelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
        About.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK));

//Adding scrollbar to text area
        JScrollPane scroll=new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scroll, BorderLayout.CENTER);

//Adding separator to separate text area and status label
        JSeparator separator = new JSeparator();

//Adding status label
        JLabel statusLabel = new JLabel("Line: 1, Column: 1");
        frame.add(statusLabel, BorderLayout.SOUTH);
        frame.setJMenuBar(jmb);
        frame.setVisible(true);

// Code for line position and caret position
        textArea.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                try {
                    int caretPos = textArea.getCaretPosition();
                    int line = textArea.getLineOfOffset(caretPos) + 1;
                    int column = caretPos - textArea.getLineStartOffset(line - 1) + 1;
                    statusLabel.setText("Line: " + line + ", Column: " + column);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

// Add a DocumentListener to update line count on text change
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateStatus();
            }
            public void removeUpdate(DocumentEvent e) {
                updateStatus();
            }
            public void changedUpdate(DocumentEvent e) {
                updateStatus();
            }
            private void updateStatus() {
                int lines = textArea.getLineCount();
                statusLabel.setText("Lines: " + lines);
            }
        });

/*    // Adding font names to Font Name menu
        for (String fontName : FONT_NAMES) {
            JMenuItem menuItem = new JMenuItem(fontName);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedFontName = fontName;
                    updateFont();
                }
            });
            FontNames.add(menuItem);
        }

    // Adding font styles to Font Style menu
        for (int i = 0; i < FONT_STYLES.length; i++) {
            String fontStyleStr = FONT_STYLES[i];
            JMenuItem menuItem = new JMenuItem(fontStyleStr);
            final int fontStyleIndex = i;
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedFontStyle = fontStyleIndex;
                    updateFont();
                }
            });
            FontStyles.add(menuItem);
        }

    // Adding font sizes to Font Size menu
        for (Integer fontSize : FONT_SIZES) {
            JMenuItem menuItem = new JMenuItem(fontSize.toString());
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedFontSize = fontSize;
                    updateFont();
                }
            });
            FontSizes.add(menuItem);
        }

        Font.add(FontNames);
        Font.add(FontStyles);
        Font.add(FontSizes);
*/

// Creating JComboBoxes for font names, styles, and sizes
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontNameCB = new JComboBox<>(fontNames);
        
        String[] fontStyles = { "Plain", "Bold", "Italic", "Bold Italic" };
        JComboBox<String> fontStyleCB = new JComboBox<>(fontStyles);
        
        Integer[] fontSizes = { 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40,42,44,46,48,50,52,54,56,58,60,62,64,66,68,70,72 };
        JComboBox<Integer> fontSizeCB = new JComboBox<>(fontSizes);
        fontSizeCB.setSelectedItem(12);

// Action listener to update the fontNames, fontStyles, fontSizes
        ActionListener fontChanger = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fontName = (String) fontNameCB.getSelectedItem();
                int fontStyle = fontStyleCB.getSelectedIndex();
                int fontSize = (Integer) fontSizeCB.getSelectedItem();
                Font newFont = new Font(fontName, fontStyle, fontSize);
                textArea.setFont(newFont);
            }
        };

// Adding action listener to combo boxes
        fontNameCB.addActionListener(fontChanger);
        fontStyleCB.addActionListener(fontChanger);
        fontSizeCB.addActionListener(fontChanger);

// Creating a panel for font controls
        JPanel fontPanel = new JPanel();
        fontPanel.add(new JLabel("Font Names:"));
        fontPanel.add(fontNameCB);
        fontPanel.add(new JLabel("Font Styles:"));
        fontPanel.add(fontStyleCB);
        fontPanel.add(new JLabel("Font Sizes:"));
        fontPanel.add(fontSizeCB);
        jmb.add(fontPanel, BorderLayout.NORTH);

// Adding action listeners to menu items
        About.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "<html><head><h1><b><i>MADHU personal Text Editor</i></b></h1></head><body><b>This Text Editor is like Notepad</b> <br/><b>This is only for creating new document<br/>editing the existed document <br/>saving the created document  </b></body></html>");
            }
        });
        ZoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font = textArea.getFont();
                float fontSize = font.getSize() + 10.0f;
                if(fontSize<=72){
                    textArea.setFont(font.deriveFont(fontSize));
                }
            }
        });

        ZoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font = textArea.getFont();
                float fontSize = font.getSize() - 10.0f;
                if(fontSize >=8){
                    textArea.setFont(font.deriveFont(fontSize));
                }
            }
        });

        WordWrap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(WordWrap.isSelected())
                    textArea.setLineWrap(true);
                else
                    textArea.setLineWrap(false);
            }
        });

        Cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                textArea.cut();
            }
        });

        Copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
            }
        });

        Paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });

        SelectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.selectAll();
            }
        });

        New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Do you want to save the file", "save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    saving();
                }
                else {
                    textArea.setText(null);
                }
                textArea.setText(" ");
            }
        });

        Open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open File");
                int userSelection = fileChooser.showOpenDialog(frame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToOpen = fileChooser.getSelectedFile();
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileToOpen))) {
                        textArea.read(reader, null);
                    }
                    catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error opening file: " + ex.getMessage());
                    }
                }
            }
        });

        SaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saving();
            }
        });

        Setup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PrinterJob pj = PrinterJob.getPrinterJob();
                PageFormat pf = pj.pageDialog(pj.defaultPage());
            }
        });

        Print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    textArea.setLineWrap(true);
                    textArea.print();
                    textArea.setLineWrap(false);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (JOptionPane.showConfirmDialog(null, "Do you want to save the file", "save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    saving();
                }
                else {
                    System.exit(0);
                }
            }
        });

    }

public void actionPerformed(ActionEvent ae)
{
System.out.println("gfiuerf");
}

//Code for saving file
    public void saving(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save File");
        int userSelection = fileChooser.showSaveDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                textArea.write(writer);
                JOptionPane.showMessageDialog(frame, "File saved successfully!");}
            catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage());}
        }
    }

//Code for update font in font menu
/*    private void updateFont() {
        Font newFont = new Font(selectedFontName, selectedFontStyle, selectedFontSize);
        textArea.setFont(newFont);
    }
*/


}
