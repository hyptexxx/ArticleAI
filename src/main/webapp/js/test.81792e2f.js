(function(e){function t(t){for(var o,u,c=t[0],i=t[1],s=t[2],p=0,d=[];p<c.length;p++)u=c[p],Object.prototype.hasOwnProperty.call(r,u)&&r[u]&&d.push(r[u][0]),r[u]=0;for(o in i)Object.prototype.hasOwnProperty.call(i,o)&&(e[o]=i[o]);l&&l(t);while(d.length)d.shift()();return a.push.apply(a,s||[]),n()}function n(){for(var e,t=0;t<a.length;t++){for(var n=a[t],o=!0,c=1;c<n.length;c++){var i=n[c];0!==r[i]&&(o=!1)}o&&(a.splice(t--,1),e=u(u.s=n[0]))}return e}var o={},r={test:0,index:0},a=[];function u(t){if(o[t])return o[t].exports;var n=o[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,u),n.l=!0,n.exports}u.m=e,u.c=o,u.d=function(e,t,n){u.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},u.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},u.t=function(e,t){if(1&t&&(e=u(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(u.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var o in e)u.d(n,o,function(t){return e[t]}.bind(null,o));return n},u.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return u.d(t,"a",t),t},u.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},u.p="/";var c=window["webpackJsonp"]=window["webpackJsonp"]||[],i=c.push.bind(c);c.push=t,c=c.slice();for(var s=0;s<c.length;s++)t(c[s]);var l=i;a.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("cd49")},cd49:function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var o=n("2b0e"),r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"app"}},[n("MainComponent",{attrs:{response:"response"}})],1)},a=[],u=n("d4ec"),c=n("262e"),i=n("2caf"),s=n("9ab4"),l=n("60a3"),p=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("div",{staticStyle:{display:"flex","flex-direction":"column"}},[n("input",{directives:[{name:"model",rawName:"v-model",value:e.docInfo.language,expression:"docInfo.language"}],attrs:{type:"text"},domProps:{value:e.docInfo.language},on:{input:function(t){t.target.composing||e.$set(e.docInfo,"language",t.target.value)}}}),n("input",{directives:[{name:"model",rawName:"v-model",value:e.docInfo.maxNgramSize,expression:"docInfo.maxNgramSize"}],attrs:{type:"number"},domProps:{value:e.docInfo.maxNgramSize},on:{input:function(t){t.target.composing||e.$set(e.docInfo,"maxNgramSize",t.target.value)}}}),n("input",{directives:[{name:"model",rawName:"v-model",value:e.docInfo.numberOfKeywords,expression:"docInfo.numberOfKeywords"}],attrs:{type:"number"},domProps:{value:e.docInfo.numberOfKeywords},on:{input:function(t){t.target.composing||e.$set(e.docInfo,"numberOfKeywords",t.target.value)}}}),n("input",{directives:[{name:"model",rawName:"v-model",value:e.docInfo.text,expression:"docInfo.text"}],attrs:{type:"text"},domProps:{value:e.docInfo.text},on:{input:function(t){t.target.composing||e.$set(e.docInfo,"text",t.target.value)}}}),n("button",{staticStyle:{width:"100px",height:"21px"},on:{click:e.sendRequest}}),n("File"),n("span",{domProps:{textContent:e._s(e.response)}})],1)])},d=[],f=n("bee2"),m=n("bc3a"),v=n.n(m),b=function(e){Object(c["a"])(n,e);var t=Object(i["a"])(n);function n(){var e;return Object(u["a"])(this,n),e=t.apply(this,arguments),e.docInfo={language:"",maxNgramSize:0,numberOfKeywords:0,text:""},e}return Object(f["a"])(n,[{key:"sendRequest",value:function(){var e=this;v.a.post("http://10.10.1.30:5000/yake/",{language:this.docInfo.language,max_ngram_size:this.docInfo.maxNgramSize,number_of_keywords:this.docInfo.numberOfKeywords,text:this.docInfo.text},{headers:{"content-type":"text/json"}}).then((function(t){return e.response=t.data}))}}]),n}(l["c"]);Object(s["a"])([Object(l["b"])({type:String,required:!0})],b.prototype,"response",void 0),b=Object(s["a"])([l["a"]],b);var g=b,y=g,x=n("2877"),h=Object(x["a"])(y,p,d,!1,null,null,null),O=h.exports,j=function(e){Object(c["a"])(n,e);var t=Object(i["a"])(n);function n(){return Object(u["a"])(this,n),t.apply(this,arguments)}return n}(l["c"]);j=Object(s["a"])([Object(l["a"])({components:{MainComponent:O}})],j);var w,I,_=j,S=_,P=Object(x["a"])(S,r,a,!1,null,null,null),N=P.exports,k={},z=Object(x["a"])(k,w,I,!1,null,null,null),$=z.exports;o["a"].config.productionTip=!1,o["a"].component("main-component",$),new o["a"]({render:function(e){return e(N)}}).$mount("#app")}});
//# sourceMappingURL=test.81792e2f.js.map