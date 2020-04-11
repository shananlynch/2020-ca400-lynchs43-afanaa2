import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map;
public class IrCodeVisitor implements monaVisitor {
    private int tmpCount = 1;
    private IrVar iv = new IrVar();
    private StringValues sv = new StringValues();
    private String  scope = "global" ;
    private int labelCount = 1;
    private static STC st;
    private static BufferedWriter buff ;
    private static String stringDec = "";
    private static String prog = "";
    private static String endLabel;
    private static String trueLabel;
    private static String FalseLabel ;
    private Map<String,Integer> varInc = new HashMap<String, Integer>();



    private String getTemp ()
    {
        String tmp = "%.t" + tmpCount;
        tmpCount = tmpCount + 1;
        return tmp;
    }

    private void getStringDeclN(String word )
    {

        Integer count = varInc.get(word);
        if (count == null) {
            varInc.put(word, 1);
        }
        else {
            varInc.put(word, count + 1);
        }
    }

    static void beginIr (BufferedWriter buffer)
    {
     prog = prog + "declare i32 @printf(i8*, ...) #1\n";
     prog =prog + "@.1arg_str = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1\n \n";
     prog = prog + "declare i32 @puts(i8*)\n" ;
     prog =prog + "define i32 @main () \n ";
     prog =prog + "{";
      return;
    }

    static void endIr (BufferedWriter buffer)
    {
        try
      {
      prog = prog + "ret i32 0 \n" ;
      prog = prog + "}\n";
      buff.write(stringDec);
      buff.write(prog);
      buff.flush ();

        }
        catch (IOException e)
        {
          System.out.println ("Failed to write end of ir code to file. Exception: ");
          e.printStackTrace (System.out);
        }

        return;
    }

    public static String machineType (String type)
    {
        String mty;
        if (type.equals ("Integer")){
            mty = "i32";
	    }
        else if (type.equals ("boolean")){
            mty = "i1";
	    }
        else if (type.equals ("string")){
            mty = "i8*";
	    }
        else if (type.equals ("void"))
        {
            mty = "void";
	    }
        else{
            mty = "i32";
        }
        return (mty);
    }

    private String getLabel ()
    {
        String lbl = "label" + labelCount;
        labelCount = labelCount + 1;
        return lbl;
    }

      public Object visit(SimpleNode node, Object data){ return null;}




      public Object visit(ASTProgram node, Object data){
          st = (STC) data;
          try{
               buff = new BufferedWriter (new FileWriter ("irFileName"));
          }
          catch (IOException e)
          {
                  System.out.println ("Failed to write LLVM code to file. Exception: ");
                  e.printStackTrace (System.out);
              }

          beginIr (buff);
          node.childrenAccept(this, data); //accepts nodes
          endIr (buff);
          return null;}




      public Object visit(ASTVariableDeclaration node, Object data){
          String dec = "";
          String command =  "";
          String load = "";
          String node0 = (String) node.jjtGetChild (0).jjtAccept (this, data);
          SimpleNode node1 = (SimpleNode) node.jjtGetChild (1);
          String mt = machineType(node0) ;
          if(node0.equals("string") && node.jjtGetNumChildren() > 2 ){
            String node2 = (String) node.jjtGetChild (2).jjtAccept (this, data);
            String dec1 = sv.get(node2);
            String var = "";
            String temp = "";
            if(dec1 ==  null){
                var = "@." + node1.jjtGetValue();
                stringDec = stringDec + var + " = constant [" + (node2.length() - 1) + " x i8] c" + node2.substring(0, node2.length() - 1) + "\\00\"" + "\n";
            }
            else{
                var = dec1;
            }
            temp = getTemp();
            prog = prog + temp + " = getelementptr [" +(node2.length() - 1) + " x i8], [" + (node2.length() - 1) + " x i8]*" + var + ", i64 0, i64 0\n";
            iv.put( node1.jjtGetValue().toString(),temp);
            sv.put(node2,var);
          }
        else{
          dec = "%.v" + node1.jjtGetValue() + " = alloca " + mt ;
          if(node.jjtGetNumChildren() > 2){
              String node2 = (String) node.jjtGetChild (2).jjtAccept (this, data);
              command = "store " + mt + " " + node2 + ", " + mt + "* " + "%.v" + node1.jjtGetValue() ;
              String temp = getTemp();
              load = temp + " = load " + mt + ", " + mt + "* " + "%.v" + node1.jjtGetValue();
              iv.put(node1.jjtGetValue().toString(),temp);
          }


          prog = prog + dec + "\n";
          prog = prog + command + "\n";
          prog = prog + load + "\n" ;



       }
          return null;
      }

      public Object visit(ASTassigns node, Object data){
          SimpleNode type = (SimpleNode)node.jjtGetParent().jjtGetChild(0);
          String sType =(String) type.jjtGetValue();
          DataType dType = st.getType(sType,scope);
          SimpleNode parentType = (SimpleNode)node.jjtGetParent() ;
          if(parentType.toString().equals("statement") && dType.toString().equals("string") ){
              SimpleNode var = (SimpleNode)node.jjtGetParent().jjtGetChild(0);
              String sVar1 = var.jjtGetValue().toString();
              String sVar = "@." + sVar1 + varInc.get(sVar1);
              SimpleNode value = (SimpleNode) node.jjtGetChild(0) ;
              String sValue = value.jjtGetValue().toString();
              String temp = getTemp();
              int len = (sValue.length() - 1);
              stringDec = stringDec + sVar + " = constant [" + len + " x i8] c" + sValue.substring(0, len) + "\\00\"" + "\n";;
              prog = prog + temp + " = getelementptr [" + len + " x i8], [" + len+ " x i8]*" + sVar + ", i64 0, i64 0\n";
              iv.put(sVar1,temp);
          }
          else if(sType.equals("string")){
              SimpleNode value = (SimpleNode) node.jjtGetChild(0) ;
              return value.jjtGetValue() ;
          }
          String node0 = (String) node.jjtGetChild(0).jjtAccept (this, data);
          String str[] = node0.split(" ");
          List<String> al = new ArrayList<String>();
          al = Arrays.asList(str);
          ArrayList<String> arith_op_l = new ArrayList<String>(al);
          String command = "";

          // multiply or division first then + or -
          for(int i = 0 ; i < arith_op_l.size() ; i ++ ){
              if(arith_op_l.get(i).equals("^")){
                  String temp = "" ;
                  String temp_1 = arith_op_l.get(i-1)  ;
                  if(arith_op_l.get(i+1).equals("0")){
                      temp = getTemp() ;
                      command = temp + " = mul i32 " + temp_1 + ", " + "0";
                      right(buff,command);
                  }
                  else if(arith_op_l.get(i+1).equals("1")){
                      temp = getTemp() ;
                      command = temp + " = mul i32 " + temp_1 + ", " + "1";
                      right(buff,command);
                  }
                  for(int j = 0 ; j < Integer.parseInt(arith_op_l.get(i+1)) - 1  ; j++){
                  temp = getTemp() ;
                  command = temp + " = mul i32 " + temp_1 + ", " + arith_op_l.get(i-1);
                  temp_1 = temp ;
                  right(buff,command);
              }
                  arith_op_l.set(i,temp);
                  arith_op_l.remove(i+1);
                  arith_op_l.remove(i-1);
                  i = 0 ;
              }
              else if(arith_op_l.get(i).equals( "%" ) && !arith_op_l.contains("^") && !arith_op_l.contains("/") ){
                  String temp = getTemp() ;
                  command = temp + " = sdiv i32 " + arith_op_l.get(i-1) + ", " + arith_op_l.get(i+1);
                  right(buff,command);
                  String temp_1 = temp;
                  temp = getTemp();
                  command = temp + " = mul i32 " +  arith_op_l.get(i+1)  + ", " + temp_1 ;
                  right(buff,command);
                  temp_1 = temp;
                  temp = getTemp();
                  command = temp + " = sub i32 " + arith_op_l.get(i-1) + ", " + temp_1 ;
                  right(buff,command);
                  arith_op_l.set(i,temp);
                  arith_op_l.remove(i+1);
                  arith_op_l.remove(i-1);
                  i = 0 ;
              }
              else if(arith_op_l.get(i).equals("*") && !arith_op_l.contains("^")  && !arith_op_l.contains("%") ){
                  String temp = getTemp() ;
                  command = temp + " = mul i32 " + arith_op_l.get(i-1) + ", " + arith_op_l.get(i+1);
                  arith_op_l.set(i,temp);
                  arith_op_l.remove(i+1);
                  arith_op_l.remove(i-1);
                  right(buff,command);
                  i = 0 ;
              }
              else if(arith_op_l.get(i).equals("/") && !arith_op_l.contains("^")){
                  String temp = getTemp() ;
                  command = temp + " = sdiv i32 " + arith_op_l.get(i-1) + ", " + arith_op_l.get(i+1);
                  arith_op_l.set(i,temp);
                  arith_op_l.remove(i+1);
                  arith_op_l.remove(i-1);
                  right(buff,command);
                  i = 0 ;
              }

              else if(!arith_op_l.contains("*")  && !arith_op_l.contains("/") && arith_op_l.get(i).equals("-")
              && !arith_op_l.contains("^")  && !arith_op_l.contains("%") ){
                  String temp = getTemp() ;
                  command = temp + " = sub i32 " + arith_op_l.get(i-1) + ", " + arith_op_l.get(i+1);
                  arith_op_l.set(i,temp);
                  arith_op_l.remove(i+1);
                  arith_op_l.remove(i-1);
                  right(buff,command);
                  i = 0 ;
              }
              else if(!arith_op_l.contains("*") && !arith_op_l.contains("/") && arith_op_l.get(i).equals("+")
             && !arith_op_l.contains("^")  && !arith_op_l.contains("%")  ){
                  String temp = getTemp() ;
                  command = temp + " = add i32 " + arith_op_l.get(i-1) + ", " + arith_op_l.get(i+1);
                  arith_op_l.set(i,temp);
                  arith_op_l.remove(i+1);
                  arith_op_l.remove(i-1);
                  right(buff,command);
                  i = 0 ;
              }
          }
          return arith_op_l.get(0);
      }
      public void right( BufferedWriter buff, String command){
         prog = prog + command + "\n";

      }
      public Object visit(ASTConstantDeclaration node, Object data){return null;}
      public Object visit(ASTfunction node, Object data){ return null;}
      public Object visit(ASTreturn_ node, Object data){ return null;}


      public Object visit(ASTType node, Object data) {
                  return node.value ;
              }

      public Object visit(ASTparameter_list node, Object data){return null;}
      public Object visit(ASTmain node, Object data){
          scope = "main" ;
           node.childrenAccept(this, data); //accepts nodes
          return null;}

      public Object visit(ASTstatement node, Object data){
          SimpleNode node0 = (SimpleNode)node.jjtGetChild(0);
          // printing
          if(node.jjtGetValue().equals("print")){

            String result = iv.get(node0.jjtGetValue().toString()); //printing an integer
            String var = node0.jjtGetValue().toString();
            DataType type = st.getType(var,scope);
            String sType = machineType(type.toString());
            if(sType.equals("i32")){
       		   prog = prog + "call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 ";
	       	   prog = prog +result + ") \n";
            }
            else if (sType.equals("i8*")){
                prog = prog +  "call i32 @puts (i8* ";
                prog = prog +result + ") \n";
            }
       	    }
          else if(node.jjtGetValue().equals("=")){
            String command =  "";
            String load = "";
            DataType type = st.getType(node0.jjtGetValue().toString(),scope);
            String mt = machineType(type.toString());
            if(mt.equals("i32")){
                String node1 = (String) node.jjtGetChild (1).jjtAccept (this, data);
                command = "store " + mt + " " + node1 + ", " + mt + "* " + "%.v" + node0.jjtGetValue() ;
                String temp = getTemp();
                load = temp + " = load " + mt + ", " + mt + "* " + "%.v" + node0.jjtGetValue();
                iv.put(node0.jjtGetValue().toString(),temp);
                prog = prog + command + "\n";
                prog = prog + load + "\n";
            }
            if(mt.equals("i8*")){
                String var = node0.jjtGetValue().toString();
                getStringDeclN(var);
                String node1 = (String) node.jjtGetChild (1).jjtAccept (this, data);

            }
      }
      else if(node.jjtGetValue().equals("if")){
              String command = "";
              String tempE = endLabel;
              String tempF = FalseLabel;
              String tempT = trueLabel;
              trueLabel = getLabel();
              FalseLabel = getLabel();
              endLabel = getLabel();


              boolean elseCheck = false ;
              String cond = (String)node.jjtGetChild(0).jjtAccept(this,data);
              command = "br i1 " + cond + ", label %" + trueLabel + ", label %" + FalseLabel ;
              prog = prog + command +"\n";
              prog = prog + trueLabel + ":" +   "\n";
              SimpleNode else_node ;
              for(int i = 1; i < node.jjtGetNumChildren(); i++){
                  else_node = (SimpleNode)node.jjtGetChild(i);
                  if(else_node.jjtGetValue().toString().equals("else")){
                      prog = prog + "br label %" + endLabel + "\n";
                      prog = prog + FalseLabel+":" + "\n";
                      node.jjtGetChild(i).jjtAccept(this,data);
                      prog = prog + "br label %" + endLabel + "\n";
                      prog = prog + endLabel + ":" + "\n" ;
                      elseCheck = true ;
                  }
                  else if(else_node.jjtGetValue().toString().equals("else_if")){
                      prog = prog + "br label %" + endLabel + "\n";
                      prog = prog + FalseLabel+":" + "\n";
                      trueLabel = getLabel();
                      FalseLabel = getLabel();
                      node.jjtGetChild(i).jjtAccept(this,data);
                  }

                  else{
                      node.jjtGetChild(i).jjtAccept(this,data);
                  }
              }
              if(!elseCheck){
                  prog = prog + "br label %" + endLabel + "\n";
                  prog = prog + FalseLabel+":" + "\n";
                  prog = prog + "br label %" + endLabel + "\n";
                  prog = prog + endLabel + ":" + "\n" ;

              }
             endLabel = tempE;
             trueLabel = tempT;
             FalseLabel = tempF;

          }

     else if(node.jjtGetValue().equals("else")){
         node.childrenAccept(this , data);
     }

      else if(node.jjtGetValue().equals("decl")){
          node0.jjtAccept(this,data);
      }
      return data;
}

      public Object visit(ASTelse_if node, Object data){
                  String command = "";
                  trueLabel = getLabel();
                  FalseLabel = getLabel();
                  String endLabel = getLabel();
                  String cond = (String)node.jjtGetChild(0).jjtAccept(this,data);
                  command = "br i1 " + cond + ", label %" + trueLabel + ", label %" + FalseLabel ;
                  prog = prog + command +"\n";
                  prog = prog + trueLabel + ":" +   "\n";
                  SimpleNode else_node ;
                  for(int i = 1; i < node.jjtGetNumChildren(); i++){
                          node.jjtGetChild(i).jjtAccept(this,data);
                  }
          return null;}
      public Object visit(ASTassignment node, Object data){
          return null;}
      public Object visit(ASTfunctionCall node, Object data){ return null;}
      public Object visit(ASTclass_call node, Object data){ return null;}

      public Object visit(ASTarith_op node, Object data){
          String command="";
          String result="";
          SimpleNode node0 = (SimpleNode)node.jjtGetChild(0);
          SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
          result = result +  node0.jjtAccept(this,data) + " "  + node.jjtGetValue() + " " + node1.jjtAccept(this,data) ;
          return result;

 }

      public Object visit(ASTcomp_op node, Object data){
          String comp = node.jjtGetValue().toString();
          String cOp;
          String command ;
          String temp ;
          String lhs ;
          String rhs ;
          String cType ;
          String mType;
          SimpleNode node0;
          SimpleNode node1;
          switch (comp)
	    {
	    case "==":
		cOp = "eq";
		break;

	    case "!=":
		cOp = "neq";
		break;

	    case ">":
		cOp = "sgt";
		break;

	    case ">=":
		cOp = "sge";
		break;

	    case "<":
		cOp = "slt";
		break;

	    case "<=":
		cOp = "sle";
		break;

	    default:
		cOp = "eq";
	    }
        node0 = (SimpleNode)node.jjtGetChild(0);
        DataType dType = st.getType(node0.jjtGetValue().toString(),scope);
        cType = dType.toString();
        mType = machineType(cType);
        temp = getTemp();
        lhs = node.jjtGetChild(0).jjtAccept(this,data).toString();
        rhs = node.jjtGetChild(1).jjtAccept(this,data).toString();
        command = temp + " = icmp " + cOp + " " + mType + " " + lhs + ", " + rhs ;
        prog = prog + command + "\n";

        return temp;}
      public Object visit(ASTandCondition node, Object data){ return null;}
      public Object visit(ASTorCondition node, Object data){return null;}
      public Object visit(ASTargumentList node, Object data){ return null;}
      public Object visit(ASTarray node, Object data){ return null;}
      public Object visit(ASTclass_ node, Object data){ return null;}
      public Object visit(ASTdecl_list node, Object data){ return null;}
      public Object visit(ASTIdentifier node, Object data){ return iv.get(node.value.toString());}
      public Object visit(ASTbreak_ node, Object data){ return null;}
      public Object visit(ASTNumber node, Object data){ return node.value;}
      public Object visit(ASTString node, Object data){
           return node.value;}
      public Object visit(ASTBoolean node, Object data){ return null;}
    }
