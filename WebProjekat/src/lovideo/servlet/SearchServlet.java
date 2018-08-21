package lovideo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import lovideo.dao.VideoDAO;
import lovideo.model.Korisnik;
import lovideo.model.Video;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Video> videos=null;
		Korisnik loggedInUser = null;
		String uneto = "";
		try {	
			
			HttpSession session = request.getSession();
			loggedInUser = (Korisnik) session.getAttribute("logovaniKorisnik");
			uneto = request.getParameter("uneto");
			
			if(uneto == "") {
				if(loggedInUser != null) {
					if(loggedInUser.getUloga().toString().equals("ADMINISTRATOR")) {
						videos=VideoDAO.getAll();
						
					}else {
						videos=VideoDAO.getAllPublic();
					}
				}else {
					videos=VideoDAO.getAllPublic();
				}
			}
			else {
			videos=VideoDAO.getAllPublicSearch(uneto);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("videos", videos);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
