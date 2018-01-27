package lovideo.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import lovideo.dao.KorisnikDAO;
import lovideo.model.Korisnik;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String korisnickoIme = request.getParameter("korisnickoIme");
		String lozinka = request.getParameter("lozinka");
		
		String message = "Uspesna prijava";
		String status = "success";
		
		try {
			Korisnik korisnik = KorisnikDAO.get(korisnickoIme);
			if (korisnik == null) throw new Exception("Neispravno korisnicko ime i/ili lozinka!");
			if (!korisnik.getLozinka().equals(lozinka)) throw new Exception("Neispravno korisnicko ime i/ili lozinka!");

			HttpSession session = request.getSession();
			session.setAttribute("logovaniKorisnik", korisnik);
		} catch (Exception ex) {
			message = ex.getMessage();
			status = "failure";
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
