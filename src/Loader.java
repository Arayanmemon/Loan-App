public class Loader {
    Loader(char c){
        Thread t1 = new Thread(new Runnable(){
            public void run(){
                try {
                    for (int i = 0; i < 20; i++) {
                        System.out.print(c);
                        Thread.sleep(150);
                    }
                    System.out.println();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
    }
}
