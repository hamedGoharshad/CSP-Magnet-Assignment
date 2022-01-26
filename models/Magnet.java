package ir.magnet;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;

import java.util.ArrayList;
import java.util.List;

public class Magnet extends CSP<ir.magnet.MagnetVariable, ir.magnet.Value> {
    private final Line hLine, vLine;
    private List<ir.magnet.MagnetVariable> variables;

    public Magnet(Line hLine, Line vLine, List<Integer> magnets) {
        this.hLine = hLine;
        this.vLine = vLine;
        addVariables();
        addPairConstraints(magnets);
        addHAxisConstraints();
        addVAxisConstraints();
    }

    private void addVariables() {
        for (int i = 0; i < hLine.length() * vLine.length(); i++) {
            var variable = new ir.magnet.MagnetVariable(i);
            addVariable(variable);
            setDomain(variable, new Domain<>(ir.magnet.Value.values()));
        }
        variables = getVariables();
    }

    private void addPairConstraints(List<Integer> magnets) {
        for (int i = 1; i <= variables.size() / 2; i++) {
            var first = variables.get(magnets.indexOf(i));
            var second = variables.get(magnets.lastIndexOf(i));

            addConstraint(new PairConstraint(first, second));
        }
    }

    private void addHAxisConstraints() {
        for (int i = 0; i < hLine.length(); i++) {
            List<ir.magnet.MagnetVariable> lineVariables = new ArrayList<>();
            for (int j = 0; j < vLine.length(); j++) lineVariables.add(variables.get(i * vLine.length() + j));

            addConstraint(new AxesConstraint(lineVariables, hLine.positives()[i], ir.magnet.Value.POSITIVE));
            addConstraint(new AxesConstraint(lineVariables, hLine.negatives()[i], ir.magnet.Value.NEGATIVE));
        }
    }

    private void addVAxisConstraints() {
        for (int i = 0; i < vLine.length(); i++) {
            List<ir.magnet.MagnetVariable> lineVariables = new ArrayList<>();
            for (int j = 0; j < hLine.length(); j++) lineVariables.add(variables.get(j * vLine.length() + i));

            addConstraint(new AxesConstraint(lineVariables, vLine.positives()[i], ir.magnet.Value.POSITIVE));
            addConstraint(new AxesConstraint(lineVariables, vLine.negatives()[i], ir.magnet.Value.NEGATIVE));
        }
    }

}

record Line(int length, int[] positives, int[] negatives) {
}