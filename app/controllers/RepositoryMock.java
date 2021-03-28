package controllers;


import java.util.List;

public class RepositoryMock {

    String name;

    List<ContributorMock> contributors;

    public RepositoryMock(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ContributorMock> getContributors() {
        return contributors;
    }

    public void setContributors(List<ContributorMock> contributors) {
        this.contributors = contributors;
    }

    public List<ContributorMock> listContributors() {
        return this.getContributors();
    }
}
