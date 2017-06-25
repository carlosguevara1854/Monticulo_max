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
 */
public class heap_max implements heap_max_i {

    //Arrays donde se almancenan las llaves a agregar y a eliminar.
    public int[] keys_add, keys_remove;
    //Nodo raiz del monticulo binario maximo.
    public node root;

    /**
     * Metodo de insercion de un nodo dentro del monticulo binario(maximo).
     * (forma simplificada)
     *
     * @param key Llave o campo clave del nodo que se va a crear.
     * @since v.1 22/06/2017
     */
    @Override
    public void insert_heap_max(int key) {
        root = insert_heap_max(root, key);
    }

    /**
     * Metodo de eliminacion de un nodo dentro del monticulo binario(maximo).
     * (forma simplificada)
     *
     * @param key Llave o campo del nodo que sera eliminado.
     * @since v.1 22/06/2017
     */
    @Override
    public void erase_heap(int key) {
        erase_heap(root, key);
    }
    
    /**
     * Grabar arbol en forma de diagrama en el archivo(.txt) de salida.
     */
    @Override
    public void writer(){
        printlevel(root);
    }

    /**
     * Metodo de eliminacion de un nodo.
     *
     * @param p_tree Nodo que contiene la llave buscada para eliminar.
     * @return Rama nueva creada que sustituira a dicho nodo.
     * @since v.1 22/06/2017
     */
    private node erase_heap(node p_tree) {
        //Si no tiene hijo izquiedo simplemente retorna la rama derecha.
        //p_right_subtree, p_left_subtree: sub-rama derecha, sub-rama izquierda.
        //p_max_node: nodo que el nodo mas a la derecha del sub-arbol izquierdo
        //de (p_tree).
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
        //A (p_max_node) Se le asigna el nodo mas a la derecha del sub-arbol izquierdo.
        node p_max_node = find_max(p_tree.getP_left());
        //Se va modelando la nueva rama a partir del nodo mas profundo.
        p_max_node.setP_left(remove_max_node(p_tree.getP_left(), p_max_node));
        //A el hijo derecho (p_max_node) se le asigna la rama derecha del nodo que
        //contiene la llave de eliminacion(p_tree).
        p_max_node.setP_rigth(p_tree.getP_rigth());
        return p_max_node;
    }

    /**
     * Metodo que realiza la pre-busqueda del nodo que sera eliminado. Realiza
     * la busqueda en forma preorden, posicionandose en el padre del nodo
     * buscado.(es decir una posicion anterior)
     *
     * @see erase_heap_max(node p_tree)
     * @param p_tree Raiz del arbol
     * @param key Lllave o clave buscada para eliminar.
     */
    private void erase_heap(node p_tree, int key) {
        //Se realiza las llamadas del metodo mientras que el nodo pasado sea
        //diferente de nulo.
        //Primero se verifica que el nodo raiz del arbol sea el buscado.
        if (p_tree != null) {
            if (root.getKey_value() == key) {
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
            //Tras la verificion anterior el arbol puede perder la propiedad del
            //monticulo, por tal razon luego de tal posible eliminacion se llama
            //al metodo que chequea la propiedad monticulo maximo(heap max).
            check(root, null);
            erase_heap(p_tree.getP_left(), key);
            erase_heap(p_tree.getP_rigth(), key);
        }
    }

    /**
     * Metodo de conteo de nodos.
     *
     * @param p_tree Nodo al cual se le realiza el conteo de nodos
     * @return Numero de nodos por la izquierda + nodos por la derecha.
     */
    private int counter(node p_tree) {
        if (p_tree == null) {
            return 0;
        } else {
            return 1 + counter(p_tree.getP_left()) + counter(p_tree.getP_rigth());
        }
    }

    /**
     * Metodo de contar la diferencia, entre el numero de nodos por la izquierda
     * y la derecha.
     *
     * @param p_tree Nodo al cual se le contabilizara la diferencia.
     * @return Numero de nodos izquierda - numero de nodos derecha.
     */
    private int diff_counter(node p_tree) {
        return counter(p_tree.getP_left()) - counter(p_tree.getP_rigth());
    }

    /**
     * Metodo se insercion en monticulo binario maximo. Realiza la insercion
     * recursiva hasta encontrar una posicion vacia (donde debe ser colocado).
     *
     * @param p_tree Raiz del arbol.
     * @param key LLave del nodo que sera creado.
     * @see diff_counter(node p_tree)
     * @see check(node p_tree, node prev)
     * @return Nodo creado.
     */
    private node insert_heap_max(node p_tree, int key) {
        if (p_tree == null) {
            //Se crea el nodo y se retorna.
            node new_node = new node(key);
            return new_node;
        }
        //Si la diferencia nodos es igual a cero(0), hace una llamada del metodo
        //por la izquierda, si es igual a uno(1) hace una llamada del metodo por la
        //derecha. De esta manera se van llenando el mismo nivel del arbol en forma
        //izquierda-derecha. Resultando de esta manera un arbol lleno/semi-lleno.
        //
        switch (diff_counter(p_tree)) {
            case 0:
                p_tree.setP_left(insert_heap_max(p_tree.getP_left(), key));
                //Se chequea la propieddad del monticulo maximo tras cada llamada
                //recursiva.
                check(p_tree, null);
                break;
            case 1:
                p_tree.setP_rigth(insert_heap_max(p_tree.getP_rigth(), key));
                check(p_tree, null);
                break;
        }
        return p_tree;
    }

    /**
     * Metodo que chequea la propiedad de un monticulo binario maximo. Visitando
     * primero el hijo izquierdo, el hijo derecho y luego la raiz.(posorden)
     * realizando los intercambios donde lo requiera.
     *
     * @see swap(node p_tree, node prev)
     * @param p_tree Nodo donde parte la verificacion.
     * @param prev Nodo padre(a la llamada del metodo e nulo).
     */
    private void check(node p_tree, node prev) {
        if (p_tree != null) {
            check(p_tree.getP_left(), p_tree);
            check(p_tree.getP_rigth(), p_tree);
            //La condicion (prev != null) solo aplica a la primera llamada del 
            //metodo.
            //Si el nodo hijo izquierdo o derecho(p_tree) es mayor al padre (prev)
            //Se hace un llamado a la funcion que le intercambia las claves.
            if ((prev != null) && (p_tree.getKey_value() > prev.getKey_value())) {
                swap(p_tree, prev);
            }
        }
    }

    /**
     * Metodo que intercambia las llavess entre nodos.
     *
     * @see check(node p_tree, node prev)
     * @param p_tree Nodo al cual se le asignara la llave de prev.
     * @param prev Nodo al cual se le asignara la llave de p_tree.
     */
    private void swap(node p_tree, node prev) {
        //Se almacena en una variable para hacer posible el cambio de llaves.
        int temp = p_tree.getKey_value();
        p_tree.setKey_value(prev.getKey_value());
        prev.setKey_value(temp);
    }

    /**
     * Metodo que devuelve el nodo mas a la derecha del subarbol izquierdo de un
     * nodo (p_tree). 
     * Ejemplo:
     * 7 
     * /\ 
     * 5 6 
     * /\ /\ 
     * 1 2 3 4
     * El valor retornado sera : 2 (si p_tree = 7)
     * @see erase_heap(node p_tree)
     * @param p_tree nodo padre
     * @return Nodo mas a la derecha.
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

    /** Metodo que modela la nueva rama a partir del nodo mas profundo.
     * Ejemplo:
     * p_max_node: 2
     * p_tree:
     * 5           5
     * /\    ->   /
     * 1 2       1
     * @see erase_heap(node p_tree)
     * @param p_tree Hijo izquierdo del nodo que contiene la clave a eliminar.
     * @param p_max_node Valor mas a la derecha del subarbol izquierdo.
     * @return Nueva rama.
     */
    private node remove_max_node(node p_tree, node p_max_node) {
        if (p_tree == null) {
            return null;
        }
        //Generalmente para cuando (p_tree) es igual a (p_max_node) y retorna su
        //hijo izquierdo.
        if (p_tree == p_max_node) {
            return p_max_node.getP_left();
        }
        p_tree.setP_rigth(remove_max_node(p_tree.getP_rigth(), p_max_node));
        return p_tree;
    }

    /**
     * Metodo de lectura del archivo de texto (.txt) de entrada.
     *
     */
    @Override
    public void reader() {
        try {
            File f = new File("monticulo_in.txt");
            if (f.exists()) {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String linea;
                String[] keys_1, keys_2;
                int i = 0;
                while (((linea = br.readLine()) != null) && (i < 2)) {
                    if (i == 0) {
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

    /**
     * Metodo que recorre por nivel el arbol.
     *
     * @param p_tree Nodo raiz del arbol.
     */
    private void printlevel(node p_tree) {
        if (root == null) {
            return;
        }
        Queue<node> queue_level = new LinkedList<>();
        queue_level.add(root);
        while (!queue_level.isEmpty()) {
            handleLevel(queue_level);
        }
    }

    /**
     * Escribir dentro del archivo de texto (.txt) de salida.
     *
     * @param queue_level Cola de nodos.
     * @see printlevel(node p_tee)
     */
    private void handleLevel(Queue<node> queue_level) {
        try {
            File f = new File("monticulo_out.txt");
            FileWriter fw;
            BufferedWriter bw;
            if (f.exists()) {
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
                bw.newLine();
            } else {
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
}
