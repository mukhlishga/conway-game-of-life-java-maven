package com.conwaygameoflife.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatrixUtil {

    public static void print(Map<String, List<List<Integer>>> cellMap) {
        Integer rowSize = getRowSize(cellMap) + 1;
        Integer columnSize = getColumnSize(cellMap) + 1;
        List<List<Integer>> aliveCellList = getAliveCellList(cellMap);

        char[][] matrix = new char[rowSize][columnSize];
        for (Integer r = 0; r < rowSize; r++) {
            for (Integer c = 0; c < columnSize; c++) {
                if (isCellAlive(aliveCellList, List.of(r, c))) {
                    matrix[r][c] = 'O';
                } else {
                    matrix[r][c] = '.';
                }
            }
        }

        for (char[] row: matrix) {
            for (char cell: row) {
                System.out.print(cell);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static Integer getRowSize(Map<String, List<List<Integer>>> cellMap) {
        return Stream.of(cellMap.get("alive"), cellMap.get("dead"))
            .flatMap(Collection::stream)
            .map(c -> c.get(0))
            .mapToInt(Integer::intValue)
            .max()
            .orElse(0);
    }

    public static Integer getColumnSize(Map<String, List<List<Integer>>> cellMap) {
        return Stream.of(cellMap.get("alive"), cellMap.get("dead"))
            .flatMap(Collection::stream)
            .map(c -> c.get(1))
            .mapToInt(Integer::intValue)
            .max()
            .orElse(0);
    }

    public static List<List<Integer>> getAliveCellList(Map<String, List<List<Integer>>> cellMap) {
        return Stream.of(cellMap.get("alive"))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    public static List<List<Integer>> getDeadCellList(Map<String, List<List<Integer>>> cellMap) {
        return Stream.of(cellMap.get("dead"))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    public static List<List<Integer>> getAllCellList(Map<String, List<List<Integer>>> cellMap) {
        return Stream.of(cellMap.get("alive"), cellMap.get("dead"))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    public static Boolean isCellAlive(List<List<Integer>> aliveCellList, List<Integer> position) {
        for (List<Integer> aliveCell : aliveCellList) {
            if (Objects.equals(aliveCell.get(0), position.get(0)) && Objects.equals(aliveCell.get(1), position.get(1))) {
                return true;
            }
        }
        return false;
    }
}
