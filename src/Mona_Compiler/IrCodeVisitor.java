import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.FileInputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
    private Map<String,String> listType = new HashMap<String, String>();
    private Map<String,String> listParam = new HashMap<String, String>();
    private static int boolLabel = 0;
    private static String lv = "";
    private static String currentList = "";
    private static String r = "";
    private static String list = "";
    private static int listInc = 0 ;
    private static String global = "";
    private static String listT = "" ;
    private static String rlen = "";
    private Map<String,String> isChar = new HashMap<String, String>();
    List<String> stringDeca = new ArrayList<String>();

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
    private static void stringCompareFunction()throws  java.io.FileNotFoundException,IOException
    {
         try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("StringCompare"), StandardCharsets.UTF_8));) {

            String line;

            while ((line = br.readLine()) != null) {

               buff.write(line);
               buff.write("\n");
            }
        }

    }

    private static void builtInModFunction()throws  java.io.FileNotFoundException,IOException
   {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
               new FileInputStream("BuilInMod"), StandardCharsets.UTF_8));) {

           String line;

           while ((line = br.readLine()) != null) {

              buff.write(line);
              buff.write("\n");
           }
       }
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
    private void replaceList(String num1, String num2, String  mt, String var , String newElement ){
        String temp = getTemp();
        prog = prog + temp +
        " = getelementptr " + "[" +  "256" + " x " + mt + "],"  + "[" +  "256" + " x " + mt + "]* "
        + var + ", " +  "i32 0 ," +" i32 " + num1 +   "\n" ;
        prog = prog + "store " + mt + " " +  newElement + " , " + mt + "* " +  temp + "\n";
    }

    /*This writes the beggining of the ir code file*/
    static void beginIr (BufferedWriter buffer)
    {
     prog = prog + "@.true = constant [5 x i8] c\"true\00\" \n";
     prog = prog + "@.false = constant [6 x i8] c\"false\00\" \n";
     prog = prog + "@.newline1098019 = constant [2 x i8] c\" \00\" \n";

     prog = prog + "declare i32 @printf(i8*, ...) #1\n";
     prog =prog + "@.1arg_str = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1\n \n";
     prog =prog + "@.1arg_ = private unnamed_addr constant [3 x i8] c\"%f\\00\", align 1\n \n";

     prog = prog + "declare i32 @puts(i8*)\n" ;
      return;
    }

    /*This writes the end of the ir code file*/
    static void endIr (BufferedWriter buffer)
    {
        try
      {
      prog = prog + "ret i32 0 \n" ;
      prog = prog + "}\n";
      buff.write(global);
      buff.write(stringDec);
      builtInModFunction();
      stringCompareFunction();
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
        else if (type.equalsIgnoreCase ("string")){
            mty = "[20 x i8]";
	    }
         else if (type.equalsIgnoreCase ("float")){
            mty = "double";
	    }
        else if (type.equalsIgnoreCase ("void"))
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
          SimpleNode node1 = (SimpleNode) node.jjtGetChild (1);
          String node0 = (String)( (SimpleNode) node.jjtGetChild (0)).jjtGetValue();

          String mt = machineType(node0) ;

          if(scope.equals("global")){
               global = global + "@." + node1.jjtGetValue() + " = global " + mt + " " +((String) node.jjtGetChild (2).jjtAccept (this, data)) ;
               global = global + "\n";
               iv.put(node1.jjtGetValue().toString(),"@." + node1.jjtGetValue());
          }
          else {
          if(node0.equals("string") && node.jjtGetNumChildren() > 2 ){
            prog = prog + dec + "\n" ;
            prog = prog + "%." + node1.jjtGetValue() + "= alloca [20 x i8] \n";

            String node2 = (String) node.jjtGetChild (2).jjtAccept (this, data);

            if(!node2.equals("getArray")){
            String dec1 = sv.get(node2);
            String var = "";
            String temp = "";

            /* string declaration */

           var = "@." + node1.jjtGetValue();
           stringDec = stringDec + var + " = constant [" + (node2.length() - 1) + " x i8] c" + node2.substring(0, node2.length() - 1) + "\\00\"" + "\n";
           listLenght.put( var , (node2.length() - 2 ) + "" ) ;

            temp = getTemp();
            prog = prog + temp + " = getelementptr [" +(node2.length() - 1) + " x i8], [" + (node2.length() - 1) + " x i8]*" + var + ", i64 0, i64 0\n";
            String temp1 = getTemp();
            prog = prog +  temp1 + " = bitcast i8* " + temp + " to [20 x i8]* \n";
            String temp2 = getTemp() ;
            prog = prog + temp2 + " = load [20 x i8] , [20 x i8]* " + temp1 + "\n";
            prog = prog + "store [20 x i8] " + temp2 + ",  [20 x i8]* " + "%." + node1.jjtGetValue() +  "\n";
            global = global + "@." + scope + node1.jjtGetValue()  + "len = global i32 "+ (node2.length() - 2) + "\n";

            sv.put(node2,"%." + node1.jjtGetValue());
            iv.put( node1.jjtGetValue().toString(),"%." +   node1.jjtGetValue());
            listLenght.put( "@." + node1.jjtGetValue() , (node2.length() - 2  ) + "" ) ;

          }
          }

           else if(node0.equals("[")  ){
                String var = "%." +  node1.jjtGetValue();
                currentList = var ;
                listT=machineType(((SimpleNode)node.jjtGetChild(0)).jjtAccept(this,data)+ "");
                listType.put(var,listT);
                global = global + "@." + scope + currentList.substring(2) + "len = global i32 0\n";
                rlen = "@." + scope + currentList.substring(2) + "len" ;
                 String mtype = machineType(((SimpleNode)node.jjtGetChild(0)).jjtAccept(this,data)+ "") ;
                 String type = "[ " + "256" + " x " + mtype + " ]";
                 prog  = prog + currentList + " = alloca " + type + " \n" ;
                if(node.jjtGetNumChildren() > 2 ){

                    String node2 = (String) node.jjtGetChild (2).jjtAccept (this, data);
                }
                iv.put(node1.jjtGetValue().toString(),var);
           }
          else {
            if(mt.equals("i8*")){
                 mt = "[20 x i8]";
            }
            dec = "%." +  node1.jjtGetValue() + " = alloca " + mt ;
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
              String var = "%." + node1.jjtGetValue() ;
              if(st.in_scope(node1.jjtGetValue()+"","global")){
                    var = "@." + node1.jjtGetValue() ;

              }
              command = "store " + mt + " " + node2 + ", " + mt + "* " + var ;
              String temp = getTemp();
              load = temp + " = load " + mt + ", " + mt + "* " +var;
              iv.put(node1.jjtGetValue().toString(),temp);
          }
              prog = prog + command + "\n";
              prog = prog + load + "\n" ;
       }
    }
     }
          return null;
      }
      public Object visit(ASTgetChar node, Object data){
           String var =(String) ((SimpleNode) node.jjtGetChild(0)).jjtGetValue();
           String num =  listLenght.get("@."+((SimpleNode) node.jjtGetChild(0)).jjtGetValue());
           String temp = getTemp();
           String in = ( ((SimpleNode) node.jjtGetChild(1))).jjtAccept(this,data) + " ";
           prog = prog + temp + "= getelementptr [ 20 x i8 ] , [20 x i8]* %." + var + ", i32 0 , i32 " + in ;
           prog = prog + "\n" ;
           String temp2 = getTemp();
           prog = prog + temp2 + " = load i8 , i8* " + temp + " \n";
            return temp2;}

      /* Assings node is called eachtime we assign a variable */
      public Object visit(ASTassigns node, Object data){
          SimpleNode type = (SimpleNode)node.jjtGetParent().jjtGetChild(0);
          String sType =(String) type.jjtGetValue();
          DataType dType = st.getType(sType,scope);
          SimpleNode parentType = (SimpleNode)node.jjtGetParent() ;
          SimpleNode cNode = (SimpleNode)node.jjtGetChild(0);
          String get = (String) cNode.toString();
          if(get.equals("getChar")){
               String cha = (String)node.jjtGetChild(0).jjtAccept(this,data);
               String store =((SimpleNode) parentType.jjtGetChild(1)).jjtGetValue() + "";
               String temp = getTemp();
               prog = prog + temp +  " = alloca i8 \n";
               prog = prog + " store i8 " + cha+ ", i8* " + temp + " \n";
               isChar.put("%." + store,temp);
               String temp2 = getTemp();
               prog = prog + temp2 + " = bitcast i8* "  + temp + " to  [20 x i8 ]* \n" ;
               String temp3 = getTemp();
               prog = prog + temp3 + " = load [20 x i8 ]  ,[20 x i8 ]* "  + temp2 + "\n";
               prog = prog + " store [20 x i8 ] " + temp3 + ", [20 x i8 ]* %." + store + " \n";


               return("getArray");

          }


          if(parentType.toString().equals("statement") && dType.toString().equals("string") && !get.equals("getArray")){
               SimpleNode val = (SimpleNode) node.jjtGetChild(0) ;
               String fc = "" + val;
               if(fc.equals("functionCall")){
                    SimpleNode parentNode = (SimpleNode) node.jjtGetParent();
                    String var = (String)((SimpleNode)(parentNode.jjtGetChild(0))).jjtGetValue();
                    var =" %." + var;
                    String value = (String) val.jjtAccept(this,data);
                    String t = "i8*";
                    prog = prog + "store [20 x i8] " + value + ",  [20 x i8]* " + var+  "\n";
                    return("getArray");

               }
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
              String temp1 = getTemp();
              prog = prog +  temp1 + " = bitcast i8* " + temp + " to [20 x i8]* \n";
              String temp2 = getTemp() ;
              prog = prog + temp2 + " = load [20 x i8] , [20 x i8]* " + temp1 + "\n";
              prog = prog + "store [20 x i8] " + temp2 + ",  [20 x i8]* %." + sVar1 +  "\n";

              iv.put(sVar1,temp);
          }
          else if(sType.equals("string") && !get.equals("getArray")){
              SimpleNode val = (SimpleNode) node.jjtGetChild(0) ;
              String fc = "" + val;
              if(fc.equals("functionCall")){
                   SimpleNode parentNode = (SimpleNode) node.jjtGetParent();
                   String var = (String)((SimpleNode)(parentNode.jjtGetChild(1))).jjtGetValue();
                   var =" %." + var;
                   String value = (String) val.jjtAccept(this,data);
                   String t = "i8*";

                   prog = prog + "store [20 x i8] " + value + ",  [20 x i8]* " + var+  "\n";
                   return("getArray");

              }
              return val.jjtGetValue() ;
          }
          else if(sType.equals("bool")){
              SimpleNode value = (SimpleNode) node.jjtGetChild(0) ;
              if((value+"").equals("functionCall")){
                   return value.jjtAccept(this,data);
              }
              return value.jjtGetValue() ;
          }
          else if((sType.equals("[")) && !get.equals("functionCall")){
              String value =  (String) node.jjtGetChild(0).jjtAccept(this,data) ;
              currentList = value ;

              return "Array";
          }
          else if((sType.equals("[") || (dType+ "").equals("Array")) && get.equals("functionCall")){
              String value =  (String) node.jjtGetChild(0).jjtAccept(this,data) ;
              //TODO
              String temp = getTemp() ;
              String t = "[ " + "256" + " x " + listT + " ]";
              prog = prog + temp + "= load " + t + ", " + t + "* " + value +  " \n" ;
              prog = prog + "store " + t + " " + temp + " , " + t + "* " + currentList + "\n";
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
              mType = machineType(dtype.toString());
          //    System.out.println(dtype.toString());
              if(parentNode.toString().equals("VariableDeclaration")){
                   var = (SimpleNode) parentNode.jjtGetChild(1);
                   sVar = var.jjtGetValue().toString();
                   t = (String) parentNode.jjtGetChild(0).jjtAccept(this,data).toString();
                   mType = machineType(t);

              }
              String mVar = "%." +  sVar  ;
              String index = (String)cNode.jjtGetChild(1).jjtAccept(this,data);
              String list = (String)cNode.jjtGetChild(0).jjtAccept(this,data);
              String ptr = " [" + "256" + " x " + mType + "]";
              String temp = getTemp();
              prog = prog + temp + "= getelementptr" + ptr + ", "+ ptr + "* " + list + ","
              + "i32  0," + "i32 " +  index + "\n" ;
              String temp2 = getTemp();
              prog = prog + temp2 + " = load " + mType + ", " + mType + "* " + temp +"\n";
              prog = prog + "store " + mType + temp2 + ", " + mType + "* " + mVar + "\n" ;

              return "getArray";
          }
          else if(get.equals("functionCall")){
              SimpleNode n = (SimpleNode) node.jjtGetChild(0);
              String temp =(String) n.jjtAccept(this,data);
          }
          else if(get.equals("getStringLength")){
               SimpleNode n = (SimpleNode) node.jjtGetChild(0);
               String temp =(String) n.jjtAccept(this,data);
               return temp ;
          }


          String node0 = (String) node.jjtGetChild(0).jjtAccept (this, data);
          String str[] = node0.split(" ");
          List<String> al = new ArrayList<String>();
          al = Arrays.asList(str);
          ArrayList<String> arith_op_l = new ArrayList<String>(al);
          String command = "";
          String mType ;
          String f = "";

          if((parentType+"").equals("statement")){
               mType = machineType(dType+"") ;
          }
          else{
               mType = machineType(sType);
          }
          if(mType.equals("double")){
               f = "f";

          }
          // multiply or division first then + or -
          for(int i = 0 ; i < arith_op_l.size() ; i ++ ){
              if(arith_op_l.get(i).equals("^")){
                  String temp = "" ;
                  String temp_1 = arith_op_l.get(i-1)  ;
                  if(arith_op_l.get(i+1).equals("0")){
                      temp = getTemp() ;
                      command = temp + " =" + f + "mul "  +  mType  + temp_1 + ", " + "0";
                      right(buff,command);
                  }
                  else if(arith_op_l.get(i+1).equals("1")){
                      temp = getTemp() ;
                      command = temp + " =" + f + "mul " + mType +  " " + temp_1 + ", " + "1";
                      right(buff,command);
                  }
                  for(int j = 0 ; j < Integer.parseInt(arith_op_l.get(i+1)) - 1  ; j++){
                  temp = getTemp() ;
                  command = temp + " = " + f + "mul " +  mType + " "+ temp_1 + ", " + arith_op_l.get(i-1);
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
                  String fOrS = "s";
                  if (mType.equals("double")){
                      fOrS = "f";
                      String a = getTemp();
                      String b = getTemp();
                      temp = temp ;
                      prog = prog + a + " = alloca double " +  " \n";
                      prog = prog + b + " = alloca double " +" \n";
                      prog = prog +  "store double " + arith_op_l.get(i-1) + " , double* "  + a + " \n";
                      prog = prog + " store double " + arith_op_l.get(i+1) + " , double* "  + b + " \n";
                      command = temp + " = call double @BuiltInFindMod (double* " + a + ", double* " + b+")";
                      right(buff,command);
                      arith_op_l.set(i,temp);
                      arith_op_l.remove(i+1);
                      arith_op_l.remove(i-1);
                      i = 0 ;
                 }
                 else{
                  command = temp + " = " + fOrS + "div "+ mType + " " + arith_op_l.get(i-1) + ", " + arith_op_l.get(i+1);
                  right(buff,command);
                  String temp_1 = temp;
                  temp = getTemp();
                  command = temp + " = " + f +"mul "+ mType + " " +  arith_op_l.get(i+1)  + ", " + temp_1 ;
                  right(buff,command);
                  temp_1 = temp;
                  temp = getTemp();
                  command = temp + " = " + f + "sub "+ mType + " "+ arith_op_l.get(i-1) + ", " + temp_1 ;
                  right(buff,command);
                  arith_op_l.set(i,temp);
                  arith_op_l.remove(i+1);
                  arith_op_l.remove(i-1);
                  i = 0 ;
               }
              }
              else if(arith_op_l.get(i).equals("*") && !arith_op_l.contains("^")  && !arith_op_l.contains("%") ){
                  String temp = getTemp() ;
                  command = temp + " = " + f + "mul "+ mType + " " + arith_op_l.get(i-1) + ", " + arith_op_l.get(i+1);
                  arith_op_l.set(i,temp);
                  arith_op_l.remove(i+1);
                  arith_op_l.remove(i-1);
                  right(buff,command);
                  i = 0 ;
              }
              else if(arith_op_l.get(i).equals("/") && !arith_op_l.contains("^")){
                  String temp = getTemp() ;
                  String fOrS = "s";
                  if (mType.equals("double")){
                       fOrS = "f";
                  }
                  command = temp + " = " + fOrS + "div "+ mType + " " + arith_op_l.get(i-1) + ", " + arith_op_l.get(i+1);
                  arith_op_l.set(i,temp);
                  arith_op_l.remove(i+1);
                  arith_op_l.remove(i-1);
                  right(buff,command);
                  i = 0 ;
              }

              else if(!arith_op_l.contains("*")  && !arith_op_l.contains("/") && arith_op_l.get(i).equals("-")
              && !arith_op_l.contains("^")  && !arith_op_l.contains("%") ){
                  String temp = getTemp() ;
                  command = temp + " = " + f + "sub "+ mType + " " + arith_op_l.get(i-1) + ", " + arith_op_l.get(i+1);
                  arith_op_l.set(i,temp);
                  arith_op_l.remove(i+1);
                  arith_op_l.remove(i-1);
                  right(buff,command);
                  i = 0 ;
              }
              else if(!arith_op_l.contains("*") && !arith_op_l.contains("/") && arith_op_l.get(i).equals("+")
             && !arith_op_l.contains("^")  && !arith_op_l.contains("%")  ){
                  String temp = getTemp() ;
                  command = temp + " = " + f + "add "+ mType + " " + arith_op_l.get(i-1) + ", " + arith_op_l.get(i+1);
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
      public Object visit(ASTConstantDeclaration node, Object data){
           String dec = "";
           String command =  "";
           String load = "";
           SimpleNode node1 = (SimpleNode) node.jjtGetChild (1);
           String node0 = (String)( (SimpleNode) node.jjtGetChild (0)).jjtGetValue();

           String mt = machineType(node0) ;

           if(scope.equals("global")){
                global = global + "@." + node1.jjtGetValue() + " = global " + mt + " " +((String) node.jjtGetChild (2).jjtAccept (this, data)) ;
                global = global + "\n";
                iv.put(node1.jjtGetValue().toString(),"@." + node1.jjtGetValue());
           }
           else {
           if(node0.equals("string") && node.jjtGetNumChildren() > 2 ){
             prog = prog + dec + "\n" ;
             prog = prog + "%." + node1.jjtGetValue() + "= alloca [20 x i8] \n";

             String node2 = (String) node.jjtGetChild (2).jjtAccept (this, data);

             if(!node2.equals("getArray")){
             String dec1 = sv.get(node2);
             String var = "";
             String temp = "";

             /* string declaration */

            var = "@." + node1.jjtGetValue();
            stringDec = stringDec + var + " = constant [" + (node2.length() - 1) + " x i8] c" + node2.substring(0, node2.length() - 1) + "\\00\"" + "\n";
            listLenght.put( var , (node2.length() - 2 ) + "" ) ;

             temp = getTemp();
             prog = prog + temp + " = getelementptr [" +(node2.length() - 1) + " x i8], [" + (node2.length() - 1) + " x i8]*" + var + ", i64 0, i64 0\n";
             String temp1 = getTemp();
             prog = prog +  temp1 + " = bitcast i8* " + temp + " to [20 x i8]* \n";
             String temp2 = getTemp() ;
             prog = prog + temp2 + " = load [20 x i8] , [20 x i8]* " + temp1 + "\n";
             prog = prog + "store [20 x i8] " + temp2 + ",  [20 x i8]* " + "%." + node1.jjtGetValue() +  "\n";
             global = global + "@." + scope + node1.jjtGetValue()  + "len = global i32 "+ (node2.length() - 2) + "\n";

             sv.put(node2,"%." + node1.jjtGetValue());
             iv.put( node1.jjtGetValue().toString(),"%." +   node1.jjtGetValue());
             listLenght.put( "@." + node1.jjtGetValue() , (node2.length() - 2  ) + "" ) ;

           }
           }

            else if(node0.equals("[")  ){
                 String var = "%." +  node1.jjtGetValue();
                 currentList = var ;
                 listT=machineType(((SimpleNode)node.jjtGetChild(0)).jjtAccept(this,data)+ "");
                 listType.put(var,listT);
                 global = global + "@." + scope + currentList.substring(2) + "len = global i32 0\n";
                 rlen = "@." + scope + currentList.substring(2) + "len" ;
                  String mtype = machineType(((SimpleNode)node.jjtGetChild(0)).jjtAccept(this,data)+ "") ;
                  String type = "[ " + "256" + " x " + mtype + " ]";
                  prog  = prog + currentList + " = alloca " + type + " \n" ;
                 if(node.jjtGetNumChildren() > 2 ){

                     String node2 = (String) node.jjtGetChild (2).jjtAccept (this, data);
                 }
                 iv.put(node1.jjtGetValue().toString(),var);
            }
           else {
             if(mt.equals("i8*")){
                  mt = "[20 x i8]";
             }
             dec = "%." +  node1.jjtGetValue() + " = alloca " + mt ;
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
               String var = "%." + node1.jjtGetValue() ;
               if(st.in_scope(node1.jjtGetValue()+"","global")){
                     var = "@." + node1.jjtGetValue() ;

               }
               command = "store " + mt + " " + node2 + ", " + mt + "* " + var ;
               String temp = getTemp();
               load = temp + " = load " + mt + ", " + mt + "* " +var;
               iv.put(node1.jjtGetValue().toString(),temp);
           }
               prog = prog + command + "\n";
               prog = prog + load + "\n" ;
        }
     }
      }
           return null;
     }
      public Object visit(ASTfunction node, Object data){
          SimpleNode sty = (SimpleNode)node.jjtGetChild (0)   ;       // Type
          SimpleNode sid = (SimpleNode)node.jjtGetChild (1)    ;      // Identifier
          String ty = (String)sty.jjtGetValue()   ;       // Type
          String mt = machineType(ty);
          if(ty.equals("[")){
               ty = (String) sty.jjtAccept(this,data);
               ty = machineType(ty);
               String returnlen =  "" + sid.jjtGetValue();
               global = global + "@." + returnlen + "len = global i32 0 \n";
               mt = "[ 256 x " + ty + "]*" ;
          }
          String id = (String)sid.jjtGetValue()   ;      // Identifier
          scope = id ;
          String argsString = (String)node.jjtGetChild (2).jjtAccept (this, data);
          prog = prog + "define " + mt + " " + "@" + id + "( " + argsString + " ) \n { \n" ;
          prog = prog + list + "\n";
          prog = prog + "%.true = getelementptr [5 x i8], [5 x i8]*@.true, i64 0, i64 0 \n";
          prog = prog + "%.false = getelementptr [6 x i8], [6 x i8]*@.false, i64 0, i64 0 \n";
          prog = prog + "%.newline1098019 = getelementptr [2 x i8], [2 x i8]*@.newline1098019, i64 0, i64 0 \n";
          r = mt ;

          for(int i = 3; i < node.jjtGetNumChildren(); i ++){
            node.jjtGetChild (i).jjtAccept (this, data);

        }
          if(mt.equals("void")){

                prog = prog + "ret void \n";
          }

          prog = prog + "}\n";
          list = "";
          return null;}
      public Object visit(ASTreturn_ node, Object data){
          String sty = (String)node.jjtGetChild(0).jjtAccept(this,data)   ;       // Type
          DataType dt ;
          String t ;
          if(sty.length() > 1){
           dt =  st.getType(sty.substring(2),scope);
           t = "" + dt;

          if(t.equals("Array")){
               String temp = getTemp();
               prog = prog + temp + " = load i32, i32* @." + scope + sty.substring(2) + "len \n";
               prog = prog + "store i32 " + temp + " , i32* @." + scope + "len \n";
          }
          }
          prog = prog + "ret " + r + " " + sty + "\n";
          return null;}


      public Object visit(ASTType node, Object data) {
                  String t = (String)node.value ;
                  if(t.equals("["))
                  {
                      return node.jjtGetChild(0).jjtAccept(this,data) ;
                  }
                  return node.value ;
              }

      public Object visit(ASTparameter_list node, Object data){
          String arg = "";
          int j = 0 ;
          for(int i = 0 ; i <( node.jjtGetNumChildren()); i  = i + 2){
          SimpleNode sty = (SimpleNode)node.jjtGetChild (0 + i)   ;       // Type
          SimpleNode sid = (SimpleNode)node.jjtGetChild (1 +i )    ;      // Identifier
          String ty = (String)sty.jjtGetValue()   ;       // Type
          String id = "%." + (String)sid.jjtGetValue()   ;      // Identifier
          String mty = machineType(ty);
          if(ty.equals("[")){
              ty = (String) sty.jjtAccept(this,data);
              String mty1 = machineType(ty);
              mty = "[256 x " + mty1 + "]";
              iv.put( (String)sid.jjtGetValue() , id);
              listLenght.put(id,"0");
              listType.put(id , mty1);
              listParam.put(i + scope , "@." + scope + id.substring(2) + "len");
              global = global + "@." + scope + id.substring(2) + "len = global i32 0\n";
         }
         if(mty.equals("[20 x i8]")){
            listLenght.put(id,"0");
            listParam.put(j + scope , "@." + scope + id.substring(2) + "len");
            global = global + "@." + scope + id.substring(2) + "len = global i32 0\n";
        }

          if(i == 0 ){
              arg =arg +  mty + "*" + " " + id;
         }
          else{
              arg = arg + ", " + mty + "* " + id;
        }
        j=j+1;
      }
          return arg;}
      public Object visit(ASTmain node, Object data){
          scope = "main" ;
          prog =prog + "define i32 @main () \n ";
          prog =prog + "{ \n";
          prog = prog + "%.true = getelementptr [5 x i8], [5 x i8]*@.true, i64 0, i64 0 \n";
          prog = prog + "%.false = getelementptr [6 x i8], [6 x i8]*@.false, i64 0, i64 0 \n";
          prog = prog + "%.newline1098019 = getelementptr [2 x i8], [2 x i8]*@.newline1098019, i64 0, i64 0 \n";
           node.childrenAccept(this, data); //accepts nodes
          return null;}

      public Object visit(ASTstatement node, Object data){
          SimpleNode node0 = (SimpleNode)node.jjtGetChild(0);
          // printing
          if(node.jjtGetValue().equals("insert")){
             node0.jjtAccept(this,data);

          }
          if(node.jjtGetValue().equals("functionCall")){
             node0.jjtAccept(this,data);

          }
          if(node.jjtGetValue().equals("return")){
             node0.jjtAccept(this,data);

          }
          if(node.jjtGetValue().equals("print")){
            String var = node0.jjtGetValue().toString();
            DataType type = st.getType(var,scope);
            String sType = machineType(type.toString());
            String isType =(String) node0.toString() ;
            if(st.in_scope(var,"global")){
                  var = iv.get(var) ;
            }
            else{
                 var = "%." + node0.jjtGetValue().toString();
            }
            if(isType.equalsIgnoreCase("string")){
                sType = "Undec";
                var =(String) node0.jjtAccept(this,data);
                String temp = getTemp();
                prog = prog + temp + "= bitcast [20 x i8]*" + var + " to i8* "  + "\n" ;
                prog = prog +  "call i32 @puts (i8* ";
                prog = prog +temp + ") \n";
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
            if(isType.equalsIgnoreCase("float")){
                sType = "undec";
                var = node0.jjtGetValue().toString();
                prog = prog +  "call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.1arg_, i32 0, i32 0), double ";
                prog = prog +var + ") \n";
                prog = prog + "call i32 @puts (i8* %.newline1098019) \n";
            }
            if(sType.equals("i32")){
               String temp = getTemp();
               prog = prog + temp + "= load i32 , i32* " + var + "\n";
       		   prog = prog + "call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 ";
	       	   prog = prog +temp + ") \n";
            }

            else if (sType.equals("[20 x i8]")){
                String temp = getTemp();
                try{
                if (!isChar.get(var).equals(null)){
                     temp = isChar.get(var);
                }

          }catch(NullPointerException e ){

               prog = prog + temp + "= bitcast [20 x i8]*" + var + " to i8* "  + "\n";
          }


                //check if its a char get char value ;
                prog = prog +  "call i32 @puts (i8* ";
                prog = prog +temp + ") \n";
            }
            else if (sType.equals("double")){
               String temp = getTemp();
               prog = prog + temp + "= load double , double* " + var + "\n";
               prog = prog +  "call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.1arg_, i32 0, i32 0), double ";
               prog = prog +temp + ") \n";
               prog = prog + "call i32 @puts (i8* %.newline1098019) \n";
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
            if((type+"").equals("Array"))
            {
                 currentList ="%."+ node0.jjtGetValue().toString();
                 String node1 = (String) node.jjtGetChild (1).jjtAccept (this, data);
                 return "";
            }
            if(mt.equals("[20 x i8]")){
                String var = node0.jjtGetValue().toString();
                getStringDeclN(var);
                String node1 = (String) node.jjtGetChild (1).jjtAccept (this, data);
            }
            else{
                String node1 = (String) node.jjtGetChild (1).jjtAccept (this, data);
                if(!node1.equals("getArray"))
                {
                     String var = "%." + node0.jjtGetValue() ;
                     if(st.in_scope(node0.jjtGetValue()+"","global")){
                          var = "@." + node0.jjtGetValue() ;

                     }
                command = "store " + mt + " " + node1 + ", " + mt + "* " + var ;
                String temp = getTemp();
                load = temp + " = load " + mt + ", " + mt + "* " + var;
                iv.put( node0.jjtGetValue().toString(),temp);
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
          public Object visit(ASTfunctionCall node, Object data){
              SimpleNode node0 = (SimpleNode) node.jjtGetChild(0);
              String var = node0.jjtGetValue().toString();
              DataType type = st.getType(var,"global");
              String sType = machineType(type.toString());
              String params = (String) node.jjtGetChild(1).jjtAccept(this,data);
              String temp = getTemp();
              prog = prog + ";llllllllll\n";


              if((type.toString()).equalsIgnoreCase("Array")){
                sType = "[ 256 x " + listT + "]*" ;
            }
            if(!sType.equalsIgnoreCase("void")){
                prog = prog + temp + " = ";
            }
              prog  = prog +  "call " + sType + " @" + var + " (" + params + ")\n";
              if((type.toString()).equalsIgnoreCase("Array")){
               String temp1 = getTemp();
               prog = prog + temp1 + " = load i32, i32* @." + var + "len \n";
               prog = prog + "store i32 " + temp1 + " , i32* "+ rlen + " \n ";
          }
              return temp;
          }

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

          // gives a variable a boolean value from condition
          node0 = (SimpleNode)node.jjtGetChild(0);
          String node0T = (String) node0.toString();
          DataType dType = st.getType(node0.jjtGetValue().toString(),scope);
          cType = dType.toString();
          mType = machineType(cType);
          String i = "i";
          String o = "";
          String s = "s";
          if(node0T.equals("String")){
                  mType = "i8*";
          }

          if(mType.equals("double") || node0T.equals("Float")){
               mType = "double";
               i= "f";
               o = "o";
               s = "" ;
          }
          switch (comp)
	    {
	    case "==":
		cOp = "eq";
		break;

	    case "!=":
		cOp = "ne";
		break;

	    case ">":
		cOp = s +"gt";
		break;

	    case ">=":
		cOp = s + "ge";
		break;

	    case "<":
		cOp =s +  "lt";
		break;

	    case "<=":
		cOp = s + "le";
		break;

	    default:
		cOp = "eq";
	    }

        temp = getTemp();
        lhs = node.jjtGetChild(0).jjtAccept(this,data).toString();
        rhs = node.jjtGetChild(1).jjtAccept(this,data).toString();
        String sttr = (node.jjtGetChild(0)) + "";
        String sttr1 = (node.jjtGetChild(1)) + "";
        if(sttr.equalsIgnoreCase("string") || sttr.equalsIgnoreCase("string") ){
             mType = "[20 x i8]";

        }
        try{
                  String a = ( "%."+((SimpleNode)node.jjtGetChild(0)).jjtGetValue()) ;
                  String b = ( "%."+((SimpleNode)node.jjtGetChild(1)).jjtGetValue()) ;
                  a = isChar.get(a) ;
                  b = isChar.get(b) ;
                  if(!a.equals(b)){
                       lhs  = getTemp() ;
                       rhs  = getTemp() ;
                       prog = prog + lhs + "= load i8 , i8* " + a + "\n";
                       prog = prog + rhs + "= load i8 , i8* " + b + "\n";
                       mType = "i8" ;

                  }


        }catch(NullPointerException e){}

             if(mType.equals("[20 x i8]")){
                  String a = ( "@."+((SimpleNode)node.jjtGetChild(0)).jjtGetValue()) ;
                  String b = ( "@."+((SimpleNode)node.jjtGetChild(1)).jjtGetValue()) ;
                  String lena = listLenght.get(a);
                  String lenb = listLenght.get(b);
                  lhs = "%." + ((SimpleNode)node.jjtGetChild(0)).jjtGetValue() ;
                  rhs = "%." + ((SimpleNode)node.jjtGetChild(1)).jjtGetValue() ;
                  if(sttr.equalsIgnoreCase("string")){
                       lena = "20" ;
                       lenb = "20" ;
                       lhs =  "" + ((SimpleNode)node.jjtGetChild(0)).jjtAccept(this,data) ;

                  }
                  if(sttr1.equalsIgnoreCase("string")){
                      lena = "20" ;
                      lenb = "20" ;
                      rhs =  "" + ((SimpleNode)node.jjtGetChild(1)).jjtAccept(this,data) ;
                 }


                 prog = prog + temp + " = call i1 @BINStringCmp([20 x i8]* " + lhs + ", i32 " + lena + ", [20 x i8]* " + rhs + ", i32 " + lenb + ") \n ";
             }
        else{
          command = temp + " = " + i + "cmp " + o + cOp + " " + mType + " " + lhs + ", " + rhs ;
          prog = prog + command + "\n";
        }
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
      public Object visit(ASTargumentList node, Object data){
          String param = "";

            for(int i = 0 ; i < node.jjtGetNumChildren(); i ++){
                String var =(String) node.jjtGetChild(i).jjtAccept(this,data);
                SimpleNode node0 = (SimpleNode) node.jjtGetChild(i);
                String t = (String) node0.toString() ;
                String arr = t =(String)( st.getType( (String) node0.jjtGetValue(),scope)).toString(); ;
                String mt = machineType(t)+ "*";
                if(t.equalsIgnoreCase("Identifier")){
                    var ="%."  + (String) node0.jjtGetValue();
                    t =(String)( st.getType( (String) node0.jjtGetValue(),scope)).toString();


                }
                else if(arr.equalsIgnoreCase("Array")){
                     SimpleNode a =(SimpleNode) ((SimpleNode)node.jjtGetParent()).jjtGetChild(0);
                     String func = (String) a.jjtGetValue();

                     String len = getlen(var);
                     String lp = listParam.get(i + func );
                     prog = prog + "store i32 " + len + " , i32* " + lp + "\n";
                     String type1 = listType.get(var);
                     mt = machineType(t);

                          mt = "[256 x "+ type1 +" ]";
              }


           else if (!t.equals("TypeUnknown")) {
                if(t.equals("string")){
                     SimpleNode a =(SimpleNode) ((SimpleNode)node.jjtGetParent()).jjtGetChild(0);
                     String func = (String) a.jjtGetValue();
                     String len = getlen("%."  + (String) node0.jjtGetValue());
                     String lp = listParam.get(i + func );
                     prog = prog + "store i32 " + len + " , i32* " + lp + "\n";
                }
                    mt = machineType(t);
                    String temp =getTemp();
                    prog = prog + temp + " = alloca "  + mt + "\n ";
                    prog = prog + "store  " + mt + " " + var + ", " + mt + "*" + " " + temp  + "\n";
                    var = temp;
               }
          else if (t.equals("TypeUnknown")) {

               mt = machineType("" + node0);
               String temp =getTemp();
               prog = prog + temp + " = alloca "  + mt + "\n ";
               prog = prog + "store  " + mt + " " + var + ", " + mt + "*" + " " + temp  + "\n";
               var = temp;

          }

                if(i == 0){
                    param = param + mt + "* " + var ;
                }

                else{
                    param = param +", " + mt + "* " + var ;
                }

            }
            return param;
        }
      public Object visit(ASTarray node, Object data){
         int numChildren = node.jjtGetNumChildren();
         String num = Integer.toString(numChildren);
         String nodeN ;
         SimpleNode assignmentType = (SimpleNode)((SimpleNode) node.jjtGetParent()).jjtGetParent();
         String assignmentTypeS = "" + assignmentType ;
     //    System.out.println(assignmentTypeS);
          String mtype = listT ;
         if(numChildren > 0 ){
             String nodeT = (String)node.jjtGetChild(0).toString();
        }
       if(assignmentTypeS.equals("statement")){
             String var = (String)((SimpleNode) assignmentType.jjtGetChild(0)).jjtGetValue();
             currentList = "%." + var ;
             mtype = listType.get("%." + var);
        }
        // for variable declaration we can keep it as listT

       // System.out.println(listT);
         String type = "[ " + "256" + " x " + mtype + " ]";
         prog = prog + "store i32 " +  num +", i32* " + " @." + scope +  currentList.substring(2) + "len \n";


         listLenght.put(currentList,num);
         listType.put(currentList,mtype);

          for(int i = 0; i < numChildren ; i ++ ){
              nodeN = (String) node.jjtGetChild(i).jjtAccept(this,data);
            //  System.out.println(type);

              String temp = getTemp();
              prog = prog + temp + " = getelementptr " + type + "," + type + "* " + currentList + ", i32" + " 0 , " + " i32 " + i ;
              prog = prog + "\n" + "store " + mtype + " " + nodeN + ", " + mtype + "* " + temp + "\n" ;
          }
          return "getArray" ;

      }
        public Object visit(ASTclass_ node, Object data){ return null;}
      public Object visit(ASTdecl_list node, Object data){ return null;}

      public Object visit(ASTIdentifier node, Object data){
          DataType dType = st.getType(node.jjtGetValue().toString(),scope);
          String cType = dType.toString();
          if(cType.equalsIgnoreCase("Array")  ){
              return iv.get(node.value.toString());
          }
          String var = "" + node.jjtGetValue();
          if(st.in_scope(var,"global")){
               var = iv.get(var) ;
         }else{
               var = "%." +  node.jjtGetValue();
          }
          String mt = machineType(cType);
          String temp = getTemp();
          String load = temp + " = load " + mt + ", " + mt + "* " + var ;
          prog = prog + load + "\n" ;
          iv.put(node.jjtGetValue().toString(),temp);

          if(st.in_scope(node.jjtGetValue() + "" ,"global")){

               iv.put(var,temp);
               return iv.get(var);
         }
          return iv.get(node.value.toString());
      }
      public Object visit(ASTbreak_ node, Object data){ return null;}
      public Object visit(ASTgetArray node, Object data){
          String l = (String) node.jjtGetChild(0).jjtAccept(this,data);
          String n = (String) node.jjtGetChild(1).jjtAccept(this,data);
          String length = listLenght.get(l);
          return l;
      }
      public String getlen(String var){
           String temp = getTemp();
           prog = prog + temp + " = load i32, i32* @." + scope + var.substring(2) + "len \n";
           prog = prog + "store i32 " + temp + ",  i32* @." +  scope + var.substring(2) + "len \n" ;
           return temp;

      }

      public Object visit(ASTinsert node, Object data){
           SimpleNode val = (SimpleNode) node.jjtGetChild(0);
           String sVar =  (String) val.jjtGetValue();
           DataType dtype = st.getType(sVar,scope);
           String mt =listType.get("%."+sVar);
           String num = listLenght.get(iv.get(sVar));
           String num2 = Integer.toString(Integer.parseInt(num) + 1) ;
           SimpleNode node0 = (SimpleNode) node.jjtGetChild(1);
           String newElement = ( String) node0.jjtAccept(this,data);
           String var = iv.get(sVar);
           String temp = getTemp();
           prog = prog + temp + " = load i32, i32* @." + scope + var.substring(2) + "len \n";
           String temp1 = getTemp();
           prog = prog + temp1 + " = add i32 " + temp +" , 1 \n";
           prog = prog + "store i32 " + temp1 + ",  i32* @." +  scope + var.substring(2) + "len \n" ;
           String stTemp ;
           if((node0 + "").equalsIgnoreCase("string")){
                stTemp = getTemp();
                prog = prog +  stTemp + " = load [20 x i8] , [20 x i8]* " + newElement + "\n" ;
                newElement = stTemp;
           }
           replaceList(temp,num2, mt, var , newElement );
           iv.put(sVar,var);
           listLenght.put(var,num2);
           listInc ++ ;
           return null;}

           public Object visit(ASTgetStringLength node, Object data){
                    String l =  listLenght.get("@."+((SimpleNode)node.jjtGetChild(0)).jjtGetValue()+"");
                     l = (Integer.parseInt(l) -1 ) + " " ;

                     return l;
                }


      public Object visit(ASTgetLength node, Object data){
          String l = (String) node.jjtGetChild(0).jjtAccept(this,data);
          String temp = getTemp();
          SimpleNode a =   (SimpleNode) node.jjtGetChild(0);
          String isString = a.jjtGetValue() + "";
          String type = st.getType(isString,scope) + "";
          String var = l.substring(2) ;
          if(type.equalsIgnoreCase("String")){
               var = isString;
          }

          prog = prog + temp + " = load i32, i32* @." + scope + var + "len \n";
          return temp;
      }
      public Object visit(ASTNumber node, Object data){ return node.value;}
      public Object visit(ASTFloat node, Object data){ return node.value;}

      public Object visit(ASTString node, Object data){
          SimpleNode nodeParent = (SimpleNode) node.jjtGetParent();
          String parent = (String) nodeParent.toString();
          if(parent.equals("assigns")){
              return node.value ;
          }
          String str = (String)node.jjtGetValue();
          String dec1 = sv.get(str);
          String var = "";
          String temp = "";
          String len =  " " +(str.length() - 1);
          /* string declaration */
          if(dec1 ==  null){
            getStringDeclN("undec");
            int v = varInc.get("undec");
            var = "@." + v;
            stringDec = stringDec + var + " = constant [" + (str.length() - 1) + " x i8] c" + str.substring(0, str.length() - 1) + "\\00\"" + "\n";
            stringDeca.add(var);
          }
          else{
              var = dec1;
              if(!stringDeca.contains(var)){
                   len = "20" ;
              }
          }
           listLenght.put( var , (str.length() - 2  ) + "" ) ;
           temp = getTemp();
           String mem =  getTemp() ;
           String dec =  mem + " = alloca " + "[20 x i8]" ;
           prog = prog + dec + "\n" ;
           prog = prog + temp + " = getelementptr [" +len + " x i8], [" +len + " x i8]*" + var + ", i64 0, i64 0\n";
           String temp1 = getTemp();
           prog = prog +  temp1 + " = bitcast i8* " + temp + " to [20 x i8]* \n";
           String temp2 = getTemp() ;
           prog = prog + temp2 + " = load [20 x i8] , [20 x i8]* " + temp1 + "\n";
           prog = prog + "store [20 x i8] " + temp2 + ",  [20 x i8]* "  + mem +  "\n";

           if(parent.equals("argumentList")){
                return temp2 ;
            }
            sv.put(str,var);
            iv.put( mem ,mem);
            if(parent.equals("array")){
                 return temp2 ;
            }
            return mem;
      }

      public Object visit(ASTBoolean node, Object data){
           String val = (String) node.value;
           if(val.equals("true")){
               return "1";
           }
           return "0" ;
    }
}
