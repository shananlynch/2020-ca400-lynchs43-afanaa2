import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeCheckVisitor implements monaVisitor {
    private static String scope = "global";
    private static STC st;
    Map<String, HashMap<String, Object>> scopeVarCheck = new HashMap<String, HashMap<String, Object>>();
    Map<String, HashMap<String, Object>> paramList = new HashMap<String, HashMap<String, Object>>();
    private static BufferedWriter buff;
    ArrayList<String> errors = new ArrayList<String>();
    File file = new File("semanticErrors.txt");

    /*
     * try { FileWriter fr = new FileWriter(file, true); BufferedWriter br = new
     * BufferedWriter(fr); PrintWriter pr = new PrintWriter(br); }catch( IOException
     * e) { e.printStackTrace(); }
     */
    private void wrightFile(int arraySize, String line) {

        if (arraySize < 2) {
            try {
                FileWriter fWright = new FileWriter(file, false);
                BufferedWriter buff = new BufferedWriter(fWright);
                PrintWriter pWright = new PrintWriter(buff);

                pWright.println(line);

                pWright.close();
                buff.close();
                fWright.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fWright = new FileWriter(file, true);
            BufferedWriter buff = new BufferedWriter(fWright);
            PrintWriter pWright = new PrintWriter(buff);

            pWright.println(line);

            pWright.close();
            buff.close();
            fWright.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object visit(ASTgetArray node, Object data) {
        // System.out.print("At get Array");
        Object l = (Object) node.jjtGetChild(0).jjtAccept(this, data);
        Object type = null;
        if (l instanceof List<?>) {
            ArrayList arrayType1 = (ArrayList) l;
            type = arrayType1.get(1);
        } else {
            DataType d = (DataType) l;
            String s = d + " is not an array type";
            errors.add(s);
            wrightFile(errors.size(), s);
        }
        return type;
    }

    public Object visit(ASTinsert node, Object data) {
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        DataType node1type = (DataType) node1.jjtAccept(this, data);
        Object l = (Object) node.jjtGetChild(0).jjtAccept(this, data);
        Object type = null;
        String s;

        if (l instanceof List<?>) {
            ArrayList arrayType1 = (ArrayList) l;
            type = arrayType1.get(1);
            if (!type.equals(node1type)) {
                s = "Paremeter " + node1type.toString() + " is not compatible with array type " + type.toString();
                errors.add(s);
                wrightFile(errors.size(), s);
            }
        } else {
            DataType d = (DataType) l;
            s = d + " is not an array type";
            errors.add(s);
            wrightFile(errors.size(), s);
        }

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
        try {
            buff = new BufferedWriter(new FileWriter("semanticErrors"));
            for (String line : errors) {
                buff.write(line);
                buff.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to write semantic errors ");
            e.printStackTrace(System.out);
        }
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
        String s;

        if (!scopeVarCheck.containsKey(scope)) {
            HashMap<String, Object> vars = new HashMap<String, Object>();
            vars.put(varId, node0type);
            scopeVarCheck.put(scope, vars);
        } else {
            HashMap<String, Object> vars = scopeVarCheck.get(scope);
            if (vars.containsKey(varId)) {
                s = varId + " has already been declared in scope " + scope;
                System.out.println(varId + " has already been declared in scope " + scope);
                errors.add(s);
                wrightFile(errors.size(), s);
            } else {
                vars.put(varId, node0type);
                scopeVarCheck.put(scope, vars);
            }
        }

        if (node.jjtGetNumChildren() == 3) {
            SimpleNode node2 = (SimpleNode) node.jjtGetChild(2);
            Object node1type = (Object) node2.jjtAccept(this, data);

            if (!node0type.equals(node1type)) {
                s = "incompatible types: " + node0type.toString() + " " + node1type.toString();
                errors.add(s);
                wrightFile(errors.size(), s);
            }

        }

        return null;
    }

    public Object visit(ASTConstantDeclaration node, Object data) {
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        Object node0type = (Object) node0.jjtAccept(this, data);
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        String varId = (String) node1.jjtGetValue().toString();
        String s;

        if (node.jjtGetNumChildren() == 3) {
            SimpleNode node2 = (SimpleNode) node.jjtGetChild(2);
            Object node1type = (Object) node2.jjtAccept(this, data);

            if (!node0type.equals(node1type)) {

                s = "incompatible types: " + node0type.toString() + " " + node1type.toString();
                errors.add(s);
                wrightFile(errors.size(), s);
            }

        }
        if (scopeVarCheck.containsKey(scope) == false) {
            HashMap<String, Object> vars = new HashMap<String, Object>();
            vars.put(varId, node0type);
            scopeVarCheck.put(scope, vars);
        } else {
            HashMap<String, Object> vars = scopeVarCheck.get(scope);
            if (vars.containsKey(varId)) {
                s = varId + " has already been declared in scope " + scope;
                errors.add(s);
                wrightFile(errors.size(), s);
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
        String s;

        for (int i = 2; i < num - 1; i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }

        SimpleNode retCheck = (SimpleNode) node.jjtGetChild(num - 1); // Get last child for return check
        if (retCheck.jjtGetValue() == "return") {
            Object retArgs = (Object) retCheck.jjtAccept(this, data);
            if (!retArgs.equals(retType)) {
                s = "Type returned: " + retArgs + " Does not equal return type: " + retType;
                errors.add(s);
                wrightFile(errors.size(), s);
            }
        } else {
            retCheck.jjtAccept(this, data);
        }
        return null;
    }

    public Object visit(ASTreturn_ node, Object data) {
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        Object node0type = (Object) node0.jjtAccept(this, data);
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
                String s;

                if (!scopeVarCheck.containsKey(scope)) {
                    HashMap<String, Object> vars = new HashMap<String, Object>();
                    vars.put(varId, node0type);
                    scopeVarCheck.put(scope, vars);
                } else {
                    HashMap<String, Object> vars = scopeVarCheck.get(scope);
                    if (vars.containsKey(varId)) {
                        s = varId + " has already been declared in scope " + scope;
                        errors.add(s);
                        wrightFile(errors.size(), s);
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
        scope = "main";
        node.childrenAccept(this, data);
        return null;
    }

    public Object visit(ASTstatement node, Object data) {
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        String value = (String) node.jjtGetValue();
        int num = node.jjtGetNumChildren();
        String s;

        if (value.equals("return")) { // Give return type to function
            Object retType = (Object) node0.jjtAccept(this, data);
            return retType;
        }

        else if (value.equals("insert")) {
            node0.jjtAccept(this, data);
            return null;

        } else if (value.equals("functionCall")) {
            node0.jjtAccept(this, data);
            return null;
        }

        else if (value.equals("print")) {
            node0.jjtAccept(this, data);
            return null;
        } else if (value.equals("while")) {
            node0.jjtAccept(this, data);
            return null;
        } else if (value.equals("insert")) {
            node0.jjtAccept(this, data);
            return null;
        }

        else if (value.equals("=")) { // If assigns
            Object idType = (Object) node0.jjtAccept(this, data);
            SimpleNode as = (SimpleNode) node.jjtGetChild(1);
            Object asType = (Object) as.jjtAccept(this, data);
            if (!idType.equals(asType)) {
                s = "Invalid assignment with type " + idType + " to " + asType;
                errors.add(s);
                wrightFile(errors.size(), s);
            }
            return null;
        } else if (value.equals("if")) { // If assigns
            Object fibs = (Object) node0.jjtAccept(this, data);
            if (!fibs.equals(DataType.Bool)) {
                s = "Invalid if statement";
                errors.add(s);
                wrightFile(errors.size(), s);
            }
            SimpleNode tmpValue = null;
            if (num > 1) {
                for (int i = 1; i < num; i++) {
                    tmpValue = (SimpleNode) node.jjtGetChild(i);
                    String condVal = (String) tmpValue.jjtGetValue().toString();
                    DataType elif = (DataType) tmpValue.jjtAccept(this, data);
                    if (condVal.equals("else_if")) {
                        if (!elif.equals(DataType.Bool)) {
                            s = "Invalid else if statement";
                            errors.add(s);
                            wrightFile(errors.size(), s);
                        }
                    }
                }
            }
            return null;
        } else if (value.equals("else")) {
            node0.jjtAccept(this, data);
            return null;
        }

        else if (value.equals("decl")) {
            node0.jjtAccept(this, data);
            return null;
        } else {
            s = "Node " + node.jjtGetValue() + "has not been accounted for in statement";
            errors.add(s);
            wrightFile(errors.size(), s);
        }
        return null;
    }

    public Object visit(ASTelse_if node, Object data) {
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        Object elif = (Object) node0.jjtAccept(this, data);
        if (!elif.equals(DataType.Bool)) {
            String s = "Invalid if statement";
            errors.add(s);
            wrightFile(errors.size(), s);
        }
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
        node.jjtGetChild(1).jjtAccept(this, data);
        return retType;
    }

    public Object visit(ASTclass_call node, Object data) {
        node.childrenAccept(this, data);
        return null;
    }

    public Object visit(ASTarith_op node, Object data) {
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        DataType node0type = (DataType) node0.jjtAccept(this, data);
        DataType node1type = (DataType) node1.jjtAccept(this, data);
        DataType value = null;
        String s;
        if (!(node0type.equals(DataType.Float) || node0type.equals(DataType.Integer))) {
            s = "Type: unsupported operand type(s) for " + node.jjtGetValue() + ":" + node0type + " and " + node1type;
            errors.add(s);
            wrightFile(errors.size(), s);
            return node0type;
        } else if (!(node1type.equals(DataType.Float) || node1type.equals(DataType.Integer))) {
            s = "Type: unsupported operand type(s) for " + node.jjtGetValue() + ":" + node0type + " and " + node1type;
            errors.add(s);
            wrightFile(errors.size(), s);
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

        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        DataType node0type = (DataType) node0.jjtAccept(this, data);
        DataType node1type = (DataType) node1.jjtAccept(this, data);
        DataType value = null;
        if (!node0type.equals(node1type)) {
            String s = "Type: unsupported operand type(s) for " + node.jjtGetValue() + ":" + node0type + " and "
                    + node1type;
            errors.add(s);
            wrightFile(errors.size(), s);
        } else {
            value = DataType.Bool;
        }
        return value;
    }

    public Object visit(ASTandCondition node, Object data) {
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        DataType node0type = (DataType) node0.jjtAccept(this, data);
        DataType node1type = (DataType) node1.jjtAccept(this, data);
        DataType value = null;
        if (!node0type.equals(node1type)) {
            String s = "Type: unsupported operand type(s) for " + node.jjtGetValue() + ":" + node0type + " and "
                    + node1type;
            errors.add(s);
            wrightFile(errors.size(), s);
        } else {
            value = DataType.Bool;
        }
        return value;
    }

    public Object visit(ASTorCondition node, Object data) {
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        DataType node0type = (DataType) node0.jjtAccept(this, data);
        DataType node1type = (DataType) node1.jjtAccept(this, data);
        DataType value = null;
        if (!node0type.equals(node1type)) {
            String s = "Type: unsupported operand type(s) for " + node.jjtGetValue() + ":" + node0type + " and "
                    + node1type;
            errors.add(s);
            wrightFile(errors.size(), s);
        } else {
            value = DataType.Bool;
        }
        return value;
    }

    public Object visit(ASTargumentList node, Object data) {
        int num = node.jjtGetNumChildren();
        String s;
        if (num > 0) {
            SimpleNode func = (SimpleNode) node.jjtGetParent().jjtGetChild(0);
            String funcId = (String) func.jjtGetValue().toString();
            HashMap<String, Object> params = paramList.get(funcId);
            Collection<Object> paramValues = params.values();

            if (num < params.size() - 1) {
                s = "Too few arguements for function: " + funcId;
                errors.add(s);
                wrightFile(errors.size(), s);
            }
            if (num > params.size() - 1) {
                s = "Too many arguements for function: " + funcId;
                errors.add(s);
                wrightFile(errors.size(), s);
            }
            int j = 0;
            for (Object o : paramValues) {
                if (!o.equals("returnType")) {
                    Object tmpArg = (Object) node.jjtGetChild(j).jjtAccept(this, data);
                    if (!o.equals(tmpArg)) {
                        s = "Arguement(" + j + "): " + tmpArg + " does not match type " + o + " in parameter (" + j
                                + ")";
                        errors.add(s);
                        wrightFile(errors.size(), s);
                    }
                    j++;
                }

            }
        }
        return null;
    }

    public Object visit(ASTarray node, Object data) {
        int num = node.jjtGetNumChildren();
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        DataType node0type = (DataType) node0.jjtAccept(this, data);
        String s;

        List<DataType> arrayType = new ArrayList<DataType>();
        arrayType.add(DataType.Array);
        DataType value = null;
        if (num > 0) {
            for (int i = 0; i < num; i++) {
                value = (DataType) node.jjtGetChild(i).jjtAccept(this, null);
                if (value.toString() != node0type.toString()) {
                    s = "incompatible types: " + value + " in element " + i + " != " + node0type + " in element 0";
                    errors.add(s);
                    wrightFile(errors.size(), s);
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
        HashMap<String, Object> vars = scopeVarCheck.get(scope);
        HashMap<String, Object> params = paramList.get(scope);
        String id = (String) node.jjtGetValue().toString();
        String s;

        if (!vars.containsKey(id)) {
            if (!params.containsKey(id)) {
                s = "Undeclared variable : " + id;
                errors.add(s);
                wrightFile(errors.size(), s);
            }
        }

        Object type = vars.get(id);
        if (type.equals(DataType.TypeUnknown)) {
            if (params.containsKey(id)) {
                type = params.get(id);
            } else {
                s = "Undeclared variable : " + id;
                errors.add(s);
                wrightFile(errors.size(), s);
            }
        }

        return type;
    }

    public Object visit(ASTbreak_ node, Object data) {
        return null;
    }

    public Object visit(ASTassigns node, Object data) {
        Node node0 = (SimpleNode) node.jjtGetChild(0);
        SimpleNode type = (SimpleNode) node.jjtGetParent().jjtGetChild(0);
        Object sType = (Object) type.jjtAccept(this, data);
        Object node0type = (Object) node0.jjtAccept(this, data);
        String s;

        if (!sType.getClass().getSimpleName().equals(node0type.getClass().getSimpleName())) {
            s = "Type: " + sType.getClass().getSimpleName() + " is incompatible with type: "
                    + node0type.getClass().getSimpleName();
            errors.add(s);
            wrightFile(errors.size(), s);
        } else if (sType instanceof List<?>) {
            ArrayList arrayType1 = (ArrayList) sType;
            ArrayList arrayType2 = (ArrayList) node0type;
            if (!arrayType1.get(1).equals(arrayType2.get(1))) {
                s = "Type Array: " + arrayType1.get(1) + " is incompatible with type Array: " + arrayType2.get(1);
                errors.add(s);
                wrightFile(errors.size(), s);
            }
        } else {
            if (!sType.equals(node0type)) {
                s = "Type : " + sType.toString() + " is incompatible with type : " + node0type.toString();
                errors.add(s);
                wrightFile(errors.size(), s);
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
