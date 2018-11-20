package communication;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by urimaj on 11/16/18.
 */

public class MapGraph{

//
//    public class Main {
//        public static void main(String[] args) {
//            MapGraph mapGraph = new MapGraph();
//            mapGraph.addPath("Bob", new Route(new City("A", 2, 2), new City("B", 2, 3), 2,GameColor.BLACK));
//            mapGraph.addPath("Bob", new Route(new City("B", 2, 2), new City("C", 2, 3), 2,GameColor.BLACK));
//            mapGraph.addPath("Bob", new Route(new City("C", 2, 2), new City("A", 2, 3), 2,GameColor.BLACK));
//            mapGraph.addPath("Bob", new Route(new City("C", 2, 2), new City("D", 2, 3), 6,GameColor.BLACK));
//            mapGraph.addPath("Bob", new Route(new City("A", 2, 2), new City("E", 2, 3), 3,GameColor.BLACK));
//            mapGraph.addPath("Bob", new Route(new City("W", 2, 2), new City("H", 2, 3), 3,GameColor.BLACK));
//
//            HashMap<String, Integer> scores = mapGraph.findLongestPath();
//            for (String s : scores.keySet()) {
//                System.out.println(s);
//                System.out.println(scores.get(s));
//            }
//        }
//    }


    //username to routes
    HashMap<String, ArrayList<Route>> graph = new HashMap<>();
    Integer maxLength = Integer.valueOf(0);

    public void addPath(String username, Route route) {
        if (graph.containsKey(username)) {
            graph.get(username).add(route);
        }
        else {
            graph.put(username, new ArrayList<>());
            graph.get(username).add(route);
        }
    }

    public HashMap<String, Integer>  findLongestPath() {
        HashMap<String, Integer> paths = new HashMap<>();
        for (String key : graph.keySet()) {
            maxLength= 0;
            paths.put(key, findPath(key));
        }
        return paths;
    }

    int findPath(String username) {
        ArrayList<Route> routes = graph.get(username);
        for (int j = 0; j < routes.size(); j++) {
            ArrayList<Boolean> visited = new ArrayList<>();
            for (int i = 0; i < routes.size(); i++) {
                visited.add(new Boolean(false));
            }
            visited.set(j, true);
//            System.out.println("STARTING AT " + routes.get(j).getCity1().toString());
            dfs(routes.get(j).getCity1().toString(), visited, routes, routes.get(j).getLength());

            //other way
            visited = new ArrayList<>();
            for (int i = 0; i < routes.size(); i++) {
                visited.add(new Boolean(false));
            }
            visited.set(j, true);
//            System.out.println("STARTING AT " + routes.get(j).getCity2().toString());
            dfs(routes.get(j).getCity2().toString(), visited, routes, routes.get(j).getLength());
        }
        return maxLength;
    }

    void dfs(String city, ArrayList<Boolean> visited, ArrayList<Route> routes, int depth) {
        this.maxLength = Math.max(depth, this.maxLength);
//        System.out.println("DEPTH " + depth + " MAX : " + maxLength);
       // System.out.println("CCCC " + maxLength + " " + depth);

        for (int i= 0; i < routes.size(); i++) {
            if (!visited.get(i)) {
                if (city.equals(routes.get(i).getCity1().toString())) {
                    visited.set(i, true);
//                    System.out.println("VISITING " + routes.get(i).getRouteID());
//                    System.out.println("CURRENT DEPTH " + depth + " AND LENGTH TO BE ADDED " + routes.get(i).getLength());
                    dfs(routes.get(i).getCity2().toString(), visited, routes, depth + routes.get(i).getLength());
                }
                else if (city.equals(routes.get(i).getCity2().toString())) {
                    visited.set(i, true);
//                    System.out.println("VISITING " + routes.get(i).getRouteID());
//                    System.out.println("CURRENT DEPTH " + depth + " AND LENGTH TO BE ADDED " + routes.get(i).getLength());
                    dfs(routes.get(i).getCity1().toString(), visited, routes, depth + routes.get(i).getLength());
                }
            }
        }
    }
}
