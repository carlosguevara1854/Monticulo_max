/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heap_max_str;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author F.R.G
 * @see https://github.com/carlosguevara1854/Monticulo_max
 */
public class heap_max implements heap_max_i {

    //Arrays donde se almancenan las llaves a agregar y a eliminar.
    public int[] keys_add, keys_remove;
    //Nodo raiz del montículo binario máximo.
    public node root;

////Métodos de inserción en el montículo.  
    /**
     * Método de insercion de un nodo dentro del montículo binario(máximo).
     *
     * @param key Llave o campo clave del nodo que se va a crear.
     * @since v.1 22/06/2017
     */
    @Override
    public void insert_heap_max(int key) {
        //Se verifica si el árbol esta vacio.
        if (root == null) {
            //Si es así se crea el nodo y se le asigna a la raíz.
            node new_node = new node(key);
            root = new_node;
            return;
        }
        //Si es el caso que no este vacio.
        //Se crea una cola de nodos.
        Queue<node> queue_level = new LinkedList<>();
        //Se añade la raíz del arbol.
        queue_level.add(root);
        //El ciclo mientras recorre el arbol en amplitud (BFS) con una cola,
        //buscando la posición donde debe ser colocado el nodo. 
        //(de izquierda a derecha en un mismo nivel)
        while (true) {
            node n = queue_level.remove();
            if (n.getP_left() == null) {
                //Se crea el nuevo nodo, y se le asigna a su hijo izquierdo.
                node new_node = new node(key);
                n.setP_left(new_node);
                //Si se agrega el nodo deja de iterar.
                break;//Se sale del ciclo mientras.
            } else {
                //Se encola su hijo izquiero.
                queue_level.add(n.getP_left());
            }
            if (n.getP_rigth() == null) {
                node new_node = new node(key);
                n.setP_rigth(new_node);
                break;
            } else {
                queue_level.add(n.getP_rigth());
            }
        }
        //Luego de la inserción del nodo este puede o no cumplir la propiedad de
        //montículo maximo, por ello se verifica, todo el árbol tras la insercion.
        check(root, null);
    }
    
////Métodos de eliminación en el montículo.
    /**
     * Método de eliminación de un nodo dentro del montículo binario(máximo).
     * (forma simplificada)
     *
     * @param key Llave o campo del nodo que sera eliminado.
     * @since v.1 22/06/2017
     */
    @Override
    public void erase_heap(int key) {
        erase_heap(root, key, false);//Se asume que no se ha encontrado.
    }

    /**
     * Método que retorna el nodo más profundo de la derecha.
     *
     * Ejemplo: 
     * 7 
     * 5 6 
     * 1 2 3 4 
     * El valor retornado sera : 2 (si p_tree = 7)
     *
     * @see erase_heap(node p_tree)
     * @param p_tree Nodo padre
     * @return Nodo más profundo de la derecha.
     * @since v.1 22/06/2017
     */
    private node find_max(node p_tree) {
        if (p_tree == null) {
            return null;
        }
        if (p_tree.getP_rigth() == null) {
            return p_tree;
        }
        return find_max(p_tree.getP_rigth());
    }

    /**
     * Método que realiza la pre-busqueda del nodo que sera eliminado. Realiza
     * la busqueda en forma preorden, posicionandose en el padre del nodo
     * buscado.(es decir una posición anterior)
     *
     * @see erase_heap_max(node p_tree)
     * @param p_tree Raíz del árbol
     * @param key Llave o clave buscada para eliminar.
     * @since v.1 22/06/2017
     */
    private void erase_heap(node p_tree, int key, boolean enc) {
        //Se realiza las llamadas del método mientras que el nodo pasado sea
        //diferente de nulo y enc sea distinto de true(que no se haya encontrado)
        if ((p_tree != null) && (enc != true)) {
            //Primero se verifica que el nodo raíz del árbol sea el buscado.
            if (root.getKey_value() == key) {
                //Una vez encontrado se se llama al método que modela la nueva rama
                //omitiendo dicho valor.
                root = erase_heap(root);
            }
            if (p_tree.getP_left() != null) {
                if (p_tree.getP_left().getKey_value() == key) {
                    //Una vez encontrado se modifica dicho rama/hoja.
                    p_tree.setP_left(erase_heap(p_tree.getP_left()));
                }
            }
            if (p_tree.getP_rigth() != null) {
                if (p_tree.getP_rigth().getKey_value() == key) {
                    p_tree.setP_rigth(erase_heap(p_tree.getP_rigth()));
                }
            }
            //Tras la verifición(eliminación) anterior el árbol puede perder la 
            //propiedad del montículo, por tal razon luego de tal posible 
            //eliminación se llama al método que chequea la propiedad montículo 
            //maximo(heap max).
            check(root, null);
            erase_heap(p_tree.getP_left(), key, enc);
            erase_heap(p_tree.getP_rigth(), key, enc);
        }
    }

    /**
     * Método de eliminación de un nodo.
     *
     * @param p_tree Nodo que contiene la llave buscada para eliminar.
     * @return Rama nueva creada que sustituirá a dicho nodo.
     * @see erase_heap(node p_tree, int key)
     * @since v.1 22/06/2017
     */
    private node erase_heap(node p_tree) {
        //Si no tiene hijo izquiedo simplemente retorna la rama derecha.
        //p_right_subtree, p_left_subtree: sub-rama derecha, sub-rama izquierda.
        //p_deep_node: nodo que el nodo más a la derecha del sub-arbol izquierdo
        //de (p_tree)(más profundo de la derecha).
        if (p_tree.getP_left() == null) {
            node p_right_subtree = p_tree.getP_rigth();
            return p_right_subtree;
        }
        if (p_tree.getP_rigth() == null) {
            node p_left_subtree = p_tree.getP_left();
            return p_left_subtree;
        }
        //En dicho caso que se llegue hasta este punto quiere decir que tanto
        //tiene hijos tanto derecho como izquierdo.
        //A (p_deep_node) Se le asigna el nodo más a la derecha del sub-arbol izquierdo.
        node p_deep_node = find_max(p_tree.getP_left());
        //Se modela la rama izquierda del nodo más profundo.
        p_deep_node.setP_left(remove_deep_node(p_tree.getP_left(), p_deep_node));
        //A el hijo derecho (p_deep_node) se le asigna la rama derecha del nodo que
        //contiene la llave de eliminacin(p_tree).
        p_deep_node.setP_rigth(p_tree.getP_rigth());
        //Retorna la rama creada a partir de el nodo más profundo de la derecha
        //Notese que dicha rama modelada, no esta cumpliendo la propiedad de montículo
        //máximo esta propiedad se corrige tras la eliminación en el método de 
        //prebusqueda erase_heap(node p_tree, int key).
        return p_deep_node;
    }

    /**
     * Método que modela la nueva rama izquierda del nodo más profundo.
     *
     *
     * Ejemplo: 
     * p_max_node: 2 
     * p_tree: 5 
     * 5       5 
     * 1 2 --> 1
     *
     * @see erase_heap(node p_tree)
     * @param p_tree Hijo izquierdo del nodo que contiene la clave a eliminar.
     * @param p_deep_node Valor más a la derecha del subarbol izquierdo.
     * @return Nueva rama.
     * @since v.1 22/06/2017
     */
    private node remove_deep_node(node p_tree, node p_deep_node) {
        if (p_tree == null) {
            return null;
        }
        //Generalmente para cuando (p_tree) es igual a (p_deep_node) y retorna su
        //hijo izquierdo.
        if (p_tree == p_deep_node) {
            return p_deep_node.getP_left();
        }
        //Se conserva (p_deep_node) y se van visitando los hijos derechos de
        //(p_tree) hasta llegar (p_deep_node). 
        p_tree.setP_rigth(remove_deep_node(p_tree.getP_rigth(), p_deep_node));
        return p_tree;
    }

////Métodos de verificación de propiedad de montículo.
    /**
     * Método que chequea la propiedad de un montículo binario máximo. Visitando
     * primero el hijo izquierdo, el hijo derecho y luego la raíz.(posorden)
     * realizando los intercambios donde lo requiera.
     *
     * @see swap(node p_tree, node prev)
     * @param p_tree Nodo donde parte la verificación.
     * @param prev Nodo padre(a la llamada del método es nulo).
     * @since v.1 22/06/2017
     */
    private void check(node p_tree, node prev) {
        if (p_tree != null) {
            check(p_tree.getP_left(), p_tree);
            check(p_tree.getP_rigth(), p_tree);
            //La condición (prev != null) solo aplicará a la primera llamada del 
            //método.
            //Si el nodo hijo izquierdo o derecho(p_tree) es mayor al padre (prev)
            //Se hace un llamado a la función que le intercambia las claves.
            if ((prev != null) && (p_tree.getKey_value() > prev.getKey_value())) {
                swap(p_tree, prev);
            }
        }
    }

    /**
     * Método que intercambia las llaves entre nodos.
     *
     * @see check(node p_tree, node prev)
     * @param p_tree Nodo al cual se le asignara la llave de prev.
     * @param prev Nodo al cual se le asignara la llave de p_tree.
     * @since v.1 22/06/2017
     */
    private void swap(node p_tree, node prev) {
        //Se almacena en una variable para hacer posible el cambio de llaves.
        int temp = p_tree.getKey_value();
        p_tree.setKey_value(prev.getKey_value());
        prev.setKey_value(temp);
    }

////Métodos de manejo de archivo de entrada y de salida.
    /**
     * Método que recorre por nivel el árbol.
     *
     * @param p_tree Nodo raíz del árbol.
     * @see handleLevel(Queue<node> queue_level)
     * @since v.1 22/06/2017
     */
    private void printlevel(node p_tree) {
        if (p_tree == null) {
            return;
        }
        //Se crea un TDA cola, para el recorrido.
        Queue<node> queue_level = new LinkedList<>();
        //Se encola el primer elemento.(raiz)
        queue_level.add(p_tree);
        while (!queue_level.isEmpty()) {
            handleLevel(queue_level);
        }
    }

    /**
     * Método de escritura del archivo de salida. (monticulo_out.txt)
     *
     * @param queue_level Cola de nodos.
     * @see printlevel(node p_tee)
     * @since v.1 22/06/2017
     */
    private void handleLevel(Queue<node> queue_level) {
        try {
            File f = new File("monticulo_out.txt");
            FileWriter fw;
            BufferedWriter bw;
            //Se comprueba la existencia del archivo de texto
            if (f.exists()) { //En caso de que exista.
                fw = new FileWriter(f, true);
                bw = new BufferedWriter(fw);
                int size = queue_level.size();
                for (int i = 0; i < size; i++) {
                    node temp = queue_level.poll();
                    if (temp != null) {
                        bw.write(temp.getKey_value() + " ");
                        queue_level.add(temp.getP_left());
                        queue_level.add(temp.getP_rigth());
                    }
                }
                //Salto de linea.
                bw.newLine();
            } else { // En el caso que el archivo no exista.
                fw = new FileWriter(f);
                bw = new BufferedWriter(fw);
                int size = queue_level.size();
                for (int i = 0; i < size; i++) {
                    node temp = queue_level.poll();
                    if (temp != null) {
                        bw.write(temp.getKey_value() + " ");
                        queue_level.add(temp.getP_left());
                        queue_level.add(temp.getP_rigth());
                    }
                }
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Grabar árbol en forma de diagrama en el archivo(.txt) de salida.
     *
     * @since v.1 22/06/2017
     *
     */
    @Override
    public void writer() {
        printlevel(root);
    }

    /**
     * Método de eliminar archivo.
     *
     * @param name Nombre del archivo a eliminar.
     * @since v.1 22/06/2017
     */
    @Override
    public void erasefile(String name) {
        File f = new File(name);
        f.delete();
    }

    /**
     * Método de lectura del archivo de texto (.txt) de entrada.
     *
     * @since v.1 22/06/2017
     */
    @Override
    public void reader() {
        try {
            File f = new File("monticulo_in.txt");
            if (f.exists()) {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                //linea: Es la variable que se usa para la lectura de la línea
                //de cada registro del archivo.
                //keys_1: LLaves que se agregarán.
                //keys_2: Llaves que se eliminarán.
                String linea;
                String[] keys_1, keys_2;
                //La variable (i), para saber en que posisión del registro se 
                //encuentra.
                int i = 0;
                while (((linea = br.readLine()) != null) && (i < 2)) {
                    if (i == 0) {
                        //Se divide la cadena con el criterio de partición (" ").
                        keys_1 = linea.split(" ");
                        this.keys_add = new int[keys_1.length];
                        for (int j = 0; j < keys_1.length; j++) {
                            this.keys_add[j] = Integer.parseInt(keys_1[j]);
                        }
                    } else {
                        keys_2 = linea.split(" ");
                        this.keys_remove = new int[keys_2.length];
                        for (int j = 0; j < keys_2.length; j++) {
                            this.keys_remove[j] = Integer.parseInt(keys_2[j]);
                        }
                    }
                    i++;
                }
            } else {
            }
        } catch (IOException e) {
            System.err.println(e);
            System.err.println("El archivo de entrada no existe.");
        }
    }
}
