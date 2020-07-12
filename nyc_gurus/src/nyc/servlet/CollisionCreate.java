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

@WebServlet("/collisioncreate")
public class CollisionCreate extends HttpServlet {
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
        //Just render the JSP.   
        req.getRequestDispatcher("/CollisionCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

    	// Create the Collision.
        float latitude = 0.0f;
        float longitude = 0.0f;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String borough = req.getParameter("borough");
        int zipCode = 0;
        int peopleInjured = 0;
        int peopleKilled = 0;
        int pedestriansInjured = 0;
        int pedestriansKilled = 0;
        int cyclistsInjured = 0;
        int cyclistsKilled = 0;
        int motoristsInjured = 0;
        int motoristsKilled = 0;
        String latitudeString = req.getParameter("latitude");
        String longitudeString = req.getParameter("longitude");
        String dateString = req.getParameter("date"); 
        String zipCodeString = req.getParameter("zipcode");
        String peopleInjuredString = req.getParameter("peopleinjured");
        String pedestriansInjuredString = req.getParameter("pedestriansinjured");
        String pedestriansKilledString = req.getParameter("pedestrianskilled");
        String cyclistsInjuredString = req.getParameter("cyclistsinjured");
        String cyclistsKilledString = req.getParameter("cyclistskilled");
        String motoristsInjuredString = req.getParameter("motoristsinjured");
        String motoristsKilledString = req.getParameter("motoristskilled");
        try {
        	latitude = Float.parseFloat(latitudeString);
        	longitude = Float.parseFloat(longitudeString);
        	date = df.parse(dateString);
        	zipCode = Integer.parseInt(zipCodeString);
        	peopleInjured  = Integer.parseInt(peopleInjuredString);
        	peopleKilled = Integer.parseInt(peopleInjuredString);
        	pedestriansInjured = Integer.parseInt(pedestriansInjuredString);
        	pedestriansKilled = Integer.parseInt(pedestriansKilledString);
        	cyclistsInjured = Integer.parseInt(cyclistsInjuredString);
        	cyclistsKilled = Integer.parseInt(cyclistsKilledString);
        	motoristsInjured = Integer.parseInt(motoristsInjuredString);
        	motoristsKilled = Integer.parseInt(motoristsKilledString);
        	
        } catch (ParseException e) {
        	e.printStackTrace();
        	throw new IOException(e);
        }
        try {
        	Collision collision = new Collision(0L, longitude, latitude, date, borough, zipCode, peopleInjured, peopleKilled, pedestriansInjured, pedestriansKilled, cyclistsInjured, cyclistsKilled, motoristsInjured, motoristsKilled);
        	collision = collisionDao.create(collision);
        	messages.put("success", "Successfully created collision with key " + collision.getKey());
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        req.getRequestDispatcher("/CollisionCreate.jsp").forward(req, resp);
    }
	
	
}
