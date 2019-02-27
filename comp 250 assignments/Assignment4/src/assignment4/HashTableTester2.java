package assignment4;
import java.util.ArrayList;
/**
 * Class for testing your implementation of the HashTable class.
 */
public class HashTableTester2 {
    
    /**
     * Returns a list of songs to use for testing the hash table.
     * @return A list of songs to use for testing the hash table
     */
    private static ArrayList<Song> initSongList() {
        ArrayList<Song> songs = new ArrayList<Song>();
        songs.add(new Song("Le Premier Bonheur du Jour", "Os Mutantes", 1968));
        songs.add(new Song("Stretch Out And Wait", "The Smiths", 1987));
        songs.add(new Song("Scream", "Black Flag", 1984));
        songs.add(new Song("Europe, After the Rain", "Max Richter", 2002));
        songs.add(new Song("Why Are You Looking Grave?", "Mew", 2005));
        songs.add(new Song("Fallen Angel", "King Crimson", 1974));
        songs.add(new Song("The Song Of Ice and Fire", "Rhaegar", 1974));
        songs.add(new Song("The Bear and The Maiden Fair", "Tom O'Sevens", 2011));
        songs.add(new Song("The Dornishman's Wife", "Tom O'Sevens", 2011));
        songs.add(new Song("The Burning of the Ships", "Tom O'Sevens", 2011));
        songs.add(new Song("Jenny of Oldstones", "Tom O'Sevens", 2011));
        songs.add(new Song("King Without courage", "Tom O'Sevens", 2011)); 
        songs.add(new Song("The Mother's Tears", "Tom O'Sevens", 2011));
        songs.add(new Song("The Rains of Castermere", "Tom O'Sevens", 2011));
        songs.add(new Song("Two hearts that beat as one", "Tom O'Sevens", 2011));
        songs.add(new Song("Wolf in the Night", "Tom O'Sevens", 2011));
        songs.add(new Song("Fallen Leaves", "Marillion", 2002));
        songs.add(new Song("Flowers of springs", "Marillion", 2002));
        songs.add(new Song("On a Misty Morn", "Marillion", 2002));
        songs.add(new Song("Her Little Flower", "Dareon", 2001));
        songs.add(new Song("Milady's Supper", "Dareon", 2001));
        songs.add(new Song("Rat Cook", "Dareon", 2001));
        songs.add(new Song("The Night that ended", "Dareon", 2001));
        songs.add(new Song("When Willum's Wife was wet", "Dareon", 2001));
        songs.add(new Song("A thousad eyes and one", "Dareon", 2001));
        
        
        return songs;
    }
    
    public static void main(String[] args) {
        ArrayList<Song> songs = initSongList();
        MyHashTable<String,Song> songTable;
        int numBuckets = 7;
        // Initialize the hash table.   Key will be the song title.
        
        songTable = new MyHashTable<String,Song>(numBuckets);
        for (Song song: songs) {
            songTable.put(song.getTitle(), song);
        }
        
        System.out.println("New MyHashtable created.....");
        System.out.println("Number of songs in the table: " + songTable.size());
        System.out.println("Number of buckets in the table: " + songTable.numBuckets());
        System.out.println(songTable);
        songs.add(new Song("A thousad eyes and one", "Dareon", 2001));
        //songTable.put("penis", new Song("A thousad eyes and one", "Dareon", 2001));
        
        
       /* for (String title1: songTable.keys()) {
        	System.out.println(title1);
        }
        System.out.println("mid\n");
        
        for (Song song1: songTable.values()) {
        	System.out.println(song1.getTitle());
        }*/
        
        
        // Try to retrieve a song
        StringBuffer errors = new StringBuffer();
        StringBuffer passed = new StringBuffer(); // to store the passed tests
        errors.append("\n*** MyHashTable Section***\n\n"); 
        passed.append("\n*** MyHashTable Section***\n\n"); 
        
        Song testSong0 = songTable.get("Scream");
        System.out.println(testSong0);
        if (testSong0 == null || !testSong0.getArtist().equals("Black Flag") || testSong0.getYear() != 1984) {
            errors.append("***TEST: Song Retrieving*** \n");
        	errors.append("Failed to retrieve song 'Scream'.\n\n");
        }
        else { 
            passed.append("***TEST: Song Retrieving***\n"); 
        	passed.append("Test song 'Scream' retrieved successfully.\n"); 
        	passed.append("Passed Song retrieving test 1.\n\n"); 
        }
        
        //  rehashing changes the capacity of the table, but not the number of entries
        Integer oldBucketCount = songTable.numBuckets();
        Integer oldSize = songTable.size();
        songTable.rehash();
        Integer newBucketCount = songTable.numBuckets();
        if( 2*oldBucketCount != newBucketCount || oldSize != songTable.size()){
        	errors.append("***TEST: Rehashing Test:*** \n"); 
        	errors.append("New bucket count = " + newBucketCount + "\n" );
            errors.append("Expected bucket count = " + 2*oldBucketCount + "\n");
            errors.append("New size = " + songTable.size() + "\n" );
            errors.append("Expected size = " + oldSize + "\n");
            passed.append("Failed Rehashing Test \n\n"); 
        }
        else{ 
        	passed.append("***TEST: Rehashing Test:*** \n"); 
            passed.append("New bucket count = " + newBucketCount + "\n" );
            passed.append("Expected bucket count = " + 2*oldBucketCount + "\n");
            passed.append("New size = " + songTable.size() + "\n" );
            passed.append("Expected size = " + oldSize + "\n");
            passed.append("Passed Rehashing Test \n\n"); 
        }
        
        // Try to retrieve a song
        Song testSong1 = songTable.get("Scream");
        System.out.println(testSong1);
        if (testSong1 == null || !testSong1.getArtist().equals("Black Flag") || testSong1.getYear() != 1984) {
            errors.append("***TEST: Retrieve and removal:*** \n"); 
        	errors.append("Failed to retrieve song 'Scream'.\n");
        }
        else {
            passed.append("***TEST: Retrieve and removal:*** \n"); 
        	passed.append("Song 'Scream' retrieved sucessfully.\n"); 
        // Try to remove a song
        Song removedSong = songTable.remove("Fallen Angel");
        Song retrievedSong = songTable.get("Fallen Angel");
        if (removedSong == null || !removedSong.getArtist().equals("King Crimson") || removedSong.getYear() != 1974 || retrievedSong != null) {
            errors.append("Failed to remove song 'Fallen Angel'.\n");
        }
        else { 
        	passed.append("Sond 'Fallen Angel' removed sucessfully"); 
        }
        
        }
        
        //***************** Music Store Basic Checks *****************//
        errors.append("\n*** MusicStore Section***\n\n"); 
        passed.append("\n\n*** MusicStore Section***\n\n"); 
        
        MusicStore store = new MusicStore(initSongList());
        ArrayList<Song> songsByTom = store.searchByArtist("Tom O'Sevens");
        if(songsByTom.size() != 9){
        	errors.append("***TEST: 9 Songs retrieval:***\n"); 
            errors.append("Failed to retrieve all 9 songs by Tom of sevenstreams\n");
        } 
        else { 
        	passed.append("***TEST: 9 Songs retrieval:***\n"); 
        	passed.append("All 9 songs by Tom of sevenstreams retrieved successfully\n"); 
        }
        System.out.println(songsByTom.size() + " songs by Tom :");
        songsByTom.forEach(song -> System.out.println("\t" + song));
        
        ArrayList<Song> songsFrom2002 = store.searchByYear(2002);
        if(songsFrom2002.size() != 4){
        	errors.append("\n***TEST: 9 Songs retrieval:***\n"); 
            errors.append("ailed to retrieve all 4 songs from 2002\n");
        }
        
        System.out.println(songsFrom2002.size() + " songs from 2002: ");
        songsFrom2002.forEach(song -> System.out.println("\t" + song));
        
        
        //   PUT MORE TESTS HERE.
        
        
        // Display the test results
        System.out.println("---------------\nTEST 1 RESULTS:\n---------------\n");
        System.out.println("ERRORS LOG: \n");
        System.out.println(errors.toString());
        System.out.println("\nPASSED TESTS LOG:\n");
        System.out.println(passed.toString());
        System.out.println();
           
    
    }
   
}
