package rpc;
import external.TicketMasterAPI ;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Item;
import db.DBConnection;
import db.DBConnectionFactory;


/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//HttpSession session =request.getSession(false);
		//if(session==null) {
			//response.setStatus(403);
			//return;
		//}
		//String userId=session.getAttribute("user_id").toString();  
		double lon=Double.parseDouble(request.getParameter("lon"));
		double lat=Double.parseDouble(request.getParameter("lat"));
		//double lon=57.64911;
		//double lat=10.40744;
		
		//TicketMasterAPI tmAPI= new TicketMasterAPI();
         //List<Item> items = tmAPI.search(lat, lon, null);
		String term = request.getParameter("term");
        DBConnection connection = DBConnectionFactory.getConnection();
        try {
        	List<Item> items = connection.searchItems(lat, lon, term);
        	//Set<String> favoritedItemIds = connection.getFavoriteItemIds(userId);
        	System.out.println("INSERT SUCCESS");
        	JSONArray array = new JSONArray();
        	for (Item item : items) {
        		//keep the favorite item being chosen last time
        		JSONObject obj = item.toJSONObject();
				//obj.put("favorite", favoritedItemIds.contains(item.getItemId()));
				array.put(obj);
        	}
        	RpcHelper.writeJsonArray(response, array);

        	} catch (Exception e) {
        	e.printStackTrace();
        	} finally {
        	connection.close();
        	}
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
