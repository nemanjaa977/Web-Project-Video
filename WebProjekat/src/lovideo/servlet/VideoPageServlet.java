package lovideo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import lovideo.dao.KomentarDao;
import lovideo.dao.KorisnikDAO;
import lovideo.dao.VideoDAO;
import lovideo.model.Komentar;
import lovideo.model.Korisnik;
import lovideo.model.Video;
import lovideo.model.Video.Vidljivost;


public class VideoPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSubscribe=false;
		try {
			HttpSession session = request.getSession();
			int id=Integer.parseInt(request.getParameter("id"));
			Korisnik logovani = (Korisnik) session.getAttribute("logovaniKorisnik");
			Video video = VideoDAO.get(id);
			
			if(logovani != null) {
				if(!logovani.getKorisnickoIme().equals(video.getVlasnik().getKorisnickoIme())) {
					//proverava da li je logovani korisnik koji nije vlasnik subscribe na vlasnik vudea, vraca true ili false 
					//da bi znao koje dugme prikazati(subscribe, unsubscribe)
					int subs=KorisnikDAO.findSubscribed(video.getVlasnik().getKorisnickoIme(), logovani.getKorisnickoIme());
					if(subs != 0) {
						isSubscribe = true;
					}
				}
			}
			
			video.setBrojPregleda(video.getBrojPregleda()+1);
			VideoDAO.updateVideo(video);
			ArrayList<Komentar> komentarii = KomentarDao.getComments(video.getId());
			
			Map<String, Object> data = new HashMap<>();
			data.put("videos", video);
			data.put("logovani", logovani);
			data.put("isSubscribe", isSubscribe);
			data.put("komentarii", komentarii);
			
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
			Video v=new Video(id, url, "photos/video.jpg", name, description, visibility, allowComment, allowRating, false, 0, 0, 0, KorisnikDAO.dateToStringForWrite(d), logovani, false);
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
			
		}else if(status.equals("izmena")) {
			int id=Integer.parseInt(request.getParameter("id"));
			String opis = request.getParameter("editedDescription");
			boolean komentari = Boolean.parseBoolean(request.getParameter("comments"));
			String vidljivostString = request.getParameter("vid");
			boolean rejting = Boolean.parseBoolean(request.getParameter("rating"));
			boolean blokiran = Boolean.parseBoolean(request.getParameter("block"));
			
			Vidljivost vidljivost = Vidljivost.valueOf(vidljivostString);
				
			Video video = VideoDAO.get(id);
			video.setOpis(opis);
			video.setDozvoljeniKomentari(komentari);
			video.setVidljivost(vidljivost);
			video.setRejtingVidljivost(rejting);
			video.setBlokiran(blokiran);
					
			VideoDAO.updateVideo(video);
			Map<String, Object> data = new HashMap<>();
			data.put("status", "success");
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}
	}

}
