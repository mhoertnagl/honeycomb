package com.honeycomb;

import static com.honeycomb.Conversions.*;
import static com.honeycomb.Parsers.*;

// TODO: Indirection required?
// TODO: error reporting?
// TODO: WS aware versions.

public class Test {

    class Ast {

        public interface Expr { }

        public record NumExpr(Integer num) implements Expr { }

        public record VarExpr(String name) implements Expr { }

        public record BinOpExpr(Expr left, String op, Expr right) implements Expr { }
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
        final var expr = Lang.parse("a+1*(3+b)");
        if (expr.isValid()) {
            System.out.println(expr.val());
        }
    }
}
