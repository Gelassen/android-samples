package com.coderbunker.javarxsample.operators;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coderbunker.javarxsample.App;
import com.coderbunker.javarxsample.BaseActivity;
import com.coderbunker.javarxsample.R;
import com.coderbunker.javarxsample.dto.CityItem;
import com.coderbunker.javarxsample.operators.view.ICityPresenter;
import com.coderbunker.javarxsample.operators.view.ISubjectPresenter;
import com.coderbunker.javarxsample.operators.view.IView;
import com.coderbunker.javarxsample.operators.view.OperatorsCityPresenter;
import com.coderbunker.javarxsample.operators.view.SubjectPresenter;

import java.util.List;

public class OperatorsActivity extends BaseActivity implements IView{

    public static void start(Context context) {
        Intent intent = new Intent(context, OperatorsActivity.class);
        context.startActivity(intent);
    }

    private Button runMap;
    private Button runFilter;
    private Button runNextItems;
    private TextView resultView;

    private ICityPresenter presenter;
    private ISubjectPresenter subjectPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators);

        presenter = new OperatorsCityPresenter(this);
        subjectPresenter = new SubjectPresenter();

        runMap = (Button) findViewById(R.id.operators_map);
        runMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(App.TAG, "Run operators");
                presenter.runMap();
            }
        });

        runFilter = (Button) findViewById(R.id.operators_filter);
        runFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(App.TAG, "Run filter");
                presenter.runFilter();
            }
        });

        runNextItems = (Button) findViewById(R.id.operators_get_next);
        runNextItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.takeNextItems(2, 1);
            }
        });

        findViewById(R.id.operators_concat)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.runConcat();
            }
        });

        findViewById(R.id.operators_merge)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.runMerge();
            }
        });

        findViewById(R.id.operators_zip)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.runZip();
            }
        });

        findViewById(R.id.operators_cache)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.runDataFromCache();
                    }
                });

        findViewById(R.id.operators_exception)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.runDataWithException();
                    }
                });

        findViewById(R.id.exception_propagate)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.runWithExceptionPropagate();
                    }
                });

        findViewById(R.id.exception_observable)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.runWithExceptionObservable();
            }
        });

        findViewById(R.id.subject_publish)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectPresenter.onPublishSubject();
            }
        });

        findViewById(R.id.subject_reply)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subjectPresenter.onReplySubject();
                    }
                });

        findViewById(R.id.subject_async)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subjectPresenter.onAsyncSubject();
                    }
                });

        findViewById(R.id.subject_observer_chain)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.runConnectedObservable();
                    }
                });

        findViewById(R.id.subject_observer_threads)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.runChainWithPartsInUIThread();
                    }
                });

        resultView = (TextView) findViewById(R.id.result);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showResult(List<CityItem> result) {
        Log.d(App.TAG, "Show result: " + result.size());
    }

    @Override
    public void showResult(String str) {
        Log.d(App.TAG, "Show result: " + str);
        resultView.setText(str);
    }

    @Override
    public void showError() {
        Log.e(App.TAG, "Show error");
    }
}
