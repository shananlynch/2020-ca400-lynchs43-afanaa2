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
            if(in_scope(id,scope)){
                System.out.println("Fatal error : variable " + id +
                " has been declared multiple times in Scope");
            }
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

    public boolean in_scope(String element, String scope){
        LinkedList l = ST.get(scope);
        return l.contains(element);
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
 public DataType getType (String id, String scope){
     LinkedList<String> scope_list = ST.get(scope);
     String type_scope = "" ;
     if(scope_list != null) {
       for (String match : scope_list) {
           if(match.equals(id)) {
               type_scope = Type.get(id + scope);
                return(getDType(type_scope));
           }
       }
    }
    return DataType.TypeUnknown;

}
 public DataType getDType(String s ){
    if (s.equals("boolean")) {
        return DataType.Boolean;
    }
    else if (s.equals("int")) {
        return DataType.Integer;
    }
    else if (s.equals("string")) {
        return DataType.string;
    }
    else if (s.equals("void")) {
        return DataType.TypeVoid;
    }
    return DataType.TypeUnknown;
    }
}
