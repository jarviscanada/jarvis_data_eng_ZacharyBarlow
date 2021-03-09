package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

  /**
   * Top level search workflow
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Traverse a given directory and return all files
   * @param rootDir
   * @return
   */
  List<File> listFiles(String rootDir);

  /**
   * Read a file and return all the lines
   *
   * Explain FileReader, BufferedReader, and character encoding
   * FileReader: class for reading character files, streams of characters
   *
   * BufferedReader: reads text from a character-input stream, buffering
   *                 characters so as to provide for the efficient reading
   *                 of characters, arrays, and lines.
   *
   * Character encoding: the representation of the code points for a character set. Assigns our
   *                     binary values to chracters, so that we as humans can read them
   *
   * @param inputFile
   * @return
   */
  List<String> readLines(File inputFile);

  /**
   * check if a line contains the regex pattern (passed by user)
   * @param line
   * @return
   */
  boolean containsPattern(String line);

  /**
   * Write lines to a file
   *
   * FileOutputStream: meant for writing streams of raw bytes such as image data
   *
   * OutputStreamWriter:  Characters written to it are encoded into bytes using a specified charset.
   *                      The charset that it uses may be specified by name or may be given
   *                      explicitly, or the platform's default charset may be accepted
   *
   * BufferedWriter: Writes text to a character-output stream, buffering characters
   *                 so as to provide for the efficient writing of single characters,
   *                 arrays, and strings.
   * @param lines
   * @throws IOException
   */
  void writeToFile(List<String> lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  String getOutFile();

  void setOutFile(String outFile);
}
