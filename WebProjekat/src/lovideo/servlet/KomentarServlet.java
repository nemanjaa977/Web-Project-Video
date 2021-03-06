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


public class KomentarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		Korisnik logovani = (Korisnik) session.getAttribute("logovaniKorisnik");
		ArrayList<Komentar> komentarii=null;

		komentarii = KomentarDao.getComments(id);
		
		Map<String, Object> data = new HashMap<>();
		data.put("logovani", logovani);
		data.put("komentarii", komentarii);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = request.getParameter("status");
		
		if(status.equals("dodavanje")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String komentar=request.getParameter("komentar");
			HttpSession session = request.getSession();
			Korisnik logovani = (Korisnik) session.getAttribute("logovaniKorisnik");
			Video v=VideoDAO.get(id);
			Date d=new Date();
			Komentar k=new Komentar(id, komentar, KorisnikDAO.dateToStringForWrite(d), logovani, v, false, 0, 0);
			KomentarDao.addKomentar(k);
			
			Map<String, Object> data = new HashMap<>();
			data.put("komentarii", k);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
		}else if(status.equals("brisanje")) {
			int id =Integer.parseInt(request.getParameter("id"));
			Komentar k = KomentarDao.getComment(id);
			k.setObrisan(true);
			KomentarDao.updateKomentar(k);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", "success");
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
		}else if(status.equals("izmena")) {
			String noviDatum = "";
			int id = Integer.parseInt(request.getParameter("id"));
			Komentar komentar = KomentarDao.getComment(id);
			String newText = request.getParameter("textUpdate");
			if(newText != null)
			{
				komentar.setSadrzaj(newText);
				Date d = new Date();
				noviDatum = KorisnikDAO.dateToString(d);
				komentar.setDatumKreiranja(noviDatum);
				KomentarDao.updateKomentar(komentar);
			}
			
			
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", "success");
			data.put("noviDatum", "noviDatum");
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}
		
	}

}
