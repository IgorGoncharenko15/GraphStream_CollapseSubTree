package pkgParse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Txt2Dgs {
    public void getDGS() throws FileNotFoundException, IOException{
        BufferedReader bf = new BufferedReader(new FileReader("C:\\Users\\igor\\YandexDisk\\data\\networks\\HBS faculty.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\igor\\YandexDisk\\data\\networks\\HBS faculty.dgs"));
        
        bw.write("DGS004"); bw.newLine();
        bw.write("null 0 0");bw.newLine();
        bw.write("cg  \"stylesheet\":\"node { shape: box; size: 5px, 5px;  fill-mode: plain;	fill-color: red; stroke-mode: plain; 	stroke-color: blue; text-size: 10; text-alignment: at-right;}\"");
        bw.newLine();
        String style = "cg  \"stylesheet\":\"node#L10 { shape: box; size: 90px, 90px;  fill-mode: plain;	fill-color: blue; stroke-mode: plain; 	stroke-color: blue; text-size: 80;}\"\n";
        
        String line = bf.readLine();
        
        String nodes = "";
                nodes = nodes + "an \"L10\"\n" ;
                nodes = nodes + "cn \"L10\" \"ui.label\":\"HBS\"\n" ;                


        String edges = "";
        int counter_level1 = 1;
        int counter_level2 = 1;
        while (line!=null){
            line = line.trim();
            if (line.contains("DHBS")){
                nodes = nodes + "an \"L1" + counter_level1 + "\"\n" ;
                nodes = nodes + "cn \"L1" + counter_level1 + "\" \"ui.label\":\"" + line.substring(5) + "\"\n" ;                
                edges = edges + "ae \"L10L2" + counter_level2 +"\" \"L10\" > \"L1" + counter_level1+"\"\n";
                style = style + "cg  \"stylesheet\":\"node#L1" + counter_level1 + " { shape: box; size: 45px, 45px;  fill-mode: plain;	fill-color: yellow; stroke-mode: plain; 	stroke-color: blue; text-size: 40;}\"\n";
                counter_level1++;
            } else if (line.length()>0){
                nodes = nodes + "an \"L2" + counter_level2 + "\"\n" ;
                nodes = nodes + "cn \"L2" + counter_level2 + "\" \"ui.label\":\"" + line + "\"\n" ;                
                edges = edges + "ae \"L1" + (counter_level1-1) + "L2" + counter_level2 +"\" \"L1" + (counter_level1-1) + "\" > \"L2" + counter_level2+"\"\n";
                counter_level2++;
                
            } else{
                
            }
            line = bf.readLine();
        }
        bw.write(style);
        bw.write(nodes);
        bw.write(edges);
        
        bw.flush();
        bw.close();
        bf.close();
    }
}
