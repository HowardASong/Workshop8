package com.example.workshop8;

public class Classes {
    private String ClassId;
    private String ClassName;

    public Classes(String classId, String className) {
        ClassId = classId;
        ClassName = className;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    @Override
    public String toString() {
        return getClassName();
    }
}
