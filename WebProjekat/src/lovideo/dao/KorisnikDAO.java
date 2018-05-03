package lovideo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lovideo.model.Korisnik;
import lovideo.model.Korisnik.Uloga;

public class KorisnikDAO {
	public static Korisnik get(String korisnickoIme) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE korisnickoIme = ? AND obrisan = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, korisnickoIme);
			pstmt.setBoolean(2, false);
			
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 2;
				String slika = rset.getString(index++);
				String lozinka = rset.getString(index++);
				String ime = rset.getString(index++);
				String prezime = rset.getString(index++);
				String email = rset.getString(index++);
				String opis = rset.getString(index++);
				Date datum = rset.getDate(index++);
				String datumRegistracije = dateToString(datum);
				Uloga uloga = Uloga.valueOf(rset.getString(index++));
				boolean blokiran = rset.getBoolean(index++);
				boolean obrisan = rset.getBoolean(index++);
				int brojPratioca = rset.getInt(index++);
				return new Korisnik(korisnickoIme, slika, lozinka, ime, prezime, email, opis, datumRegistracije, uloga, blokiran, null, null, null, obrisan, brojPratioca);
				
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
	
	public static ArrayList<Korisnik> getAll() {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
		try {
			String query = "SELECT * FROM users WHERE obrisan = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			
			rset = pstmt.executeQuery();

			 while(rset.next()) {
				int index = 1;
				String korisnickoIme = rset.getString(index++);
				String slika = rset.getString(index++);
				String lozinka = rset.getString(index++);
				String ime = rset.getString(index++);
				String prezime = rset.getString(index++);
				String email = rset.getString(index++);
				String opis = rset.getString(index++);
				Date datum = rset.getDate(index++);
				String datumRegistracije = dateToString(datum);
				Uloga uloga = Uloga.valueOf(rset.getString(index++));
				boolean blokiran = rset.getBoolean(index++);
				boolean obrisan = rset.getBoolean(index++);
				int brojPratioca = rset.getInt(index++);
				korisnici.add(new Korisnik(korisnickoIme, slika, lozinka, ime, prezime, email, opis, datumRegistracije, uloga, blokiran, null, null, null, obrisan, brojPratioca));
				
			}
			 return korisnici;
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
			String query = "INSERT INTO users (korisnickoIme, slicica, lozinka, ime, prezime, email, opis, datumRegistracije, uloga, blokiran, obrisan, brojPratioca)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, korisnik.getKorisnickoIme());
			pstmt.setString(index++, korisnik.getSlicica());
			pstmt.setString(index++, korisnik.getLozinka());
			pstmt.setString(index++, korisnik.getIme());
			pstmt.setString(index++, korisnik.getPrezime());
			pstmt.setString(index++, korisnik.getEmail());
			pstmt.setString(index++, korisnik.getOpis());
			Date myDate=stringToDateForWrite(korisnik.getDatumRegistracije());
			java.sql.Date date=new java.sql.Date(myDate.getTime());
			pstmt.setDate(index++, date);
			pstmt.setString(index++, korisnik.getUloga().toString());
			pstmt.setBoolean(index++, korisnik.isBlokiran());
			pstmt.setBoolean(index++, korisnik.isObrisan());
			pstmt.setInt(index++, korisnik.getBrojPratioca());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}
	
	public static boolean update(Korisnik korisnik) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE users SET lozinka = ?, ime = ?, prezime = ?, opis = ?, uloga = ?, blokiran = ?, obrisan = ?, brojPratioca = ? WHERE korisnickoIme = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, korisnik.getLozinka());
			pstmt.setString(index++, korisnik.getIme());
			pstmt.setString(index++, korisnik.getPrezime());
			pstmt.setString(index++, korisnik.getOpis());
			pstmt.setString(index++, korisnik.getUloga().toString());
			pstmt.setBoolean(index++, korisnik.isBlokiran());
			pstmt.setBoolean(index++, korisnik.isObrisan());
			pstmt.setInt(index++, korisnik.getBrojPratioca());
			pstmt.setString(index++, korisnik.getKorisnickoIme());
			
			return pstmt.executeUpdate() == 1;
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}
	
	public static String dateToString(Date date) {
		SimpleDateFormat formatvr = new SimpleDateFormat("dd.MM.yyyy");
		String datum;
		datum = formatvr.format(date);
		return datum;
	}

	public static Date stringToDate(String datum) {

		try {
			DateFormat formatvr = new SimpleDateFormat("dd.MM.yyyy");

			return formatvr.parse(datum);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	public static String dateToStringForWrite(Date date) {
		SimpleDateFormat formatvr = new SimpleDateFormat("yyyy-MM-dd");
		String datum;
		datum = formatvr.format(date);
		return datum;
	}
	
	public static Date stringToDateForWrite(String datum) {

		try {
			DateFormat formatvr = new SimpleDateFormat("yyyy-MM-dd");

			return formatvr.parse(datum);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	public static boolean addSubs(String masterUser, String subs) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO subscribe(korisnik,subskrajber) VALUES(?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, masterUser);
			pstmt.setString(2, subs);
			return pstmt.executeUpdate() == 1;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return false;
	}

	public static boolean deleteSubs(String masterUser, String subs) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM subscribe WHERE korisnik = ? AND subskrajber = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, masterUser);
			pstmt.setString(2, subs);
			return pstmt.executeUpdate() == 1;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return false;
	}
	
	public static int findSubscribed(String userName, String subscriber) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM subscribe WHERE  korisnik = ? AND subskrajber = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setString(2, subscriber);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				int subs = rset.getInt(index);
				return subs;

			}

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return 0;
	}
	
	public static int getSubscribeNumber(String userName) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT Count(*) FROM subscribe WHERE korisnik = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				int subs = rset.getInt(index);
				return subs;
			}
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return 0;
	}
	
	public static ArrayList<Korisnik> subscribedOn(String userName) {
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Korisnik> subscribed = new ArrayList<Korisnik>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT korisnik FROM subscribe WHERE subskrajber = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				int index = 1;
				String glavni = rset.getString(index);

				Korisnik k = get(glavni);
				subscribed.add(k);
			}

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return subscribed;
	}
}
