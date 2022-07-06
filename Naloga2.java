import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/*
Rok se igra s tekstom. Napisal je program, ki obrne vrstni red črk v besedah, zamenja sode in lihe 
besede (prvo in drugo, tretjo in četrto,...) v povedi, obrne vrstni red povedi ter obrne vrstni red lihih 
odstavkov (medtem ko sodi odstavki ostanejo na mestu). Napiši program, ki bo pretvoril besedilo v 
prvotno stanje

 */

public class Naloga2 {
    public static void main(String[] args) throws IOException {
        BufferedReader fin = new BufferedReader(new FileReader(args[0]));
        BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
        LinkedList sodi = new LinkedList();
		LinkedList lihi = new LinkedList();
		String tmp = "";
        for (int i = 1; (tmp = fin.readLine()) != null; i++) {
			String s = "";
            if(i % 2 == 1) { // lihi
				s = obrniCrke(tmp);
				
				s = uredi(s.split(" "));
				lihi.addFirst(s);
			} else { // sodi
				s = obrniCrke(tmp);
				s = uredi(s.split(" "));	
				sodi.addLast(s);
			}
			//System.out.println(s);
        }
		//out.write("te¸.st");
		sodi.izpis(lihi, out);
		fin.close();
		out.close();

    }

	public static String uredi(String[] arr) {
		String s = "";
		for(int i = 0; i < arr.length; i+= 2) {

			if(checkDot(arr[i]) && !canInclude(arr, i)) {
				if(i != arr.length - 1) {
					s +=  arr[i] + " ";
				} else {
					s += arr[i];
				}
				
				i--;
				continue;
			}


			if(i != arr.length - 2) {
				s += arr[i + 1] + " " + arr[i] + " ";
			} else {
				s += arr[i + 1] + " " + arr[i];
			}
		}
		String res[] = s.split("\\. ");
		String res2 = "";
		
		for(int i = res.length - 1; i >= 0; i--){
			if(i == 0) {
				res2 += res[i] + ".";
			} else if (i == res.length - 1){
				res2 += res[i] + " ";
			} else {
				res2 += res[i] + ". ";
			}
		}
		return res2;
	}
	
				/*
				ce vzames sodo besedo
				1 piko -> ne vzames
				2 piko -> vzami
				3 piko -> ne vzami

				2 veliko zacetnico -> ne vzami
				3  veliko zacetnico -> vzemi
				*/
	public static boolean canInclude(String[] arr, int index) {
		if (index + 1 < arr.length) {
			if(checkDot(arr[index + 1])){
				return false;
			}
			if((index + 2 >= arr.length) || checkDot(arr[index + 2])) {
				return true;
			}
			if(checkDot(arr[index + 3])) {
				return false;
			}
			if(checkCaptial(arr[index + 2])) {
				return false;
			}
			if (checkCaptial(arr[index + 3])) {
				return true;
			}
	
		}
		return false;
	}
	public static boolean checkCaptial(String s) {
		return Character.isUpperCase(s.charAt(0));
	}
	public static boolean checkDot(String s){
		return s.charAt(s.length() - 1) == '.';
	}
	public static String obrniCrke(String s) {
		String[] besede = s.split(" ");
		String result = "";
		for (int i = 0; i < besede.length; i++) {
			StringBuilder sb = new StringBuilder(besede[i]);
			sb.reverse();
			if (i != besede.length - 1) {
				result += sb.toString() + " ";
			} else {
				result += sb.toString();
			}
		}
		return result;
	}


	
static class LinkedList {

	
    static class LinkedListElement {
	Object element;
	LinkedListElement next;
	
	LinkedListElement(Object obj)
	{
		element = obj;
		next = null;
	}
	
	LinkedListElement(Object obj, LinkedListElement nxt)
	{
		element = obj;
		next = nxt;
	}
	}

	protected LinkedListElement first;
	protected LinkedListElement last;
	
	LinkedList()
	{
		makenull();
	}
	
	//Funkcija makenull inicializira seznam
	void makenull()
	{
		//drzimo se implementacije iz knjige:
		//po dogovoru je na zacetku glava seznama (header)
		first = new LinkedListElement(null, null);
		last = null;
	}
	
	//Funkcija addLast doda nov element na konec seznama
	void addLast(Object obj)
	{
		
		LinkedListElement newEl = new LinkedListElement(obj, null);
		if (last == null)
		{
			first.next = newEl;
			last = first;
		}
		else
		{
			last.next.next = newEl;
			last = last.next;
		}
	}
	int length()
	{
		int count = 0;
		while(first.next != null) {
			count++;
			first = first.next;
		}

		return count;
	}
	
	//Funkcija write izpise elemente seznama
	void izpis(LinkedList l, BufferedWriter out) throws IOException {// this -> soda l -> liha
		LinkedListElement e1 = this.first.next; // sodi
		LinkedListElement e2 = l.first.next; // lihi	
		int le1 = this.length();
		int le2 = l.length();
		int n = Math.min(le1, le2);
		
		
		if ((le1 + le2) % 2 == 1) {
			out.write((String)e2.element);
			out.write("\n");
			e2 = e2.next;
			for(int i = 0; e2 != null; i++) {
				out.write((String)e1.element);
				out.write("\n");
				out.write((String)e2.element);
				if (i != n -1) out.write("\n");
				
				e2 = e2.next;
				e1 = e1.next;
			} 
		} else {
			for(int i = 0; i < n; i++) {
				out.write((String)e2.element);
				out.write("\n");
				out.write((String)e1.element);
				if (i != n -1) out.write("\n");
				
				e2 = e2.next;
				e1 = e1.next;
			}
		} 

	}
	
	//Funkcija addFirst doda nov element na prvo mesto v seznamu (takoj za glavo seznama)
	void addFirst(Object obj)

	{	
		LinkedListElement newEl = new LinkedListElement(obj);
		
		
		newEl.next = first.next;
		first.next = newEl;
		
		if (last == null)
			last = first;
		else if (last == first)
			last = newEl;
	}
}

}