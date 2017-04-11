package com.stormphoenix.ogit.shares;

import com.stormphoenix.httpknife.github.GitTrendRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StormPhoenix on 17-4-10.
 * StormPhoenix is a intelligent Android developer.
 */

public class JsoupParser {
    public static List<GitTrendRepository> parseTrendRepositories(String html) {
        List<GitTrendRepository> trendRepositories = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements repoList = document.getElementsByClass("repo-list").first().getElementsByTag("li");
        for (int index = 0; index < repoList.size(); index++) {
            Element liElem = repoList.get(index);
            trendRepositories.add(createTrendRepository(liElem));
        }
        return trendRepositories;
    }

    private static GitTrendRepository createTrendRepository(Element liElem) {
        GitTrendRepository repository = new GitTrendRepository();
        List<String> contibutorList = new ArrayList<>();
        repository.setRepoName(liElem.getElementsByClass("d-inline-block col-9 mb-1").get(0).getElementsByTag("a").get(0).text());
        repository.setOwnerName(repository.getRepoName().substring(0, repository.getRepoName().indexOf('/') - 1));
        repository.setRepoName(repository.getRepoName().substring(repository.getRepoName().indexOf('/') + 2));
        repository.setRepoDesc(liElem.getElementsByClass("py-1").get(0).text());
        Elements langTypeElems = liElem.getElementsByClass("f6 text-gray mt-2").get(0).getElementsByAttributeValue("itemprop", "programmingLanguage");
        if (langTypeElems != null && langTypeElems.size() > 0) {
            repository.setLangType(langTypeElems.get(0).text());
        } else {
            repository.setLangType("None");
        }
        repository.setStarNum(liElem.getElementsByClass("muted-link mr-3").get(0).text());

        try {
            repository.setForkNum(liElem.getElementsByClass("muted-link mr-3").get(1).text());
        } catch (Exception e) {
            repository.setForkNum(null);
        }

        try {
            repository.setStarPerDuration(liElem.getElementsByClass("f6 text-gray mt-2").get(0).getElementsByClass("float-right").get(0).text());
        } catch (Exception e) {
            repository.setStarPerDuration(null);
        }
        if (liElem.getElementsByClass("no-underline").size() != 0) {
            Elements imgs = liElem.getElementsByClass("no-underline").get(0).getElementsByTag("img");
            for (int i = 0; i < imgs.size(); i++) {
                contibutorList.add(imgs.get(i).attr("src"));
            }
        }
        repository.setContibutorUrl(contibutorList);
        return repository;
    }
}
