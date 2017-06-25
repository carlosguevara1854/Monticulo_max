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
        heap_max monticulo_max = new heap_max();
        //Lee el archivo "monticulo_in.txt"
        monticulo_max.reader();
        //Agraga las lleves leidas.(primera linea)
        for (int i = 0; i < monticulo_max.keys_add.length; i++) {
            monticulo_max.insert_heap_max(monticulo_max.keys_add[i]);
        }
        //Eliminas las lleves leidad.(segunda linea)
        for (int i = 0; i < monticulo_max.keys_remove.length; i++) {
            monticulo_max.erase_heap(monticulo_max.keys_remove[i]);
        }
        //Escribe en el archivo "monticulo_out.txt"
        monticulo_max.writer();
    }
}
