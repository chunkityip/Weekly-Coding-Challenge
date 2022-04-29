public class StringCompression {

    public static void main (String args[]) {
        System.out.println(new StringCompression().solution("aabcccccaaa"));
    }
    
    public String solution (String string) {

        //Since we are adding number at String. Therefore , I will use StringBuilder
        StringBuilder sb = new StringBuilder();


        //putting string to character 
        char[] c = string.toCharArray();

        //starting at the first char and creating a new integer counting to count how many repeated character
        char repeated = c[0];
        int counting = 1;

        //loop
        for (int i = 1; i < c.length; i++) {
            //if the current char same as adjacent char , adding 1 to counting 
            if (c[i] == repeated) {
                counting++;
            } else {
                //else , it means there have no same character , adding the counting number into sb and go loop for next char
                sb.append((repeated + "") + counting);
                repeated = c[i];
                counting = 1;
            }
        } 

        sb.append((repeated + "") + counting);

        //If the "compressed" string would not become smaller than the original string, your method should return the original string.
        if (sb.toString().length() <= string.length()) {
            return sb.toString();
        }
        return string;
    }
}
