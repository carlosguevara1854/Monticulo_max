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
public class heap_max {

    public int[] keys_add, keys_remove;
    public node root;

    public void insert_heap_max(int key) {
        root = insert_heap_max(root, key);
    }

    public void erase_heap(int key) {
        if (root.getKey_value() == key) {
            root = erase_heap(root);
        } else {
            erase_heap(root, key, false);
        }
    }

    public int BFS(node p_tree) {
        if (p_tree == null) {
            return 1;
        }
        Queue<node> queue_level = new LinkedList<>();
        queue_level.clear();
        queue_level.add(p_tree);
        while (!(queue_level.isEmpty())) {
            node temp = queue_level.remove();
            System.out.print(temp.getKey_value() + " ");
            if (temp.getP_left() != null) {
                queue_level.add(temp.getP_left());
            }
            if (temp.getP_rigth() != null) {
                queue_level.add(temp.getP_rigth());
            }
        }
        return 0;
    }

    private node erase_heap(node p_tree) {
        if (p_tree.getP_left() == null) {
            node p_right_subtree = p_tree.getP_rigth();
            check(p_right_subtree, null);
            return p_right_subtree;
        }
        if (p_tree.getP_rigth() == null) {
            node p_left_subtree = p_tree.getP_left();
            check(p_left_subtree, null);
            return p_left_subtree;
        }
        node p_max_node = find_max(p_tree.getP_left());
        p_max_node.setP_left(remove_max_node(p_tree.getP_left(), p_max_node));
        p_max_node.setP_rigth(p_tree.getP_rigth());
        check(p_max_node, null);
        return p_max_node;
    }

    private void erase_heap(node p_tree, int key, boolean enc) {
        if ((p_tree != null) && (enc != true)) {
            if (p_tree.getP_left() != null) {
                if (p_tree.getP_left().getKey_value() == key) {
                    p_tree.setP_left(erase_heap(p_tree.getP_left()));
                    enc = true;
                }
            }
            if (p_tree.getP_rigth() != null) {
                if (p_tree.getP_rigth().getKey_value() == key) {
                    p_tree.setP_rigth(erase_heap(p_tree.getP_rigth()));
                    enc = true;
                }
            }
            erase_heap(p_tree.getP_left(), key, enc);
            erase_heap(p_tree.getP_rigth(), key, enc);
        }
    }

    private int counter(node p_tree) {
        if (p_tree == null) {
            return 0;
        } else {
            return 1 + counter(p_tree.getP_left()) + counter(p_tree.getP_rigth());
        }
    }

    private int diff_counter(node p_tree) {
        return counter(p_tree.getP_left()) - counter(p_tree.getP_rigth());
    }

    private node insert_heap_max(node p_tree, int key) {
        if (p_tree == null) {
            node new_node = new node(key);
            return new_node;
        }
        switch (diff_counter(p_tree)) {
            case 0:
                p_tree.setP_left(insert_heap_max(p_tree.getP_left(), key));
                check(p_tree, null);
                break;
            case 1:
                p_tree.setP_rigth(insert_heap_max(p_tree.getP_rigth(), key));
                check(p_tree, null);
                break;
        }
        return p_tree;
    }

    private void check(node p_tree, node prev) {
        if (p_tree != null) {
            check(p_tree.getP_left(), p_tree);
            check(p_tree.getP_rigth(), p_tree);
            if ((prev != null) && (p_tree.getKey_value() > prev.getKey_value())) {
                swap(p_tree, prev);
            }
        }
    }

    private void swap(node p_tree, node prev) {
        int temp = p_tree.getKey_value();
        p_tree.setKey_value(prev.getKey_value());
        prev.setKey_value(temp);
    }

    private node find_max(node p_tree) {
        if (p_tree == null) {
            return null;
        }
        if (p_tree.getP_rigth() == null) {
            return p_tree;
        }
        return find_max(p_tree.getP_rigth());
    }

    private node remove_max_node(node p_tree, node p_max_node) {
        if (p_tree == null) {
            return null;
        }
        if (p_tree == p_max_node) {
            return p_max_node.getP_left();
        }
        p_tree.setP_rigth(remove_max_node(p_tree.getP_rigth(), p_max_node));
        return p_tree;
    }

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

    public void writer() {
        try {
            File f = new File("monticulo_out.txt");
            FileWriter fw;
            BufferedWriter bw;
            if (f.exists()) {
                fw = new FileWriter(f, true);
                bw = new BufferedWriter(fw);
                bw.newLine();
            } else {
                fw = new FileWriter(f);
                bw = new BufferedWriter(fw);
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
