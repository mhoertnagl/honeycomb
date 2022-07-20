package com.honeycomb;

import com.honeycomb.parsers.Parser;

import java.util.List;
import java.util.Optional;

import static com.honeycomb.Parsers.*;

/**
 * GRAMMAR : HEADER
 *           CODE
 *           RULE*
 *         ;
 *
 * HEADER  : 'grammar' ID ';'
 *         ;
 *
 * RULE    : ID ':' ALTS ';'
 *         ;
 *
 * ALTS    : PROD ('|' PROD)*
 *         ;
 *
 * PROD    : TERM+ ACTION?
 *         ;
 *
 * TERM    : ID
 *         | TERMINAL
 *         ;
 *
 * ACTION  : '->' CODE
 *         ;
 *
 * CODE    : '{{' .* '}}'
 *         ;
 */

class Main {

    interface Expr {}

    record NumExpr(Integer num) implements Expr {}
    record BinOpExpr(Expr left, String op, Expr right) implements Expr {}

    interface Statement {}

    record AssignmentStatement(String id, Expr value) implements Statement {}

    record MethodNode(
            String id,
            Optional<List<String>> args,
            List<? extends Statement> body
            //List<?> body
    ) {}

    record ClassNode(
            String id,
            List<MethodNode> methods
    ) {}

    class Lang {

        private static final Parser<Expr> primary = any(
                INT.map(NumExpr::new),
                wseq(literal("("), Lang.factor, literal(")"))._2()
        );

        private static final Parser<Expr> factor = any(
                wseq(primary, literal("*"), Lang.factor).map(BinOpExpr::new),
                wseq(primary, literal("/"), Lang.factor).map(BinOpExpr::new)
        );

        private static final Parser<Expr> term = any(
                wseq(Lang.factor, literal("+"), Lang.term).map(BinOpExpr::new),
                wseq(Lang.factor, literal("-"), Lang.term).map(BinOpExpr::new)
        );

        private static final Parser<Statement> assignment = wseq(
                literal("var"),
                UID,
                literal("="),
                INT.map(NumExpr::new),
                literal(";")
        ).map((_1, id, _3, value, _5) -> new AssignmentStatement(id, value));

        private static final Parser<Statement> statement = any(assignment);

        private static final Parser<List<Statement>> statements = many(statement);

        private static final Parser<Optional<List<String>>> methodArgs = wseq(
                literal("("),
                list(",", UID),
                literal(")")
        )._2();

        private static final Parser<List<Statement>> methodBody = wseq(
                literal("{"),
                statements,
                literal("}")
        )._2();

        private static final Parser<MethodNode> methodDef = wseq(
                literal("def"),
                UID,
                methodArgs,
                methodBody
        ).map((_def, id, args, body) -> new MethodNode(id, args, body));

        private static final Parser<List<MethodNode>> classBody = wseq(
                literal("{"),
                many(methodDef),
                literal("}")
        )._2();

        private static final Parser<ClassNode> parser = wseq(
                literal("class"),
                UID,
                classBody
        ).map((_class, id, methods) -> new ClassNode(id, methods));

        public static State<? extends ClassNode> parse(String source) {
            return parser.parse(source);
        }
    }

    public static void main(String[] argv) {
        var res = Lang.parse("class A { def main() { var x = -1; } }");
        System.out.println(res);
    }
}
