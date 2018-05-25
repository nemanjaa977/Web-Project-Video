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

import lovideo.dao.KomentarDao;
import lovideo.dao.KorisnikDAO;
import lovideo.dao.LikeDislikeDao;
import lovideo.model.Komentar;
import lovideo.model.Korisnik;
import lovideo.model.LikeDislike;


public class KomentarLikeDislikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int id=Integer.parseInt(request.getParameter("id"));
		Korisnik logovani = (Korisnik) session.getAttribute("logovaniKorisnik");
		Komentar komentar = KomentarDao.getComment(id);
		if(logovani != null) {
			LikeDislike li = LikeDislikeDao.getCommentLikeByOwner(komentar.getId(), logovani.getKorisnickoIme());
			if(li == null) {
				Date d=new Date();
				int likeId = LikeDislikeDao.getLikeId();
				LikeDislike l = new LikeDislike(likeId, true, KorisnikDAO.dateToStringForWrite(d), null, komentar, logovani);
				LikeDislikeDao.addLikeDislike(l);
				LikeDislikeDao.addCommentLikeDislike(l.getId(),komentar.getId());
			}
			else if(li != null && li.isLajkovan() == false) {
				li.setLajkovan(true);
				LikeDislikeDao.updateLike(li);
			}
		}
		int brojLike = LikeDislikeDao.getCommentLikeNumber(komentar.getId());
		int brojDislike = LikeDislikeDao.getCommentDislikeNumber(komentar.getId());
		komentar.setBrojLike(brojLike);
		komentar.setBrojDislike(brojDislike);
		KomentarDao.updateKomentar(komentar);
		
		Map<String, Object> data = new HashMap<>();
		data.put("brojLike", brojLike);
		data.put("brojDislike", brojDislike);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int id=Integer.parseInt(request.getParameter("id"));
		Korisnik logovani = (Korisnik) session.getAttribute("logovaniKorisnik");
		Komentar komentar = KomentarDao.getComment(id);
		if(logovani != null) {
			LikeDislike li= LikeDislikeDao.getCommentLikeByOwner(komentar.getId(), logovani.getKorisnickoIme());
			if(li == null) {
				Date d = new Date();
				int likeId = LikeDislikeDao.getLikeId();
				LikeDislike l = new LikeDislike(likeId, false, KorisnikDAO.dateToStringForWrite(d), null, komentar, logovani);
				LikeDislikeDao.addLikeDislike(l);
				LikeDislikeDao.addCommentLikeDislike(l.getId(),komentar.getId());
			}
			else if(li != null && li.isLajkovan() == true) {
				li.setLajkovan(false);
				LikeDislikeDao.updateLike(li);
			}
		}
		int brojLike = LikeDislikeDao.getCommentLikeNumber(komentar.getId());
		int brojDislike = LikeDislikeDao.getCommentDislikeNumber(komentar.getId());
		komentar.setBrojLike(brojLike);
		komentar.setBrojDislike(brojDislike);
		KomentarDao.updateKomentar(komentar);
		
		Map<String, Object> data = new HashMap<>();
		data.put("brojLike", brojLike);
		data.put("brojDislike", brojDislike);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
