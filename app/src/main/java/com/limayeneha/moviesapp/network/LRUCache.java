package com.limayeneha.moviesapp.network;
import java.util.Hashtable;

public class LRUCache {

    class Node {
        int key;
        int val;
        Node previous;
        Node next;
    }

    private void addNode(Node node) {
        node.previous = head;
        node.next = head.next;

        head.next.previous = node;
        head.next = node;
    }

    private void removeNode(Node node){
        Node prev = node.previous;
        Node next = node.next;

        prev.next = next;
        next.previous = prev;
    }

    private void moveToHead(Node node){
        removeNode(node);
        addNode(node);
    }

    private Node popTail() {
        Node res = tail.previous;
        removeNode(res);
        return res;
    }

    private Hashtable<Integer, Node> cache =
            new Hashtable<Integer, Node>();
    private int size;
    private int capacity;
    private Node head, tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;

        head = new Node();
        // head.previous = null;

        tail = new Node();
        // tail.next = null;

        head.next = tail;
        tail.previous = head;
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) return -1;

        moveToHead(node);

        return node.val;
    }

    public boolean isValid(int key) {
        Node node = cache.get(key);
        if(node == null) return false;

        return true;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);

        if(node == null) {
            Node newNode = new Node();
            newNode.key = key;
            newNode.val = value;

            cache.put(key, newNode);
            addNode(newNode);

            ++size;

            if(size > capacity) {
                Node tail = popTail();
                cache.remove(tail.key);
                --size;
            }
        } else {
            node.val = value;
            moveToHead(node);
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,val);
 */
