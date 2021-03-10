package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) {
	String s = args[0];
	String filePath = args[1];
	String fileNameOut = args[2];
	if(!fileNameOut.contains(".csv")){
	    fileNameOut += ".csv";
    }

	try( BufferedReader br = new BufferedReader(new FileReader(filePath)) ) {
	    String currLine = "";

	    while ( (currLine = br.readLine()) != null) {
	        currLine = currLine.replaceAll("(.{1,10}) (.{1,12}) (.*): (.*)\t(.*)",
                    "$1"+s+"$2"+s+"$3:"+s+"$4"+s+"$5");
	        writeToFile(currLine, fileNameOut);
	        }

        } catch (IOException e) {
            e.printStackTrace();
        }

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
