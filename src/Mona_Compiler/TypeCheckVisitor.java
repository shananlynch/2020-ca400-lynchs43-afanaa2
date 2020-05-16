import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeCheckVisitor implements monaVisitor {
    private static String scope = "global";
    private static STC st;
    HashMap<String, HashMap<String, Object>> scopeVarCheck = new HashMap<String, HashMap<String, Object>>();
    HashMap<String, HashMap<String, Object>> paramList = new HashMap<String, HashMap<String, Object>>();
    HashMap<String, Object> tmp = new HashMap<String, Object>();
    private static BufferedWriter buff;

    public Object visit(ASTgetArray node, Object data) {
        System.out.print("At get Array");
        Object l = (Object) node.jjtGetChild(0).jjtAccept(this, data);
        Object type = null;
        if (l instanceof List<?>) {
            ArrayList arrayType1 = (ArrayList) l;
            type = arrayType1.get(1);
        } else {
            DataType d = (DataType) l;
            System.out.println(d + " is not an array type");
        }
        return type;
    }

    public Object visit(ASTinsert node, Object data) {
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        DataType node1type = (DataType) node1.jjtAccept(this, data);
        Object l = (Object) node.jjtGetChild(0).jjtAccept(this, data);
        Object type = null;

        if (l instanceof List<?>) {
            ArrayList arrayType1 = (ArrayList) l;
            type = arrayType1.get(1);
            if (!type.equals(node1type)) {
                System.out.println(
                        "Paremeter " + node1type.toString() + " is not compatible with array type " + type.toString());
            }
        } else {
            DataType d = (DataType) l;
            System.out.println(d + " is not an array type");
        }

        System.out.println("Insert type : " + node1type.toString());
        return type;
    }

    public Object visit(SimpleNode node, Object data) {
        return null;
    }

    public Object visit(ASTgetChar node, Object data) {

        return DataType.string;
    }

    public Object visit(ASTgetStringLength node, Object data) {
        return DataType.Integer;
    }

    public Object visit(ASTgetLength node, Object data) {
        return DataType.Integer;
    }

    public Object visit(ASTProgram node, Object data) {
        st = (STC) data;
        node.childrenAccept(this, data); // accepts nodes
        // System.out.println(st.get_scope("i","global"));
        System.out.println("At program");
        return null;
    }

    public Object visit(ASTFloat node, Object data) {
        return DataType.Float;
    }

    public Object visit(ASTVariableDeclaration node, Object data) {
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        Object node0type = (Object) node0.jjtAccept(this, data);
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        String varId = (String) node1.jjtGetValue().toString();
        System.out.println("VarDec id: " + varId + " type: " + node0type);

        if (!scopeVarCheck.containsKey(scope)) {
            HashMap<String, Object> vars = new HashMap<String, Object>();
            vars.put(varId, node0type);
            scopeVarCheck.put(scope, vars);
        } else {
            HashMap<String, Object> vars = scopeVarCheck.get(scope);
            if (vars.containsKey(varId)) {
                System.out.println(varId + " has already been declared in scope " + scope);
            } else {
                vars.put(varId, node0type);
                scopeVarCheck.put(scope, vars);
            }
        }

        if (node.jjtGetNumChildren() == 3) {
            SimpleNode node2 = (SimpleNode) node.jjtGetChild(2);
            Object node1type = (Object) node2.jjtAccept(this, data);
            System.out.println("Var:" + node0type.toString());

            if (!node0type.equals(node1type)) {
                System.out.println("incompatible types: " + node0type.toString() + " " + node1type.toString());
            }

        }

        return null;
    }

    public Object visit(ASTConstantDeclaration node, Object data) {
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        Object node0type = (Object) node0.jjtAccept(this, data);
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        String varId = (String) node1.jjtGetValue().toString();

        if (node.jjtGetNumChildren() == 3) {
            SimpleNode node2 = (SimpleNode) node.jjtGetChild(2);
            Object node1type = (Object) node2.jjtAccept(this, data);
            System.out.println("Var:" + node0type.toString());

            if (!node0type.equals(node1type)) {
                System.out.println("incompatible types: " + node0type.toString() + " " + node1type.toString());
            }

        }
        System.out.println("scopeVarCheck: " + scope);
        if (scopeVarCheck.containsKey(scope) == false) {
            HashMap<String, Object> vars = new HashMap<String, Object>();
            vars.put(varId, node0type);
            scopeVarCheck.put(scope, vars);
        } else {
            HashMap<String, Object> vars = scopeVarCheck.get(scope);
            if (vars.containsKey(varId)) {
                System.out.println(varId + " has already been declared in scope " + scope);
            }
        }

        return null;
    }

    public Object visit(ASTfunction node, Object data) {
        scope = node.jjtGetValue().toString();
        int num = node.jjtGetNumChildren();
        HashMap<String, Object> params = new HashMap<String, Object>();
        Object retType = (Object) node.jjtGetChild(0).jjtAccept(this, data); // Type
        params.put("returnType", retType);
        paramList.put(scope, params);
        SimpleNode sid = (SimpleNode) node.jjtGetChild(1); // Identifier

        for (int i = 2; i < num - 1; i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }

        SimpleNode retCheck = (SimpleNode) node.jjtGetChild(num - 1); // Get last child for return check
        if (retCheck.jjtGetValue() == "return") {
            Object retArgs = (Object) retCheck.jjtAccept(this, data);
            if (!retArgs.equals(retType)) {
                System.out.println("Type returned: " + retArgs + " Does not equal return type: " + retType);
            }
        } else {
            retCheck.jjtAccept(this, data);
        }
        return null;
    }

    public Object visit(ASTreturn_ node, Object data) {
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        Object node0type = (Object) node0.jjtAccept(this, data);
        System.out.println("Return node 0 " + node0type.toString());
        return node0type;
    }

    public Object visit(ASTType node, Object data) {
        String s = (String) node.value;

        if (s.equals("boolean")) {
            return DataType.Bool;
        } else if (s.equals("int")) {
            return DataType.Integer;
        } else if (s.equals("string")) {
            return DataType.string;
        } else if (s.equals("float")) {
            return DataType.Float;
        } else if (s.equals("void")) {
            return DataType.Void;
        } else if (s.equals("[")) {
            SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
            DataType node0type = (DataType) node0.jjtAccept(this, data);
            List<DataType> arrayType = new ArrayList<DataType>();
            arrayType.add(DataType.Array);
            arrayType.add(node0type);

            return arrayType;
        }
        return DataType.TypeUnknown;
    }

    public Object visit(ASTparameter_list node, Object data) {
        int num = node.jjtGetNumChildren();
        HashMap<String, Object> params = paramList.get(scope); // element(0) is the return type
        if (num > 0) {
            for (int i = 0; i < num; i += 2) {
                SimpleNode node0 = (SimpleNode) node.jjtGetChild(i);
                Object node0type = (Object) node0.jjtAccept(this, data);
                SimpleNode node1 = (SimpleNode) node.jjtGetChild(i + 1);
                String varId = (String) node1.jjtGetValue().toString();
                System.out.println("ParamList id: " + varId + " type: " + node0type);

                if (!scopeVarCheck.containsKey(scope)) {
                    HashMap<String, Object> vars = new HashMap<String, Object>();
                    vars.put(varId, node0type);
                    scopeVarCheck.put(scope, vars);
                } else {
                    HashMap<String, Object> vars = scopeVarCheck.get(scope);
                    if (vars.containsKey(varId)) {
                        System.out.println(varId + " has already been declared in scope " + scope);
                    } else {
                        vars.put(varId, node0type);
                        scopeVarCheck.put(scope, vars);
                    }
                }
                params.put(varId, node0type);

            }
        }
        paramList.put(scope, params);
        return null;
    }

    public Object visit(ASTmain node, Object data) {
        System.out.println("At main");
        scope = "main";
        scopeVarCheck.put(scope, tmp);
        node.childrenAccept(this, data);
        return null;
    }

    public Object visit(ASTstatement node, Object data) {
        System.out.println("At statement");
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        String value = (String) node.jjtGetValue();
        System.out.println("Statement value: " + value);
        int num = node.jjtGetNumChildren();
        // System.out.println("statement node 0 :" + node0.jjtAccept(this,
        // data).toString());

        if (value.equals("return")) { // Give return type to function
            Object retType = (Object) node0.jjtAccept(this, data);
            System.out.println(retType);
            return retType;
        }

        else if (value.equals("insert")) {
            node0.jjtAccept(this, data);
            System.out.println("insert");
            return null;

        } else if (value.equals("functionCall")) {
            node0.jjtAccept(this, data);
            System.out.println("functionCall");
            return null;
        }

        else if (value.equals("print")) {
            node0.jjtAccept(this, data);
            System.out.println("print");
            return null;
        } else if (value.equals("while")) {
            node0.jjtAccept(this, data);
            System.out.println("while");
            return null;
        } else if (value.equals("insert")) {
            node0.jjtAccept(this, data);
            System.out.println("insert");
            return null;
        }

        else if (value.equals("=")) { // If assigns
            System.out.println("Statement assigns");
            Object idType = (Object) node0.jjtAccept(this, data);
            SimpleNode as = (SimpleNode) node.jjtGetChild(1);
            Object asType = (Object) as.jjtAccept(this, data);
            if (!idType.equals(asType)) {
                System.out.println("Invalid assignment with type " + idType + " to " + asType);
            }
            return null;
        } else if (value.equals("if")) { // If assigns
            Object fibs = (Object) node0.jjtAccept(this, data);
            if (!fibs.equals(DataType.Bool)) {
                System.out.println("Invalid if statement");
            }
            SimpleNode tmpValue = null;
            if (num > 1) {
                for (int i = 1; i < num; i++) {
                    tmpValue = (SimpleNode) node.jjtGetChild(i);
                    String condVal = (String) tmpValue.jjtGetValue().toString();
                    DataType elif = (DataType) tmpValue.jjtAccept(this, data);
                    if (condVal.equals("else_if")) {
                        if (!elif.equals(DataType.Bool)) {
                            System.out.println("Invalid else if statement");
                        }
                    }
                }
            }
            return null;
        } else if (value.equals("else")) {
            System.out.println("At else");
            node0.jjtAccept(this, data);
            return null;
        }

        else if (value.equals("decl")) {
            System.out.println("At statement dec and " + num + " children");
            node0.jjtAccept(this, data);
            return null;
        } else {
            System.out.println("Node " + node.jjtGetValue() + "has not been accounted for in statement");
        }
        return null;
    }

    public Object visit(ASTelse_if node, Object data) {
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        Object elif = (Object) node0.jjtAccept(this, data);
        if (!elif.equals(DataType.Bool)) {
            System.out.println("Invalid if statement");
        }
        System.out.println("At elif");
        return elif;
    }

    public Object visit(ASTassignment node, Object data) {
        return null;
    }

    public Object visit(ASTfunctionCall node, Object data) {
        SimpleNode func = (SimpleNode) node.jjtGetChild(0);
        String funcScope = (String) func.jjtGetValue();
        HashMap<String, Object> params = paramList.get(funcScope);
        Object retType = params.get("returnType");
        System.out.println("Function Call Return type: " + retType);
        node.jjtGetChild(1).jjtAccept(this, data);
        return retType;
    }

    public Object visit(ASTclass_call node, Object data) {
        node.childrenAccept(this, data);
        return null;
    }

    public Object visit(ASTarith_op node, Object data) {
        System.out.println("Reaches arithOp");
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        DataType node0type = (DataType) node0.jjtAccept(this, data);
        DataType node1type = (DataType) node1.jjtAccept(this, data);
        DataType value = null;
        if (!(node0type.equals(DataType.Float) || node0type.equals(DataType.Integer))) {
            System.out.println("Type: unsupported operand type(s) for " + node.jjtGetValue() + ":" + node0type + " and "
                    + node1type);
            return node0type;
        } else if (!(node1type.equals(DataType.Float) || node1type.equals(DataType.Integer))) {
            System.out.println("Type: unsupported operand type(s) for " + node.jjtGetValue() + ":" + node0type + " and "
                    + node1type);
            return node1type;
        } else {
            if (node0type.equals(node1type)) {
                value = node0type;
            } else {
                value = DataType.Float;
            }
        }
        return value;
    }

    public Object visit(ASTcomp_op node, Object data) {

        System.out.println("Reaches comp_op");
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        DataType node0type = (DataType) node0.jjtAccept(this, data);
        System.out.println(node0type.toString());
        DataType node1type = (DataType) node1.jjtAccept(this, data);
        DataType value = null;
        if (!node0type.equals(node1type)) {
            System.out.println("Type: unsupported operand type(s) for " + node.jjtGetValue() + ":" + node0type + " and "
                    + node1type);
        } else {
            value = DataType.Bool;
        }
        return value;
    }

    public Object visit(ASTandCondition node, Object data) {
        System.out.println("Reaches andCond");
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        DataType node0type = (DataType) node0.jjtAccept(this, data);
        System.out.println(node0type.toString());
        DataType node1type = (DataType) node1.jjtAccept(this, data);
        DataType value = null;
        if (!node0type.equals(node1type)) {
            System.out.println("Type: unsupported operand type(s) for " + node.jjtGetValue() + ":" + node0type + " and "
                    + node1type);
        } else {
            value = DataType.Bool;
        }
        return value;
    }

    public Object visit(ASTorCondition node, Object data) {
        System.out.println("Reaches orCond");
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        DataType node0type = (DataType) node0.jjtAccept(this, data);
        System.out.println(node0type.toString());
        DataType node1type = (DataType) node1.jjtAccept(this, data);
        DataType value = null;
        if (!node0type.equals(node1type)) {
            System.out.println("Type: unsupported operand type(s) for " + node.jjtGetValue() + ":" + node0type + " and "
                    + node1type);
        } else {
            value = DataType.Bool;
        }
        return value;
    }

    public Object visit(ASTargumentList node, Object data) {
        int num = node.jjtGetNumChildren();
        if (num > 0) {
            SimpleNode func = (SimpleNode) node.jjtGetParent().jjtGetChild(0);
            String funcId = (String) func.jjtGetValue().toString();
            HashMap<String, Object> params = paramList.get(funcId);
            Collection<Object> paramValues = params.values();

            if (num < params.size() - 1) {
                System.out.println("Too few arguements for function: " + funcId);
            }

            if (num > params.size() - 1) {
                System.out.println("Too many arguements for function: " + funcId);
            }

            int j = 0;
          for (Object o : paramValues) {
               if(j < num){
                    if (!o.equals("returnType")) {
                         Object tmpArg = (Object) node.jjtGetChild(j).jjtAccept(this, data);
                         if (!o.equals(tmpArg)) {
                              System.out.println("Arguement(" + j + "): " + tmpArg + " does not match type " + o
                              + " in parameter (" + j + ")");
                         }
                         j++;
                    }
               }
               }
          }
          return null;
    }

    public Object visit(ASTarray node, Object data) {
        System.out.println("Reached array");
        int num = node.jjtGetNumChildren();
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        DataType node0type = (DataType) node0.jjtAccept(this, data);

        List<DataType> arrayType = new ArrayList<DataType>();
        arrayType.add(DataType.Array);
        DataType value = null;
        if (num > 0) {
            for (int i = 0; i < num; i++) {
                value = (DataType) node.jjtGetChild(i).jjtAccept(this, null);
                System.out.println("Array Value at element(" + i + "): " + value);
                if (value.toString() != node0type.toString()) {
                    System.out.println(
                            "incompatible types: " + value + " in element " + i + " != " + node0type + " in element 0");
                    break;
                }
            }

        }
        arrayType.add(value);
        return arrayType;
    }

    public Object visit(ASTclass_ node, Object data) {
        node.childrenAccept(this, data);
        return null;
    }

    public Object visit(ASTdecl_list node, Object data) {
        return null;
    }

    public Object visit(ASTIdentifier node, Object data) {
        System.out.println("At identifier");
        HashMap<String, Object> vars = scopeVarCheck.get(scope);
        HashMap<String, Object> params = paramList.get(scope);
        String id = (String) node.jjtGetValue().toString();

     System.out.println("ID: " + id.toString());
     try{
          if (!vars.containsKey(id)) {
               if (!params.containsKey(id)) {
                    System.out.println("Undeclared variable : " + id);
               }
          }
     } catch (NullPointerException e ){}

     Object type = vars.get(id);
     try{
          if (type.equals(DataType.TypeUnknown)) {
               if (params.containsKey(id)) {
                    type = params.get(id);
               } else {
                    System.out.println("Undeclared variable : " + id);
               }
          }

     }catch (NullPointerException e ){}
        return type;
    }

    public Object visit(ASTbreak_ node, Object data) {
        return null;
    }

    public Object visit(ASTassigns node, Object data) {
        System.out.println("At assigns");
        Node node0 = (SimpleNode) node.jjtGetChild(0);
        SimpleNode type = (SimpleNode) node.jjtGetParent().jjtGetChild(0);
        Object sType = (Object) type.jjtAccept(this, data);
        System.out.println(sType);
        Object node0type = (Object) node0.jjtAccept(this, data);
        System.out.println(node0type);
        if (!sType.getClass().getSimpleName().equals(node0type.getClass().getSimpleName())) {
            System.out.println("Type: " + sType.getClass().getSimpleName() + " is incompatible with type: "
                    + node0type.getClass().getSimpleName());
        } else if (sType instanceof List<?>) {
            ArrayList arrayType1 = (ArrayList) sType;
            ArrayList arrayType2 = (ArrayList) node0type;
            if (!arrayType1.get(1).equals(arrayType2.get(1))) {
                System.out.println(
                        "Type Array: " + arrayType1.get(1) + " is incompatible with type Array: " + arrayType2.get(1));
            }
        } else {
            if (!sType.equals(node0type)) {
                System.out
                        .println("Type : " + sType.toString() + " is incompatible with type : " + node0type.toString());
            }
        }
        return node0type;
    }

    public Object visit(ASTNumber node, Object data) {
        return DataType.Integer;
    }

    public Object visit(ASTString node, Object data) {
        return DataType.string;
    }

    public Object visit(ASTBoolean node, Object data) {
        return DataType.Bool;
    }

}
