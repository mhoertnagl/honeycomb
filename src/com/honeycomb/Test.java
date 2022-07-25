package com.honeycomb;

import java.util.Optional;

import static com.honeycomb.Conversions.*;
import static com.honeycomb.Parsers.*;

// TODO: Indirection required?
// TODO: list, list1, between (eg. between('"', term, '"'))
// TODO: error reporting?
// TODO: WS aware versions.

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
            // to the first parser map.
            public static Expr create(Expr left, String op, Expr right) {
                return new BinOpExpr(left, op, right);
            }
        }
    }

    class Lang {

        private static Parser<Ast.Expr> primary() {
            return any(
                    literal("(").skipLeft(Lang::term).skipRight(")"),
                    UID.map(Ast.VarExpr::create),
                    INT.map(Ast.NumExpr::create)
            );
        }

        private static Parser<Ast.Expr> factor() {
             return any(
                    Lang.primary().then("*").then(Lang::factor).map(to(Ast.BinOpExpr::create)),
                    Lang.primary().then("/").then(Lang::factor).map(to(Ast.BinOpExpr::create)),
                    Lang.primary()
            );
        }

        private static Parser<Ast.Expr> term() {
            return any(
                    Lang.factor().then("+").then(Lang::term).map(to(Ast.BinOpExpr::create)),
                    Lang.factor().then("-").then(Lang::term).map(to(Ast.BinOpExpr::create)),
                    Lang.factor()
            );
        }

        public static Optional<Ast.Expr> parse(String in) {
            return term().parse(in);
        }

        // First expression needs a cast to Expr, or else Java will infer
        // NumExpr which is not a superclass of VarExpr.
//        private static final Parser<Ast.Expr> primary = any(
//                () -> literal("(").skipLeft(() -> Lang.term).skipRight(")"),
//                () -> UID.map(Ast.VarExpr::create),
//                () -> INT.map(Ast.NumExpr::create)
//        );
//
//        private static final Parser<Ast.Expr> factor = any(
//                () -> Lang.primary.then("*").then(() -> Lang.factor).map(to(Ast.BinOpExpr::create)),
//                () -> Lang.primary.then("/").then(() -> Lang.factor).map(to(Ast.BinOpExpr::create)),
//                () -> Lang.primary
//        );
//
//        private static final Parser<Ast.Expr> term = any(
//                () -> Lang.factor.then("+").then(() -> Lang.term).map(to(Ast.BinOpExpr::create)),
//                () -> Lang.factor.then("-").then(() -> Lang.term).map(to(Ast.BinOpExpr::create)),
//                () -> Lang.factor
//        );

//        public static Optional<Ast.Expr> parse(String in) {
//            return term.parse(in);
//        }
    }

    public static void main(String[] args) {
        final var expr = Lang.parse("a+1*(3+b)");
        System.out.println(expr);
    }
}
