package lovideo.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import lovideo.dao.KorisnikDAO;
import lovideo.dao.VideoDAO;
import lovideo.model.Korisnik;
import lovideo.model.Video;
import lovideo.model.Video.Vidljivost;

/**
 * Servlet implementation class VideoPageServlet
 */
public class VideoPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id=Integer.parseInt(request.getParameter("id"));
			Video video = VideoDAO.get(id);
			
			Map<String, Object> data = new HashMap<>();
			data.put("videos", video);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		} catch (Exception e) {
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String status=request.getParameter("status");
		HttpSession session = request.getSession();
		Korisnik logovani = (Korisnik) session.getAttribute("logovaniKorisnik");
		
		if(status.equals("dodavanje")) {
			boolean allowComment = Boolean.parseBoolean(request.getParameter("allowComment"));
			boolean allowRating = Boolean.parseBoolean(request.getParameter("allowRating"));
			String name = request.getParameter("NAMEValue");
			String url = request.getParameter("URLValue");
			String description = request.getParameter("DESValue");
			String visibilityString = request.getParameter("visibility");
			Vidljivost visibility;
			if(visibilityString.equals("Public")) {
				visibility=Vidljivost.PUBLIC;
			}else if(visibilityString.equals("Private")) {
				visibility=Vidljivost.PRIVATE;
			}else {
				visibility=Vidljivost.UNLISTED;
			}
			
			int id =VideoDAO.getVideoId();
			Date d=new Date();
			Video v=new Video(id, url, "photos/Zac.jpg", name, description, visibility, allowComment, allowRating, false, 0, 0, 0, KorisnikDAO.dateToStringForWrite(d), logovani, false);
			VideoDAO.addVideo(v);
			Map<String, Object> data = new HashMap<>();
			data.put("status", "success");
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
		}else if(status.equals("brisanje")) {	
			int id=Integer.parseInt(request.getParameter("videoId"));
			Video video = VideoDAO.get(id);
			video.setObrisan(true);
			VideoDAO.updateVideo(video);
			
			Map<String, Object> data = new HashMap<>();		
			data.put("status", "success");
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);
		}
	}

}
