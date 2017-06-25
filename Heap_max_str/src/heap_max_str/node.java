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
public class node {

    //Hijo izquierdo e hijo derecho.
    private node p_left, p_rigth;
    //LLeva o clasve de cada nodo.
    private int key_value;

    /**
     * Constructor de la clase.
     *
     * @param key Clave ó llave del nodo.
     * @since v.1 03/06/2017
     */
    public node(int key) {
        this.p_left = null;
        this.p_rigth = null;
        this.key_value = key;
    }

    /**
     * 2do Constructor de la clase.
     *
     * @since v.1 16/06/2017
     */
    public node() {
        this.p_left = null;
        this.p_rigth = null;
    }

    /**
     * Obtener hijo izquierdo.
     *
     * @return Nodo izquierdo.
     * @since v.1 03/06/2017
     */
    public node getP_left() {
        return p_left;
    }

    /**
     * Asignar al hijo izquierdo.
     *
     * @param p_left Nodo el cual sera asignado.
     * @since v.1 03/06/2017
     */
    public void setP_left(node p_left) {
        this.p_left = p_left;
    }

    /**
     * Obtener hijo derecho.
     *
     * @return Nodo derecho.
     * @since v.1 03/06/2017
     */
    public node getP_rigth() {
        return p_rigth;
    }

    /**
     * Asignar al hijo derecho.
     *
     * @param p_rigth Nodo el cual sera asignado.
     * @since v.1 03/06/2017
     */
    public void setP_rigth(node p_rigth) {
        this.p_rigth = p_rigth;
    }

    /**
     * Obtener la llave(key).
     *
     * @return TDA del nodo.
     * @since v.1 03/06/2017
     */
    public int getKey_value() {
        return key_value;
    }

    /**
     * Asignar a la llave.
     *
     * @param key_value Llave el cual sera asignado.
     * @since v.1 03/06/2017
     */
    public void setKey_value(int key_value) {
        this.key_value = key_value;
    }
}

