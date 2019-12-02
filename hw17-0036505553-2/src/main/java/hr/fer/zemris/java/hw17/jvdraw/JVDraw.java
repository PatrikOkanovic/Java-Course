package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;


import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.Tool;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.states.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.states.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.states.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.visitors.SaveVisitor;

/**
 * The main program of the class.
 * Combines all the classes made. Used for drawing
 * {@link Line}, {@link Circle}, {@link FilledCircle} to the
 * {@link JDrawingCanvas} using {@link Tool} and visitor patter 
 * with {@link GeometricalObjectVisitor}.
 * Capable of saving, opening old drawings and exporting current drawings to
 * jpg, gif and png files.
 * 
 * @author Patrik Okanovic
 *
 */
public class JVDraw extends JFrame{

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tool used for State pattern
	 */
	private Tool currentTool;
	
	/**
	 * State when {@link Line} will be drawn
	 */
	private LineTool lineTool;
	
	/**
	 * State when {@link Circle} will be drawn
	 */
	private CircleTool circleTool;
	
	/**
	 * State when {@link FilledCircle} will be drawn
	 */
	private FilledCircleTool filledCircleTool;
	
	/**
	 * The {@link DrawingModel}
	 */
	private DrawingModel model = new DrawingModelImplementation();
	
	/**
	 * The {@link JDrawingCanvas}
	 */
	private JDrawingCanvas canvas;
	
	/**
	 * The {@link JColorArea} for foreground color, color of the lines
	 */
	private JColorArea fgColor;
	
	/**
	 * The {@link JColorArea} for background color, color of the filling.
	 */
	private JColorArea bgColor;
	
	/**
	 * The {@link DrawingObjectListModel}
	 */
	private DrawingObjectListModel listModel;
	
	/**
	 * {@link JList} used with the listModel of {@link DrawingObjectListModel}
	 */
	private JList<GeometricalObject> list;
	
	/**
	 * If file does not have location, on save action call save as action
	 */
	private boolean firstSave = true;
	
	/**
	 * Path of the last save
	 */
	private Path currentPath;
	
	/**
	 * Constructor of the class.
	 * 
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(10,10);
		setSize(650,650);
		setTitle("JVDraw");
		
		initGUI();
		
	}
	
	
	
	/**
	 * Method used to initialize the graphic interface
	 * Layout is {@link BorderLayout}
	 * {@link JDrawingCanvas} is in the middle
	 * {@link ToolBarBorder} with {@link ButtonGroup} is used
	 * to select the current state
	 * {@link JColorLabel} is on the bottom
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
	
		canvas = new JDrawingCanvas(model, new Supplier<Tool>() {
			
			@Override
			public Tool get() {
				return currentTool;
			}
		});
		
		cp.add(canvas,BorderLayout.CENTER);
		
		listModel = new DrawingObjectListModel(model);
		list = new JList<>(listModel);
		
		addListenersForListInteraction();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.add(list);
		
		
		cp.add(new JScrollPane(list),BorderLayout.EAST);
		
		
		
		cp.add(createToolbar(),BorderLayout.NORTH);
	
		JColorLabel colorLabel = new JColorLabel(fgColor, bgColor);
		
		cp.add(colorLabel, BorderLayout.SOUTH);
		
		createActions();
		createMenus();
		
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
		
		
	}


	/**
	 * When double clicked on the list open the editor for {@link GeometricalObjec}
	 * + for the current selected in the listModel moves up in the list
	 * - for the current selected in the listModel moves down in the list
	 */
	private void addListenersForListInteraction() {
		list.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// do nothing
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// do nothing
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int index = list.getSelectedIndex();
				if(index == -1) {
					return;
				}
				if(e.getKeyCode() == KeyEvent.VK_PLUS) {
					GeometricalObject object = listModel.getElementAt(index);
					model.changeOrder(object, -1);
					
				} else if(e.getKeyCode() == KeyEvent.VK_MINUS) {
					GeometricalObject object = listModel.getElementAt(index);
					model.changeOrder(object, 1);
					
				} else if(e.getKeyCode() == KeyEvent.VK_DELETE) { // this is for delete
					GeometricalObject object = listModel.getElementAt(index);
					model.remove(object);
				}
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					int index = list.getSelectedIndex();
					if(index == -1) {
						return;
					}
					GeometricalObject object = listModel.getElementAt(index);
					GeometricalObjectEditor editor = object.createGeometricalObjectEditor();
					if(JOptionPane.showConfirmDialog(JVDraw.this, editor,
							"Editor", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
							canvas.repaint();
						} catch(Exception ex) {
							JOptionPane.showMessageDialog(JVDraw.this, "Invalid editing, please repeat");
						}
					}
				}
			}
		});
		
		
		
	}

	/**
	 * Creates the {@link ToolBar} with
	 * two {@link JColorArea} and {@link ButtonGroup} for
	 * selecting the current state with the {@link Tool}
	 * 
	 * @return
	 */
	private Component createToolbar() {
		JToolBar tb = new JToolBar();
		
		fgColor= new JColorArea(Color.BLACK);
		bgColor = new JColorArea(Color.white);
		
		tb.add(fgColor);
		tb.add(bgColor);
	
		
		lineTool = new LineTool(model, fgColor);
		circleTool = new CircleTool(model, fgColor);
		filledCircleTool = new FilledCircleTool(model, fgColor, bgColor);
		
		
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		JRadioButton lineButton = new JRadioButton("Line");
	
		lineButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(lineButton.isSelected()) {
					currentTool = lineTool;
				}
			}
		});
		
		JRadioButton circleButton = new JRadioButton("Circle");
		circleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(circleButton.isSelected()) {
					currentTool = circleTool;
				}
				
			}
		});
		
		JRadioButton filledCircleButton = new JRadioButton("Filled circle");
		filledCircleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(filledCircleButton.isSelected()) {
					currentTool = filledCircleTool;
				}
				
			}
		});
		
		
		buttonGroup.add(circleButton);
		buttonGroup.add(filledCircleButton);
		buttonGroup.add(lineButton);
		
		tb.add(lineButton);
		tb.add(circleButton);
		tb.add(filledCircleButton);
		
		
		lineButton.setSelected(true);
		currentTool = lineTool;
		
		return tb;
	}




	/**
	 * Creates a menu bar with open, save, save as, export and exit action
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		
		JMenu file = new JMenu("File");
		mb.add(file);
		
		JMenuItem open = new JMenuItem(openAction);
		open.setText("Open");
		file.add(open);
		
		JMenuItem save = new JMenuItem(saveAction);
		save.setText("Save");
		file.add(save);
		
		JMenuItem saveAs = new JMenuItem(saveAsAction);
		saveAs.setText("Save As");
		file.add(saveAs);
		
		JMenuItem exit = new JMenuItem(exitAction);
		exit.setText("Exit");
		file.add(exit);
		
		JMenuItem export = new JMenuItem(exportAction);
		export.setText("Export");
		file.add(export);
		
		setJMenuBar(mb);
		
		
	}



	/**
	 * Creates  open, save, save as, export and exit action
	 */
	private void createActions() {
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
		
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		
		
	}
	
	/**
	 * {@link AbstractAction} used to open a new drawing, only
	 * .jvd files can be opened.
	 * If current is modified user is asked to save the current work before opening new
	 */
	@SuppressWarnings("serial")
	private final Action openAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(model.isModified()) {
				int response = JOptionPane.showConfirmDialog(JVDraw.this,
						"File has been modified do you want to save file?", "Question",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION) {
					return;
				} else if (response == JOptionPane.YES_OPTION) {
					saveAction.actionPerformed(e);
				}
			}
			JFileChooser jfc = new JFileChooser();
			Path path = null;
			jfc.setDialogTitle("Load document");
			jfc.addChoosableFileFilter(new FileNameExtensionFilter("*.jvd", "jvd"));
			
			if(jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this,
						"Nothing has been loaded", 
						"Information",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			path = jfc.getSelectedFile().toPath();
			if(!path.toString().endsWith(".jvd")) {
				JOptionPane.showMessageDialog(JVDraw.this, "Cannot open files which are not .jvd");
				return;
			}
			loadFile(path);
			
		}

		/**
		 * Loads the current .jvd file in the {@link DrawingModel}
		 * Example of jvd file:
		 * 
		 * LINE 10 10 50 50 255 255 0
¸		 * LINE 50 90 30 10 128 0 128
		 * CIRCLE 40 40 18 0 0 255
		 * FCIRCLE 40 40 18 0 0 255 255 0 0
		 *
		 * @param path
		 */
		private void loadFile(Path path) {
			model.clear();
			List<String> lines;
			try {
				lines = Files.readAllLines(path);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(JVDraw.this, "Unable to open that file");
				return;
			}
			currentPath = path;
			for(String line : lines) {
				try {
					parseLine(line);
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(JVDraw.this, "Corrupted file!");
				}
			}
			
			model.clearModifiedFlag();
		}

		/**
		 * Parses the line of the .jvd and creates an {@link GeometricalObject}
		 * which is added to the model
		 * 
		 * @param line
		 * @throws NumberFormatException
		 */
		private void parseLine(String line) throws NumberFormatException{
			String[] splitted = line.split(" ");
			if(line.startsWith("LINE")) {
				Point startingPoint = new Point(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
				Point endingPoint = new Point(Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]));
				Color color = new Color(Integer.parseInt(splitted[5]), Integer.parseInt(splitted[6]), Integer.parseInt(splitted[7]));
				model.add(new Line(startingPoint,endingPoint,color));
			} else if(line.startsWith("CIRCLE")) {
				Point center = new Point(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
				int radius = Integer.parseInt(splitted[3]);
				Color color = new Color(Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]), Integer.parseInt(splitted[6]));
				model.add(new Circle(center, color, radius));
				
			} else if(line.startsWith("FCIRCLE")) {
				Point center = new Point(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
				int radius = Integer.parseInt(splitted[3]);
				Color fgColor = new Color(Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]), Integer.parseInt(splitted[6]));
				Color bgColor = new Color(Integer.parseInt(splitted[7]), Integer.parseInt(splitted[8]), Integer.parseInt(splitted[9]));
				model.add(new FilledCircle(center, radius, bgColor, fgColor));
			}
			
		}
	};
	
	/**
	 * Exports the file as png, gif or jpg.
	 * Other formats are not allowed
	 */
	@SuppressWarnings("serial")
	private final Action exportAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			Path path = null;
			jfc.setDialogTitle("Load document");
			jfc.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif"));
			
			if(jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this,
						"Nothing has been selected for export", 
						"Information",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			path = jfc.getSelectedFile().toPath();
			if(!(path.toString().endsWith(".jpg") || 
				 path.toString().endsWith(".png") ||
				 path.toString().endsWith(".gif"))) {
				JOptionPane.showMessageDialog(JVDraw.this, "Cannot export files which are not png, jpg or gif");
				return;
			}
			GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
			for(int i = 0; i < model.getSize(); i++) {
				model.getObject(i).accept(bbcalc);
			}
			Rectangle box = bbcalc.getBoundingBox();
			BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, box.width, box.height);
			g.translate(-box.x, -box.y);
			
			GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
			for(int i = 0; i < model.getSize(); i++) {
				model.getObject(i).accept(painter);
			}
			g.dispose();
			File file = path.toFile();
			if(!Files.exists(path)) {
				try {
					file = Files.createFile(path).toFile();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(JVDraw.this, "Could not create file");
					return;
				}
			}
			String pathString = path.toString();
			String fileExtension = pathString.substring(pathString.length()-3);
			try {
				ImageIO.write(image, fileExtension, file);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JVDraw.this, "Failed to export image");
				return;
			}
			JOptionPane.showMessageDialog(JVDraw.this, "Successfully exported image");
			
			
		}
	};
	
	/**
	 * Exits the program, if user has changed something he is first asked
	 * to save the current work.
	 * 
	 */
	@SuppressWarnings("serial")
	private final Action exitAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(model.isModified() == true) {
				int response = JOptionPane.showConfirmDialog(JVDraw.this,
						"File has been modified do you want to save file?", "Question",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION) {
					return;
				} else if (response == JOptionPane.YES_OPTION) {
					saveAction.actionPerformed(e);
				}
			}
			Runtime.getRuntime().halt(0);
			
		}
	};
	
	/**
	 * Saves to the current path, if first save has not happened it is used as save as action
	 */
	@SuppressWarnings("serial")
	private final Action saveAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(firstSave == true ) {
				saveAsAction.actionPerformed(e);
			} else {
				SaveVisitor visitor = new SaveVisitor();
				PrintWriter writer;
				try {//used to empty an existing file
					writer = new PrintWriter(currentPath.toFile());
					writer.print("");
					writer.close();
				} catch (FileNotFoundException e1) {
				}
				try {
					for(int i = 0; i < model.getSize(); i++) {
						model.getObject(i).acceptSaveVisitor(visitor, currentPath);
					}
					model.clearModifiedFlag();
				} catch(IOException ex) {
					JOptionPane.showMessageDialog(JVDraw.this, "Error occured while saving");
				}
				JOptionPane.showMessageDialog(JVDraw.this, "File has been saved");
				
			}
			
			
		}
	};
	
	/**
	 * Saves the file to the selected .jvd destination.
	 */
	@SuppressWarnings("serial")
	private final Action saveAsAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			firstSave = false;
			model.clearModifiedFlag();
			JFileChooser jfc = new JFileChooser();
			jfc.addChoosableFileFilter(new FileNameExtensionFilter("*.jvd", "jvd"));
			jfc.setDialogTitle("Save document");
			currentPath = null;
			if(jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this,
						"Nothing has been saved", 
						"Information",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			currentPath = jfc.getSelectedFile().toPath();
			if(! currentPath.toString().endsWith(".jvd")) {
				String realPath = currentPath.toString() + ".jvd";
				currentPath = Paths.get(realPath);
			}

			if(Files.exists(currentPath)) {
				int response = JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to overwrite the file?",
						"Save document",JOptionPane.YES_NO_OPTION);
				if(response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) {
					return;
				}
				PrintWriter writer;
				try {//used to empty an existing file
					writer = new PrintWriter(currentPath.toFile());
					writer.print("");
					writer.close();
				} catch (FileNotFoundException e1) {
				}
				
				
			} else {
				try {
					Files.createFile(currentPath);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(JVDraw.this, "Unable to create file");
				}
			}
			try {
				SaveVisitor visitor = new SaveVisitor();
				for(int i = 0; i < model.getSize(); i++) {
					model.getObject(i).acceptSaveVisitor(visitor, currentPath);
				}
				model.clearModifiedFlag();
			} catch(IOException ex) {
				JOptionPane.showMessageDialog(JVDraw.this, "Error occured while savnig");
			}
			JOptionPane.showMessageDialog(JVDraw.this, "File has been saved");
			
		}
	};



	/**
	 * Main program of the class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			new JVDraw().setVisible(true);
		});
	}

}
