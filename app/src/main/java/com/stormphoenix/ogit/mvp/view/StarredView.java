package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.httpknife.github.GitRepository;

import java.util.List;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */

public interface StarredView extends BaseView {
    void initGitRepositoryList(List<GitRepository> repositories);

    void showMessage(String message);
}
