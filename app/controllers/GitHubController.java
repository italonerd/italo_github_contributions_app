package controllers;
/*
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;*/

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.typesafe.config.Config;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.inject.Inject;
import javax.swing.*;

import org.kohsuke.github.*;
import play.libs.Json;
import play.mvc.*;

public class GitHubController extends Controller {
    private final Config config;

    @Inject
    public GitHubController(Config config) {
        this.config = config;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result contributors(String orgName) throws IOException {
        String token = config.getString("token");
        ArrayNode arrayNode = Json.newObject().arrayNode();
        if (orgName.equalsIgnoreCase("mock")) {
            mockRepositories(arrayNode);
        } else {
            getContributionsByContributorName(token, orgName, arrayNode);
        }

        return ok(arrayNode);
    }

    private void getContributionsByContributorName(String token, String orgName, ArrayNode arrayNode) throws IOException {
        GitHub github = new GitHubBuilder().withOAuthToken(token).build();
        GHOrganization organization = github.getOrganization(orgName);
        PagedIterable<GHRepository> repositories = organization.listRepositories();

        /* Get contributions by contributor name for every repository.
         *  Due to GitHub request limits we have to limit the number of repositories used for our response.
         */
        Map<String, Integer> responseMap = new HashMap<>();
        int repositoyLimit = (repositories.toList().size() < 3 ? repositories.toList().size() : 3);
        for (int i = 0; i < repositoyLimit; i++) {
            for (GHRepository.Contributor contributor : repositories.toList().get(i).listContributors()) {
                responseMap.put(
                        contributor.getName(),
                        (responseMap.get(contributor.getName()) != null ? responseMap.get(contributor.getName()) : 0)
                                + contributor.getContributions()
                );
            }
        }
        /* Merge items by name*/
        List<JsonNode> responseList = new ArrayList<>();
        responseMap.forEach((name, contributions) ->
                responseList.add(
                        Json.newObject().put("name", name).put("contributions", contributions)
                )
        );

        descendSort(arrayNode, responseList);
    }

    /* Descending sort of contributions */
    public void descendSort(ArrayNode arrayNode, List<JsonNode> jsonList) {
        Comparator<JsonNode> contributionsComparator = (jn1, jn2) -> jn1.get("contributions").asInt() - jn2.get("contributions").asInt();
        jsonList.sort(contributionsComparator.reversed());
        jsonList.forEach((jsonNode) -> arrayNode.add(jsonNode));
    }

    private void mockRepositories(ArrayNode arrayNode) {
        /*CreateMocks- BEGIN*/
        int numberOfRepositories = 10;
        int numberOfContributors = 10;

        List<RepositoryMock> repositoriesList = new ArrayList<RepositoryMock>();
        for (int i = 1; i <= numberOfRepositories; i++) {
            RepositoryMock repository = new RepositoryMock("repo_" + i);
            List<ContributorMock> contributorMocks = new ArrayList<ContributorMock>();
            for (int j = 1; j <= numberOfContributors; j++) {
                contributorMocks.add(new ContributorMock("cola_" + j, ThreadLocalRandom.current().nextInt(0, 100)));
            }
            repository.setContributors(contributorMocks);
            repositoriesList.add(repository);
        }
        /*CreateMocks- END*/

        /* Get contributions by contributor name for every repository.*/
        Map<String, Integer> responseMap = new HashMap<>();
        System.out.println(responseMap);
        repositoriesList.forEach((repository) ->
                repository.listContributors().forEach((contributor) ->
                        responseMap.put(
                                contributor.getName(),
                                (responseMap.get(contributor.getName()) != null ? responseMap.get(contributor.getName()) : 0)
                                        + contributor.getContributions()
                        )
                )
        );
        /* Merge items by name*/
        List<JsonNode> responseList = new ArrayList<>();
        responseMap.forEach((name, contributions) ->
                responseList.add(
                        Json.newObject().put("name", name).put("contributions", contributions)
                )
        );

        descendSort(arrayNode, responseList);
    }
}




