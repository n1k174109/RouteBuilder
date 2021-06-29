import java.util.*;

public class Graph {
        private Set<GraphNode> nodes = new HashSet<>();

        public void addNode(GraphNode nodeA) {
            nodes.add(nodeA);
        }

        public  Set<GraphNode> getNodes() {
            return nodes;
        }
        public void setNodes(Set<GraphNode> nodes) {
            this.nodes = nodes;
            }

        }
