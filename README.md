# Get contributions by contributor name for every repository of an organization.

|Dependências |
| ----------- |
|sbt-1.4.9.msi |
|Java version: 8 (version 15.0.1 was used in the development)|
|A GitHub Token stored in your system enviroment variables with the name "GH_TOKEN"|

This is a Play Framework project to get contributions by contributor name for every repository of an organization using GitHub API for Java(https://github-api.kohsuke.org/).

Hello everyone, for a mocked example you can use this url(in local env):<br>
    http://localhost:9000/api/v1/organizations/mock/contributors<br>

For a specific organization you can use a specific organization login instead of "mock".<br>
To get some organization you can go to <a href="https://api.github.com/organizations ">https://api.github.com/organizations</a>.

Response for url "http://localhost:9000/api/v1/organizations/entryway/contributors":<br>
```
[
    {
        "name": "Bobby Wilson",
        "contributions": 173
    },
    {
        "name": "Vojtech Rinik",
        "contributions": 165
    },
    {
        "name": "gustin",
        "contributions": 133
    },
    {
        "name": "Lake Denman",
        "contributions": 20
    },
    {
        "name": null,
        "contributions": 12
    }
]
```
You can find other response examples at https://github.com/italonerd/italo_github_contributions_app/tree/master/app/resources/response/examples.

ps. I've limited the number of repositories iterated due to GitHub API Request Limits (https://docs.github.com/en/developers/apps/rate-limits-for-github-apps). 

Best Regards     
Italo Mendes
    
