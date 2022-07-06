import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;




public class Naloga6 {
    public static void main(String[] args) throws IOException {
        BufferedReader fin = new BufferedReader(new FileReader(args[0]));
        BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
        //Drevo res = new Drevo("");
        Stack<Node> stN = new Stack<>();
        Stack<String> stC = new Stack<>();
        HashMap<String, Integer> p = new HashMap<String, Integer>();
        p.put("NOT", 3);
        p.put("OR", 1);
        p.put("AND", 2);
        p.put(")", 0); 
        
        
        String s = fin.readLine();
        //System.out.print(s);
        s = s.replaceAll("\\(", "( ");
        s = s.replaceAll("\\)", " )");
        s = "( " + s + " )";
        String[] arr = s.split(" ");
        Node n, n1, n2, res;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("(")) {
                stC.add(arr[i]);
            } else if (arr[i].equals("TRUE") || arr[i].equals("FALSE") || (!Character.isUpperCase(arr[i].charAt(0)) && !arr[i].equals("(") && !arr[i].equals(")")))  {
                n = new Node(arr[i]);
                stN.add(n);
                
            } else if(p.containsKey(arr[i]) && p.get(arr[i]) > 0) {
                while(!stC.isEmpty() && !(stC.peek().equals("(")) && (p.get(stC.peek()) >= p.get(arr[i]))) {
                    if(stC.peek().equals("NOT")) {
                        if(p.get(stC.peek()) == p.get(arr[i])) {
                            break;
                        }
                        n = new Node(stC.peek());
                        stC.pop();

                       
                        n1 = stN.peek();
                        stN.pop();
                        n.levi = n1;
                        
                        

                        stN.add(n);

                    } else {
                        n = new Node(stC.peek());
                        stC.pop();

                        n1 = stN.peek();
                        stN.pop();
                        n2 = stN.peek() ;
                        stN.pop();
                        n.levi = n2;
                        n.desni = n1;

                        stN.add(n);

                    }
                }
                stC.push(arr[i]);

            } else if(arr[i].equals(")")) {
                while(!stC.isEmpty() && !stC.peek().equals("(")) {
                    if(stC.peek().equals("NOT")) {
                        n = new Node(stC.peek());
                        stC.pop();

                        n1 = stN.peek();
                        stN.pop();
                        n.levi = n1;

                        stN.add(n);

                    } else {
                        n = new Node(stC.peek());
                        stC.pop();

                        n1 = stN.peek();
                        stN.pop();
                        n2 = stN.peek() ;
                        stN.pop();
                        n.levi = n2;
                        n.desni = n1;

                        stN.add(n);

                    }
                    /*
                    n = new Node(stC.peek());
                    stC.pop();
                    n1 = stN.peek();
                    stN.pop();
                    n2 = stN.peek();
                    stN.pop();
                    n.levi = n2;
                    n.desni = n1;
                    stN.add(n);*/
                }
                stC.pop();
            }
            
        }
        res = stN.peek();

    
        printResult(res,out, true);
        out.write("\n");
        out.write(""+ height(res));
        out.write("\n");
        //System.out.println();
        //System.out.print(height(res));


        fin.close();
        out.close();

    }

    public static void printResult(Node n, BufferedWriter out , boolean jePrvi) throws IOException {
        
        if (n == null) {
            return;
        }
        if (jePrvi) {
            //System.out.print(n.v);
            out.write(n.v);
        } else {
            //System.out.print(","+ n.v);
            out.write(","+ n.v);
        }
       
        printResult(n.levi, out,false);
        printResult(n.desni, out, false);
    }

    public static int height(Node node) {
		if (node == null)
			return 0;
		else
			return Math.max(height(node.levi), height(node.desni)) + 1;
	}
	
	
	

    static class Node {
        String v;
        Node levi;
        Node desni;
        public Node(String v) {
            this.v = v;
            this.levi = null;
            this.desni = null;
            
        }
    }

    
    /*
    static class Drevo {
        Node koren;
        public Drevo(String v) {
            koren = new Node(v);
        }

    }
    */
}
