import java.util.*;

public class STC extends Object {
    public Hashtable<String, LinkedList<String>> ST;
    public Hashtable<String, String> Type, Description;
    public STC() {
        this.ST = new Hashtable<>();
        this.Type = new Hashtable<>();
        this.Description = new Hashtable<>();

        ST.put("global", new LinkedList<>());
    }

    public void insert(String id, String type, String descr, String scope) {
        LinkedList<String> temp = ST.get(scope);
        if (temp != null) {
            temp.addFirst(id);
        }
        else {
            temp = new LinkedList<>();
            temp.add(id);
            ST.put(scope, temp);
        }
        Type.put(id + scope, type);
        Description.put(id + scope, descr);
    }

    public void print() {
        String scope;
        Enumeration e = ST.keys();

        System.out.printf("|%11s | %10s | %10s | %12s|\n", "ID", "Type", "Scope", "Description");
        System.out.println("------------------------------------------------------");

        while(e.hasMoreElements()) {
            scope = (String)e.nextElement();
            LinkedList<String> temp = ST.get(scope);

            for(String id : temp) {
                String type = Type.get(id + scope);
                String descr = Description.get(id + scope);
                System.out.printf("|%11s | %10s | %10s | %12s|\n", id, type, scope, descr);
            }

       }
 }
}
