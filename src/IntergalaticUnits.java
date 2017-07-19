import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class IntergalaticUnits {
	
	private List<String> galaticNamesList = new ArrayList<String>();
	
	private Map<String,String> given = new HashMap<String, String>();
	
	private List<String> toFind = new ArrayList<String>();
	
	private List<String> metalList = new ArrayList<String>(Arrays.asList("Gold", "Silver", "Iron"));
	
	private List<Symbols> symbolsList =  Arrays.asList(Symbols.values());;
	
	private Map<String,Double> metalCredits = new HashMap<String, Double>();
		
	public IntergalaticUnits(Map<String,String> given, List<String> toFind){
		
		this.given = given;
		
		this.toFind = toFind;
		
		setup();
		
	}
	
	public void setMetalCredits(Map<String,Double> metalCredits){
		
		this.metalCredits = metalCredits;
	}

	public void setup(){
		
        Map<String,String> galUnits = new HashMap<String, String>();
		
		Map<String,String> galAndMetalUnits = new HashMap<String, String>();
		
		 given.forEach((key, value) -> {
			    
			    if(key.split("\\s+").length == 1){
			    	
			    	galUnits.put(key, value);
			    	
			    }else{
			    	
			    	galAndMetalUnits.put(key, value);
			    	
			    }
			    
			});
		 
		 List<Symbols> symbolsList = Arrays.asList(Symbols.values());
		 
		 for(Symbols sym: symbolsList){  
		 
			 galUnits.forEach((key, value) -> {
			    
			    if(IsEqualStringandEnum(value,sym)){
			    	
			    	sym.setGalaticName(key);
			    	
			    }
			    
		 });
		 
		 }
		 
		 this.galaticNamesList = getGalaticNamesList();
		 
		processGivenGalMetalUnits(galAndMetalUnits);
		
	}
	
	public void processGivenGalMetalUnits(Map<String,String> galAndMetalUnits){
		
		List<String> lhsKeysList = galAndMetalUnits.keySet().stream()
				.collect(Collectors.toList());
		
        List<String> lhsGiven = new ArrayList<String>();
        
        for(String item: lhsKeysList){              //loop to repeat
        	lhsGiven = 
					  Pattern.compile("\\s+")
					  .splitAsStream(item)
					  .collect(Collectors.toList());
			
			 double lhsCredit = processGalaticAndMetalUnits(lhsGiven);
			 
			 List<String> lhsMetalsOnly = getMetalPart(lhsGiven);
			 
			 String lhsMetal = "";
			 
			 if(lhsMetalsOnly.size() == 1){
				 
				 lhsMetal = lhsMetalsOnly.get(0);
				 
			 }
			 
			 String rhsStr = galAndMetalUnits.get(item);
			
			 double rhsDouble = Double.parseDouble(rhsStr.replaceAll("[^0-9]", ""));
			 
			 double metalCredit = equate(lhsCredit, lhsMetal, rhsDouble);
			 
			 this.metalCredits.put(lhsMetal, metalCredit);
			 
		 }
		
	     this.setMetalCredits(metalCredits);
	}
	
	
             public double equate(double lhsCredit, String lhsMetal, double rhsDouble){
    	
    	              double metalCredit = 0;
    
    	              metalCredit = rhsDouble/lhsCredit;
    	
    	              return metalCredit;
             
             }
	
	         
             public boolean IsEqualStringandEnum (String str,Symbols sym){
		
	            if (str.trim().equals(sym.toString().trim())){
	                    
	            	return true;
	            }
	            
	            else{
	                
	            	return false;
	            
	            }	
	            
	           }
             
	
	          public List<String> getGalaticNamesList(){
		
		            List<String> galaticNamesList = new ArrayList<String>();
		
		            List<Symbols> symbolsList = Arrays.asList(Symbols.values());
		
		            for(Symbols s: symbolsList){
			
			               if(s.getGalaticName() != null){
			
			                    galaticNamesList.add(s.getGalaticName());
			
			                }
		             
		            }
		
		       return galaticNamesList;
	         
	          }
	
	

	     private double processGalaticAndMetalUnits(List<String> items) {
		
		         double credit = 0;
		
		                if(isGalUnitsOnly(items)){
			
                                 String numeralString = "";
			
			                     for(String s: items){
				
				                        String numeralVal = (Symbols.fromGalaticNames(s)).toString();
				
				                        numeralString = (numeralString.trim() + numeralVal.trim()).trim();
				
			                      }
			
			      credit = processRomanNumerals(numeralString);
			
		                }else if(hasMetalsAndNumerals(items)){
				
			                   List<String> numeralpart = getNumeralPart(items);
          
                               numeralpart.forEach(System.out::println);
			
                               double numeralCredit = processNumeralPart(numeralpart);
            
                               credit = numeralCredit;
          
		           }
		
		        return credit;
		        
	     }
	
	           public double calculateTotalCredits(List<String> items){
		
		                 double credit = 0;
		
		                 if(isGalUnitsOnly(items)){
			
                                       String numeralString = "";
			
			                           for(String s: items){
				
				                                  String numeralVal = (Symbols.fromGalaticNames(s)).toString();
				
				                                  numeralString = (numeralString.trim() + numeralVal.trim()).trim();
			
			                           }
			
			                           credit = processRomanNumerals(numeralString);
			
		                  }else if(hasMetalsAndNumerals(items)){
				
			
			List<String> numeralpart = getNumeralPart(items);
            
            double numeralCredit = processNumeralPart(numeralpart);
            
            List<String> lhsMetalsOnly = getMetalPart(items);
			 
			 String lhsMetal = "";
			 
			 if(lhsMetalsOnly.size() == 1){
				 
				 lhsMetal = lhsMetalsOnly.get(0);
				 
			 }
			 
			 
			 double mCredit = 0;
			 
			 if(metalCredits.size() > 0){
			 
			 mCredit = this.metalCredits.get(lhsMetal.trim());
			 
			 }
            
            credit = numeralCredit * mCredit;
            
            
		}
		
		return credit;
		
	}
	
	public double processNumeralPart(List<String> numeralpart){
		
		 String numeralString = "";
			
			for(String s: numeralpart){
				
				String numeralVal = (Symbols.fromGalaticNames(s)).toString();
				
				numeralString = (numeralString.trim() + numeralVal.trim()).trim();
				
				System.out.println("numeralString    " + numeralString);
			}
			
			double credit = processRomanNumerals(numeralString);
			
			System.out.println(" credit for: numeral String:  " + credit);
		
		
		return credit;
		
	}
	
	public List<String> getNumeralPart(List<String> list3){
		
		List<String> numeralPart = new ArrayList<String>();
		
		for(String s: list3){
			
			
		if(isGalListContains(s)){
			
			numeralPart.add(s);
			
		}
			
		
	    }
		
		return numeralPart;
			
		}
		
public List<String> getMetalPart(List<String> items){
		
		List<String> metalPart = new ArrayList<String>();
		
		for(String s: items){
			
		if(isMetalListContains(s)){
			
			
			metalPart.add(s);
			
		}
			
		
	    }
		
		return metalPart;
			
		}
	
	
	
	private boolean isGalListContains(String name){
		
		for(String str: galaticNamesList) {
		    if(str.trim().equals(name.trim()))
		       return true;
		}
		return false;
	}
	
   private boolean isMetalListContains(String name){
		
		for(String str: metalList) {
		    if(str.trim().equals(name.trim()))
		       return true;
		}
		return false;
	}
	
	private boolean hasMetalsAndNumerals(List<String> list3){
		
		return (list3.stream().anyMatch(metalList::contains));
		
	}
	
	private boolean isGalUnitsOnly(List<String> list3){
		
		
		return (!(list3.stream().anyMatch(metalList::contains)));
		
	}
	
	
	
public double processRomanNumerals(String matchedSymbols){
		
		double totalCredits = 0.0;
		
		Symbols curr = null;
		Symbols prev = null;
		
		Symbols next = null;
		
		
		 for(int i = 0; i < matchedSymbols.length(); i++){
			
			     if(curr!= null){
			    	 
				 prev = curr;
			 }
        	 System.out.println(" i  ==  " + i);
			    int t = i;
			    int r = 0;
			 System.out.println(" t  ==  " + t);
			    char first = matchedSymbols.charAt(t);
                char second = '\0';
                
                if(++t < matchedSymbols.length()){
                	r = t;
                
                System.out.println(" r  ==  " + r);
			    
			    second = matchedSymbols.charAt(r);
			    
                }
				
				if (first != ' '){
					
					curr = Symbols.fromString(first);
					
				}
				
				
				
				if (second != ' '){
				
				next = Symbols.fromString(second);
				
				}
			
		        	 if(prev != null && curr == prev){
		        		
		        		 curr.setSuccessiveRepeat(curr.getSuccessiveRepeat() + 1);
		        	 }
		          
		          if(prev != null && (curr.ordinal() > prev.ordinal())){
		          
		          if(!(curr.isValidSubtraction(next))){
						
		 				System.out.println(curr + "cannot be subtracted from " + next + " Invalid Input");
		 				
		 				
		 			}
		          
		          }
		          
		          
		    
		          if(i == 1){
		        	  if(curr.getCredit() <= prev.getCredit()){
		        		  
		        		  totalCredits = prev.getCredit() + curr.getCredit();
		        		  System.out.println(" totalCredits" + totalCredits + " i: " + i);
		        		  
		        	  } if(curr.getCredit() > prev.getCredit()){
		        		  
		        		  totalCredits = curr.getCredit() - prev.getCredit();
		        		  System.out.println(" totalCredits" + totalCredits + " i: " + i);
		        		  
		        	  }
		        	  
		        	 
		          } else if(i > 1){
		          
		          
		          if(curr.getCredit() <= prev.getCredit()){
		        		  
		        		  totalCredits = totalCredits + curr.getCredit();
		        		  
		        		  System.out.println(" totalCredits" + totalCredits + " i: " + i);
		        		  
		          } else  if(curr.getCredit() > prev.getCredit()){
		        		  totalCredits = totalCredits + (curr.getCredit() - prev.getCredit());
		        		  
		         }
		         
		          }
		          
		        	 if(canRepeat(curr, next)){
		        		 
		        		 
		        		 
		        		 System.out.println("Invalid input;" + curr + " cannot be repeated more than " + curr.getAllowedRepeats() + "times");
		        	     break;
		        	 
		        	 }
		     
		     	  
		          }
		          
	
		
return totalCredits;
	
	
}

public boolean canRepeat(Symbols curr, Symbols next){
	
	/*
	 if(next != null){
         if(!(curr.checkRepeatability(next))){
         
       	  System.out.println("Invalid input;" + curr + " cannot be repeated more than " + curr.getAllowedRepeats() + "times");
				return false;
				
			}
       	}
       	
       	*/
	return false;
	
}


public List<String> prepareSolution(List<String> toFind2) {
	
	List<String> solutionList = new ArrayList<String>();
	
	 
	 List<String> items = new ArrayList<String>();
	 
	 double credits = 0;
	 
	 for(String item: toFind){
		 items = 
				  Pattern.compile("\\s+")
				  .splitAsStream(item)
				  .collect(Collectors.toList());
		
		 credits = calculateTotalCredits(items);
		 
		 int creditInt = (int) credits;
		 
		
		 
		 String solutionStr = item + " is " + creditInt;
		 
		 solutionList.add(solutionStr);
		 
	 }
	 
	
	 return solutionList;
	
}

	
}
