package com.example.fileshare.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
@Scope(value = "session")
public class SessionFileMap {

    private Map<String, String> fileMap = new HashMap<>();

    public String getUniqueString(String fileName){
        for(String uniqueName: fileMap.keySet()){
            //check, if file already in map, return it
            if(fileMap.get(uniqueName).equals(fileName)) return uniqueName;
        }
        //otherwise, create new
        String uniqueName = generateUniqueName();
        fileMap.put(uniqueName, fileName);
        return uniqueName;
    }

    public String getFileName(String uniqueString){
        if(fileMap.containsKey(uniqueString)){
            return fileMap.get(uniqueString);
        }
        return null;
    }

    private String generateUniqueName() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);

        return generatedString;

    }
}
