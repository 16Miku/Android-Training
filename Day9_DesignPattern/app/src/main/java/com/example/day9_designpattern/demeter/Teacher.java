package com.example.day9_designpattern.demeter;

public class Teacher {

    private ExamResult examResult;

    public Teacher( ExamResult examResult ) {

        this.examResult = examResult;
    }


    public int searchResult() {


        return examResult.getResult();



    }



}
