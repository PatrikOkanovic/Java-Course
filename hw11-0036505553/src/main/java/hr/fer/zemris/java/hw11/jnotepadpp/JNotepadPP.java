package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;


import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Implementation of a simple text editor capable of switching
 * between tabs and editing multiple files at once.
 * Contains basic operations like saving, saving as, cutting,
 * copying, pasting, showing statistics.
 * 
 * @author Patrik Okanovic
 *
 */
public class JNotepadPP extends JFrame{

	private static final long serialVersionUID = 1L;
	/**
	 * Used to stopping exiting the program if cancel was pressed on some modified file
	 */
	private boolean cancelWhileExit = false;
	/**
	 * Label for length of the current document
	 */
	private JLabel length;
	/**
	 * Label for number of row, column and selected text in the current document
	 */
	private JLabel info;
	/**
	 * Label for date and time in the current document
	 */
	private JLabel time;
	
	private FormLocalizationProvider flprovider = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);;
	/**
	 * {@link MultipleDocumentModel} used to communicate with files
	 */
	private DefaultMultipleDocumentModel model = new DefaultMultipleDocumentModel();
	/**
	 * Constructor of the class. Starts the initialization of the graphical interface
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(10, 10);
		setSize(650, 650);
		setTitle("JNotepad++");

		
		initGUI();
		
		flprovider.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				try {
					updateStatusBar(model.getCurrentDocument());
				} catch(Exception e) {
					//ignore, if someone changes language before creating blank document
				}
			}
		});
		
//		model.addChangeListener(new ChangeListener() {
//			
//			@Override
//			public void stateChanged(ChangeEvent e) {
//				if(model.getNumberOfDocuments() == 0) {
//					JNotepadPP.this.setTitle("JNotepad++");
//				} else {
//					if(model.getCurrentDocument().getFilePath() != null) {
//						JNotepadPP.this.setTitle("JNotepad++ " + model.getCurrentDocument().getFilePath().getFileName().toString());
//					} else {
//						JNotepadPP.this.setTitle("JNotepad++ (unnamed)" );
//					}
//				}
//				
//			}
//		});
		
	}
	/**
	 * Creates the toolbar, menus and sets the actions. 
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.add(model, BorderLayout.CENTER);
		
		
		
		cp.add(panel,BorderLayout.CENTER);
		

		
		createActions();
		createMenus();
		cp.add(createToolbar(),BorderLayout.NORTH);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
		
		setStatusBar(panel);
		
		
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				if(JNotepadPP.this.model.getNumberOfDocuments() == 0) {
					switchButtons(false);

				} else {
					switchButtons(true);
				}					
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				if(JNotepadPP.this.model.getNumberOfDocuments() == 0) {
					switchButtons(false);

				} else {
					switchButtons(true);
				}	
				
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if(JNotepadPP.this.model.getNumberOfDocuments() == 0) {
					switchButtons(false);

				} else {
					switchButtons(true);
				}				
			}
		});
		
		
		
	}
	/**
	 * Used for turning buttons on and off depending whether a tab is opened or not
	 * 
	 * @param state
	 */
	private void switchButtons(boolean state) {
		saveAction.setEnabled(state);
		closeAction.setEnabled(state);
		saveAsAction.setEnabled(state);
		statisticalAction.setEnabled(state);
	}

	/**
	 * Used to set the status bar with the information, in the way so that the toolbar stays floatable.
	 * Also adds a new thread representing clock,which sets the time and date on 
	 * the status bar
	 * 
	 * @param panel where to add status bar on the bottom
	 */
	private void setStatusBar(JPanel panel) {
		JPanel statusPanel = new JPanel();
		
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		length = new JLabel();
		info = new JLabel();
		time = new JLabel();
		
		length.setBorder(BorderFactory.createEmptyBorder());
		
		
		length.setHorizontalAlignment(SwingConstants.LEFT);
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		info.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		statusPanel.add(length,BorderLayout.WEST);
		statusPanel.add(info,BorderLayout.CENTER);
		statusPanel.add(time,BorderLayout.EAST);
		
		panel.add(statusPanel,BorderLayout.SOUTH);
		
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				updateStatusBar(JNotepadPP.this.model.getCurrentDocument());
				updateTitle(JNotepadPP.this.model.getCurrentDocument());
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				model.getTextComponent().getCaret().addChangeListener(new ChangeListener() {
					
					@Override
					public void stateChanged(ChangeEvent e) {
						updateStatusBar(model);
						updateTitle(model);
						
					}
				});
				
				
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				updateStatusBar(currentModel);
				updateTitle(currentModel);
			}

		});
		
		Thread clock = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						
					}
					SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
					time.setText(format.format(new Date()));
				}
				
			}
		});
		clock.setDaemon(true); //program needs some time to die because of the clock
		clock.start();
	
	}
	/**
	 * Method used to update windows name
	 * 
	 * @param model
	 */
	private void updateTitle(SingleDocumentModel model) {
		if(model.getFilePath() == null) {
			this.setTitle("JNotepad++ (unnamed)");
		} else {
			this.setTitle("JNotepad++ " + model.getFilePath().getFileName().toString());
		}
	}
	/**
	 * Method used for updating the status bar, with current length, row
	 * column, and selected text in the current opened document
	 * @param model
	 */
	private void updateStatusBar(SingleDocumentModel model) {
		int totalChar = model.getTextComponent().getText().length();
		length.setText(flprovider.getString("length")+": " + totalChar);
		
		
		int selected = Math.abs(model.getTextComponent().getCaret().getDot() - model.getTextComponent().getCaret().getMark());
		
		int caretPos = model.getTextComponent().getCaretPosition();
		int rowNum = (caretPos == 0) ? 1 : 0;
		for (int offset = caretPos; offset > 0;) {
		    try {
				offset = Utilities.getRowStart(model.getTextComponent(), offset) - 1;
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		    rowNum++;
		}
		
		int offset = 0;
		try {
			offset = Utilities.getRowStart(model.getTextComponent(), caretPos);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		int colNum = caretPos - offset+1;
		if(offset == -1) {
			colNum = 1;
		}
		
		
		info.setText(flprovider.getString("ln")+": " + rowNum + " "+flprovider.getString("col")+": " + colNum + " "+flprovider.getString("sel")+": " + selected);
		//deselect buttons which depend on selected
		cutAction.setEnabled(selected != 0);
		copyAction.setEnabled(selected != 0);
		toUpperCaseAction.setEnabled(selected != 0);
		toLowerCase.setEnabled(selected != 0);
		invertCaseAction.setEnabled(selected != 0);
		ascendingSortAction.setEnabled(selected != 0);
		descendingSortAction.setEnabled(selected != 0);
		uniqueAction.setEnabled(selected != 0);
	}
	
	

	/**
	 * Sets the names, descriptions, accelerator keys and mnemonics of the actions.
	 */
	private void createActions() {
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAction.setEnabled(false);

		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		saveAsAction.setEnabled(false);
		
		openExisting.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openExisting.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		createBlank.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createBlank.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		closeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		closeAction.setEnabled(false);

		
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		cutAction.setEnabled(false);
		
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.setEnabled(false);
		
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		
		statisticalAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
		statisticalAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		statisticalAction.setEnabled(false);

		enAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F7"));
		enAction.putValue(Action.MNEMONIC_KEY,  KeyEvent.VK_E);
		
		deAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F8"));
		deAction.putValue(Action.MNEMONIC_KEY,  KeyEvent.VK_D);
		
		hrAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F9"));
		hrAction.putValue(Action.MNEMONIC_KEY,  KeyEvent.VK_H);
		
		toUpperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUpperCaseAction.putValue(Action.MNEMONIC_KEY,  KeyEvent.VK_U);
		toUpperCaseAction.setEnabled(false);
		
		toLowerCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowerCase.putValue(Action.MNEMONIC_KEY,  KeyEvent.VK_L);
		toLowerCase.setEnabled(false);
		
		invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		invertCaseAction.putValue(Action.MNEMONIC_KEY,  KeyEvent.VK_I);
		invertCaseAction.setEnabled(false);
		
		ascendingSortAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		ascendingSortAction.putValue(Action.MNEMONIC_KEY,  KeyEvent.VK_A);
		ascendingSortAction.setEnabled(false);
		
		descendingSortAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		descendingSortAction.putValue(Action.MNEMONIC_KEY,  KeyEvent.VK_D);
		descendingSortAction.setEnabled(false);
		
		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
		uniqueAction.putValue(Action.MNEMONIC_KEY,  KeyEvent.VK_U);
		uniqueAction.setEnabled(false);
		
	}

	/**
	 * Creates the menus file, edit and view. Sets the icons for each action.
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		
		JMenu file = new LJMenu(flprovider,"file");
		mb.add(file);
		
		JMenuItem blank = new JMenuItem(createBlank);
		blank.setIcon(getImageIcon("blank.png"));
		file.add(blank);
		
		JMenuItem open = new JMenuItem(openExisting);
		open.setIcon(getImageIcon("open_existing.png"));
		file.add(open);
		
		JMenuItem save = new JMenuItem(saveAction);
		save.setIcon(getImageIcon("save.png"));
		file.add(save);
		
		
		JMenuItem saveAs = new JMenuItem(saveAsAction);
		saveAs.setIcon(getImageIcon("save.png"));
		file.add(saveAs);
		
		JMenuItem close = new JMenuItem(closeAction);
		close.setIcon(getImageIcon("close.png"));
		file.add(close);
		
		file.addSeparator();
		JMenuItem exit = new JMenuItem(exitAction);
		exit.setIcon(getImageIcon("exit.png"));
		file.add(exit);
		
		JMenu edit = new LJMenu(flprovider,"edit");
		mb.add(edit);
		
		JMenuItem copy = new JMenuItem(copyAction);
		copy.setIcon(getImageIcon("copy.png"));
		edit.add(copy);
		
		JMenuItem cut = new JMenuItem(cutAction);
		cut.setIcon(getImageIcon("cut.png"));
		edit.add(cut);
		
		JMenuItem paste = new JMenuItem(pasteAction);
		paste.setIcon(getImageIcon("paste.png"));
		edit.add(paste);
		
		JMenu view = new LJMenu(flprovider,"view");
		mb.add(view);
		JMenuItem statistics = new JMenuItem(statisticalAction);
		statistics.setIcon(getImageIcon("statistics.png"));
		view.add(statistics);
		
		JMenu languages = new LJMenu(flprovider,"languages");
		mb.add(languages);
		languages.add(enAction);
		languages.add(deAction);
		languages.add(hrAction);
		
		JMenu tools = new LJMenu(flprovider, "tools");
		mb.add(tools);
		JMenu changeCase = new LJMenu(flprovider, "changeCase");
		tools.add(changeCase);
		changeCase.add(toUpperCaseAction);
		changeCase.add(toLowerCase);
		changeCase.add(invertCaseAction);
		
		JMenu sort = new LJMenu(flprovider, "sort");
		tools.add(sort);
		sort.add(ascendingSortAction);
		sort.add(descendingSortAction);
		
		tools.add(uniqueAction);
	
		
		setJMenuBar(mb);
		
		
	}

	/**
	 * Creates a toolbar with all the actions of {@link JNotepadPP}
	 * 
	 * @return {@link JToolBar}
	 */
	private Component createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		
		tb.add(new JButton(createBlank));
		tb.add(new JButton(openExisting));
		tb.add(new JButton(saveAction));
		tb.add(new JButton(saveAsAction));
		tb.add(new JButton(closeAction));
		tb.add(new JButton(exitAction));
		
		tb.add(new JButton(copyAction));
		tb.add(new JButton(cutAction));
		tb.add(new JButton(pasteAction));
		
		tb.add(new JButton(statisticalAction));
		
		return tb;
		
	}
	/**
	 * Action for switching the program to English language.
	 */
	@SuppressWarnings("serial")
	private final Action enAction = new LocalizableAction("en",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	/**
	 * Action for switching the program to Croatian language
	 */
	@SuppressWarnings("serial")
	private final Action hrAction = new LocalizableAction("hr",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};
	/**
	 * Action for switching the program to German language
	 */
	@SuppressWarnings("serial")
	private final Action deAction = new LocalizableAction("de",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};
		
	/**
	 * Saves the current work in the tab, if file has never before been saved
	 * calls the save as action.
	 */
	@SuppressWarnings("serial")
	private final Action saveAction = new LocalizableAction("save",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(model.getCurrentDocument().getFilePath() == null) {
				saveAsAction.actionPerformed(e);
				return;
			}
			
			model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
			
		}
	};
	
	/**
	 * If the file has been saved asks user if he wants to save to the old location.
	 * Finds the file where to save the current document. Asks if the user wants to overwrite
	 * an existing file
	 */
	@SuppressWarnings("serial")
	private final Action saveAsAction = new LocalizableAction("saveAs",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			
			//warn user file already exists
			if(model.getCurrentDocument().getFilePath() != null) {
				int response = JOptionPane.showConfirmDialog(JNotepadPP.this, "File already exists. Do you want to save to the "
						+ "old location : " + model.getCurrentDocument().getFilePath().toString() + "?","Saving", JOptionPane.YES_NO_OPTION);
				if(response == JOptionPane.CLOSED_OPTION) {
					return;//cancel
				}
				if(response == JOptionPane.YES_OPTION) {
					saveAction.actionPerformed(e);
					return;
				}
			}
			Path newPath = null;
			if(jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						"Nothing has been saved", 
						"Information",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//check if selected path is already active
			for(SingleDocumentModel doc : model) {
				if(doc.getFilePath() == null) {
					continue;
				}
				if(doc.getFilePath().equals(jfc.getSelectedFile().toPath())) {
					JOptionPane.showMessageDialog(JNotepadPP.this, "That path is currently active, cannot save to that location", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			newPath = jfc.getSelectedFile().toPath();
			
			if(Files.exists(newPath)) {
				int response = JOptionPane.showConfirmDialog(JNotepadPP.this, "Do you want to overwrite the file?",
						"Save document",JOptionPane.YES_NO_OPTION);
				if(response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) {
					return;
				}
				
			}
			model.saveDocument(model.getCurrentDocument(), newPath);
			
		}
	};
	
	/**
	 * Creates a blank new unnamed document and sets is a curretn tab.
	 */
	@SuppressWarnings("serial")
	private final Action createBlank = new LocalizableAction("create",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
			
		}
	};
	/**
	 * Used to load an existing file for the user, opens an {@link JFileChooser} where
	 * user can chose what file to chose for loading
	 */
	@SuppressWarnings("serial")
	private final Action openExisting = new LocalizableAction("open",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			Path path = null;
			jfc.setDialogTitle("Load document");
			
			if(jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						"Nothing has been loaded", 
						"Information",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			path = jfc.getSelectedFile().toPath();
			model.loadDocument(path);
		}
	};
	
	/**
	 * Closes the current tab, asks the user to save before closing the tab.
	 * If the tab is the last one shuts the application down
	 */
	@SuppressWarnings("serial")
	private final Action closeAction = new LocalizableAction("close",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(model.getCurrentDocument().isModified()) {
				int response = JOptionPane.showConfirmDialog(JNotepadPP.this, "File has been modified do you want to save file?", "Question", 
									JOptionPane.YES_NO_CANCEL_OPTION);
				if(response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION) {
					cancelWhileExit = true;
					return;
				} else if(response == JOptionPane.YES_OPTION) {
					saveAction.actionPerformed(e);
				}
			}
			
			if(model.getNumberOfDocuments() == 1) {
				createBlank.actionPerformed(e);
				model.closeDocument(model.getDocument(0));
		
			} else {
				model.closeDocument(model.getCurrentDocument());

			}
			
			
		}
	};
	/**
	 * Used to cut text, visible only if there is a selected text
	 */
	@SuppressWarnings("serial")
	private final Action cutAction = new LocalizableAction("cut",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Action action = new DefaultEditorKit.CutAction();
			action.actionPerformed(e);
			
		}
	};
	/**
	 * Used to copy text, visible only if there is a selected text
	 */
	@SuppressWarnings("serial")
	private final Action copyAction = new LocalizableAction("copy",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Action action = new DefaultEditorKit.CopyAction();
			action.actionPerformed(e);
			
		}
	};
	
	/**
	 * Used to paste text, visible only if there is a selected text
	 */
	@SuppressWarnings("serial")
	private final Action pasteAction = new LocalizableAction("paste",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Action action = new DefaultEditorKit.PasteAction();
			action.actionPerformed(e);
			
		}
	};
	
	/**
	 * Counts number of total characters, number of non blank characters, number 
	 * of lines and gives the information to the user
	 */
	@SuppressWarnings("serial")
	private final Action statisticalAction = new LocalizableAction("statistics",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int totalChar = model.getCurrentDocument().getTextComponent().getText().length();
			int nonBlankChar = 0;
			for(char c : model.getCurrentDocument().getTextComponent().getText().toCharArray()) {
				if(! Character.isWhitespace(c)) {
					nonBlankChar++;
				}
			}
			long numOfLines = model.getCurrentDocument().getTextComponent().getText().chars().filter(x -> x == '\n').count() + 1;
			
			JOptionPane.showMessageDialog(JNotepadPP.this, "Your doucment has "+totalChar+" characters, "
					+ nonBlankChar +" non blank characters and "+numOfLines+" lines.");
			
			
		}
	};
	/**
	 * Closes every tab, asks to save if the tab has been modified and
	 * finally shuts down the application down.
	 */
	@SuppressWarnings("serial")
	private final Action exitAction = new LocalizableAction("exit",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			cancelWhileExit = false;
			int numOfDocuments = model.getNumberOfDocuments();
			for(int i = 0; i < numOfDocuments - 1; i++) {
				closeAction.actionPerformed(e);
				if(cancelWhileExit) {
					return;
				}
			}

			if (model.getNumberOfDocuments() == 1) {
				if (model.getCurrentDocument().isModified()) {
					int response = JOptionPane.showConfirmDialog(JNotepadPP.this,
							"File has been modified do you want to save file?", "Question",
							JOptionPane.YES_NO_CANCEL_OPTION);
					if (response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION) {
						cancelWhileExit = true;
						return;
					} else if (response == JOptionPane.YES_OPTION) {
						saveAction.actionPerformed(e);
					}
				}
				dispose();
			}
			
			if(numOfDocuments == 0) {
				dispose();
			}
			
		}
	};
	/**
	 * Sets upper case of the selected text and rewrites it back down to the textual component.
	 */
	@SuppressWarnings("serial")
	private final Action toUpperCaseAction = new LocalizableAction("upperCase",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int offset = Math.min(model.getCurrentDocument().getTextComponent().getCaret().getDot(),
									model.getCurrentDocument().getTextComponent().getCaret().getMark());
			int len = Math.abs(model.getCurrentDocument().getTextComponent().getCaret().getDot() -
									model.getCurrentDocument().getTextComponent().getCaret().getMark());
			String text = model.getCurrentDocument().getTextComponent().getSelectedText();
			
			String toUpper = text.toUpperCase();
			Document doc = model.getCurrentDocument().getTextComponent().getDocument();
			
			try {
				doc.remove(offset, len);
				doc.insertString(offset, toUpper, null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			
		}
	};
	/**
	 * Sets lower case of the selected text and rewrites it back down to the textual component.
	 */
	@SuppressWarnings("serial")
	private final Action toLowerCase = new LocalizableAction("lowerCase",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int offset = Math.min(model.getCurrentDocument().getTextComponent().getCaret().getDot(),
					model.getCurrentDocument().getTextComponent().getCaret().getMark());
			int len = Math.abs(model.getCurrentDocument().getTextComponent().getCaret().getDot()
					- model.getCurrentDocument().getTextComponent().getCaret().getMark());
			String text = model.getCurrentDocument().getTextComponent().getSelectedText();

			String toLower = text.toLowerCase();
			Document doc = model.getCurrentDocument().getTextComponent().getDocument();

			try {
				doc.remove(offset, len);
				doc.insertString(offset, toLower, null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	/**
	 * Inverts case of the selected text and rewrites it back down to the textual component.
	 */
	@SuppressWarnings("serial")
	private final Action invertCaseAction = new LocalizableAction("invertCase",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int offset = Math.min(model.getCurrentDocument().getTextComponent().getCaret().getDot(),
					model.getCurrentDocument().getTextComponent().getCaret().getMark());
			int len = Math.abs(model.getCurrentDocument().getTextComponent().getCaret().getDot()
					- model.getCurrentDocument().getTextComponent().getCaret().getMark());
			String text = model.getCurrentDocument().getTextComponent().getSelectedText();

			StringBuilder sb = new StringBuilder();
			for(char c : text.toCharArray()) {
				if(Character.isUpperCase(c)) {
					sb.append(Character.toLowerCase(c));
				} else  {
					sb.append(Character.toUpperCase(c));
				}
			}
			Document doc = model.getCurrentDocument().getTextComponent().getDocument();

			try {
				doc.remove(offset, len);
				doc.insertString(offset, sb.toString(), null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			
		}
	};
	/**
	 * Removes duplicates from selected text from the window and rewrites it back down.
	 */
	@SuppressWarnings("serial")
	private final Action uniqueAction = new LocalizableAction("unique",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextComponent c = model.getCurrentDocument().getTextComponent();
			int dot = c.getCaret().getDot();
			int mark = c.getCaret().getMark();

			Document doc = c.getDocument(); // we assume it is instanceof PlainDocument
			Element root = doc.getDefaultRootElement();
			int lineStart = root.getElementIndex(Math.min(dot, mark)); // zero-based line index...
			int lineEnds = root.getElementIndex(Math.max(dot, mark));
			int endOfFile = model.getCurrentDocument().getTextComponent().getLineCount();
			boolean isItLastLine = lineEnds+1 == endOfFile;
			
			String text = null;
			int offset = 0,len = 0;
			try {
				offset = model.getCurrentDocument().getTextComponent().getLineStartOffset(lineStart);
				len = model.getCurrentDocument().getTextComponent().getLineEndOffset(lineEnds)
							- offset;
				text = model.getCurrentDocument().getTextComponent().getText(offset, len);

			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			List<String> lines = getLines(text);
			Set<String> uniqueSet = removeDuplicatesForUnique(lines);
			
			StringBuilder sb = new StringBuilder();
			for(String line : uniqueSet) {
				sb.append(line);
				sb.append("\n");
			}
			if(isItLastLine) {
				sb.setLength(sb.length()-1);//remove last \n
			}
			
			try {
				doc.remove(offset, len);
				doc.insertString(offset, sb.toString(), null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			
		}

		/**
		 * Used as a helping method for uniqueAction, removes duplicates from the lines
		 * @param lines of text
		 * @return lines without duplicates
		 */
		private Set<String> removeDuplicatesForUnique(List<String> lines) {
			Set<String> set = new LinkedHashSet<>();
			for(String s : lines) {
				set.add(s);
			}
			return set;
		}
	};
	
	/**
	 * Descending action used to sort the selected text from the window and rewrite it back in the descending order.
	 */
	@SuppressWarnings("serial")
	private final Action ascendingSortAction = new LocalizableAction("ascending",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			sort(true);
		}
	};
	
	/**
	 * Descending action used to sort the selected text from the window and rewrite it back in the descending order.
	 */
	@SuppressWarnings("serial")
	private final Action descendingSortAction = new LocalizableAction("descending",flprovider) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			sort(false);
			
		}
	};
	/**
	 * Method used for implementing ascending and descending sort action, 
	 * sorts the selected lines, whole lines do not have to be selected.
	 * If ascending is true sorts them as ascending, else descending.
	 * Gets the selected text and rewrites it back to the textual component.
	 * 
	 * @param ascending if true sort ascending
	 */
	private void sort(boolean ascending) {
		JTextComponent c = model.getCurrentDocument().getTextComponent();
		int dot = c.getCaret().getDot();
		int mark = c.getCaret().getMark();

		Document doc = c.getDocument(); // we assume it is instanceof PlainDocument
		Element root = doc.getDefaultRootElement();
		int lineStart = root.getElementIndex(Math.min(dot, mark)); // zero-based line index...
		int lineEnds = root.getElementIndex(Math.max(dot, mark));
		int endOfFile = model.getCurrentDocument().getTextComponent().getLineCount();
		boolean isItLastLine = lineEnds+1 == endOfFile;
		
		String text = null;
		int offset = 0,len = 0;
		try {
			offset = model.getCurrentDocument().getTextComponent().getLineStartOffset(lineStart);
			len = model.getCurrentDocument().getTextComponent().getLineEndOffset(lineEnds)
						- offset;
			text = model.getCurrentDocument().getTextComponent().getText(offset, len);

		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		List<String> lines = getLines(text);
		Locale locale = new Locale(flprovider.getCurrentLanguage());
		Collator collator = Collator.getInstance(locale);
		Collections.sort(lines, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				int order = ascending ? 1 : -1;
				return order*collator.compare(o1, o2);
			}
		});
		StringBuilder sb = new StringBuilder();
		for(String line : lines) {
			sb.append(line);
			sb.append("\n");
		}
		if(isItLastLine) {
			sb.setLength(sb.length()-1);//remove last \n
		}
		
		try {
			doc.remove(offset, len);
			doc.insertString(offset, sb.toString(), null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * Method used for getting a list of Strings representing lines from the document
	 * 
	 * @param text source of lines
	 * @return list of lines
	 */
	private List<String> getLines(String text) {
		try(Scanner sc = new Scanner(text)) {
			List<String> lines = new ArrayList<>();
			while(sc.hasNext()) {
				lines.add(sc.nextLine());
			}
			return lines;
		}
	}
	
	
	
	
	/**
	 * Method used to get {@link ImageIcon}
	 * 
	 * @param name of the .png file
	 * @return {@link ImageIcon}
	 * @throws RuntimeException when reading goes bad
	 */
	private  ImageIcon getImageIcon(String name) {
		
		try(InputStream is = this.getClass().getResourceAsStream("icons/"+name)) {
			if(is==null) {
				throw new RuntimeException("Reading image went bad");
			}
			byte[] bytes = null;
			try {
				bytes = is.readAllBytes();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ImageIcon icon = new ImageIcon(bytes);
			Image image = icon.getImage();
            Image newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);
			return  icon;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	/**
	 * Main method of the class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			new JNotepadPP().setVisible(true);
		});
	}
}
