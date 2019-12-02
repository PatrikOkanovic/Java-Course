package hr.fer.zemris.java.jsdemo.servlets;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * {@link HttpServlet} used to load picture
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet("/servlets/loadPicture")
public class LoadPictureServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String filename = req.getParameter("name");
        String realPhoto = req.getServletContext().getRealPath("/WEB-INF/slike/"+filename);

        BufferedImage picture = ImageIO.read(new File(realPhoto));

        OutputStream os = resp.getOutputStream();
        ImageIO.write(picture, "jpg", os);
	}
}
