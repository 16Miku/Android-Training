package com.example.day9_designpattern.demeter;

public class Student {

    public Teacher teacher;


    public Student( Teacher teacher) {
        this.teacher = teacher;
    }

    public int getMyResult() {


        return teacher.searchResult();
        // 迪米特法则，又称最少知道原则（Demeter Principle）
        //最少知道原则是指：一个实体应当尽量少地与其他实体之间发生相互作用，使得系统功能模块相对独立。
        // student通过teacher的方法查询成绩，只与teacher交互，不涉及ExamResult的方法

    }

}
