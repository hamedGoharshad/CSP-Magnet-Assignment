package ir.magnet;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CspHeuristics;
import aima.core.search.csp.FlexibleBacktrackingSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        try (var in = new BufferedReader(new InputStreamReader(System.in))) {
            var rowsAndColumns = in.readLine().split(" ");
            var rows = Integer.parseInt(rowsAndColumns[0]);
            var columns = Integer.parseInt(rowsAndColumns[1]);
            var rowPositives = getArrayFromInput(in);
            var rowNegatives = getArrayFromInput(in);
            var columnPositives = getArrayFromInput(in);
            var columnNegatives = getArrayFromInput(in);
            var magnets = in.lines().limit(rows).flatMap(s -> Arrays.stream(s.split(" "))).map(Integer::parseInt).toList();
            var hLine = new Line(rows, rowPositives, rowNegatives);
            var vLine = new Line(columns, columnPositives, columnNegatives);
            var magnet = new ir.magnet.Magnet(hLine, vLine, magnets);
            var backtrackingSolver = new FlexibleBacktrackingSolver<ir.magnet.MagnetVariable, ir.magnet.Value>();
            var start = Instant.now();
            var solve = backtrackingSolver.solve(magnet);
            var end = Instant.now();
            System.out.printf("Execution Time: %s%n", Duration.between(start, end).toMillis());
            solve.ifPresent(magnetVariableValueAssignment -> printResult(magnetVariableValueAssignment, rows));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printResult(Assignment<ir.magnet.MagnetVariable, ir.magnet.Value> assignment, int rows) {
        var variables = assignment.getVariables();
        for (int i = 0; i < variables.size(); i++) {
            var value = assignment.getValue(variables.get(i));
            System.out.print(value + " ");
            if (i % rows == rows - 1) System.out.println();
        }
    }

    private static int[] getArrayFromInput(BufferedReader in) throws IOException {
        return Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    }

}
