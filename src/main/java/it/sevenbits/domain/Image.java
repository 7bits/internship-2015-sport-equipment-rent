package it.sevenbits.domain;

/**
 * Created by awemath on 8/12/15.
 */
public class Image {
    private String url;
    private long id;
    private long goodsId;

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(final long goodsId) {
        this.goodsId = goodsId;
    }
}
