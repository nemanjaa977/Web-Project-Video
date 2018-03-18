package lovideo.model;

import java.util.Date;

public class Video {
	public enum Vidljivost {
		PUBLIC, PRIVATE, UNLISTED
	}
	
	private int id;
	private String videoURL;
	private String slicica;
	private String nazivVideo;
	private String opis;
	private Vidljivost vidljivost;
	private boolean dozvoljeniKomentari;
	private boolean rejtingVidljivost;
	private boolean blokiran;
	private int brojPregleda;
	private int brojLike;
	private int brojDislike;
	private String datumKreiranja;
	private Korisnik vlasnik;
	private boolean obrisan;
	
	public Video(int id, String videoURL, String slicica, String nazivVideo, String opis, Vidljivost vidljivost,
			boolean dozvoljeniKomentari, boolean rejtingVidljivost, boolean blokiran, int brojPregleda, int brojLike,
			int brojDislike, String datumKreiranja, Korisnik vlasnik, boolean obrisan) {
		super();
		this.id = id;
		this.videoURL = videoURL;
		this.slicica = slicica;
		this.nazivVideo = nazivVideo;
		this.opis = opis;
		this.vidljivost = vidljivost;
		this.dozvoljeniKomentari = dozvoljeniKomentari;
		this.rejtingVidljivost = rejtingVidljivost;
		this.blokiran = blokiran;
		this.brojPregleda = brojPregleda;
		this.brojLike = brojLike;
		this.brojDislike = brojDislike;
		this.datumKreiranja = datumKreiranja;
		this.vlasnik = vlasnik;
		this.obrisan = obrisan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public String getSlicica() {
		return slicica;
	}

	public void setSlicica(String slicica) {
		this.slicica = slicica;
	}

	public String getNazivVideo() {
		return nazivVideo;
	}

	public void setNazivVideo(String nazivVideo) {
		this.nazivVideo = nazivVideo;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Vidljivost getVidljivost() {
		return vidljivost;
	}

	public void setVidljivost(Vidljivost vidljivost) {
		this.vidljivost = vidljivost;
	}

	public boolean isDozvoljeniKomentari() {
		return dozvoljeniKomentari;
	}

	public void setDozvoljeniKomentari(boolean dozvoljeniKomentari) {
		this.dozvoljeniKomentari = dozvoljeniKomentari;
	}

	public boolean isRejtingVidljivost() {
		return rejtingVidljivost;
	}

	public void setRejtingVidljivost(boolean rejtingVidljivost) {
		this.rejtingVidljivost = rejtingVidljivost;
	}

	public boolean isBlokiran() {
		return blokiran;
	}

	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}

	public int getBrojPregleda() {
		return brojPregleda;
	}

	public void setBrojPregleda(int brojPregleda) {
		this.brojPregleda = brojPregleda;
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

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", videoURL=" + videoURL + ", slicica=" + slicica + ", nazivVideo=" + nazivVideo
				+ ", opis=" + opis + ", vidljivost=" + vidljivost + ", dozvoljeniKomentari=" + dozvoljeniKomentari
				+ ", rejtingVidljivost=" + rejtingVidljivost + ", blokiran=" + blokiran + ", brojPregleda="
				+ brojPregleda + ", brojLike=" + brojLike + ", brojDislike=" + brojDislike + ", datumKreiranja="
				+ datumKreiranja + ", vlasnik=" + vlasnik + ", obrisan=" + obrisan + "]";
	}
	
	
	
	
	

}
