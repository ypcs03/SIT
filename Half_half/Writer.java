import java.io.*;

public class Writer{
    File java = null;
    FileWriter filewriter = null;
    public BufferedWriter bufferwriter = null;
    
    public Writer(Writer existed){
        bufferwriter = existed.bufferwriter;
    }

    public Writer(){
        try {
            java = new File(new String("EMF_QUERY.java")); // create the file
            filewriter = new FileWriter(java.getName(), true);
            bufferwriter = new BufferedWriter(filewriter);
        }catch(Exception e){
            System.out.println("Failed to open the file");
        }
    }    

    public void write(String str){
        try{
            bufferwriter.write(str);
        }catch(IOException e){
            System.out.println("Failed to write into the file");
        }       
    }

    public void close(){
        try{
            bufferwriter.close();
        }catch(IOException e){
            System.out.println("Failed to close the file");
        }
    }
    
}
