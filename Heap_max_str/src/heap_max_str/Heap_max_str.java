/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heap_max_str;

/**
 *
 * @author F.R.G
 */
public class Heap_max_str {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        heap_max test = new heap_max();
        test.insert_heap_max(1);
        test.insert_heap_max(2);
        test.insert_heap_max(3);
        test.insert_heap_max(4);
        test.insert_heap_max(5);
        test.insert_heap_max(6);
        test.insert_heap_max(7);
        test.erase_heap(7);
        test.BFS(test.root);
    }
}
