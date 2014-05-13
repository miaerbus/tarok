import java.util.List;
import java.util.Vector;

public class Uporabno {
	public static String karteVSTring(Vector<Karta> karte) {
		String ret = Integer.toString(karte.size());
		for (Karta k: karte) ret += " " + k.toString();
		return ret;
	}
	
	public static Vector<Karta> stringiVKarteN(List<String> opis) {
		int N = Integer.parseInt(opis.get(0));
		opis.remove(0);
		Vector<Karta> karte = new Vector<Karta>();
		for (int i=0;i<N;i++) {
			karte.add(new Karta(opis.get(0)));
			opis.remove(0);
		}
		return karte;
	}
	
	public static int steviloTock(Vector<Karta> karte) {
		int sum = 0;
		for (Karta k : karte) sum += k.stTock();
		return sum;
	}
	
	public static Vector<Karta> veljavnePoteze(Vector<Karta> mojeKarte, Vector<Karta> karteNaMizi) {
		Vector<Karta> veljavne = new Vector<Karta>();
		
		if (karteNaMizi.size() == 0) {
			veljavne.addAll(mojeKarte);
			return veljavne;
		} else {
			Barva b = karteNaMizi.get(0).barva;
			for (Karta k: mojeKarte) {
				if (k.barva.equals(b)) veljavne.add(k);
			}
			if (veljavne.size() > 0) return veljavne;
			
			for (Karta k: mojeKarte) {
				if (k.barva.equals(Barva.TAROK)) veljavne.add(k);
			}
			if (veljavne.size() > 0) return veljavne;
			veljavne.addAll(mojeKarte);
			return veljavne;
		}
	}
}
