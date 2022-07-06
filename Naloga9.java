import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Naloga9 {
    public static ArrayList<Mesto> mestaNapoti; // zaporedje mest na poti
    public static ArrayList<Cesta> pot; // zaporedje cest na poti
    public static void main(String[] args) throws IOException {
        BufferedReader fin = new BufferedReader(new FileReader(args[0]));
        BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
        HashMap<Integer,Mesto> mesta = new HashMap<>(); //vsa mesta
        ArrayList<Cesta> ceste = new ArrayList<>(); // vse ceste
        String s = fin.readLine();
        String[] tmp = s.split(",");
        int n = Integer.parseInt(tmp[0]);
        int m = Integer.parseInt(tmp[1]);
        int id1;
        int id2;
        for (int i = 0; i < n; i++) {
            s = fin.readLine();
            tmp = s.split(",");
            id1 = Integer.parseInt(tmp[0]);
            id2 = Integer.parseInt(tmp[1]);
            Mesto m1;
            Mesto m2;
            if(!mesta.containsKey(id1)){
                m1 = new Mesto(id1);
                mesta.put(id1, m1);
            } else {
                m1 = mesta.get(id1);
            }
            if(!mesta.containsKey(id2)){
                m2 = new Mesto(id2);
                mesta.put(id2, m2);
            } else {
                m2 = mesta.get(id2);
            }
            Cesta c = new Cesta(m1, m2);
            ceste.add(c);
            m1.dodajPovezavo(c);
            m2.dodajPovezavo(c);
        }
        for(Mesto mesto:mesta.values()){
            mesto.narediPovezave2();
        }
        for(int i = 0; i < m; i++) {
            s = fin.readLine();
            tmp = s.split(",");
            Mesto m1 = mesta.get(Integer.parseInt(tmp[0]));
            Mesto m2 = mesta.get(Integer.parseInt(tmp[1]));
            int stPotnikov = Integer.parseInt(tmp[2]);
            if(m1 == null|| m2 == null) {
                continue;
            }
            ArrayList<Cesta> pot = najdiPot(m1, m2, mesta);// najde najkrajšo pot med mestoma
            for(Cesta c: pot) {
                c.stPotnikov += stPotnikov;
            }
        }
        ArrayList<Cesta> najCeste = new ArrayList<>(); // najboljša/e ceste
        int maxPotnikov = -1;
        for(Cesta c: ceste) {
            if(c.stPotnikov > maxPotnikov) {// ce ima ta cesta največ potnikov
                najCeste.clear();   //zbrisemo ostale ceste
                najCeste.add(c);
                maxPotnikov = c.stPotnikov;
            } else if(c.stPotnikov == maxPotnikov) {
                najCeste.add(c);// ce ima vec cest največ potnikov dodamo vse
            }
        }
        najCeste.sort(null);// sortiramo po ozankah mest
        for(Cesta c: najCeste) {
            out.write(""+ c.a.mestoID + "," + c.b.mestoID + "\n");//izpis
        }

        fin.close();
        out.close();
    }
    public static ArrayList<Cesta> najdiPot(Mesto m1, Mesto m2, HashMap<Integer,Mesto> mesta) {
        for (Mesto m : mesta.values()) {
            m.dolzinaPoti = Integer.MAX_VALUE;
            m.prevCeste = new ArrayList<>();
        }
        Queue<Mesto> curr = new LinkedList<>();
        m1.dolzinaPoti = 0;
        curr.add(m1);
        while(!curr.isEmpty()) {
            Mesto tmp = curr.poll();
            if(tmp == m2) {
                break;
            }
            int novaDolzina = tmp.dolzinaPoti + 1;
            for (Cesta c: tmp.povezave2) { // najdemo cesto
                Mesto sosed = c.a == tmp ? c.b : c.a;
                if(sosed.dolzinaPoti > novaDolzina) { // ce je nova pot krajsa odstranimo vse prejsne poti in dodamo novo
                    sosed.dolzinaPoti = novaDolzina;
                    sosed.prevCeste.clear();
                    sosed.prevCeste.add(c);
                    curr.add(sosed);
                } else if(sosed.dolzinaPoti == novaDolzina) { // ce je enako dolga pot jo samo dodamo
                    sosed.prevCeste.add(c);
                }
            }
        }
        pot = null;
        mestaNapoti = null;
        poti(m1,m2, m2, new ArrayList<>()); // poiscemo najboljse poti
        return pot;
    }
    static void poti(Mesto zacetek, Mesto cilj, Mesto curr, ArrayList<Cesta> currPot) {
        if(curr == zacetek) { // smo na koncu
            ArrayList<Mesto> mesta = new ArrayList<>();
            mesta.add(cilj);
            Mesto last = cilj;
            for(Cesta c : currPot) {
                Mesto drugo = c.a == last ? c.b : c.a;
                mesta.add(drugo);
                last = drugo;
            }
            if(pot == null) { // ce se ni druge poti jo nastavis na trenuto
                pot = currPot;
                mestaNapoti = mesta;
            }
            else{ // ce je ze pot pogledas kera je boljsa
                for (int i = mesta.size() - 1; i >= 0; i--) {
                    if(mesta.get(i).mestoID < mestaNapoti.get(i).mestoID) {// ce je boljsa nastavis na novo pot
                        pot = currPot;
                        mestaNapoti = mesta;
                        break;
                    } else if(mesta.get(i).mestoID > mestaNapoti.get(i).mestoID) { // ce ni naredis nic
                        break;
                    }
                }
            }
        } else if(curr.prevCeste.size() == 1) { // ce je samo ena pot jo uporabimo in gremo naprej
            Cesta currCesta = curr.prevCeste.get(0);
            Mesto sosed = currCesta.a == curr ? currCesta.b : currCesta.a;
            currPot.add(currCesta);
            poti(zacetek, cilj, sosed, currPot);
        } else { // ce je vec poti
            for(int i=0 ; i < curr.prevCeste.size() - 1 ;i++) { // nadaljujemo pot
                Cesta currCesta = curr.prevCeste.get(i);
                Mesto sosed = currCesta.a == curr ? currCesta.b : currCesta.a;
                ArrayList<Cesta> novaPot = new ArrayList<>(currPot);
                novaPot.add(currCesta);
                poti(zacetek, cilj, sosed, novaPot); // pogledamo do konca novo pot
            }
        }
    }
    static class Cesta implements Comparable<Cesta>{
        int stPotnikov;
        Mesto a;
        Mesto b;
        public Cesta(Mesto a, Mesto b) {
            if(a.mestoID < b.mestoID) {
                this.a = a;
                this.b = b;
            } else {
                this.a = b;
                this.b = a;
            }
            
        }
        @Override
        public int compareTo(Cesta c1) {
            if(c1.a.mestoID == this.a.mestoID) {
                return this.b.mestoID - c1.b.mestoID;
            }
            return this.a.mestoID - c1.a.mestoID;
        }
    }
    

    static class Mesto {
        int mestoID;
        ArrayList<Cesta> povezave = new ArrayList<Cesta>();
        Cesta[] povezave2;
        int dolzinaPoti;
        ArrayList<Cesta> prevCeste;

        public Mesto(int mestoID) {
            this.mestoID = mestoID;
        }

        public void dodajPovezavo(Cesta povezava) {
            povezave.add(povezava);
        }

        public void narediPovezave2(){
            povezave2 = povezave.toArray(new Cesta[0]);
        }
    }
    
}