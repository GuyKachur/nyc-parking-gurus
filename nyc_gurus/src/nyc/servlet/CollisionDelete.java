package nyc.servlet;

import nyc.dal.*;
import nyc.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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

@WebServlet("/collisiondelete")
public class CollisionDelete extends HttpServlet {

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
        // Provide a title and render the JSP.
        messages.put("title", "Delete Collision");        
        req.getRequestDispatcher("/CollisionDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate collision key.
        String collisionPKString = req.getParameter("collisionpk");
        long collisionPK = -1;
        collisionPK = Long.parseLong(collisionPKString);
        if (collisionPK == -1) {
            messages.put("title", "Invalid CollisionPK");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the Collision.
	        Collision collision = new Collision(collisionPK, 0l, 0l, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	        try {
	        	collision = collisionDao.delete(collision);
	        	// Update the message.
		        if (collision == null) {
		            messages.put("title", "Successfully deleted " + collisionPK);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + collisionPK);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CollisionDelete.jsp").forward(req, resp);
    }
}
