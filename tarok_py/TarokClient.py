#!/usr/bin/python

import sys, socket, math, traceback, random, copy

from common import *
igralec = None

def event_loop(self):
	cmda = self.readvector()
	if cmda[0] == "ADIJO": return False
	if cmda[0] == "KONEC_IGRE": return True
	
	if cmda[0] != "ZACNI": raise Exception("Napaka pri komunikaciji s streznikom: %s" % cmda[0])
		
	karte,foo = stringiVKarteN(cmda[1:])
	karte = set(karte)

	t = igralec.Igralec()
	tip = t.zacniIgro(list(karte))
	self.writeline("PREDLAGAM " + repr(tip))
	
	pozicija = -1
	while True:
		cmda = self.readvector()
		if cmda[0] == "ADIJO": return False
		elif cmda[0] == "KONEC_IGRE":
			t.konecIgre(cmda[1]); break
		elif cmda[0] == 'ZALOZI':
			N_grup = int(cmda[1])
			talon = []
			ret = cmda[2:]
			for i in range(N_grup):
				grupa,ret = stringiVKarteN(ret)
				talon.append(grupa)
			vzete,zalozene=t.zalozi(karte, talon)
			karte |= set(vzete)
			karte -= set(zalozene)
			#print 'po zalaganju imam', karteStr(karte)
			self.writeline('LEZIM ' + karteVString(vzete) + ' ' + karteVString(zalozene))
		elif cmda[0] == 'IGRAJ':
			pozicija = idxIgralca = int(cmda[1])
			idxGlavnega = int(cmda[2])
			tip_igre = TipIgre(cmda[3])
			talon_ostanek,foo = stringiVKarteN(cmda[4:])
			talon_pobran,foo = stringiVKarteN(foo)
			t.zacniRedniDel(idxIgralca, idxGlavnega, tip_igre, talon_ostanek, talon_pobran)
			self.writeline('MHM')
		elif cmda[0] == 'VRZI':
			prvi_idx = int(cmda[1])
			karte_na_mizi,foo = stringiVKarteN(cmda[2:])
			karta = t.vrziKarto(list(copy.deepcopy(karte)), karte_na_mizi, prvi_idx)
			karte.remove(karta)
			#print 'po metanju imam', karteStr(karte)
			self.writeline('VRZEM ' + repr(karta))
		elif cmda[0] == 'POVZETEK':
			prvi_idx = int(cmda[1])
			zmagovalec_idx = int(cmda[2])
			karte_na_mizi,foo = stringiVKarteN(cmda[3:])
			t.konecKroga(zmagovalec_idx==pozicija, prvi_idx, zmagovalec_idx, karte_na_mizi)
			self.writeline('MHM')
		else: raise Exception("Napaka pri komunikaciji s streznikom: %s" % cmda[0])
	return True


def main():
	global igralec
	hostName = 'localhost'
	igralec_nm = 'igralec'
	
	if (len(sys.argv) > 1) and (sys.argv[1] != '-'):
		hostname = sys.argv[1]
	if len(sys.argv) > 2:
		igralec_nm = sys.argv[2]
	
	igralec = __import__(igralec_nm, globals(), locals(), [], -1)
	reload(igralec)

	try:
		sock = Socket()
		sock.connect(hostName, 1179, igralec.Igralec.ime)
		sock.event_loop = event_loop
		random.seed()
		while sock.event_loop(sock): pass
		sock.close()
	except:
		traceback.print_exc()

if __name__ == '__main__': main()
