package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {

        Pattern p = Pattern.compile(args[0]);
        Matcher m;
        System.out.println("Pattern = "+args[0]);

        String filePath = args[1];//.replace("\\","\\\\");
        System.out.println("File path = "+filePath);

        String fileNameOut = args[2];
        System.out.println("File output = "+fileNameOut);

        String currLine = "";

        File folder = new File(filePath);

        if (folder.isDirectory()) {
            System.out.println("Working with directory");

            File[] listOfFiles = folder.listFiles();
            assert listOfFiles != null;

            for (Iterator<File> it = Arrays.stream(listOfFiles).iterator(); it.hasNext();) {
                File log = it.next();
                System.out.println("Checking file = "+log.toString());

                try( BufferedReader br = new BufferedReader(new FileReader(log)) ) {
                    String lastLine = currLine;

                    while ( (currLine = br.readLine()) != null) {
                        currLine = lastLine+currLine;
                        lastLine = "";
                        if(!br.ready() && it.hasNext()){
                            break;
                        }
                        m = p.matcher(currLine);
                        if(m.find()) {
                            writeToFile(currLine, fileNameOut);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            try( BufferedReader br = new BufferedReader(new FileReader(folder)) ) {

                System.out.println("Working with single file "+folder);

                while ( (currLine = br.readLine()) != null) {

                    m = p.matcher(currLine);
                    if(m.find()){
                        writeToFile(currLine,fileNameOut);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Done");
        System.in.read();
    }

    public static void writeToFile(String line, String fileNameOut)throws IOException {
        File fout = new File(fileNameOut);
        FileOutputStream fos = new FileOutputStream(fout,true);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(line);
        bw.newLine();
        bw.close();
    }
}
