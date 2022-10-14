package com.example.sharepicture.entity;

public class discoverPic {
    public discoverPic(Long collectId, int collectNum, String content, Long createTime, Boolean hasCollect, Boolean hasFocus, Boolean hasLike, Long id, Long imageCode, Long likeId, int likeNum, Long pUserId, String title, String username, Object[] imageUrlList) {
        this.collectId = collectId;
        this.collectNum = collectNum;
        this.content = content;
        this.createTime = createTime;
        this.hasCollect = hasCollect;
        this.hasFocus = hasFocus;
        this.hasLike = hasLike;
        this.id = id;
        this.imageCode = imageCode;
        this.likeId = likeId;
        this.likeNum = likeNum;
        this.pUserId = pUserId;
        this.title = title;
        this.username = username;
        this.imageUrlList = imageUrlList;
    }

    private Long collectId;
    private int collectNum;
    private String content;
    private Long createTime;
    private Boolean hasCollect;
    private Boolean hasFocus;
    private Boolean hasLike;
    private Long id;
    private Long imageCode;
    private Long likeId;
    private int likeNum;
    private Long pUserId;
    private String title;
    private String username;
    private Object imageUrlList[];

    public Object getImageUrlList(){
        if(imageUrlList.length>0)
            return imageUrlList[0];
        else
        {
            Object nullUrl = "null";
            return nullUrl;
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getUsername(){
        return username;
    }

    public int getLikeNum(){return likeNum; }

    public int getCollectNum(){return collectNum; }

    public String toString() {
        return getTitle();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImageUrlList(Object imageUrlList[]) {
        this.imageUrlList = imageUrlList;
    }
}
