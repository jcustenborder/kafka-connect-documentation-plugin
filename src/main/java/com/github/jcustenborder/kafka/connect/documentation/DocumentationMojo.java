package com.github.jcustenborder.kafka.connect.documentation;

import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mojo(name = "documentation")
public class DocumentationMojo extends AbstractMojo {

  Configuration configuration;
  ClassTemplateLoader loader;

  public DocumentationMojo() {

  }

  @Parameter(property = "type", defaultValue = "rst")
  String type;

  @Parameter(property = "pomFile", defaultValue = "pom.xml")
  File pomFile;

  @Parameter(property = "outputDirectory", defaultValue = "target/docs")
  File outputDirectory;

  Model load(File pomFile) throws MojoFailureException {
    getLog().info(
        String.format("Loading %s", pomFile)
    );

    Model result;
    MavenXpp3Reader reader = new MavenXpp3Reader();
    try (FileReader fileReader = new FileReader(this.pomFile)) {
      result = reader.read(fileReader);
    } catch (IOException ex) {
      throw new MojoFailureException("Exception thrown while reading pom.", ex);
    } catch (XmlPullParserException ex) {
      throw new MojoFailureException("Exception thrown while reading pom.", ex);
    }
    return result;
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    loader = new ClassTemplateLoader(
        this.getClass(),
        this.type
    );

    this.configuration = new Configuration(Configuration.getVersion());
    this.configuration.setDefaultEncoding("UTF-8");
    this.configuration.setTemplateLoader(loader);
    this.configuration.setObjectWrapper(new BeansWrapper(Configuration.getVersion()));

    if (!pomFile.exists()) {
      getLog().error(
          String.format("Pom '%s' does not exist.", this.pomFile)
      );
      return;
    }

    if (!this.outputDirectory.isDirectory()) {
      getLog().info(String.format("Creating %s", this.outputDirectory));
      this.outputDirectory.mkdirs();
    }

    Model mainPom = load(pomFile);

    final List<Model> models = new ArrayList<>();

    if ("pom".equals(mainPom.getPackaging())) {
      for (final String module : mainPom.getModules()) {
        final File moduleDirectory = new File(this.pomFile.getParent(), module);
        final File modulePomFile = new File(moduleDirectory, "pom.xml");
        final Model moduleModel = load(modulePomFile);
        models.add(moduleModel);
      }
    } else {
      final Model moduleModel = load(this.pomFile);
      models.add(moduleModel);
    }

    for (final Model model : models) {
      process(model);
    }
  }

  void processTemplate(String templateName, Model model, String fileName) throws MojoExecutionException, MojoFailureException {
    try {
      final File outputFile = new File(this.outputDirectory, fileName);
      final File parentDirectory = outputFile.getParentFile();
      if (!parentDirectory.exists()) {
        parentDirectory.mkdirs();
      }


      Template template = configuration.getTemplate(templateName);
      getLog().info(
          String.format("Writing %s", outputFile)
      );
      try (Writer writer = new FileWriter(outputFile)) {
        Map<String, Object> input = new HashMap<>();
        input.put("model", model);
        template.process(input, writer);
      }
    } catch (TemplateException | IOException ex) {
      throw new MojoFailureException("Exception thrown", ex);
    }
  }

  void process(Model model) throws MojoExecutionException, MojoFailureException {
    processTemplate("contributors.rst.ftl", model, "info/contributors.rst");
    processTemplate("info.rst.ftl", model, "info.rst");
    processTemplate("install.rst.ftl", model, "info/install.rst");
    processTemplate("license.rst.ftl", model, "info/license.rst");
    processTemplate("support.rst.ftl", model, "info/support.rst");
    processTemplate("source.rst.ftl", model, "info/source.rst");
  }
}
