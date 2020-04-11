import java.util.* ;
public class StringValues{
    public  Hashtable<String, String> values;
    public StringValues(){
        this.values = new Hashtable<>();
    }
    public void put(String mona, String ll){
        values.put(mona,ll);
    }
    public String get(String mona){
        return values.get(mona);
    }

}
