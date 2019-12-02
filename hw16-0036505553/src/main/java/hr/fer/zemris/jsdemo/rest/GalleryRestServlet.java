package hr.fer.zemris.jsdemo.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.jsdemo.model.Photo;
import hr.fer.zemris.java.jsdemo.model.PhotoDB;
/**
 * Used to return a tags list and a list of thumbs for the given tag.
 * 
 * @author Patrik Okanovic
 *
 */
@Path("/gallery")
public class GalleryRestServlet {
	/**
	 * Returns all the tags used to create buttons.
	 * @return
	 */
	@Path("/tags")
	@GET
	@Produces("application/json")
	public Response getTagsList() {
		List<String> tags = PhotoDB.getTags();
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(tags);
		
		return Response.status(Status.OK).entity(jsonText).build();
	}
	/**
	 * Returns {@link Photo} in JSON format for the given tag.
	 * @param tag
	 * @return
	 * @throws IOException
	 */
	@Path("/thumbs/{tag}")
	@GET
	@Produces("application/json")
	public Response getThumbsList(@PathParam("tag") String tag) throws IOException {
		
		List<Photo> photos = PhotoDB.getPhotosWithTag(tag);
		
		
		
		Gson gson = new Gson();
        String jsonText = gson.toJson(photos);
        
        return Response.status(Status.OK).entity(jsonText).build();	}
	
	/**
	 * Used to get info about {@link Photo} for the given fileName.
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	@Path("/getPhotoInfo/{name}")
	@GET
	@Produces("application/json")
	public Response getPhotoInfo(@PathParam("name") String name) throws IOException {
		
		Photo photo = PhotoDB.getPhoto(name);

		Gson gson = new Gson();
		String jsonText = gson.toJson(photo);

		return Response.status(Status.OK).entity(jsonText).build();	
    }

}
