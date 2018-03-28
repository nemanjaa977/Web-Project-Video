package lovideo.model;

public class LikeDislike {
	
	private int id;
	private boolean lajkovan;
	private String datumKreiranja;
	private Video videoLajk;
	private Komentar komentarLajk;
	private Korisnik vlasnik;
	
	public LikeDislike(int id, boolean lajkovan, String datumKreiranja, Video videoLajk, Komentar komentarLajk,
			Korisnik vlasnik) {
		super();
		this.id = id;
		this.lajkovan = lajkovan;
		this.datumKreiranja = datumKreiranja;
		this.videoLajk = videoLajk;
		this.komentarLajk = komentarLajk;
		this.vlasnik = vlasnik;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLajkovan() {
		return lajkovan;
	}

	public void setLajkovan(boolean lajkovan) {
		this.lajkovan = lajkovan;
	}

	public String getDatumKreiranja() {
		return datumKreiranja;
	}

	public void setDatumKreiranja(String datumKreiranja) {
		this.datumKreiranja = datumKreiranja;
	}

	public Video getVideoLajk() {
		return videoLajk;
	}

	public void setVideoLajk(Video videoLajk) {
		this.videoLajk = videoLajk;
	}

	public Komentar getKomentarLajk() {
		return komentarLajk;
	}

	public void setKomentarLajk(Komentar komentarLajk) {
		this.komentarLajk = komentarLajk;
	}

	public Korisnik getVlasnik() {
		return vlasnik;
	}

	public void setVlasnik(Korisnik vlasnik) {
		this.vlasnik = vlasnik;
	}
	
	
}
