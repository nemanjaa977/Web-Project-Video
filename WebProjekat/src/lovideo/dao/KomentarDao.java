package lovideo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import lovideo.model.Komentar;
import lovideo.model.Korisnik;
import lovideo.model.Video;

public class KomentarDao {
	
	public static int getCommentId() {
		Connection conn = ConnectionManager.getConnection();
		int id=0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT MAX(id) FROM comment";
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
		
			if (rset.next()) {
				id=rset.getInt(1);
				
			}
			id++;
			return id;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return 0;
	}
	
	public static Komentar getComment(int id) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM comment WHERE id = ? AND obrisan = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 2;
				String sadrzaj = rset.getString(index++);
				Date d= rset.getDate(index++);
				String vlasnik = rset.getString(index++);
				int videoId = rset.getInt(index++);
				int brojLike = rset.getInt(index++);
				int brojDislike = rset.getInt(index++);
				boolean obrisan = rset.getBoolean(index++);
				Korisnik k = KorisnikDAO.get(vlasnik);
				Video video = VideoDAO.get(videoId);
				String datumKreiranja = KorisnikDAO.dateToString(d);
				return new Komentar(id, sadrzaj, datumKreiranja, k, video, obrisan, brojLike, brojDislike);
			}

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static ArrayList<Komentar> getComments(int videosId) {
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Komentar> comments = new ArrayList<Komentar>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM comment WHERE video = ? AND obrisan = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, videosId);
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String sadrzaj = rset.getString(index++);
				Date d= rset.getDate(index++);
				String vlasnik = rset.getString(index++);
				int videoId = rset.getInt(index++);
				int brojLike = rset.getInt(index++);
				int brojDislike = rset.getInt(index++);
				boolean obrisan = rset.getBoolean(index++);
				Korisnik k = KorisnikDAO.get(vlasnik);
				Video video = VideoDAO.get(videoId);
				String datumKreiranja = KorisnikDAO.dateToString(d);
				if(k == null || video == null) {
					continue;
				}
				else {
					Komentar kom = new Komentar(id, sadrzaj, datumKreiranja, k, video, obrisan, brojLike, brojDislike);
					comments.add(kom);
				}
				
			}

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return comments;
	}
	
	public static boolean addKomentar(Komentar komentar) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO comment (sadrzaj, datumKreiranja, vlasnik, video, brojLike, brojDislike, obrisan) VALUES (?, ?, ?, ?, ?, ?, ? )";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, komentar.getSadrzaj());
			Date myDate = KorisnikDAO.stringToDateForWrite(komentar.getDatumKreiranja());
			java.sql.Date date = new java.sql.Date(myDate.getTime());
			pstmt.setDate(index++, date);
			pstmt.setString(index++, komentar.getVlasnik().getKorisnickoIme());
			pstmt.setInt(index++, komentar.getVideo().getId());
			pstmt.setInt(index++, komentar.getBrojLike());
			pstmt.setInt(index++, komentar.getBrojDislike());
			pstmt.setBoolean(index++, komentar.isObrisan());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}

	public static boolean updateKomentar(Komentar komentar) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE comment SET brojLike =?, brojDislike = ?, obrisan = ?, datumKreiranja = ?, sadrzaj = ? WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, komentar.getBrojLike());
			pstmt.setInt(2, komentar.getBrojDislike());
			pstmt.setBoolean(3, komentar.isObrisan());
			Date d1 = KorisnikDAO.stringToDate(komentar.getDatumKreiranja());
			String dd = KorisnikDAO.dateToStringForWrite(d1);
			Date d = KorisnikDAO.stringToDateForWrite(dd);
			java.sql.Date date=new java.sql.Date(d.getTime());
			pstmt.setDate(4, date);
			pstmt.setString(5, komentar.getSadrzaj());
			pstmt.setInt(6, komentar.getId());
		
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}
	
}
