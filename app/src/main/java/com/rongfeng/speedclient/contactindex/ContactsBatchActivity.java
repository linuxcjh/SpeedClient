package com.rongfeng.speedclient.contactindex;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.voice.VoiceAnalysisTools;
import com.rongfeng.speedclient.voice.model.SyncClientInfoModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rongfeng.speedclient.R.id.commit_tv;


/**
 * 批量导入
 */
public class ContactsBatchActivity extends BaseActivity {

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
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(commit_tv)
    SingleClickBt commitTv;

    private List<SortModel> mAllContactsList = new ArrayList<>();

    private BatchSortAdapter adapter;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_batch_contacts);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initView();
        loadContacts();
        initListener();
    }

    private void initListener() {
        toast = Toast.makeText(AppConfig.getContext(), "", Toast.LENGTH_SHORT);
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
                BatchSortAdapter.ViewHolder viewHolder = (BatchSortAdapter.ViewHolder) view.getTag();
                viewHolder.cbChecked.performClick();
                adapter.toggleChecked(position);
                commitTv.setText("完成(" + adapter.getSelectedList().size() + ")");
            }
        });

    }


    private void initView() {

        sideBar.setTextView(dialog);

        /** 给ListView设置adapter **/
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        adapter = new BatchSortAdapter(this);
        mListView.setAdapter(adapter);
    }


    /**
     * 添加客户
     *
     * @param selectModel
     */
    private void invoke(SortModel selectModel) {
        AddClientTransModel transModel = new AddClientTransModel();
        transModel.setCustomerType("2");//客户类型【1企业客户；2个人客户】
        transModel.setCustomerName(selectModel.name);
        transModel.setCustomerTel(selectModel.number);
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTCSR, transModel,
                new TypeToken<SyncClientInfoModel>() {
                });

    }

    int i = 0;

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.INSERTCSR:

                if (status == 1) {
                    i++;
                    toast.setText("导入成功");
                    toast.show();
                    List<SyncClientInfoModel> list = new ArrayList<>();
                    SyncClientInfoModel m = (SyncClientInfoModel) data;
                    list.add(m);
                    VoiceAnalysisTools.getInstance().analysisData(list);//新增客户插入数据库
                    if (i == adapter.getSelectedList().size()) {
                        finish();
                    }
                }

                break;


        }

    }

    /**
     * 加载通讯录
     */
    private void loadContacts() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ContentResolver resolver = getApplicationContext().getContentResolver();
                    Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, new String[]{Phone.DISPLAY_NAME, Phone.NUMBER, "sort_key"}, null, null, "sort_key COLLATE LOCALIZED ASC");
                    if (phoneCursor == null || phoneCursor.getCount() == 0) {
                        toast.setText("未获得读取联系人权限 或 未获得联系人数据");
                        toast.show();
                        return;
                    }
                    int PHONES_NUMBER_INDEX = phoneCursor.getColumnIndex(Phone.NUMBER);
                    int PHONES_DISPLAY_NAME_INDEX = phoneCursor.getColumnIndex(Phone.DISPLAY_NAME);
                    int SORT_KEY_INDEX = phoneCursor.getColumnIndex("sort_key");
                    if (phoneCursor.getCount() > 0) {
                        mAllContactsList.clear();
                        while (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                            if (TextUtils.isEmpty(phoneNumber))
                                continue;
                            String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                            String sortKey = phoneCursor.getString(SORT_KEY_INDEX);
                            SortModel sortModel = new SortModel(contactName, phoneNumber, sortKey);
                            //优先使用系统sortkey取,取不到再使用工具取
                            String sortLetters = getSortLetterBySortKey(sortKey);
                            if (sortLetters == null) {
                                sortLetters = getSortLetter(contactName);
                            }
                            sortModel.sortLetters = sortLetters;
                            sortModel.sortToken = parseSortKey(sortKey);

//                            if (!TextUtils.isEmpty(invited) && invited.contains(sortModel.number)) {//一存在
//                                sortModel.isExist = true;
//                            }
                            mAllContactsList.add(sortModel);
                        }
                    }
                    phoneCursor.close();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Collections.sort(mAllContactsList, pinyinComparator);
                            adapter.updateListView(mAllContactsList);
                        }
                    });
                } catch (Exception e) {
                    Log.e("xbc", e.getLocalizedMessage());
                }
            }
        }).start();
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
        String pinyin = characterParser.getSelling(name);
        String sortString = pinyin.substring(0, 1).toUpperCase(Locale.CHINESE);

        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            letter = sortString.toUpperCase(Locale.CHINESE);
        }
        return letter;
    }

    /**
     * 取sort_key的首字母
     *
     * @param sortKey
     * @return
     */
    private String getSortLetterBySortKey(String sortKey) {
        if (sortKey == null || "".equals(sortKey.trim())) {
            return null;
        }
        String letter = "#";
        //汉字转换成拼音
        String sortString = sortKey.trim().substring(0, 1).toUpperCase(Locale.CHINESE);
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


    @OnClick({R.id.cancel_tv, commit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case commit_tv:
                for (SortModel m : adapter.getSelectedList()) {
                    invoke(m);
                }
                break;
        }
    }
}