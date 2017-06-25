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
public interface heap_max_i {

    /**
     * Insertar una clave en el monticulo binario de maximos.
     *
     * @param key LLave nueva.
     */
    public void insert_heap_max(int key);

    /**
     * Eliminar un clave en el monticulo binario de maximos.
     *
     * @param key LLave a eliminar.
     */
    public void erase_heap(int key);

    /**
     * Lectura de del archivo.
     */
    public void reader();

    /**
     * Metodo de imprimir arbol por nivel, en archivo (.txt) de salida.
     *
     */
    public void writer();

}
