
import java.io.*;
import java.util.*;

public class Encode {

    public static ArrayList<Node> secure = new ArrayList<Node>();
    public static ArrayList<Node> leaves = new ArrayList<Node>();
    public static ArrayList<Integer> binary = new ArrayList<Integer>();
    public static ArrayList<Integer> secondary = new ArrayList<Integer>();
    public static ArrayList<Object> chars = new ArrayList<Object>();
    public static PQHeap heap = new PQHeap(256);
    public static int[] Histogram = new int[256];
    public static int counter;
    public static int current;
    public static Node root = null;
    public static FileOutputStream outFile;
    public static BitOutputStream out;

    public static void main(String[] args) {

        BufferedReader reader;

        try {
            outFile = new FileOutputStream(args[1]);
            out = new BitOutputStream(outFile);
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(args[0])));

            int c;
            while ((c = reader.read()) != -1) {
                char character = (char) c;
                Histogram[c]++;
                chars.add(character);
            }
            for (int i = 0; i < Histogram.length; i++) {
                int d = Histogram[i];
                if (d != 0) {
                    Node n = new Node(d, null, null, null, (char) i);
                    Element e = new Element(d, n);
                    leaves.add(n);
                    out.writeInt(d);
                    System.out.println(" " + (char) i + ":" + d);
                    heap.insert(e);
                    counter++;
                } else {
                    out.writeInt(d);
                }
            }
        } catch (IOException ex) {
        }
        BuildTree(counter);
        Element h = heap.extractMin();
        root = (Node) h.data;
        //HuffmanTreeDraw.draw(root);
        EncodeFile();
    }

    public static void BuildTree(int counter) {
        for (int i = 0; i < counter - 1; i++) {
            Element a = heap.extractMin();
            Element b = heap.extractMin();
            Element c = Combine(a, b);
            heap.insert(c);
        }
    }

    public static Element Combine(Element e, Element f) {
        int a = e.key + f.key;
        Node m = (Node) e.data;
        Node o = (Node) f.data;
        Node n = new Node(a, null, m, o, null);
        Element h = new Element(a, n);
        m.parent = n;
        o.parent = n;
        return h;
    }

    public static void HuffmanCode(Node root) {
        int i = 0;
        int sum = 0;
        for (Integer c : Histogram) {
            sum = sum + c;
        }
    }

    public static void recursiveHuff(Node n, Node root) {
        int i;
        try {

            if (n == root) {
                Collections.reverse(secondary);
                for (i = 0; i < secondary.size(); i++) {
                    out.writeBit(secondary.get(i));
                }
                secondary.clear();
            } else if (((n.parent).LChild) == n) {
                secondary.add(0);
                recursiveHuff(n.parent, root);
            } else if (((n.parent).RChild) == n) {
                secondary.add(1);
                recursiveHuff(n.parent, root);
            }
        } catch (IOException e) {

        }
    }

    public static void EncodeFile() {

        try {

            int i = 0;
            int j = 0;
            int k = 0;
            for (k = 0; k < binary.size(); k++) {
                out.writeBit(binary.get(k));
            }
            for (i = 0; i < chars.size(); i++) {
                for (j = 0; j < leaves.size(); j++) {
                    if (chars.get(i) == leaves.get(j).data) {
                        recursiveHuff(leaves.get(j), root);
                    }
                }
            }
            out.close();
            outFile.close();
        } catch (IOException e) {

        }
        System.out.println("Succesfully encoded.");
    }
}
