package lovideo.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import lovideo.dao.KorisnikDAO;
import lovideo.dao.VideoDAO;
import lovideo.model.Korisnik;
import lovideo.model.Korisnik.Uloga;


public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String korisnickoIme = request.getParameter("korisnickoIme");
		String lozinka = request.getParameter("lozinka");
		String ime = request.getParameter("ime");
		String prezime = request.getParameter("prezime");
		String email = request.getParameter("email");
		String opis = request.getParameter("opis");
		
		String status = "success";

		try {
//			if ("".equals(korisnickoIme) || "".equals(lozinka) ||  "".equals(email))
//				throw new Exception("Niste popunili potrebna polja!");

			Korisnik existingUser = KorisnikDAO.get(korisnickoIme);
			if (existingUser != null)
				throw new Exception("Korisnik vec postoji!");
			Date d=new Date();
			String date = KorisnikDAO.dateToStringForWrite(d);
			
			Korisnik noviKorisnik = new Korisnik(korisnickoIme, lozinka, ime, prezime, email, opis, date, Uloga.KORISNIK, false, null, null, null, false, 0);
			KorisnikDAO.add(noviKorisnik);
		} catch (Exception ex) {
			status = "failure";
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
	}

}
