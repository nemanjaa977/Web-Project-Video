package lovideo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Korisnik {
	
	public enum Uloga {KORISNIK, ADMINISTRATOR};
	
	private String korisnickoIme;
	private String lozinka;
	private String ime;
	private String prezime;
	private String email;
	private String opis;
	private Date datumRegistracije;
	private Uloga uloga;
	private boolean blokiran;
	private ArrayList<Korisnik> pratioci;
	private Map<Video, Boolean> videoLikeDislake;
	private Map<Komentar, Boolean> komentarLikeDislake;
	
	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime, String email, String opis,
			Date datumRegistracije, Uloga uloga, boolean blokiran, ArrayList<Korisnik> pratioci,
			Map<Video, Boolean> videoLikeDislake, Map<Komentar, Boolean> komentarLikeDislake) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.opis = opis;
		this.datumRegistracije = datumRegistracije;
		this.uloga = uloga;
		this.blokiran = blokiran;
		this.pratioci = pratioci;
		this.videoLikeDislake = videoLikeDislake;
		this.komentarLikeDislake = komentarLikeDislake;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Date getDatumRegistracije() {
		return datumRegistracije;
	}

	public void setDatumRegistracije(Date datumRegistracije) {
		this.datumRegistracije = datumRegistracije;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public boolean isBlokiran() {
		return blokiran;
	}

	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}

	public ArrayList<Korisnik> getPratioci() {
		return pratioci;
	}

	public void setPratioci(ArrayList<Korisnik> pratioci) {
		this.pratioci = pratioci;
	}

	public Map<Video, Boolean> getVideoLikeDislake() {
		return videoLikeDislake;
	}

	public void setVideoLikeDislake(Map<Video, Boolean> videoLikeDislake) {
		this.videoLikeDislake = videoLikeDislake;
	}

	public Map<Komentar, Boolean> getKomentarLikeDislake() {
		return komentarLikeDislake;
	}

	public void setKomentarLikeDislake(Map<Komentar, Boolean> komentarLikeDislake) {
		this.komentarLikeDislake = komentarLikeDislake;
	}
	
	
	
}
