package com.mercadolibre.integradora2.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Searcher <T extends Comparable<T>> {
//    public static void main(String[] args) {
////        Searcher<Integer> bs = new Searcher<>();
//        Searcher<Double> bs = new Searcher<>();
//        Searcher<String> bsStr = new Searcher<>();
//
//        ArrayList<Product> products = new ArrayList<>();
//        products.add(new Product("CocaCola","soda", 133, 4, 5, 0));
//        products.add(new Product("Pepsi","Soda", 100.5, 2, 1, 0));
//        products.add(new Product("Pepsi1","Soda", 102.5, 3, 2, 0));
//        products.add(new Product("Pepsi2","Soda", 101.5, 6, 3, 0));
//        products.add(new Product("Pepsi3","Soda", 100.5, 8, 4, 0));
//
//        products.sort(Comparator.comparing( (Product p) -> new StringBuilder(p.getName()).reverse().toString()));
////            products.sort(Comparator.comparingInt(Product::getTimesSold));
//        //products.sort(Comparator.comparingDouble(Product::getPrice));
//        //            Integer[] arr2 = products.stream().mapToInt(Product::getTimesSold).boxed().toArray(Integer[]::new);
//        Double[] arr2 = products.stream().mapToDouble(Product::getPrice).boxed().toArray(Double[]::new);
//
//        String[] arr3 = products.stream().map(Product::getName).map(str -> new StringBuilder(str).reverse().toString()).toArray(String[]::new);
//        // Arrays.sort(arr3);
//
//        System.out.println(Arrays.toString(arr3));
//        int[] rangeStr = bsStr.searchByRange(arr3, "i", "n");
//        System.out.println(rangeStr[0] + ", " + rangeStr[1]);
//
//        // int result = bs.binarySearch(arr2, 100.5, 1, arr2.length - 1, false);
//
//        // System.out.println(products.get(result).getName());
//
//        // int[] range = bs.searchByRange(arr2, 10.5,3000.5);
//
//        /*
//        System.out.println(String.format("Range: (%d, %d):", range[0], range[1]));
//        for(int i=range[0]; i<=range[1]; i++) {
//            System.out.println( String.format("%s (%f)", products.get(i).getName(), products.get(i).getPrice()));
//        }
//         *
//         */
//
//        System.out.println(String.format("Range: (%d, %d):", rangeStr[0], rangeStr[1]));
//        for(int i=rangeStr[0]; i<=rangeStr[1]; i++) {
//            System.out.println( String.format("%s (%f)", products.get(i).getName(), products.get(i).getPrice()));
//        }
//    }

    public int[] searchByRange(T[] arr, T lt, T rt) {
        int l_approx = binarySearch(arr, lt, 0, arr.length-1, true);
        int xl = 0, xr = 0;

        if(arr[l_approx].compareTo(lt) >= 0) {
            if( l_approx > 0 && arr[l_approx-1].compareTo(lt) >= 0) {
                xl = l_approx-1;
            } else {
                xl = l_approx;
            }
        } else {
            xl = l_approx + 1;
        }

        int r_approx = binarySearch(arr, rt, xl, arr.length-1, true);

        if(arr[r_approx].compareTo(rt) <= 0) {
            if( r_approx < arr.length-1 && arr[r_approx+1].compareTo(rt) <= 0) {
                xr = r_approx+1;
            } else {
                xr = r_approx;
            }
        } else {
            xr = r_approx - 1;
        }

        return new int[]{xl,xr};
    }

    public int binarySearch(T[] arr, T target, int l, int r, boolean range) {
        while (l <= r || range) {
            int m = l + (r-l)/2;
            if (target.compareTo(arr[m]) < 0) {
                r = m-1;
            } else if (target.compareTo(arr[m]) > 0) {
                l = m+1;
            } else {
                return m;
            }
            if(l > r && range) {
                return m;
            }
        }
        return -1;
    }
}
