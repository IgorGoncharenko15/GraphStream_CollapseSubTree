package pkgMain;

import java.io.IOException;
import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.algorithm.generator.LobsterGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.ProxyPipe;
import org.graphstream.stream.SinkAdapter;
import org.graphstream.ui.view.Viewer;

import java.util.Iterator;
import org.graphstream.stream.GraphParseException;
import org.graphstream.ui.swingViewer.ViewPanel;
import pkgParse.Txt2Dgs;

/**
 * Created by pigne on 8/13/16.
 */
public class CollapseSubTree {
    public static void main(String[] args) throws IOException, GraphParseException {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        String styleSheet =
            "node {" +
        " shape: box; size: 45px, 45px; " +
	" fill-mode: plain;	fill-color: red; stroke-mode: plain; 	stroke-color: blue; text-size: 40;"+
            "}" +
            "node.marked {" +
            "	fill-color: red;" +
            "}";
        Graph g = new SingleGraph("collapse");
        g.setAttribute("stylesheet", styleSheet);

        Viewer viewer = g.display();
        ViewPanel v = g.display(true).getDefaultView();
        v.addMouseWheelListener(event -> zoom(event.getWheelRotation() < 0));
        //Layout l = Layouts.newLayoutAlgorithm();
        //l.setStabilizationLimit(0);
        //viewer.enableAutoLayout(l);

        /*BaseGenerator gen  = new LobsterGenerator();
        gen.setDirectedEdges(true, false);
        gen.addNodeLabels(true);
        gen.addSink(g);

        gen.begin();
        for (int i = 0; i < 30; i++) {
            gen.nextEvents();
        }
        gen.end();
        */
        /*
            g.addNode("A"); g.addNode("B"); g.addNode("C"); g.addNode("D"); g.addNode("E"); g.addNode("F");
            g.addEdge("AB", "A", "B",true);
            g.addEdge("CA", "A", "C",true);
            g.addEdge("AD", "A", "D",true);
            g.addEdge("DE", "D", "E",true);
            g.addEdge("DF", "D", "F",true);
            
            
            for (Node node : g) {
               node.addAttribute("ui.label", node.getId());
            }
            g.write("C:\\Users\\igor\\YandexDisk\\data\\networks\\testGraph.dgs");
          */
        Txt2Dgs DGSfile = new Txt2Dgs();
        DGSfile.getDGS();
        g.read("C:\\Users\\igor\\YandexDisk\\data\\networks\\HBS faculty.dgs");
        
        ProxyPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addSink(g);

        fromViewer.addSink(new SinkAdapter(){
            @Override
            public void nodeAttributeAdded(String sourceId, long timeId, String nodeId, String attribute, Object value) {
                if(attribute.equals("ui.clicked")){
                    toggleNode(nodeId);
                }
            }

            @Override
            public void nodeAttributeChanged(String sourceId, long timeId, String nodeId, String attribute, Object oldValue, Object newValue) {
                if(attribute.equals("ui.clicked")){
                    toggleNode(nodeId);
                }
            }
            void toggleNode(String id){
                Node n  = g.getNode(id);
                Object[] pos = n.getAttribute("xyz");
                Iterator<Node> it = n.getBreadthFirstIterator(true);
                if(n.hasAttribute("collapsed")){
                    n.removeAttribute("collapsed");
                    while(it.hasNext()){
                        Node m  =  it.next();

                        for(Edge e : m.getLeavingEdgeSet()) {
                            e.removeAttribute("ui.hide");
                        }
                        m.removeAttribute("layout.frozen");
                        m.setAttribute("x",((double)pos[0])+Math.random()*0.0001);
                        m.setAttribute("y",((double)pos[1])+Math.random()*0.0001);

                        m.removeAttribute("ui.hide");

                    }
                    n.removeAttribute("ui.class");

                } else {
                    n.setAttribute("ui.class", "plus");
                    n.setAttribute("collapsed");

                    while(it.hasNext()){
                        Node m  =  it.next();

                        for(Edge e : m.getLeavingEdgeSet()) {
                            e.setAttribute("ui.hide");
                        }
                        if(n != m) {
                            m.setAttribute("layout.frozen");
                            m.setAttribute("x", ((double) pos[0]) + Math.random() * 0.0001);
                            m.setAttribute("y", ((double) pos[1]) + Math.random() * 0.0001);

                            m.setAttribute("ui.hide");
                        }

                    }
                }
            }
        });

        while(true){
            try {
                fromViewer.blockingPump();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static ViewPanel view;
    private static double viewPercent = 0.7;
        public static void zoom(boolean zoomOut) {
        viewPercent += viewPercent * 0.1 * (zoomOut ? -1 : 1);
        view.getCamera().setViewPercent(viewPercent);
    }
}
