package ir.magnet;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;

import java.util.List;

public record PairConstraint(MagnetVariable var1, MagnetVariable var2) implements Constraint<MagnetVariable, Value> {

    @Override
    public List<MagnetVariable> getScope() {
        return List.of(var1, var2);
    }

    @Override
    public boolean isSatisfiedWith(Assignment<MagnetVariable, Value> assignment) {
        var value1 = assignment.getValue(var1);
        var value2 = assignment.getValue(var2);
        return  value1 == null || value2 == null ||
                value1.isOpposite(value2) ||
                value1 == Value.EMPTY && value2 == Value.EMPTY;
    }
}
