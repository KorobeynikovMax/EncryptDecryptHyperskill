package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Cryptor cryptor1 = new Cryptor();
        cryptor1.run(args);
    }
}

class Cryptor {
        private String[] input;
        private String op;
        private String string;
        private String pathToFileRead;
        private String pathToFileWrite;
        private int key;
        private String alg;

        public Cryptor() {
            this.input = null;
            this.op = "enc";
            this.string = "";
            this.pathToFileRead = "";
            this.pathToFileWrite = "";
            this.key = 0;
            this.alg = "shift";
        }

        public String run(String[] input) {
            String result = "";
            this.input = input;
            this.parseInput();
            if ("".equals(this.pathToFileWrite)) {
                if (op.equals("enc")) {
                    if (this.alg.equals("unicode")) {
                        System.out.println(encryptUni(string, key));
                    } else {
                        System.out.println(encryptShift(string, key));
                    }
                } else {
                    if (this.alg.equals("unicode")) {
                        System.out.println(decryptUni(string, key));
                    } else {
                        System.out.println(decryptShift(string, key));
                    }
                }
            } else {
                File fileOut = new File(pathToFileWrite);
                try (PrintWriter printWriter = new PrintWriter(fileOut)) {
                    if (op.equals("enc")) {
                        if (this.alg.equals("unicode")) {
                            printWriter.print(encryptUni(string, key));
                        } else {
                            printWriter.print(encryptShift(string, key));
                        }
                    } else {
                        if (this.alg.equals("unicode")) {
                            printWriter.print(decryptUni(string, key));
                        } else {
                            printWriter.print(decryptShift(string, key));
                        }
                    }
                } catch (IOException e) {
                    System.out.printf("An exception occurs %s", e.getMessage());
                }
            }
            return result;
        }

        private void parseInput() {
            for (int i = 0; i < this.input.length; i++) {
                if (input[i].equals("-mode") && input[i + 1].equals("dec")) {
                    op = "dec";
                }
                if (input[i].equals("-key")) {
                    key = Integer.parseInt(input[i + 1]);
                }
                if (input[i].equals("-data")) {
                    string = input[i + 1];
                }
                if (input[i].equals("-in")) {
                    pathToFileRead = input[i + 1];
                }
                if (input[i].equals("-out")) {
                    pathToFileWrite = input[i + 1];
                }
                if (input[i].equals("-alg")) {
                    alg = input[i + 1];
                }
            }
            if (string.equals("")) {
                File fileRead = new File(pathToFileRead);
                try (Scanner scanner = new Scanner(fileRead)) {
                    if (scanner.hasNext()) {
                        string = scanner.nextLine();
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("No file found: " + pathToFileRead);
                }
            }
            if (string.equals("")) {
                System.out.println("Data is empty string");
                return;
            }
        }

        public String encryptShift(String string, int key) {
            String res = "";
            for (int i = 0; i <= string.length() - 1; i++) {
                char c = string.charAt(i);
                key = key % 26;
                if (c >= 'a' && c <= 'z') {
                    c = (char) ((int) c + key);
                    if (c > 'z') {
                        c = (char) ((int) c - 26);
                    }
                } else if (c >= 'A' && c <= 'Z') {
                    c = (char) ((int) c + key);
                    if (c > 'Z') {
                        c = (char) ((int) c - 26);
                    }
                }
                res += c;
            }
            return res;
        }

    public String encryptUni(String string, int key) {
        String res = "";
        for (int i = 0; i <= string.length() - 1; i++) {
            char c = string.charAt(i);
            c = (char) ((int) c + key);
            res += c;
        }
        return res;
    }

        public String decryptShift(String string, int key) {
            String res = "";
            for (int i = 0; i <= string.length() - 1; i++) {
                char c = string.charAt(i);
                key = key % 26;
                if (c >= 'a' && c <= 'z') {
                    c = (char) ((int) c - key);
                    if (c < 'a') {
                        c = (char) ((int) c + 26);
                    }
                } else if (c >= 'A' && c <= 'Z') {
                    c = (char) ((int) c - key);
                    if (c < 'A') {
                        c = (char) ((int) c + 26);
                    }
                }
                res += c;
            }
            return res;
        }

    public String decryptUni(String string, int key) {
        String res = "";
        for (int i = 0; i <= string.length() - 1; i++) {
            char c = string.charAt(i);
            c = (char) ((int) c - key);
            res += c;
        }
        return res;
    }
}
