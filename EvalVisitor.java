// EvalVisitor.java
import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends labeldExprBaseVisitor<Double> {
    Map<String, Double> memory = new HashMap<>();
    // mode: true = degrees, false = radians (default radians)
    private boolean degreesMode = false;

    private double toRadiansIfNeeded(double x) {
        if (degreesMode) return Math.toRadians(x);
        return x;
    }

    @Override
    public Double visitAssign(labeldExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Double value = visit(ctx.expr());
        memory.put(id, value);
        return value;
    }

    @Override
    public Double visitPrintExpr(labeldExprParser.PrintExprContext ctx) {
        Double value = visit(ctx.expr());
        System.out.println(value);
        return 0.0;
    }

    @Override
    public Double visitInt(labeldExprParser.IntContext ctx) {
        return Double.valueOf(ctx.getText());
    }

    @Override
    public Double visitFloat(labeldExprParser.FloatContext ctx) {
        return Double.valueOf(ctx.getText());
    }

    @Override
    public Double visitId(labeldExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) return memory.get(id);
        if (id.equalsIgnoreCase("PI")) return Math.PI;
        if (id.equalsIgnoreCase("E")) return Math.E;
        return 0.0;
    }

    @Override
    public Double visitParens(labeldExprParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Double visitUnaryMinus(labeldExprParser.UnaryMinusContext ctx) {
        return -visit(ctx.expr());
    }

    @Override
    public Double visitAddSub(labeldExprParser.AddSubContext ctx) {
        Double left = visit(ctx.expr(0));
        Double right = visit(ctx.expr(1));
        if (ctx.op.getText().equals("+")) return left + right;
        return left - right;
    }

    @Override
    public Double visitMulDiv(labeldExprParser.MulDivContext ctx) {
        Double left = visit(ctx.expr(0));
        Double right = visit(ctx.expr(1));
        if (ctx.op.getText().equals("*")) return left * right;
        return left / right;
    }

    @Override
    public Double visitPowExpr(labeldExprParser.PowExprContext ctx) {
        Double base = visit(ctx.expr(0));
        Double exp = visit(ctx.expr(1));
        return Math.pow(base, exp);
    }

    @Override
    public Double visitFactorial(labeldExprParser.FactorialContext ctx) {
        Double v = visit(ctx.expr());
        return (double) factorial((int) Math.floor(v));
    }

    private long factorial(int n) {
        if (n < 0) throw new RuntimeException("factorial of negative number");
        long r = 1;
        for (int i = 2; i <= n; i++) r *= i;
        return r;
    }

    @Override
    public Double visitFunc(labeldExprParser.FuncContext ctx) {
        String name = ctx.funcCall().ID().getText();
        labeldExprParser.ArgListContext args = ctx.funcCall().argList();
        double a = 0, b = 0;
        if (args != null) {
            java.util.List<labeldExprParser.ExprContext> list = args.expr();
            if (list.size() >= 1) a = visit(list.get(0));
            if (list.size() >= 2) b = visit(list.get(1));
        }

        switch (name.toLowerCase()) {
            case "sin":
                return Math.sin(toRadiansIfNeeded(a));
            case "cos":
                return Math.cos(toRadiansIfNeeded(a));
            case "tan":
                return Math.tan(toRadiansIfNeeded(a));
            case "sqrt":
                return Math.sqrt(a);
            case "ln":
                return Math.log(a);
            case "log":
                return Math.log10(a);
            case "deg":
                degreesMode = true;
                return 0.0;
            case "rad":
                degreesMode = false;
                return 0.0;
            default:
                throw new RuntimeException("Unknown function: " + name);
        }
    }
}

