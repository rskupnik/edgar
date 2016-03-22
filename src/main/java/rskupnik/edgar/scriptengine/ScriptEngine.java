package rskupnik.edgar.scriptengine;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class ScriptEngine {

    private static final Logger log = LogManager.getLogger(ScriptEngine.class);

    private final GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
    private final List<Script> scripts = new ArrayList<>();
    private Script firstScript;

    private Script lastScript;

    public ScriptEngine() {
        log.info("Initializing script engine...");

        try {
            Files.list(Paths.get(System.getProperty("user.dir") + FileSystems.getDefault().getSeparator() + "scripts"))
                    .forEach(path -> {
                        String fileName = path.getFileName().toString();

                        if (fileName.endsWith(".groovy")) {
                            Optional<Script> script = registerScript(path);
                            if (script.isPresent()) {
                                if (lastScript != null) {
                                    lastScript.setNext(script.get());
                                }

                                lastScript = script.get();

                                if (firstScript == null) {
                                    firstScript = script.get();
                                }
                            }
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Scripts loaded: " + scripts.size());
    }

    public void handle(String cmd) {
        log.info("Handled ["+cmd+"] ? : "+firstScript.handle(cmd));
    }

    private Optional<Script> registerScript(Path path) {
        try {
            File file = path.toFile();
            Class scriptClass = groovyClassLoader.parseClass(file);
            GroovyObject groovyObject = (GroovyObject) scriptClass.newInstance();
            String[] phrases = (String[]) groovyObject.invokeMethod("phrase", null);
            Script script = new Script(phrases, groovyObject);
            scripts.add(script);
            log.info("Added a new script [" + path.toString() + "] with phrases " + Arrays.toString(phrases));
            return Optional.of(script);
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }

        return Optional.empty();
    }
}
