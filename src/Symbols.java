
public enum Symbols {
	
	M('M', 1000){
	@Override
	public boolean checkRepeatability(Symbols nextOne){
		if(this.getSuccessiveRepeat() % 3 == 0) {
			if(nextOne.getName() == this.getName()){
			return false;
			}else {
				return true;
			}
		}
		return true;
	}
	
	@Override
	public boolean isValidSubtraction(Symbols nextOne){
		return false;
	}
	
	}
	
	, D('D', 500){
		@Override
		public boolean checkRepeatability(Symbols nextOne){
			if(this.getSuccessiveRepeat() > 0) {
				return false;
			}
			return true;
		}
		@Override
		public boolean isValidSubtraction(Symbols nextOne){
			return false;
		}
		
	}
	
	, C('C', 100) {
		@Override
		public boolean checkRepeatability(Symbols nextOne){
			if(this.getSuccessiveRepeat() % 3 == 0) {
				if(nextOne.getName() == this.getName()){
				return false;
				}else {
					return true;
				}
			}
			return true;
		}
		@Override
		public boolean isValidSubtraction(Symbols nextOne){
			if(nextOne == Symbols.D || nextOne == Symbols.M){
				return true;
			}
			return false;
		}
		
		}
	
	, L('L', 50){
		@Override
		public boolean checkRepeatability(Symbols nextOne){
			if(this.getSuccessiveRepeat() > 0) {
				return false;
			}
			return true;
		}
		@Override
		public boolean isValidSubtraction(Symbols nextOne){
			return false;
		}
		
	}
	
	, X('X', 10) {
		@Override
		public boolean checkRepeatability(Symbols nextOne){
			if(this.getSuccessiveRepeat() % 3 == 0) {
				if(nextOne.getName() == this.getName()){
				return false;
				}else {
					return true;
				}
			}
			return true;
		}
		@Override
		public boolean isValidSubtraction(Symbols nextOne){
			if(nextOne == Symbols.L || nextOne == Symbols.C){
				return true;
			}
			return false;
		}
	
	}
	
	, V('V', 5){
		@Override
		public boolean checkRepeatability(Symbols nextOne){
			if(this.getSuccessiveRepeat() > 0) {
				return false;
			}
			return true;
		}
		@Override
		public boolean isValidSubtraction(Symbols nextOne){
			return false;
		}
	}
	
	, I('I', 1) {
		@Override
		public boolean checkRepeatability(Symbols nextOne){
			if(this.getSuccessiveRepeat() % 3 == 0) {
				if(nextOne.getName() == this.getName()){
				return false;
				}else {
					return true;
				}
			}
			return true;
		}
		@Override
		public boolean isValidSubtraction(Symbols nextOne){
			if(nextOne == Symbols.V || nextOne == Symbols.X){
				return true;
			}
			return false;
		}
	
	}
	
	;

	private char name;

	private double credit;
	
	private String galaticName;
	
	private int successiveRepeat;
	
	private int allowedRepeats;

	private Symbols(char name, double credit){

	this.name = name;

	this.credit = credit;

	}
	
	
	public char getName(){
		return name;
	}
	
	
	public double getCredit(){
		return credit;
	}
	
	public String getGalaticName(){
		return galaticName;
	}
	
	public void setGalaticName(String galaticName){
		this.galaticName = galaticName;
	}
	
	public static Symbols fromString(char first) {
	    for (Symbols b : Symbols.values()) {
	    	
	      if (b.getName() == first) {
	        return b;
	      }
	    }
	    return null;
	  }
	
	public static Symbols fromGalaticNames(String gname) {
	    for (Symbols b : Symbols.values()) {
	    	
	      if (b.getGalaticName()!= null && b.getGalaticName().trim().equals(gname.trim())) {
	        return b;
	      }
	    }
	    return null;
	  }
	
	public abstract boolean checkRepeatability(Symbols next);
	
	public abstract boolean isValidSubtraction(Symbols next);


	public int getSuccessiveRepeat() {
		return successiveRepeat;
	}


	public void setSuccessiveRepeat(int successiveRepeat) {
		this.successiveRepeat = successiveRepeat;
	}
	
	public int getAllowedRepeats() { 
		switch (this) 
		{ 
		case M: 
		case C: 
		case X: 
		case I:
			allowedRepeats = 3;
			break;
		case D: 
		case L: 
		case V: 
			allowedRepeats = 0;
			break;
		}
		return allowedRepeats;
	}
	
	public String toString(){
		
		return String.valueOf(this.getName());
	}
	
}
  