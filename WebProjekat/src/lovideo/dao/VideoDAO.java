package lovideo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import lovideo.model.Korisnik;
import lovideo.model.Video;
import lovideo.model.Video.Vidljivost;

public class VideoDAO {
	
	public static Video get(int id) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM videos WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 2;
				String videoURL = rset.getString(index++);
				String slicica = rset.getString(index++);
				String nazivVideo = rset.getString(index++);
				String opis = rset.getString(index++);
				Vidljivost viidljivost = Vidljivost.valueOf(rset.getString(index++));
				boolean dozvoljeniKomentari = rset.getBoolean(index++);
				int brojLike = rset.getInt(index++);
				int brojDislike = rset.getInt(index++);
				boolean blokiran = rset.getBoolean(index++);
				boolean rejtingVidljivost = rset.getBoolean(index++);
				int brojPregleda = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datumKreiranja=KorisnikDAO.dateToString(d);
				boolean obrisan = rset.getBoolean(index++);
				String vlasnik = rset.getString(index++);
				Korisnik k = KorisnikDAO.get(vlasnik);
				
				return new Video(id, videoURL, slicica, nazivVideo, opis, viidljivost, dozvoljeniKomentari, rejtingVidljivost, blokiran,
						brojPregleda, brojLike, brojDislike, datumKreiranja, k, obrisan);
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
	
	public static ArrayList<Video> getAllPublic() {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<Video> videos=new ArrayList<Video>();
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM videos WHERE vidljivost = ? AND blokiran = ? AND obrisan = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "PUBLIC");
			pstmt.setBoolean(2, false);
			pstmt.setBoolean(3, false);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id=rset.getInt(index++);
				String videoURL = rset.getString(index++);
				String slicica = rset.getString(index++);
				String nazivVideo = rset.getString(index++);
				String opis = rset.getString(index++);
				Vidljivost viidljivost = Vidljivost.valueOf(rset.getString(index++));
				boolean dozvoljeniKomentari = rset.getBoolean(index++);
				int brojLike = rset.getInt(index++);
				int brojDislike = rset.getInt(index++);
				boolean blokiran = rset.getBoolean(index++);
				boolean rejtingVidljivost = rset.getBoolean(index++);
				int brojPregleda = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datumKreiranja=KorisnikDAO.dateToString(d);
				boolean obrisan = rset.getBoolean(index++);
				String vlasnik = rset.getString(index++);
				Korisnik k = KorisnikDAO.get(vlasnik);
				
				videos.add( new Video(id, videoURL, slicica, nazivVideo, opis, viidljivost, dozvoljeniKomentari, rejtingVidljivost, blokiran,
						brojPregleda, brojLike, brojDislike, datumKreiranja, k, obrisan));
			}

			return videos;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static ArrayList<Video> getAllPublicByUser(String username) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<Video> videos=new ArrayList<Video>();
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM videos WHERE vidljivost = ? AND blokiran = ? AND obrisan = ? AND vlasnik = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "PUBLIC");
			pstmt.setBoolean(2, false);
			pstmt.setBoolean(3, false);
			pstmt.setString(4, username);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id=rset.getInt(index++);
				String videoURL = rset.getString(index++);
				String slicica = rset.getString(index++);
				String nazivVideo = rset.getString(index++);
				String opis = rset.getString(index++);
				Vidljivost viidljivost = Vidljivost.valueOf(rset.getString(index++));
				boolean dozvoljeniKomentari = rset.getBoolean(index++);
				int brojLike = rset.getInt(index++);
				int brojDislike = rset.getInt(index++);
				boolean blokiran = rset.getBoolean(index++);
				boolean rejtingVidljivost = rset.getBoolean(index++);
				int brojPregleda = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datumKreiranja=KorisnikDAO.dateToString(d);
				boolean obrisan = rset.getBoolean(index++);
				String vlasnik = rset.getString(index++);
				Korisnik k = KorisnikDAO.get(vlasnik);
				
				videos.add( new Video(id, videoURL, slicica, nazivVideo, opis, viidljivost, dozvoljeniKomentari, rejtingVidljivost, blokiran,
						brojPregleda, brojLike, brojDislike, datumKreiranja, k, obrisan));
			}

			return videos;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static ArrayList<Video> getAll() {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<Video> videos=new ArrayList<Video>();
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM videos WHERE vidljivost != ? AND blokiran = ? AND obrisan = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "UNLISTED");
			pstmt.setBoolean(2, false);
			pstmt.setBoolean(3, false);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id=rset.getInt(index++);
				String videoURL = rset.getString(index++);
				String slicica = rset.getString(index++);
				String nazivVideo = rset.getString(index++);
				String opis = rset.getString(index++);
				Vidljivost viidljivost = Vidljivost.valueOf(rset.getString(index++));
				boolean dozvoljeniKomentari = rset.getBoolean(index++);
				int brojLike = rset.getInt(index++);
				int brojDislike = rset.getInt(index++);
				boolean blokiran = rset.getBoolean(index++);
				boolean rejtingVidljivost = rset.getBoolean(index++);
				int brojPregleda = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datumKreiranja=KorisnikDAO.dateToString(d);
				boolean obrisan = rset.getBoolean(index++);
				String vlasnik = rset.getString(index++);
				Korisnik k = KorisnikDAO.get(vlasnik);
				
				videos.add( new Video(id, videoURL, slicica, nazivVideo, opis, viidljivost, dozvoljeniKomentari, rejtingVidljivost, blokiran,
						brojPregleda, brojLike, brojDislike, datumKreiranja, k, obrisan));
			}

			return videos;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static ArrayList<Video> getAllByUser(String username) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<Video> videos=new ArrayList<Video>();
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM videos WHERE vidljivost != ? AND blokiran = ? AND obrisan = ? AND vlasnik = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "UNLISTED");
			pstmt.setBoolean(2, false);
			pstmt.setBoolean(3, false);
			pstmt.setString(4, username);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id=rset.getInt(index++);
				String videoURL = rset.getString(index++);
				String slicica = rset.getString(index++);
				String nazivVideo = rset.getString(index++);
				String opis = rset.getString(index++);
				Vidljivost viidljivost = Vidljivost.valueOf(rset.getString(index++));
				boolean dozvoljeniKomentari = rset.getBoolean(index++);
				int brojLike = rset.getInt(index++);
				int brojDislike = rset.getInt(index++);
				boolean blokiran = rset.getBoolean(index++);
				boolean rejtingVidljivost = rset.getBoolean(index++);
				int brojPregleda = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datumKreiranja=KorisnikDAO.dateToString(d);
				boolean obrisan = rset.getBoolean(index++);
				String vlasnik = rset.getString(index++);
				Korisnik k = KorisnikDAO.get(vlasnik);
				
				videos.add( new Video(id, videoURL, slicica, nazivVideo, opis, viidljivost, dozvoljeniKomentari, rejtingVidljivost, blokiran,
						brojPregleda, brojLike, brojDislike, datumKreiranja, k, obrisan));
			}

			return videos;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static boolean addVideo(Video video) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO videos (videoURL, slicica, nazivVideo, opis, vidljivost, dozvoljeniKomentari, brojLike, brojDislike, blokiran, rejtingVidljivost, brojPregleda, datumKreiranja, obrisan, vlasnik)"
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, video.getVideoURL());
			pstmt.setString(index++, video.getSlicica());
			pstmt.setString(index++, video.getNazivVideo());
			pstmt.setString(index++, video.getOpis());
			pstmt.setString(index++, video.getVidljivost().toString());
			pstmt.setBoolean(index++, video.isDozvoljeniKomentari());
			pstmt.setInt(index++, video.getBrojLike());
			pstmt.setInt(index++, video.getBrojDislike());
			pstmt.setBoolean(index++, video.isBlokiran());
			pstmt.setBoolean(index++, video.isRejtingVidljivost());
			pstmt.setInt(index++, video.getBrojPregleda());
			Date myDate=KorisnikDAO.stringToDateForWrite(video.getDatumKreiranja());
			java.sql.Date date=new java.sql.Date(myDate.getTime());
			pstmt.setDate(index++,date);
			pstmt.setBoolean(index++, video.isObrisan());
			pstmt.setString(index++, video.getVlasnik().getKorisnickoIme());

			return pstmt.executeUpdate() == 1;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean updateVideo(Video video) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE videos SET nazivVideo = ?, opis = ?, vidljivost = ?, dozvoljeniKomentari = ?, rejtingVidljivost = ?, blokiran = ?, obrisan = ?, brojPregleda = ?, brojLike = ?, brojDislike = ?  WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, video.getNazivVideo());
			pstmt.setString(index++, video.getOpis());
			pstmt.setString(index++, video.getVidljivost().toString());
			pstmt.setBoolean(index++, video.isDozvoljeniKomentari());
			pstmt.setBoolean(index++, video.isRejtingVidljivost());
			pstmt.setBoolean(index++, video.isBlokiran());
			pstmt.setBoolean(index++, video.isObrisan());
			pstmt.setInt(index++, video.getBrojPregleda());
			pstmt.setInt(index++, video.getBrojLike());
			pstmt.setInt(index++, video.getBrojDislike());
			pstmt.setInt(index++, video.getId());
			
			return pstmt.executeUpdate() == 1;
			
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static int getVideoId() {
		Connection conn = ConnectionManager.getConnection();
		int id=0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT MAX(id) FROM videos";
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
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return 0;
	}
}
