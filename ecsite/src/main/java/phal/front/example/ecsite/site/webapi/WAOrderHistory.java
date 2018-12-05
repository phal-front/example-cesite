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

import phal.front.example.ecsite.site.facade.FCOrderHistory;
import phal.front.example.ecsite.util.ResultCodeMessage;


@RequestScoped
@Path("order_history")
public class WAOrderHistory {
	@Inject
    private FCOrderHistory fcorderHistory;;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpGetFindAll(@Context final HttpServletResponse response) throws IOException {
		ResultCodeMessage rcm = fcorderHistory.findAll();
		if(rcm.code != 100 && rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}

	@GET
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

		ResultCodeMessage rcm = fcorderHistory.find(orderHistoryIdL);
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
			@FormParam("order_history_send_name") String orderHistorySendName,
			@FormParam("order_history_send_addr") String orderHistorySendAddr,
			@FormParam("order_history_send_email") String orderHistorySendEmail
	) throws IOException {

		ResultCodeMessage rcm = fcorderHistory.insert(orderHistorySendName, orderHistorySendAddr, orderHistorySendEmail);
		if(rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}

	@POST
	@Path("/bill")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultCodeMessage httpPost(
			@Context final HttpServletResponse response,
			@FormParam("order_history_id") String orderHistoryId,
			@FormParam("order_history_send_name") String orderHistorySendName,
			@FormParam("order_history_send_addr") String orderHistorySendAddr,
			@FormParam("order_history_send_email") String orderHistorySendEmail,
			@FormParam("item_id[]") List<String> itemIds,
			@FormParam("item_price[]") List<String> itemPrices
	) throws IOException {
		List<BigDecimal> itemPricesBD = new ArrayList<>();
		for(int i=0; i<itemIds.size(); i++) {
			List<String> messages = new ArrayList<>();
			BigDecimal itemPriceBD = null;
			try {
				itemPriceBD = new BigDecimal(itemPrices.get(i));
			}catch(Exception ex) {
				messages.add("itemPrice の変換に失敗しました。");
			}
			if(messages.size() > 0) {
				ResultCodeMessage rcm = new ResultCodeMessage();
				rcm.messages = messages;
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			    response.flushBuffer();
				return rcm;
			}
			itemPricesBD.add(itemPriceBD);
		}

		ResultCodeMessage rcm = fcorderHistory.insertBill(
				orderHistorySendName,
				orderHistorySendAddr,
				orderHistorySendEmail,
				itemIds,
				itemPricesBD
				);
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

		ResultCodeMessage rcm = fcorderHistory.delete(orderHistoryIdL);
		if(rcm.code > 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.flushBuffer();
		}
		return rcm;
	}
}
