package com.conwaygameoflife.services;

import com.conwaygameoflife.utils.MatrixUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.conwaygameoflife.utils.GameUtil.clearScreen;
import static com.conwaygameoflife.utils.GameUtil.delay;
import static com.conwaygameoflife.utils.MatrixUtil.*;

public class ConwayGameOfLife {
    Map<String, List<List<Integer>>> cellMap;

    public ConwayGameOfLife(Map<String, List<List<Integer>>> initialCellMap) {
        this.cellMap = initialCellMap;
    }

    public void playOnce(String timeout) throws InterruptedException {
        if (resizeCellMapIfAliveCellAtBorder()) {
            clearScreen();
            MatrixUtil.print(cellMap);
            delay(timeout);
        }

        constructNextCellMap();

        clearScreen();
        MatrixUtil.print(cellMap);
        delay(timeout);
    }

    public Boolean resizeCellMapIfAliveCellAtBorder() {
        Boolean isCellMapResized = false;
        while (isAliveCellAtTop() || isAliveCellAtBottom() || isAliveCellAtLeft() || isAliveCellAtRight()) {
            List<List<Integer>> aliveCellList = getAliveCellList(cellMap);
            List<List<Integer>> deadCellList = getDeadCellList(cellMap);
            Integer rowSize = getRowSize(cellMap);
            Integer columnSize = getColumnSize(cellMap);

            if (isAliveCellAtTop()) {
                aliveCellList = aliveCellList.stream().map(c -> List.of(c.get(0) + 1, c.get(1))).collect(Collectors.toList());
                deadCellList = deadCellList.stream().map(c -> List.of(c.get(0) + 1, c.get(1))).collect(Collectors.toList());
                for (Integer c = 0; c <= columnSize; c++) {
                    if (!deadCellList.contains(List.of(0, c))) deadCellList.add(List.of(0, c));
                }
            } else if (isAliveCellAtBottom()) {
                for (Integer c = 0; c <= columnSize; c++) {
                    if (!deadCellList.contains(List.of(rowSize + 1, c))) deadCellList.add(List.of(rowSize + 1, c));
                }
            } else if (isAliveCellAtLeft()) {
                aliveCellList = aliveCellList.stream().map(c -> List.of(c.get(0), c.get(1) + 1)).collect(Collectors.toList());
                deadCellList = deadCellList.stream().map(c -> List.of(c.get(0), c.get(1) + 1)).collect(Collectors.toList());
                for (Integer r = 0; r <= rowSize; r++) {
                    if (!deadCellList.contains(List.of(r, 0))) deadCellList.add(List.of(r, 0));
                }
            } else if (isAliveCellAtRight()) {
                for (Integer r = 0; r <= rowSize; r++) {
                    if (!deadCellList.contains(List.of(r, columnSize + 1))) deadCellList.add(List.of(r, columnSize + 1));
                }
            }

            Map<String, List<List<Integer>>> resizedCellMap = new HashMap<>();
            resizedCellMap.put("alive", aliveCellList);
            resizedCellMap.put("dead", deadCellList);
            cellMap = resizedCellMap;
            isCellMapResized = true;
        }
        return isCellMapResized;
    }

    public void constructNextCellMap() {
        List<List<Integer>> allCellList = getAllCellList(cellMap);
        List<List<Integer>> aliveCellList = getAliveCellList(cellMap);
        List<List<Integer>> deadCellList = getDeadCellList(cellMap);
        List<List<Integer>> nextAliveCellList = new ArrayList<>();
        List<List<Integer>> nextDeadCellList = new ArrayList<>();

        for (List<Integer> cell : allCellList) {
            Integer neighbors = calculateNeighbors(aliveCellList, cell);
            if ((aliveCellList.contains(cell) && neighbors >= 2 && neighbors <= 3) || (deadCellList.contains(cell) && neighbors == 3)) {
                nextAliveCellList.add(cell);
            } else {
                nextDeadCellList.add(cell);
            }
        }

        Map<String, List<List<Integer>>> nextCellMap = new HashMap<>();
        nextCellMap.put("alive", nextAliveCellList);
        nextCellMap.put("dead", nextDeadCellList);
        cellMap = nextCellMap;
    }

    private Boolean isAliveCellAtTop() {
        return getAliveCellList(cellMap).stream().map(cell -> cell.get(0)).anyMatch(row -> row == 0);
    }

    private Boolean isAliveCellAtBottom() {
        return getAliveCellList(cellMap).stream().map(cell -> cell.get(0)).anyMatch(row -> row == getRowSize(cellMap));
    }

    private Boolean isAliveCellAtLeft() {
        return getAliveCellList(cellMap).stream().map(cell -> cell.get(1)).anyMatch(column -> column == 0);
    }

    private Boolean isAliveCellAtRight() {
        return getAliveCellList(cellMap).stream().map(cell -> cell.get(1)).anyMatch(column -> column == getColumnSize(cellMap));
    }

    private Integer calculateNeighbors(List<List<Integer>> aliveCellList, List<Integer> centralCell) {
        Integer neighbors = 0;
        for (List<Integer> aliveCell : aliveCellList) {
            if (aliveCell.get(0) == (centralCell.get(0) - 1) && aliveCell.get(1) == (centralCell.get(1) - 1)) {
                neighbors++;
            } else if (aliveCell.get(0) == (centralCell.get(0) - 1) && aliveCell.get(1) == (centralCell.get(1))) {
                neighbors++;
            } else if (aliveCell.get(0) == (centralCell.get(0) - 1) && aliveCell.get(1) == (centralCell.get(1) + 1)) {
                neighbors++;
            } else if (aliveCell.get(0) == (centralCell.get(0)) && aliveCell.get(1) == (centralCell.get(1) - 1)) {
                neighbors++;
            } else if (aliveCell.get(0) == (centralCell.get(0)) && aliveCell.get(1) == (centralCell.get(1) + 1)) {
                neighbors++;
            } else if (aliveCell.get(0) == (centralCell.get(0) + 1) && aliveCell.get(1) == (centralCell.get(1) - 1)) {
                neighbors++;
            } else if (aliveCell.get(0) == (centralCell.get(0) + 1) && aliveCell.get(1) == (centralCell.get(1))) {
                neighbors++;
            } else if (aliveCell.get(0) == (centralCell.get(0) + 1) && aliveCell.get(1) == (centralCell.get(1) + 1)) {
                neighbors++;
            }
        }
        return neighbors;
    }
}
