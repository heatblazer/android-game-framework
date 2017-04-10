package com.example.ilian.izframework;

/* framwoerk Input interface */
import com.example.ilian.Framework.*;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * main()
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FOREVER
        // update game tick
        // update input
        // update gamelogic
        // update renderer
        // do other stuff
    }
}


/* note: to oneself - a small refresher on java ( have not been there sicne 2012...
 * how to fix a callback before java 1.8`s lambdas
    public class HelloWorld
    {
        public HelloWorld() {}

        public interface Cb
        {
            public void print();
        }

        public void callMe(Cb c)
        {
            c.print();
        }
        // arguments are passed using the text field below this editor
        public static void main(String[] args)
        {
            HelloWorld hw = new HelloWorld();
            hw.callMe(new Cb() {
                public void print()
                {
                    System.out.println("CB...AAAAAAAA");
                }
            });
        }
    }
*/