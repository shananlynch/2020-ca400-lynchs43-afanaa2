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
    private Map<String,String> listLenght = new HashMap<String, String>();
    private Map<String,String> listInsert = new HashMap<String, String>();
    private static int boolLabel = 0;
    private static String lv = "";
    private static int listInc = 0 ;

    /*
    getTemp funtion gets a temporary local variable
    */
    private String getTemp ()
    {
        String tmp = "%.t" + tmpCount;
        tmpCount = tmpCount + 1;
        return tmp;
    }

    private void printBool(String b){
        String temp = getTemp() ;
        String truel = getLabel();
        String falsel = getLabel();
        String endl = getLabel();
        prog = prog + temp + "= load i1 , i1* " + b + "\n";
        prog  = prog +  "br i1 " + temp + ", label %" + truel+ ", label %" + falsel +  " \n" ;
        prog = prog + truel+": \n";
        prog = prog + "call i32 @puts (i8* %.true) \n";
        prog = prog + "br label %" + endl + "\n";
        prog = prog + falsel + ": \n";
        prog = prog + "call i32 @puts (i8* %.false) \n";
        prog = prog + "br label %" + endl + "\n";
        prog = prog + endl + ": \n";
    }

    /*
    getStringDeclN this functions increments how many times a string is redeclared
    */
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

    /*This writes the beggining of the ir code file*/
    static void beginIr (BufferedWriter buffer)
    {
     prog = prog + "@.true = constant [5 x i8] c\"true\00\" \n";
     prog = prog + "@.false = constant [6 x i8] c\"false\00\" \n";
     prog = prog + "declare i32 @printf(i8*, ...) #1\n";
     prog =prog + "@.1arg_str = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1\n \n";
     prog = prog + "declare i32 @puts(i8*)\n" ;
     prog =prog + "define i32 @main () \n ";
     prog =prog + "{ \n";
     prog = prog + "%.true = getelementptr [5 x i8], [5 x i8]*@.true, i64 0, i64 0 \n";
     prog = prog + "%.false = getelementptr [6 x i8], [6 x i8]*@.false, i64 0, i64 0 \n";
      return;
    }

    /*This writes the end of the ir code file*/
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

    /* machineType gets machine type from a string type */
    public static String machineType (String type)
    {
        String mty;
        if (type.equalsIgnoreCase("Integer") || type.equalsIgnoreCase("Number")){
            mty = "i32";
	    }
        else if (type.equalsIgnoreCase("bool") || type.equalsIgnoreCase("boolean")){
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

    /* getlabel gets jump labels for ir */
    private String getLabel ()
    {
        String lbl = "label" + labelCount;
        labelCount = labelCount + 1;
        return lbl;
    }

      public Object visit(SimpleNode node, Object data){ return null;}

      /* Program node firs node called in here we set up the essential
      enviroment to start writing to Ir code */

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
          return null;
      }



      /* ASTVariableDeclaration node, This node is called evertime a variable is
      declares */
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

            /* string declaration */
            if(dec1 ==  null){
                var = "@." + node1.jjtGetValue();
                stringDec = stringDec + var + " = constant [" + (node2.length() - 1) + " x i8] c" + node2.substring(0, node2.length() - 1) + "\\00\"" + "\n";
            }
            else{
                var = dec1;
            }
            temp = getTemp();
            dec = "%.v" + node1.jjtGetValue() + " = alloca " + mt ;
            prog = prog + dec + "\n" ;
            prog = prog + temp + " = getelementptr [" +(node2.length() - 1) + " x i8], [" + (node2.length() - 1) + " x i8]*" + var + ", i64 0, i64 0\n";
            prog = prog + "store i8* " + temp + ", i8** %.v" + node1.jjtGetValue() + "\n";
            temp = getTemp();
            prog = prog + temp + " = load " + mt + ", " + mt + "* " + "%.v" + node1.jjtGetValue() + "\n";
            sv.put(node2,var);
            iv.put( node1.jjtGetValue().toString(),"%.v." + node1.jjtGetValue());
          }
           else if(node0.equals("[") && node.jjtGetNumChildren() > 2 ){
                SimpleNode sNode0 = (SimpleNode) node.jjtGetChild(0);
                node0 = (String) sNode0.jjtGetChild (0).jjtAccept (this, data);
                mt = machineType(node0) ;
                String num = (String) node.jjtGetChild (2).jjtAccept (this, data);
                String var = "%.l" + node1.jjtGetValue();
                dec = var + " = alloca [" +  num + " x " + mt + "]" ;
                prog = prog + dec + "\n" ;
                prog = prog + "store [" +  num + " x " + mt + "]" + lv + ", [" +  num + " x " + mt + "]*" + var;
                prog = prog +"\n" ;
                listInsert.put((String)node1.jjtGetValue(),lv.substring(0,lv.length()-1));

                iv.put(node1.jjtGetValue().toString(),var);
                listLenght.put(var,num);
           }
          else {
            dec = "%.v" + node1.jjtGetValue() + " = alloca " + mt ;
            prog = prog + dec + "\n";

            String node2 = "";
            if( node.jjtGetNumChildren() > 2 ){
                 node2 = (String) node.jjtGetChild (2).jjtAccept (this, data);
            }
            if(!node2.equals("getArray"))
            {
            if(node.jjtGetNumChildren() > 2){
              if(node2.equals("true")){
                  node2 = "1";
              }
              else if(node2.equals("false")){
                  node2 = "0" ;
              }
              command = "store " + mt + " " + node2 + ", " + mt + "* " + "%.v" + node1.jjtGetValue() ;
              String temp = getTemp();
              load = temp + " = load " + mt + ", " + mt + "* " + "%.v" + node1.jjtGetValue();
              iv.put(node1.jjtGetValue().toString(),temp);
          }
              prog = prog + command + "\n";
              prog = prog + load + "\n" ;
       }
    }
          return null;
      }

      /* Assings node is called eachtime we assign a variable */
      public Object visit(ASTassigns node, Object data){
          SimpleNode type = (SimpleNode)node.jjtGetParent().jjtGetChild(0);
          String sType =(String) type.jjtGetValue();
          DataType dType = st.getType(sType,scope);
          SimpleNode parentType = (SimpleNode)node.jjtGetParent() ;
          SimpleNode cNode = (SimpleNode)node.jjtGetChild(0);
          String get = (String) cNode.toString();
          if(parentType.toString().equals("statement") && dType.toString().equals("string") ){
              SimpleNode var = (SimpleNode)node.jjtGetParent().jjtGetChild(0);
              String sVar1 = var.jjtGetValue().toString();
              String sVar = "@." + sVar1 + varInc.get(sVar1);
              SimpleNode value = (SimpleNode) node.jjtGetChild(0) ;
              String sValue = value.jjtGetValue().toString();
              String temp = getTemp();
              int len = (sValue.length() - 1);
              String dec1 = sv.get(sValue);
              if(dec1 == null){
                  stringDec = stringDec + sVar + " = constant [" + len + " x i8] c" + sValue.substring(0, len) + "\\00\"" + "\n";;
              }
              else{
                  sVar = dec1 ;
              }
              prog = prog + temp + " = getelementptr [" + len + " x i8], [" + len+ " x i8]*" + sVar + ", i64 0, i64 0\n";
              prog = prog + "store i8* " + temp + ", i8** %.v" + sVar1 + "\n";
              iv.put(sVar1,temp);
          }
          else if(sType.equals("string")){
              SimpleNode value = (SimpleNode) node.jjtGetChild(0) ;
              return value.jjtGetValue() ;
          }
          else if(sType.equals("bool")){
              SimpleNode value = (SimpleNode) node.jjtGetChild(0) ;
              return value.jjtGetValue() ;
          }
          else if(sType.equals("[")){
              String value = (String) node.jjtGetChild(0).jjtAccept(this,data) ;
              return value;
          }


          else if(parentType.toString().equals("statement") &&
          dType.toString().equals("Bool") ){
             SimpleNode value = (SimpleNode) node.jjtGetChild(0) ;
             String sValue = (String)value.jjtGetValue();
             if(sValue.equals("true")){
                 return "1";
             }
             else{
                 return "0" ;
             }

          }
          /*Arrithmetic operations */
          else if (get.equals("getArray")){
              SimpleNode parentNode = (SimpleNode) node.jjtGetParent();
               cNode = (SimpleNode) node.jjtGetChild(0);
              SimpleNode var = (SimpleNode) parentNode.jjtGetChild(0);
              String sVar = var.jjtGetValue().toString();
              String mType="";
              String t ="" ;
              DataType dtype = st.getType(sVar,scope);
              mType = machineType(type.toString());
              if(parentNode.toString().equals("VariableDeclaration")){
                   var = (SimpleNode) parentNode.jjtGetChild(1);
                   sVar = var.jjtGetValue().toString();
                   t = (String) parentNode.jjtGetChild(0).jjtAccept(this,data).toString();
                   mType = machineType(t);

              }
              String mVar = "%.v" + sVar  ;
              String index = (String)cNode.jjtGetChild(1).jjtAccept(this,data);
              String list = (String)cNode.jjtGetChild(0).jjtAccept(this,data);
              String length = listLenght.get(list);
              String ptr = " [" + length + " x " + mType + "]";
              String temp = getTemp();
              prog = prog + temp + "= getelementptr" + ptr + ", "+ ptr + "* " + list + ","
              + mType + " 0," + mType  +" " + index + "\n" ;
              String temp2 = getTemp();
              prog = prog + temp2 + " = load " + mType + ", " + mType + "* " + temp +"\n";
              prog = prog + "store i32 " + temp2 + ", i32* " + mVar + "\n" ;

              return "getArray";
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
          if(node.jjtGetValue().equals("insert")){
             node0.jjtAccept(this,data);

          }
          if(node.jjtGetValue().equals("print")){
            String var = node0.jjtGetValue().toString();
            DataType type = st.getType(var,scope);
            String sType = machineType(type.toString());
            String isType =(String) node0.toString() ;
            var = "%.v"+ var ;
            if(isType.equalsIgnoreCase("string")){
                sType = "Undec";
                var =(String) node0.jjtAccept(this,data);
                prog = prog +  "call i32 @puts (i8* ";
                prog = prog +var + ") \n";
            }
            if(isType.equalsIgnoreCase("Boolean")){
                sType = "Undec";
                var =(String) node0.jjtAccept(this,data);
                if(var.equals("1")){
                    prog = prog + "call i32 @puts (i8* %.true) \n";
                }
                else{
                    prog = prog + "call i32 @puts (i8* %.false) \n";
                }
            }
            if(isType.equalsIgnoreCase("Number")){
                sType = "undec";
                var = node0.jjtGetValue().toString();
                prog = prog + "call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 ";
                prog = prog +var + ") \n";
            }
            if(sType.equals("i32")){
               String temp = getTemp();
               prog = prog + temp + "= load i32 , i32* " + var + "\n";
       		   prog = prog + "call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 ";
	       	   prog = prog +temp + ") \n";
            }

            else if (sType.equals("i8*")){
                String temp = getTemp();
                prog = prog + temp + "= load i8* , i8** " + var + "\n";
                prog = prog +  "call i32 @puts (i8* ";
                prog = prog +temp + ") \n";
            }

            else if (sType.equals("i1")){
                printBool(var);
                }
       	    }
          else if(node.jjtGetValue().equals("=")){
            String command =  "";
            String load = "";
            DataType type = st.getType(node0.jjtGetValue().toString(),scope);
            String mt = machineType(type.toString());

            if(mt.equals("i8*")){
                String var = node0.jjtGetValue().toString();
                getStringDeclN(var);
                String node1 = (String) node.jjtGetChild (1).jjtAccept (this, data);
            }
            else{
                String node1 = (String) node.jjtGetChild (1).jjtAccept (this, data);
                if(!node1.equals("getArray"))
                {
                command = "store " + mt + " " + node1 + ", " + mt + "* " + "%.v" + node0.jjtGetValue() ;
                String temp = getTemp();
                load = temp + " = load " + mt + ", " + mt + "* " + "%.v" + node0.jjtGetValue();
                iv.put(node0.jjtGetValue().toString(),temp);
                prog = prog + command + "\n";
                prog = prog + load + "\n";
            }
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
      else if(node.jjtGetValue().equals("while")){
            String conditionLabel = getLabel ();
            String trueLabel = getLabel ();
            String endLabel = getLabel ();
            prog = prog + "br label %" + conditionLabel + "\n" + conditionLabel + ":" + "\n";     // end the previous basic block and start a new one
            String cond = (String)node.jjtGetChild(0).jjtAccept(this,data); //
            prog = prog + "br i1 " + cond + ", label %" + trueLabel + ", label %" + endLabel + "\n";
            prog = prog + trueLabel + ":" +"\n";
            for(int i = 1; i < node.jjtGetNumChildren(); i++){
                node.jjtGetChild (i).jjtAccept (this, data);
            }                                 // While true Block
            prog = prog +  "br label %" + conditionLabel + "\n";
		    prog = prog + endLabel + ":" + "\n";
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
		cOp = "ne";
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
        // gives a variable a boolean value from condition
        node0 = (SimpleNode)node.jjtGetChild(0);
        String node0T = (String) node0.toString();
        DataType dType = st.getType(node0.jjtGetValue().toString(),scope);
        cType = dType.toString();
        mType = machineType(cType);
        if(node0T.equals("String")){
                mType = "i8*";
        }
        temp = getTemp();
        lhs = node.jjtGetChild(0).jjtAccept(this,data).toString();
        rhs = node.jjtGetChild(1).jjtAccept(this,data).toString();
        command = temp + " = icmp " + cOp + " " + mType + " " + lhs + ", " + rhs ;
        prog = prog + command + "\n";

        return temp;
        }
      public Object visit(ASTandCondition node, Object data){
           SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
           SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
           String s0 = (String) node0.jjtAccept(this,data);
           String s1 = (String) node1.jjtAccept(this,data);
           String temp = getTemp();
           prog = prog + temp +  " = and i1 " + s0 + ", " + s1 + "\n";
           return temp;
       }
      public Object visit(ASTorCondition node, Object data){
          SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
          SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
          String s0 = (String) node0.jjtAccept(this,data);
          String s1 = (String) node1.jjtAccept(this,data);
          String temp = getTemp();
          prog = prog + temp +  " = or i1 " + s0 + ", " + s1 + "\n";
          return temp;
      }
      public Object visit(ASTargumentList node, Object data){ return null;}
      public Object visit(ASTarray node, Object data){
          int numChildren = node.jjtGetNumChildren();
          if(numChildren == 0){
              lv="[]";
              return "0";
              }
          String num = Integer.toString(numChildren);
          String nodeN ;
          String nodeT = (String)node.jjtGetChild(0).toString();
          String mtype = machineType(nodeT);
          lv = "[";
          for(int i = 0; i < numChildren ; i ++ ){
              nodeN = (String) node.jjtGetChild(i).jjtAccept(this,data);
              if(i == 0){
                  lv = lv + mtype + " " + nodeN;
              }
              else{
                  lv = lv + "," + mtype + " " +  nodeN;
              }
          }
          lv = lv +"]";
          return num ;

      }
        public Object visit(ASTclass_ node, Object data){ return null;}
      public Object visit(ASTdecl_list node, Object data){ return null;}

      public Object visit(ASTIdentifier node, Object data){

          DataType dType = st.getType(node.jjtGetValue().toString(),scope);
          String cType = dType.toString();
          if(cType.equalsIgnoreCase("TypeUnknown")){
              return iv.get(node.value.toString());
          }
          String mt = machineType(cType);
          String temp = getTemp();
          String load = temp + " = load " + mt + ", " + mt + "* " + "%.v" + node.jjtGetValue();
          prog = prog + load + "\n" ;
          iv.put(node.jjtGetValue().toString(),temp);
          return iv.get(node.value.toString());
      }
      public Object visit(ASTbreak_ node, Object data){ return null;}
      public Object visit(ASTgetArray node, Object data){
          String l = (String) node.jjtGetChild(0).jjtAccept(this,data);
          String n = (String) node.jjtGetChild(1).jjtAccept(this,data);
          String length = listLenght.get(l);
          return l;
      }
      public Object visit(ASTinsert node, Object data){
           SimpleNode val = (SimpleNode) node.jjtGetChild(0);
           String sVar = (String) val.jjtGetValue();
           DataType dtype = st.getType(sVar,scope);
           String mt = machineType(dtype.toString());
           String num = listLenght.get(iv.get(sVar));
           num = Integer.toString(Integer.parseInt(num) + 1) ;
           SimpleNode node0 = (SimpleNode) node.jjtGetChild(1);
           String newElement = ( String) node0.jjtAccept(this,data);
           if( listInsert.get(sVar).length() > 2 ){
               lv = listInsert.get(sVar) + ", " + mt + " " + newElement + "]" ;
           }
           else{
              lv = listInsert.get(sVar) + mt + " " + newElement + "]" ;
          }
           listInsert.put(sVar,lv.substring(0,lv.length() - 1));

           String var = "%.l" + sVar + listInc;
           prog = prog + var + " = alloca [" +  num + " x " + mt + "]" ;
           prog = prog +  "\n" ;
           prog = prog + "store [" +  num + " x " + mt + "]" + lv + ", [" +  num + " x " + mt + "]*" + var;
           prog = prog +"\n" ;
           iv.put(sVar,var);
           listLenght.put(var,num);
           listInc ++ ;
           return null;}

      public Object visit(ASTgetLength node, Object data){
          String l = (String) node.jjtGetChild(0).jjtAccept(this,data);
          String length = listLenght.get(l);
          return length;
      }
      public Object visit(ASTNumber node, Object data){ return node.value;}
      public Object visit(ASTString node, Object data){
          SimpleNode nodeParent = (SimpleNode) node.jjtGetParent();
          String parent = (String) nodeParent.jjtGetValue();
          if(parent.equals("assigns")){
              return node.value ;
          }
          String str = (String)node.jjtGetValue();
          String dec1 = sv.get(str);
          String var = "";
          String temp = "";
          /* string declaration */
          if(dec1 ==  null){
            getStringDeclN("undec");
            int v = varInc.get("undec");
            var = "@." + v;
            stringDec = stringDec + var + " = constant [" + (str.length() - 1) + " x i8] c" + str.substring(0, str.length() - 1) + "\\00\"" + "\n";
          }
          else{
              var = dec1;
          }
            temp = getTemp();
            String mem =  getTemp() ;
            String dec =  mem + " = alloca " + "i8*" ;
            prog = prog + dec + "\n" ;
            prog = prog + temp + " = getelementptr [" +(str.length() - 1) + " x i8], [" + (str.length() - 1) + " x i8]*" + var + ", i64 0, i64 0\n";
            prog = prog + "store i8* " + temp + ", i8** " + mem + "\n";
            temp = getTemp();
            prog = prog + temp + " = load " +  " i8* , " +  "i8** "  + mem + "\n";
            sv.put(str,var);
            iv.put( mem ,mem);
            return temp;
      }

      public Object visit(ASTBoolean node, Object data){
           String val = (String) node.value;
           if(val.equals("true")){
               return "1";
           }
           return "0" ;
    }
}
