package org.abelsromero.asciidoctorj.extensions;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.OptionsBuilder;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author abelsromero
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PdfImageAdjusterTest {

    @Test
    public void should_render_an_html_without_extension() {
        // given
        final Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        final String sourceAdoc = "src/test/resources/sample.adoc";

        // when
        final File output = new File("target", "output.html");
        OptionsBuilder options = OptionsBuilder.options()
            .backend("html5")
            .toFile(output);
        asciidoctor.renderFile(new File(sourceAdoc), options);

        // then
        assertThat(output.exists(), is(Boolean.TRUE));
    }

    @Test
    public void should_render_an_html_with_extension() {
        // given
        final Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        final String sourceAdoc = "src/test/resources/sample.adoc";

        // when
        final File output = new File("target", "output-extension.html");
        OptionsBuilder options = OptionsBuilder.options()
            .backend("html5")
            .toFile(output);

        asciidoctor.javaExtensionRegistry().treeprocessor(PdfImageAdjuster.class);
        asciidoctor.renderFile(new File(sourceAdoc), options);

        // then
        assertThat(output.exists(), is(Boolean.TRUE));
    }

    @Test
    public void should_render_a_pdf_without_extension() {
        // given
        final Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        final String sourceAdoc = "src/test/resources/sample.adoc";

        // when
        final File output = new File("target", "output.pdf");
        OptionsBuilder options = OptionsBuilder.options()
            .backend("pdf")
            .toFile(output);
        asciidoctor.renderFile(new File(sourceAdoc), options);

        // then
        assertThat(output.exists(), is(Boolean.TRUE));
    }

    @Test
    public void should_render_a_pdf_with_extension() {
        // given
        final Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        final String sourceAdoc = "src/test/resources/sample.adoc";

        // when
        final File output = new File("target", "output-extension.pdf");
        OptionsBuilder options = OptionsBuilder.options()
            .backend("pdf")
            .toFile(output);

        asciidoctor.javaExtensionRegistry().treeprocessor(PdfImageAdjuster.class);
        asciidoctor.renderFile(new File(sourceAdoc).getAbsoluteFile(), options);

        // then
        assertThat(output.exists(), is(Boolean.TRUE));
    }

}
