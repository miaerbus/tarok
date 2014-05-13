import java.util.Vector;

public class Igralec {
    public static String Ime = "JavaIgralec";
    Vector<Karta> karte;

    public Igralec() {
    }

    public TipIgre zacniIgro(Vector<Karta> karteZaVRoko) {
		return new TipIgre(3, false, Barva.KARO);
    
    }
    
    public void zalozi(Vector<Karta> karte, Vector<Vector<Karta>> talon, Vector<Karta> vzete, Vector<Karta> zalozene) {
    	vzete.addAll(talon.get(1));
    	for (int i=0;i<karte.size();i++) {
    		if (karte.get(i).vrednost != 14 || karte.get(i).barva == Barva.TAROK) zalozene.add(karte.get(i));
    		if (zalozene.size() == 3) break;
    	}
    }

    public void zacniRedniDel(int idxIgralca, int glavniIgralec, TipIgre tipIgre, Vector<Karta> ostanekTalona, Vector<Karta> pobraneKarteTalona) {
    	
    }
    
    public Karta vrziKarto(Vector<Karta> mojeKarte, Vector<Karta> karteNaMizi, int prviIgralec) {
		Vector<Karta> veljavne = Uporabno.veljavnePoteze(mojeKarte, karteNaMizi);
		return veljavne.get(0);
    }
    
    public void konecKroga(boolean zmagal, int prviIgralecVKrogu, int zmagovalec, Vector<Karta> karteNaMizi) {
    }
    
    public void KonecIgre(String razlog) {
        System.out.println("Konec igre, razlog = " + razlog);
    }
}
