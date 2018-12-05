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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import phal.front.example.ecsite.site.facade.FCKind;
import phal.front.example.ecsite.util.ResultCodeMessage;


@RequestScoped
@Path("kind")
public class WAKind {
	@Inject
    private FCKind fcKind;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpGetFindAll(@Context final HttpServletResponse response) throws IOException {
		ResultCodeMessage rcm = fcKind.findAll();
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
			@QueryParam("kind_id") String kindId
	) throws IOException {
		ResultCodeMessage rcm = fcKind.find(kindId);
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
			@FormParam("kind_name") String kindName
	) throws IOException {
		ResultCodeMessage rcm = fcKind.insert(kindId, kindName);
		if(rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpPut(
			@Context final HttpServletResponse response,
			@FormParam("kind_id") String kindId,
			@FormParam("kind_name") String kindName
	) throws IOException {
		ResultCodeMessage rcm = fcKind.update(kindId, kindName);
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
			@QueryParam("kind_id") String kindId
	) throws IOException {
		ResultCodeMessage rcm = fcKind.delete(kindId);
		if(rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}
}
