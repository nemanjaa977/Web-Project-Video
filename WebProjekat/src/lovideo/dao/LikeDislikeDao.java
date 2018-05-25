package lovideo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import lovideo.model.LikeDislike;

public class LikeDislikeDao {
	
	public static int getLikeId() {
		Connection conn = ConnectionManager.getConnection();
		int id=0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT MAX(id) FROM likeDislike";
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
	
	public static LikeDislike getVideoLajkOdStraneKorisnika(int videoId, String vlasnik) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM likeDislikeVideo JOIN likeDislike on likeDislikeVideo.likeId = likeDislike.id WHERE likeDislikeVideo.videoId = ? AND likeDislike.vlasnik = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, videoId);
			pstmt.setString(2, vlasnik);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 2;
				int videooId = rset.getInt(index++);
				int likeId = rset.getInt(index++);
				boolean isLike = rset.getBoolean(index++);
				Date d = rset.getDate(index++);
				String vlasnikk = rset.getString(index++);
				String date = KorisnikDAO.dateToString(d);
				return  new LikeDislike(likeId, isLike, date, VideoDAO.get(videooId), null, KorisnikDAO.get(vlasnikk));
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
	
	public static int getVideoBrojLajka(int videoId) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM likeDislikeVideo JOIN likeDislike on likeDislikeVideo.likeId = likeDislike.id WHERE lajkovan= ? AND likeDislikeVideo.videoId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, videoId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
					int likeNumber =rset.getInt(1);					
					return likeNumber;
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
	
	public static int getVideoBrojDislajka(int videoId) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM likeDislikeVideo JOIN likeDislike on likeDislikeVideo.likeId = likeDislike.id WHERE lajkovan= ? AND likeDislikeVideo.videoId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			pstmt.setInt(2, videoId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
					int likeNumber =rset.getInt(1);				
					return likeNumber;
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
	
	public static boolean addLikeDislike(LikeDislike likeDislike) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO likeDislike(lajkovan,datumKreiranja,vlasnik) VALUES(?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, likeDislike.isLajkovan());
			Date myDate = KorisnikDAO.stringToDateForWrite(likeDislike.getDatumKreiranja());
			java.sql.Date date = new java.sql.Date(myDate.getTime());
			pstmt.setDate(2, date);
			pstmt.setString(3, likeDislike.getVlasnik().getKorisnickoIme());
			
			return pstmt.executeUpdate() == 1;

		}  catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean addVideoLikeDislike(int likeId, int videoId) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			
			String query="INSERT INTO likeDislikeVideo(likeId,videoId) VALUES(?, ?)";
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, likeId);
			pstmt.setInt(2, videoId);
			return pstmt.executeUpdate() == 1;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean updateLike(LikeDislike li) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE likeDislike SET lajkovan = ? WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, li.isLajkovan());
			pstmt.setInt(2, li.getId());	
			return pstmt.executeUpdate() == 1;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static LikeDislike getCommentLikeByOwner(int commentId,String vlasnik) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM likeDislikeComment JOIN likeDislike on likeDislikeComment.likeId = likeDislike.id WHERE likeDislikeComment.commentId = ? AND likeDislike.vlasnik = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, commentId);
			pstmt.setString(2, vlasnik);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 2;
				int videosId = rset.getInt(index++);
				int likeId = rset.getInt(index++);
				boolean isLike = rset.getBoolean(index++);
				Date d = rset.getDate(index++);
				String owner = rset.getString(index++);
				String date = KorisnikDAO.dateToString(d);
				return new LikeDislike(likeId, isLike, date, VideoDAO.get(videosId), null, KorisnikDAO.get(owner));
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
	
	public static int getCommentLikeNumber(int commentId) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM likeDislikeComment JOIN likeDislike on likeDislikeComment.likeId = likeDislike.id WHERE lajkovan = ? AND likeDislikeComment.commentId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, commentId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
					int likeNumber =rset.getInt(1);					
					return likeNumber;
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
	
	public static boolean addCommentLikeDislike(int likeId, int commentId) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query="INSERT INTO likeDislikeComment(likeId,commentId) VALUES(?, ?)";
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, likeId);
			pstmt.setInt(2, commentId);
			 return pstmt.executeUpdate() == 1;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static int getCommentDislikeNumber(int commentId) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM likeDislikeComment JOIN likeDislike on likeDislikeComment.likeId = likeDislike.id WHERE lajkovan = ? AND likeDislikeComment.commentId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			pstmt.setInt(2, commentId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
					int likeNumber =rset.getInt(1);			
					return likeNumber;
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
}
