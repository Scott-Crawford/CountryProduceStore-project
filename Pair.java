public class Pair<A, B> {

    private A first;
    private B second;

    public Pair() {
        first=null;
        second=null;
    }
    
    public Pair(A a1, B b2) {
        first=a1;
        second=b2;
    }
    
    public void changeFirst(A a1){
        first=a1;
    }
    
    public void changeSecond(B b2){
        second=b2;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

}