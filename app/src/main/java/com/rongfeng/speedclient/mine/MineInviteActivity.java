package com.rongfeng.speedclient.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.contactindex.ContactsActivity;
import com.rongfeng.speedclient.contactindex.InviteSortAdapter;
import com.rongfeng.speedclient.contactindex.PinyinComparator;
import com.rongfeng.speedclient.contactindex.SideBar;
import com.rongfeng.speedclient.contactindex.SortModel;
import com.rongfeng.speedclient.contactindex.SortToken;
import com.rongfeng.speedclient.dynamic.model.InviteModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 我的邀请
 */
public class MineInviteActivity extends BaseActivity {

    TextView inviteTv;
    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.invent_tv)
    TextView inventTv;
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
    @Bind(R.id.invite_bottom_bt)
    Button inviteBottomBt;
    @Bind(R.id.no_layout)
    LinearLayout noLayout;


    private List<SortModel> mAllContactsList;
    private InviteSortAdapter adapter;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_mine_invent_layout);
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
    }

    private void initView() {
        sideBar.setTextView(dialog);
        mAllContactsList = new ArrayList<>();
        pinyinComparator = new PinyinComparator();

    }


    @OnClick({R.id.cancel_tv, R.id.invent_tv, R.id.invite_bottom_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.invite_bottom_bt:

                startActivityForResult(new Intent(MineInviteActivity.this, ContactsActivity.class).putExtra("list", ""), 0x11);

                break;
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.invent_tv:
                String exist = BasePresenter.gson.toJson(mAllContactsList);
                startActivityForResult(new Intent(MineInviteActivity.this, ContactsActivity.class).putExtra("list", exist), 0x11);

                break;
        }
    }


    /**
     * 邀请列表
     *
     * @param
     */
    private void invoke() {
        commonPresenter.isShowProgressDialog = true;
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHINVITER, new TypeToken<List<InviteModel>>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        if (data != null) {

            List<InviteModel> list = (List<InviteModel>) data;
            if (list.size() == 0) {
                noLayout.setVisibility(View.VISIBLE);
            } else {
                noLayout.setVisibility(View.GONE);
            }

            for (InviteModel model : list) {
                if (TextUtils.isEmpty(model.getInviteeUserPhone()))
                    continue;
                SortModel sortModel = new SortModel(model.getInviteeUserName(), model.getInviteeUserPhone(), "");
                sortModel.sortLetters = getSortLetter(model.getInviteeUserName());
                sortModel.sortToken = parseSortKey(model.getInviteeUserName());
                sortModel.isForbidden = model.getIsForbidden();
                mAllContactsList.add(sortModel);
                Collections.sort(mAllContactsList, pinyinComparator);// 根据a-z进行排序源数据
                adapter = new InviteSortAdapter(this, mAllContactsList);
                mListView.setAdapter(adapter);
            }
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
        if (name == null) {
            return letter;
        }
        //汉字转换成拼音
        String pinyin = AppTools.convertPinYin(name);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 0x11) {
//                List<SortModel> results = BasePresenter.gson.fromJson(data.getSerializableExtra("list"), new TypeToken<List<SortModel>>() {
//                }.getType());
                List<SortModel> results = (List<SortModel>) data.getSerializableExtra("list");
                if (results.size() > 0) {
                    noLayout.setVisibility(View.GONE);
                    mAllContactsList.addAll(results);
                    Collections.sort(mAllContactsList, pinyinComparator);// 根据a-z进行排序源数据

                    if (adapter != null) {
                        adapter.updateListView(mAllContactsList);
                    } else {
                        adapter = new InviteSortAdapter(this, mAllContactsList);
                        mListView.setAdapter(adapter);
                    }
                }
            }
        }
    }


}