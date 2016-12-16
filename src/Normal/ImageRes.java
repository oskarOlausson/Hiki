package Normal;

import java.net.URL;

/**
 * Created by oskar on 2016-12-07.
 * This classes is a wrap around the URL class, easier to know if something didn't work
 */
public class ImageRes {

    private URL path;
    private String aim;
    private String fileEnd = ".png";


    public ImageRes(String string) {

        string = "/" + string;

        if (!string.substring(string.length() - 4, string.length()).equals(".png")) {

            string += fileEnd;
        }

        aim = "'" + string + "'";
        path = this.getClass().getResource(string);
    }

    public boolean isValid() {
        return path != null;
    }

    public URL getPath() {
        return path;
    }

    public String toString() {
        return aim;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageRes imageRes = (ImageRes) o;

        return getPath() != null ? getPath().equals(imageRes.getPath()) : imageRes.getPath() == null;

    }

    @Override
    public int hashCode() {
        return getPath() != null ? getPath().hashCode() : 0;
    }
}