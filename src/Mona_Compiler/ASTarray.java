/* Generated By:JJTree: Do not edit this line. ASTarray.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTarray extends SimpleNode {
  public ASTarray(int id) {
    super(id);
  }

  public ASTarray(mona p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(monaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b1306057c46e089897a26df5edf312c9 (do not edit this line) */
