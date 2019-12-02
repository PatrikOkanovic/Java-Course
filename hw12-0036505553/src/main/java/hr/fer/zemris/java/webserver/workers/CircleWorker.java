package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * Implements {@link IWebWorker}.
 * Draws a blue circle in the middle of the page.
 * 
 * @author Patrik Okanovic
 *
 */
public class CircleWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		// do drawing...
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, 200, 200);
		g2d.setColor(Color.blue);
		g2d.fillOval(0,0,200,200);
		g2d.dispose();
		context.setMimeType("image/png");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
