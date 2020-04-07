import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class IrCodeVisitor implements monaVisitor {
    private int tmpCount = 1;
    private IrVar iv = new IrVar();


    private String getTemp ()
    {
        String tmp = "%.t" + tmpCount;
        tmpCount = tmpCount + 1;
        return tmp;
    }
    static void beginIr (BufferedWriter buffer)
    {
        try
      {
          buffer.write ("declare i32 @printf(i8*, ...) #1");
          buffer.newLine ();
          buffer.write ("@.1arg_str = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1");
          buffer.newLine ();
          buffer.newLine ();
          buffer.write ("define i32 @main ()");
          buffer.newLine ();
          buffer.write ("{");
          buffer.newLine ();
      }
      catch (IOException e)
      {
          System.out.println ("Failed to write start of ir code to file. Exception: ");
          e.printStackTrace (System.out);
      }

      return;
    }

    static void endIr (BufferedWriter buffer)
    {
        try
      {
      buffer.write ("ret i32 0");
      buffer.newLine ();
      buffer.write ("}");
      buffer.newLine ();
        }
        catch (IOException e)
        {
          System.out.println ("Failed to write end of ir code to file. Exception: ");
          e.printStackTrace (System.out);
        }

        return;
    }
      public Object visit(SimpleNode node, Object data){ return null;}




      public Object visit(ASTProgram node, Object data){
          BufferedWriter buff = (BufferedWriter)data;

          beginIr (buff);
          node.childrenAccept(this, data); //accepts nodes
          endIr (buff);

           return null;}



      public Object visit(ASTVariableDeclaration node, Object data){
          String node2= (String) node.jjtGetChild (2).jjtAccept (this, data);
          DataType node0 = (DataType) node.jjtGetChild (0).jjtAccept (this, data);
          SimpleNode node1 = (SimpleNode) node.jjtGetChild (1);
          iv.put(node1.jjtGetValue().toString(),node2);
          return null;}

      public Object visit(ASTassigns node, Object data){
          String node0 = (String) node.jjtGetChild(0).jjtAccept (this, data);
          String str[] = node0.split(" ");
          List<String> al = new ArrayList<String>();
          al = Arrays.asList(str);
          ArrayList<String> arith_op_l = new ArrayList<String>(al);
          BufferedWriter buff = (BufferedWriter)data;
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
          try
          {
          buff.write (command);
          buff.newLine ();
      }
      catch (IOException e)
        {
               System.out.println ("Failed to write ir code for an expression to file. Exception: ");
               e.printStackTrace (System.out);
           }
      }
      public Object visit(ASTConstantDeclaration node, Object data){return null;}
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

      public Object visit(ASTparameter_list node, Object data){return null;}
      public Object visit(ASTmain node, Object data){
           node.childrenAccept(this, data); //accepts nodes
          return null;}

      public Object visit(ASTstatement node, Object data){
          BufferedWriter buff = (BufferedWriter)data;
          // printing
          if(node.jjtGetValue().equals("print")){
	try
       	    {
	       	SimpleNode node0 = (SimpleNode)node.jjtGetChild(0);
            String result = iv.get(node0.jjtGetValue().toString()); //printing an integer
       		buff.write ("call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 ");
	       	buff.write (result);
       		buff.write (")");
       		buff.newLine ();
       	    }
       	catch (IOException e)
       	    {
       		System.out.println ("Failed to write ir code for print to file. Exception: ");
       		e.printStackTrace (System.out);
       	    }
          }
          return data;
}

      public Object visit(ASTelse_ node, Object data){return null;}
      public Object visit(ASTassignment node, Object data){ return null;}
      public Object visit(ASTfunctionCall node, Object data){ return null;}
      public Object visit(ASTclass_call node, Object data){ return null;}

      public Object visit(ASTarith_op node, Object data){
          BufferedWriter buff = (BufferedWriter)data;
          String command="";
          String result="";
          SimpleNode node0 = (SimpleNode)node.jjtGetChild(0);
          SimpleNode node1 = (SimpleNode) node.jjtGetChild(1);
          result = result +  node0.jjtGetValue() + " "  + node.jjtGetValue() + " " + node1.jjtAccept(this,data) ;
          return result;

 }

      public Object visit(ASTcomp_op node, Object data){ return null;}
      public Object visit(ASTandCondition node, Object data){ return null;}
      public Object visit(ASTorCondition node, Object data){return null;}
      public Object visit(ASTargumentList node, Object data){ return null;}
      public Object visit(ASTarray node, Object data){ return null;}
      public Object visit(ASTclass_ node, Object data){ return null;}
      public Object visit(ASTdecl_list node, Object data){ return null;}
      public Object visit(ASTIdentifier node, Object data){ return null;}
      public Object visit(ASTbreak_ node, Object data){ return null;}
      public Object visit(ASTNumber node, Object data){ return node.value;}
      public Object visit(ASTString node, Object data){ return node.value;}
      public Object visit(ASTBoolean node, Object data){ return null;}
    }
