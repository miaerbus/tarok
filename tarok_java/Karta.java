public class Karta {
	public Barva barva;
	public int vrednost;
	
	public Karta() { barva=Barva.NEZNANA; vrednost=-1; }
	
	public Karta(String s) {
		barva = Barva.fromString(s.charAt(0));
		vrednost = Integer.parseInt(s.substring(1));
	}
	
	public Karta(Barva b, int v) { barva=b; vrednost=v; }
	
	public int stTock() {
        if (barva != Barva.TAROK)
        {
            switch (vrednost)
            {
                case 11: return 4;
                case 12: return 7;
                case 13: return 10;
                case 14: return 13;
                default: return 1;
            }
        }
        else
        {
            if ((vrednost == 1) || (vrednost == 21) || (vrednost == 22)) return 13;
            else return 1;
        }
	}
	
	public String toString() {
		return barva.toString() + Integer.toString(vrednost);
	}
	
	public boolean equals(Object b) {
		if (!(b instanceof Karta)) return false;
		else return barva == ((Karta)b).barva && vrednost == ((Karta)b).vrednost;
	}
	
	public int hashCode() {
		return 23*(int)(barva.getbid()) + vrednost;
	}
}
