package ir.magnet;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;

import java.util.List;

public record AxesConstraint(List<MagnetVariable> scope, int numberOfPoles,
                             Value value) implements Constraint<MagnetVariable, Value> {

    @Override
    public List<MagnetVariable> getScope() {
        return scope;
    }

    @Override
    public boolean isSatisfiedWith(Assignment<MagnetVariable, Value> assignment) {
        int count = 0;
        for (var variable : scope){
            var value = assignment.getValue(variable);
            if (value == this.value) count++;
            if (value == null) return true;
        }
        return count == numberOfPoles;
    }
}
