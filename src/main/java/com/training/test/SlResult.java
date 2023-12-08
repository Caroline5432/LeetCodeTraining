package com.training.test;

/**
 * @author zhangliujie
 * @Description
 * @date 2020/1/14.
 */
public class SlResult {

    private String jbfyid;//经办法院id
    private Integer xsajsz;//新收案件数值
    private Integer jcajsz;//旧存案件数值
    private Integer yjajsz;//已结案件数值
    private Integer wjajsz;//未结案件数值

    public String getJbfyid() {
        return jbfyid;
    }

    public void setJbfyid(String jbfyid) {
        this.jbfyid = jbfyid;
    }

    public Integer getXsajsz() {
        return xsajsz;
    }

    public void setXsajsz(Integer xsajsz) {
        this.xsajsz = xsajsz;
    }

    public Integer getJcajsz() {
        return jcajsz;
    }

    public void setJcajsz(Integer jcajsz) {
        this.jcajsz = jcajsz;
    }

    public Integer getYjajsz() {
        return yjajsz;
    }

    public void setYjajsz(Integer yjajsz) {
        this.yjajsz = yjajsz;
    }

    public Integer getWjajsz() {
        return wjajsz;
    }

    public void setWjajsz(Integer wjajsz) {
        this.wjajsz = wjajsz;
    }

    @Override
    public String toString() {
        return "SlResult{" +
                "jbfyid='" + jbfyid + '\'' +
                ", xsajsz=" + xsajsz +
                ", jcajsz=" + jcajsz +
                ", yjajsz=" + yjajsz +
                ", wjajsz=" + wjajsz +
                '}';
    }

    public SlResult() {
    }

    public SlResult(String jbfyid, Integer xsajsz, Integer jcajsz, Integer yjajsz, Integer wjajsz) {
        this.jbfyid = jbfyid;
        this.xsajsz = xsajsz;
        this.jcajsz = jcajsz;
        this.yjajsz = yjajsz;
        this.wjajsz = wjajsz;
    }
}
