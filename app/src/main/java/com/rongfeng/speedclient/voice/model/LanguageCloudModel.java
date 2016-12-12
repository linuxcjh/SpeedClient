package com.rongfeng.speedclient.voice.model;

/**
 * Created by chen on 2016/12/11.
 */

public class LanguageCloudModel {

//    api_key=YourApiKey&text=我是中国人。&pattern=dp&format=plain

    private String api_key = "y164S767l7d3s0s5h7R1UIZHkaYzGWeRsP5HmJqe";
    private String text="云天化股份有限公司";
    private String pattern = "ws";//用以指定分析模式，可选值包括ws(分词)，pos(词性标注)，ner(命名实体识别)，dp(依存句法分析)，srl(语义角色标注),all(全部任务)
    private String format="json";//用以指定结果格式类型，可选值包括xml(XML格式)，json(JSON格式)，conll(CONLL格式)，plain(简洁文本格式)

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
