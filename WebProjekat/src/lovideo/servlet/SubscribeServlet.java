package lovideo.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import lovideo.dao.KorisnikDAO;
import lovideo.model.Korisnik;


public class SubscribeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String korisnik=request.getParameter("korisnik");
		String subskrajber=request.getParameter("subskrajber");
		int issubscribe=KorisnikDAO.findSubscribed(korisnik, subskrajber);
		Korisnik k=KorisnikDAO.get(korisnik);
		int brojSubova;
		String status="Subscribe";
		if(issubscribe == 0) {
			//ako nije subskrajbovan
			KorisnikDAO.addSubs(korisnik, subskrajber);
			k.setBrojPratioca(k.getBrojPratioca()+1);
			brojSubova=k.getBrojPratioca();
			KorisnikDAO.update(k);
		}
		else {
			// ako je vec subskrajbovan
			KorisnikDAO.deleteSubs(korisnik, subskrajber);
			k.setBrojPratioca(k.getBrojPratioca()-1);
			brojSubova=k.getBrojPratioca();
			KorisnikDAO.update(k);
			status="Unsubscribe";
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);
		data.put("brojSubova",brojSubova);
		
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
