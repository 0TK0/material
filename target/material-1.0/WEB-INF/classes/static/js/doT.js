
/* Laura Doktorova https://github.com/olado/doT */
define(function(require) {
    (function () {
        function p(b, a, d) {
            return ("string" === typeof a ? a : a.toString()).replace(b.define || h, function (a, c, e, g) {
                0 === c.indexOf("def.") && (c = c.substring(4));
                c in d || (":" === e ? (b.defineParams && g.replace(b.defineParams, function (a, b, l) {
                    d[c] = {arg: b, text: l}
                }), c in d || (d[c] = g)) : (new Function("def", "def['" + c + "']=" + g))(d));
                return ""
            }).replace(b.use || h, function (a, c) {
                b.useParams && (c = c.replace(b.useParams, function (a, b, c, l) {
                    if (d[c] && d[c].arg && l)return a = (c + ":" + l).replace(/'|\\/g, "_"), d.__exp = d.__exp || {}, d.__exp[a] =
                        d[c].text.replace(new RegExp("(^|[^\\w$])" + d[c].arg + "([^\\w$])", "g"), "$1" + l + "$2"), b + "def.__exp['" + a + "']"
                }));
                var e = (new Function("def", "return " + c))(d);
                return e ? p(b, e, d) : e
            })
        }

        function k(b) {
            return b.replace(/\\('|\\)/g, "$1").replace(/[\r\t\n]/g, " ")
        }

        var f = {
            version: "1.0.3", templateSettings: {
                evaluate: /\{\{([\s\S]+?(\}?)+)\}\}/g,
                interpolate: /\{\{=([\s\S]+?)\}\}/g,
                encode: /\{\{!([\s\S]+?)\}\}/g,
                use: /\{\{#([\s\S]+?)\}\}/g,
                useParams: /(^|[^\w$])def(?:\.|\[[\'\"])([\w$\.]+)(?:[\'\"]\])?\s*\:\s*([\w$\.]+|\"[^\"]+\"|\'[^\']+\'|\{[^\}]+\})/g,
                define: /\{\{##\s*([\w\.$]+)\s*(\:|=)([\s\S]+?)#\}\}/g,
                defineParams: /^\s*([\w$]+):([\s\S]+)/,
                conditional: /\{\{\?(\?)?\s*([\s\S]*?)\s*\}\}/g,
                iterate: /\{\{~\s*(?:\}\}|([\s\S]+?)\s*\:\s*([\w$]+)\s*(?:\:\s*([\w$]+))?\s*\}\})/g,
                varname: "it",
                strip: !0,
                append: !0,
                selfcontained: !1,
                doNotSkipEncoded: !1
            }, template: void 0, compile: void 0
        }, m;
        f.encodeHTMLSource = function (b) {
            var a = {
                "&": "&#38;",
                "<": "&#60;",
                ">": "&#62;",
                '"': "&#34;",
                "'": "&#39;",
                "/": "&#47;"
            }, d = b ? /[&<>"'\/]/g : /&(?!#?\w+;)|<|>|"|'|\//g;
            return function (b) {
                return b ?
                    b.toString().replace(d, function (b) {
                        return a[b] || b
                    }) : ""
            }
        };
        m = function () {
            return this || (0, eval)("this")
        }();
        "undefined" !== typeof module && module.exports ? module.exports = f : "function" === typeof define && define.amd ? define(function () {
            return f
        }) : m.doT = f;
        var r = {start: "'+(", end: ")+'", startencode: "'+encodeHTML("}, s = {
            start: "';out+=(",
            end: ");out+='",
            startencode: "';out+=encodeHTML("
        }, h = /$^/;
        f.template = function (b, a, d) {
            a = a || f.templateSettings;
            var n = a.append ? r : s, c, e = 0, g;
            b = a.use || a.define ? p(a, b, d || {}) : b;
            b = ("var out='" + (a.strip ?
                b.replace(/(^|\r|\n)\t* +| +\t*(\r|\n|$)/g, " ").replace(/\r|\n|\t|\/\*[\s\S]*?\*\//g, "") : b).replace(/'|\\/g, "\\$&").replace(a.interpolate || h, function (b, a) {
                return n.start + k(a) + n.end
            }).replace(a.encode || h, function (b, a) {
                c = !0;
                return n.startencode + k(a) + n.end
            }).replace(a.conditional || h, function (b, a, c) {
                return a ? c ? "';}else if(" + k(c) + "){out+='" : "';}else{out+='" : c ? "';if(" + k(c) + "){out+='" : "';}out+='"
            }).replace(a.iterate || h, function (b, a, c, d) {
                if (!a)return "';} } out+='";
                e += 1;
                g = d || "i" + e;
                a = k(a);
                return "';var arr" +
                    e + "=" + a + ";if(arr" + e + "){var " + c + "," + g + "=-1,l" + e + "=arr" + e + ".length-1;while(" + g + "<l" + e + "){" + c + "=arr" + e + "[" + g + "+=1];out+='"
            }).replace(a.evaluate || h, function (a, b) {
                return "';" + k(b) + "out+='"
            }) + "';return out;").replace(/\n/g, "\\n").replace(/\t/g, "\\t").replace(/\r/g, "\\r").replace(/(\s|;|\}|^|\{)out\+='';/g, "$1").replace(/\+''/g, "");
            c && (a.selfcontained || !m || m._encodeHTML || (m._encodeHTML = f.encodeHTMLSource(a.doNotSkipEncoded)), b = "var encodeHTML = typeof _encodeHTML !== 'undefined' ? _encodeHTML : (" + f.encodeHTMLSource.toString() +
                "(" + (a.doNotSkipEncoded || "") + "));" + b);
            try {
                return new Function(a.varname, b)
            } catch (q) {
                throw"undefined" !== typeof console && console.log("Could not create a template function: " + b), q;
            }
        };
        f.compile = function (b, a) {
            return f.template(b, null, a)
        }
    })();
});