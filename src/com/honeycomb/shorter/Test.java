package com.honeycomb.shorter;

import java.util.Optional;

import static com.honeycomb.shorter.Parsers.*;

public class Test {

    class Ast {
        public interface Expr {
        }

        public record NumExpr(Integer num) implements Expr {

            public static Expr create(Integer num) {
                return new NumExpr(num);
            }
        }

        public record VarExpr(String name) implements Expr {

            public static Expr create(String name) {
                return new VarExpr(name);
            }
        }

        public record BinOpExpr(Expr left, String op, Expr right) implements Expr {

            // Return as Expr. This way we don't need to add explicit casts
            // for the first parser map.
            public static Expr create(Expr left, String op, Expr right) {
                return new BinOpExpr(left, op, right);
            }
        }
    }

    class Lang {

        // First expression needs a cast to Expr, or else Java will infer
        // NumExpr which is not a superclass of VarExpr.
        private static final Parser<Ast.Expr> primary = any(
                INT.map(Ast.NumExpr::create),
                UID.map(Ast.VarExpr::create),
                literal("(").then(Lang.term).then(")").map(take2of3())
                // TODO: How to skip a parser at the end of the sequence?
                // literal("(").skip(Lang.term).then(")").skip(succeed()).map(x -> )
        );

        private static final Parser<Ast.Expr> factor = any(
                Lang.primary.then("*").then(Lang.primary).map(match(Ast.BinOpExpr::create)),
                Lang.primary.then("/").then(Lang.primary).map(match(Ast.BinOpExpr::create))
        );

        private static final Parser<Ast.Expr> term = any(
                Lang.factor.then("+").then(Lang.factor).map(match(Ast.BinOpExpr::create)),
                Lang.factor.then("-").then(Lang.factor).map(match(Ast.BinOpExpr::create))
        );

        public static Optional<Ast.Expr> parse(String in) {
            return term.parse(in);
        }
    }

    public static void main(String[] args) {
        final var expr = Lang.parse("a*10-b/3");
        System.out.println(expr);
    }
}
