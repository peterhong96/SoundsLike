package Grammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ProcessWords {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner process = new Scanner(System.in);
		System.out.print("Name of the file to process? ");
		String fileName = process.nextLine();
		
		List<String> line = readLines(fileName);
		Map<String, Map<String, Integer>> allWords = readWords(line);
		
		Set<String> basic = new HashSet<String>(); 
		Map<String, String> patternWords = new HashMap<String, String>();
		
		// key :: word1
		// key2 :: word2
		// allWords.get(key) :: HashMap<key2, count>
		// allWords.get(key).get(key2) :: count
		for(String key : allWords.keySet()){
			boolean isCommon = false;                       // Checks if the word1 occurs in a sequence more than once
			for(String key2 : allWords.get(key).keySet()){
				int count = allWords.get(key).get(key2);
				if(count > 1) {                             // If count is more than 1, then add the  occurrence in the map
					patternWords.put(key, key2);
					isCommon = true;
				} else {
					basic.add(key2);
				}
			}
			
			if(!isCommon){                                  // If word1 isn't used often, add to list of basic words.
				basic.add(key);
			}
		}
		
		System.out.println("Basic list size is " + basic.size());
		System.out.println("Common set of words is " + patternWords.size());
	}
	
	// Takes the list of lines from the given txt file. It processes each consecutive
	// words from each line and puts it into a map (word -> word2 -> count). 
	public static Map<String, Map<String, Integer>> readWords(List<String> line){
		Map<String, Map<String, Integer>> allWords = new HashMap<String, Map<String, Integer>>();
		for(int i = 0; i < line.size(); i++) {              // Process the words line by line and ignore apostrophe
			String[] words = line.get(i).split("[^\\w']+");
			 
			for(int j = 0; j < words.length - 1; j++) {
				// Add words into map. If already exists, input new key and iterate count. 
				String word1 = words[j].toLowerCase();     // First word
				String word2 = words[j+1].toLowerCase();   // The word after
				//System.out.println("WORDS: " + word1 + " :: " + word2);
				if(!allWords.containsKey(word1)){
					// Create new HashMap inside map if it isn't present
					allWords.put(word1, new HashMap<String, Integer>());
					allWords.get(word1).put(word2, 0);
				} else if(!allWords.get(word1).containsKey(word2)){
					// Enters word2 and count if not present
					allWords.get(word1).put(word2, 0);
				}
				
				// Increase the count of the two consecutive words by 1
				allWords.get(word1).put(word2, allWords.get(word1).get(word2) + 1);
			}
		}
		
		return allWords;
	}
	
	// Prints the two consecutive words and its occurrence
	public static void printConsecVal(Map<String, Map<String, Integer>> allWords) {
		for(String key : allWords.keySet()){
			for(String key2 : allWords.get(key).keySet()){
				System.out.println(key + " ||  " + key2 + " ||  " + allWords.get(key).get(key2));
			}
		}
	}
	
	// Reads text from the file with the given name and returns as a List.
    // Strips empty lines and trims leading/trailing whitespace from each line.
    // post: returns null if no such filename exists 
	// NOT MY CODE :: From CSE143
	public static List<String> readLines(String fileName)  {
        List<String> lines = new ArrayList<String>();
        try {
            Scanner input = new Scanner(new File(fileName));
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.length() > 0) {
                    lines.add(line);
                }
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
        return lines;
    }

}
