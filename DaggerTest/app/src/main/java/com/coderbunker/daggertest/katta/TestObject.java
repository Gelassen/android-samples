package com.coderbunker.daggertest.katta;


public class TestObject {
    private String name;

    public String getName() {
        return name;
    }

    public String getName(String name) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int processNumbers(int first, int second) {
        int result;
        result = first + second;
        result /= 2;
        return result;
    }

    public void execute() {
        // TODO execute

    }
}
