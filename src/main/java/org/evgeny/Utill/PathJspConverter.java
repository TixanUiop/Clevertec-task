package org.evgeny.Utill;


import lombok.experimental.UtilityClass;

@UtilityClass
public class PathJspConverter {

    private static final String DEFAULT_JSP_PATH = "/WEB-INF/jsp/%s.jsp";

    public static String getJspPath (String path) {
        return DEFAULT_JSP_PATH.formatted(path);
    }

}
