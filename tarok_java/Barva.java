public enum Barva {
	TAROK(0), 
	KARO(1),
	SRCE(2),
	KRIZ(3),
	PIK(4),
	NEZNANA(-1);
	
	private int bid;
	
	Barva(int b) { bid = b; }
	
	public int getbid() { return bid; }
	
	public String toString() {
        switch (bid)
        {
            case 0: return "T";
            case 1: return "K";
            case 2: return "S";
            case 3: return "R";
            case 4: return "P";
            case -1: return "X";
            default: return "X";
        }
	}
	
	public static Barva fromString(char c) {
        switch (c)
        {
            case 'T': return Barva.TAROK;
            case 'K': return Barva.KARO;
            case 'R': return Barva.KRIZ;
            case 'P': return Barva.PIK;
            case 'S': return Barva.SRCE;
            default: return Barva.NEZNANA;
        }
	}
}
