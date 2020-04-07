import java.util.* ;
public class IrVar{
    public  Hashtable<String, String> values;
    public IrVar(){
        this.values = new Hashtable<>();
    }
    public void put(String mona, String ll){
        values.put(mona,ll);
    }
    public String get(String mona){
        return values.get(mona);
    }

}
