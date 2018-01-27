package lovideo.model;

import java.util.Date;

public class Video {
	
	private String id;
	private String videoURL;
	private String slicica;
	private String opis;
	private String vidljivost;
	private boolean dozvoljeniKomentari;
	private boolean rejtingVidljivost;
	private boolean blokiran;
	private int brojPregleda;
	private Date datumKreiranja;
	private Korisnik vlasnik;
	
	public Video(String id, String videoURL, String slicica, String opis, String vidljivost,
			boolean dozvoljeniKomentari, boolean rejtingVidljivost, boolean blokiran, int brojPregleda,
			Date datumKreiranja, Korisnik vlasnik) {
		super();
		this.id = id;
		this.videoURL = videoURL;
		this.slicica = slicica;
		this.opis = opis;
		this.vidljivost = vidljivost;
		this.dozvoljeniKomentari = dozvoljeniKomentari;
		this.rejtingVidljivost = rejtingVidljivost;
		this.blokiran = blokiran;
		this.brojPregleda = brojPregleda;
		this.datumKreiranja = datumKreiranja;
		this.vlasnik = vlasnik;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getVidljivost() {
		return vidljivost;
	}

	public void setVidljivost(String vidljivost) {
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

	public Date getDatumKreiranja() {
		return datumKreiranja;
	}

	public void setDatumKreiranja(Date datumKreiranja) {
		this.datumKreiranja = datumKreiranja;
	}

	public Korisnik getVlasnik() {
		return vlasnik;
	}

	public void setVlasnik(Korisnik vlasnik) {
		this.vlasnik = vlasnik;
	}
	
	

}
