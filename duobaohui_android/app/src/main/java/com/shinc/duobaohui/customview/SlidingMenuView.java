package com.shinc.duobaohui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.adapter.MenuListAdapter;
import com.shinc.duobaohui.bean.MenuListBean;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.event.CategoryMenuEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.web.MyHttpUtils;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 名称：SlidingMenuView
 * 作者：zhaopl 时间: 15/11/17.
 * 实现的主要功能： 侧拉菜单；
 */
public class SlidingMenuView extends RelativeLayout {

    private Context context;
    private View view;

    private ListView menuListView;

    private RelativeLayout menuTitle;

    private OnListItemClickListener onListItemClickListener;

    private onTitleClickListener onTitleClickListener;

    private MenuListAdapter adapter;

    private ArrayList<MenuListBean.CategoryMenu> menuList;

    private WaitLoadingUtils loadingUtils;

    private DbUtils dbUtils;


    public void setOnTitleClickListener(SlidingMenuView.onTitleClickListener onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
    }

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    public SlidingMenuView(Context context) {
        super(context);
        initView(context);
        EventBus.getDefault().register(this);
        initListener(context);
    }

    public SlidingMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

        initListener(context);
    }

    public SlidingMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

        initListener(context);
    }

    public SlidingMenuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);

        initListener(context);
    }

    private void initView(Context context) {


        dbUtils = DbUtils.create(context);
        EventBus.getDefault().register(this);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.sliding_menu_layout, this);

        menuListView = (ListView) view.findViewById(R.id.menu_list);
        menuTitle = (RelativeLayout) view.findViewById(R.id.sliding_menu_title);

        loadingUtils = new WaitLoadingUtils(view);
        initData(context);

    }

    private void initListener(final Context context) {
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo 点击目录的时候进行的跳转；
                if (onListItemClickListener != null && menuList != null) {
                    onListItemClickListener.onLiteItemClick(position, menuList.get(position).getId(), menuList.get(position).getCat_name());
                }
            }
        });

        menuTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击title的时候进行的操作；
                if (onTitleClickListener != null) {
                    onTitleClickListener.onTitleClick();
                }
            }
        });

    }

    /**
     * 进行数据的加载；
     */
    private void initData(Context context) {
        loadingUtils.show();
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        httpUtils.sendHttpPost(requestParams, ConstantApi.CATEGORY_MENU, new GetCategoryMenuRequestImpl(), context);
    }


    /**
     * 发送请求；
     */
    public class GetCategoryMenuRequestImpl implements HttpSendInterFace {


        @Override
        public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

            MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.GET, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {

                MenuListBean bean;

                @Override
                public void onSuccess(final ResponseInfo<String> responseInfo) {
                    try {
                        bean = GsonUtil.json2Bean(context, responseInfo.result, MenuListBean.class);
                    } catch (Exception e) {
                        e.printStackTrace();

                        EventBus.getDefault().post(new CategoryMenuEvent(bean));
                    }

                    EventBus.getDefault().post(new CategoryMenuEvent(bean));
                    //todo 得到数据后，进行的操作；
                }

                @Override
                public void onFailure(HttpException e, String s) {

                    EventBus.getDefault().post(new CategoryMenuEvent(bean));
                    //todo 获取数据失败后进行的操作；
                }
            });

        }

        @Override
        public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
        }
    }


    /**
     * 拿到category list event;
     *
     * @param event
     */
    public void onEventMainThread(CategoryMenuEvent event) {

//        List<MenuListBean.CategoryMenu> categoryMenuss = new ArrayList<>();
//        try {
//            categoryMenuss = dbUtils.findAll(Selector.from(MenuListBean.CategoryMenu.class).where("id", "=", "id"));
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
        loadingUtils.disable();
        MenuListBean menuListBean = event.getMenuListBean();
        if (menuListBean != null && "1".equals(menuListBean.getCode())) {
            if (menuListBean.getData() != null) {
                menuList = menuListBean.getData();

                if (adapter == null) {
                    adapter = new MenuListAdapter(context);
                    menuListView.setAdapter(adapter);
                }
                adapter.setList(menuListBean.getData());
                adapter.notifyDataSetChanged();

                //todo 进行保存处理；

                try {
                    dbUtils.saveOrUpdateAll(menuList);

                } catch (DbException e) {
                    e.printStackTrace();
                }

            } else {
                List<MenuListBean.CategoryMenu> categoryMenus = new ArrayList<MenuListBean.CategoryMenu>();
                try {
                    categoryMenus = dbUtils.findAll(Selector.from(MenuListBean.CategoryMenu.class).where("id", "=", "id"));
                } catch (DbException e) {
                    e.printStackTrace();
                }

                if (categoryMenus != null) {
                    if (adapter == null) {
                        adapter = new MenuListAdapter(context);
                        menuListView.setAdapter(adapter);
                    }
                    adapter.setList((ArrayList<MenuListBean.CategoryMenu>) categoryMenus);
                    adapter.notifyDataSetChanged();
                } else {
                    //数据为null;
                    Toast.makeText(context, "获取分类数据失败，点击刷新！", Toast.LENGTH_SHORT).show();

                }
            }
        } else {
            List<MenuListBean.CategoryMenu> categoryMenus = new ArrayList<MenuListBean.CategoryMenu>();
            try {
                categoryMenus = dbUtils.findAll(Selector.from(MenuListBean.CategoryMenu.class).where("id", "=", "id"));

                //Log.e("categorymenus", categoryMenus.toString());
            } catch (DbException e) {
                e.printStackTrace();
            }

            if (categoryMenus != null && categoryMenus.size() > 0) {
                if (adapter == null) {
                    adapter = new MenuListAdapter(context);
                    menuListView.setAdapter(adapter);
                }
                adapter.setList((ArrayList<MenuListBean.CategoryMenu>) categoryMenus);
                adapter.notifyDataSetChanged();
            } else {
                noWeb();
            }
        }
    }


    /**
     * 点击Item时候，执行的回调；
     */
    public interface OnListItemClickListener {
        void onLiteItemClick(int position, String Id, String catName);
    }


    /**
     * 点击title的时候，进行的回调；
     */
    public interface onTitleClickListener {
        void onTitleClick();
    }


    private void noWeb() {
        loadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                loadingUtils.haveWeb();
                loadingUtils.show();
                initData(context);
            }
        });
    }
}
