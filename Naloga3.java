import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Želimo implementirati seznam celih števil s podatkovno strukturo, ki kombinira dobre lastnosti 
statičnih in dinamičnih polj. Za razliko od običajnega linearnega seznama s kazalci, kjer vsak člen
seznama hrani en sam element, bo v naši strukturi vsak člen seznama hranil do N elementov.
Seznam bo tako predstavljen kot usmerjen linearni seznam, katerih členi vsebujejo statična polja 
velikosti N.
Podatkovna struktura naj podpira naslednje metode:
- public void init(int N)
- public boolean insert(int v, int p)
- public boolean remove(int p)
Metoda void init(int N) sprejme parameter N (kjer je N>1), ki določa največje število elementov 
v posameznem členu seznama. Po klicu metode je seznam prazen. Konstruktor strukture avtomatsko 
kliče metodo init z vrednostjo N=5.
Metoda boolean insert(int v, int p) prejme dva argumenta: vrednost (v), ki jo želimo 
vstaviti in pozicijo (p), na kateri naj se ta element vstavi (prvi element je na poziciji 0). Opozorilo: 
pozicija ni definirana v fizičnem, temveč v logičnem smislu - upoštevajo se le dejansko vstavljeni 
elementi in ne indeksi statičnih polj. Najprej poskusimo vrednost v vstaviti za elementom, ki se 
trenutno nahaja na poziciji p-1 (s tem bo v postal p-ti element seznama, kar je naš cilj). To naredimo 
izključno v primeru, ko ciljni člen (tisti, ki vsebuje element na poziciji p-1) ima vsaj eno prazno mesto. 
V nasprotnem primeru poiščemo člen, v katerem se nahaja element na poziciji p (lahko, da bo to isti 
člen, ki vsebuje element na poziciji p-1) in poskusimo vrednost v vstaviti pred ta element (kar bo spet 
pripeljalo do tega, da bo v postal p-ti element seznama). Če ima statično polje v tem členu vsaj eno 
prazno mesto, vrednost v vstavimo na ustrezno pozicijo in zaključimo. Če pa je statično polje polno, ga 
razdelimo na dva dela tako, da ga nadomestimo z dvema členoma. Prvi člen vsebuje prvo polovico 
elementov (zaokroženo navzdol), preostanek pa je vsebovan v drugem členu. Sedaj ponovimo 
postopek vstavljanja elementa v, ki bo zagotovo končal v enem izmed ustvarjenih dveh členov. Po
uspešnem vstavljanju metoda vrne vrednost true. V primeru vstavljanja na neveljavno lokacijo 
(negativna vrednost ali vrednost, večja od števila elementov v strukturi) se vrednost zavrže in metoda 
vrne false.
Funkcija za brisanje boolean remove(int p) prejme pozicijo elementa, ki ga želimo odstraniti iz 
seznama. Najprej poiščemo logično pozicijo, ki bo izbrisana (upoštevamo samo dejansko vstavljene 
elemente in ne indeksov polj). Element na tem mestu izbrišemo in po potrebi izvedeno zamik 
elementov v levo, da se izognemo vrzeli znotraj fizičnega polja. Če po brisanju število vstavljenih 
elementov v tem členu pade pod N/2 (zaokroženo navzdol), prenesemo iz morebitnega naslednjega 
člena toliko elementov, da dobimo v našem členu ravno N/2 (zaokroženo navzdol) zasedenih mest. Če 
je sedaj v naslednjem členu premalo elementov (manj kot N/2 zaokroženo navzdol), v trenutni člen 
prenesemo tudi vse preostale elemente iz naslednika in ga izbrišemo.

 */
public class Naloga3 {

    public static void main(String[] args) throws IOException {
        BufferedReader fin = new BufferedReader(new FileReader(args[0]));
        BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
        LinkedList res = new LinkedList();
        int n = Integer.parseInt(fin.readLine());
        String s = "";
        String[] arr;
        for (int i = 0; i < n; i++) {
            s = fin.readLine();
            arr = s.split(",");
            switch (arr[0]) {
                case "s":
                    res.init(Integer.parseInt(arr[1]));
                    break;
                case "i":
                    res.insert(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
                    break;
                case "r":
                    res.remove(Integer.parseInt(arr[1]));
                    break;
            }
        }
        out.write("" + res.length + "\n");
        LinkedListElement e = res.first;
        while (e != null) {
            for (int i = 0; i < e.elementi.length; i++) {
                Integer a = e.elementi[i];
                if (a == null) {
                    out.write("NULL");
                } else {
                    out.write("" + a);
                }
                if (i != e.elementi.length - 1) {
                    out.write(",");
                }
            }
            out.write("\n");
            e = e.next;
        }
        
        out.close();
        fin.close();
    }


    static class LinkedListElement {
        Integer[] elementi;
        LinkedListElement next;
        int stZasedenih;

        LinkedListElement(int n) {
            elementi = new Integer[n];
            next = null;
            stZasedenih = 0;
        }

        LinkedListElement(int n, LinkedListElement nxt) {
            elementi = new Integer[n];
            next = nxt;
            stZasedenih = 0;
        }
    }

    static class LinkedList {
        protected LinkedListElement first;
        protected int length;

        LinkedList(int n) {
            init(n);
            length = 1;
        }

        LinkedList() {
            init(5);
            length = 1;
        }


        public void init(int N) {
            first = new LinkedListElement(N, null);
        }

        public boolean insert(int v, int p) {
            LinkedListElement e = first;
            int neNull = 0;
            for (int i = 0; i < e.elementi.length; i++) {
                if (neNull == p) {
                    if (e.stZasedenih < e.elementi.length) {
                        System.arraycopy(e.elementi, i, e.elementi, i + 1, e.elementi.length - i - 1); // zamaknes elemente v desno in vpises v na p
                        e.elementi[i] = v;
                        e.stZasedenih++;
                        return true;
                    } else {
                        LinkedListElement nov = new LinkedListElement(e.elementi.length);
                        this.length++;
                        nov.next = e.next;
                        e.next = nov;
                        int prvi = e.elementi.length / 2;
                        System.arraycopy(e.elementi, prvi, nov.elementi, 0, e.elementi.length - prvi);
                        for (int j = prvi; j < e.elementi.length; j++) {
                            e.elementi[j] = null;
                        }
                        e.stZasedenih = prvi;
                        nov.stZasedenih = e.elementi.length - prvi;
                        neNull = 0;
                        i = -1;
                        e = first;
                        continue;
                    }

                }

                if (p > neNull + e.stZasedenih) { // ce p ni v tem elementu ga preskočimo
                    neNull += e.stZasedenih;
                    if (e.next != null) {
                        i = -1;
                        e = e.next;
                        continue;
                    }
                }

                if (e.elementi[i] != null) {
                    neNull++;
                }
                
                if (i == e.elementi.length - 1) {
                    if (e.next != null) {
                        i = -1;
                        e = e.next;
                    }
                }

            }
            return false;
        }

        public boolean remove(int p) {
            LinkedListElement e = first;
            int neNull = 0;
            for (int i = 0; i < e.elementi.length; i++) {
                if (neNull == p && i < e.stZasedenih) {
                    System.arraycopy(e.elementi, i + 1, e.elementi, i, e.elementi.length - i - 1);
                    e.stZasedenih--;
                    e.elementi[e.stZasedenih] = null;
                    if (e.stZasedenih < e.elementi.length / 2) {
                        if (e.next != null) {
                            e.elementi[e.stZasedenih] = e.next.elementi[0];
                            e.stZasedenih++;
                            System.arraycopy(e.next.elementi, 1, e.next.elementi, 0, e.next.elementi.length - 1);
                            e.next.stZasedenih--;
                            e.next.elementi[e.next.stZasedenih] = null;
                            if (e.next.stZasedenih < e.next.elementi.length / 2) {
                                System.arraycopy(e.next.elementi, 0, e.elementi, e.stZasedenih, e.next.stZasedenih);
                                e.stZasedenih += e.next.stZasedenih;
                                e.next = e.next.next;
                                length--;
                            }
                        }
                    }
                    return true;
                }
                if (p > neNull + e.stZasedenih) {
                    neNull += e.stZasedenih;
                    if (e.next != null) {
                        i = -1;
                        e = e.next;
                        continue;
                    }
                }

                if (e.elementi[i] != null) {
                    neNull++;
                } 
                
                if (i == e.elementi.length - 1) {
                    if (e.next != null) {
                        i = -1;
                        e = e.next;
                    }
                }
            }
            return false;
        }
    }

}