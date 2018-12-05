package phal.front.example.ecsite.site.webapi;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import phal.front.example.ecsite.site.facade.FCItem;
import phal.front.example.ecsite.util.ResultCodeMessage;

@RequestScoped
@Path("item")
public class WAItem {
	@Inject
	private FCItem fcItem;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpGetFindAll(@Context final HttpServletResponse response) throws IOException {
		ResultCodeMessage rcm = fcItem.findAll();
		if(rcm.code != 100 && rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}

	@GET
	@Path("/search_name")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpGetFindSearchName(
			@Context final HttpServletResponse response,
			@QueryParam("search_name") String searchName
	) throws IOException {
		ResultCodeMessage rcm = fcItem.findSearchName(searchName);
		if(rcm.code != 100 && rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}

	@GET
	@Path("/kind")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpGetFindKindId(
			@Context final HttpServletResponse response,
			@QueryParam("kind_id") String kindId
	) throws IOException {
		ResultCodeMessage rcm = fcItem.findKindId(kindId);
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
			@QueryParam("item_id") String itemId
	) throws IOException {
		ResultCodeMessage rcm = fcItem.find(itemId);
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
			@FormParam("item_id") String itemId,
			@FormParam("item_name") String itemName,
			@FormParam("item_price") String itemPrice,
			@FormParam("item_stock") String itemStock
	) throws IOException {
		List<String> messages = new ArrayList<>();
		BigDecimal itemPriceBD = null;
		int itemStockI = 0;
		try {
			itemPriceBD = new BigDecimal(itemPrice);
		}catch(Exception ex) {
			messages.add("itemPrice の変換に失敗しました。");
		}
		try {
			itemStockI = Integer.valueOf(itemStock);
		}catch(Exception ex) {
			messages.add("itemStock の変換に失敗しました。");
		}
		if(messages.size() > 0) {
			ResultCodeMessage rcm = new ResultCodeMessage();
			rcm.messages = messages;
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
			return rcm;
		}

		ResultCodeMessage rcm = fcItem.insert(itemId, itemName, itemPriceBD, itemStockI);
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
			@FormParam("item_id") String itemId,
			@FormParam("item_name") String itemName,
			@FormParam("item_price") String itemPrice,
			@FormParam("item_stock") String itemStock
	) throws IOException {
		List<String> messages = new ArrayList<>();
		BigDecimal itemPriceBD = null;
		int itemStockI = 0;
		try {
			itemPriceBD = new BigDecimal(itemPrice);
		}catch(Exception ex) {
			messages.add("itemPrice の変換に失敗しました。");
		}
		try {
			itemStockI = Integer.valueOf(itemStock);
		}catch(Exception ex) {
			messages.add("itemStock の変換に失敗しました。");
		}
		if(messages.size() > 0) {
			ResultCodeMessage rcm = new ResultCodeMessage();
			rcm.messages = messages;
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
			return rcm;
		}

		ResultCodeMessage rcm = fcItem.update(itemId, itemName, itemPriceBD, itemStockI);
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
			@QueryParam("item_id") String itemId
	) throws IOException {
		ResultCodeMessage rcm = fcItem.delete(itemId);
		if(rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}
}
