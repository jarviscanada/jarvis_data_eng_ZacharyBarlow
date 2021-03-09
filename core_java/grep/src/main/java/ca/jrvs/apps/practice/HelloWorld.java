package ca.jrvs.apps.practice;

class HelloWorld {

  public static void main(String[] args) {
    System.out.println("Hello, World");

    RegexExcImp rei = new RegexExcImp();

    boolean ri = rei.matchJpeg("file.jpg");
    System.out.println(ri);
    boolean ri3 = rei.matchIp("999.168.100.100");
    System.out.println(ri3);
    boolean ri2 = rei.isEmptyLine("");
    System.out.println(ri2);
  }
}