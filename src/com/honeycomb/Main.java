package com.honeycomb;

import java.util.ArrayList;

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
                WS,
                literal(";"),
                WS
        ));

        var methodArgs = seq(
                literal("("),
                WS,
                list(",", ID),
                WS,
                literal(")"),
                WS
        );

        var methodBody = seq(
                literal("{"),
                WS,
                statements,
                literal("}"),
                WS
        );

        var methodDef = seq(
                literal("def"),
                WS,
                ID,
                WS,
                methodArgs,
                methodBody,
                WS
        );

        var classBody = seq(
                literal("{"),
                WS,
                many(methodDef),
                literal("}"),
                WS
        );

        var classDef = seq(
                WS,
                literal("class"),
                WS,
                ID,
                WS,
                classBody
        );

        final var res = classDef.parse("class A { def main() {} }");
        System.out.println(res);

        // buildFuns(4, 16);
        // buildWhenAll(4, 16);
        // buildTuples(1, 16);
        // buildSeqParsers(2, 16);
        // buildParserShorthand(3, 16);
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

    private static void buildSeqParsers(int begin, int end) {
        var builder = new StringBuilder();

        for (int i = begin; i <= end; i++) {
            final var types = buildTypeList(i);
            final var args = buildArgsList("_", i);
            builder.append(String.format("public record Seq%dParser<%s>(\n", i, types));
            for (int j = 1; j < i; j++) {
                builder.append(String.format("  Parser<T%d> p%d,\n", j, j));
            }
            builder.append(String.format("  Parser<T%d> p%d\n", i, i));
            builder.append(String.format(") implements Parser<Tuple%d<%s>> {\n", i, types));

            builder.append("@Override\n");
            builder.append(String.format("    public <S> State<Tuple%d<%s>> parse(State<S> state, String value) {\n", i, types));
            builder.append("final var s1 = p1.parse(state, value);\n");
            for (int j = 2; j <= i; j++) {
                builder.append(String.format("final var s%d = p%d.parse(s%d, value);", j, j, j-1));
            }

            builder.append("return ");
            builder.append(String.format("%s;", buildFlatMap(1, i)));
            builder.append("}\n\n");

            builder.append(String.format("public <U> Parser<U> map(Fun%d<%s, U> mapping) {\n", i, types));
            builder.append(String.format("return new Seq%dMapParser<>(this, mapping);\n", i));
            builder.append("}\n\n");

            for (int j = 1; j <= i; j++) {
                builder.append(String.format("public Parser<T%d> _%d() { return this.map((%s) -> _%d); }\n", j, j, args, j));
            }
            builder.append("\n");

            builder.append(String.format("private record Seq%dMapParser<%s, U>(\n", i, types));
            builder.append(String.format("Parser<Tuple%d<%s>> parser,\n", i, types));
            builder.append(String.format("Fun%d<%s, U> mapping\n", i, types));
            builder.append(") implements Parser<U> {\n");

            builder.append("@Override\n");
            builder.append("public <S> State<U> parse(State<S> state, String value) {\n");
            builder.append("return parser.parse(state, value).map(t -> mapping.apply(\n");
            for (int j = 1; j < i; j++) {
                builder.append(String.format("t._%d(),\n", j));
            }
            builder.append(String.format("t._%d()\n", i));
            builder.append("));\n");
            builder.append("}\n");
            builder.append("}\n");
            builder.append("}\n");
        }

        System.out.println(builder);
    }

    private static String buildFlatMap(int begin, int end) {
        if (begin == end) {
            final var args = buildArgsList("_", end);
            return String.format("s%d.map(_%d -> tuple(%s)\n)", end, end, args);
        } else {
            final var sub = buildFlatMap(begin+1, end);
            return String.format("s%d.flatMap(_%d ->\n %s)", begin, begin, sub);
        }
    }

    private static void buildParserShorthand(int begin, int end) {
        var builder = new StringBuilder();
        for (int i = begin; i <= end; i++) {
            final var types = buildTypeList(i);
            final var params = buildGenericParamsList("Parser", "T", "p", i);
            final var args = buildArgsList("p", i);
            builder.append(String.format("public static <%s> Seq%dParser<%s>\n", types, i, types));
            builder.append(String.format("seq(\n%s\n) {\n", params));
            builder.append(String.format("return new Seq%dParser<>(%s);\n", i, args));
            builder.append("}\n\n");
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

    private static String buildGenericParamsList(String container, String type, String name, int end) {
        final var list = new ArrayList<String>();
        for (int i = 1; i <= end; i++) {
            list.add(String.format("%s<%s%d> %s%d", container, type, i, name, i));
        }
        return String.join(",\n", list);
    }

    private static String buildArgsList(String name, int end) {
        final var list = new ArrayList<String>();
        for (int i = 1; i <= end; i++) {
            list.add(String.format("%s%d", name, i));
        }
        return String.join(",", list);
    }
}