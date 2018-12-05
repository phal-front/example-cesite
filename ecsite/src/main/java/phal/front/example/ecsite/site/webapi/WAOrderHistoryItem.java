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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import phal.front.example.ecsite.site.facade.FCOrderHistoryItem;
import phal.front.example.ecsite.util.ResultCodeMessage;


@RequestScoped
@Path("order_history_item")
public class WAOrderHistoryItem {
	@Inject
    private FCOrderHistoryItem fcorderHistoryItem;;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpGetFindAll(@Context final HttpServletResponse response) throws IOException {
		ResultCodeMessage rcm = fcorderHistoryItem.findAll();
		if(rcm.code != 100 && rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}

	@GET
	@Path("/order_history")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpGetFindOrderHistoryId(
			@Context final HttpServletResponse response,
			@QueryParam("order_history_id") String orderHistoryId
	) throws IOException {
		List<String> messages = new ArrayList<>();
		long orderHistoryIdL = 0;
		try {
			orderHistoryIdL = Integer.valueOf(orderHistoryId);
		}catch(Exception ex) {
			messages.add("orderHistoryId の変換に失敗しました。");
		}
		if(messages.size() > 0) {
			ResultCodeMessage rcm = new ResultCodeMessage();
			rcm.messages = messages;
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
			return rcm;
		}

		ResultCodeMessage rcm = fcorderHistoryItem.find(orderHistoryIdL);
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
			@QueryParam("order_history_id") String orderHistoryId,
			@QueryParam("item_id") String itemId
	) throws IOException {
		List<String> messages = new ArrayList<>();
		long orderHistoryIdL = 0;
		try {
			orderHistoryIdL = Integer.valueOf(orderHistoryId);
		}catch(Exception ex) {
			messages.add("orderHistoryId の変換に失敗しました。");
		}
		if(messages.size() > 0) {
			ResultCodeMessage rcm = new ResultCodeMessage();
			rcm.messages = messages;
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
			return rcm;
		}

		ResultCodeMessage rcm = fcorderHistoryItem.find(orderHistoryIdL, itemId);
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
			@FormParam("order_history_id") String orderHistoryId,
			@FormParam("item_id") String itemId,
			@FormParam("item_price") String itemPrice
	) throws IOException {
		List<String> messages = new ArrayList<>();
		long orderHistoryIdL = 0;
		BigDecimal itemPriceBD = null;
		int itemStockI = 0;
		try {
			itemPriceBD = new BigDecimal(itemPrice);
		}catch(Exception ex) {
			messages.add("itemPrice の変換に失敗しました。");
		}
		try {
			orderHistoryIdL = Integer.valueOf(orderHistoryId);
		}catch(Exception ex) {
			messages.add("orderHistoryId の変換に失敗しました。");
		}
		if(messages.size() > 0) {
			ResultCodeMessage rcm = new ResultCodeMessage();
			rcm.messages = messages;
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
			return rcm;
		}

		ResultCodeMessage rcm = fcorderHistoryItem.insert(orderHistoryIdL, itemId, itemPriceBD);
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
			@QueryParam("order_history_id") String orderHistoryId,
			@QueryParam("item_id") String itemId

	) throws IOException {
		List<String> messages = new ArrayList<>();
		long orderHistoryIdL = 0;
		try {
			orderHistoryIdL = Integer.valueOf(orderHistoryId);
		}catch(Exception ex) {
			messages.add("orderHistoryId の変換に失敗しました。");
		}
		if(messages.size() > 0) {
			ResultCodeMessage rcm = new ResultCodeMessage();
			rcm.messages = messages;
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
			return rcm;
		}

		ResultCodeMessage rcm = fcorderHistoryItem.delete(orderHistoryIdL, itemId);
		if(rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}
}
