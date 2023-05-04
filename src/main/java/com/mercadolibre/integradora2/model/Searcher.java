package com.mercadolibre.integradora2.model;

public class Searcher<T extends Comparable<T>> {
    /**
     * This method searches by range using the binary search method.
     * An array of an object that extends comparable must be given.
     * The array must be ordered before performing the search by range.
     * <p>
     * The method uses the idea that if an element doesn't exist its
     * because the lower limit is bigger than the upper limit, simplifying
     * the concept it means that the binary search on that point will return
     * the position +1, the position -1 or the exact position for the possible
     * position of that element.
     * <p>
     * At the end, the method returns an array with a range representing where
     * the user could find the elements that them are looking for.
     *
     * @param arr The array where the search is going to be performed.
     * @param lt  The lower parameter to search by range.
     * @param rt  The upper parameter to search by range.
     * @return An int array with the indexes where such elements can be found.
     */
    public int[] searchByRange(T[] arr, T lt, T rt) {
        int l_approx = binarySearch(arr, lt, 0, arr.length - 1, true);
        int xl, xr;

        if (arr[l_approx].compareTo(lt) >= 0) {
            if (l_approx > 0 && arr[l_approx - 1].compareTo(lt) >= 0) {
                xl = l_approx - 1;
            } else {
                xl = l_approx;
            }
        } else {
            xl = l_approx + 1;
        }

        int r_approx = binarySearch(arr, rt, xl, arr.length - 1, true);

        if (arr[r_approx].compareTo(rt) <= 0) {
            if (r_approx < arr.length - 1 && arr[r_approx + 1].compareTo(rt) <= 0) {
                xr = r_approx + 1;
            } else {
                xr = r_approx;
            }
        } else {
            xr = r_approx - 1;
        }

        return new int[]{xl, xr};
    }


    /**
     * This method performs the binary search as a generic type, an array
     * of a type that extends comparable with himself must be used.
     * The method returns the position of the element corresponding to the target.
     * The array must be ordered before performing the binary search.
     *
     * @param arr    The array where the method is going to search
     * @param target The object searched in the array
     * @param l      lower limit, must be greater or equals than 0
     * @param r      upper limit, must be lower or equals than the array length
     * @param range  A boolean that permits the method to find an estimated position
     *               for the value searched
     * @return A single int representing the position of the element, if the element
     * is not found in the array, the method will return a -1
     */
    public int binarySearch(T[] arr, T target, int l, int r, boolean range) {
        while (l <= r || range) {
            int m = l + (r - l) / 2;
            if (l > r) {
                return m;
            }

            if (target.compareTo(arr[m]) < 0) {
                r = m - 1;
            } else if (target.compareTo(arr[m]) > 0) {
                l = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }
}
