package controllers;

import org.kohsuke.github.GHObject;

import java.io.IOException;
import java.net.URL;

public class ContributorMock {

    String name;
    int contributions;

    public ContributorMock(String name, int contributions) {
        this.name = name;
        this.contributions = contributions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContributions() {
        return contributions;
    }

    public void setContributions(int contributions) {
        this.contributions = contributions;
    }

}
