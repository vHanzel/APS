import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Naloga4 {
    public static void main(String[] args) throws IOException {
        BufferedReader fin = new BufferedReader(new FileReader(args[0]));
        BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
        int t = Integer.parseInt(fin.readLine());
        int stStolov = Integer.parseInt(fin.readLine());
        int casStrizenja = Integer.parseInt(fin.readLine());
        int k = Integer.parseInt(fin.readLine());
        String x = fin.readLine();
        String[] arr = x.split(",");
        int[] zamiki = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            zamiki[i] = Integer.parseInt(arr[i]);
        }
        x = fin.readLine();
        arr = x.split(",");
        int[] potrpljenje = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            potrpljenje[i] = Integer.parseInt(arr[i]);
        }

        int j = 0;
        int sumWait = zamiki[j];
        Cakalnica waitRoom = new Cakalnica(stStolov);
        boolean jeZaseden = false;
        int id = 0; // id stranke
        int y = 0; // index v tabeli potrpljenja
        int currStrankaID = 0;
        Integer timer = -1;
        boolean jeZePostrizen = false;

        for (int i = 1; i <= t; i++) {

            if (timer == 0 && jeZaseden) {
                if (jeZePostrizen) {
                    out.write(",");
                }
                jeZePostrizen = true;
                out.write("" + currStrankaID);
                jeZaseden = false;
                casStrizenja += k;
            }

            if (!jeZaseden && waitRoom.dolzina() != 0) { // ce stol ni zaseden vzamemo stranko iz čakalnice in nastavimo timer
                Stranka stranka = waitRoom.odstrani();
                currStrankaID = stranka.id;
                jeZaseden = true;
                timer = casStrizenja;


            }
            boolean odstranjen = false;
            for (int a = 0; a < waitRoom.length; a++) {
                waitRoom.get(a).potrpljenje--;
                if (waitRoom.get(a).potrpljenje == 0) {
                    waitRoom.stoli[a] = null;
                    odstranjen = true;
                }
            }
            if (odstranjen) {
                int neNullIndex = 0;
                for (int a = 0; a < waitRoom.length; a++) {
                    if (waitRoom.get(a) != null) {
                        waitRoom.stoli[neNullIndex++] = waitRoom.get(a);
                    }
                }
                waitRoom.length = neNullIndex;
            }


            if (i == sumWait) { //nova stranka pride v salon
                id++;
                if (!jeZaseden) {
                    currStrankaID = id;
                    jeZaseden = true;
                    timer = casStrizenja;


                } else if (waitRoom.dolzina() < stStolov) {
                    waitRoom.add(new Stranka(potrpljenje[y % potrpljenje.length], id));
                }
                j++;
                y++;

                sumWait += zamiki[j % zamiki.length];
            }


            if (jeZaseden) {
                timer--;
            }


        }
		out.write("\n");

        fin.close();
        out.close();

    }

    static class Stranka {
        int potrpljenje;
        int id;

        Stranka(int potrpljenje, int id) {
            this.potrpljenje = potrpljenje;
            this.id = id;

        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Stranka)) {
                return false;
            }
            return id == ((Stranka) obj).id;
        }
    }

    static class Cakalnica {


        private Stranka[] stoli;
        private int length;

        public Cakalnica(int stStolov) {
            stoli = new Stranka[stStolov];
        }

        public boolean empty() {
            return length == 0;
        }

        public void add(Stranka stranka) {
            stoli[length] = stranka;
            length++;
        }

        public void remove(Stranka stranka) {
            for (int i = 0; i < length; i++) {
                if (stoli[i].equals(stranka)) {
                    System.arraycopy(stoli, i + 1, stoli, i, length - i - 1);
                    length--;
                    return;
                }
            }
        }

        public Stranka get(int i) {
            return stoli[i];
        }

        public void remove(int i) {
            System.arraycopy(stoli, i + 1, stoli, i, length - i - 1);
            length--;
        }


        private Stranka odstrani() {
            if (empty()) {
                return null;
            }
            Stranka stranka = stoli[0];
            remove(0);
            return stranka;
        }

        public int dolzina() {
            return this.length;
        }

    }


}