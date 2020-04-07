import java.util.*;

public class TypeCheckVisitor implements monaVisitor {
    private static String scope = "global";
    private static STC st;
    public Object visit(SimpleNode node, Object data){
         return null;}

    public Object visit(ASTProgram node, Object data){
          st = (STC) data;
          node.childrenAccept(this, data); //accepts nodes
         // System.out.println(st.get_scope("i","global"));
        ; return null;}
    public Object visit(ASTVariableDeclaration node, Object data){
        if(node.jjtGetNumChildren() == 3 ){
            SimpleNode node0 = (SimpleNode)node.jjtGetChild(0);
            SimpleNode node2 = (SimpleNode)node.jjtGetChild(2);
            DataType node0type = (DataType) node0.jjtAccept(this, data);
            DataType node1type = (DataType) node2.jjtAccept(this, data);
            if(node0type != node1type){
                System.out.println("incompatible types:");
            }
        }
        return null;}

    public Object visit(ASTConstantDeclaration node, Object data){
    if(node.jjtGetNumChildren() == 3 ){
        SimpleNode node0 = (SimpleNode)node.jjtGetChild(0);
        SimpleNode node2 = (SimpleNode)node.jjtGetChild(2);
        DataType node0type = (DataType) node0.jjtAccept(this, data);
        DataType node1type = (DataType) node2.jjtAccept(this, data);
        if(node0type != node1type){
            System.out.println("incompatible types:");
        }
    }
    return null;}
    public Object visit(ASTfunction node, Object data){ return null;}
    public Object visit(ASTreturn_ node, Object data){ return null;}


        public Object visit(ASTType node, Object data) {
            String s = (String)node.value;
            if (s.equals("boolean")) {
                return DataType.Boolean;
            }
            else if (s.equals("int")) {
                return DataType.Integer;
            }
            else if (s.equals("string")) {
                return DataType.String;
            }
            else if (s.equals("void")) {
                return DataType.TypeVoid;
            }
            return DataType.TypeUnknown;
        }
    public Object visit(ASTparameter_list node, Object data){ return null;}
    public Object visit(ASTmain node, Object data){ return null;}
    public Object visit(ASTstatement node, Object data){ return null;}
    public Object visit(ASTelse_ node, Object data){ return null;}
    public Object visit(ASTassignment node, Object data){ return null;}
    public Object visit(ASTfunctionCall node, Object data){ return null;}
    public Object visit(ASTclass_call node, Object data){ return null;}

    public Object visit(ASTarith_op node, Object data){
        SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
        SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
        DataType node0type = (DataType) node0.jjtAccept(this, data);
        DataType node1type = (DataType) node1.jjtAccept(this, data);
        DataType value = null;
        if(node0type == node1type){
                value = node0type;
            }
            else{
                System.out.println("Type: unsupported operand type(s) for "+ node.jjtGetValue() + ":"  + node0type
                +" and "+node1type);
            }
        return value ;
    }
    public Object visit(ASTcomp_op node, Object data){ return null;}
    public Object visit(ASTandCondition node, Object data){ return null;}
    public Object visit(ASTorCondition node, Object data){ return null;}
    public Object visit(ASTargumentList node, Object data){ return null;}
    public Object visit(ASTarray node, Object data){ return null;}
    public Object visit(ASTclass_ node, Object data){ return null;}
    public Object visit(ASTdecl_list node, Object data){ return null;}

    public Object visit(ASTIdentifier node, Object data){
        DataType type = st.getType(node.jjtGetValue().toString(),scope);
        if(!st.in_scope(node.jjtGetValue().toString(),scope)){
            System.out.println("Undeclared variable : " + node.jjtGetValue());
        }
         return type;}

    public Object visit(ASTbreak_ node, Object data){ return null;}
    public Object visit(ASTassigns node, Object data){
        Node node0 = node.jjtGetChild(0);
        DataType node0type =(DataType)node0.jjtAccept(this,data);
        return node0type;
    }
    public Object visit(ASTNumber node, Object data){
         return DataType.Integer;}
    public Object visit(ASTString node, Object data){ return DataType.String;}
    public Object visit(ASTBoolean node, Object data){ return DataType.Boolean;}

}
