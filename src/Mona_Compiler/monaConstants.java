/* Generated By:JJTree&JavaCC: Do not edit this line. monaConstants.java */

/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface monaConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int VAR = 11;
  /** RegularExpression Id. */
  int CONST = 12;
  /** RegularExpression Id. */
  int RETURN = 13;
  /** RegularExpression Id. */
  int CLASS = 14;
  /** RegularExpression Id. */
  int INT = 15;
  /** RegularExpression Id. */
  int BOOL = 16;
  /** RegularExpression Id. */
  int T_FLOAT = 17;
  /** RegularExpression Id. */
  int VOID = 18;
  /** RegularExpression Id. */
  int MAIN = 19;
  /** RegularExpression Id. */
  int BREAK = 20;
  /** RegularExpression Id. */
  int DICT = 21;
  /** RegularExpression Id. */
  int IF = 22;
  /** RegularExpression Id. */
  int ELSE = 23;
  /** RegularExpression Id. */
  int ELSEIF = 24;
  /** RegularExpression Id. */
  int EXCEPT = 25;
  /** RegularExpression Id. */
  int NOT = 26;
  /** RegularExpression Id. */
  int PRINT = 27;
  /** RegularExpression Id. */
  int IN = 28;
  /** RegularExpression Id. */
  int TRUE = 29;
  /** RegularExpression Id. */
  int FALSE = 30;
  /** RegularExpression Id. */
  int WHILE = 31;
  /** RegularExpression Id. */
  int FOR = 32;
  /** RegularExpression Id. */
  int GET = 33;
  /** RegularExpression Id. */
  int LENGTH = 34;
  /** RegularExpression Id. */
  int INSERT = 35;
  /** RegularExpression Id. */
  int STRING = 36;
  /** RegularExpression Id. */
  int SKIP_mona = 37;
  /** RegularExpression Id. */
  int COMMA = 38;
  /** RegularExpression Id. */
  int SEMIC = 39;
  /** RegularExpression Id. */
  int COLON = 40;
  /** RegularExpression Id. */
  int LCBR = 41;
  /** RegularExpression Id. */
  int RCBR = 42;
  /** RegularExpression Id. */
  int LBR = 43;
  /** RegularExpression Id. */
  int RBR = 44;
  /** RegularExpression Id. */
  int LSBR = 45;
  /** RegularExpression Id. */
  int RSBR = 46;
  /** RegularExpression Id. */
  int DOT = 47;
  /** RegularExpression Id. */
  int ASSIGN = 48;
  /** RegularExpression Id. */
  int PLUS_SIGN = 49;
  /** RegularExpression Id. */
  int MINUS_SIGN = 50;
  /** RegularExpression Id. */
  int MUL = 51;
  /** RegularExpression Id. */
  int DIV = 52;
  /** RegularExpression Id. */
  int POW = 53;
  /** RegularExpression Id. */
  int MOD = 54;
  /** RegularExpression Id. */
  int LOG_NEG = 55;
  /** RegularExpression Id. */
  int OR = 56;
  /** RegularExpression Id. */
  int AND = 57;
  /** RegularExpression Id. */
  int EQUAL = 58;
  /** RegularExpression Id. */
  int NOT_EQUAL = 59;
  /** RegularExpression Id. */
  int LESS_THAN = 60;
  /** RegularExpression Id. */
  int LESS_THAN_OR_EQUAL = 61;
  /** RegularExpression Id. */
  int GREATER_THAN = 62;
  /** RegularExpression Id. */
  int GREATER_THAN_OR_EQUAL = 63;
  /** RegularExpression Id. */
  int DIGIT = 64;
  /** RegularExpression Id. */
  int CHAR = 65;
  /** RegularExpression Id. */
  int NUM = 66;
  /** RegularExpression Id. */
  int FLOAT = 67;
  /** RegularExpression Id. */
  int IDENTIFIER = 68;
  /** RegularExpression Id. */
  int QUOTED_STRING = 69;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int IN_COMMENT = 1;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\f\"",
    "\"//\"",
    "<token of kind 7>",
    "\"/#\"",
    "\"#/\"",
    "<token of kind 10>",
    "\"var\"",
    "\"const\"",
    "\"return\"",
    "\"class\"",
    "\"int\"",
    "\"bool\"",
    "\"float\"",
    "\"void\"",
    "\"main\"",
    "\"break\"",
    "\"dict\"",
    "\"if\"",
    "\"else\"",
    "\"else_if\"",
    "\"except\"",
    "\"not\"",
    "\"print\"",
    "\"in\"",
    "\"true\"",
    "\"false\"",
    "\"while\"",
    "\"for\"",
    "\".get\"",
    "\".length\"",
    "\".insert\"",
    "\"String\"",
    "\"skip\"",
    "\",\"",
    "\";\"",
    "\":\"",
    "\"{\"",
    "\"}\"",
    "\"(\"",
    "\")\"",
    "\"[\"",
    "\"]\"",
    "\".\"",
    "\"=\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"^\"",
    "\"%\"",
    "\"~\"",
    "\"||\"",
    "\"&&\"",
    "\"==\"",
    "\"!=\"",
    "\"<\"",
    "\"<=\"",
    "\">\"",
    "\">=\"",
    "<DIGIT>",
    "<CHAR>",
    "<NUM>",
    "<FLOAT>",
    "<IDENTIFIER>",
    "<QUOTED_STRING>",
  };

}
