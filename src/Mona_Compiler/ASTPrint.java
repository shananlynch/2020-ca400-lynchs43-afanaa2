/* Generated By:JJTree: Do not edit this line. ASTPrint.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTPrint extends SimpleNode {
  public ASTPrint(int id) {
    super(id);
  }

  public ASTPrint(mona p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(monaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=8fafeaf70df38d0fbd0f845318e7afed (do not edit this line) */
