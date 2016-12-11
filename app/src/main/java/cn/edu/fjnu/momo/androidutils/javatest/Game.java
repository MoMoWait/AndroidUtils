package cn.edu.fjnu.momo.androidutils.javatest;

/**
 * Created by gaofei on 2016/11/5.
 */

public class Game {
    public Game(int j){

    }
}

class BoardGame extends Game {
    BoardGame(int i) {
        super(2);
        System.out.println("BoardGame constructor");
    }
}
