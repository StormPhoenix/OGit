package com.stormphoenix.ogit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.stormphoenix.httpknife.github.GitCommit;
import com.stormphoenix.ogit.mvp.model.interactor.CommitDetailsInteractor;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;

import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit2.Response;
import rx.Subscriber;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        CommitDetailsInteractor interactor = new CommitDetailsInteractor(appContext);
        interactor.loadSingleCommitDetails("StormPhoenix", "OGit", "bb58ed676f3bfe94b606b01b858f59dd735cb7a6")
//                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Subscriber<Response<GitCommit>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<GitCommit> response) {
                        GitCommit body = response.body();
                        System.out.println();
                    }
                });

        assertEquals("com.stormphoenix.ogit", appContext.getPackageName());
    }
}
