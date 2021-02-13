
import java.io.*;
import java.util.*;

public class Decode {

    public static ArrayList<Node> leaves = new ArrayList<Node>();
    public static ArrayList<Object> chars = new ArrayList<Object>();
    public static PQHeap heap = new PQHeap(256);
    public static BitInputStream bitIn = null;
    public static FileInputStream inFile = null;
    public static int[] Histogram = new int[256];
    public static int counter;
    public static int current;
    public static int sum;
    public static Node root;
    public static FileOutputStream outFile = null;
    public static BitOutputStream bitOut = null;

    public static void main(String[] args) {

        BufferedReader reader;

        try {

            inFile = new FileInputStream(args[0]);
            bitIn = new BitInputStream(inFile);
            outFile = new FileOutputStream(args[1]);
            bitOut = new BitOutputStream(outFile);

            for (int i = 0; i < 256; i++) {
                int c = bitIn.readInt();
                Histogram[i] = c;
                sum = sum + Histogram[i];
            }

            for (int i = 0; i < Histogram.length; i++) {
                int d = Histogram[i];
                if (d != 0) {
                    //System.out.print((char) i + " " + d);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Succesfully decoded histogram.");
        for (int i = 0; i < Histogram.length; i++) {
            int c = Histogram[i];
            if (c != 0) {
                Node n = new Node(c, null, null, null, (char) i);
                leaves.add(n);
                Element e = new Element(c, n);
                //System.out.print((char) i + " " + c);
                heap.insert(e);
                counter++;
            }
        }
        BuildTree(counter);
        Element herp = heap.extractMin();
        root = (Node) herp.data;
        //HuffmanTreeDraw.draw(root);
        try {
            DeHuff(root);
        }
        catch (StackOverflowError s) {
            System.out.println("Stackoverflow. Call with -Xss512m as arguement.");
        }
        System.out.println("Successfully decoded the file.");

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

    public static void DeHuff(Node n) {
        try {
            if (sum == 0) {
                return;
            } else if (n.LChild == null && n.RChild == null) {
                //System.out.print(n.data);
                sum--;
                outFile.write((char) n.data);
                DeHuff(root);
                return;
            }
            int i = bitIn.readBit();
            if (i == 1) {
                DeHuff(n.RChild);
            } else if (i == 0) {
                DeHuff(n.LChild);
            } else {
                bitIn.close();
                outFile.close();
            }
        } catch (IOException e) {
            System.out.print(e);
        }
    }
}
