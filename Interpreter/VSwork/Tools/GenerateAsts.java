package Tools;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAsts {
  public static void main(String[] args) throws IOException {
    // if (args.length != 1) {
    //   System.err.println("Usage: generate_ast <output directory>");
    //   System.exit(64);
    // }
/*expression       → literal
                   | unary
                   | binary
                   | grouping ;

literal        → NUMBER | STRING | "true" | "false" | "nil" ;
grouping       → "(" expression ")" ;
unary          → ( "-" | "!" ) expression ;
binary         → expression   operator    expression ;
operator       → "==" | "!=" | "<" | "<=" | ">" | ">="
               | "+"  | "-"  | "*" | "/" ; */
    String outputDir = "C:\\Users\\veera\\Desktop\\Tree Walk Tutorial\\Tree Walk Tutorial\\MYOWN\\MYOWN";
    defineAst(outputDir, "Expr", Arrays.asList(
      "Binary   : Expr left, Token operator, Expr right",
      "Call     : Expr callee, Token paren, List<Expr> arguments",
      "Grouping : Expr expression",
      "Literal  : Object value",
      "Logical  : Expr left, Token operator, Expr right",
      "Unary    : Token operator, Expr right",
      "Variable : Token name"
    ));
    defineAst(outputDir, "Stmt", Arrays.asList(
      "Assign   : Token name, Expr value",
      "Block      : List<Stmt> statements",
      "Expression : Expr expression",
      "Function   : Token name, List<Token> params," +" List<Stmt> body",
      "If         : Expr condition, Stmt thenBranch," +" Stmt elseBranch",
      "Print      : Expr expression",
      "Return     : Token keyword, Expr value",
      "Var        : Token name, Expr initializer",
      "While      : Expr condition, Stmt body",
      "Var        : Token name, Expr initializer"
    ));
  }
  private static void defineAst(
      String outputDir, String baseName, List<String> types)
      throws IOException {
    String path = outputDir + "/" + baseName + ".java";
    PrintWriter writer = new PrintWriter(path, "UTF-8");

    writer.println("package MYOWN;");
    writer.println();
    writer.println("import java.util.List;");
    writer.println();
    writer.println("abstract class " + baseName + " {");
    defineVisitor(writer, baseName, types);
    // The AST classes.
    for (String type : types) {
      String className = type.split(":")[0].trim();
      String fields = type.split(":")[1].trim(); 
      defineType(writer, baseName, className, fields);
    }
    // The base accept() method.
    writer.println();
    writer.println("  abstract <R> R accept(Visitor<R> visitor);");
    
    writer.println("}");
    writer.close();
  }
  private static void defineVisitor(
    PrintWriter writer, String baseName, List<String> types) {
  writer.println("  interface Visitor<R> {");

  for (String type : types) {
    String typeName = type.split(":")[0].trim();
    writer.println("    R visit" + typeName + baseName + "(" +
        typeName + " " + baseName.toLowerCase() + ");");
  }

  writer.println("  }");
}

  private static void defineType(
      PrintWriter writer, String baseName,
      String className, String fieldList) {
    writer.println("  static class " + className + " extends " +
        baseName + " {");

    // Constructor.
    writer.println("    " + className + "(" + fieldList + ") {");

    // Store parameters in fields.
    String[] fields = fieldList.split(", ");
    for (String field : fields) {
      String name = field.split(" ")[1];
      writer.println("      this." + name + " = " + name + ";");
    }

    writer.println("    }");
    // Visitor pattern.
    writer.println();
    writer.println("    @Override");
    writer.println("    <R> R accept(Visitor<R> visitor) {");
    writer.println("      return visitor.visit" +
        className + baseName + "(this);");
    writer.println("    }");
    // Fields.
    writer.println();
    for (String field : fields) {
      writer.println("    final " + field + ";");
    }

    writer.println("  }");
  }
}

/*VVVVVVVVVVVVVVVVIIIIIIIIIIP 
 * We want to be able to define new pastry operations—cooking them, eating them, decorating them, etc.—without having to add a new method to each class every time. Here’s how we do it. First, we define a separate interface.

  interface PastryVisitor {
    void visitBeignet(Beignet beignet); 
    void visitCruller(Cruller cruller);
  }     
In Design Patterns, both of these methods are confusingly named visit(), and they rely on overloading to distinguish them. This leads some readers to think that the correct visit method is chosen at runtime based on its parameter type. That isn’t the case. Unlike overriding, overloading is statically dispatched at compile time.

Using distinct names for each method makes the dispatch more obvious, and also shows you how to apply this pattern in languages that don’t support overloading.

Each operation that can be performed on pastries is a new class that implements that interface. It has a concrete method for each type of pastry. That keeps the code for the operation on both types all nestled snugly together in one class.
*/
