package hr.fer.zemris.java.jsdemo.init;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.jsdemo.model.Photo;
import hr.fer.zemris.java.jsdemo.model.PhotoDB;
/**
 * Used to fill {@link PhotoDB} with {@link Photo} from the definition text file
 * : opisnik.txt
 * 
 * @author Patrik Okanovic
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String path = sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt");
		
		if(! Files.exists(Paths.get(path))) {
			throw new RuntimeException("Cannot do anything without definition of photos.");
		}
		
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			throw new RuntimeException("Cannot read the file");
		}
		
		for(int i = 0 ; i + 2 < lines.size(); i += 3) {
			String fileName = lines.get(i);

			String title = lines.get(i + 1);

			String tags = lines.get(i + 2);;
			String[] tagSplitted = tags.split(",");
			for(int j = 0; j < tagSplitted.length; j++) {
				tagSplitted[j] = tagSplitted[j].trim();
				PhotoDB.tags.add(tagSplitted[j]);
			}
			Photo photo = new Photo(title, tagSplitted, fileName);
			PhotoDB.photos.add(photo);
			
			
		}
		
		
	
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
