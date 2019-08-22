/*
 * @projectName spider
 * @package cn.stark.spider.common.bean
 * @className cn.stark.spider.common.bean.DouBanRequestBuilder
 * @copyright Copyright 2019 Thuisoft, Inc. All rights reserved.
 */
package cn.stark.spider.common.bean;

/**
 * DouBanRequestBuilder
 * @description TODO
 * @author liubolun
 * @date 2019年08月22日 11:45
 * @version 3.0.0
 */
public final class DouBanRequestBuilder {
    private String tags = "";

    private String sort = "U";

    private String range = "5,10";

    private String genres = "";

    private String countries = "";

    private String year_range = "";

    private DouBanRequestBuilder() {
    }

    public static DouBanRequestBuilder douBanRequestBuilder() {
        return new DouBanRequestBuilder();
    }

    public DouBanRequestBuilder withTags(String tags) {
        this.tags = tags;
        return this;
    }

    public DouBanRequestBuilder withSort(String sort) {
        this.sort = sort;
        return this;
    }

    public DouBanRequestBuilder withRange(String range) {
        this.range = range;
        return this;
    }

    public DouBanRequestBuilder withGenres(String genres) {
        this.genres = genres;
        return this;
    }

    public DouBanRequestBuilder withCountries(String countries) {
        this.countries = countries;
        return this;
    }

    public DouBanRequestBuilder withYear_range(String year_range) {
        this.year_range = year_range;
        return this;
    }

    public DouBanRequest build() {
        DouBanRequest douBanRequest = new DouBanRequest();
        douBanRequest.setTags(tags);
        douBanRequest.setSort(sort);
        douBanRequest.setRange(range);
        douBanRequest.setGenres(genres);
        douBanRequest.setCountries(countries);
        douBanRequest.setYear_range(year_range);
        return douBanRequest;
    }
}
