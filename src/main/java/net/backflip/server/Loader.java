package net.backflip.server;

public class Loader {

    /*
     $port:25566
     $host-address:localhost
     $max-player-count:10
     */

    public static void main(String[] args) {
        BackFlip.getInstance().start(args);
    }
}
