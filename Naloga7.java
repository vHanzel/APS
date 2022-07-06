import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Naloga7 {
    public static void main(String[] args) throws IOException {
        BufferedReader fin = new BufferedReader(new FileReader(args[0]));
        BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
        int n = Integer.parseInt(fin.readLine());
        HashMap<Integer,Postaja> postaje  = new HashMap<>();//k = stPostaje |  v =obj postaja 
        HashMap<Integer,ArrayList<Postaja>> linije  = new HashMap<>();// k = st Linije | v = array postaj na liniji
        String s;
        String[] tmp;
        int currPostaja = -1;
        for (int i = 1; i <= n; i++) {
            s = fin.readLine();
            tmp = s.split(",");
            ArrayList<Postaja> linija = new ArrayList<>();
            for (int j = 0; j < tmp.length; j++) {
                currPostaja = Integer.parseInt(tmp[j]);
                Postaja postaja;
                if(postaje.containsKey(currPostaja)) {
                    postaja = postaje.get(currPostaja);

                } else {
                    postaja = new Postaja(currPostaja);
                    postaje.put(currPostaja, postaja);
                }
                postaja.dodajLinijo(i);
                linija.add(postaja);
            }
            linije.put(i,linija);


        }
        s = fin.readLine();
        tmp = s.split(",");
        int vstop = Integer.parseInt(tmp[0]);// st vstopne postaje
        int izstop = Integer.parseInt(tmp[1]);//st izstopne postaje
        int minStPrestopov = minStPrestopov(postaje.get(vstop), postaje.get(izstop), linije, n);
        int[] arr = stPrevozenih(postaje.get(vstop), postaje.get(izstop), linije); // arr[0] = stPrevozenih psotaj arr[1] = stPrestopov
        out.write(""+ minStPrestopov + "\n" + arr[0] + "\n");
        if(arr[0] == -1) {
            out.write(""+ -1+ "\n");
            
        } else if(arr[1] == minStPrestopov) {
            out.write(""+1+"\n");
        } else {
            out.write(""+0+"\n");
        }
        


        fin.close();
        out.close();
    }

    public static int minStPrestopov(Postaja vstop, Postaja izstop, HashMap<Integer,ArrayList<Postaja>> linije, int size) {
        Queue<Postaja> curr = new LinkedList<>();// trenutne postaje
        Queue<Postaja> accessible = new LinkedList<>();// vse doseglijve postaje
        HashMap<Integer, Boolean> zeObsikana = new HashMap<>(); // ce je linija ze obiskana
        int stPrestopov = -1;
        for (int i = 1; i <= size; i++) {
            zeObsikana.put(i, false);
        }
        curr.add(vstop);// dodamo prvo postajo
        while(!curr.isEmpty()) {
            while(!curr.isEmpty()){
                Postaja tmp = curr.poll(); // vzamemo prvo postajo
                if(tmp == izstop) { // ce smo na koncu vrnemo st prestopov
                    return stPrestopov;
                }
                for(int i : tmp.linije) {
                    if (!zeObsikana.get(i)) {
                        accessible.addAll(linije.get(i));// dodamo vse postaje iz linije
                        zeObsikana.put(i, true);//oznacimo da smo ze obiskali postajo
                    }
                }
                
            }
            curr = accessible;
            accessible = new LinkedList<>();
            stPrestopov++;
        }

        return -1;
    }
    public static int[] stPrevozenih(Postaja vstop, Postaja izstop, HashMap<Integer,ArrayList<Postaja>> linije) {
        Queue<Postaja> curr = new LinkedList<>();
        vstop.stPrestopov = -1;
        vstop.stPostaj = 0;
        curr.add(vstop);
        while (!curr.isEmpty()) {
            Postaja tmp = curr.poll();
            if(tmp == izstop) { //ce smo na koncu vrnemo stPostaj in stPrestopov
                return new int[]{izstop.stPostaj, izstop.stPrestopov};
            }
            for(int linija : tmp.linije) {
                ArrayList<Postaja> sosedi = new ArrayList<>(); // sosedne postaje
                ArrayList<Postaja> currLinija = linije.get(linija); //arrayList postaj na trenutni liniji
                for (int j = 0; j < currLinija.size(); j++) {
                    if (currLinija.get(j).stPostaje == tmp.stPostaje) { // posicemo trenutno postajo na trenutni liniji
                        if(j - 1 >= 0) { // pogledamo ce obstaja levi sosed in ga dodamo k sosedom
                            sosedi.add(currLinija.get(j - 1)); 
                        }
                        if(j + 1 < currLinija.size()) { // pogledamo ce obstaja desni sosed in ga dodamo k sosedom
                            sosedi.add(currLinija.get(j + 1));
                        }
                    }
                }
                for(Postaja sosed: sosedi) {
                    if(!sosed.obiskana) { // ce sosed ni obiskan 
                        sosed.stPostaj = tmp.stPostaj + 1;// povecamo st prevozenih postaj
                        sosed.obiskana = true; //oznacimo postajo
                        int novoStPrestopov = tmp.stPrestopov;
                        if(!tmp.prevLine.contains(linija)) { // ce postaja ni na isti liniji povecamo st prestopov
                            novoStPrestopov++;
                        }
                        sosed.stPrestopov = novoStPrestopov;
                        sosed.prevLine.add(linija);// sosedu dodamo linijo po kateri smo prisli
                        curr.add(sosed); // dodamo soseda v queue
                    } else { // ce sosed je ze bil obiskan
                        if(tmp.stPostaj < sosed.stPostaj) { // pogledamo ce je trenutna pot blizja ali enaka od trenutnih moznih
                            int novoStPrestopov = tmp.stPrestopov;
                            if(!tmp.prevLine.contains(linija)) { // ce je nova linija povecamo st prestopov
                                novoStPrestopov++;
                            }
                            if(novoStPrestopov < sosed.stPrestopov) { // ce je nova pot boljsa od ostalih zbrisemo vse ostale in dodamo samo novo pot
                                sosed.prevLine.clear();
                                sosed.prevLine.add(linija);
                                sosed.stPrestopov = novoStPrestopov;
                            } else if(novoStPrestopov == sosed.stPrestopov) { // ce je pot enakovredna jo dodamo
                                sosed.prevLine.add(linija);
                            }
                        }
                    }
                }
            }
            
        }
        return new int[]{-1, -1}; // ce ni poti
    }
    static class Postaja {
        int stPostaje;
        ArrayList<Integer> linije = new ArrayList<Integer>();
        int stPrestopov = Integer.MAX_VALUE;
        int stPostaj = Integer.MAX_VALUE;
        ArrayList<Integer> prevLine = new ArrayList<Integer>();
        boolean obiskana = false;
        public Postaja(int stPostaje) {
            this.stPostaje = stPostaje;
        }
        public void dodajLinijo(int linija) {
            linije.add(linija);
        }
    }

}