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
import java.util.Stack;

/**
 *
 * @author F.R.G
 * @see https://github.com/carlosguevara1854/Monticulo_max
 */
public class heap_max implements heap_max_i{

    //Arrays donde se almancenan las llaves a agregar y a eliminar.
    public int[] keys_add, keys_remove;
    //Nodo raiz del montículo binario máximo.
    public node root;

////Métodos de inserción en el montículo.  
    /**
     * Método de inserción de un nodo dentro del montículo binario máximo.
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
            return;//Corta la ejecución tras la asignación.
        }
        //Si es el caso que no este vació.
        //Se crea una cola de nodos.
        Queue<node> queue_level = new LinkedList<>();
        //Se añade la raíz del árbol.
        queue_level.add(root);
        //El ciclo mientras recorre el árbol en amplitud (BFS) con una cola,
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
        //montículo máximo, por ello se verifica, todo el árbol.
        check(root, null);
    }

////Métodos de eliminación en el montículo.
    /**
     * Método que realiza la eliminacion de un nodo hoja. (Nótese que el este
     * método solo se usa para eliminar los punteros hojas del último nivel del
     * árbol.)
     *
     * @param p_tree Raiz del árbol.
     * @param key Llave a eliminar.
     * @param enc Indicador si fue encontrado.
     */
    private void erase_heap(node p_tree, int key, boolean enc) {
        //Se verifica que el nodo pasado por parámetro sea distinto de nulo, y
        //que (enc) sea diferente de verdadero, es decir que no se haya encontrado.
        if ((p_tree != null) && (enc != true)) {
            //Primero se verifica que el nodo raíz del árbol sea el buscado.
            if (root.getKey_value() == key) {
                //Una vez encontrado le asigna a al nodo nulo.
                root = null;
            }
            if (p_tree.getP_left() != null) {
                if (p_tree.getP_left().getKey_value() == key) {
                    p_tree.setP_left(null);
                    enc = true;
                }
            }
            if (p_tree.getP_rigth() != null) {
                if (p_tree.getP_rigth().getKey_value() == key) {
                    p_tree.setP_rigth(null);
                    enc = true;
                }
            }
            //Recorre en búsqueda del nodo primero raíz, izquierda y luego derecha(preorden).
            erase_heap(p_tree.getP_left(), key, enc);
            erase_heap(p_tree.getP_rigth(), key, enc);
        }
    }

    /**
     * Método que realiza la eliminación de una llave en el montículo binario.
     * (realiza la eliminación sustituyendo la clave a eliminar por el último
     * nodo ingresado *cumpliendo la propiedad de completo/semicompleto*, luego
     * realiza el chequeo.)
     *
     * @param key LLave que sera eliminada.
     * @since v.1 22/06/2017 
     */
    @Override
    public void erase_heap(int key) {
        if (root != null) {
            //Se crea una pila, que almacenara los nodos del último nivel.
            Stack<node> stack = new Stack<>();
            //Se llena la cola con los nodos del último nivel, pasandole como 
            //parámetro la altura del árbol.
            under_level(root, 0, height(root), stack);
            //Si el nodo a eliminar es igual al tope de la pila de nodos.
            //Simplemente se manda a eliminar como una hoja.
            //Nótese que tras la eliminación no se reliza el chequeo del árbol, 
            //por tratarse de una hoja.
            if (under_node(stack) == key) {
                erase_heap(root, under_node(stack), false);//Se asume que no se ha encontrado.
            } else {
                //Si no es así, se manda a eliminar(como una hoja) el tope de la pila. 
                erase_heap(root, under_node(stack), false);
                //Se sustituye el nodo a eliminar por la llave del tope de la pila.
                change(key, root, under_node(stack));
                //Quedando de esta manera un árbol completo/semicompleto. (inserción inversa)
            }
        } else {
            System.err.println("El motículo esta vació. No se puede realizar la eliminación.");
        }
    }

    /**
     * Método que busca la llave a sustutuir y realiza el cambio de llaves.
     *
     * @param key LLave que sera sustituida.
     * @param p_tree Raíz del árbol.
     * @param new_key Nueva llave a sustituir.
     * @since v.1 22/06/2017
     */
    private void change(int key, node p_tree, int new_key) {
        if (p_tree != null) {
            if (p_tree.getKey_value() == key) {
                p_tree.setKey_value(new_key);
            }
            //Cuando hace el cambio de claves puede que inhabilite la propiedad
            //de montículo(padres mayores). Por tal razón se hace un chequeo del
            //arbol tras cada llamada.
            check(p_tree, null);
            //Se realiza primero raíz, izquierda luego derecha. (preorden)
            change(key, p_tree.getP_left(), new_key);
            change(key, p_tree.getP_rigth(), new_key);
        }
    }

    /**
     * Método que recibe como parametro una pila y devuelve la llave del nodo
     * del tope.
     *
     * @param stack Pila de nodos.
     * @return LLave del tope de la pila.
     * @since v.1 22/06/2017
     */
    private int under_node(Stack<node> stack) {
        return stack.peek().getKey_value();
    }

    /**
     * Método que apila los nodos del último nivel.
     *
     * @param p_tree Nodo ráiz.
     * @param level Nivel actual(la raíz del árbol es nivel cero(0)).
     * @param height Altura del árbol.
     * @param stack Pila de nodos.
     * @since v.1 22/06/2017
     */
    private void under_level(node p_tree, int level, int height, Stack<node> stack) {
        if (p_tree != null) {
            //Se aumenta 1 al nivel, y se recorre primero izquierda, raiz y luego
            //derecha. (inorden)
            under_level(p_tree.getP_left(), level + 1, height, stack);
            //Si el nivel actual es igual a la altura del árbol, apila el nodo
            //es decir apila los nodos del ultimo nivel.
            if (level == height) {
                stack.add(p_tree);
            }
            under_level(p_tree.getP_rigth(), level + 1, height, stack);
        }
    }

    /**
     * Método que devuelve la altura del arbol. (nodo raiz = altura 0)
     *
     * @param p_tree
     * @return Altura del árbol binario.
     * @since v.1 22/06/2017
     */
    private int height(node p_tree) {
        if (p_tree == null) {
            return -1;
        } else {
            //Máximo entre ambas alturas.
            return (Math.max(height(p_tree.getP_left()), height(p_tree.getP_rigth())) + 1);
        }
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
     * @param p_tree Nodo al cual se le asignará la llave de prev.
     * @param prev Nodo al cual se le asignará la llave de p_tree.
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
                //La variable (i), para saber en que posisión del archivo se 
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
