package com.honeycomb;

import static com.honeycomb.Parsers.*;

class Main {
    public static void main(String[] args) {

        interface Node {}
        record IdNode(String id) implements Node {}


        var ID = regex("[a-zA-Z][a-zA-Z0-9]");

        var statement = any(
                ID.map(IdNode::new)
        );

        var statements = many(seq(
                statement,
                WS(),
                KW(";"),
                WS()
        ));

        var methodArgs = seq(
                KW("("),
                WS(),
                list(",", ID),
                WS(),
                KW(")"),
                WS()
        );

        var methodBody = seq(
                KW("{"),
                WS(),
                statements,
                WS(),
                KW("}"),
                WS()
        );

        var methodDef = seq(
                KW("def"),
                WS(),
                ID,
                WS(),
                methodArgs,
                methodBody
        );

        var classBody = seq(
                KW("{"),
                WS(),
                many(methodDef),
                WS(),
                KW("}"),
                WS()
        );

        var classDef = seq(
                WS(),
                KW("class"),
                WS(),
                ID,
                WS(),
                classBody
        );

        final var res = classDef.parse("class A { def main() {} }");
        System.out.println(res);
    }
}