package com.example;

import java.util.Random;

public class JokeProvider {

    private static Random rndGen = new Random();
    private String[] jokeArr = {
            "A father asks his son why he is crying." +
            " The son replies with \" I put an extra bracket" +
            " in my code and I don't know which one to delete \"." +
            " They then hug and cry in unison.",

            "A wife explains how she is having trouble opening a jar," +
            " the husband responds with \"Download the JRE.\"",

            "A wife explains how the husband broke a light switch," +
            " he replies with \"I need a break\"",

            "Bro, can you even for loop?",

            "Brace yourselves, the hello world programs are coming.",

            "Old man: \"Back in my day, we used binary to program!\"",

            "Reading code, you see \"i++ \\\\ increment i\". How would" +
            " you have ever known what that code did without that" +
            " amazingly useful comment?"
    };

    public String getJoke(){

        return jokeArr[rndGen.nextInt(jokeArr.length)];

    }

}
