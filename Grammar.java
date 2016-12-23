package Grammar;
/** 
* Sungmeen Hong
 * CSE 143 AF
 * This program will take a list of BNF rules and produce
 * a set of words that follows the rules.
 **/
import java.util.*;
public class Grammar {
	private Map<String, List<String>> map;
	
	// The constructor takes in the values (terminal and nonterminal) and rules
	// from the list and appropriately organizes the nonterminal values
	// in alphabetical order.
	// pre: If rules is null or is empty, throw IllegalArgumentException.
	public Grammar(List<String> rules){
		if(rules == null || rules.isEmpty()){
			throw new IllegalArgumentException();
		}
		this.map = new TreeMap<String, List<String>>();
		for(int i = 0; i < rules.size(); i++){
			String curr = rules.get(i);
			String[] sepCurr = curr.split("::=");
			String nonterminal = sepCurr[0].trim();
			if(!this.map.containsKey(nonterminal)){
				map.put(nonterminal, new ArrayList<String>());
			}
			String[] sepRules = sepCurr[1].split("\\|");
			List<String> rulesList = new ArrayList<String>(Arrays.asList(sepRules));
			for(int j = 0; j < rulesList.size(); j++){
				this.map.get(nonterminal).add(rulesList.get(j).trim());
			}
		}
	}
	
	// The method returns whether or not the string inputted
	// is a non-terminal value.
	// pre: If length of symbol is 0 or if symbol is null throw
	// IllegalArgumentException
	public boolean isNonTerminal(String symbol){
		if(symbol == null || symbol.length() == 0){
			throw new IllegalArgumentException();
		}
		
		// Avoid exposing direct references to the field
		boolean contains = this.map.containsKey(symbol);
		return contains;
	}
	
	// The method returns all non-terminal values in 
	// alphabetical order.
	public String toString(){
		// Avoid exposing direct references to the field
		String solution = this.map.keySet().toString();
		return solution;
	}
	
	// The method uses the BNF rules that are established to
	// randomly create a sequence of values that meets the rules
	// that were outlined.
	// pre: If parameter is terminal, throw IllegalArgumentException.
	public String getRandom(String nonterminal){
		if(!this.isNonTerminal(nonterminal)){
			throw new IllegalArgumentException();
		}
		String rule = this.random(this.map.get(nonterminal)).trim(); 
		String[] sepRule = rule.split("\\s+");
		String solution = "";
		for(int i = 0; i < sepRule.length; i++){
			String symbol = sepRule[i];
			if(this.isNonTerminal(symbol)){
				solution += getRandom(symbol).trim() + " ";
			} else {
				solution += symbol.trim() + " ";
			}
		}
		return solution.trim();
	}
	
	// This method generates multiple random sentences that follows
	// the BNF rules that are established.
	// pre: If nonterminal is a terminal value or the number is 
	// negative, throw IllegalArgumentException.
	public List<String> getRandom(int number, String nonterminal){
		if(!this.isNonTerminal(nonterminal) || number < 0){
			throw new IllegalArgumentException();
		}
		List<String> solution = new ArrayList<String>();
		for(int i = 0; i < number; i++){
			solution.add(getRandom(nonterminal));
		}
		return solution;
	}
	
    // Selects a random String from the given list
    // pre: Assume that list has at least one value
    // and is not null.
    private String random(List<String> list){
    	Random rand = new Random();
        int random = rand.nextInt(list.size());
        return list.get(random);
    }
}
