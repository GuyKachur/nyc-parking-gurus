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

@WebServlet("/findcollisions")
public class FindCollisions extends HttpServlet {
	protected CollisionDAO collisionDao;
	
	@Override
	public void init() throws ServletException {
		collisionDao = CollisionDAO.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Collision> collisions = new ArrayList<Collision>();
        
        // Retrieve and validate borough.
        // Borough is retrieved from the URL query string.
        String borough = req.getParameter("borough");
        if (borough == null || borough.trim().isEmpty()) {
            messages.put("success", "Please enter a valid borough.");
        } else {
        	// Retrieve Collisions, and store as a message.
        	try {
            	collisions = collisionDao.getCollisionsByBorough(borough);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + borough);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previousBorough", borough);
        }
        req.setAttribute("collisions", collisions);
        
        req.getRequestDispatcher("/FindCollisions.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Collision> collisions = new ArrayList<Collision>();
        
        // Retrieve and validate name.
        // borough is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindCollisions.jsp).
        String borough = req.getParameter("borough");
        if (borough == null || borough.trim().isEmpty()) {
            messages.put("success", "Please enter a valid borough.");
        } else {
        	// Retrieve Collisions, and store as a message.
        	try {
            	collisions = collisionDao.getCollisionsByBorough(borough);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + borough);
        }
        req.setAttribute("collisions", collisions);
        
        req.getRequestDispatcher("/FindCollisions.jsp").forward(req, resp);
    }
}
