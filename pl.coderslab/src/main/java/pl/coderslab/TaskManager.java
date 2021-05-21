package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String fileName = "tasks.csv";
    static final String[] options = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void main(String[] args) {

        printOptions(options);
        loadDataTab(fileName);
        tasks = loadDataTab(fileName);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String type = scanner.nextLine();
            switch (type){
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks, getNumb());
                    System.out.println("Deleted correctly");
                    break;
                case "list":
                    printList(tasks);
                    break;
                case "exit":
                    saveTabFile(fileName, tasks);
                    System.out.println(ConsoleColors.RED + "Good bye");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please choose correct");
            }
            printOptions(options);
        }

    }

    public static void printOptions(String[] tab){
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option: " + ConsoleColors.RESET);

        for (String option : tab){
            System.out.println(option);
        }
    }

    public static String[][] loadDataTab(String fileName){

        Path path = Paths.get(fileName);
        if (!Files.exists(path)){
            System.out.println("File not found :(");
            System.exit(0);
        }

        String[][] tab = null;
        try{
            List<String>strings = Files.readAllLines(path);
            tab = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++){
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++){
                    tab[i][j] = split[j];
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
            return tab;
    }

    public static void printList(String [][] list){
        for (int i = 0; i < list.length; i++){
            System.out.println(i + " : ");
            for (int j = 0; j < list.length; j++){
                System.out.println(list[i][j] + " ");
            }
            System.out.println();
        }

    }

    private static void addTask(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task info");
        String taskInfo = scanner.nextLine();
        System.out.println("Please add date");
        String taskDate = scanner.nextLine();
        System.out.println("Task important ? true/false");
        String taskImportant = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length -1] = new String[3];
        tasks[tasks.length - 1][0] = taskInfo;
        tasks[tasks.length - 1][1] = taskDate;
        tasks[tasks.length - 1][2] = taskImportant;

    }

    public static boolean isNumGreaterEqualZero(String input){
        if (NumberUtils.isParsable(input)){
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    public static int getNumb(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove");

        String n = scanner.nextLine();
        while (!isNumGreaterEqualZero(n)){
            System.out.println("Incorrect argument sorry. Try again nad type number greater ");
            scanner.nextLine();
        }
        return Integer.parseInt(n);
    }

    private static void removeTask(String[][] tab, int index){
        try {
            if (index < tab.length){
                tasks = ArrayUtils.remove(tab, index);
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Element not visible");
        }
    }

    public static void saveTabFile(String fileName, String[][] tab){
        Path dir = Paths.get(fileName);

        String[] lines = new String[tasks.length];
        for (int i = 0; i < tab.length; i++){
            lines[i] = String.join(",", tab[i]);
        }
        try {
            Files.write(dir, Arrays.asList(lines));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}