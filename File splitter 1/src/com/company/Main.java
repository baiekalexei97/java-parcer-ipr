package com.company;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {

    public static void main(String[] args) throws Exception {
        String fileNameIn = args[0];
        String fileNameOut = args[1];

        RandomAccessFile raf = new RandomAccessFile(fileNameIn, "r");
        long sourceSize = raf.length();
        long bytesPerSplit = 20 * 1024 * 1024 ;
        long numSplits = sourceSize/bytesPerSplit;
        long remainingBytes = sourceSize - (bytesPerSplit * numSplits);

        int maxReadBufferSize = 8 * 1024;
        for(int destIx=1; destIx <= numSplits; destIx++) {
            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(fileNameOut+destIx));
            long numReads = bytesPerSplit/maxReadBufferSize;
            for(int i=0; i<numReads; i++) {
                fileWrite(raf, bw, maxReadBufferSize);
            }
            bw.close();
        }
        
        if(remainingBytes > 0) {
            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(fileNameOut+(numSplits+1)));
            fileWrite(raf, bw, remainingBytes);
            bw.close();
        }
        raf.close();
    }

    static void fileWrite(RandomAccessFile raf, BufferedOutputStream bw, long numBytes) throws IOException {
        byte[] buf = new byte[(int) numBytes];
        int val = raf.read(buf);
        if (val != -1) {
            bw.write(buf);
        }
    }
}
