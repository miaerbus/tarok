public class TipIgre {
	public int stZalozenihKart;
	public boolean solo;
	public Barva klicaniKralj;
	
	public TipIgre() { stZalozenihKart = -1; solo = false; klicaniKralj = Barva.NEZNANA; }
	
	public TipIgre(String s) throws Exception {
        if (s.length()!= 3) throw new Exception(s + " ni veljaven opis tipa igre");
        solo = s.charAt(0) == 'S';
        stZalozenihKart = s.charAt(1) - '0';
        klicaniKralj = Barva.fromString(s.charAt(2));
	}
	
	public TipIgre(int st, boolean _solo, Barva _kralj) { stZalozenihKart = st; solo = _solo; klicaniKralj = _kralj; }
	
	public String toString() {
        String r = "";
        if (solo) r += "S"; else r += "N";
        r += Integer.toString(stZalozenihKart);
        r += klicaniKralj.toString();
        return r;
	}
	
	public int vrednost() {
		return solo?90:0 + 30 * (4 - stZalozenihKart);
	}
	
	public boolean equals(TipIgre b) {
		return stZalozenihKart == b.stZalozenihKart && solo == b.solo && klicaniKralj == b.klicaniKralj;
	}
}
