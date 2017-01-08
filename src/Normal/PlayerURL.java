package Normal;

import java.net.URL;

/**
 * Created by oskar on 2016-12-27.
 * This classes has some inputs and outputs
 */
public class PlayerURL {
    private URL url;
    private String name;
    private String search;
    private String end = ".plf";

    public PlayerURL (String fileName) {
        name = fileName;
        if (name.substring(name.length() - 4) != ".plf") {
            search = "/PlayerFiles/" + name + ".plf";
        }
        else search = name;

        url = this.getClass().getResource(search);
    }

    public boolean validPath() {
        return url != null;
    }

    public URL getPath() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getSearch() {
        return search;
    }

    @Override
    public String toString() {
        return "'" + search + "'";
    }
}

