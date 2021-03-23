package com.infinity.infoway.vimal.util.common;

import java.util.UUID;

public class CommonUtils {
    public static final String FOLDER_NAME = "Vimal";
    public static String generateUniqueFileName(String fileName) {
        return UUID.randomUUID().toString() + "_" + fileName;
    }
}
