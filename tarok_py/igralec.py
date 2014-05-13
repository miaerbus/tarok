from common import *

class Igralec:
	ime = "Dumb"

	def __init__(self):
		pass

	def zacniIgro(self, karteZaVRoko):
		# zapomnimo si svoje karte v kaksno spremenljivko
		self.karte = karteZaVRoko
		# vedno poskusimo igrati trojko v kari
		return TipIgre(3, False, KARO)
		
	def zalozi(self, karte, talon):
		# najdemo tri karte (ker smo sli igrat trojko), ki jih lahko zalozimo (= ki niso kralj):
		zalozeneKarte = [k for k in karte if (k.vrednost!=14 or k.barva==TAROK)][:3]
		# vedno izberemo drugi kupcek iz talona
		return (talon[1], zalozeneKarte)
		
	def zacniRedniDel(self, idxIgralca, glavniIgralec, tipIgre, ostanekTalona, pobraneKarteTalona):
		# resen igralec dobi tukaj kup informacij, ki si jih je vredno zapomniti
		return
		
	def vrziKarto(self, mojeKarte, karteNaMizi, prviIgralec):
		# vrzemo prvo dopustno karto
		return veljavnePoteze(mojeKarte, karteNaMizi)[0]
		
	def konecKroga(self, zmagal, prviIgralecVKrogu, zmagovalec, karteNaMizi):
		# resen igralec si morda zeli zapomniti, katere karte so padle.
		return
		
	def konecIgre(self, razlog):
		# izpisemo razlog, da vidimo, ce je slo vse po nacrtih
		print 'KONEC IGRE.', razlog
