package hr.fer.zemris.java.jsdemo.servlets;

import java.awt.AlphaComposite;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * {@link HttpServlet} used to load thumb and get it from thumbnails folder.
 * If it does not exist it creates a smaller image from the big one in slike folder.
 * 
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet("/servlets/getThumb")
public class GetThumb extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName = req.getParameter("name");
		
		Path thumbFolder = Paths.get(req.getServletContext().getRealPath("/WEB-INF/thumbnails"));

		
		if(! Files.exists(thumbFolder)) {
			Files.createDirectory(thumbFolder);
		}
		
		String bigImagePath = req.getServletContext().getRealPath("/WEB-INF/slike/"+fileName);
		String thumbPath = req.getServletContext().getRealPath("/WEB-INF/thumbnails/"+fileName);
		
		if(!Files.exists(Paths.get(thumbPath) )) {
			BufferedImage inputImage = ImageIO.read(new File(bigImagePath));
			 
	        // creates output image
	        BufferedImage smallImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
	 
	        // scales the input image to the output image
	        Graphics2D g2d = smallImage.createGraphics();
	        g2d.setComposite(AlphaComposite.Src);
	        g2d.drawImage(inputImage, 0, 0, 150, 150, null);
	        g2d.dispose();
	        
	        ImageIO.write(smallImage, "jpg", new File(thumbPath));//save thumb
		}
		
		BufferedImage thumbNail = ImageIO.read(new File(thumbPath));
		
		OutputStream os = resp.getOutputStream();
		ImageIO.write(thumbNail, "jpg", os);
		
	}
}
