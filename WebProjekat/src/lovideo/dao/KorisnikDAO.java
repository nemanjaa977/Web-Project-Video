package lovideo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import lovideo.model.Korisnik;
import lovideo.model.Korisnik.Uloga;

public class KorisnikDAO {
	public static Korisnik get(String korisnickoIme) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE korisnickoIme = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, korisnickoIme);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 2;
				String lozinka = rset.getString(index++);
				String ime = rset.getString(index++);
				String prezime = rset.getString(index++);
				String email = rset.getString(index++);
				String opis = rset.getString(index++);
				Date datumRegistracije = rset.getDate(index++);
				Uloga uloga = Uloga.valueOf(rset.getString(index++));
				boolean blokiran = rset.getBoolean(index++);
				
				return new Korisnik(korisnickoIme, lozinka, ime, prezime, email, opis, datumRegistracije, uloga, blokiran, null, null, null);
			}
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return null;
	}
	
	public static boolean add(Korisnik korisnik) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO users (korisnickoIme, lozinka, ime, prezime, email, opis, datumRegistracije, uloga, blokiran) VALUES (?, ?, ?, ? ,? ,? , ?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, korisnik.getKorisnickoIme());
			pstmt.setString(index++, korisnik.getLozinka());
			pstmt.setString(index++, korisnik.getIme());
			pstmt.setString(index++, korisnik.getPrezime());
			pstmt.setString(index++, korisnik.getEmail());
			pstmt.setString(index++, korisnik.getOpis());
			Timestamp date = new Timestamp(new Date().getTime());
			pstmt.setTimestamp(index++, date);
			pstmt.setString(index++, korisnik.getUloga().toString());
			pstmt.setBoolean(index++, korisnik.isBlokiran());
			return pstmt.executeUpdate(query) == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}
}
