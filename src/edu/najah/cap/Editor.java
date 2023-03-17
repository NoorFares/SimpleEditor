package edu.najah.cap;
import edu.najah.cap.ex.EditorSaveAsException;
import edu.najah.cap.ex.EditorSaveException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.logging.Logger;


@SuppressWarnings("serial")
public class Editor extends JFrame implements ActionListener, DocumentListener {

	public static  void main(String[] args) {
		new Editor();
	}
	private static final Logger LOGGER = Logger.getLogger(Editor.class.getName());
	private  static final String USER_HOME ="user.home";
	private static final String SAVE_FILE ="Save file";
	private static final String CANNOT_WRITE_FILE ="Cannot write file!";
	private JEditorPane jEditorPane;
	public JMenuBar menu;

	public JMenuItem getCopy() {
		return copy;
	}

	public void setCopy(JMenuItem copy) {
		this.copy = copy;
	}

	private JMenuItem copy;

	public JMenuItem getPaste() {
		return paste;
	}

	public void setPaste(JMenuItem paste) {
		this.paste = paste;
	}

	private JMenuItem paste;

	public JMenuItem getCut() {
		return cut;
	}

	public void setCut(JMenuItem cut) {
		this.cut = cut;
	}

	private JMenuItem cut;

	public JEditorPane getjEditorPane() {
		return jEditorPane;
	}

	public void setjEditorPane(JEditorPane jEditorPane) {
		this.jEditorPane = jEditorPane;
	}

	public boolean changed = false;
	protected File file;
	
	private String[] actions = {"Open","Save","New","Edit","Quit", "Save as..."};
	
	protected JMenu jMenu;
	public Editor() {
		super("Editor");
		jEditorPane = new JEditorPane();
		add(new JScrollPane(jEditorPane), "Center");
		jEditorPane.getDocument().addDocumentListener(this);
		menu = new JMenuBar();
		setJMenuBar(menu);
		BuildMenu();
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void BuildMenu() {
		buildFileMenu();
		buildEditMenu();
	}

	private void buildFileMenu() {
		jMenu = new JMenu("File");
		jMenu.setMnemonic('F');
		menu.add(jMenu);
		JMenuItem n = new JMenuItem(actions[2]);
		n.setMnemonic('N');
		n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		n.addActionListener(this);
		jMenu.add(n);
		JMenuItem open = new JMenuItem(actions[0]);
		jMenu.add(open);
		open.addActionListener(this);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		JMenuItem save = new JMenuItem(actions[1]);
		jMenu.add(save);
		save.setMnemonic('S');
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem saveas = new JMenuItem(actions[5]);
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		jMenu.add(saveas);
		saveas.addActionListener(this);
		JMenuItem quit = new JMenuItem(actions[4]);
		jMenu.add(quit);
		quit.addActionListener(this);
		quit.setMnemonic('Q');
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
	}

	private void buildEditMenu() {
		JMenu edit = new JMenu(actions[3]);
		menu.add(edit);
		edit.setMnemonic('E');
		// cut
		cut = new JMenuItem("Cut");
		cut.addActionListener(this);
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		cut.setMnemonic('T');
		edit.add(cut);
		// copy
		copy = new JMenuItem("Copy");
		copy.addActionListener(this);
		copy.setMnemonic('C');
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		edit.add(copy);
		// paste
		paste = new JMenuItem("Paste");
		paste.setMnemonic('P');
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		edit.add(paste);
		paste.addActionListener(this);
		JMenuItem find = new JMenuItem("Find");
		find.setMnemonic('F');
		find.addActionListener(this);
		edit.add(find);
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		JMenuItem sall = new JMenuItem("Select All");
		sall.setMnemonic('A');
		sall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		sall.addActionListener(this);
		edit.add(sall);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
			case "Quit":
				exit();
				break;
			case "Open":
				openFile();
				break;
			case "Save":
				saveFile();
				break;
			case "New":
				newFile();
				break;
			case "Save As":
				saveAs();
				break;
			case "Select All":
				selectAll();
				break;
			case "Copy":
				copy();
				break;
			case "Cut":
				cut();
				break;
			case "Paste":
				paste();
				break;
			case "Find":
				openFindDialog();
				break;
			default:
				break;
		}
	}

	private void exit() {
		System.exit(0);
	}

	private void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				FileReader fileReader = new FileReader(selectedFile);
				jEditorPane.read(fileReader, null);
				fileReader.close();
				file = selectedFile;
				setTitle(file.getName());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	private void newFile() {
		int result = 0;
		if (changed) {
			result = JOptionPane.showConfirmDialog(null, "The file has changed. Do you want to save it?", SAVE_FILE, 0, 2);
		}
		if (result != 1&&file == null) {
			saveAs();
		} else {
			String text = jEditorPane.getText();
			try (PrintWriter writer = new PrintWriter(file)) {
				if (!file.canWrite()) {
					throw new EditorSaveException(CANNOT_WRITE_FILE);
				}
				writer.write(text);
				changed = false;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	private void saveAs() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String text = jEditorPane.getText();
			try (PrintWriter writer = new PrintWriter(selectedFile)) {
				if (!selectedFile.canWrite()) {
					throw new EditorSaveException(CANNOT_WRITE_FILE);
				}
				writer.write(text);
				file = selectedFile;
				setTitle(file.getName());
				changed = false;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	private void selectAll() {
		jEditorPane.selectAll();
	}

	private void copy() {
		jEditorPane.copy();
	}

	private void cut() {
		jEditorPane.cut();
	}

	private void paste() {
		jEditorPane.paste();
	}

	private void openFindDialog() {
		FindDialog find = new FindDialog(this, true);
		find.showDialog();
	}
	public void saveFile () {
		int result = 0;
		if (changed)
		{
			result = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", SAVE_FILE,
					0, 2);
		}
		if (result != 1&&file == null) {
			saveAs("Save");
		}
		else {
			String text = jEditorPane.getText();
			LOGGER.info(text);
			try (PrintWriter printWriter = new PrintWriter(file)){
				if (!file.canWrite())
					LOGGER.info(CANNOT_WRITE_FILE);
				printWriter.write(text);
				changed = false;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}


	private void saveAs(String dialogTitle) {
		dialogTitle = dialogTitle.toUpperCase();
		JFileChooser dialog = new JFileChooser(System.getProperty(USER_HOME));
		dialog.setDialogTitle(dialogTitle);
		int result = dialog.showSaveDialog(this);

		if (result != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File selectedFile = dialog.getSelectedFile();
		try (PrintWriter writer = new PrintWriter(selectedFile)) {
			if (jEditorPane != null) {
				writer.write(jEditorPane.getText());
			}
			changed = false;
			setTitle("Editor - " + selectedFile.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static PrintWriter getWriter(File file) {
		try {
			return new PrintWriter(file);
		} catch (Exception e){
			return null;
		}
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		changed = true;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changed = true;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changed = true;
	}

}