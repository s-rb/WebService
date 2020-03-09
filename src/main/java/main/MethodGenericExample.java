//package main;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MethodGenericExample {
//
//    public static <T> T getTheFirst(List<T> list) {
//        return list.get(0);
//    }
//
//    public static void main(String[] args) {
//
//        List<Integer> listOfInts = new ArrayList<Integer>();
//        listOfInts.add(0);
//        Integer intValue = getTheFirst(listOfInts);
//
//        List<String> listOfStrs = new ArrayList<>();
//        listOfStrs.add("some str");
//        String strValue = getTheFirst(listOfStrs);
//    }
//}
