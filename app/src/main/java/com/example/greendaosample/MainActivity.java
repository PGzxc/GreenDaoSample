package com.example.greendaosample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.greendaosample.bean.User;
import com.example.greendaosample.db.DBHelper;
import com.example.greendaosample.gen.UserDao;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG=MainActivity.class.getSimpleName();

    @BindView(R.id.tv_content)
    TextView tvContent;
    private UserDao userDao;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userDao= DBHelper.getDaoSession(this).getUserDao();
        setListener();
    }

    private void setListener() {
        findViewById(R.id.btn_save).setOnClickListener(view -> saveData());
        findViewById(R.id.btn_update).setOnClickListener(view -> updateData());
        findViewById(R.id.btn_del).setOnClickListener(view -> delData());
        findViewById(R.id.btn_query).setOnClickListener(view -> queryData());
    }

    private void saveData() {
        Observable.create((Observable.OnSubscribe<User>) subscriber -> {
            user=new User( 1l,"张三");
            userDao.insert(user);
            subscriber.onNext(user);

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        tvContent.setText("数据已保存"+user.toString());
                        Log.d(TAG, "onError: "+e);
                    }

                    @Override
                    public void onNext(User user) {
                         Log.d(TAG, "onNext: ");
                        tvContent.setText(user.toString());
                    }
                });

    }

    private void updateData() {
        Observable.create((Observable.OnSubscribe<User>) subscriber -> {
            user=new User(2l,"lisi");
            userDao.update(user);
            subscriber.onNext(user);

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(User user) {
                        tvContent.setText(user.toString());
                    }
                });
    }

    private void delData() {
        Observable.create((Observable.OnSubscribe<Boolean>) subscriber -> {
            userDao.deleteByKey(1l);
            subscriber.onNext(false);

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean b) {

                        tvContent.setText("数据已删除");
                    }
                });
    }
    private void queryData() {
        Observable.create((Observable.OnSubscribe<List<User>>) subscriber -> {
            List<User> users = userDao.loadAll();
            subscriber.onNext(users);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<User> users) {
                        tvContent.setText("查询全部数据==>"+users.toString());
                    }
                });
    }
}
