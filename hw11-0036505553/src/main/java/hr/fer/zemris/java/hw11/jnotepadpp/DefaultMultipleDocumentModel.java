package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * Implementation of {@link MultipleDocumentModel}.
 * Used to implement {@link JNotepadPP}
 * 
 * @author Patrik Okanovic
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{


	private static final long serialVersionUID = 1L;
	/**
	 * Storage for {@link SingleDocumentModel}
	 */
	private List<SingleDocumentModel> documents = new ArrayList<>();
	/**
	 * The current {@link SingleDocumentModel}
	 */
	private SingleDocumentModel currentDocument;
	/**
	 * Storage for {@link MultipleDocumentListener}
	 */
	private List<MultipleDocumentListener> listeners = new ArrayList<>();
	/**
	 * Constructor of the class, adds a listener so that every time an other tab is selected
	 * currentDocument will become that tab
	 */
	public DefaultMultipleDocumentModel() {
		this.addChangeListener(l-> {
			currentDocument = documents.get(this.getSelectedIndex());
			notifyListenersDocumentChanged(null, currentDocument);
			
		});

	}
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel doc = new DefaultSingleDocumentModel(null, "");
		documents.add(doc);
		notifyListenersDocumentAdded(doc);
		
		createNewTab(doc);
//		currentDocument.getTextComponent().setCaretPosition(0);
		
		currentDocument = doc;
		
		
		
		return doc;
	}

	/**
	 * Used for creating a new unnamed tab. That tab becomes selected after adding it.
	 * Icon is switched between red and green colour depending on whether the selected document has 
	 * been saved or not
	 * 
	 * @param doc {@link SingleDocumentModel}
	 */
	private void createNewTab(SingleDocumentModel doc) {
		ImageIcon icon = getImageIcon("green_disc.png");
		
		String title = doc.getFilePath() == null ? "(unnamed) - JNotepad++" : doc.getFilePath().toString() + " - JNotepad++";
		
		this.addTab(title, icon, new JScrollPane(doc.getTextComponent()));
		
		setSelectedIndex(getNumberOfDocuments() - 1);
		
		doc.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				ImageIcon icon = getImageIcon("green_disc.png");
				if(model.isModified()) {
					 icon = getImageIcon("red_disc.png");
				}
				
				DefaultMultipleDocumentModel.this.setIconAt(documents.indexOf(doc), icon);
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				DefaultMultipleDocumentModel.this.setTitleAt(documents.indexOf(doc), doc.getFilePath().getFileName().toString() + " - JNotepad++");	
				setToolTipTextAt(documents.indexOf(doc), doc.getFilePath().getFileName().toString() + " - JNotepad++");
			}
		});
		
		
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);
		
		SingleDocumentModel oldDocument = currentDocument;
		
		for(SingleDocumentModel document : documents) {
			if(document.getFilePath()!=null && document.getFilePath().equals(path)) {
				currentDocument = document;
				notifyListenersDocumentChanged(oldDocument, currentDocument);
				setSelectedIndex(documents.indexOf(currentDocument));
				return currentDocument;
			}
		}
		
		if(! Files.isReadable(path)) {
			JOptionPane.showMessageDialog(
					this,
					"File" + path + "is not readable", 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		String text = null;
		try {
			text = Files.readString(path);
		} catch(IOException exc) {
			JOptionPane.showMessageDialog(
					this,
					"There has been a mistake while reading file " + path, 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		currentDocument = new DefaultSingleDocumentModel(path, text);
		
		documents.add(currentDocument);
		createNewTab(currentDocument); 
		setToolTipTextAt(getNumberOfDocuments() - 1, path.toString());
		return currentDocument;
		
		
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Objects.requireNonNull(model);
		
		if(newPath == null) {
			newPath = model.getFilePath();//save to the associated path
		}
		
		try {
			Files.writeString(newPath, model.getTextComponent().getText());
		} catch(IOException e1) {
			JOptionPane.showMessageDialog(this,
					"There has been a mistake while saving!", 
					"Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		model.setFilePath(newPath);
		
		model.setModified(false);
		
		JOptionPane.showMessageDialog(this,
				"File has been saved", 
				"Information",
				JOptionPane.INFORMATION_MESSAGE);
		return;
		
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		
		if(index < 0) {
			return;
		}
		
		try {
			this.removeTabAt(index);
		} catch(Exception exc) {
			
		}
		documents.remove(index);
		notifyListenersDocumentRemoved(model);
		if(documents.size() == 0) {
			currentDocument=null;
		} else {
			currentDocument = documents.get(Math.max(documents.size()-1,0));
			notifyListenersDocumentChanged(null, documents.get(Math.max(documents.size()-1,0)));

		}
		
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
		
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}
	
	/**
	 * Used to notify {@link MultipleDocumentListener} when a document has been added.
	 * 
	 * @param doc {@link SingleDocumentModel}
	 */
	private void notifyListenersDocumentAdded(SingleDocumentModel doc) {
		listeners.forEach(l->l.documentAdded(doc));
	}
	/**
	 * Used to notify {@link MultipleDocumentListener} when a document has been removed.
	 * 
	 * @param doc {@link SingleDocumentModel}
	 */
	private void notifyListenersDocumentRemoved(SingleDocumentModel doc) {
		listeners.forEach(l->l.documentRemoved(doc));
	}
	
	/**
	 * Used to notify {@link MultipleDocumentListener} when a document has been changed from oldDocument to newDocument.
	 * 
	 * @param oldDocument {@link SingleDocumentModel}
	 * @param newDocument {@link SingleDocumentModel}
	 */
	private void notifyListenersDocumentChanged(SingleDocumentModel oldDocument, SingleDocumentModel newDocument) {
		listeners.forEach(l->l.currentDocumentChanged(oldDocument, newDocument));
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

}
