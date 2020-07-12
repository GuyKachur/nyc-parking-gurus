package nyc.servlet;

import nyc.dal.*;
import nyc.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/usertrips")
public class UserTrips extends HttpServlet {
	protected TripDAO tripDao;
	
	@Override
	public void init() throws ServletException {
		tripDao = TripDAO.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
		// Retrieve and validate UserName.
        String userName = req.getParameter("username");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("title", "Invalid username.");
        } else {
        	messages.put("title", "Trips for " + userName);
        }
        
        List<Trip> trips = new ArrayList<Trip>();
        try {
        	trips = tripDao.getTripsByUserName(userName);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("trips", trips);
        req.getRequestDispatcher("/UserTrips.jsp").forward(req, resp);
	}
}
