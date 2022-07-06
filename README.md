**Naloga 2**

Rok se igra s tekstom. Napisal je program, ki obrne vrstni red črk v besedah, zamenja sode in lihe

besede (prvo in drugo, tretjo in četrto,...) v povedi, obrne vrstni red povedi ter obrne vrstni red lihih

odstavkov (medtem ko sodi odstavki ostanejo na mestu). Napiši program, ki bo pretvoril besedilo v

prvotno stanje.



**Naloga 3**

Želimo implementirati seznam celih števil s podatkovno strukturo, ki kombinira dobre lastnosti

statičnih in dinamičnih polj. Za razliko od običajnega linearnega seznama s kazalci, kjer vsak člen

seznama hrani en sam element, bo v naši strukturi vsak člen seznama hranil do N elementov.

Seznam bo tako predstavljen kot usmerjen linearni seznam, katerih členi vsebujejo statična polja

velikosti N.

Podatkovna struktura naj podpira naslednje metode:

· public void init(int N)

· public boolean insert(int v, int p)

· public boolean remove(int p)

Metoda void init(int N) sprejme parameter N(kjer je N>1), ki določa največje število elementov

v posameznem členu seznama. Po klicu metode je seznam prazen. Konstruktor strukture avtomatsko

kliče metodo init z vrednostjo N=5.

Metoda boolean insert(int v, int p) prejme dva argumenta: vrednost (v), ki jo želimo

vstaviti in pozicijo (p), na kateri naj se ta element vstavi (prvi element je na poziciji 0). Opozorilo:

pozicija ni definirana v fizičnem, temveč v logičnem smislu - upoštevajo se le dejansko vstavljeni

elementi in ne indeksi statičnih polj. Najprej poskusimo vrednost v vstaviti za elementom, ki se

trenutno nahaja na poziciji p-1(s tem bo vpostal p-ti element seznama, kar je naš cilj). To naredimo

**izključno** v primeru, ko ciljni člen (tisti, ki vsebuje element na poziciji p-1) ima vsaj eno prazno mesto.

V nasprotnem primeru poiščemo člen, v katerem se nahaja element na poziciji p(lahko, da bo to isti

člen, ki vsebuje element na poziciji p-1) in poskusimo vrednost vvstaviti pred ta element (kar bo spet

pripeljalo do tega, da bo vpostal p-ti element seznama). Če ima statično polje v tem členu vsaj eno

prazno mesto, vrednost vvstavimo na ustrezno pozicijo in zaključimo. Če pa je statično polje polno, ga

razdelimo na dva dela tako, da ga nadomestimo z dvema členoma. Prvi člen vsebuje prvo polovico

elementov (**zaokroženo navzdol**), preostanek pa je vsebovan v drugem členu. Sedaj ponovimo

postopek vstavljanja elementa v, ki bo zagotovo končal v enem izmed ustvarjenih dveh členov. Po

uspešnem vstavljanju metoda vrne vrednost true. V primeru vstavljanja na neveljavno lokacijo

(negativna vrednost ali vrednost, večja od števila elementov v strukturi) se vrednost zavrže in metoda

vrne false.

Funkcija za brisanje boolean remove(int p) prejme pozicijo elementa, ki ga želimo odstraniti iz

seznama. Najprej poiščemo logično pozicijo, ki bo izbrisana (upoštevamo samo dejansko vstavljene

elemente in ne indeksov polj). Element na tem mestu izbrišemo in po potrebi izvedeno zamik

elementov v levo, da se izognemo vrzeli znotraj fizičnega polja. Če po brisanju število vstavljenih

elementov v tem členu pade pod N/2 (**zaokroženo navzdol**), prenesemo iz morebitnega naslednjega

člena toliko elementov, da dobimo v našem členu ravno N/2 (**zaokroženo navzdol**) zasedenih mest. Če

je sedaj v naslednjem členu premalo elementov (manj kot N/2 **zaokroženo navzdol**), v trenutni člen

prenesemo tudi vse preostale elemente iz naslednika in ga izbrišemo.





**Naloga 4**

Napisali bomo simulator dogajanja v frizerskem salonu. V salonu se nahaja samo en frizerski stol, kar

pomeni, da se v vsakem trenutku lahko striže samo ena stranka. Salon ima tudi čakalnico, ki deluje po

sistemu FIFO (first-in-first-out).

V salon periodično vstopajo nove stranke, ki so opisane z id-jem in potrpljenjem (koliko so pripravljene

sedeti v čakalnici preden nepostrižene odidejo iz salona). Če je ob vstopu stranke v salon čakalnica

polna, stranka odide in je ni več nazaj. V nasprotnem primeru se usede kot zadnja v vrsti (če je čakalnica

prazna in se trenutno nobeden ne striže, se usede kar na frizerski sol).

Ko se striženje ene stranke začne, se izvede do konca. Po končanem striženju stranka zapusti salon in

se prične s striženjem naslednjega v vrsti (če le-ta obstaja). Vsako striženje dodatno utrudi frizerja, kar

povzroči podaljševanje časa naslednjih striženj.

Vhodni parametri simulacije so:

T – število korakov simulacije

N – število stolov v čakalnici

S – trajanje striženja (v korakih simulacije)

K – za koliko se podaljša striženje vsake naslednje stranke (v korakih simulacije)

L – seznam z zamiki prihodov strank (v korakih simulacije). Seznam L lahko vsebuje enega ali

LA – seznam z zamiki prihodov strank (v korakih simulacije). Seznam LA lahko vsebuje enega ali 
več elementov. Na primer, če velja LA = [X, Y, Z], pomeni, da bodo stranke vstopale v salon v 
korakih X, X+Y, X+Y+Z, X+Y+Z+X, X+Y+Z+X+Y, X+Y+Z+X+Y+Z,…

LW – seznam s podatki o potrpljenju strank (v korakih simulacije). Seznam LW lahko vsebuje 
enega ali več elementov. Na primer, če velja Lw = [A,B], pomeni, da bo prva stranka imela 
potrpljenje A, druga stranka B, tretja stranka A, četrta stranka B in tako naprej.


**Naloga 6**

Vhodna tekstovna datoteka vsebuje logični izraz, sestavljen iz konstant ("TRUE" in "FALSE"), spremenljivk

(simbolična imena, ki začnejo z malo črko, nadaljujejo se pa lahko s poljubnimi črkami ali ciframi), operatorjev

("AND", "OR" in "NOT") in oklepajev.

Implementirajte razred ***Naloga6***, ki vsebuje metodo ***main***. Metoda v argumentih prejme poti do vhodne in

izhodne datoteke (args[0] in args[1]) in na podlagi prebranega vhoda sestavi binarno izrazno drevo (pri tem se

držite dogovora, da sta operatorja "AND" in "OR" **levo asociativna**). Ko je drevo zgrajeno, naj se v izhodno

datoteko najprej izpišejo oznake vseh vozlišč drevesa v premem (preorder) vrstnem redu (ločeno z vejicami, brez

presledkov), nato se v novi vrstici izpiše še višina drevesa.

**Naloga 7**

V nekem mestu ima javni prevoz **N** linij. Po mestu je razporejenih **M** postajališč, ki so označena s celimi števili.

Potek proge posamezne linije je podan s seznamom oznak postajališč. Vsaka linija obratuje v obeh smereh. Za

dani postaji **A** (vstop) in **B** (izstop) poiščite dve poti: pot z najmanjšim številom prestopanj in pot z najmanjšim

številom postankov.

Vhodna datoteka v prvi vrstici vsebuje število linij javnega prevoza **N**. Nato so po vrsticah podana zaporedja

postajališč, ki določajo potek posameznih linij (ena linija v eni vrstici). Oznake postajališč so ločene z vejicami. V

zadnji vrstici vhodne datoteke sta zapisani in z vejico ločeni celi števili **A** in **B**, ki predstavljata oznaki vstopnega

in izstopnega postajališča.

Implementirajte razred ***Naloga7***, ki vsebuje metodo ***main***. Metoda prejme poti do vhodne in izhodne datoteke

(args[0] in args[1]), prebere vhodne podatke o linijah javnega prevoza in nato v izhodno datoteko zapiše tri

vrednosti, vsako v svoji vrstici.

V prvi vrstici naj bo zapisano minimalno število prestopanj, ki ga potrebujemo za pot od vstopnega postajališča

**A** do izstopnega postajališča **B** (pri tem nas ne zanima dejanska dolžina poti oziroma število prevoženih postaj).

Če sta obe postajališči na isti progi, je število prestopanj enako 0. Če z uporabo linij javnega prevoza ni možno

priti iz enega postajališča do drugega, je število prestopanj enako -1.

V drugi vrstici naj bo zapisano število prevoženih postaj na najkrajši vožnji med vstopnim postajališčem **A** in

izstopnim postajališčem **B** (pri tem nas ne zanima število prestopanj na tej poti). Če sta postajališči sosednji na

isti liniji, je dolžina najkrajše poti enaka 1. Če z uporabo linij javnega prevoza ni možno priti iz enega postajališča

do drugega, je dolžina najkrajše poti enaka -1.

V tretji vrstici naj bo zapisano število 1, če ima pot z najmanj postajami tudi najmanj prestopanj. Drugače

povedano, v tretji vrstici naj bo zapisano število 1, če obstaja pot, ki je hkrati optimalna po kriteriju števila postaj

in kriteriju števila prestopanj. V nasprotnem primeru naj bo zapisano število 0. Če z uporabo linij javnega prevoza

ni možno priti iz enega postajališča do drugega, naj bo v tretji vrstici zapisano število -1.



**Naloga 8**

Želimo poskrbeti za lepši izris splošnih binarnih dreves (med elementi ni nujna urejenost). V ta namen želimo

vsakemu vozlišču v drevesu določiti koordinati (x,y) v izrisu. Veljajo naslednja pravila:

Koordinata y je enaka globini vozlišča v drevesu. Koren je na globini 0.

Za vsako poddrevo s korenom *k* velja:

Koordinate x vozlišč v levem poddrevesu so manjše od koordinate x korena *k.*

Koordinate x vozlišč v desnem poddrevesu so večje od koordinate x korena *k.*

Noben par vozlišč v drevesu nima enakih koordinat x.

Zaloga vrednosti koordinat x je od 0 do N - 1, pri čemer je N število vozlišč v drevesu.

Drevo je podano v tekstovni vhodni datoteki. V prvi vrstici je zapisano celo število N, ki označuje število vozlišč v

drevesu. V naslednjih N vrsticah so zapisani podatki o vozliščih v poljubnem vrstnem redu. Posamezna vrstica je

oblike *ID,V,ID\_L,ID\_R* (vse vrednosti so cela števila). *ID* predstavlja identifikator vozlišča; *V* je vrednost, zapisana

v tem vozlišču; *ID\_L* je identifikator levega sina; *ID\_R* je identifikator desnega sina. Za identifikatorje velja, da so

enolično določeni. Identifikator -1 označuje prazno poddrevo (vozlišče nima ustreznega sina).

Izhodna datoteka naj vsebuje N vrstic. Posamezna vrstica naj bo sestavljena iz podatkov: vrednost v vozlišču, x

koordinata vozlišča, y koordinata vozlišča (ločeno z vejicama). Vozlišča izpisujte v vrstnem redu, ki ustreza obhodu

drevesa po nivojih - vozlišča izpisujemo nivo po nivo (vozlišča znotraj istega nivoja naj bodo izpisana od skrajno

levega proti skrajno desnem).



**Naloga 9**

Podan je neusmerjen povezan graf, kjer vozlišča predstavljajo mesta, povezave pa cestne odseke med njimi.

Podana je tudi množica dejstev oblike, **A,B,C**, kjer sta **A** in **B** (naravni števili) oznaki mest, **C** pa predstavlja število

potnikov (vozil), ki bodo potovali iz mesta **A** v mesto B. Vsak potnik vedno izbere najkrajšo pot iz mesta **A** v mesto

**B**. Če najkrajša pot ni enolično določena, potniki izberejo pot, ki se pojavi prej v urejenem seznamu najkrajših

poti. Urejen seznam dobimo tako, da razvrstimo poti naraščajoče najprej po velikosti ključa prvega mesta na

poti, potem naraščajoče po velikosti drugega ključa in tako naprej do ključa zadnjega mesta. Na vsakem cestnem

odseku se nahaja cestninska postaja, ki vsem vozilom zaračuna enako cestnino. Poiščite cestninske postaje, ki

bodo pobrale največ cestnin.

Implementirajte razred ***Naloga9***, ki vsebuje metodo ***main***. Metoda v argumentih prejme poti do vhodne in

izhodne datoteke (args[0] in args[1]).

Vhodna datoteka ima v prvi vrstici zapisani in z vejico ločeni naravni števili **N** in **M**, ki določata število povezav

grafa (**N**) in število dejstev (**M**). V naslednjih **N** vrsticah so zapisane povezave neusmerjenega grafa, ločene z

vejico. Nato pa v naslednjih **M** vrsticah dejstva oblike **A**,**B**,**C**.

Izhodna datoteka naj vsebuje eno vrstico v formatu **A,B**, pri čemer velja, da je **A** < **B**. Zapis **A,B** označuje cestni

odsek med mesti **A** in **B**, na katerem se bo pobralo največ cestnine. V primeru, da je takšnih odsekov več, naj bo

vsak zapisan v svojo vrstico naraščajoče urejeno najprej po oznaki **A** nato pa **B**.


