/* Generated By:JJTree: Do not edit this line. ASTConstantDeclaration.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTConstantDeclaration extends SimpleNode {
  public ASTConstantDeclaration(int id) {
    super(id);
  }

  public ASTConstantDeclaration(mona p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(monaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=37cacade0ba0b13de177804e22f85192 (do not edit this line) */