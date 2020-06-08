package util.leetcode;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by songpanfei on 2020-05-26.
 */
public class BSTree<T extends Comparable<T>> {

    private Node<T> root;
    private AtomicInteger size = new AtomicInteger(0);

    public Node search(T data){
        return search(root, data);
    }

    public Node search(Node root, T data){
        if(data == null || root == null) return null;
        Node<T> p = root;
        while (p != null){
            int cmp = data.compareTo(p.data);
            if(cmp<0){
                p = p.getLeft();
            }else if(cmp>0){
                p = p.getRight();
            }else {
                return p;
            }
        }
        return null;
    }

    public boolean insert(T data){
        return insert(root, data);
    }

    public boolean insert(Node<T> root, T data){
        if(root == null){
            this.root = new Node(data);
            size.incrementAndGet();
            return true;
        }
        Node<T> node = new Node<>(data);
        Node<T> p = root;
        Node<T> parent = null;
        while (p != null){
            parent = p;
            int cmp = data.compareTo(p.data);
            if(cmp<0){
                p = p.getLeft();
            }else if(cmp>0){
                p = p.getRight();
            }else {
                return false;
            }
        }
        node.parent = parent;
        int cmp = data.compareTo(parent.data);
        if(cmp<0){
            parent.left = node;
            size.incrementAndGet();
        }else {
            parent.right = node;
            size.incrementAndGet();
        }
        return true;
    }

    public boolean del(T data){
        return del(root, data);
    }

    public boolean del(Node<T> root, T data){
        if(root == null) return false;
        Node<T> s = search(root, data);
        if(s == null) return false;
        Node<T> parent = s.parent;
        if(s.left == null && s.right == null){
            if(parent.left == s) parent.left = null;
            if(parent.right == s) parent.right = null;
            return true;
        }else if(s.left == null && s.right != null){
            if(parent.left == s) parent.left = s.right;
            if(parent.right == s) parent.right = s.right;
        }else if(s.left != null &&s.right == null){
            if(parent.left == s) parent.left = s.left;
            if(parent.right == s) parent.right = s.left;
        }else {
            Node<T> min = getSucceed(s.right);
            T tmp = min.data;
            min.data = s.data;
            s.data = tmp;
            del(root, min.data);
        }
        return true;
    }

    public Node getSucceed(Node<T> node){
        Node<T> p = node;
        while (p.left != null){
            p = p.left;
        }
        return p;
    }

    class Node<T extends Comparable<T>>{
        T data;
        Node left;
        Node right;
        Node parent;

        public Node(T data){
            this.data = data;
        }
        public Node(){}
        public Node(T data, Node left, Node right, Node parent){
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }


    }

    public static final void main(String args[]){
        BSTree tree = new BSTree();
        System.out.println(tree.insert(1));
        System.out.println(tree.insert(5));
        System.out.println(tree.insert(4));
        System.out.println(tree.insert(2));
        System.out.println(tree.insert(6));
        System.out.println(tree.insert(3));
        System.out.println(tree.search(5).data);
        System.out.println(tree.del(5));
        System.out.println(tree.search(5));
    }
}
