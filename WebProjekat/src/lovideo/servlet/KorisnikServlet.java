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

import lovideo.dao.KorisnikDAO;
import lovideo.dao.VideoDAO;
import lovideo.model.Korisnik;
import lovideo.model.Korisnik.Uloga;
import lovideo.model.Video;


public class KorisnikServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Video> videos=null;
		Korisnik vlasnik=null;
		Korisnik loggedInUser=null;
		boolean isSubscribe=false;
		String korisnickoIme = null;
		try {	
			
			korisnickoIme=request.getParameter("korisnickoIme");
			vlasnik=KorisnikDAO.get(korisnickoIme);
			HttpSession session = request.getSession();
			
			loggedInUser= (Korisnik) session.getAttribute("logovaniKorisnik");
			
			if(loggedInUser != null) {
				if(loggedInUser.getUloga().toString().equals("ADMINISTRATOR")|| loggedInUser.getKorisnickoIme().equals(korisnickoIme)) {
					videos=VideoDAO.getAllByUser(korisnickoIme);
					
				}else {
					videos=VideoDAO.getAllPublicByUser(korisnickoIme);
				}
				if(!loggedInUser.getKorisnickoIme().equals(vlasnik.getKorisnickoIme())) {
					int subs=KorisnikDAO.findSubscribed(vlasnik.getKorisnickoIme(), loggedInUser.getKorisnickoIme());
					if(subs !=0) {
						isSubscribe = true;
					}
				}
			}
			else {
			videos=VideoDAO.getAllPublicByUser(korisnickoIme);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		ArrayList<Korisnik>subskrajberi = KorisnikDAO.subscribedOn(korisnickoIme);
		
		Map<String, Object> data = new HashMap<>();
		data.put("videos", videos);
		data.put("vlasnik", vlasnik);
		data.put("logovani", loggedInUser);
		data.put("isSubscribe", isSubscribe);
		data.put("subskrajberi", subskrajberi);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = request.getParameter("status");
		
		if(status.equals("brisanje")) {
			String korIme = request.getParameter("korisnikIme");
			Korisnik k = KorisnikDAO.get(korIme);
			k.setObrisan(true);
			KorisnikDAO.update(k);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", "success");
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
		}else if(status.equals("blokiranje")){
			String korIme = request.getParameter("korIme");
			Korisnik k = KorisnikDAO.get(korIme);
			k.setBlokiran(true);
			KorisnikDAO.update(k);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", "success");
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}else if(status.equals("odblokiranje")){
			String korIme = request.getParameter("korIme");
			Korisnik k = KorisnikDAO.get(korIme);
			k.setBlokiran(false);
			KorisnikDAO.update(k);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", "success");
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}else if(status.equals("izmena")){
			String lozinka = request.getParameter("editedPassword");
			String opis = request.getParameter("editedDescription");
			String ime = request.getParameter("editedName");
			String prezime = request.getParameter("editedSurname");
			String ulogaString = request.getParameter("role");
			String korIme = request.getParameter("korIme");
			
			Uloga uloga =Uloga.valueOf(ulogaString);
			
			Korisnik k = KorisnikDAO.get(korIme);
			k.setIme(ime);
			k.setLozinka(lozinka);
			k.setPrezime(prezime);
			k.setOpis(opis);
			k.setUloga(uloga);
			
			KorisnikDAO.update(k);
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
