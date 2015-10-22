//Class created to return two variables from one function
public class Pair<A, B> {
    //Holds a generic variable first and second, each possibly of a different type.
    private A first;
    private B second;
    
    /**creates a pair with both values set to null
     */
    public Pair() {
        first=null;
        second=null;
    }
    
    /**creates a Pair 
     * @param variables of variable type A and variable type B 
     */ 
    public Pair(A a1, B b2) {
        first=a1;
        second=b2;
    }
    
    /**Changes the first value to a1
     * @param variable of variable type A
     */
    public void changeFirst(A a1){
        first=a1;
    }
   
    /**Changes the second value to b2
     * @param variable of variable type B
     */
    public void changeSecond(B b2){
        second=b2;
    }
    
    /**returns first
     * @return variable of variable type A
     */
    public A getFirst() {
        return first;
    }

    /**returns second
     * @return variable of variable type B
     */
    public B getSecond() {
        return second;
    }

}