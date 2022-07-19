package com.honeycomb;

import com.honeycomb.parsers.RefParser;

import java.util.List;
import java.util.Optional;

import static com.honeycomb.Parsers.*;

class Main {
    public static void main(String[] argv) {

        interface Expr {}

        record NumExpr(Integer num) implements Expr {}
        record BinOpExpr(Expr left, String op, Expr right) implements Expr {}

        interface Statement {}

        record AssignmentStatement(String id, Expr value) implements Statement {}

        record MethodNode(
                String id,
                Optional<List<String>> args,
                // List<? extends Statement> body
                List<?> body
        ) {}

        record ClassNode(
                String id,
                List<MethodNode> methods
        ) {}

        var NUM = regex("[0-9]+").map(Integer::parseInt);

        RefParser<Expr> factorRef = ref();
        RefParser<Expr> termRef = ref();

        var primary = any(
                NUM.map(NumExpr::new),
                wseq(literal("("), termRef, literal(")"))._2()
        );

        factorRef.set(() -> any(
                wseq(primary, literal("*"), factorRef).map(BinOpExpr::new),
                wseq(primary, literal("/"), factorRef).map(BinOpExpr::new)
        ));

        termRef.set(() -> any(
                wseq(factorRef, literal("+"), termRef).map(BinOpExpr::new),
                wseq(factorRef, literal("-"), termRef).map(BinOpExpr::new)
        ));

        var assignment = wseq(
                literal("var"),
                UID,
                literal("="),
                termRef,
                literal(";")
        ).map((_1, id, _3, value, _5) -> new AssignmentStatement(id, value));

        var statement = any(
                // assignment
        );

        var statements = many(statement);

        var methodArgs = wseq(
                literal("("),
                list(",", UID),
                literal(")")
        )._2();

        var methodBody = wseq(
                literal("{"),
                statements,
                literal("}")
        )._2();

        var methodDef = wseq(
                literal("def"),
                UID,
                methodArgs,
                methodBody
        ).map((_def, id, args, body) -> new MethodNode(id, args, body));

        var classBody = wseq(
                literal("{"),
                many(methodDef),
                literal("}")
        )._2();

        var parser = wseq(
                literal("class"),
                UID,
                classBody
        ).map((_class, id, methods) -> new ClassNode(id, methods));

        final var res = parser.parse("class A { def main() { } }");
        System.out.println(res);
    }
}