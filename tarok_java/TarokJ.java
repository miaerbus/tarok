import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

class Comms {
    BufferedReader reader;
    PrintWriter writer;
}

public class TarokJ {
    public static void event_loop(Comms comms) throws Exception {

        Igralec t;
        Vector<Karta> karte;
        int pozicija = -1;
        
        ArrayList<String> cmda;

        while (true) {
            cmda = new ArrayList<String>(Arrays.asList(comms.reader.readLine().split(" ")));

            if (cmda.get(0).equals("ADIJO")) return;
            if (cmda.get(0).startsWith("KONEC_IGRE")) continue;
            if (!cmda.get(0).equals("ZACNI")) throw new Exception("Napaka pri komunikaciji s streznikom.");

            cmda.remove(0);
            karte = Uporabno.stringiVKarteN(cmda); 
            t = new Igralec();
            TipIgre predlog = t.zacniIgro(karte);

            comms.writer.format("PREDLAGAM %s\n", predlog.toString());
            comms.writer.flush();

            while (true) {
                cmda = new ArrayList<String>(Arrays.asList(comms.reader.readLine().split(" ")));

                if (cmda.get(0).equals("ADIJO")) return;
                if (cmda.get(0).equals("KONEC_IGRE")) {
                	t.KonecIgre(cmda.get(1));
                    t = null;
                    karte = null;
                    pozicija = -1;
                    break;
                } else if (cmda.get(0).equals("ZALOZI")) {
                	cmda.remove(0);
                	int N_grup = Integer.parseInt(cmda.get(0)); cmda.remove(0);
                	Vector<Vector<Karta>> talon = new Vector<Vector<Karta>>();
                	for (int i=0;i<N_grup;i++) talon.add(Uporabno.stringiVKarteN(cmda));
                	Vector<Karta> vzete, zalozene;
                	vzete = new Vector<Karta>();
                	zalozene = new Vector<Karta>();
                	t.zalozi(karte, talon, vzete, zalozene);
                	
                	Set<Karta> ks = new HashSet<Karta>(karte);
                	ks.addAll(new HashSet<Karta>(vzete));
                	ks.removeAll(new HashSet<Karta>(zalozene));
                	karte = new Vector<Karta>(ks);
                	
                	comms.writer.write("LEZIM " + Uporabno.karteVSTring(vzete) + " " + Uporabno.karteVSTring(zalozene) + "\n");
                	comms.writer.flush();
                } else if (cmda.get(0).equals("IGRAJ")) {
                	cmda.remove(0);
                	pozicija = Integer.parseInt(cmda.get(0)); cmda.remove(0); 
                	int idxGlavnega = Integer.parseInt(cmda.get(0)); cmda.remove(0); 
                	TipIgre tip = new TipIgre(cmda.get(0)); cmda.remove(0);
                	Vector<Karta> talon_ostanek = Uporabno.stringiVKarteN(cmda);
                	Vector<Karta> talon_pobran = Uporabno.stringiVKarteN(cmda);
                	t.zacniRedniDel(pozicija, idxGlavnega, tip, talon_ostanek, talon_pobran);
                	comms.writer.write("MHM\n");
                	comms.writer.flush();
                } else if (cmda.get(0).equals("VRZI")) {
                	cmda.remove(0);
                	int prvi_idx = Integer.parseInt(cmda.get(0)); cmda.remove(0);
                	Vector<Karta> karte_na_mizi = Uporabno.stringiVKarteN(cmda);
                	Karta k = t.vrziKarto(karte, karte_na_mizi, prvi_idx);
                	
                	int foo = -1;
                	for (int i=0; i< karte.size(); i++) {
                		if (karte.elementAt(i).equals(k)) { foo = i; break; }
                	}
                	karte.remove(foo);
                	
                	comms.writer.write("VRZEM " + k.toString() + "\n");
                	comms.writer.flush();
                } else if (cmda.get(0).equals("POVZETEK")) {
                	cmda.remove(0);
                	int prvi_idx = Integer.parseInt(cmda.get(0)); cmda.remove(0);
                	int zmagovalec_idx = Integer.parseInt(cmda.get(0)); cmda.remove(0);
                	Vector<Karta> karte_na_mizi = Uporabno.stringiVKarteN(cmda);
                	t.konecKroga(pozicija==zmagovalec_idx, prvi_idx, zmagovalec_idx, karte_na_mizi);
                	comms.writer.write("MHM\n");
                	comms.writer.flush();
                } else {
                }
            }
        }
    }

    public static void connect(String Hostname, Comms comms) throws Exception {
        Socket client = new Socket(Hostname, 1179);

        comms.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        comms.writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

        String server = comms.reader.readLine();
        System.out.println(server);

        comms.writer.println("AVE " + Igralec.Ime);
        comms.writer.flush();

        String succ = comms.reader.readLine();
        if (!succ.equals("PRIJAVA USPESNA")) throw new Exception("Prijava na streznik neuspesna" + succ);
    }

    public static void main(String argv[]) throws Exception {
        String Hostname;
        if (argv.length < 1) Hostname = "localhost";
        else Hostname = argv[0];

        Comms comms = new Comms();
        connect(Hostname, comms);
        event_loop(comms);

    }
}
