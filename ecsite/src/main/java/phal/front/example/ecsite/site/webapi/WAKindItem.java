package phal.front.example.ecsite.site.webapi;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import phal.front.example.ecsite.site.facade.FCKindItem;
import phal.front.example.ecsite.util.ResultCodeMessage;


@RequestScoped
@Path("kind_item")
public class WAKindItem {
	@Inject
    private FCKindItem fcKindItem;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpGetFindAll(@Context final HttpServletResponse response) throws IOException {
		ResultCodeMessage rcm = fcKindItem.findAll();
		if(rcm.code != 100 && rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpGetFind(
			@Context final HttpServletResponse response,
			@QueryParam("kind_id") String kindId,
			@QueryParam("item_id") String itemId
	) throws IOException {
		ResultCodeMessage rcm = fcKindItem.find(kindId, itemId);
		if(rcm.code != 100 && rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpPost(
			@Context final HttpServletResponse response,
			@FormParam("kind_id") String kindId,
			@FormParam("item_id") String itemId
	) throws IOException {
		ResultCodeMessage rcm = fcKindItem.insert(kindId, itemId);
		if(rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}


	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpDelete(
			@Context final HttpServletResponse response,
			@QueryParam("kind_id") String kindId,
			@QueryParam("item_id") String itemId

	) throws IOException {
		ResultCodeMessage rcm = fcKindItem.delete(kindId, itemId);
		if(rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}
}
