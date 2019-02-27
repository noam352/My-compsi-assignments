package assignment4;

import java.util.ArrayList;

public class MusicStore {
	
    private MyHashTable<String,Song> storeT;
    
    private MyHashTable<String,ArrayList<Song>> storeA;
    private MyHashTable<Integer,ArrayList<Song>> storeY;
    
    private ArrayList<ArrayList<Song>> aList;
    private ArrayList<ArrayList<Song>> yList;
    
    
    
    
    public MusicStore(ArrayList<Song> songs) {
    	
    	//hash table with title keys
    	this.storeT = new MyHashTable<String,Song>(songs.size());
        for (Song songs2: songs) {
        	this.storeT.put(songs2.getTitle(), songs2);	
        }
        
        // hash table with artist keys
        this.aList=new ArrayList<ArrayList<Song>>();
        ArrayList<Song> g = new ArrayList<Song>();
        this.aList.add(g);
        this.aList.get(0).add(songs.get(0));
        for (int p = 1; p<songs.size();p++) {
        	
        	for (int c = 0;c < this.aList.size();c++) {
        		
        		if (!(songs.get(p).getArtist().equals(this.aList.get(c).get(0).getArtist())) && c==(this.aList.size()-1)) {
        			ArrayList<Song> q = new ArrayList<Song>();
        	        this.aList.add(q);
        			this.aList.get(c+1).add(songs.get(p));
        			break;
        		}else if (!(songs.get(p).getArtist().equals(this.aList.get(c).get(0).getArtist()))){
        			continue;
        		}else {
        			this.aList.get(c).add(songs.get(p));
        			break;
        		}
    
        	}
        	
        }
        this.storeA = new MyHashTable<String,ArrayList<Song>>(this.aList.size());
        for (ArrayList<Song> templ : this.aList) {
        	this.storeA.put(templ.get(0).getArtist(),templ);
        }
        
        //hash table with array list of of songs sepperated by years
        this.yList=new ArrayList<ArrayList<Song>>();
        ArrayList<Song> o = new ArrayList<Song>();
        this.yList.add(o);
        this.yList.get(0).add(songs.get(0));
        for (int p = 1; p<songs.size();p++) {
        	
        	for (int c = 0;c < this.yList.size();c++) {
        		
        		if (!(songs.get(p).getYear()==(this.yList.get(c).get(0).getYear())) && c==(this.yList.size()-1)) {
        			ArrayList<Song> q = new ArrayList<Song>();
        	        this.yList.add(q);
        			this.yList.get(c+1).add(songs.get(p));
        			break;
        		}else if (!(songs.get(p).getYear()==(this.yList.get(c).get(0).getYear()))){
        			continue;
        		}else {
        			this.yList.get(c).add(songs.get(p));
        			break;
        		}
    
        	}
        	
        }
        this.storeY = new MyHashTable<Integer, ArrayList<Song>>(this.yList.size());
        for (ArrayList<Song> templ : this.yList) {
        	this.storeY.put(templ.get(0).getYear(),templ);
        }
        
        
    }
    
    
    /**
     * Add Song s to this MusicStore
     */
    public void addSong(Song s) {
        this.storeT.put(s.getTitle(), s); // normal add for the regular hash table
        
        if (this.storeA.get(s.getArtist()) == null) { //add to the hashtable with aritst keys
        	ArrayList<Song> sL = new ArrayList<Song>();
        	sL.add(s);
        	this.storeA.put(s.getArtist(), sL);
        }else {
        	this.storeA.get(s.getArtist()).add(s);
        }
        
        if (this.storeY.get(s.getYear()) == null) { //adding to the hashtable of year keys
        	ArrayList<Song> sY = new ArrayList<Song>();
        	sY.add(s);
        	this.storeY.put(s.getYear(), sY);
        }else {
        	this.storeY.get(s.getYear()).add(s);
        }
        
    }
    
    /**
     * Search this MusicStore for Song by title and return any one song 
     * by that title 
     */
    public Song searchByTitle(String title) {
        
        return this.storeT.get(title); 
    }
    
    /**
     * Search this MusicStore for song by `artist' and return an 
     * ArrayList of all such Songs.
     */
    public ArrayList<Song> searchByArtist(String artist) {
        
        return this.storeA.get(artist);
    }
    /**
     * Search this MusicSotre for all songs from a `year'
     *  and return an ArrayList of all such  Songs  
     */
    public ArrayList<Song> searchByYear(Integer year) {
        
        return this.storeY.get(year);
        
    }
}
