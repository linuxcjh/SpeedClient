package com.rongfeng.speedclient.product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.components.AddProductDialog;
import com.rongfeng.speedclient.contactindex.PinyinComparator;
import com.rongfeng.speedclient.contactindex.SideBar;
import com.rongfeng.speedclient.contactindex.SortModel;
import com.rongfeng.speedclient.contactindex.SortToken;
import com.rongfeng.speedclient.entity.BaseDataModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProductActivity extends BaseActivity {

    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.ivClearText)
    ImageView ivClearText;
    @Bind(R.id.search_layout)
    RelativeLayout searchLayout;
    @Bind(R.id.layoutContainer)
    FrameLayout layoutContainer;
    @Bind(R.id.lv_contacts)
    ListView mListView;
    @Bind(R.id.dialog)
    TextView dialog;
    @Bind(R.id.sidrbar)
    SideBar sideBar;
    @Bind(R.id.add_client_tv)
    ImageView addClientTv;
    @Bind(R.id.no_layout)
    LinearLayout noLayout;
    @Bind(R.id.complete)
    TextView complete;


    private List<SortModel> mAllContactsList;

    private ProductSortAdapter adapter;


    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_layout);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initView();
        invoke();
        initListener();
    }

    private void initListener() {

        /**清除输入字符**/
        ivClearText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable e) {

                String content = etSearch.getText().toString();
                if ("".equals(content)) {
                    ivClearText.setVisibility(View.INVISIBLE);
                } else {
                    ivClearText.setVisibility(View.VISIBLE);
                }
                if (content.length() > 0) {
                    ArrayList<SortModel> fileterList = (ArrayList<SortModel>) search(content);
                    adapter.updateListView(fileterList);
                    //mAdapter.updateData(mContacts);
                } else {
                    adapter.updateListView(mAllContactsList);
                }
                mListView.setSelection(0);

            }

        });

        //设置右侧[A-Z]快速导航栏触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }
            }
        });
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
//                ProductSortAdapter.ViewHolder viewHolder = (ProductSortAdapter.ViewHolder) view.getTag();
//                viewHolder.cbChecked.performClick();
//                adapter.toggleChecked(position);
//                complete.setText("完成(" + adapter.getSelectedList().size() + ")");
                setResult(RESULT_OK, new Intent().putExtra("model", mAllContactsList.get(position)));
                finish();

            }
        });


        addClientTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProductDialog dialog = new AddProductDialog(ProductActivity.this, mHandler);
                dialog.buildDialog().setTitle("产品或服务");
            }
        });

        complete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent().putExtra("selectList", BasePresenter.gson.toJson(adapter.getSelectedList())));
                finish();
            }
        });
    }

    private void initView() {

        sideBar.setTextView(dialog);

        /** 给ListView设置adapter **/
        mAllContactsList = new ArrayList<SortModel>();
        pinyinComparator = new PinyinComparator();
        Collections.sort(mAllContactsList, pinyinComparator);// 根据a-z进行排序源数据
        adapter = new ProductSortAdapter(this, mAllContactsList, mHandler);
        mListView.setAdapter(adapter);
    }


    Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {

                case Constant.CONFIRMDIALOG:

                    insertInvoke((String) msg.obj);
                    break;
            }
        }

    };


    /**
     * 查询产品
     *
     * @param
     */
    private void invoke() {
        transDataModel.setFlg("6");
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCATEGORYLIST, transDataModel,
                new TypeToken<List<BaseDataModel>>() {
                });

    }

    /**
     * 新增
     */
    private void insertInvoke(String name) {
        transDataModel.setProductName(name);
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTPRODUCT, transDataModel,
                new TypeToken<BaseDataModel>() {
                });

    }


    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.SEARCHCATEGORYLIST:
                if (data != null) {
                    List<BaseDataModel> list = (List<BaseDataModel>) data;
                    if (list.size() == 0) {
                        noLayout.setVisibility(View.VISIBLE);
                    } else {
                        noLayout.setVisibility(View.GONE);
                    }
                    mAllContactsList.clear();
                    for (BaseDataModel model : list) {
                        SortModel sortModel = new SortModel(model.getDictionaryName(), model.getDictionaryId(), "");
                        sortModel.sortLetters = getSortLetter(model.getDictionaryName());
                        sortModel.sortToken = parseSortKey(model.getDictionaryName());
                        mAllContactsList.add(sortModel);
                        Collections.sort(mAllContactsList, pinyinComparator);// 根据a-z进行排序源数据
                        adapter = new ProductSortAdapter(ProductActivity.this, mAllContactsList, mHandler);
                        mListView.setAdapter(adapter);
                    }
                }
                break;
            case XxbService.INSERTPRODUCT:

                if (status == 1) {
                    AppTools.getToast("新增成功");
                    invoke();
                }

                break;
        }

    }

    /**
     * 名字转拼音,取首字母
     *
     * @param name
     * @return
     */
    private String getSortLetter(String name) {
        String letter = "#";
        if (TextUtils.isEmpty(name)) {
            return letter;
        }
        //汉字转换成拼音
        String pinyin = AppTools.convertPinYin(name.toString().trim());
        String sortString = pinyin.substring(0, 1).toUpperCase(Locale.CHINESE);

        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            letter = sortString.toUpperCase(Locale.CHINESE);
        }
        return letter;
    }


    /**
     * 模糊查询
     *
     * @param str
     * @return
     */
    private List<SortModel> search(String str) {
        List<SortModel> filterList = new ArrayList<SortModel>();// 过滤后的list
        //if (str.matches("^([0-9]|[/+])*$")) {// 正则表达式 匹配号码
        if (str.matches("^([0-9]|[/+]).*")) {// 正则表达式 匹配以数字或者加号开头的字符串(包括了带空格及-分割的号码)
            String simpleStr = str.replaceAll("\\-|\\s", "");
            for (SortModel contact : mAllContactsList) {
                if (contact.number != null && contact.name != null) {
                    if (contact.simpleNumber.contains(simpleStr) || contact.name.contains(str)) {
                        if (!filterList.contains(contact)) {
                            filterList.add(contact);
                        }
                    }
                }
            }
        } else {
            for (SortModel contact : mAllContactsList) {
                if (contact.number != null && contact.name != null) {
                    //姓名全匹配,姓名首字母简拼匹配,姓名全字母匹配
                    if (contact.name.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))
                            || contact.sortKey.toLowerCase(Locale.CHINESE).replace(" ", "").contains(str.toLowerCase(Locale.CHINESE))
                            || contact.sortToken.simpleSpell.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))
                            || contact.sortToken.wholeSpell.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))) {
                        if (!filterList.contains(contact)) {
                            filterList.add(contact);
                        }
                    }
                }
            }
        }
        return filterList;
    }

    String chReg = "[\\u4E00-\\u9FA5]+";//中文字符串匹配

    //String chReg="[^\\u4E00-\\u9FA5]";//除中文外的字符匹配

    /**
     * 解析sort_key,封装简拼,全拼
     *
     * @param sortKey
     * @return
     */
    public SortToken parseSortKey(String sortKey) {
        SortToken token = new SortToken();
        if (sortKey != null && sortKey.length() > 0) {
            //其中包含的中文字符
            String[] enStrs = sortKey.replace(" ", "").split(chReg);
            for (int i = 0, length = enStrs.length; i < length; i++) {
                if (enStrs[i].length() > 0) {
                    //拼接简拼
                    token.simpleSpell += enStrs[i].charAt(0);
                    token.wholeSpell += enStrs[i];
                }
            }
        }
        return token;
    }


    @OnClick(R.id.cancel_tv)
    public void onClick() {

        finish();
    }


}