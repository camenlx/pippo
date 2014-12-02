package ro.fortsoft.pippo.pebble;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import ro.fortsoft.pippo.core.PippoRuntimeException;
import ro.fortsoft.pippo.core.util.ClasspathUtils;

import com.mitchellbosecke.pebble.error.LoaderException;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;

class PippoTemplateLoader extends ClasspathLoader {

    public PippoTemplateLoader() {
    }

    @Override
    public Reader getReader(String templateName) throws LoaderException {

        StringBuilder path = new StringBuilder("");
        if (getPrefix() != null) {

            path.append(getPrefix());

            if (!getPrefix().endsWith(String.valueOf(File.separatorChar))) {
                path.append(File.separatorChar);
            }
        }

        String resource = path.toString() + templateName + (getSuffix() == null ? "" : getSuffix());
        String location = resource;
        if (resource.charAt(0) == '/') {
            location = resource.substring(1);
        }
        URL url = ClasspathUtils.locateOnClasspath(location);

        if (url == null) {
            throw new LoaderException(null, "Could not find template \"" + location + "\"");
        }

        Reader reader = null;
        try {
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is, getCharset());
            reader = new BufferedReader(isr);
        } catch (IOException e) {
            throw new PippoRuntimeException(e);
        }

        return reader;
    }

}