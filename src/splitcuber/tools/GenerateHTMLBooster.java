package splitcuber.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class GenerateHTMLBooster {

    private static final String PATH = "cache/splitcards/";

    public static void main(String[] args) {
        File dir = new File(PATH);

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }
        };

        File[] files = dir.listFiles(filter);
        ArrayList<String> arr = new ArrayList<String>();
        for (File f : files) {
            arr.add("<img src=\"" + f.getName().replaceAll("#", "%23") + "\" />\n");
        }
        int arrl = arr.size();

        StringBuilder str = new StringBuilder();
        str.append("</body>\n</html>");
        for (int i = 0; i < 15; i++) {
            str.append(arr.get((int) (arrl * Math.random())));
        }
        str.append("</body>\n</html>");

        Writer out = null;
        try {
            String HTMLpath = PATH + "_cardlist.html";
            out = new OutputStreamWriter(new FileOutputStream(HTMLpath));
            out.write(str.toString());
            System.out.println("Wrote list as HTML (for printing) to " + HTMLpath);
        } catch (IOException e) {
            System.out.println("could not write list as HTML: " + e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // don't care
                }
            }
        }
    }

}
