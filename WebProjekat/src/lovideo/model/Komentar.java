package lovideo.model;

public class Komentar {
	
	private int id;
	private String sadrzaj;
	private String datumKreiranja;
	private Korisnik vlasnik;
	private Video video;
	private boolean obrisan;	
	private int brojLike;
	private int brojDislike;
	
	public Komentar(int id, String sadrzaj, String datumKreiranja, Korisnik vlasnik, Video video, boolean obrisan,
			int brojLike, int brojDislike) {
		super();
		this.id = id;
		this.sadrzaj = sadrzaj;
		this.datumKreiranja = datumKreiranja;
		this.vlasnik = vlasnik;
		this.video = video;
		this.obrisan = obrisan;
		this.brojLike = brojLike;
		this.brojDislike = brojDislike;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public String getDatumKreiranja() {
		return datumKreiranja;
	}

	public void setDatumKreiranja(String datumKreiranja) {
		this.datumKreiranja = datumKreiranja;
	}

	public Korisnik getVlasnik() {
		return vlasnik;
	}

	public void setVlasnik(Korisnik vlasnik) {
		this.vlasnik = vlasnik;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	public int getBrojLike() {
		return brojLike;
	}

	public void setBrojLike(int brojLike) {
		this.brojLike = brojLike;
	}

	public int getBrojDislike() {
		return brojDislike;
	}

	public void setBrojDislike(int brojDislike) {
		this.brojDislike = brojDislike;
	}
	
	

}
