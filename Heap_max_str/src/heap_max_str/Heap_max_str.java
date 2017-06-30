/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heap_max_str;

/**
 *
 * @author F.R.G
 * @see https://github.com/carlosguevara1854/Monticulo_max
 */
public class Heap_max_str {

    public static void main(String[] args) {
        heap_max monticulo_max = new heap_max();
        //Lee el archivo "monticulo_in.txt"
        monticulo_max.reader();
        //Se elimina el archivo de salida, para borrar el "historial" y que solo
        //se conserve o muestre el status actual.
        monticulo_max.erasefile("monticulo_out.txt");
        //Se agraga la condición, para cubrir el caso en que el usuario no ingrese
        //llaves a agregar o a eliminar.
        if (monticulo_max.keys_add != null) {
            for (int i = 0; i < monticulo_max.keys_add.length; i++) {
                monticulo_max.insert_heap_max(monticulo_max.keys_add[i]);
            }
            //Se graba el status tras la inserción.
            monticulo_max.writer();
        } else {
            System.err.println("Error. No se han encontrado llaves para agregar.");
        }        
        if (monticulo_max.keys_remove != null) {
            for (int i = 0; i < monticulo_max.keys_remove.length; i++) {
                monticulo_max.erase_heap(monticulo_max.keys_remove[i]);
            }
            //Se graba el status tras la eliminación.
            monticulo_max.writer();
        } else {
            System.err.println("Error. No se han encontrado llaves para eliminar.");
        }
    }
}
