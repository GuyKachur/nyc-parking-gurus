package nyc.servlet;

import nyc.dal.*;
import nyc.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

@WebServlet("/collisionupdate")
public class CollisionUpdate extends HttpServlet{
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

        // Retrieve collision and validate.
        String collisionPKString = req.getParameter("collisionpk");
        if (collisionPKString == null || collisionPKString.trim().isEmpty()) {
            messages.put("success", "Please enter a valid CollisionPK.");
        } else {
        	try {
        		int collisionPK = Integer.parseInt(collisionPKString);
        		Collision collision = collisionDao.getCollisionByID(collisionPK);
        		if(collision == null) {
        			messages.put("success", "CollisionPK does not exist.");
        		}
        		req.setAttribute("collision", collision);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CollisionUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve collision and validate.
        String collisionPKString = req.getParameter("collisionpk");
        if (collisionPKString == null || collisionPKString.trim().isEmpty()) {
            messages.put("success", "Please enter a valid CollisionPK.");
        } else {
        	try {
        		int collisionPK = Integer.parseInt(collisionPKString);
        		Collision collision = collisionDao.getCollisionByID(collisionPK);
        		if(collision == null) {
        			messages.put("success", "CollisionPK does not exist. No update to perform.");
        		} else {
        			String colName = req.getParameter("colname");
        			String newValue = req.getParameter("newvalue");
        			if (newValue == null || newValue.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid new value.");
        	        } else if (colName == null || colName.trim().isEmpty()) {
        	        	messages.put("success", "Please enter a valid column name");
        	        } else {
        	        	collision = collisionDao.updateCollision(collision, colName, newValue);
        	        	messages.put("success", "Successfully updated " + collisionPK);
        	        }
        		}
        		req.setAttribute("collision", collision);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CollisionUpdate.jsp").forward(req, resp);
    }
}
