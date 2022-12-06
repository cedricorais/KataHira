package ths.learnjp.katahira;

public class Rng { // TODO remove: snipped @ https://stackoverflow.com/questions/13442611/how-can-i-generate-a-random-number-without-use-of-math-random

    private int max;
    private int last;

    public Rng(int max){
        this.max = max;
        last = (int) (System.currentTimeMillis() % max);
    }

    public int nextInt(){
        System.out.println("last: " + last);
        last = (last * 32719) % 32749;
        return last % max;
    }
}
