package io.github.manuelarte.spring

class ProjectVersion {
    Integer major
    Integer minor
    Integer bugFix
    Boolean release

    ProjectVersion(Integer major, Integer minor, Integer bugFix) {
        this(major, minor, bugFix, Boolean.FALSE)
    }

    ProjectVersion(Integer major, Integer minor, Integer bugFix, Boolean release) {
        this.major = major
        this.minor = minor
        this.bugFix = bugFix
        this.release = release
    }

    @Override
    String toString() {
        "$major.$minor.$bugFix${release ? '' : '-SNAPSHOT'}"
    }
}