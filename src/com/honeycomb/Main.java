package com.honeycomb;

import com.honeycomb.tuples.Tuples;

import java.util.ArrayList;

import static com.honeycomb.Parsers.*;

class Main {
    public static void main(String[] args) {

//        interface Node {}
//        record IdNode(String id) implements Node {}
//
//
//        var ID = regex("[a-zA-Z][a-zA-Z0-9]");
//
//        var statement = any(
//                ID.map(IdNode::new)
//        );
//
//        var statements = many(seq(
//                statement,
//                WS(),
//                KW(";"),
//                WS()
//        ));
//
//        var methodArgs = seq(
//                KW("("),
//                WS(),
//                list(",", ID),
//                WS(),
//                KW(")"),
//                WS()
//        );
//
//        var methodBody = seq(
//                KW("{"),
//                WS(),
//                statements,
//                KW("}"),
//                WS()
//        );
//
//        var methodDef = seq(
//                KW("def"),
//                WS(),
//                ID,
//                WS(),
//                methodArgs,
//                methodBody,
//                WS()
//        );
//
//        var classBody = seq(
//                KW("{"),
//                WS(),
//                many(methodDef),
//                KW("}"),
//                WS()
//        );
//
//        var classDef = seq(
//                WS(),
//                KW("class"),
//                WS(),
//                ID,
//                WS(),
//                classBody
//        );
//
//        final var res = classDef.parse("class A { def main() {} }");
//        System.out.println(res);

        // buildFuns(4, 16);
        // buildWhenAll(4, 16);
        // buildTuples(1, 16);
    }

    private static void buildFuns(int begin, int end) {
        var builder = new StringBuilder();

        for (int i = begin; i <= end ; i++) {
            builder.append("@FunctionalInterface\n");
            builder.append(String.format("public interface Fun%d<", i));
            for (int j = 1; j <= i; j++) {
                builder.append(String.format("T%d,", j));
            }
            builder.append("R> {\n");

            builder.append("  R apply(T1 t1");
            for (int j = 2; j <= i; j++) {
                builder.append(String.format(",T%d t%d", j, j));
            }
            builder.append(");\n");

            builder.append("}\n\n");
        }

        System.out.println(builder);
    }

    private static void buildWhenAll(int begin, int end) {
        var builder = new StringBuilder();

        for (int i = 4; i <= 16; i++) {
            builder.append("public static <");
            for (int j = 1; j <= i; j++) {
                builder.append(String.format("T%d,", j));
            }
            builder.append("R> R whenAll(\n");
            for (int j = 1; j <= i; j++) {
                builder.append(String.format("  State<T%d> s%d,\n", j, j));
            }
            builder.append(String.format("  Fun%d<", i));
            for (int j = 1; j <= i; j++) {
                builder.append(String.format("T%d,", j));
            }
            builder.append("R> fun) {\n");

            builder.append("  if(s1.isPresent()\n");
            for (int j = 2; j <= i; j++) {
                builder.append(String.format("    && s%d.isPresent()\n", j));
            }
            builder.append("  ) {\n");

            builder.append("    return fun.apply(\n");
            builder.append("      s1.get()");
            for (int j = 2; j <= i; j++) {
                builder.append(String.format(",\n      s%d.get()", j));
            }
            builder.append("\n    );\n");

            builder.append("  }\n");
            builder.append("  return null;\n");
            builder.append("}\n\n");
        }

        System.out.println(builder);
    }

    private static void buildTuples(int begin, int end) {
        var builder = new StringBuilder();

        for (int i = begin; i <= end; i++) {
            final var types = buildTypeList(i);
            final var params = buildParamsList("T", "_", i);
            final var args = buildArgsList("_", i);
            builder.append(String.format("public static <%s>\nTuple%d<%s> tuple(\n%s\n) {\n", types, i, types, params));
            builder.append(String.format("  return new Tuple%d<>(%s);\n", i, args));
            builder.append("}\n\n");
        }

        for (int i = begin; i <= end; i++) {
            final var types = buildTypeList(i);
            final var params = buildParamsList("T", "_", i);
            builder.append(String.format("public record Tuple%d<%s>(\n%s\n) {}\n", i, types, params));
        }

        System.out.println(builder);
    }

    private static String buildTypeList(int end) {
        final var list = new ArrayList<String>();
        for (int i = 1; i <= end; i++) {
            list.add(String.format("T%d", i));
        }
        return String.join(",", list);
    }

    private static String buildParamsList(String type, String name, int end) {
        final var list = new ArrayList<String>();
        for (int i = 1; i <= end; i++) {
            list.add(String.format("%s%d %s%d", type, i, name, i));
        }
        return String.join(",", list);
    }

    private static String buildArgsList(String name, int end) {
        final var list = new ArrayList<String>();
        for (int i = 1; i <= end; i++) {
            list.add(String.format("%s%d", name, i));
        }
        return String.join(",", list);
    }
}