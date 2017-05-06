package please.picture.com.pictureplease.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jeka on 06.05.17.
 */

public class Parser {
    private String reqString;

    public Parser(String reqString) {
        this.reqString = reqString;
    }

    public List<String> getStringRes() {
        List<String> s = Arrays.asList(reqString.split(","));
        return s;
    }
}
