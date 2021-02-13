
public class PQHeap implements PQ {

    private Element[] heap;
    public int size;

    public PQHeap(int maxElms) {
        this.heap = new Element[maxElms];
    }

    public Element extractMin() {
        if (this.size < 1) {
            System.out.println("Heap underflow.");
        }
        Element min = this.heap[0];
        this.heap[0] = this.heap[this.size - 1];
        this.size--;
        MinHeapify(0);
        return min;
    }

    public void MinHeapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest;
        if (l <= this.size && this.heap[l].key < this.heap[i].key) {
            smallest = l;
        } else {
            smallest = i;
        }
        if (r <= this.size && this.heap[r].key < this.heap[smallest].key) {
            smallest = r;
        }
        if (smallest != i) {
            Exchange(i, smallest);
            MinHeapify(smallest);
        }
    }

    public void insert(Element e) {
        this.heap[this.size] = e;
        int p = e.key;
        e.key = Integer.MAX_VALUE;
        HeapDecreaseKey(size, p);
        this.size++;
    }

    public void HeapDecreaseKey(int i, int key) {
        if (key > this.heap[i].key) {
            System.out.println("New key is larger than current key.");
        }
        this.heap[i].key = key;
        while (i > 0 && this.heap[parent(i)].key > this.heap[i].key) {
            Exchange(i, parent(i));
            i = parent(i);
        }
    }

    public void Exchange(int i, int j) {
        Element one = this.heap[i];
        Element two = this.heap[j];
        this.heap[i] = two;
        this.heap[j] = one;
    }

    public int parent(int i) {
        return i / 2;
    }

    public int left(int i) {
        return 2 * i;
    }

    public int right(int i) {
        return (2 * i) + 1;
    }

}
