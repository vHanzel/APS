import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Naloga8 {
    private static int n;
    private static BufferedWriter out;
    private static Node[] nodes;
    public static void main(String[] args) throws IOException {
        BufferedReader fin = new BufferedReader(new FileReader(args[0]));
        out = new BufferedWriter(new FileWriter(args[1]));
        n = Integer.parseInt(fin.readLine());
        HashMap <Integer, Node> Id = new HashMap<>();
        String tmp = "";
        String[] line;
        nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            tmp = fin.readLine();
            line = tmp.split(",");
            nodes[i] = new Node(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3]));
            Id.put(Integer.parseInt(line[0]),nodes[i]);


        }
        Node koren = build(Id); // zgradimo drevo
        setCoordinates(koren, 0);
        printLevelOrder(koren);
        
        fin.close();
        out.close();
    }

   public static void printLevelOrder(Node root) throws IOException
    {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        while (!queue.isEmpty()) {
 
            
            Node tempNode = queue.poll();
            //System.out.print(tempNode.v + ","+ tempNode.x + ","+ tempNode.y);
            //System.out.println();
            
            out.write(tempNode.v + ","+ tempNode.x + ","+ tempNode.y +"\n");
            if (tempNode.levi != null) {
                queue.add(tempNode.levi);
            }
            if (tempNode.desni != null) {
                queue.add(tempNode.desni);
            }
        }
    }
    public static void setCoordinates(Node node, int tip) { // tip == 0 koren | tip == 1 lsin | tip == 2 dsin
        if (node == null) {
            return;
        }
        if(tip == 0) {
            node.y = 0;
            node.x = size(node.levi);

            setCoordinates(node.levi, 1);
            setCoordinates(node.desni, 2);
        } else if(tip == 1) { // levi sin
            node.x = node.stars.x - size(node.desni) - 1;
            node.y = node.stars.y + 1;

            setCoordinates(node.levi, 1);
            setCoordinates(node.desni, 2);

        } else if(tip == 2) { // desni sin
            node.x = node.stars.x + size(node.levi) + 1;
            node.y = node.stars.y + 1;
            setCoordinates(node.levi, 1);
            setCoordinates(node.desni, 2);
        }
    }
    public static int size(Node root) {
        if(root == null) {
            return 0;
        }
        return size(root.desni) + size(root.levi) + 1;
    }
    public static int height(Node koren) {
        if(koren == null) {
            return 0;
        }
        int lheight = height(koren.levi);
        int rheight = height(koren.desni);
        if (lheight > rheight)
            return (lheight + 1);
        else
            return (rheight + 1);
    }
    public static Node koren() {
        for (int i = 0; i < nodes.length; i++) {
            Node curr = nodes[i];
            if(curr.stars == null) {
                return curr;
            }
        }
        return null;
    }
    public static Node build(HashMap <Integer, Node> Id) {
        for (int i = 0 ; i < nodes.length; i++) {
            Node node = nodes[i];
            if(node == null) {
                continue;
            } else {
                if(node.idLevi != -1) {
                    Node lSin = Id.get(node.idLevi);

                    node.levi = lSin;
                    lSin.stars = node;
                    lSin.idStars = node.id;


                }
                if(node.idDesni != -1) {
                    Node dSin = Id.get(node.idDesni);

                    node.desni = dSin;
                    dSin.stars = node;
                    dSin.idStars = node.id;
                    

                }
            
            }

        }


        return koren();

    }

    static class Node {
        public int id;
        public int v;
        public int idLevi;
        public int idDesni;
        public int idStars;
        public Node levi;
        public Node desni;
        public Node stars;

        public int x;
        public int y;

        public Node(int id, int v, int idLevi, int idDesni) {
            this.id = id;
            this.v = v;
            this.idLevi = idLevi;
            this.idDesni = idDesni;
        }
     }
}