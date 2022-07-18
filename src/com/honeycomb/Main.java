package com.honeycomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.honeycomb.Parsers.*;

class Main {
    public static void main(String[] argv) {

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


        var ID = regex("[a-zA-Z][a-zA-Z0-9]*");

        var statement = any(
        ).map(v -> new StatementNode());

        var statements = many(wseq(
                statement,
                literal(";")
        )._1());

        var methodArgs = wseq(
                literal("("),
                list(",", ID),
                literal(")")
        )._2();

        var methodBody = wseq(
                literal("{"),
                statements,
                literal("}")
        )._2();

        var methodDef = wseq(
                literal("def"),
                ID,
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
                ID,
                classBody
        ).map((_class, id, methods) -> new ClassNode(id, methods));

        final var res = parser.parse("class A { def main() {} }");
        System.out.println(res);
    }
}