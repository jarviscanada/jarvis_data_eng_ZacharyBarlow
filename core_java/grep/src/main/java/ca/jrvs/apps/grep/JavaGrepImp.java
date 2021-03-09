package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import org.apache.log4j.BasicConfigurator;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  private String regex;
  private String rootPath;
  private String outFile;

  @Override
  public void process() throws IOException {
    try {
      List<String> matchedLines = new ArrayList<>();

      for (File file : listFiles(getRootPath())) {
        for (String line : readLines(file)) {
          if (containsPattern(line)) {
            matchedLines.add(line);
          }
        }
      }
      writeToFile(matchedLines);
    } catch (IOException ex) {
      logger.error("USAGE: JavaGrep regex rootPath outFile");
    }
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> files = new ArrayList<>();
    File[] dir = new File(rootDir).listFiles();

    for (File file : dir) {
      if (file.isDirectory()) {
        files.addAll(listFiles(file.getAbsolutePath()));
      } else if (file.isFile()) {
        files.add(file);
      }
    }
    return files;
  }

  @Override
  public List<String> readLines(File inputFile) {
    try {
      BufferedReader in = new BufferedReader(new FileReader(inputFile));
      List<String> lines = new ArrayList<>();
      String line;

      while((line = in.readLine()) != null) {
        lines.add(line);
      }
      in.close();
      return lines;
    } catch (IOException ex) {
      logger.error("Unable to read line in file ", ex);
    }

    return null;
  }

  @Override
  public boolean containsPattern(String line) {
    return Pattern.matches(getRegex(), line);
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter(getOutFile()));
      for (String line : lines) {
        out.write(line);
        out.newLine();
      }
      out.close();
    } catch(IOException ex) {
      logger.error("Unable to write to file", ex);
    }
  }

  // GETTER AND SETTERS !!
  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  // MAIN METHOD
  public static void main(String[] args) {
    if(args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    //Use default logger config
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      javaGrepImp.logger.error(ex.getMessage(), ex);
    }
  }
}
