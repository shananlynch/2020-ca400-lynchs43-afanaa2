/* Generated By:JJTree: Do not edit this line. ASTstatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTstatement extends SimpleNode {
  public ASTstatement(int id) {
    super(id);
  }

  public ASTstatement(mona p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(monaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=afe04056722538c7d56ca2ee8d00400d (do not edit this line) */
