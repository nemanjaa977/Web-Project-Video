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
import lovideo.dao.LikeDislikeDao;
import lovideo.dao.VideoDAO;
import lovideo.model.Korisnik;
import lovideo.model.LikeDislike;
import lovideo.model.Video;

public class LikeDislikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		Korisnik logovani = (Korisnik) session.getAttribute("logovaniKorisnik");
		Video video = VideoDAO.get(id);
		
		if(logovani != null) {
			LikeDislike li = LikeDislikeDao.getVideoLajkOdStraneKorisnika(video.getId(), logovani.getKorisnickoIme());
			if(li == null) {
				Date d = new Date();
				int likeId = LikeDislikeDao.getLikeId();
				LikeDislike l = new LikeDislike(likeId, true, KorisnikDAO.dateToStringForWrite(d), video, null, logovani);
				LikeDislikeDao.addLikeDislike(l);
				LikeDislikeDao.addVideoLikeDislike(l.getId(),video.getId());
			}
			else if(li != null && li.isLajkovan() == false) {
				li.setLajkovan(true);
				LikeDislikeDao.updateLike(li);
			}
		}
		int brojLajka = LikeDislikeDao.getVideoBrojLajka(video.getId());
		int brojDislajka = LikeDislikeDao.getVideoBrojDislajka(video.getId());
		video.setBrojLike(brojLajka);
		video.setBrojDislike(brojDislajka);
		VideoDAO.updateVideo(video);
		Map<String, Object> data = new HashMap<>();
		data.put("brojLajka", brojLajka);
		data.put("brojDislajka", brojDislajka);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		Korisnik logovani = (Korisnik) session.getAttribute("logovaniKorisnik");
		Video video = VideoDAO.get(id);
		
		if(logovani!= null) {
			LikeDislike li = LikeDislikeDao.getVideoLajkOdStraneKorisnika(video.getId(), logovani.getKorisnickoIme());
			if(li == null) {
				Date d = new Date();
				int likeId = LikeDislikeDao.getLikeId();
				LikeDislike l = new LikeDislike(likeId, false, KorisnikDAO.dateToStringForWrite(d), video, null, logovani);
				LikeDislikeDao.addLikeDislike(l);
				LikeDislikeDao.addVideoLikeDislike(l.getId(),video.getId());
			}
			else if(li != null && li.isLajkovan() == true) {
				li.setLajkovan(false);
				LikeDislikeDao.updateLike(li);
			}
		}
		int brojDislajka = LikeDislikeDao.getVideoBrojDislajka(video.getId());
		int brojLajka = LikeDislikeDao.getVideoBrojLajka(video.getId());
		video.setBrojLike(brojLajka);
		video.setBrojDislike(brojDislajka);;
		VideoDAO.updateVideo(video);
		Map<String, Object> data = new HashMap<>();
		data.put("brojDislajka", brojDislajka);
		data.put("brojLajka", brojLajka);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
