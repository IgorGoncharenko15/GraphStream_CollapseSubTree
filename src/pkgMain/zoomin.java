/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMain;

import java.io.IOException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
//import graphx.package$;

public class zoomin {

    private final MultiGraph graph;
    private ViewPanel view;
    private double viewPercent = 0.7;

    public zoomin() throws IOException {

        // creates the graph and its attributes
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph = new MultiGraph("Relationships");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
  //      graph.addAttribute("ui.stylesheet", "url('file:./" + graphx.package$.MODULE$.CSS_FILENAME() + "')");

        // adds nodes and edges to the graph
        addDataFromFile(graph);
    }


    public void run() {
        // starts the GUI with a custom mouse wheel listener for zooming in and out
        view = graph.display(true).getDefaultView();
        view.resizeFrame(800, 600);
        view.addMouseWheelListener(event -> zoom(event.getWheelRotation() < 0));
    }

    public void zoom(boolean zoomOut) {
        viewPercent += viewPercent * 0.1 * (zoomOut ? -1 : 1);
        view.getCamera().setViewPercent(viewPercent);
    }

    public void addDataFromFile(Graph graph) throws IOException {
            //Graph graph = new SingleGraph("tutorial 1");
            
            //graph.addAttribute("ui.stylesheet", styleSheet);
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

    }

    public static void main(String[] args) throws Exception {
        zoomin simpleGraphViewer = new zoomin();
        simpleGraphViewer.run();
    }
}