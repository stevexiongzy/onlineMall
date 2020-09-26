package com.cloudatai.codegen;

/**
 * @author jinge
 * @email admin(a)cnbbx.com
 */
public class GeneratorMain {
    public static void main(String[] args) throws Exception {
        Generator g = new Generator();
        String createMode = PropertiesProvider.getProperty("createMode","rebuild");
        if(!"cover".equals(createMode)){
            g.clean();
        }
        System.out.println("<<<<<<<<<<starting!>>>>>>>>>>");
        promptInfo();
        g.generateSelectTables();
        System.out.println("<<<<<<<<<<completed!>>>>>>>>>>");
        System.exit(0);
    }

    private static void promptInfo() {
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000);
                    System.out.println("project is running ,please wait!");
                }
            }catch (Exception ex){

            }
        }).start();
    }
}
