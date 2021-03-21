(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[5],{"8b24":function(t,e,n){"use strict";n.r(e);var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("q-page",{staticClass:"row items-center justify-evenly"},[n("Main")],1)},i=[],o=n("60a3"),r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("q-page",{staticClass:"flex bg-white",staticStyle:{width:"100%",height:"100%"}},[n("q-card",{staticClass:"bg-white my-card text-black",staticStyle:{width:"100%"},attrs:{dark:"",bordered:""}},[n("q-card-section",[n("q-separator",{attrs:{dark:"",inset:""}}),n("q-card-section",[n("q-card",[n("q-card-section",[n("div",{staticClass:"text-h6 text-black"},[t._v("Для начала анализа загрузите вашу публикацию.")]),n("div",{staticClass:"text-subtitle2 text-black"},[t._v("Разрешённые типы документов:")]),n("div",{staticClass:"text-subtitle2 text-black"},[t._v(".doc .docx .pdf")]),n("q-file",{attrs:{filled:"","bottom-slots":"",multiple:"",label:"Публикация",counter:"","max-files":"1"},scopedSlots:t._u([{key:"before",fn:function(){return[n("q-icon",{attrs:{name:"folder_open"}})]},proxy:!0},{key:"hint",fn:function(){return[t._v("Выберите публикацию для анализа")]},proxy:!0},{key:"append",fn:function(){return[n("q-btn",{attrs:{round:"",dense:"",flat:"",icon:"add"},on:{click:function(t){t.stopPropagation()}}})]},proxy:!0}]),model:{value:t.files,callback:function(e){t.files=e},expression:"files"}})],1),n("q-card-section",[n("q-btn",{staticClass:"bg-indigo text-white",attrs:{flat:"",label:"Анализ"},on:{click:function(e){return e.stopPropagation(),t.setVisibleContent(e)}}},[n("q-tooltip",{attrs:{"content-class":"bg-indigo","content-style":"font-size: 16px",offset:[10,10]}},[t._v("Анализировать публикацию и получить рекомендации")])],1)],1),n("q-slide-transition",[n("div",{directives:[{name:"show",rawName:"v-show",value:t.visible,expression:"visible"}]},[n("q-separator",{attrs:{dark:"",inset:""}}),t.visible&&this.files?n("Recommendation",{attrs:{files:this.files},on:{"update:files":function(e){return t.$set(this,"files",e)}}}):t._e()],1)])],1)],1)],1)],1)],1)},s=[],c=function(){var t=function(e,n){return t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])},t(e,n)};return function(e,n){function a(){this.constructor=e}t(e,n),e.prototype=null===n?Object.create(n):(a.prototype=n.prototype,new a)}}(),l=function(t,e,n,a){var i,o=arguments.length,r=o<3?e:null===a?a=Object.getOwnPropertyDescriptor(e,n):a;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)r=Reflect.decorate(t,e,n,a);else for(var s=t.length-1;s>=0;s--)(i=t[s])&&(r=(o<3?i(r):o>3?i(e,n,r):i(e,n))||r);return o>3&&r&&Object.defineProperty(e,n,r),r},u=function(t,e,n,a){function i(t){return t instanceof n?t:new n((function(e){e(t)}))}return new(n||(n=Promise))((function(n,o){function r(t){try{c(a.next(t))}catch(e){o(e)}}function s(t){try{c(a["throw"](t))}catch(e){o(e)}}function c(t){t.done?n(t.value):i(t.value).then(r,s)}c((a=a.apply(t,e||[])).next())}))},p=function(t,e){var n,a,i,o,r={label:0,sent:function(){if(1&i[0])throw i[1];return i[1]},trys:[],ops:[]};return o={next:s(0),throw:s(1),return:s(2)},"function"===typeof Symbol&&(o[Symbol.iterator]=function(){return this}),o;function s(t){return function(e){return c([t,e])}}function c(o){if(n)throw new TypeError("Generator is already executing.");while(r)try{if(n=1,a&&(i=2&o[0]?a["return"]:o[0]?a["throw"]||((i=a["return"])&&i.call(a),0):a.next)&&!(i=i.call(a,o[1])).done)return i;switch(a=0,i&&(o=[2&o[0],i.value]),o[0]){case 0:case 1:i=o;break;case 4:return r.label++,{value:o[1],done:!1};case 5:r.label++,a=o[1],o=[0];continue;case 7:o=r.ops.pop(),r.trys.pop();continue;default:if(i=r.trys,!(i=i.length>0&&i[i.length-1])&&(6===o[0]||2===o[0])){r=0;continue}if(3===o[0]&&(!i||o[1]>i[0]&&o[1]<i[3])){r.label=o[1];break}if(6===o[0]&&r.label<i[1]){r.label=i[1],i=o;break}if(i&&r.label<i[2]){r.label=i[2],r.ops.push(o);break}i[2]&&r.ops.pop(),r.trys.pop();continue}o=e.call(t,r)}catch(s){o=[6,s],a=0}finally{n=i=0}if(5&o[0])throw o[1];return{value:o[0]?o[1]:void 0,done:!0}}},d=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return c(e,t),e.prototype.getActualityRequest=function(t){return u(this,void 0,Promise,(function(){var e,n;return p(this,(function(a){switch(a.label){case 0:return e=new FormData,e.append("classes",JSON.stringify(t)),[4,this.$axios.post("/api/actuality/analyze",e)];case 1:return n=a.sent(),[2,n.data]}}))}))},e.prototype.classesAnalyseRequest=function(t,e){return u(this,void 0,Promise,(function(){var n,a;return p(this,(function(i){switch(i.label){case 0:return n=new FormData,n.append("analyseResponse",JSON.stringify(t.yakeResponse)),n.append("articleId",e.toString()),[4,this.$axios.post("/api/classes/analyse",n)];case 1:return a=i.sent(),[2,a.data]}}))}))},e.prototype.sendToNlp=function(t){return u(this,void 0,Promise,(function(){var e,n;return p(this,(function(a){switch(a.label){case 0:return e=new FormData,e.append("yakeData",JSON.stringify(t)),[4,this.$axios.post("/api/nlp/analyse",e)];case 1:return n=a.sent(),[2,n.data]}}))}))},e.prototype.sendAndAnalyse=function(t){return u(this,void 0,Promise,(function(){var e,n;return p(this,(function(a){switch(a.label){case 0:return e=new FormData,t.files&&t.files.forEach((function(t){e.append("files",t)})),[4,this.$axios.post("/api/files/analyze",this.createFormDataForArticleFile(t,e),{headers:{"Content-Type":"multipart/form-data"}})];case 1:return n=a.sent(),[2,n.data]}}))}))},e.prototype.sendRequestToYandexFromServer=function(){return u(this,void 0,Promise,(function(){var t;return p(this,(function(e){switch(e.label){case 0:return[4,this.$axios.get("/api/yandex/search_count")];case 1:return t=e.sent(),[2,t.data]}}))}))},e.prototype.sendTextAndAnalyse=function(t){return u(this,void 0,Promise,(function(){var e,n;return p(this,(function(a){switch(a.label){case 0:return e=new FormData,e.append("language",t.language),e.append("max_ngram_size",t.maxNgramSize.toString()),e.append("deduplication_thresold",t.deduplicationThresold.toString()),e.append("deduplication_algo",t.deduplicationAlgo.toString()),e.append("windowSize",t.windowSize.toString()),e.append("number_of_keywords",t.numberOfKeywords.toString()),e.append("text",t.text),[4,this.$axios.post("/api/yake/analyze",e)];case 1:return n=a.sent(),[2,n.data]}}))}))},e.prototype.saveResultRequest=function(t,e,n){return u(this,void 0,Promise,(function(){var a,i;return p(this,(function(o){switch(o.label){case 0:return a=new FormData,a.append("analyseResponse",JSON.stringify(t.yakeResponse)),a.append("classes",JSON.stringify(n)),this.createFormDataForArticleFile(e,a),e.files&&e.files.forEach((function(t){a.append("files",t)})),[4,this.$axios.post("/api/yake/saveResultEntity",a)];case 1:return i=o.sent(),200===i.status?[2,i.data]:[2,null]}}))}))},e.prototype.actualityAnalyseRequest=function(t){return u(this,void 0,Promise,(function(){var e,n;return p(this,(function(a){switch(a.label){case 0:return e=new FormData,e.append("analyseResponse",JSON.stringify(t)),[4,this.$axios.post("/api/actuality/analyse",e)];case 1:return n=a.sent(),n.data||console.log("notify"),[2,n.data]}}))}))},e.prototype.createFormDataForArticleFile=function(t,e){return e.append("language",t.meta.language),e.append("max_ngram_size",t.meta.maxNgramSize.toString()),e.append("deduplication_thresold",t.meta.deduplicationThresold.toString()),e.append("deduplication_algo",t.meta.deduplicationAlgo.toString()),e.append("windowSize",t.meta.windowSize.toString()),e.append("number_of_keywords",t.meta.numberOfKeywords.toString()),e.append("text",t.meta.text),e},e.prototype.loadSavedResults=function(t){return u(this,void 0,Promise,(function(){var e,n;return p(this,(function(a){switch(a.label){case 0:return e=new FormData,e.append("yakeId",t.toString()),[4,this.$axios.post("/api/yake/response",e)];case 1:return n=a.sent(),[2,n.data]}}))}))},e=l([o["a"]],e),e}(o["d"]),f=d,y=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("q-stepper",{ref:"stepper",attrs:{"done-color":"green","active-color":"purple","inactive-color":"indigo",id:"publication-analyse-stepper"},scopedSlots:t._u([{key:"message",fn:function(){return[1===t.step?n("q-banner",{staticClass:"bg-indigo text-white q-px-lg"},[t._v("Сохраняем вашу публикацию...")]):2===t.step?n("q-banner",{staticClass:"bg-indigo text-white q-px-lg"},[t._v("Подгатавливаем текст публикации для более точного анализа...")]):3===t.step?n("q-banner",{staticClass:"bg-indigo text-white q-px-lg"},[t._v("Анализируем текст публикации...")]):4===t.step?n("q-banner",{staticClass:"bg-indigo text-white q-px-lg"},[t._v("Фильтруем результаты анализа...")]):5===t.step?n("q-banner",{staticClass:"bg-indigo text-white q-px-lg"},[t._v("Формируем рекомендации, для повышения актуальности публикации...")]):6===t.step?n("q-banner",{staticClass:"bg-green text-white q-px-lg"},[t._v("Готово!\nПросмотрите рекоммендации, оцените их точность и полезность.")]):t._e()]},proxy:!0}]),model:{value:t.step,callback:function(e){t.step=e},expression:"step"}},[n("q-step",{attrs:{name:1,title:"Сохранение публикации",icon:"settings",done:t.step>1}}),n("q-step",{attrs:{name:2,title:"Подготовка публикации к анализу",icon:"settings",done:t.step>2}}),n("q-step",{attrs:{name:3,title:"Анализ текста",icon:"settings",done:t.step>3}}),n("q-step",{attrs:{name:4,title:"Фильтрация результатов",icon:"settings",done:t.step>4}}),n("q-step",{attrs:{name:5,title:"Формирование рекомендаций",icon:"settings",done:t.step>5}}),n("q-step",{attrs:{name:6,title:"Завершение",icon:"settings",done:t.step>6}})],1),n("q-card-section",[6===t.step?n("q-btn",{staticStyle:{float:"right"},attrs:{icon:"settings",round:""},on:{click:function(e){t.dialog=!0}}}):t._e(),n("div",{staticClass:"text-h6 text-black"},[t._v(t._s(t.currentStatusText))]),n("div",{staticClass:"text-subtitle2 text-black"},[t._v(t._s(t.currentStatusTextDescription))]),n("div",{staticClass:"q-gutter-md row"},[t._l(3,(function(e){return 6!==t.step?n("q-intersection",{key:e,staticClass:"example-item",attrs:{transition:"scale"}},[n("q-card",{staticStyle:{width:"400px"}},[n("q-item",[n("q-item-section",[n("q-item-label",[n("q-skeleton",{attrs:{type:"text"}})],1),n("q-item-label",{attrs:{caption:""}},[n("q-skeleton",{attrs:{type:"text"}})],1)],1)],1),n("q-skeleton",{attrs:{height:"100px",square:""}}),n("q-card-actions",{staticClass:"q-gutter-md",attrs:{align:"right"}},[n("q-skeleton",{attrs:{type:"QBtn"}}),n("q-skeleton",{attrs:{type:"QBtn"}})],1)],1)],1):t._e()})),6===t.step?n("InnerRecommendation"):t._e(),6===t.step?n("RecommendationSettings",{attrs:{dialog:t.dialog},on:{"update:dialog":function(e){t.dialog=e}}}):t._e()],2)],1)],1)},h=[],m=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"q-pa-md row items-start q-gutter-md"},[n("q-card",{staticClass:"recommendation-card-width"},[n("q-card-section",{staticClass:"bg-indigo text-white"},[n("div",{staticClass:"text-h6"},[t._v("Общие рекоммендации по увеличению актуальности")]),n("div",{staticClass:"text-subtitle2"},[t._v("Предложения для правок в тексте")])]),n("q-card-section",[n("q-chip",{staticClass:"bg-indigo-5",attrs:{square:"","text-color":"white","icon-right":"edit"}},[t._v("Внесите предложенные правки в ключевые слова")])],1),n("q-card-section",[n("div",{staticClass:"text-subtitle2"},[t._v("Не вносите в текст следующие ключевые слова:")]),n("q-chip",{attrs:{square:"","text-color":"white",color:"red","icon-right":"remove"}},[t._v(t._s(this.srecommendation.classKeywordPairMin.keyword))])],1),n("q-separator",{attrs:{inset:""}}),n("q-card-actions",[n("q-btn",{staticClass:"bg-green text-white"},[t._v("Принять")]),n("q-btn",{staticClass:"bg-red-4 text-white"},[t._v("Отклонить")])],1)],1),n("q-card",{staticClass:"recommendation-card-width"},[n("q-card-section",{staticClass:"bg-indigo text-white"},[n("div",{staticClass:"text-h6"},[t._v("Рекоммендации по редактированию ключевых слов")]),n("div",{staticClass:"text-subtitle2"},[t._v("Ключевые слова")])]),n("q-card-section",[n("q-chip",{attrs:{square:"","text-color":"white",color:"green","icon-right":"add"}},[t._v(t._s(this.srecommendation.classKeywordPairMax.keyword))])],1),n("q-separator",{attrs:{inset:""}}),n("q-card-actions",[n("q-btn",{staticClass:"bg-green text-white"},[t._v("Принять")]),n("q-btn",{staticClass:"bg-red-4 text-white"},[t._v("Отклонить")])],1)],1),n("q-card",{staticClass:"recommendation-card-width"},[n("q-card-section",{staticClass:"bg-indigo text-white"},[n("div",{staticClass:"text-h6"},[t._v("Предполагаемые темы по ключевым словам текста статьи")]),n("div",{staticClass:"text-subtitle2"},[t._v("Ключевые слова")])]),n("q-card-section",t._l(this.srecommendation.classKeywordPairs,(function(e){return n("q-chip",{key:"i",attrs:{square:"","text-color":"white",color:"green"}},[t._v(t._s(e.cluster))])})),1),n("q-separator",{attrs:{dark:""}})],1),n("q-card",{staticClass:"recommendation-card-width"},[n("q-card-section",{staticClass:"bg-indigo text-white"},[n("div",{staticClass:"text-h6"},[t._v("Общая статистика")]),n("div",{staticClass:"text-subtitle2"},[t._v("Результаты анализа")])]),n("q-card-section",[this.srecommendation.actuality>50?n("q-chip",{attrs:{square:"",color:"green","text-color":"white","icon-right":"star"}},[t._v("Актуальность составляет "+t._s(this.srecommendation.actuality)+"%")]):n("q-chip",{attrs:{square:"",color:"yellow","text-color":"black","icon-right":"star"}},[t._v("Актуальность составляет "+t._s(this.srecommendation.actuality)+"%")])],1)],1)],1)},g=[],b=n("2b0e"),w=n("2fe1"),v=n("4bb5"),x=function(){var t=function(e,n){return t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])},t(e,n)};return function(e,n){function a(){this.constructor=e}t(e,n),e.prototype=null===n?Object.create(n):(a.prototype=n.prototype,new a)}}(),_=function(t,e,n,a){var i,o=arguments.length,r=o<3?e:null===a?a=Object.getOwnPropertyDescriptor(e,n):a;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)r=Reflect.decorate(t,e,n,a);else for(var s=t.length-1;s>=0;s--)(i=t[s])&&(r=(o<3?i(r):o>3?i(e,n,r):i(e,n))||r);return o>3&&r&&Object.defineProperty(e,n,r),r},q=Object(v["a"])("RecommendationModule"),O=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return x(e,t),_([q.Mutation("setRecommendation")],e.prototype,"setRecommendation",void 0),_([q.Getter("getRecommendation")],e.prototype,"recommendations",void 0),e=_([w["b"]],e),e}(b["a"]),k=O,C=function(){var t=function(e,n){return t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])},t(e,n)};return function(e,n){function a(){this.constructor=e}t(e,n),e.prototype=null===n?Object.create(n):(a.prototype=n.prototype,new a)}}(),S=function(t,e,n,a){var i,o=arguments.length,r=o<3?e:null===a?a=Object.getOwnPropertyDescriptor(e,n):a;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)r=Reflect.decorate(t,e,n,a);else for(var s=t.length-1;s>=0;s--)(i=t[s])&&(r=(o<3?i(r):o>3?i(e,n,r):i(e,n))||r);return o>3&&r&&Object.defineProperty(e,n,r),r},P=function(t){function e(){var e=null!==t&&t.apply(this,arguments)||this;return e.srecommendation={payload:[],actuality:0,classKeywordPairMax:{actuality:0,cluster:"",keyword:""},classKeywordPairMin:{actuality:0,cluster:"",keyword:""},classKeywordPairs:[],classesActuality:[],keywordClassMax:""},e}return C(e,t),e.prototype.recommendationWatcher=function(){this.srecommendation=this.recommendations},S([Object(o["e"])("recommendations")],e.prototype,"recommendationWatcher",null),e=S([o["a"]],e),e}(Object(o["b"])(k)),j=P,R=j,A=n("2877"),Q=n("f09f"),T=n("a370"),z=n("b047"),D=n("eb85"),F=n("4b7e"),K=n("9c40"),M=n("eebe"),$=n.n(M),N=Object(A["a"])(R,m,g,!1,null,null,null),E=N.exports;$()(N,"components",{QCard:Q["a"],QCardSection:T["a"],QChip:z["a"],QSeparator:D["a"],QCardActions:F["a"],QBtn:K["a"]});var I=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("q-dialog",{attrs:{persistent:"",maximized:t.maximizedToggle,"transition-show":"slide-up","transition-hide":"slide-down"},model:{value:t.sDialog,callback:function(e){t.sDialog=e},expression:"sDialog"}},[n("q-card",{staticClass:"text-white bg-indigo"},[n("q-bar",[n("q-space"),n("q-btn",{attrs:{dense:"",flat:"",icon:"minimize",disable:!t.maximizedToggle},on:{click:function(e){t.maximizedToggle=!1}}},[t.maximizedToggle?n("q-tooltip",{attrs:{"content-class":"bg-white text-primary"}},[t._v("Minimize")]):t._e()],1),n("q-btn",{attrs:{dense:"",flat:"",icon:"crop_square",disable:t.maximizedToggle},on:{click:function(e){t.maximizedToggle=!0}}},[t.maximizedToggle?t._e():n("q-tooltip",{attrs:{"content-class":"bg-white text-primary"}},[t._v("Maximize")])],1),n("q-btn",{directives:[{name:"close-popup",rawName:"v-close-popup"}],attrs:{dense:"",flat:"",icon:"close"}},[n("q-tooltip",{attrs:{"content-class":"bg-white text-primary"}},[t._v("Close")])],1)],1),n("q-card-section",[n("div",{staticClass:"text-h6"},[t._v("Техническая информация")])]),n("q-card-section",{staticClass:"q-gutter-md row"},[n("q-table",{staticStyle:{width:"500px"},attrs:{title:"Класс - актуальность",data:this.srecommendation.classesActuality,separator:t.separator,"virtual-scroll":"",columns:t.classActuality,"pagination.sync":"pagination","rows-per-page-options":[0],"row-key":"name"}}),n("q-table",{staticStyle:{width:"500px"},attrs:{title:"Ключевые слова, Классы, актуальность класса",data:this.srecommendation.classKeywordPairs,separator:t.separator,"virtual-scroll":"",columns:t.classKeyActuality,"pagination.sync":"pagination","rows-per-page-options":[0],"row-key":"keyword"}}),n("q-table",{staticStyle:{width:"700px"},attrs:{title:"Результаты фильтрации",data:this.srecommendation.payload,separator:t.separator,"virtual-scroll":"",columns:t.nlpPayloadColumns,"pagination.sync":"pagination","rows-per-page-options":[0],"row-key":"ngram"}})],1)],1)],1)},B=[],J=function(){var t=function(e,n){return t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])},t(e,n)};return function(e,n){function a(){this.constructor=e}t(e,n),e.prototype=null===n?Object.create(n):(a.prototype=n.prototype,new a)}}(),G=function(t,e,n,a){var i,o=arguments.length,r=o<3?e:null===a?a=Object.getOwnPropertyDescriptor(e,n):a;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)r=Reflect.decorate(t,e,n,a);else for(var s=t.length-1;s>=0;s--)(i=t[s])&&(r=(o<3?i(r):o>3?i(e,n,r):i(e,n))||r);return o>3&&r&&Object.defineProperty(e,n,r),r},W=function(t){function e(){var e=null!==t&&t.apply(this,arguments)||this;return e.separator="cell",e.srecommendation={actuality:0,payload:[{avg:0,isGood:0,ngram:"",value:0}],classKeywordPairMax:{actuality:0,cluster:"",keyword:""},classKeywordPairMin:{actuality:0,cluster:"",keyword:""},classKeywordPairs:[{actuality:0,cluster:"",keyword:""}],classesActuality:[{name:"",embedding:"",classActuality:0}],keywordClassMax:""},e.maximizedToggle=!0,e.classKeyActuality=[{name:"cluster",label:"Класс",field:"cluster",align:"center",style:"width: 10px"},{name:"keyword",label:"Ключевая фраза",field:"keyword",align:"center",style:"width: 10px"},{name:"actuality",label:"Актаульность",field:"actuality",align:"center",style:"width: 10px"}],e.classActuality=[{name:"name",label:"Класс",field:"name",align:"center",style:"width: 10px"},{name:"classActuality",label:"Актуальность",field:"classActuality",align:"center",style:"width: 10px"}],e.nlpPayloadColumns=[{name:"ngram",label:"Ключевая фраза",field:"ngram",align:"center",style:"width: 10px"},{name:"avg",label:"Среднее",field:"avg",align:"center",style:"width: 10px"},{name:"value",label:"Значение важности",field:"value",align:"center",style:"width: 10px"},{name:"isGood",label:"Прошёл фильтр",field:"isGood",align:"center",style:"width: 10px"}],e}return J(e,t),e.prototype.recommendationWatcher=function(){this.srecommendation=this.recommendations,console.log(this.srecommendation)},G([Object(o["c"])("dialog",{type:Boolean})],e.prototype,"sDialog",void 0),G([Object(o["e"])("recommendations")],e.prototype,"recommendationWatcher",null),e=G([o["a"]],e),e}(Object(o["b"])(k)),V=W,L=V,Y=n("24e8"),H=n("d847"),U=n("2c91"),X=n("05c0"),Z=n("eaac"),tt=n("7f67"),et=Object(A["a"])(L,I,B,!1,null,null,null),nt=et.exports;$()(et,"components",{QDialog:Y["a"],QCard:Q["a"],QBar:H["a"],QSpace:U["a"],QBtn:K["a"],QTooltip:X["a"],QCardSection:T["a"],QTable:Z["a"]}),$()(et,"directives",{ClosePopup:tt["a"]});var at=n("4572"),it=n("cc7d"),ot=n.n(it),rt=function(){var t=function(e,n){return t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])},t(e,n)};return function(e,n){function a(){this.constructor=e}t(e,n),e.prototype=null===n?Object.create(n):(a.prototype=n.prototype,new a)}}(),st=function(t,e,n,a){var i,o=arguments.length,r=o<3?e:null===a?a=Object.getOwnPropertyDescriptor(e,n):a;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)r=Reflect.decorate(t,e,n,a);else for(var s=t.length-1;s>=0;s--)(i=t[s])&&(r=(o<3?i(r):o>3?i(e,n,r):i(e,n))||r);return o>3&&r&&Object.defineProperty(e,n,r),r},ct=function(t,e,n,a){function i(t){return t instanceof n?t:new n((function(e){e(t)}))}return new(n||(n=Promise))((function(n,o){function r(t){try{c(a.next(t))}catch(e){o(e)}}function s(t){try{c(a["throw"](t))}catch(e){o(e)}}function c(t){t.done?n(t.value):i(t.value).then(r,s)}c((a=a.apply(t,e||[])).next())}))},lt=function(t,e){var n,a,i,o,r={label:0,sent:function(){if(1&i[0])throw i[1];return i[1]},trys:[],ops:[]};return o={next:s(0),throw:s(1),return:s(2)},"function"===typeof Symbol&&(o[Symbol.iterator]=function(){return this}),o;function s(t){return function(e){return c([t,e])}}function c(o){if(n)throw new TypeError("Generator is already executing.");while(r)try{if(n=1,a&&(i=2&o[0]?a["return"]:o[0]?a["throw"]||((i=a["return"])&&i.call(a),0):a.next)&&!(i=i.call(a,o[1])).done)return i;switch(a=0,i&&(o=[2&o[0],i.value]),o[0]){case 0:case 1:i=o;break;case 4:return r.label++,{value:o[1],done:!1};case 5:r.label++,a=o[1],o=[0];continue;case 7:o=r.ops.pop(),r.trys.pop();continue;default:if(i=r.trys,!(i=i.length>0&&i[i.length-1])&&(6===o[0]||2===o[0])){r=0;continue}if(3===o[0]&&(!i||o[1]>i[0]&&o[1]<i[3])){r.label=o[1];break}if(6===o[0]&&r.label<i[1]){r.label=i[1],i=o;break}if(i&&r.label<i[2]){r.label=i[2],r.ops.push(o);break}i[2]&&r.ops.pop(),r.trys.pop();continue}o=e.call(t,r)}catch(s){o=[6,s],a=0}finally{n=i=0}if(5&o[0])throw o[1];return{value:o[0]?o[1]:void 0,done:!0}}},ut=function(t){function e(){var e=null!==t&&t.apply(this,arguments)||this;return e.currentStatusText="Анализ публикации...",e.currentStatusTextDescription="Пожалуйста дождитесь окончания анализа вашей публикации.",e.step=1,e.dialog=!1,e.articleFile={files:null,meta:{language:"ru",maxNgramSize:3,deduplicationThresold:1,deduplicationAlgo:"leve",windowSize:1,numberOfKeywords:10,text:""}},e.data={yakeResponse:[{ngram:"",score:0}],generatedArticleId:0},e.recomendation={payload:[],actuality:0,classKeywordPairMax:{actuality:0,cluster:"",keyword:""},classKeywordPairMin:{actuality:0,cluster:"",keyword:""},classKeywordPairs:[],classesActuality:[],keywordClassMax:""},e}return rt(e,t),e.prototype.stepWatcher=function(){6===this.step&&(this.currentStatusText="Просмотрите полученные рекоммендации.",this.currentStatusTextDescription="Пожалуйста выберите Принять / Отклонить. Это необходимо для сбора статистики для улучшения качества и точности рекомендаций в дальнейшем.")},e.prototype.created=function(){var t=this;this.stompClient=at["Stomp"].over((function(){return new ot.a("/steps")})),this.stompClient.connect({},(function(e){console.log("Connected: "+e),t.stompClient.subscribe("/user/topic/analyseSteps",(function(e){t.showMessageOutput(e.body)}))}))},e.prototype.mounted=function(){return ct(this,void 0,Promise,(function(){var t,e;return lt(this,(function(n){switch(n.label){case 0:return this.articleFile?(this.articleFile.files=this.sFiles,t=this,[4,this.sendAndAnalyse(this.articleFile)]):[3,3];case 1:return t.data=n.sent(),this.data&&this.data.yakeResponse.length?(e=this,[4,this.sendToNlp(this.data.yakeResponse)]):[3,3];case 2:e.recomendation=n.sent(),this.setRecommendation(this.recomendation),n.label=3;case 3:return[2]}}))}))},e.prototype.showMessageOutput=function(t){this.step=t,this.$refs.stepper.next()},st([Object(o["c"])("files")],e.prototype,"sFiles",void 0),st([Object(o["e"])("step")],e.prototype,"stepWatcher",null),e=st([Object(o["a"])({components:{InnerRecommendation:E,RecommendationSettings:nt}})],e),e}(Object(o["b"])(f,k)),pt=ut,dt=pt,ft=n("f531"),yt=n("87fe"),ht=n("54e1"),mt=n("ad56"),gt=n("66e5"),bt=n("4074"),wt=n("0170"),vt=n("293e"),xt=Object(A["a"])(dt,y,h,!1,null,null,null),_t=xt.exports;$()(xt,"components",{QStepper:ft["a"],QStep:yt["a"],QBanner:ht["a"],QCardSection:T["a"],QBtn:K["a"],QIntersection:mt["a"],QCard:Q["a"],QItem:gt["a"],QItemSection:bt["a"],QItemLabel:wt["a"],QSkeleton:vt["a"],QCardActions:F["a"]});var qt=function(){var t=function(e,n){return t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])},t(e,n)};return function(e,n){function a(){this.constructor=e}t(e,n),e.prototype=null===n?Object.create(n):(a.prototype=n.prototype,new a)}}(),Ot=function(t,e,n,a){var i,o=arguments.length,r=o<3?e:null===a?a=Object.getOwnPropertyDescriptor(e,n):a;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)r=Reflect.decorate(t,e,n,a);else for(var s=t.length-1;s>=0;s--)(i=t[s])&&(r=(o<3?i(r):o>3?i(e,n,r):i(e,n))||r);return o>3&&r&&Object.defineProperty(e,n,r),r},kt=function(t){function e(){var e=null!==t&&t.apply(this,arguments)||this;return e.visible=!1,e.files=null,e.a=[{name:"cluster",label:"Класс",field:"cluster",align:"center",style:"width: 10px"},{name:"keyword",label:"Ключевая фраза",field:"keyword",align:"center",style:"width: 10px"},{name:"actuality",label:"Актаульность",field:"actuality",align:"center",style:"width: 10px"}],e.b=[{name:"name",label:"Класс",field:"name",align:"center",style:"width: 10px"},{name:"classActuality",label:"Актуальность",field:"classActuality",align:"center",style:"width: 10px"}],e.classColumns=[{name:"className",label:"Имя класса",field:"className",align:"center",style:"width: 10px"},{name:"actuality",label:"Вес класса",field:"actuality",align:"center",style:"width: 10px"},{name:"keywordText",label:"Ключевое слово",field:"keywordText",align:"center",style:"width: 10px"}],e.classes=[],e.articleFile={files:null,meta:{language:"ru",maxNgramSize:3,deduplicationThresold:1,deduplicationAlgo:"leve",windowSize:1,numberOfKeywords:10,text:""}},e}return qt(e,t),e.prototype.setVisibleContent=function(){this.files?this.visible=!0:this.$q.notify({icon:"warning",message:"Загрузите публикацию.",caption:"Для продолжения - загрузите публикацию.",color:"primary"})},e=Ot([Object(o["a"])({components:{Recommendation:_t}})],e),e}(Object(o["b"])(f)),Ct=kt,St=Ct,Pt=n("9989"),jt=n("7d53"),Rt=n("0016"),At=n("e9c1"),Qt=Object(A["a"])(St,r,s,!1,null,null,null),Tt=Qt.exports;$()(Qt,"components",{QPage:Pt["a"],QCard:Q["a"],QCardSection:T["a"],QSeparator:D["a"],QFile:jt["a"],QIcon:Rt["a"],QBtn:K["a"],QTooltip:X["a"],QSlideTransition:At["a"]});var zt=function(){var t=function(e,n){return t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])},t(e,n)};return function(e,n){function a(){this.constructor=e}t(e,n),e.prototype=null===n?Object.create(n):(a.prototype=n.prototype,new a)}}(),Dt=function(t,e,n,a){var i,o=arguments.length,r=o<3?e:null===a?a=Object.getOwnPropertyDescriptor(e,n):a;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)r=Reflect.decorate(t,e,n,a);else for(var s=t.length-1;s>=0;s--)(i=t[s])&&(r=(o<3?i(r):o>3?i(e,n,r):i(e,n))||r);return o>3&&r&&Object.defineProperty(e,n,r),r},Ft=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return zt(e,t),e=Dt([Object(o["a"])({components:{Main:Tt}})],e),e}(o["d"]),Kt=Ft,Mt=Kt,$t=Object(A["a"])(Mt,a,i,!1,null,null,null);e["default"]=$t.exports;$()($t,"components",{QPage:Pt["a"]})}}]);