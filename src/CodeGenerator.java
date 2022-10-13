public class CodeGenerator{
    String Generate(){

    String AlphaNumericString = "0123456789"; 
 
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(5); 
        
        for (int i = 0; i < 5; i++) { 
        
        // generate a random number between 
        int index 
            = (int)(AlphaNumericString.length() 
            * Math.random()); 
        
        // add Character one by one in end of sb 
        sb.append(AlphaNumericString 
            .charAt(index)); 
        } 
        
        return sb.toString(); 
    }
}

