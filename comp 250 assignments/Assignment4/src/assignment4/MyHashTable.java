package assignment4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        
    	this.numBuckets = initialCapacity;
    	this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>(initialCapacity);
    	for (int i=0;i<initialCapacity;i++) {
    		LinkedList<HashPair<K,V>> p=new LinkedList<HashPair<K,V>>();
    		this.buckets.add(p);
    	}
    	//System.out.println(this.buckets.size());
    	this.numEntries=0;
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets vairable. Usefull for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
    	V temp1=null;
    	if (!(key.equals(null))) {
	        int val = hashFunction(key); // the index in the array list where the new pair will be put
	        LinkedList<HashPair<K,V>> pack= this.getBuckets().get(val); // reference to the linked list at that index
	        
	        for (int i=0; i < pack.size() ; i++) {
	        	if (pack.get(i).getKey().equals(key)) {
	        		temp1 = pack.get(i).getValue();
	        		pack.get(i).setValue(value); // if the key youre putting is already in the linked list, replace its value
	        		//this.numEntries++;
	        		if ((double)(this.size())/(double)(this.numBuckets()) > MAX_LOAD_FACTOR) { // if the num entries/ numbuckets is more than 0.75, rehash
	        			this.rehash();
	        		}
	        		return temp1; 
	        	}
	        }
	        pack.add(new HashPair<K,V>(key,value)); //put the value if the key didnt already exist 
	        this.numEntries++;
	        if ((double)(this.size())/(double)(this.numBuckets()) > MAX_LOAD_FACTOR) { // if the num entries/ numbuckets is more than 0.75, rehash
	        	this.rehash();
	        }
    	}
        return temp1;
        
    }
    
    
    /**
     * Get the value corresponding to key. Expected average runtime = O(1)
     */
    
    public V get(K key) {
    	
        int val = hashFunction(key);
        LinkedList<HashPair<K,V>> pack= this.getBuckets().get(val);
        for (int i=0; i<pack.size();i++) {
        	if (key.equals(pack.get(i).getKey())) {
        		return pack.get(i).getValue();
        	}
        }
        System.out.println("this key is not in the list already");
        return null;
        
    }
    
    /**
     * Remove the HashPair correspoinding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
        int val = hashFunction(key);
        LinkedList<HashPair<K,V>> pack = this.getBuckets().get(val);
        LinkedList<HashPair<K,V>> tempL = new LinkedList<HashPair<K,V>>();
        
        V tempV = null;
        
        while(pack.size()>0) {
        	if (pack.getFirst().getKey().equals(key)) {
        		tempV = pack.removeFirst().getValue();
        		this.numEntries--;
        		
        	}else {
        		HashPair<K,V> temp3 = pack.removeFirst();
        		tempL.add(temp3);
        		
        	}	
        }
        
        this.buckets.set(val,tempL);
        return tempV;
    }
    
    // Method to double the size of the hashtable if load factor increases
    // beyond MAX_LOAD_FACTOR.
    // Made public for ease of testing.
    
    public void rehash() {
        	MyHashTable<K,V> rTemp = new MyHashTable<K,V>(this.numBuckets()*2) ;
        	//System.out.println(rTemp.numBuckets());
        	//ArrayList<LinkedList<HashPair<K,V>>> rTemp = new ArrayList<LinkedList<HashPair<K,V>>>(this.numBuckets * 2);
        	
        	for (int i = 0; i < this.numBuckets(); i++) {
        		if (this.getBuckets().get(i).size()==0) {
        			continue;
        		}else {
        			for (int l=0; l < this.getBuckets().get(i).size();l++) {
        				rTemp.put(this.getBuckets().get(i).get(l).getKey(),this.getBuckets().get(i).get(l).getValue());
        			}
        		}
        	}
        	this.buckets=rTemp.getBuckets();
        	this.numEntries=rTemp.size();
        	this.numBuckets=rTemp.numBuckets();
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     */
    
    public ArrayList<K> keys() {
    	ArrayList<K> tempK = new ArrayList<K>();
        for (LinkedList<HashPair<K,V>> li: this.getBuckets()) {
        	for (int p=0; p<li.size();p++) {
        		tempK.add(li.get(p).getKey()); // p is very small always so should be fine
        	}
        }
        return tempK;
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(n)
     */
    public ArrayList<V> values() {
    	
    	MyHashTable<V,V> tempo = new MyHashTable<V,V>(10);
    	
    	for (HashPair<K,V> pair4: this) {
    		tempo.put(pair4.getValue(), pair4.getValue());
    	}
    	
       
        return tempo.keys();
    }
    
    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }
    
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        private LinkedList<HashPair<K,V>> entries = new LinkedList<HashPair<K,V>>();
        
        private MyHashIterator() { // the constructor basically puts all the entries of a hashtable into a single linked list
        	
            for (LinkedList<HashPair<K,V>> li: buckets) {
            	for(int p=0; p<li.size();p++) {
            		entries.add(li.get(p));
            		
            	}
            }
        }
        
        @Override
        public boolean hasNext() { // checks if theres more than one entry
            if (entries.size()>0) {
            	return true;
            }
            return false;
        }
        
        @Override
        public HashPair<K,V> next() {
            
            return entries.removeFirst(); // returns the head, then the next element becomes the head
        }
        
    }
}
