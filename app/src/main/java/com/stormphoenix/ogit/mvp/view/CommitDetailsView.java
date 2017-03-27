package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.httpknife.github.GitCommitFile;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;

import java.util.List;

/**
 * Created by StormPhoenix on 17-3-27.
 * StormPhoenix is a intelligent Android developer.
 */

public interface CommitDetailsView extends BaseUIView {
    void loadListData(List<GitCommitFile> files);

    void initListItemView();
}
