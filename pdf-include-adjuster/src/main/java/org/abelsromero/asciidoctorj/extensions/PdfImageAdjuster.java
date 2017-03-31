package org.abelsromero.asciidoctorj.extensions;

import org.asciidoctor.ast.BlockImpl;
import org.asciidoctor.ast.Document;
import org.asciidoctor.extension.Treeprocessor;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author abelsromero
 */
public class PdfImageAdjuster extends Treeprocessor {

    public static final Map<String, String> CONTEXT = new HashMap<>();

    private List<String> paths = Arrays.asList(
        "images",
        "more-images",
        "even-more-images"
    );

    static {
        CONTEXT.put("context", ":image");
    }

    public PdfImageAdjuster(Map<String, Object> config) {
        super(config);
    }

    @Override
    public Document process(Document document) {
        document.findBy((Map) CONTEXT).forEach(b -> {
            String imagesdir = (String) ((BlockImpl) b).getAttr("imagesdir");
            String target = (String) ((BlockImpl) b).getAttr("target");
            String docdir = (String) ((Map) document.getOptions().get("attributes")).get("docdir");

            // Proper solution should check if other ptions like baseDir are set
            if (new File(new File(docdir, imagesdir), target).exists() == false) {
                String path = filterValidPath(paths, docdir, target);
                if (path != null) {
                    // I can modify `target` but not imagesdir
                    ((BlockImpl) b).setAttr("imagesdir", path, true);
                }
            }

        });
        return document;
    }

    private String filterValidPath(List<String> paths, String basePath, String imagePath) {
        for (String path : paths) {
            final File candidate = new File(new File(basePath, path), imagePath);
            if (candidate.exists())
                return path;
        }
        return null;
    }

}
