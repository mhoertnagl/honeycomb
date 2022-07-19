package com.honeycomb;

import com.honeycomb.parsers.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.honeycomb.Parsers.*;

class Main {

    interface Expr {}

    record NumExpr(Integer num) implements Expr {}
    record BinOpExpr(Expr left, String op, Expr right) implements Expr {}

    record StatementNode() {}

    record MethodNode(
            String id,
            Optional<List<String>> args,
            List<StatementNode> body
    ) {}

    record ClassNode(
            String id,
            List<MethodNode> methods
    ) {}

    static Parser<Integer> NUM = regex("[0-9]+").map(Integer::parseInt);

    static Parser<Expr> factor() {
        return any(
                wseq(primary, literal("*"), factor).map(BinOpExpr::new),
                wseq(primary, literal("/"), factor).map(BinOpExpr::new)
        );
    }

    public static void main(String[] argv) {

        var primary = any(
                NUM.map(NumExpr::new),
                wseq(literal("("), term, literal(")"))._2()
        );

        var factor = any(
                wseq(primary, literal("*"), factor).map(BinOpExpr::new),
                wseq(primary, literal("/"), factor).map(BinOpExpr::new)
        );

        var term = any(
                wseq(factor, literal("+"), term).map(BinOpExpr::new),
                wseq(factor, literal("-"), term).map(BinOpExpr::new)
        );

        var assignment = wseq(
                literal("var"),
                UID,
                literal("="),
                term,
                literal(";")
        );

        var statement = any(
                assignment
        ).map(v -> new StatementNode());

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

        final var res = parser.parse("class A { def main() {} }");
        System.out.println(res);
    }
}