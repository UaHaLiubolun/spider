function convert(url) {
    var b = Math.floor(100 * Math.random()) + 1,
        a = url.indexOf("url="),
        c = url.indexOf("&k=");
    -1 !== a && -1 === c && (a = url.substr(a + 4 + parseInt("26") + b, 1), url += "&k=" + b + "&h=" + a)
    return url;
}

	