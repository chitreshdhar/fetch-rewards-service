package com.fetchrewards.fetchrewardsservice.utils;

import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVUtility {

    public void createFile(String filePath) {
        File outputFile = new File(filePath);
        try {
            outputFile.createNewFile();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    // Function to create a new CSV element
    public String[] createTheNewCSVElement(String[] arr, String status, String statusSetOn,
                                           int index)
    {
        // If the array is empty
        // or the index is not in array range
        // return the original array
        if (arr == null
                || index < 0
                || index >= arr.length) {

            return arr;
        }

        // Create ArrayList from the array
        String[] arrayList = arr;

        List<String> list = new ArrayList<>(Arrays.asList(arr));
        // Remove the specified element
        list.remove(1);
        list.add(3,status);
        list.add(4,statusSetOn);
        // return the resultant array
        return list.toArray(new String[list.size()]);

    }

}
