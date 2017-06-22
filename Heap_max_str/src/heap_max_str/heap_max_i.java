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
     * Recorrido en emplitud en un arbol binario. (BFS)
     *
     * @param p_tree Nodo raiz del arbol
     * @return 1: No se realizo el recorrido, 0: Recorrido con normalidad.
     */
    public int BFS(node p_tree);

    /**
     * Lectura de del archivo.
     */
    public void reader();

    /**
     * Escritura de el archivo.
     */
    public void writer();
}
