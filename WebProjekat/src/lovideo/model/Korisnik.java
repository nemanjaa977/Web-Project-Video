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
	private String datumRegistracije;
	private Uloga uloga;
	private boolean blokiran;
	private ArrayList<Korisnik> pratioci;
	private ArrayList<LikeDislike> likeVideo;
	private ArrayList<LikeDislike> likeKomentar;
	private boolean obrisan;
	public int brojPratioca;
	
	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime, String email, String opis,
			String datumRegistracije, Uloga uloga, boolean blokiran, ArrayList<Korisnik> pratioci,
			ArrayList<LikeDislike> likeVideo, ArrayList<LikeDislike> likeKomentar, boolean obrisan, int brojPratioca) {
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
		this.likeVideo = likeVideo;
		this.likeKomentar = likeKomentar;
		this.obrisan = obrisan;
		this.brojPratioca = brojPratioca;
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

	public String getDatumRegistracije() {
		return datumRegistracije;
	}

	public void setDatumRegistracije(String datumRegistracije) {
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

	public ArrayList<LikeDislike> getLikeVideo() {
		return likeVideo;
	}

	public void setLikeVideo(ArrayList<LikeDislike> likeVideo) {
		this.likeVideo = likeVideo;
	}

	public ArrayList<LikeDislike> getLikeKomentar() {
		return likeKomentar;
	}

	public void setLikeKomentar(ArrayList<LikeDislike> likeKomentar) {
		this.likeKomentar = likeKomentar;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	public int getBrojPratioca() {
		return brojPratioca;
	}

	public void setBrojPratioca(int brojPratioca) {
		this.brojPratioca = brojPratioca;
	}
		
}
