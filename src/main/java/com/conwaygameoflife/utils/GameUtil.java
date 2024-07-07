package com.conwaygameoflife.utils;

import com.conwaygameoflife.services.ConwayGameOfLife;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.Long.parseLong;

public class GameUtil {
    public static Map<String, List<List<Integer>>> initialCellMap = new HashMap<>();

    public static void play(String fileName, String timeout) throws InterruptedException {
        parseFileInput(fileName);
        ConwayGameOfLife conwayGameOfLife = new ConwayGameOfLife(initialCellMap);

        clearScreen();
        MatrixUtil.print(initialCellMap);
        delay(timeout);

        while (true) {
            conwayGameOfLife.playOnce(timeout);
        }
    }

    public static void parseFileInput(String filepath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader((filepath)));
            String line;
            try {
                List<List<Integer>> aliveCellList = new ArrayList<>();
                List<List<Integer>> deadCellList = new ArrayList<>();
                Integer r = 0;
                while ((line = br.readLine()) != null) {
                    Integer c = 0;
                    for (String s : line.trim().replaceAll("\\s", "").split("")) {
                        if (s.equalsIgnoreCase("O")) {
                            aliveCellList.add(List.of(r, c));
                        } else if (s.equalsIgnoreCase(".")){
                            deadCellList.add(List.of(r, c));
                        }
                        c++;
                    }
                    r++;
                }
                initialCellMap = Map.of("alive", aliveCellList, "dead", deadCellList);
            } catch (IOException e) {
                System.out.println("Error reading the file input");
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error : File not found in the specified path");
            throw new RuntimeException(e);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void delay(String timeout) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(timeout != null ? parseLong(timeout) : 500);
    }
}
