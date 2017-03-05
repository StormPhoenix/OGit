package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.stormphoenix.httpknife.github.GitTreeItem;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.mvp.ui.component.BreadcrumbView;
import com.stormphoenix.ogit.mvp.ui.fragments.FoldsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BreadcrumbTreeActivity extends BaseActivity {
    /**
     * BreadcrumbTreeController 用于对BreadcrumbView的行为进行控制
     */
    public static interface BreadcrumbTreeController {
        /**
         * 从下标 index 处删除之后（包括index处）的所有的Breadcrumb内容
         * @param index
         */
        void removeFrom(int index);

        /**
         * 在尾部添加一个BreadcrumbView
         * @param breadcrumb
         */
        void addBreadcrumb(BreadcrumbView.Breadcrumb breadcrumb);

        /**
         * 获取Breadcrumb所保存的数据
         * example: 如果BreadcrumbView保存的是 [app,src,main,java] 的数组
         *          则返回 app/src/main/java
         * @return 返回数据构成一条路径
         */
        String getAbsolutePath();
    }

    public static final String TYPE = "type";
    public static final String TITLE = "title";
    public static final String SUB_TITLE = "subtitle";
    public static final int TYPE_FOLD_LIST = 0;

    // 启动该Activity时传输给本Activity的数据集
    private Bundle dataBundle;
    // 声明该Activity显示内容的类型
    private int type;
    // Toolbar 设置的标题
    private String title;
    // Toolbar 设置的子标题
    private String subTitle;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.file_bread_crumb)
    BreadcrumbView<GitTreeItem> mFileBreadCrumb;

    // BreadcurumbActivity 的fragment内容
    private BaseFragment currentFragment;

    public static Intent getIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, BreadcrumbTreeActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        parseIntent();

        switch (type) {
            case TYPE_FOLD_LIST:
                currentFragment = FoldsFragment.getInstance(mFileBreadCrumb);
                break;
            default:
                break;
        }
        setUpToolbar();
        // 设置fragment内容
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, currentFragment)
                .commit();
    }

    /**
     * 从 dataBundle 数据集中获取数据用于设置 Toolbar
     */
    private void setUpToolbar() {
        title = dataBundle.getString(TITLE);
        subTitle = dataBundle.getString(SUB_TITLE);

        mToolbar.setTitle(title);
        if (!TextUtils.isEmpty(subTitle)) {
            mToolbar.setSubtitle(subTitle);
        }

        // 设置为默认工具栏杆
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 解析传输给此Activity的数据
     */
    private void parseIntent() {
        dataBundle = getIntent().getExtras();
        type = dataBundle.getInt(TYPE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_file_tree;
    }

    @Override
    public void initializeInjector() {
    }
}
