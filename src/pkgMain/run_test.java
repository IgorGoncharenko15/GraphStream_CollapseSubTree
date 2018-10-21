package pkgMain;

import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;


public class run_test {

    public static void main(String[] args) {
         new run_test();
        //Graph graph = new SingleGraph("Tutorial_1");
        /*
        // simple example
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("AD", "A", "D");
        */
        
        /*
        // auto create nodes
        graph.setStrict(false);
        graph.setAutoCreate( true );
        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("AD", "A", "D");
        
        
        for(Node n:graph) {
            System.out.println(n.getId());
        }
        
        for(Edge e:graph.getEachEdge()) {
            System.out.println(e.getId());
        }
        
        Node n_A = graph.getNode("A");
        
        System.out.println(n_A.getDegree() + " || " + n_A.getEdgeFrom("B") + " || " + n_A.hasEdgeFrom(n_A));
        
        
        Iterator<? extends Node> nodes = graph.getNodeIterator();

        while(nodes.hasNext()) {
            Node node = nodes.next();
            System.out.println(node.getId() + " " + node.getDegree()  + "\n");
        }
        */
        //graph.display();
            
        System.out.println("Done!");
    }
    
    
      public run_test() {
            Graph graph = new SingleGraph("tutorial 1");
            
            graph.addAttribute("ui.stylesheet", styleSheet);
            graph.setAutoCreate(true);
            graph.setStrict(false);
            graph.display();

            graph.addEdge("AB", "A", "B");
            graph.addEdge("BC", "B", "C");
            graph.addEdge("CA", "C", "A");
            graph.addEdge("AD", "A", "D");
            graph.addEdge("DE", "D", "E");
            graph.addEdge("DF", "D", "F");
            graph.addEdge("EF", "E", "F");

            for (Node node : graph) {
                node.addAttribute("ui.label", node.getId());
            }

            explore(graph.getNode("A"));
        }

        public void explore(Node source) {
            Iterator<? extends Node> k = source.getBreadthFirstIterator();

            while (k.hasNext()) {
                Node next = k.next();
                next.setAttribute("ui.class", "marked");
                sleep();
            }
        }

        protected void sleep() {
            try { Thread.sleep(2000); } catch (Exception e) {}
        }

        protected String styleSheet =
            "node {" +
        " shape: box; size: 45px, 45px; " +
	" fill-mode: plain;	fill-color: red; stroke-mode: plain; 	stroke-color: blue; text-size: 40;"+
            "}" +
            "node.marked {" +
            "	fill-color: red;" +
            "}";
    

}
