import static com.honeycomb.Parsers.*;

import com.fundamentals.Prelude;
import com.honeycomb.ParserException;

class Main {
  public static void main(String[] args) {

    var ID = regex("[a-zA-Z][a-zA-Z0-9]");
    
    var statement = any(
      
    );

    var statements = many(
      seq(
        WS,
        statement, 
        WS,
        literal(";"),
        WS
      )
    );
    
    var methodDef = seq(
      WS,
      literal("def"),
      WS,
      ID,
      WS,
      literal("("),
      WS,
      list(",", ID),
      WS,
      literal(")"),
      WS,
      literal("{"),
      WS,
      statements,
      WS,
      literal("}"),
      WS
    );
    
    var classDef = seq(
      WS,
      literal("class"),
      WS,
      ID,
      WS,
      literal("{"),
      WS,
      many(methodDef),
      WS,
      literal("}"),
      WS
    );

    try {
      var res = classDef.parse("class A { def main() {} }");
      System.out.println(res);
    } catch (ParserException e) {
      System.out.println(e);
    } 
  }
}