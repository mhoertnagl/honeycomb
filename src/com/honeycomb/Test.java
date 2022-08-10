package com.honeycomb;

import static com.honeycomb.Conversions.*;
import static com.honeycomb.Parsers.*;

// TODO: Better way to handle the state results without explicit methods
// TODO: FUnctional way to express or?
// TODO: WS aware versions.

public class Test {

    class Ast {

        public interface Expr { }

        public record NumExpr(Integer num) implements Expr {

            @Override
            public String toString() {
                return num.toString();
            }
        }

        public record VarExpr(String name) implements Expr {

            @Override
            public String toString() {
                return name;
            }
        }

        public record BinOpExpr(Expr left, String op, Expr right) implements Expr {

            @Override
            public String toString() {
                final var lhs = left.toString();
                final var rhs = right.toString();
                return String.format("(%s %s %s)", lhs, op, rhs);
            }
        }
    }

    class Lang {

        private static Parser<Ast.Expr> primary() {
            return any(
                    between("(", Lang::term, ")"),
                    UID.map(Ast.VarExpr::new),
                    INT.map(Ast.NumExpr::new)
            );
        }

        private static Parser<Ast.Expr> factor() {
             return any(
                    Lang.primary().then("*").then(Lang::factor).map(to(Ast.BinOpExpr::new)),
                    Lang.primary().then("/").then(Lang::factor).map(to(Ast.BinOpExpr::new)),
                    Lang.primary()
            );
        }

        private static Parser<Ast.Expr> term() {
            return any(
                    Lang.factor().then("+").then(Lang::term).map(to(Ast.BinOpExpr::new)),
                    Lang.factor().then("-").then(Lang::term).map(to(Ast.BinOpExpr::new)),
                    Lang.factor()
            );
        }

        public static State<? extends Ast.Expr> parse(String in) {
            return term().parse(in);
        }

//        private static final Parser<Ast.Expr> primary = any(
//                between("(", () -> Lang.term, ")"),
//                UID.map(Ast.VarExpr::new),
//                INT.map(Ast.NumExpr::new)
//        );
//
//        private static final Parser<Ast.Expr> factor = any(
//                Lang.primary.then("*").then(() -> Lang.factor).map(to(Ast.BinOpExpr::new)),
//                Lang.primary.then("/").then(() -> Lang.factor).map(to(Ast.BinOpExpr::new)),
//                Lang.primary
//        );
//
//        private static final Parser<Ast.Expr> term = any(
//                Lang.factor.then("+").then(() -> Lang.term).map(to(Ast.BinOpExpr::new)),
//                Lang.factor.then("-").then(() -> Lang.term).map(to(Ast.BinOpExpr::new)),
//                Lang.factor
//        );
//
//public static State<? extends Ast.Expr> parse(String in) {
//    return term().parse(in);
//}
    }

    public static void main(String[] args) {
        final var expr = Lang.parse("1*(3+b)+a");
        if (expr.isValid()) {
            System.out.println(expr.val().orElse(null));
        } else {
            System.out.println(expr.error().orElse(null));
        }
    }
}
