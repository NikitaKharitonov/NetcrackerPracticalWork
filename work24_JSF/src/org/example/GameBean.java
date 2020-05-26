package org.example;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@ManagedBean
@SessionScoped
public class GameBean {
    private int min;
    private int max;
    private int number;

    public GameBean() {
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String check() {
        if (max >= min) {
            guessNumber();
            return "success";
        }
        return "fail";
    }

    public void guessNumber() {
        number = ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public String less() {
        min = number + 1;
        return check();
    }

    public String more() {
        max = number - 1;
        return check();
    }

    public String equals() {
        return "win";
    }
}
