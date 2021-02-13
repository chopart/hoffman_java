
public class Node {

    public int freq;
    public Node parent;
    public Node LChild;
    public Node RChild;
    public int id;
    public Object data; //this data is the character in the text

    public Node(int i, Node p, Node l, Node r, Object d) {
        this.freq = i;
        this.parent = p;
        this.LChild = l;
        this.RChild = r;
        this.id = 0;
        this.data = d;
    }
}
